package karashokleo.leobrary.datagen.generator;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class TagGenerator<T> implements CustomGenerator
{
    public record Container<T>(
            RegistryKey<Registry<T>> registryKey,
            boolean replace,
            ArrayList<RegistryKey<T>> entries,
            ArrayList<Identifier> entriesOptional,
            ArrayList<TagKey<T>> tags,
            ArrayList<Identifier> tagsOptional
    )
    {
        private Container(RegistryKey<Registry<T>> registryKey, boolean replace)
        {
            this(
                    registryKey,
                    replace,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>()
            );
        }

        private Container(RegistryKey<Registry<T>> registryKey)
        {
            this(registryKey, false);
        }

        public Container<T> add(T element)
        {
            entries.add(reverseLookup(element));
            return this;
        }

        @SafeVarargs
        public final Container<T> add(T... elements)
        {
            entries.addAll(Stream.of(elements).map(this::reverseLookup).toList());
            return this;
        }

        public Container<T> add(Identifier id)
        {
            entries.add(RegistryKey.of(registryKey, id));
            return this;
        }

        public Container<T> add(RegistryKey<T> registryKey)
        {
            entries.add(registryKey);
            return this;
        }

        public Container<T> addOptional(Identifier... identifiers)
        {
            entriesOptional.addAll(List.of(identifiers));
            return this;
        }

        @SafeVarargs
        public final Container<T> addOptional(RegistryKey<T>... registryKeys)
        {
            entriesOptional.addAll(Stream.of(registryKeys).map(RegistryKey::getValue).toList());
            return this;
        }

        @SafeVarargs
        public final Container<T> addTag(TagKey<T>... values)
        {
            tags.addAll(List.of(values));
            return this;
        }

        public Container<T> addOptionalTag(Identifier... values)
        {
            tagsOptional.addAll(List.of(values));
            return this;
        }

        @SafeVarargs
        public final Container<T> addOptionalTag(TagKey<T>... values)
        {
            tagsOptional.addAll(Stream.of(values).map(TagKey::id).toList());
            return this;
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
    }

    private final RegistryKey<Registry<T>> registryKey;

    private final Map<TagKey<T>, Container<T>> all = new HashMap<>();

    public TagGenerator(RegistryKey<Registry<T>> registryKey)
    {
        this.registryKey = registryKey;
    }

    public RegistryKey<Registry<T>> getRegistryKey()
    {
        return registryKey;
    }

    public Container<T> getOrCreateContainer(TagKey<T> key)
    {
        return getOrCreateContainer(key, false);
    }

    public Container<T> getOrCreateContainer(TagKey<T> key, boolean replace)
    {
        return all.computeIfAbsent(key, c -> new Container<>(registryKey, replace));
    }

    @Override
    public void generate(FabricDataGenerator.Pack pack)
    {
        pack.addProvider((output, future) -> new FabricTagProvider<T>(output, registryKey, future)
        {
            @Override
            protected void configure(RegistryWrapper.WrapperLookup arg)
            {
                all.forEach((key, container) ->
                {
                    FabricTagProvider<T>.FabricTagBuilder builder = getOrCreateTagBuilder(key);
                    builder.setReplace(container.replace);
                    container.entries.forEach(builder::add);
                    container.entriesOptional.forEach(builder::addOptional);
                    container.tags.forEach(builder::addTag);
                    container.tagsOptional.forEach(builder::addOptionalTag);
                });
            }
        });
    }
}
