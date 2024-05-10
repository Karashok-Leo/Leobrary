package net.karashokleo.leobrary.effect.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ApiStatus.Experimental
public interface EffectExpired
{
    Event<EffectExpired> EVENT = EventFactory.createArrayBacked(EffectExpired.class, (callbacks) -> ((entity, effect, ci) ->
    {
        for (EffectExpired callback : callbacks)
            callback.onEffectExpired(entity, effect, ci);
    }));

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    void onEffectExpired(LivingEntity entity, StatusEffectInstance effect, CallbackInfo ci);
}
