package net.karashokleo.leobrary.effect.api.util;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class GenericStatusEffect extends StatusEffect
{
    public GenericStatusEffect(StatusEffectCategory category, int color)
    {
        super(category, color);
    }
}
