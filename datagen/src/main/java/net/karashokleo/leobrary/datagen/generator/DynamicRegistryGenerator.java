package net.karashokleo.leobrary.datagen.generator;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class DynamicRegistryGenerator
{
    private final String name;
    private final List<Consumer<FabricDynamicRegistryProvider.Entries>> consumers = new ArrayList<>();

    public DynamicRegistryGenerator(String name)
    {
        this.name = name;
    }

    public List<Consumer<FabricDynamicRegistryProvider.Entries>> getConsumers()
    {
        return consumers;
    }

    public void add(Consumer<FabricDynamicRegistryProvider.Entries> consumer)
    {
        consumers.add(consumer);
    }

    public void generate(FabricDataGenerator.Pack pack)
    {
        pack.addProvider((output, future) -> new FabricDynamicRegistryProvider(output, future)
        {
            @Override
            public String getName()
            {
                return name;
            }

            @Override
            protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries)
            {
                for (Consumer<Entries> consumer : getConsumers())
                    consumer.accept(entries);
            }
        });
    }
}
