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
        boolean flag = false;
        for (DamageState<?> state : this.getStates())
            if (state.get() == tag)
                flag = true;
        // Is it going too far?
        cir.setReturnValue(flag);
    }

    @Override
    public ArrayList<DamageState<?>> getStates()
    {
        return damageStates;
    }
}
