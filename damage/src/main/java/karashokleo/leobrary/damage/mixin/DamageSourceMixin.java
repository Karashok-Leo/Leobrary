package karashokleo.leobrary.damage.mixin;

import karashokleo.leobrary.damage.api.event.DamageSourceCreateCallback;
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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.Predicate;

@Mixin(DamageSource.class)
public abstract class DamageSourceMixin implements DefaultDamageStateProvider
{
    @Unique
    private final HashSet<DamageState> damageStates = new HashSet<>();

    @Inject(
            method = "<init>(Lnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;)V",
            at = @At("RETURN")
    )
    private void inject_init(RegistryEntry<DamageType> type, Entity source, Entity attacker, Vec3d position, CallbackInfo ci)
    {
        DamageSourceCreateCallback.EVENT.invoker().onDamageSourceCreate(type, source, attacker, position, (DamageSource) (Object) this);
    }

    @Inject(
            method = "isIn",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_isIn(TagKey<DamageType> tag, CallbackInfoReturnable<Boolean> cir)
    {
        this.getState(TagDamageState.class, state -> state.tag() == tag)
                .ifPresent(state -> cir.setReturnValue(state.in()));
    }

    @Override
    public Collection<DamageState> getStates()
    {
        return Collections.unmodifiableCollection(this.damageStates);
    }

    @Override
    public boolean hasState(DamageState state)
    {
        return damageStates.contains(state);
    }

    @Override
    public boolean hasState(Predicate<DamageState> predicate)
    {
        return damageStates.stream().anyMatch(predicate);
    }

    @Override
    public <T extends DamageState> Optional<T> getState(Class<T> clazz, Predicate<T> predicate)
    {
        return damageStates.stream().filter(clazz::isInstance).map(clazz::cast).filter(predicate).findFirst();
    }

    @Override
    public Optional<DamageState> getState(Predicate<DamageState> predicate)
    {
        return damageStates.stream().filter(predicate).findFirst();
    }

    @Override
    public void addState(DamageState damageState)
    {
        damageStates.add(damageState);
    }

    @Override
    public void removeState(Predicate<DamageState> predicate)
    {
        damageStates.removeIf(predicate);
    }

    @Override
    public void clearStates()
    {
        damageStates.clear();
    }
}
