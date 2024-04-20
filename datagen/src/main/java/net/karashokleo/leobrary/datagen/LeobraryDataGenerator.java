package net.karashokleo.leobrary.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.karashokleo.leobrary.datagen.provider.DynamicRegistryProvider;
import net.karashokleo.leobrary.datagen.provider.ModelProvider;
import net.karashokleo.leobrary.datagen.provider.lang.ChineseLanguageProvider;
import net.karashokleo.leobrary.datagen.provider.lang.EnglishLanguageProvider;
import net.karashokleo.leobrary.datagen.provider.tag.EnchantmentTagProvider;
import net.karashokleo.leobrary.datagen.provider.tag.EntityTypeTagProvider;
import net.karashokleo.leobrary.datagen.provider.tag.ItemTagProvider;
import net.karashokleo.leobrary.datagen.provider.tag.StatusEffectTagProvider;

public class LeobraryDataGenerator implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
    {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(EnglishLanguageProvider::new);
        pack.addProvider(ChineseLanguageProvider::new);
        pack.addProvider(ItemTagProvider::new);
        pack.addProvider(EntityTypeTagProvider::new);
        pack.addProvider(EnchantmentTagProvider::new);
        pack.addProvider(StatusEffectTagProvider::new);
        pack.addProvider(ModelProvider::new);
        pack.addProvider(DynamicRegistryProvider::new);
    }
}
