package karashokleo.leobrary.datagen.builder.provider;

import karashokleo.leobrary.datagen.generator.LanguageGenerator;
import karashokleo.leobrary.datagen.generator.init.GeneratorStorageView;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface LanguageGeneratorProvider extends NameSpaceProvider
{
    @NotNull
    default LanguageGenerator getLanguageGenerator(String languageCode)
    {
        return Objects.requireNonNull(
                GeneratorStorageView.getInstance(this.getNameSpace())
                        .getLanguageGenerator(languageCode),
                "Could not find language generator for mod: '%s'".formatted(this.getNameSpace())
        );
    }
}
