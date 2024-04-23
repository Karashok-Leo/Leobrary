package net.karashokleo.leobrary.datagen.generator;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.stat.StatType;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class LanguageGenerator
{
    private final String languageCode;
    private final Map<Supplier<String>, String> texts = new HashMap<>();

    public LanguageGenerator(String languageCode)
    {
        this.languageCode = languageCode;
    }

    public Map<Supplier<String>, String> getTexts()
    {
        return texts;
    }

    public void addText(String key, String value)
    {
        texts.put(() -> key, value);
    }

    public void addText(Identifier id, String value)
    {
        texts.put(id::toTranslationKey, value);
    }

    public void addItem(Item item, String s)
    {
        texts.put(item::getTranslationKey, s);
    }

    public void addEffect(StatusEffect effect, String s)
    {
        texts.put(effect::getTranslationKey, s);
    }

    public void addEffectDesc(StatusEffect effect, String s)
    {
        texts.put(() -> effect.getTranslationKey() + ".desc", s);
    }

    public void addEnchantment(Enchantment enchantment, String s)
    {
        texts.put(enchantment::getTranslationKey, s);
    }

    public void addEnchantmentDesc(Enchantment enchantment, String s)
    {
        texts.put(() -> enchantment.getTranslationKey() + ".desc", s);
    }

    public void addEntityType(EntityType<?> entityType, String value)
    {
        texts.put(entityType::getTranslationKey, value);
    }

    public void addAttribute(EntityAttribute entityAttribute, String value)
    {
        texts.put(entityAttribute::getTranslationKey, value);
    }

    public void addStatType(StatType<?> statType, String value)
    {
        texts.put(statType::getTranslationKey, value);
    }

    public void generate(FabricDataGenerator.Pack pack)
    {
        pack.addProvider((FabricDataOutput output) -> new FabricLanguageProvider(output, languageCode)
        {
            @Override
            public void generateTranslations(TranslationBuilder translationBuilder)
            {
                getTexts().forEach((sup, s) -> translationBuilder.add(sup.get(), s));
            }
        });
    }
}
