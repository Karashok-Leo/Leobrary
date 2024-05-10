package net.karashokleo.leobrary.effect.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Completed
public interface EffectAdded
{
    Event<EffectAdded> EVENT = EventFactory.createArrayBacked(EffectAdded.class, (callbacks) -> ((livingEntity, newEffectInstance, oldEffectInstance, source, cir) ->
    {
        for (EffectAdded callback : callbacks)
            callback.onEffectAdded(livingEntity, newEffectInstance, oldEffectInstance, source, cir);
    }));

    void onEffectAdded(LivingEntity entity, StatusEffectInstance newEffectInstance, @Nullable StatusEffectInstance oldEffectInstance, @Nullable Entity source, CallbackInfoReturnable<Boolean> cir);
}
