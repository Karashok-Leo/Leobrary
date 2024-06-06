package net.karashokleo.leobrary.effect.api.util;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

@SuppressWarnings("unused")
public class GenericStatusEffect extends StatusEffect
{
    public GenericStatusEffect(StatusEffectCategory category, int color)
    {
        super(category, color);
    }
}
