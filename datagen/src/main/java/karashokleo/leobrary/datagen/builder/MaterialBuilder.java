package karashokleo.leobrary.datagen.builder;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("unused")
public abstract class MaterialBuilder
{
    protected String name_en;
    protected String name_zh;
    protected Function<Item.Settings, Item.Settings> ingotSettingsFunc = settings -> settings;
    protected Function<Item.Settings, Item.Settings> nuggetSettingsFunc = settings -> settings;
    protected Function<AbstractBlock.Settings, AbstractBlock.Settings> blockSettingFunc = settings -> settings;
    protected Consumer<ItemBuilder<?>> ingotBuilderConsumer = itemBuilder ->
    {
    };
    protected Consumer<ItemBuilder<?>> nuggetBuilderConsumer = itemBuilder ->
    {
    };
    protected Consumer<BlockBuilder<?>> blockBuilderConsumer = blockBuilder ->
    {
    };

    public MaterialBuilder(String name_en, String name_zh)
    {
        this.name_en = name_en;
        this.name_zh = name_zh;
    }

    public MaterialBuilder itemSettings(Function<Item.Settings, Item.Settings> function)
    {
        return this
                .ingotSettings(function)
                .nuggetSettings(function);
    }

    public MaterialBuilder ingotSettings(Function<Item.Settings, Item.Settings> function)
    {
        this.ingotSettingsFunc = function;
        return this;
    }

    public MaterialBuilder nuggetSettings(Function<Item.Settings, Item.Settings> function)
    {
        this.nuggetSettingsFunc = function;
        return this;
    }

    public MaterialBuilder blockSetting(Function<AbstractBlock.Settings, AbstractBlock.Settings> function)
    {
        this.blockSettingFunc = function;
        return this;
    }

    public MaterialBuilder itemBuilder(Consumer<ItemBuilder<?>> consumer)
    {
        return this
                .ingotBuilder(consumer)
                .nuggetBuilder(consumer);
    }

    public MaterialBuilder ingotBuilder(Consumer<ItemBuilder<?>> consumer)
    {
        this.ingotBuilderConsumer = consumer;
        return this;
    }

    public MaterialBuilder nuggetBuilder(Consumer<ItemBuilder<?>> consumer)
    {
        this.nuggetBuilderConsumer = consumer;
        return this;
    }

    public MaterialBuilder blockBuilder(Consumer<BlockBuilder<?>> consumer)
    {
        this.blockBuilderConsumer = consumer;
        return this;
    }

    protected abstract <T extends Item> BiFunction<String, T, ItemBuilder<T>> getItemBuilder();

    protected abstract <T extends Block> BiFunction<String, T, BlockBuilder<T>> getBlockBuilder();

    public MaterialSet register()
    {
        return register(true, true, true);
    }

    public MaterialSet register(boolean registerIngot, boolean registerNugget, boolean registerBlock)
    {
        Item ingot = null;
        Item nugget = null;
        BlockSet blockSet = null;
        if (registerIngot)
        {
            var ingotBuilder = getItemBuilder().apply(
                            name_en + "_ingot",
                            new Item(nuggetSettingsFunc.apply(new FabricItemSettings()))
                    )
                    .addModel()
                    .addEN()
                    .addZH(name_zh + "锭");
            ingotBuilderConsumer.accept(ingotBuilder);
            ingot = ingotBuilder.register();
        }
        if (registerNugget)
        {
            var nuggetBuilder = getItemBuilder().apply(
                            name_en + "_nugget",
                            new Item(nuggetSettingsFunc.apply(new FabricItemSettings()))
                    )
                    .addModel()
                    .addEN()
                    .addZH(name_zh + "粒");
            nuggetBuilderConsumer.accept(nuggetBuilder);
            nugget = nuggetBuilder.register();
        }
        if (registerBlock)
        {
            var blockBuilder = getBlockBuilder().apply(
                            name_en + "_block",
                            new Block(blockSettingFunc.apply(FabricBlockSettings.create()))
                    )
                    .addSimpleItem(nuggetSettingsFunc.apply(new FabricItemSettings()))
                    .addModel()
                    .addEN()
                    .addZH(name_zh + "块");
            blockBuilderConsumer.accept(blockBuilder);
            blockSet = blockBuilder.registerWithItem();
        }
        return new MaterialSet(ingot, nugget, blockSet);
    }
}

