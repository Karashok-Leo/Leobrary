package karashokleo.leobrary.effect.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface LivingHeal
{
    Event<LivingHeal> EVENT = EventFactory.createArrayBacked(LivingHeal.class, (callbacks) -> ((entity, amount, ci) ->
    {
        for (LivingHeal callback : callbacks)
            callback.onLivingHeal(entity, amount, ci);
    }));

    void onLivingHeal(LivingEntity entity, Float amount, CallbackInfo ci);
}
