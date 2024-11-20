package karashokleo.leobrary.effect.api.event;

import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface LivingHeal
{
    Event<LivingHeal> EVENT = EventFactory.createArrayBacked(LivingHeal.class, (callbacks) -> ((entity, amount, amountRef, ci) ->
    {
        for (LivingHeal callback : callbacks)
            callback.onLivingHeal(entity, amount, amountRef, ci);
    }));

    void onLivingHeal(LivingEntity entity, float amount, LocalRef<Float> amountRef, CallbackInfo ci);
}
