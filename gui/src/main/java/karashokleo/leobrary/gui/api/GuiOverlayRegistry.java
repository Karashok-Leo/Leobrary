package karashokleo.leobrary.gui.api;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class GuiOverlayRegistry
{
    private static final Map<Integer, IGuiOverlay> OVERLAYS = new TreeMap<>();

    public static void registerLayer(int priority, IGuiOverlay IGuiOverlay)
    {
        while (OVERLAYS.containsKey(priority))
            priority++;
        OVERLAYS.put(priority, IGuiOverlay);
    }

    public static Iterator<IGuiOverlay> iterator()
    {
        return OVERLAYS.values().iterator();
    }
}
