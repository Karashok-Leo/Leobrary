package net.karashokleo.leobrary.datagen.provider.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.karashokleo.leobrary.datagen.generator.TagGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

@Deprecated
public class EntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider
{
    private final TagGenerator<EntityType<?>> container;

    public EntityTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, TagGenerator<EntityType<?>> container)
    {
        super(output, completableFuture);
        this.container = container;
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg)
    {
        for (var entry : container.getEntries().entrySet())
        {
            var builder = getOrCreateTagBuilder(entry.getKey());
            for (var value : entry.getValue())
                builder.add(value);
        }
        for (var entry : container.getTags().entrySet())
        {
            var builder = getOrCreateTagBuilder(entry.getKey());
            for (var value : entry.getValue())
                builder.forceAddTag(value);
        }
    }
}
