package net.karashokleo.leobrary.datagen.builder;

import net.karashokleo.leobrary.datagen.generator.ModelGenerator;
import net.karashokleo.leobrary.datagen.generator.TagGenerator;
import net.karashokleo.leobrary.datagen.generator.LanguageGenerator;
import net.karashokleo.leobrary.datagen.util.*;
import net.minecraft.data.client.Model;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public abstract class ItemBuilder<T extends Item> extends NamedEntryBuilder<T>
{
    @Nullable
    public abstract LanguageGenerator getEnglishGenerator();

    @Nullable
    public abstract LanguageGenerator getChineseGenerator();

    @Nullable
    public abstract TagGenerator<Item> getTagGenerator();

    @Nullable
    public abstract ModelGenerator getModelGenerator();

    @Nullable
    private ItemGroupBuilder group;

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
        if (getModelGenerator() == null)
            throw new UnsupportedOperationException();
        getModelGenerator().addItem(content);
        return this;
    }

    public ItemBuilder<T> addModel(Model model)
    {
        if (getModelGenerator() == null)
            throw new UnsupportedOperationException();
        getModelGenerator().addItem(content, model);
        return this;
    }

    public ItemBuilder<T> addEN()
    {
        return addEN(StringUtil.defaultName(name));
    }

    public ItemBuilder<T> addEN(String en)
    {
        if (getEnglishGenerator() == null)
            throw new UnsupportedOperationException();
        getEnglishGenerator().addItem(content, en);
        return this;
    }

    public ItemBuilder<T> addZH(String zh)
    {
        if (getChineseGenerator() == null)
            throw new UnsupportedOperationException();
        getChineseGenerator().addItem(content, zh);
        return this;
    }

    public ItemBuilder<T> addTag(TagKey<Item> key)
    {
        if (getTagGenerator() == null)
            throw new UnsupportedOperationException();
        getTagGenerator().add(key, content);
        return this;
    }

    @SafeVarargs
    public final ItemBuilder<T> addTag(TagKey<Item>... keys)
    {
        if (getTagGenerator() == null)
            throw new UnsupportedOperationException();
        for (TagKey<Item> key : keys)
            getTagGenerator().add(key, content);
        return this;
    }
}
