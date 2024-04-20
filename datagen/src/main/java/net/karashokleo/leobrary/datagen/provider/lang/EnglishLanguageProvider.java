package net.karashokleo.leobrary.datagen.provider.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.karashokleo.leobrary.datagen.util.TextContainer;

public class EnglishLanguageProvider extends FabricLanguageProvider
{
    public static final TextContainer CONTAINER = new TextContainer();

    public EnglishLanguageProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder)
    {
        CONTAINER.getTexts().forEach(translationBuilder::add);
    }
}
