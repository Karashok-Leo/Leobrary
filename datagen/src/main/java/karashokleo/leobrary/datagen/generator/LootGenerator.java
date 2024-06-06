package karashokleo.leobrary.datagen.generator;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public class LootGenerator implements CustomGenerator
{
    private final LootContextType type;
    private final Map<Identifier, LootTable.Builder> entries = new HashMap<>();

    public LootGenerator(LootContextType type)
    {
        this.type = type;
    }

    public void addLoot(Identifier id, LootTable.Builder builder)
    {
        entries.put(id, builder);
    }

    @Override
    public void generate(FabricDataGenerator.Pack pack)
    {
        pack.addProvider((FabricDataOutput output) -> new SimpleFabricLootTableProvider(output, type)
        {
            @Override
            public void accept(BiConsumer<Identifier, LootTable.Builder> exporter)
            {
                entries.forEach(exporter);
            }
        });
    }
}
