package karashokleo.leobrary.datagen.generator;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.server.tag.TagProvider;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class DynamicTagGenerator<T> implements CustomGenerator
{
    private final RegistryKey<Registry<T>> registryKey;
    private final Multimap<TagKey<T>, RegistryKey<T>> entries = ArrayListMultimap.create();
    private final Multimap<TagKey<T>, Identifier> entriesOptional = ArrayListMultimap.create();
    private final Multimap<TagKey<T>, TagKey<T>> tags = ArrayListMultimap.create();
    private final Multimap<TagKey<T>, Identifier> tagsOptional = ArrayListMultimap.create();

    public DynamicTagGenerator(RegistryKey<Registry<T>> registryKey)
    {
        this.registryKey = registryKey;
    }

    public Multimap<TagKey<T>, RegistryKey<T>> getEntries()
    {
        return entries;
    }

    public Multimap<TagKey<T>, Identifier> getEntriesOptional()
    {
        return entriesOptional;
    }

    public Multimap<TagKey<T>, TagKey<T>> getTags()
    {
        return tags;
    }

    public Multimap<TagKey<T>, Identifier> getTagsOptional()
    {
        return tagsOptional;
    }

    @SafeVarargs
    public final void add(TagKey<T> key, RegistryKey<T>... values)
    {
        entries.putAll(key, List.of(values));
    }

    public final void addOptional(TagKey<T> key, Identifier... values)
    {
        entriesOptional.putAll(key, List.of(values));
    }

    @SafeVarargs
    public final void addTag(TagKey<T> key, TagKey<T>... values)
    {
        tags.putAll(key, List.of(values));
    }

    @SafeVarargs
    public final void addOptionalTag(TagKey<T> key, TagKey<T>... values)
    {
        tagsOptional.putAll(key, Stream.of(values).map(TagKey::id).toList());
    }

    @Override
    public void generate(FabricDataGenerator.Pack pack)
    {
        pack.addProvider((output, future) -> new TagProvider<T>(output, registryKey, future)
        {
            @Override
            protected void configure(RegistryWrapper.WrapperLookup arg)
            {
                getEntries().keySet().forEach(tagKey ->
                {
                    var builder = getOrCreateTagBuilder(tagKey);
                    getEntries().get(tagKey).forEach(builder::add);
                });
                getEntriesOptional().keySet().forEach(tagKey ->
                {
                    var builder = getOrCreateTagBuilder(tagKey);
                    getEntriesOptional().get(tagKey).forEach(builder::addOptional);
                });
                getTags().keySet().forEach(tagKey ->
                {
                    var builder = getOrCreateTagBuilder(tagKey);
                    getTags().get(tagKey).forEach(builder::addTag);
                });
                getTagsOptional().keySet().forEach(tagKey ->
                {
                    var builder = getOrCreateTagBuilder(tagKey);
                    getTagsOptional().get(tagKey).forEach(builder::addOptionalTag);
                });
            }
        });
    }
}
