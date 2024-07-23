package karashokleo.leobrary.data;

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

@SuppressWarnings("unused")
public abstract class AbstractDataProvider implements DataProvider
{
    protected final FabricDataOutput output;
    private final String midPath;
    private final List<ConfigEntry<?>> entryList = new ArrayList<>();

    public AbstractDataProvider(FabricDataOutput output, String midPath)
    {
        this.output = output;
        this.midPath = midPath;
    }

    public abstract void addAll();

    public <T> void add(Identifier id, T config)
    {
        entryList.add(
                new ConfigEntry<>(
                        "data/" + id.getNamespace() + "/" + midPath + "/" + id.getPath() + ".json",
                        config
                )
        );
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer)
    {
        Path folder = output.getPath();
        addAll();
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
