package karashokleo.leobrary.datagen.builder;

import karashokleo.leobrary.datagen.generator.BlockLootGenerator;
import karashokleo.leobrary.datagen.generator.LanguageGenerator;
import karashokleo.leobrary.datagen.generator.ModelGenerator;
import karashokleo.leobrary.datagen.generator.TagGenerator;
import karashokleo.leobrary.datagen.util.StringUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
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
    public abstract BlockLootGenerator getLootGenerator();

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
        return Registry.register(Registries.BLOCK, getId(), content);
    }

    public BlockSet registerWithItem()
    {
        if (item == null)
            throw new UnsupportedOperationException(String.format("Item {%s} not yet registered!", getId()));
        Registry.register(Registries.ITEM, getId(), item);
        Registry.register(Registries.BLOCK, getId(), content);
        return new BlockSet(content, item);
    }

    public BlockBuilder<T> addItem(BlockItem item)
    {
        this.item = item;
        return this;
    }

    public BlockBuilder<T> addSimpleItem()
    {
        return addSimpleItem(new FabricItemSettings());
    }

    public BlockBuilder<T> addSimpleItem(Item.Settings itemSetting)
    {
        return addItem(new BlockItem(content, itemSetting));
    }

    public BlockBuilder<T> addModel()
    {
        if (getModelGenerator() == null)
            throw new UnsupportedOperationException();
        getModelGenerator().addBlock(content);
        return this;
    }

    public BlockBuilder<T> addLoot()
    {
        return addLoot(false);
    }

    public BlockBuilder<T> addLoot(boolean requireSilkTouch)
    {
        if (getLootGenerator() == null)
            throw new UnsupportedOperationException();
        if (requireSilkTouch)
            getLootGenerator().addLoot(generator -> generator.addDropWithSilkTouch(content));
        else
            getLootGenerator().addLoot(generator -> generator.addDrop(content));
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
        getEnglishGenerator().addBlock(content, en);
        return this;
    }

    public BlockBuilder<T> addZH(String zh)
    {
        if (getChineseGenerator() == null)
            throw new UnsupportedOperationException();
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
