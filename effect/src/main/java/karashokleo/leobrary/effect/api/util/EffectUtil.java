package karashokleo.leobrary.effect.api.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class EffectUtil
{
    public static Stream<StatusEffect> streamEffects(LivingEntity entity)
    {
        return entity.getActiveStatusEffects().keySet().stream();
    }

    public static Stream<StatusEffect> streamEffects(LivingEntity entity, StatusEffectCategory category)
    {
        return streamEffects(entity).filter(effect -> effect.getCategory() == category);
    }

    public static boolean forceAddEffect(LivingEntity entity, StatusEffectInstance newEffectInstance, @Nullable Entity source)
    {
        Map<StatusEffect, StatusEffectInstance> active = entity.getActiveStatusEffects();
        StatusEffectInstance oldEffectInstance = active.get(newEffectInstance.getEffectType());

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
