package net.karashokleo.leobrary.datagen.provider.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.karashokleo.leobrary.datagen.util.TagContainer;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider
{
    public static final TagContainer<Item> CONTAINER = new TagContainer<>();

    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture)
    {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg)
    {
        for (var entry : CONTAINER.getEntries().entrySet())
        {
            var builder = getOrCreateTagBuilder(entry.getKey());
            for (var value : entry.getValue())
                builder.add(value);
        }
        for (var entry : CONTAINER.getTags().entrySet())
        {
            var builder = getOrCreateTagBuilder(entry.getKey());
            for (var value : entry.getValue())
                builder.forceAddTag(value);
        }
    }
}
