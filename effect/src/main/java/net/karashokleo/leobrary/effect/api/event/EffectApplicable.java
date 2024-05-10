package net.karashokleo.leobrary.effect.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Completed
public interface EffectApplicable
{
    Event<EffectApplicable> EVENT = EventFactory.createArrayBacked(EffectApplicable.class, (callbacks) -> ((entity, effect, cir) ->
    {
        for (EffectApplicable callback : callbacks)
            callback.onEffectApplicable(entity, effect, cir);
    }));

    void onEffectApplicable(LivingEntity entity, StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir);
}
