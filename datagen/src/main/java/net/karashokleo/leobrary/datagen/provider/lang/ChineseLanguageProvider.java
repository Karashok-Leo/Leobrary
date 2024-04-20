package net.karashokleo.leobrary.datagen.provider.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.karashokleo.leobrary.datagen.util.TextContainer;

public class ChineseLanguageProvider extends FabricLanguageProvider
{
    public static final TextContainer CONTAINER = new TextContainer();

    public ChineseLanguageProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput, "zh_cn");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder)
    {
        CONTAINER.getTexts().forEach(translationBuilder::add);
    }
}
