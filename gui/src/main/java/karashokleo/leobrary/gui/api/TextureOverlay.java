package karashokleo.leobrary.gui.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;

public record TextureOverlay(Identifier texture, float opacity, RenderCondition condition)
{
    public interface RenderCondition
    {
        boolean shouldRender(MinecraftClient client, ClientPlayerEntity player, DrawContext context, float tickDelta);
    }
}
