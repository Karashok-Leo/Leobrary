package karashokleo.leobrary.damage.mixin;

import karashokleo.leobrary.damage.api.state.DamageState;
import karashokleo.leobrary.damage.api.state.DefaultDamageStateProvider;
import karashokleo.leobrary.damage.api.state.TagDamageState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Predicate;

@Mixin(DamageSource.class)
public abstract class DamageSourceMixin implements DefaultDamageStateProvider
{
    @Unique
    private final ArrayList<DamageState<?>> damageStates = new ArrayList<>();

    @Inject(
            method = "<init>(Lnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;)V",
            at = @At("TAIL")
    )
    private void inject_init(RegistryEntry<DamageType> type, Entity source, Entity attacker, Vec3d position, CallbackInfo ci)
    {
        type.streamTags().forEach(tagKey -> this.getStates().add(new TagDamageState(tagKey)));
    }

    @Inject(
            method = "isIn",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_isIn(TagKey<DamageType> tag, CallbackInfoReturnable<Boolean> cir)
    {
        // Is it going too far?
        cir.setReturnValue(this.hasState(state -> state.get() == tag));
    }

    @Override
    public Collection<DamageState<?>> getStates()
    {
        return Collections.unmodifiableCollection(this.damageStates);
    }

    @Override
    public boolean hasState(Predicate<DamageState<?>> predicate)
    {
        return damageStates.stream().anyMatch(predicate);
    }

    @Override
    public Optional<DamageState<?>> getState(Predicate<DamageState<?>> predicate)
    {
        return damageStates.stream().filter(predicate).findFirst();
    }

    @Override
    public void addState(DamageState<?> damageState)
    {
        damageStates.add(damageState);
    }

    @Override
    public void removeState(Predicate<DamageState<?>> predicate)
    {
        damageStates.removeIf(predicate);
    }

    @Override
    public void clearStates()
    {
        damageStates.clear();
    }
}
