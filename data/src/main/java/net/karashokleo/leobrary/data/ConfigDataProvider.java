package net.karashokleo.leobrary.data;

import com.google.gson.JsonElement;
import dev.xkmc.l2serial.serialization.codec.JsonCodec;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class ConfigDataProvider implements DataProvider
{
    protected final FabricDataOutput output;
    private final String name;
    private final List<ConfigEntry<?>> entryList = new ArrayList<>();

    public ConfigDataProvider(FabricDataOutput output, String name)
    {
        this.output = output;
        this.name = name;
    }

    public abstract void add();

    public <T> void add(Identifier id, T config)
    {
        entryList.add(
                new ConfigEntry<>(
                        "data/" + id.getNamespace() + "/" + name + "/" + id.getPath() + ".json",
                        config
                )
        );
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer)
    {
        Path folder = output.getPath();
        add();
        List<CompletableFuture<?>> list = new ArrayList<>();
        entryList.forEach(entry ->
        {
            JsonElement elem = entry.serialize();
            if (elem != null)
                list.add(DataProvider.writeToPath(writer, elem, folder.resolve(entry.path)));
        });
        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    public record ConfigEntry<T>(String path, T config)
    {
        @Nullable
        public JsonElement serialize()
        {
            return JsonCodec.toJson(config);
        }
    }
}
