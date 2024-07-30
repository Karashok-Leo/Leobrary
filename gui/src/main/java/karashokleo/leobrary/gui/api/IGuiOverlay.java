package karashokleo.leobrary.gui.api;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;

public interface IGuiOverlay
{
    void render(InGameHud gui, DrawContext context, float tickDelta, int scaledWidth, int scaledHeight);
}
