package karashokleo.leobrary.gui.api.overlay;

import karashokleo.leobrary.gui.Constants;
import karashokleo.leobrary.gui.api.IGuiOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;

import java.util.List;

public abstract class InfoSideBar<S extends SideBar.Signature<S>> extends SideBar<S> implements IGuiOverlay
{
    public InfoSideBar(float duration, float ease)
    {
        super(duration, ease);
    }

    @Override
    public void render(InGameHud gui, DrawContext context, float tickDelta, int scaledWidth, int scaledHeight)
    {
        if (!ease(gui.getTicks() + tickDelta)) return;
        var text = getText();
        if (text.isEmpty()) return;
        int anchor = Constants.infoAnchor;
        int y = scaledHeight * anchor / 2;
        int w = (int) (scaledWidth * Constants.infoMaxWidth);
        new TextBox(context, 0, anchor, getXOffset(scaledWidth), y, w)
                .renderLongText(MinecraftClient.getInstance().textRenderer, text);
    }

    protected abstract List<Text> getText();

    @Override
    protected int getXOffset(int width)
    {
        float progress = (max_ease - ease_time) / max_ease;
        return Math.round(-progress * width / 2 + 8);
    }
}