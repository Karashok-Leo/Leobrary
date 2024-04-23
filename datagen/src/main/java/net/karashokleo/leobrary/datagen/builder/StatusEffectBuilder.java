package net.karashokleo.leobrary.datagen.builder;

import net.karashokleo.leobrary.datagen.util.StringUtil;
import net.karashokleo.leobrary.datagen.generator.TagGenerator;
import net.karashokleo.leobrary.datagen.generator.LanguageGenerator;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public abstract class StatusEffectBuilder<T extends StatusEffect> extends NamedEntryBuilder<T>
{
    @Nullable
    public abstract LanguageGenerator getEnglishGenerator();

    @Nullable
    public abstract LanguageGenerator getChineseGenerator();

    @Nullable
    public abstract TagGenerator<StatusEffect> getTagGenerator();

    public StatusEffectBuilder(String name, T content)
    {
        super(name, content);
    }

    public T register()
    {
        return Registry.register(Registries.STATUS_EFFECT, getId(), content);
    }

    public StatusEffectBuilder<T> addEN()
    {
        return addEN(StringUtil.defaultName(name));
    }

    public StatusEffectBuilder<T> addEN(String en)
    {
        if (getEnglishGenerator() == null)
            throw new UnsupportedOperationException();
        getEnglishGenerator().addEffect(content, en);
        return this;
    }

    public StatusEffectBuilder<T> addENDesc(String en)
    {
        if (getEnglishGenerator() == null)
            throw new UnsupportedOperationException();
        getEnglishGenerator().addEffectDesc(content, en);
        return this;
    }

    public StatusEffectBuilder<T> addZH(String zh)
    {
        if (getChineseGenerator() == null)
            throw new UnsupportedOperationException();
        getChineseGenerator().addEffect(content, zh);
        return this;
    }

    public StatusEffectBuilder<T> addZHDesc(String zh)
    {
        if (getChineseGenerator() == null)
            throw new UnsupportedOperationException();
        getChineseGenerator().addEffectDesc(content, zh);
        return this;
    }

    public StatusEffectBuilder<T> addTag(TagKey<StatusEffect> key)
    {
        if (getTagGenerator() == null)
            throw new UnsupportedOperationException();
        getTagGenerator().add(key, content);
        return this;
    }
}