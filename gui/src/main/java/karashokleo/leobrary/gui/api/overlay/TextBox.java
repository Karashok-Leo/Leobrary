package karashokleo.leobrary.gui.api.overlay;

import net.minecraft.client.gui.DrawContext;
import org.joml.Vector2i;
import org.joml.Vector2ic;

public class TextBox extends OverlayUtil
{
    private final int anchorX, anchorY;

    public TextBox(DrawContext context, int anchorX, int anchorY, int x, int y, int width)
    {
        super(context, x, y, width);
        this.anchorX = anchorX;
        this.anchorY = anchorY;
    }

    @Override
    public Vector2ic getPosition(int screenWidth, int screenHeight, int x, int y, int width, int height)
    {
        return new Vector2i(x - width * anchorX / 2, y - height * anchorY / 2);
    }

    @Override
    public int getMaxWidth()
    {
        if (anchorX == 0) return context.getScaledWindowWidth() - x0 - 8;
        if (anchorX == 1) return Math.max(x0 / 2 - 4, context.getScaledWindowWidth() - x0 / 2 - 4);
        if (anchorX == 2) return x0 - 8;
        return context.getScaledWindowWidth();
    }
}