package net.karashokleo.leobrary.datagen.entry;

import net.karashokleo.leobrary.datagen.provider.lang.ChineseLanguageProvider;
import net.karashokleo.leobrary.datagen.provider.lang.EnglishLanguageProvider;
import net.karashokleo.leobrary.datagen.provider.tag.EnchantmentTagProvider;
import net.karashokleo.leobrary.datagen.util.StringUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;

public class EnchantmentEntry<T extends Enchantment> extends NamedEntry<T>
{
    private EnchantmentEntry(String name, T content)
    {
        super(name, content);
    }

    public static <T extends Enchantment> EnchantmentEntry<T> of(String name, T enchantment)
    {
        return new EnchantmentEntry<>(name, enchantment);
    }

    public T register()
    {
        return Registry.register(Registries.ENCHANTMENT, getId(), content);
    }

    public EnchantmentEntry<T> addEN()
    {
        return addEN(StringUtil.defaultName(name));
    }

    public EnchantmentEntry<T> addEN(String en)
    {
        EnglishLanguageProvider.CONTAINER.addEnchantment(content, en);
        return this;
    }

    public EnchantmentEntry<T> addENDesc(String en)
    {
        EnglishLanguageProvider.CONTAINER.addEnchantmentDesc(content, en);
        return this;
    }

    public EnchantmentEntry<T> addZH(String zh)
    {
        ChineseLanguageProvider.CONTAINER.addEnchantment(content, zh);
        return this;
    }

    public EnchantmentEntry<T> addZHDesc(String zh)
    {
        ChineseLanguageProvider.CONTAINER.addEnchantmentDesc(content, zh);
        return this;
    }

    public EnchantmentEntry<T> addTag(TagKey<Enchantment> key)
    {
        EnchantmentTagProvider.CONTAINER.add(key, content);
        return this;
    }
}
