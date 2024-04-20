package net.karashokleo.leobrary.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModelProvider extends FabricModelProvider
{
    private static final List<Consumer<ItemModelGenerator>> items = new ArrayList<>();
    private static final List<Consumer<BlockStateModelGenerator>> blocks = new ArrayList<>();

    public static void addItem(Consumer<ItemModelGenerator> consumer)
    {
        items.add(consumer);
    }

    public static void addItem(Item item)
    {
        addItem(item, Models.GENERATED);
    }

    public static void addItem(Item item, Model model)
    {
        addItem(generator -> generator.register(item, model));
    }

    public static void addItem(Item item, Model model, String prefix)
    {
        addItem(generator -> registerWithPrefix(generator, item, model, prefix));
    }

    public static void addBlock(Consumer<BlockStateModelGenerator> consumer)
    {
        blocks.add(consumer);
    }

    public ModelProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator)
    {
        for (Consumer<ItemModelGenerator> consumer : items)
            consumer.accept(generator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator)
    {
        for (Consumer<BlockStateModelGenerator> consumer : blocks)
            consumer.accept(generator);
    }

    public static void registerWithPrefix(ItemModelGenerator generator, Item item, Model model, String prefix)
    {
        Identifier id = Registries.ITEM.getId(item);
        model.upload(
                id.withPrefixedPath("item/"),
                TextureMap.layer0(id.withPrefixedPath("item/" + prefix)),
                generator.writer
        );
    }
}
