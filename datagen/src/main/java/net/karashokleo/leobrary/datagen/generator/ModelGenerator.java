package net.karashokleo.leobrary.datagen.generator;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ModelGenerator
{
    private final List<Consumer<ItemModelGenerator>> items = new ArrayList<>();
    private final List<Consumer<BlockStateModelGenerator>> blocks = new ArrayList<>();

    public void addItem(Consumer<ItemModelGenerator> consumer)
    {
        items.add(consumer);
    }

    public void addItem(Item item)
    {
        addItem(item, Models.GENERATED);
    }

    public void addItem(Item item, Model model)
    {
        addItem(generator -> generator.register(item, model));
    }

    public void addItem(Item item, Model model, String prefix)
    {
        addItem(generator -> registerWithPrefix(generator, item, model, prefix));
    }

    public void addBlock(Consumer<BlockStateModelGenerator> consumer)
    {
        blocks.add(consumer);
    }

    public void generate(FabricDataGenerator.Pack pack)
    {
        pack.addProvider((FabricDataOutput output) -> new FabricModelProvider(output)
        {
            @Override
            public void generateBlockStateModels(BlockStateModelGenerator generator)
            {
                for (var consumer : blocks)
                    consumer.accept(generator);
            }

            @Override
            public void generateItemModels(ItemModelGenerator generator)
            {
                for (var consumer : items)
                    consumer.accept(generator);
            }
        });
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
