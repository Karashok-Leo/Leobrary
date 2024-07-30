package karashokleo.leobrary.gui.api.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import karashokleo.leobrary.gui.api.IGuiOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

import java.util.List;

public abstract class SelectionSideBar<T, S extends SideBar.Signature<S>> extends SideBar<S> implements IGuiOverlay
{
    public SelectionSideBar(float duration, float ease)
    {
        super(duration, ease);
    }

    public abstract Pair<List<T>, Integer> getItems();

    public abstract boolean isAvailable(T t);

    public abstract boolean onCenter();

    public void initRender()
    {
    }

    @Override
    public void render(InGameHud gui, DrawContext context, float tickDelta, int scaledWidth, int scaledHeight)
    {
        if (!ease(gui.getTicks() + tickDelta)) return;
        initRender();
        setupOverlayRenderState(true, false);
        int x0 = this.getXOffset(scaledWidth);
        int y0 = this.getYOffset(scaledHeight);
        Context ctx = new Context(gui, context, tickDelta, MinecraftClient.getInstance().textRenderer, x0, y0);
        renderContent(ctx);
    }

    /**
     * From ForgeGui
     *
     * @param blend     enableBlend
     * @param depthTest enableDepthTest
     */
    public void setupOverlayRenderState(boolean blend, boolean depthTest)
    {
        if (blend)
        {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
        } else
        {
            RenderSystem.disableBlend();
        }

        if (depthTest)
        {
            RenderSystem.enableDepthTest();
        } else
        {
            RenderSystem.disableDepthTest();
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
    }

    public void renderContent(Context ctx)
    {
        Pair<List<T>, Integer> content = getItems();
        var list = content.getLeft();
        for (int i = 0; i < list.size(); i++)
            renderEntry(ctx, list.get(i), i, content.getRight());
    }

    protected abstract void renderEntry(Context ctx, T t, int index, int select);

    public record Context(InGameHud gui, DrawContext context, float pTick, TextRenderer textRenderer, int x0, int y0)
    {
        public void renderItem(ItemStack stack, int x, int y)
        {
            if (!stack.isEmpty())
            {
                context.drawItem(stack, x, y);
                context.drawItemInSlot(textRenderer, stack, x, y);
            }
        }
    }
}
