package karashokleo.leobrary.datagen.generator;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class TagGenerator<T> implements CustomGenerator
{
    private final RegistryKey<Registry<T>> registryKey;

    private final Multimap<TagKey<T>, RegistryKey<T>> entries = ArrayListMultimap.create();
    private final Multimap<TagKey<T>, Identifier> entriesOptional = ArrayListMultimap.create();
    private final Multimap<TagKey<T>, TagKey<T>> tags = ArrayListMultimap.create();
    private final Multimap<TagKey<T>, Identifier> tagsOptional = ArrayListMultimap.create();

    public TagGenerator(RegistryKey<Registry<T>> registryKey)
    {
        this.registryKey = registryKey;
    }

    public RegistryKey<Registry<T>> getRegistryKey()
    {
        return registryKey;
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

    @SafeVarargs
    public final void add(TagKey<T> key, T... values)
    {
        entries.putAll(key, Stream.of(values).map(this::reverseLookup).toList());
    }

    public final void add(TagKey<T> key, Identifier... values)
    {
        entries.putAll(key, Stream.of(values).map(id -> RegistryKey.of(registryKey, id)).toList());
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

    public final void addOptionalTag(TagKey<T> key, Identifier values)
    {
        tagsOptional.putAll(key, List.of(values));
    }

    @SafeVarargs
    public final void addOptionalTag(TagKey<T> key, TagKey<T>... values)
    {
        tagsOptional.putAll(key, Stream.of(values).map(TagKey::id).toList());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private RegistryKey<T> reverseLookup(T element)
    {
        Registry<T> registry = (Registry<T>) Registries.REGISTRIES.get((RegistryKey) registryKey);

        if (registry != null)
        {

            Optional<RegistryKey<T>> key = registry.getKey(element);
            if (key.isPresent()) return key.get();
        }

        throw new UnsupportedOperationException("Adding objects is not supported by " + getClass());
    }

    @Override
    public void generate(FabricDataGenerator.Pack pack)
    {
        pack.addProvider((output, future) -> new FabricTagProvider<T>(output, registryKey, future)
        {
            @Override
            protected void configure(RegistryWrapper.WrapperLookup arg)
            {
                getEntries().forEach((tagKey, registryKey) -> getOrCreateTagBuilder(tagKey).add(registryKey));
                getEntriesOptional().forEach((tagKey, id) -> getOrCreateTagBuilder(tagKey).addOptional(id));
                getTags().forEach((tagKey, pTagKey) -> getOrCreateTagBuilder(tagKey).addTag(pTagKey));
                getTagsOptional().forEach((tagKey, id) -> getOrCreateTagBuilder(tagKey).addOptional(id));
            }
        });
    }
}
