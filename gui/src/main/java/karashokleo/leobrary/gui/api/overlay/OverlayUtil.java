package karashokleo.leobrary.gui.api.overlay;

import karashokleo.leobrary.gui.Constants;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipPositioner;
import net.minecraft.text.Text;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.List;

public class OverlayUtil implements TooltipPositioner
{
    @Deprecated
    private static int getBGColor()
    {
        return (int) (Math.round(Constants.infoAlpha * 255)) << 24 | 0x100010;
    }

//    public int bg = getBGColor();
//    public int bs = 0x505000FF;
//    public int be = 0x5028007f;
//    public int tc = 0xFFFFFFFF;

    protected final DrawContext context;
    protected final int x0, y0, maxW;

    public OverlayUtil(DrawContext context, int x0, int y0, int maxW)
    {
        this.context = context;
        this.x0 = x0;
        this.y0 = y0;
        this.maxW = maxW < 0 ? getMaxWidth() : maxW;
    }

    public int getMaxWidth()
    {
        return context.getScaledWindowWidth() / 4;
    }

    public void renderLongText(TextRenderer textRenderer, List<Text> list)
    {
        List<TooltipComponent> ans = list.stream().flatMap(text -> textRenderer.wrapLines(text, maxW).stream())
                .map(TooltipComponent::of).toList();
        renderTooltipInternal(textRenderer, ans);
    }

    public void renderTooltipInternal(TextRenderer textRenderer, List<TooltipComponent> list)
    {
        if (list.isEmpty()) return;
        int w = 0;
        int h = list.size() == 1 ? -2 : 0;
        for (TooltipComponent c : list)
        {
            int wi = c.getWidth(textRenderer);
            if (wi > w)
            {
                w = wi;
            }
            h += c.getHeight();
        }
        int wf = w;
        int hf = h;
        Vector2ic pos = getPosition(context.getScaledWindowWidth(), context.getScaledWindowHeight(), x0, y0, wf, hf);
        int xf = pos.x();
        int yf = pos.y();
        context.getMatrices().push();
        int z = 400;
//        context.draw(() -> TooltipBackgroundRenderer.render(context, xf, yf, wf, hf, z, bg, bg, bs, be));
        context.draw(() -> TooltipBackgroundRenderer.render(context, xf, yf, wf, hf, z));
        context.getMatrices().translate(0.0F, 0.0F, z);
        int yi = yf;
        for (int i = 0; i < list.size(); ++i)
        {
            TooltipComponent c = list.get(i);
            c.drawText(textRenderer, xf, yi, context.getMatrices().peek().getPositionMatrix(), context.getVertexConsumers());
            yi += c.getHeight() + (i == 0 ? 2 : 0);
        }
        yi = yf;
        for (int i = 0; i < list.size(); ++i)
        {
            TooltipComponent c = list.get(i);
            c.drawItems(textRenderer, xf, yi, context);
            yi += c.getHeight() + (i == 0 ? 2 : 0);
        }
        context.getMatrices().pop();
    }

    @Override
    public Vector2ic getPosition(int screenWidth, int screenHeight, int x, int y, int width, int height)
    {
        if (x < 0) x = Math.round(screenWidth / 8f);
        if (y < 0) y = Math.round((screenHeight - height) / 2f);
        return new Vector2i(x, y);
    }

    /**
     * specifies outer size
     */
    public static void fillRect(DrawContext context, int x, int y, int w, int h, int col)
    {
        context.fill(x, y, x + w, y + h, col);
    }

    /**
     * specifies inner size
     */
    public static void drawRect(DrawContext context, int x, int y, int w, int h, int col)
    {
        fillRect(context, x - 1, y - 1, w + 2, 1, col);
        fillRect(context, x - 1, y - 1, 1, h + 2, col);
        fillRect(context, x - 1, y + h, w + 2, 1, col);
        fillRect(context, x + w, y - 1, 1, h + 2, col);
    }
}
