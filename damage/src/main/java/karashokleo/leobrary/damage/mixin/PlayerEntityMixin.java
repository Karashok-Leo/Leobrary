package karashokleo.leobrary.damage.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import karashokleo.leobrary.damage.api.modify.DamagePhase;
import karashokleo.leobrary.damage.api.state.DamageStateProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin
{
    @ModifyVariable(
            method = "applyDamage",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    ordinal = 0,
                    target = "Lnet/minecraft/entity/player/PlayerEntity;modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F"
            ),
            argsOnly = true
    )
    private float inject_applyDamage_absorbPhase(float amount, @Local(argsOnly = true) DamageSource source)
    {
        return DamagePhase.ABSORB.getFinalAmount((LivingEntity) (Object) this, source, amount);
    }

    @ModifyVariable(
            method = "applyDamage",
            at = @At(value = "LOAD", ordinal = 6),
            index = 2,
            argsOnly = true
    )
    private float inject_applyDamage_applyPhase(float amount, @Local(argsOnly = true) DamageSource source)
    {
        return DamagePhase.APPLY.getFinalAmount((LivingEntity) (Object) this, source, amount);
    }

    @Inject(
            method = "applyDamage",
            at = @At("TAIL")
    )
    private void inject_damage_clearStates(DamageSource source, float amount, CallbackInfo ci)
    {
        ((DamageStateProvider) source).clearStates();
    }
}
