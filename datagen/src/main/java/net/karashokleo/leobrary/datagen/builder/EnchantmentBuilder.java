package net.karashokleo.leobrary.datagen.builder;

import net.karashokleo.leobrary.datagen.util.StringUtil;
import net.karashokleo.leobrary.datagen.generator.TagGenerator;
import net.karashokleo.leobrary.datagen.generator.LanguageGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public abstract class EnchantmentBuilder<T extends Enchantment> extends NamedEntryBuilder<T>
{
    @Nullable
    public abstract LanguageGenerator getEnglishGenerator();

    @Nullable
    public abstract LanguageGenerator getChineseGenerator();

    @Nullable
    public abstract TagGenerator<Enchantment> getTagGenerator();

    public EnchantmentBuilder(String name, T content)
    {
        super(name, content);
    }

    public T register()
    {
        return Registry.register(Registries.ENCHANTMENT, getId(), content);
    }

    public EnchantmentBuilder<T> addEN()
    {
        return addEN(StringUtil.defaultName(name));
    }

    public EnchantmentBuilder<T> addEN(String en)
    {
        if (getEnglishGenerator() == null)
            throw new UnsupportedOperationException();
        getEnglishGenerator().addEnchantment(content, en);
        return this;
    }

    public EnchantmentBuilder<T> addENDesc(String en)
    {
        if (getEnglishGenerator() == null)
            throw new UnsupportedOperationException();
        getEnglishGenerator().addEnchantmentDesc(content, en);
        return this;
    }

    public EnchantmentBuilder<T> addZH(String zh)
    {
        if (getChineseGenerator() == null)
            throw new UnsupportedOperationException();
        getChineseGenerator().addEnchantment(content, zh);
        return this;
    }

    public EnchantmentBuilder<T> addZHDesc(String zh)
    {
        if (getChineseGenerator() == null)
            throw new UnsupportedOperationException();
        getChineseGenerator().addEnchantmentDesc(content, zh);
        return this;
    }

    public EnchantmentBuilder<T> addTag(TagKey<Enchantment> key)
    {
        if (getTagGenerator() == null)
            throw new UnsupportedOperationException();
        getTagGenerator().add(key, content);
        return this;
    }
}
