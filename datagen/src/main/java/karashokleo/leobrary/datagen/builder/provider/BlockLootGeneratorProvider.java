package karashokleo.leobrary.datagen.builder.provider;

import karashokleo.leobrary.datagen.generator.BlockLootGenerator;
import karashokleo.leobrary.datagen.generator.init.GeneratorStorageView;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface BlockLootGeneratorProvider extends NameSpaceProvider
{
    @NotNull
    default BlockLootGenerator getBlockLootGenerator()
    {
        return Objects.requireNonNull(
                GeneratorStorageView.getInstance(this.getNameSpace())
                        .getBlockLootGenerator(),
                "Could not find block loot generator for mod: '%s'".formatted(this.getNameSpace())
        );
    }
}
