package karashokleo.leobrary.damage.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import karashokleo.leobrary.damage.accessor.DamageCallDepthAccessor;
import karashokleo.leobrary.damage.api.modify.DamagePhase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{
    @WrapMethod(method = "damage")
    private boolean wrap_damage(DamageSource source, float amount, Operation<Boolean> original)
    {
        DamageCallDepthAccessor accessor = (DamageCallDepthAccessor) source;
        accessor.leobrary$enterDamageCall();
        boolean value = original.call(source, amount);
        accessor.leobrary$exitDamageCall();
        return value;
    }

    @ModifyVariable(
        method = "damage",
        at = @At(
            value = "INVOKE",
            ordinal = 0,
            target = "Lnet/minecraft/entity/LivingEntity;isSleeping()Z"
        ),
        argsOnly = true
    )
    private float inject_damage_shieldPhase(float amount, @Local(argsOnly = true) DamageSource source)
    {
        return DamagePhase.SHIELD.getFinalAmount((LivingEntity) (Object) this, source, amount);
    }

    @ModifyVariable(
        method = "applyArmorToDamage",
        at = @At(value = "HEAD"),
        argsOnly = true
    )
    private float inject_applyDamage_armorPhase(float amount, @Local(argsOnly = true) DamageSource source)
    {
        return DamagePhase.ARMOR.getFinalAmount((LivingEntity) (Object) this, source, amount);
    }

    @ModifyVariable(
        method = "modifyAppliedDamage",
        at = @At(
            value = "INVOKE",
            ordinal = 0,
            target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"
        ),
        argsOnly = true
    )
    private float inject_applyDamage_effectPhase(float amount, @Local(argsOnly = true) DamageSource source)
    {
        return DamagePhase.EFFECT.getFinalAmount((LivingEntity) (Object) this, source, amount);
    }

    @ModifyVariable(
        method = "modifyAppliedDamage",
        at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/enchantment/EnchantmentHelper;getProtectionAmount(Ljava/lang/Iterable;Lnet/minecraft/entity/damage/DamageSource;)I"
        ),
        argsOnly = true
    )
    private float inject_applyDamage_enchantmentPhase(float amount, @Local(argsOnly = true) DamageSource source)
    {
        return DamagePhase.ENCHANTMENT.getFinalAmount((LivingEntity) (Object) this, source, amount);
    }

    @ModifyVariable(
        method = "applyDamage",
        at = @At(
            value = "INVOKE_ASSIGN",
            ordinal = 0,
            target = "Lnet/minecraft/entity/LivingEntity;modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F"
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

    /*
    Trying to control knockback
    @ModifyExpressionValue(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/damage/DamageSource;isIn(Lnet/minecraft/registry/tag/TagKey;)Z",
                    ordinal = 7
            )
    )
    private boolean inject_damage_noKnockBack(boolean original, @Local(argsOnly = true) DamageSource source)
    {
        return original || source.isIn(DamageTypeTags.NO_KNOCKBACK);
    }
    */
}
