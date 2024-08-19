package karashokleo.leobrary.datagen.builder.provider;

import karashokleo.leobrary.datagen.generator.LanguageGenerator;
import org.jetbrains.annotations.NotNull;

public interface DefaultLanguageGeneratorProvider extends LanguageGeneratorProvider
{
    @NotNull
    default LanguageGenerator getEnglishGenerator()
    {
        return this.getLanguageGenerator("en_us");
    }

    @NotNull
    default LanguageGenerator getChineseGenerator()
    {
        return this.getLanguageGenerator("zh_cn");
    }
}
