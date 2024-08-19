package karashokleo.leobrary.datagen.builder.provider;

import karashokleo.leobrary.datagen.generator.TagGenerator;
import karashokleo.leobrary.datagen.generator.init.GeneratorStorageView;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface TagGeneratorProvider<T> extends NameSpaceProvider
{
    @NotNull
    default TagGenerator<T> getTagGenerator(RegistryKey<Registry<T>> registryKey)
    {
        return Objects.requireNonNull(
                GeneratorStorageView.getInstance(this.getNameSpace())
                        .getTagGenerator(registryKey),
                "Could not find tag generator for mod: '%s'".formatted(this.getNameSpace())
        );
    }
}
