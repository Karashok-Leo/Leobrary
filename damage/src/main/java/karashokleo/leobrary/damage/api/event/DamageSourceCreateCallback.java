package karashokleo.leobrary.damage.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public interface DamageSourceCreateCallback
{
    Event<DamageSourceCreateCallback> EVENT = EventFactory.createArrayBacked(DamageSourceCreateCallback.class, callbacks -> (type, source, attacker, position, damageSource) ->
    {
        for (DamageSourceCreateCallback callback : callbacks)
            callback.onDamageSourceCreate(type, source, attacker, position, damageSource);
    });

    void onDamageSourceCreate(RegistryEntry<DamageType> type, @Nullable Entity source, @Nullable Entity attacker, @Nullable Vec3d position, DamageSource damageSource);
}
