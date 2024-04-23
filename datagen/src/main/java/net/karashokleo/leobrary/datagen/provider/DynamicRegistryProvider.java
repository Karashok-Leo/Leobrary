package net.karashokleo.leobrary.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.karashokleo.leobrary.datagen.generator.DynamicRegistryGenerator;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Deprecated
public class DynamicRegistryProvider extends FabricDynamicRegistryProvider
{
    private final DynamicRegistryGenerator consumers;

    public DynamicRegistryProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, DynamicRegistryGenerator consumers)
    {
        super(output, registriesFuture);
        this.consumers = consumers;
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, FabricDynamicRegistryProvider.Entries entries)
    {
        for (Consumer<Entries> consumer : consumers.getConsumers())
            consumer.accept(entries);
    }

    @Override
    public String getName()
    {
        return "Leobrary Dynamic Registry";
    }
}
