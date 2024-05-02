package net.karashokleo.leobrary.datagen.builder;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.karashokleo.leobrary.datagen.generator.LanguageGenerator;
import net.karashokleo.leobrary.datagen.generator.ModelGenerator;
import net.karashokleo.leobrary.datagen.generator.TagGenerator;
import net.karashokleo.leobrary.datagen.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public abstract class BlockBuilder<T extends Block> extends NamedEntryBuilder<T>
{
    @Nullable
    public abstract LanguageGenerator getEnglishGenerator();

    @Nullable
    public abstract LanguageGenerator getChineseGenerator();

    @Nullable
    public abstract TagGenerator<Block> getTagGenerator();

    @Nullable
    public abstract ModelGenerator getModelGenerator();

    @Nullable
    private BlockItem item;

    public BlockBuilder(String name, T content)
    {
        super(name, content);
    }

    @Nullable
    public BlockItem getItem()
    {
        return item;
    }

    public T register()
    {
        if (getItem() != null)
            Registry.register(Registries.ITEM, getId(), getItem());
        return Registry.register(Registries.BLOCK, getId(), content);
    }

    public BlockBuilder<T> addSimpleItem()
    {
        return addSimpleItem(new FabricItemSettings());
    }

    public BlockBuilder<T> addSimpleItem(Item.Settings itemSetting)
    {
        this.item = new BlockItem(content, itemSetting);
        if (getModelGenerator() == null)
            throw new UnsupportedOperationException();
        getModelGenerator().addBlock(content);
        return this;
    }

    public BlockBuilder<T> addModel()
    {
        if (getModelGenerator() == null)
            throw new UnsupportedOperationException();
        getModelGenerator().addBlock(content);
        return this;
    }

    public BlockBuilder<T> addEN()
    {
        return addEN(StringUtil.defaultName(name));
    }

    public BlockBuilder<T> addEN(String en)
    {
        if (getEnglishGenerator() == null)
            throw new UnsupportedOperationException();
        if (getItem() != null)
            getEnglishGenerator().addItem(getItem(), en);
        getEnglishGenerator().addBlock(content, en);
        return this;
    }

    public BlockBuilder<T> addZH(String zh)
    {
        if (getChineseGenerator() == null)
            throw new UnsupportedOperationException();
        if (getItem() != null)
            getChineseGenerator().addItem(getItem(), zh);
        getChineseGenerator().addBlock(content, zh);
        return this;
    }

    public BlockBuilder<T> addTag(TagKey<Block> key)
    {
        if (getTagGenerator() == null)
            throw new UnsupportedOperationException();
        getTagGenerator().add(key, content);
        return this;
    }

    @SafeVarargs
    public final BlockBuilder<T> addTag(TagKey<Block>... keys)
    {
        if (getTagGenerator() == null)
            throw new UnsupportedOperationException();
        for (TagKey<Block> key : keys)
            getTagGenerator().add(key, content);
        return this;
    }
}
