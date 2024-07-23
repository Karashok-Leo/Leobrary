package karashokleo.leobrary.datagen.builder;

import net.minecraft.item.Item;

import java.util.Objects;

public record MaterialSet(Item ingot, Item nugget, BlockSet blockSet)
{
    @Override
    public Item ingot()
    {
        return Objects.requireNonNull(ingot);
    }

    @Override
    public Item nugget()
    {
        return Objects.requireNonNull(nugget);
    }

    @Override
    public BlockSet blockSet()
    {
        return Objects.requireNonNull(blockSet);
    }
}
