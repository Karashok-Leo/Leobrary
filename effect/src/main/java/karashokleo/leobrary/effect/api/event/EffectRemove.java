package karashokleo.leobrary.effect.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ApiStatus.Experimental
public interface EffectRemove
{
    Event<EffectRemove> EVENT = EventFactory.createArrayBacked(EffectRemove.class, (callbacks) -> ((entity, effect, cir) ->
    {
        for (EffectRemove callback : callbacks)
            callback.onEffectRemove(entity, effect, cir);
    }));

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    void onEffectRemove(LivingEntity entity, StatusEffect effect, CallbackInfoReturnable<Boolean> cir);
}
