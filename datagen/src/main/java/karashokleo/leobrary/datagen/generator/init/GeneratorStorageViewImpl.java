package karashokleo.leobrary.datagen.generator.init;

import karashokleo.leobrary.datagen.generator.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class GeneratorStorageViewImpl implements GeneratorStorageView
{
    private static final Map<String, GeneratorStorageView> storageViews = new HashMap<>();

    static
    {
        FabricLoader.getInstance().invokeEntrypoints("leobrary-generator", GeneratorStorage.class, GeneratorStorageViewImpl::init);
    }

    public static GeneratorStorageView getInstance(String modid)
    {
        return storageViews.get(modid);
    }

    private GeneratorStorageViewImpl()
    {
    }

    private final Map<String, LanguageGenerator> languageGenerators = new HashMap<>();
    private final Map<RegistryKey<?>, TagGenerator<?>> tagGenerators = new HashMap<>();
    private final Map<RegistryKey<?>, DynamicRegistryGenerator<?>> dynamicRegistryGenerators = new HashMap<>();
    private ModelGenerator modelGenerator = null;
    private LootGenerator lootGenerator = null;
    private BlockLootGenerator blockLootGenerator = null;

    @SuppressWarnings("rawtypes")
    private static void init(GeneratorStorage storage)
    {
        GeneratorStorageViewImpl view = new GeneratorStorageViewImpl();
        try
        {
            for (Field field : storage.getClass().getDeclaredFields())
            {
                field.setAccessible(true);
                if (field.getType() == LanguageGenerator.class)
                {
                    LanguageGenerator languageGenerator = (LanguageGenerator) field.get(storage);
                    view.languageGenerators.put(languageGenerator.getLanguageCode(), languageGenerator);
                } else if (field.getType() == TagGenerator.class)
                {
                    TagGenerator tagGenerator = (TagGenerator) field.get(storage);
                    view.tagGenerators.put(tagGenerator.getRegistryKey(), tagGenerator);
                } else if (field.getType() == DynamicRegistryGenerator.class)
                {
                    DynamicRegistryGenerator registryGenerator = (DynamicRegistryGenerator) field.get(storage);
                    view.dynamicRegistryGenerators.put(registryGenerator.getRegistryKey(), registryGenerator);
                } else if (field.getType() == ModelGenerator.class)
                    view.modelGenerator = (ModelGenerator) field.get(storage);
                else if (field.getType() == LootGenerator.class)
                    view.lootGenerator = (LootGenerator) field.get(storage);
                else if (field.getType() == BlockLootGenerator.class)
                    view.blockLootGenerator = (BlockLootGenerator) field.get(storage);
            }
        } catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
        storageViews.put(storage.getModId(), view);
    }

    @Override
    public @Nullable LanguageGenerator getLanguageGenerator(String languageCode)
    {
        return languageGenerators.get(languageCode);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> TagGenerator<T> getTagGenerator(RegistryKey<Registry<T>> registryKey)
    {
        return (TagGenerator<T>) tagGenerators.get(registryKey);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> DynamicRegistryGenerator<T> getDynamicRegistryGenerator(RegistryKey<Registry<T>> registryKey)
    {
        return (DynamicRegistryGenerator<T>) dynamicRegistryGenerators.get(registryKey);
    }

    @Nullable
    @Override
    public ModelGenerator getModelGenerator()
    {
        return modelGenerator;
    }

    @Nullable
    @Override
    public LootGenerator getLootGenerator()
    {
        return lootGenerator;
    }

    @Nullable
    @Override
    public BlockLootGenerator getBlockLootGenerator()
    {
        return blockLootGenerator;
    }
}
