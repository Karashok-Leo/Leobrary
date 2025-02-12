package karashokleo.leobrary.datagen.object;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

import java.util.Objects;

public record BlockSet(Block block, BlockItem item)
{
    @Override
    public Block block()
    {
        return Objects.requireNonNull(block);
    }

    @Override
    public BlockItem item()
    {
        return Objects.requireNonNull(item);
    }
}
