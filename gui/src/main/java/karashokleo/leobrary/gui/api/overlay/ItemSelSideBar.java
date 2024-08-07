package karashokleo.leobrary.gui.api.overlay;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class ItemSelSideBar<S extends SideBar.Signature<S>> extends SelectionSideBar<ItemStack, S>
{
    public ItemSelSideBar(float duration, float ease)
    {
        super(duration, ease);
    }

    @Override
    protected void renderEntry(Context ctx, ItemStack stack, int i, int selected)
    {
        boolean shift = MinecraftClient.getInstance().options.sneakKey.isPressed();
        int y = 18 * i + ctx.y0();
        renderSelection(ctx.context(), ctx.x0(), y, shift ? 127 : 64, isAvailable(stack), selected == i);
        if (selected == i)
        {
            if (!stack.isEmpty() && ease_time == max_ease)
            {
                boolean onCenter = onCenter();
                ctx.context().drawTooltip(ctx.textRenderer(), stack.getName(), 0, 0);
                TextBox box = new TextBox(ctx.context(), onCenter ? 0 : 2, 1, ctx.x0() + (onCenter ? 22 : -6), y + 8, -1);
                box.renderLongText(ctx.textRenderer(), List.of(stack.getName()));
            }
        }
        ctx.renderItem(stack, ctx.x0(), y);
    }

    public void renderSelection(DrawContext g, int x, int y, int a, boolean available, boolean selected)
    {
        if (available)
            OverlayUtil.fillRect(g, x, y, 16, 16, color(255, 255, 255, a));
        else
            OverlayUtil.fillRect(g, x, y, 16, 16, color(255, 0, 0, a));
        if (selected)
            OverlayUtil.drawRect(g, x, y, 16, 16, color(255, 170, 0, 255));
    }

    public static int color(int r, int g, int b, int a)
    {
        return a << 24 | r << 16 | g << 8 | b;
    }
}
