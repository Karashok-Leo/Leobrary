package karashokleo.leobrary.datagen.builder;

import karashokleo.leobrary.datagen.builder.provider.DefaultLanguageGeneratorProvider;
import karashokleo.leobrary.datagen.builder.provider.ModelGeneratorProvider;
import karashokleo.leobrary.datagen.builder.provider.TagGeneratorProvider;
import karashokleo.leobrary.datagen.generator.TagGenerator;
import karashokleo.leobrary.datagen.util.StringUtil;
import net.minecraft.data.client.Model;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public abstract class ItemBuilder<T extends Item>
        extends NamedEntryBuilder<T>
        implements DefaultLanguageGeneratorProvider, TagGeneratorProvider, ModelGeneratorProvider
{
    @Nullable
    protected ItemGroupBuilder group;

    public ItemBuilder(String name, T content, @Nullable ItemGroupBuilder group)
    {
        super(name, content);
        this.group = group;
    }

    public ItemBuilder(String name, T content)
    {
        this(name, content, null);
    }

    public T register()
    {
        addToTab();
        return Registry.register(Registries.ITEM, getId(), content);
    }

    public ItemBuilder<T> setTab(@Nullable ItemGroupBuilder group)
    {
        this.group = group;
        return this;
    }

    protected void addToTab()
    {
        if (group != null)
            group.add(content);
    }

    public ItemBuilder<T> addModel()
    {
        this.getModelGenerator().addItem(content);
        return this;
    }

    public ItemBuilder<T> addModel(Model model)
    {
        this.getModelGenerator().addItem(content, model);
        return this;
    }

    public ItemBuilder<T> addEN()
    {
        return addEN(StringUtil.defaultName(name));
    }

    public ItemBuilder<T> addEN(String en)
    {
        this.getEnglishGenerator().addItem(content, en);
        return this;
    }

    public ItemBuilder<T> addZH(String zh)
    {
        this.getChineseGenerator().addItem(content, zh);
        return this;
    }

    public ItemBuilder<T> addTag(TagKey<Item> key)
    {
        this.getTagGenerator(RegistryKeys.ITEM).getOrCreateContainer(key).add(getId());
        return this;
    }

    @SafeVarargs
    public final ItemBuilder<T> addTag(TagKey<Item>... keys)
    {
        TagGenerator<Item> tagGenerator = getTagGenerator(RegistryKeys.ITEM);
        for (TagKey<Item> key : keys)
            tagGenerator.getOrCreateContainer(key).add(getId());
        return this;
    }
}
