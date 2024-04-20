package net.karashokleo.leobrary.datagen.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.stat.StatType;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class TextContainer
{
    protected final Map<String, String> texts = new HashMap<>();

    public Map<String, String> getTexts()
    {
        return texts;
    }

    public void addText(String key, String value)
    {
        texts.put(key, value);
    }

    public void addText(Identifier id, String value)
    {
        texts.put(id.toTranslationKey(), value);
    }

    public void addItem(Item item, String s)
    {
        texts.put(item.getTranslationKey(), s);
    }

    public void addEffect(StatusEffect effect, String s)
    {
        texts.put(effect.getTranslationKey(), s);
    }

    public void addEffectDesc(StatusEffect effect, String s)
    {
        texts.put(effect.getTranslationKey() + ".desc", s);
    }

    public void addEnchantment(Enchantment enchantment, String s)
    {
        texts.put(enchantment.getTranslationKey(), s);
    }

    public void addEnchantmentDesc(Enchantment enchantment, String s)
    {
        texts.put(enchantment.getTranslationKey() + ".desc", s);
    }

    public void addEntityType(EntityType<?> entityType, String value)
    {
        texts.put(entityType.getTranslationKey(), value);
    }

    public void addAttribute(EntityAttribute entityAttribute, String value)
    {
        texts.put(entityAttribute.getTranslationKey(), value);
    }

    public void addStatType(StatType<?> statType, String value)
    {
        texts.put(statType.getTranslationKey(), value);
    }
}
