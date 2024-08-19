package karashokleo.leobrary.datagen.generator.init;

import karashokleo.leobrary.datagen.generator.*;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface GeneratorStorageView
{
    static GeneratorStorageView getInstance(String modid)
    {
        return Objects.requireNonNull(
                GeneratorStorageViewImpl.getInstance(modid),
                "GeneratorStorage for mod: '%s' not found".formatted(modid)
        );
    }

    @Nullable
    LanguageGenerator getLanguageGenerator(String languageCode);

    @Nullable
    <T> TagGenerator<T> getTagGenerator(RegistryKey<Registry<T>> registryKey);

    @Nullable
    <T> DynamicRegistryGenerator<T> getDynamicRegistryGenerator(RegistryKey<Registry<T>> registryKey);

    @Nullable
    ModelGenerator getModelGenerator();

    @Nullable
    LootGenerator getLootGenerator();

    @Nullable
    BlockLootGenerator getBlockLootGenerator();
}
