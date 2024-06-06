package net.karashokleo.leobrary.effect.api.util;

import net.karashokleo.leobrary.effect.api.event.EffectAdded;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@SuppressWarnings("unused")
public class EffectUtil
{
    public static boolean forceAddEffect(LivingEntity entity, StatusEffectInstance newEffectInstance, @Nullable Entity source)
    {
        Map<StatusEffect, StatusEffectInstance> active = entity.getActiveStatusEffects();
        StatusEffectInstance oldEffectInstance = active.get(newEffectInstance.getEffectType());

        CallbackInfoReturnable<Boolean> cir = new CallbackInfoReturnable<>("ForceAddedEffectCallback", true);
        EffectAdded.EVENT.invoker().onEffectAdded(entity, newEffectInstance, oldEffectInstance, entity, cir);
        if (cir.isCancelled()) return cir.getReturnValue();

        if (oldEffectInstance == null)
        {
            active.put(newEffectInstance.getEffectType(), newEffectInstance);
            entity.onStatusEffectApplied(newEffectInstance, source);
            return true;
        }
        if (oldEffectInstance.upgrade(newEffectInstance))
        {
            entity.onStatusEffectUpgraded(oldEffectInstance, true, source);
            return true;
        }
        return false;
    }
}
