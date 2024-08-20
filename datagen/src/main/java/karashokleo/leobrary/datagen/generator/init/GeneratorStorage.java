package karashokleo.leobrary.datagen.generator.init;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public interface GeneratorStorage
{
    String getModId();

    static void generate(String modid, FabricDataGenerator.Pack pack)
    {
        GeneratorStorageView.getInstance(modid).generate(pack);
    }
}

