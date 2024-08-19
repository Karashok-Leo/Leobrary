package karashokleo.leobrary.datagen.builder.provider;

import karashokleo.leobrary.datagen.generator.ModelGenerator;
import karashokleo.leobrary.datagen.generator.init.GeneratorStorageView;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface ModelGeneratorProvider extends NameSpaceProvider
{
    @NotNull
    default ModelGenerator getModelGenerator()
    {
        return Objects.requireNonNull(
                GeneratorStorageView.getInstance(this.getNameSpace()).getModelGenerator(),
                "Could not find model generator for mod: '%s'".formatted(this.getNameSpace())
        );
    }
}
