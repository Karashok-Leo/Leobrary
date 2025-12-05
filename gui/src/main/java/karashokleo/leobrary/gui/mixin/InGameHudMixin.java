package karashokleo.leobrary.gui.mixin;

import karashokleo.leobrary.gui.api.GuiOverlayRegistry;
import karashokleo.leobrary.gui.api.IGuiOverlay;
import karashokleo.leobrary.gui.api.TextureOverlay;
import karashokleo.leobrary.gui.api.TextureOverlayRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin
{
    @Final
    @Shadow
    private MinecraftClient client;

    @Shadow
    protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Shadow
    private int scaledWidth;

    @Shadow
    private int scaledHeight;

    @Inject(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayerEntity;getFrozenTicks()I"
        )
    )
    private void inject_renderOverlay(DrawContext context, float tickDelta, CallbackInfo ci)
    {
        Iterator<TextureOverlay> iterator = TextureOverlayRegistry.iterator();
        while (iterator.hasNext())
        {
            TextureOverlay textureOverlay = iterator.next();
            float opacity = textureOverlay.opacity().getOpacity(client, client.player, context, tickDelta);
            if (opacity < 0)
            {
                continue;
            }
            renderOverlay(context, textureOverlay.texture(), opacity);
        }
    }

    @Inject(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/InGameHud;renderMountHealth(Lnet/minecraft/client/gui/DrawContext;)V"
        )
    )
    private void inject_renderMountHealth(DrawContext context, float tickDelta, CallbackInfo ci)
    {
        Iterator<IGuiOverlay> iterator = GuiOverlayRegistry.iterator();
        while (iterator.hasNext())
        {
            iterator.next().render((InGameHud) (Object) this, context, tickDelta, this.scaledWidth, this.scaledHeight);
        }
    }
}
