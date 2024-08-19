package karashokleo.leobrary.datagen.generator;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class DynamicRegistryGenerator<T> implements CustomGenerator
{
    private final String name;
    private final RegistryKey<Registry<T>> registryKey;
    private final Map<RegistryKey<T>, List<T>> entries = new HashMap<>();

    public DynamicRegistryGenerator(String name, RegistryKey<Registry<T>> registryKey)
    {
        this.name = name;
        this.registryKey = registryKey;
    }

    public RegistryKey<Registry<T>> getRegistryKey()
    {
        return registryKey;
    }

    public Map<RegistryKey<T>, List<T>> getEntries()
    {
        return entries;
    }

    @SafeVarargs
    public final void add(RegistryKey<T> key, T... values)
    {
        entries.putIfAbsent(key, new ArrayList<>());
        for (T type : values)
            entries.get(key).add(type);
    }

    @Override
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
                getEntries().forEach((key, list) ->
                        list.forEach(entry -> entries.add(key, entry)));
            }
        });
    }

    public void register(RegistryBuilder builder)
    {
        builder.addRegistry(registryKey, registerable ->
                getEntries().forEach((key, list) ->
                        list.forEach(entry -> registerable.register(key, entry))));
    }
}
