package karashokleo.leobrary.datagen.builder;

import karashokleo.leobrary.datagen.builder.provider.DefaultLanguageGeneratorProvider;
import karashokleo.leobrary.datagen.builder.provider.TagGeneratorProvider;
import karashokleo.leobrary.datagen.util.StringUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

@SuppressWarnings("unused")
public abstract class EnchantmentBuilder<T extends Enchantment>
        extends NamedEntryBuilder<T>
        implements DefaultLanguageGeneratorProvider, TagGeneratorProvider<Enchantment>
{
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
        this.getEnglishGenerator().addEnchantment(content, en);
        return this;
    }

    public EnchantmentBuilder<T> addENDesc(String en)
    {
        this.getEnglishGenerator().addEnchantmentDesc(content, en);
        return this;
    }

    public EnchantmentBuilder<T> addZH(String zh)
    {
        this.getChineseGenerator().addEnchantment(content, zh);
        return this;
    }

    public EnchantmentBuilder<T> addZHDesc(String zh)
    {
        this.getChineseGenerator().addEnchantmentDesc(content, zh);
        return this;
    }

    public EnchantmentBuilder<T> addTag(TagKey<Enchantment> key)
    {
        this.getTagGenerator(RegistryKeys.ENCHANTMENT).add(key, getId());
        return this;
    }
}
