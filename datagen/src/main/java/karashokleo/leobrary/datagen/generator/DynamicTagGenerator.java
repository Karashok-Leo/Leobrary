package karashokleo.leobrary.datagen.generator;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.server.tag.TagProvider;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class DynamicTagGenerator<T> implements CustomGenerator
{
    private final RegistryKey<Registry<T>> registryKey;
    private final Map<TagKey<T>, List<RegistryKey<T>>> entries = new HashMap<>();
    private final Map<TagKey<T>, List<TagKey<T>>> tags = new HashMap<>();

    public DynamicTagGenerator(RegistryKey<Registry<T>> registryKey)
    {
        this.registryKey = registryKey;
    }

    public Map<TagKey<T>, List<RegistryKey<T>>> getEntries()
    {
        return entries;
    }

    public Map<TagKey<T>, List<TagKey<T>>> getTags()
    {
        return tags;
    }

    @SafeVarargs
    public final void add(TagKey<T> key, RegistryKey<T>... values)
    {
        entries.putIfAbsent(key, new ArrayList<>());
        for (RegistryKey<T> value : values)
            entries.get(key).add(value);
    }

    public void add(TagKey<T> key, TagKey<T> value)
    {
        tags.putIfAbsent(key, new ArrayList<>());
        tags.get(key).add(value);
    }

    @Override
    public void generate(FabricDataGenerator.Pack pack)
    {
        pack.addProvider((output, future) -> new TagProvider<T>(output, registryKey, future)
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
                        builder.addTag(value);
                }
            }
        });
    }
}
