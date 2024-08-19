package karashokleo.leobrary.datagen.builder;

import karashokleo.leobrary.datagen.builder.provider.DefaultLanguageGeneratorProvider;
import karashokleo.leobrary.datagen.builder.provider.TagGeneratorProvider;
import karashokleo.leobrary.datagen.util.StringUtil;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

@SuppressWarnings("unused")
public abstract class StatusEffectBuilder<T extends StatusEffect>
        extends NamedEntryBuilder<T>
        implements DefaultLanguageGeneratorProvider, TagGeneratorProvider<StatusEffect>
{
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
        this.getEnglishGenerator().addEffect(content, en);
        return this;
    }

    public StatusEffectBuilder<T> addENDesc(String en)
    {
        this.getEnglishGenerator().addEffectDesc(content, en);
        return this;
    }

    public StatusEffectBuilder<T> addZH(String zh)
    {
        this.getChineseGenerator().addEffect(content, zh);
        return this;
    }

    public StatusEffectBuilder<T> addZHDesc(String zh)
    {
        this.getChineseGenerator().addEffectDesc(content, zh);
        return this;
    }

    public StatusEffectBuilder<T> addTag(TagKey<StatusEffect> key)
    {
        this.getTagGenerator(RegistryKeys.STATUS_EFFECT).add(key, getId());
        return this;
    }
}