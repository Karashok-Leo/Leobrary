package karashokleo.leobrary.gui.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;

public record TextureOverlay(Identifier texture, OpacityProvider opacity)
{
    public interface OpacityProvider
    {
        /**
         * Get opacity value for the overlay.
         *
         * @param client    client instance
         * @param player    client player
         * @param context   draw context
         * @param tickDelta tick delta
         * @return opacity value (0.0 - 1.0), return negative value to skip rendering
         */
        float getOpacity(MinecraftClient client, ClientPlayerEntity player, DrawContext context, float tickDelta);
    }
}
