package net.karashokleo.leobrary.datagen.entry;

import net.karashokleo.leobrary.datagen.provider.ModelProvider;
import net.karashokleo.leobrary.datagen.provider.lang.ChineseLanguageProvider;
import net.karashokleo.leobrary.datagen.provider.lang.EnglishLanguageProvider;
import net.karashokleo.leobrary.datagen.provider.tag.ItemTagProvider;
import net.karashokleo.leobrary.datagen.util.ItemGroupContainer;
import net.karashokleo.leobrary.datagen.util.StringUtil;
import net.minecraft.data.client.Model;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

public class ItemEntry<T extends Item> extends NamedEntry<T>
{
    @Nullable
    protected RegistryKey<ItemGroup> group = null;

    public ItemEntry(String name, T content)
    {
        super(name, content);
    }

    public static <T extends Item> ItemEntry<T> of(String name, T item)
    {
        return new ItemEntry<>(name, item);
    }

    public T register()
    {
        if (group != null)
            ItemGroupContainer.add(group, content);
        return Registry.register(Registries.ITEM, getId(), content);
    }

    public ItemEntry<T> setTab(RegistryKey<ItemGroup> group)
    {
        this.group = group;
        return this;
    }

    public ItemEntry<T> addModel()
    {
        ModelProvider.addItem(content);
        return this;
    }

    public ItemEntry<T> addModel(Model model)
    {
        ModelProvider.addItem(content, model);
        return this;
    }

    public ItemEntry<T> addEN()
    {
        return addEN(StringUtil.defaultName(name));
    }

    public ItemEntry<T> addEN(String en)
    {
        EnglishLanguageProvider.CONTAINER.addItem(content, en);
        return this;
    }

    public ItemEntry<T> addZH(String zh)
    {
        ChineseLanguageProvider.CONTAINER.addItem(content, zh);
        return this;
    }

    public ItemEntry<T> addTag(TagKey<Item> key)
    {
        ItemTagProvider.CONTAINER.add(key, content);
        return this;
    }

    @SafeVarargs
    public final ItemEntry<T> addTag(TagKey<Item>... keys)
    {
        for (TagKey<Item> key : keys)
            ItemTagProvider.CONTAINER.add(key, content);
        return this;
    }
}
