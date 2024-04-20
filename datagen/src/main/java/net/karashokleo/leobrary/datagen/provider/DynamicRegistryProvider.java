package net.karashokleo.leobrary.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class DynamicRegistryProvider extends FabricDynamicRegistryProvider
{
    private static final List<Consumer<FabricDynamicRegistryProvider.Entries>> consumers = new ArrayList<>();

    public static void add(Consumer<FabricDynamicRegistryProvider.Entries> consumer)
    {
        consumers.add(consumer);
    }

    public DynamicRegistryProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, FabricDynamicRegistryProvider.Entries entries)
    {
        for (Consumer<Entries> consumer : consumers)
            consumer.accept(entries);
    }

    @Override
    public String getName()
    {
        return "Leobrary Dynamic Registry";
    }
}
