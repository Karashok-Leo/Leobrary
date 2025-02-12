package karashokleo.leobrary.datagen.builder;

import karashokleo.leobrary.datagen.object.BlockSet;
import karashokleo.leobrary.datagen.object.MaterialSet;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public abstract class MaterialBuilder
{
    protected String name_en;
    protected String name_zh;

    @Nullable
    protected Item nugget;
    @Nullable
    protected Item ingot;
    @Nullable
    protected BlockSet blockSet;

    @Nullable
    protected Consumer<ItemBuilder<?>> ingotBuilderConsumer;
    @Nullable
    protected Consumer<ItemBuilder<?>> nuggetBuilderConsumer;
    @Nullable
    protected Consumer<BlockBuilder<?>> blockBuilderConsumer;

    public MaterialBuilder(String name_en, String name_zh)
    {
        this.name_en = name_en;
        this.name_zh = name_zh;
    }

    public MaterialBuilder nugget()
    {
        return nugget(new FabricItemSettings());
    }

    public MaterialBuilder nugget(Item.Settings settings)
    {
        return nugget(new Item(settings));
    }

    public MaterialBuilder nugget(Item nugget)
    {
        this.nugget = nugget;
        return this;
    }

    public MaterialBuilder ingot()
    {
        return ingot(new FabricItemSettings());
    }

    public MaterialBuilder ingot(Item.Settings settings)
    {
        return ingot(new Item(settings));
    }

    public MaterialBuilder ingot(Item ingot)
    {
        this.ingot = ingot;
        return this;
    }

    public MaterialBuilder ingotAndNugget()
    {
        return ingot().nugget();
    }

    public MaterialBuilder ingotAndNugget(Item.Settings settings)
    {
        return ingot(settings).nugget(settings);
    }

    public MaterialBuilder block(AbstractBlock.Settings blockSetting)
    {
        return block(blockSetting, new FabricItemSettings());
    }

    public MaterialBuilder block(AbstractBlock.Settings blockSetting, Item.Settings itemSetting)
    {
        Block block = new Block(blockSetting);
        BlockItem item = new BlockItem(block, itemSetting);
        return block(block, item);
    }

    public MaterialBuilder block(Block block)
    {
        return block(block, new BlockItem(block, new FabricItemSettings()));
    }

    public MaterialBuilder block(Block block, BlockItem item)
    {
        this.blockSet = new BlockSet(block, item);
        return this;
    }

    public MaterialBuilder buildNugget(Consumer<ItemBuilder<?>> consumer)
    {
        this.nuggetBuilderConsumer = consumer;
        return this;
    }

    public MaterialBuilder buildIngot(Consumer<ItemBuilder<?>> consumer)
    {
        this.ingotBuilderConsumer = consumer;
        return this;
    }

    public MaterialBuilder buildIngotAndNugget(Consumer<ItemBuilder<?>> consumer)
    {
        return this
                .buildIngot(consumer)
                .buildNugget(consumer);
    }

    public MaterialBuilder buildBlock(Consumer<BlockBuilder<?>> consumer)
    {
        this.blockBuilderConsumer = consumer;
        return this;
    }

    protected abstract <T extends Item> BiFunction<String, T, ItemBuilder<T>> getItemBuilder();

    protected abstract <T extends Block> BiFunction<String, T, BlockBuilder<T>> getBlockBuilder();

    public MaterialSet register()
    {
        if (this.nugget != null)
        {
            var nuggetBuilder = getItemBuilder().apply(
                            this.name_en + "_nugget",
                            this.nugget
                    )
                    .addModel()
                    .addEN()
                    .addZH(this.name_zh + "粒");
            if (this.nuggetBuilderConsumer != null)
                this.nuggetBuilderConsumer.accept(nuggetBuilder);
            this.nugget = nuggetBuilder.register();
        }
        if (this.ingot != null)
        {
            var ingotBuilder = getItemBuilder().apply(
                            this.name_en + "_ingot",
                            this.ingot
                    )
                    .addModel()
                    .addEN()
                    .addZH(this.name_zh + "锭");
            if (this.ingotBuilderConsumer != null)
                this.ingotBuilderConsumer.accept(ingotBuilder);
            this.ingot = ingotBuilder.register();
        }
        if (this.blockSet != null)
        {
            var blockBuilder = getBlockBuilder().apply(
                            this.name_en + "_block",
                            blockSet.block()
                    )
                    .addItem(blockSet.item())
                    .addModel()
                    .addEN()
                    .addZH(this.name_zh + "块");
            if (this.blockBuilderConsumer != null)
                this.blockBuilderConsumer.accept(blockBuilder);
            this.blockSet = blockBuilder.registerWithItem();
        }
        return new MaterialSet(this.ingot, this.nugget, this.blockSet);
    }
}

