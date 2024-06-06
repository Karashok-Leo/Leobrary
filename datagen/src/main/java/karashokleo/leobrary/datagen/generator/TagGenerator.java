package karashokleo.leobrary.datagen.generator;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.util.*;

@SuppressWarnings("unused")
public class TagGenerator<T> implements CustomGenerator
{
    private final RegistryKey<Registry<T>> registryKey;
    private final Map<TagKey<T>, List<T>> entries = new HashMap<>();
    private final Map<TagKey<T>, List<TagKey<T>>> tags = new HashMap<>();

    public TagGenerator(RegistryKey<Registry<T>> registryKey)
    {
        this.registryKey = registryKey;
    }

    public Map<TagKey<T>, List<T>> getEntries()
    {
        return entries;
    }

    public Map<TagKey<T>, List<TagKey<T>>> getTags()
    {
        return tags;
    }

    @SafeVarargs
    public final void add(TagKey<T> key, T... values)
    {
        entries.putIfAbsent(key, new ArrayList<>());
        for (T type : values)
            entries.get(key).add(type);
    }

    public void add(TagKey<T> key, TagKey<T> value)
    {
        tags.putIfAbsent(key, new ArrayList<>());
        tags.get(key).add(value);
    }

    @Override
    public void generate(FabricDataGenerator.Pack pack)
    {
        pack.addProvider((output, future) -> new FabricTagProvider<T>(output, registryKey, future)
        {
            @Override
            protected void configure(RegistryWrapper.WrapperLookup arg)
            {
                for (var entry : getEntries().entrySet())
                {
                    var builder = getOrCreateTagBuilder(entry.getKey());
                    for (var value : entry.getValue())
                        builder.add(value);
                }
                for (var entry : getTags().entrySet())
                {
                    var builder = getOrCreateTagBuilder(entry.getKey());
                    for (var value : entry.getValue())
                        builder.addOptionalTag(value);
                }
            }
        });
    }
}
