package net.karashokleo.leobrary.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.xkmc.l2serial.serialization.codec.JsonCodec;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

@SuppressWarnings("unused")
public abstract class AbstractDataLoader<T> implements SimpleSynchronousResourceReloadListener
{
    private final String path;
    private final Class<T> cls;
    private final Logger LOGGER;

    protected AbstractDataLoader(String path, Class<T> cls)
    {
        this.path = path;
        this.cls = cls;
        this.LOGGER = LoggerFactory.getLogger(cls.getName());
    }

    public void error(Identifier id, Exception e)
    {
        LOGGER.error("Error loading resource {}. {}", id.toString(), e.toString());
    }

    @Override
    public void reload(ResourceManager manager)
    {
        clear();
        manager.findResources(path, id -> id.getPath().endsWith(".json")).forEach((id, resourceRef) ->
        {
            try
            {
                InputStream stream = resourceRef.getInputStream();
                JsonObject data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
                load(
                        id.withPath(s -> s
                                .replaceFirst(path + "/", "")
                                .replaceFirst(".json", "")),
                        Objects.requireNonNull(JsonCodec.from(data, cls, null))
                );
            } catch (Exception e)
            {
                error(id, e);
            }
        });
    }

    protected abstract void clear();

    protected abstract void load(Identifier id, T config);
}
