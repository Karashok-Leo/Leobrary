package karashokleo.leobrary.damage.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{
    @ModifyVariable(
            method = "damage",
            at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/entity/LivingEntity;isSleeping()Z"),
            argsOnly = true
    )
    private float inject_applyDamage_shieldPhase(float amount)
    {
        return amount;
    }

    @ModifyVariable(
            method = "applyArmorToDamage",
            at = @At(value = "HEAD"),
            argsOnly = true
    )
    private float inject_applyDamage_armorPhase(float amount)
    {
        return amount;
    }

    @ModifyVariable(
            method = "modifyAppliedDamage",
            at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"),
            argsOnly = true
    )
    private float inject_applyDamage_effectPhase(float amount)
    {
        return amount;
    }

    @ModifyVariable(
            method = "modifyAppliedDamage",
            at = @At(
                    value = "JUMP",
                    ordinal = 0,
                    opcode = 157
            ),
            argsOnly = true
    )
    private float inject_applyDamage_enchantmentPhase(float amount)
    {
        return amount;
    }

    @ModifyVariable(
            method = "applyDamage",
            at = @At(value = "INVOKE_ASSIGN", ordinal = 0, target = "Lnet/minecraft/entity/LivingEntity;modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F"),
            argsOnly = true
    )
    private float inject_applyDamage_absorbPhase(float amount)
    {
        return amount;
    }

    @ModifyVariable(
            method = "applyDamage",
            at = @At(value = "LOAD", ordinal = 6),
            index = 2,
            argsOnly = true
    )
    private float inject_applyDamage_applyPhase(float amount)
    {
        return amount;
    }
}
