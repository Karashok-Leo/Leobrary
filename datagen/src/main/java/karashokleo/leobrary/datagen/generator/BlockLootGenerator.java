package karashokleo.leobrary.datagen.generator;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BlockLootGenerator implements CustomGenerator
{
    private final List<Consumer<BlockLootTableGenerator>> consumers = new ArrayList<>();

    public void addLoot(Consumer<BlockLootTableGenerator> consumer)
    {
        consumers.add(consumer);
    }

    @Override
    public void generate(FabricDataGenerator.Pack pack)
    {
        pack.addProvider((FabricDataOutput output) -> new FabricBlockLootTableProvider(output)
        {
            @Override
            public void generate()
            {
                for (Consumer<BlockLootTableGenerator> consumer : consumers)
                    consumer.accept(this);
            }
        });
    }
}
