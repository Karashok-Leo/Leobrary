package karashokleo.leobrary.datagen.generator;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public class ModelGenerator implements CustomGenerator
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

    public void addItemWithTexturePath(Item item, Model model, String path)
    {
        addItem(generator -> registerWithTexturePath(generator, item, model, path));
    }

    public void addItemWithTexturePrefix(Item item, Model model, String prefix)
    {
        addItem(generator -> registerWithTexturePrefix(generator, item, model, prefix));
    }

    public void addBlock(Consumer<BlockStateModelGenerator> consumer)
    {
        blocks.add(consumer);
    }

    public void addBlock(Block block)
    {
        addBlock(generator -> generator.registerSimpleCubeAll(block));
    }

    @Override
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

    public static void registerWithTexturePrefix(ItemModelGenerator generator, Item item, Model model, String prefix)
    {
        registerWithTexture(generator, item, model, id -> id.withPrefixedPath("item/" + prefix));
    }

    private static void registerWithTexturePath(ItemModelGenerator generator, Item item, Model model, String texturePath)
    {
        registerWithTexture(generator, item, model, id -> id.withPath("item/" + texturePath));
    }

    private static void registerWithTexture(ItemModelGenerator generator, Item item, Model model, Identifier textureId)
    {
        registerWithTexture(generator, item, model, id -> textureId);
    }

    private static void registerWithTexture(ItemModelGenerator generator, Item item, Model model, UnaryOperator<Identifier> textureIdOperator)
    {
        Identifier id = Registries.ITEM.getId(item);
        model.upload(
                id.withPrefixedPath("item/"),
                TextureMap.layer0(textureIdOperator.apply(id)),
                generator.writer
        );
    }
}
