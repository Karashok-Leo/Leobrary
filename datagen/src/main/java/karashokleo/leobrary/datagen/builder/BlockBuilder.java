package karashokleo.leobrary.datagen.builder;

import karashokleo.leobrary.datagen.builder.provider.BlockLootGeneratorProvider;
import karashokleo.leobrary.datagen.builder.provider.DefaultLanguageGeneratorProvider;
import karashokleo.leobrary.datagen.builder.provider.ModelGeneratorProvider;
import karashokleo.leobrary.datagen.builder.provider.TagGeneratorProvider;
import karashokleo.leobrary.datagen.generator.BlockLootGenerator;
import karashokleo.leobrary.datagen.generator.TagGenerator;
import karashokleo.leobrary.datagen.util.StringUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public abstract class BlockBuilder<T extends Block>
        extends NamedEntryBuilder<T>
        implements DefaultLanguageGeneratorProvider, TagGeneratorProvider, ModelGeneratorProvider, BlockLootGeneratorProvider
{
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
        this.getModelGenerator().addBlock(content);
        return this;
    }

    public BlockBuilder<T> addLoot()
    {
        return addLoot(false);
    }

    public BlockBuilder<T> addLoot(boolean requireSilkTouch)
    {
        BlockLootGenerator blockLootGenerator = this.getBlockLootGenerator();
        if (requireSilkTouch)
            blockLootGenerator.addLoot(generator -> generator.addDropWithSilkTouch(content));
        else
            blockLootGenerator.addLoot(generator -> generator.addDrop(content));
        return this;
    }

    public BlockBuilder<T> addEN()
    {
        return addEN(StringUtil.defaultName(name));
    }

    public BlockBuilder<T> addEN(String en)
    {
        this.getEnglishGenerator().addBlock(content, en);
        return this;
    }

    public BlockBuilder<T> addZH(String zh)
    {
        this.getChineseGenerator().addBlock(content, zh);
        return this;
    }

    public BlockBuilder<T> addTag(TagKey<Block> key)
    {
        this.getTagGenerator(RegistryKeys.BLOCK).getOrCreateContainer(key).add(getId());
        return this;
    }

    @SafeVarargs
    public final BlockBuilder<T> addTag(TagKey<Block>... keys)
    {
        TagGenerator<Block> tagGenerator = this.getTagGenerator(RegistryKeys.BLOCK);
        for (TagKey<Block> key : keys)
            tagGenerator.getOrCreateContainer(key).add(getId());
        return this;
    }
}
