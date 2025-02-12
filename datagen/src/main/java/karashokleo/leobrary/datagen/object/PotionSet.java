package karashokleo.leobrary.datagen.object;

import net.minecraft.potion.Potion;
import org.jetbrains.annotations.Nullable;

public record PotionSet(
        @Nullable Potion potion,
        @Nullable Potion longPotion,
        @Nullable Potion strongPotion
)
{
}
