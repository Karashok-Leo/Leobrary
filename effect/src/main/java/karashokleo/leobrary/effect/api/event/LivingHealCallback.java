package karashokleo.leobrary.effect.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

public interface LivingHealCallback
{
    Event<LivingHealCallback> EVENT = EventFactory.createArrayBacked(LivingHealCallback.class, (callbacks) -> (event ->
    {
        boolean allow = true;
        for (LivingHealCallback callback : callbacks)
        {
            if (!callback.onLivingHeal(event))
                allow = false;
        }
        return allow;
    }));

    /**
     * @param event The event to be called
     * @return false to cancel the event
     */
    boolean onLivingHeal(LivingHeal event);

    class LivingHeal
    {
        private final LivingEntity entity;
        private float amount;

        public LivingHeal(LivingEntity entity, float amount)
        {
            this.entity = entity;
            this.amount = amount;
        }

        public LivingEntity getEntity()
        {
            return entity;
        }

        public float getAmount()
        {
            return amount;
        }

        public void setAmount(float amount)
        {
            this.amount = amount;
        }
    }
}
