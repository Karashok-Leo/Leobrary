package karashokleo.leobrary.gui.api;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextureOverlayRegistry
{
    private static final List<TextureOverlay> TEXTURE_OVERLAYS = new ArrayList<>();

    public static void register(Identifier texture, float opacity, TextureOverlay.RenderCondition context)
    {
        TEXTURE_OVERLAYS.add(new TextureOverlay(texture, opacity, context));
    }

    public static Iterator<TextureOverlay> iterator()
    {
        return TEXTURE_OVERLAYS.iterator();
    }
}
