package net.karashokleo.leobrary.datagen.entry;

import net.karashokleo.leobrary.datagen.provider.lang.ChineseLanguageProvider;
import net.karashokleo.leobrary.datagen.provider.lang.EnglishLanguageProvider;
import net.karashokleo.leobrary.datagen.provider.tag.StatusEffectTagProvider;
import net.karashokleo.leobrary.datagen.util.StringUtil;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;

public class StatusEffectEntry<T extends StatusEffect> extends NamedEntry<T>
{
    public StatusEffectEntry(String name, T content)
    {
        super(name, content);
    }

    public static <T extends StatusEffect> StatusEffectEntry<T> of(String name, T effect)
    {
        return new StatusEffectEntry<>(name, effect);
    }

    public T register()
    {
        return Registry.register(Registries.STATUS_EFFECT, getId(), content);
    }

    public StatusEffectEntry<T> addEN()
    {
        return addEN(StringUtil.defaultName(name));
    }

    public StatusEffectEntry<T> addEN(String en)
    {
        EnglishLanguageProvider.CONTAINER.addEffect(content, en);
        return this;
    }

    public StatusEffectEntry<T> addENDesc(String en)
    {
        EnglishLanguageProvider.CONTAINER.addEffectDesc(content, en);
        return this;
    }

    public StatusEffectEntry<T> addZH(String zh)
    {
        ChineseLanguageProvider.CONTAINER.addEffect(content, zh);
        return this;
    }

    public StatusEffectEntry<T> addZHDesc(String zh)
    {
        ChineseLanguageProvider.CONTAINER.addEffectDesc(content, zh);
        return this;
    }

    public StatusEffectEntry<T> addTag(TagKey<StatusEffect> key)
    {
        StatusEffectTagProvider.CONTAINER.add(key, content);
        return this;
    }
}