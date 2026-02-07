package karashokleo.leobrary.damage.mixin;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import karashokleo.leobrary.damage.accessor.DamageCallDepthAccessor;
import karashokleo.leobrary.damage.api.event.DamageSourceCreateCallback;
import karashokleo.leobrary.damage.api.state.DamageState;
import karashokleo.leobrary.damage.api.state.DefaultDamageStateProvider;
import karashokleo.leobrary.damage.api.state.TagDamageState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Predicate;

@SuppressWarnings("AddedMixinMembersNamePattern")
@Mixin(DamageSource.class)
public abstract class DamageSourceMixin implements DefaultDamageStateProvider, DamageCallDepthAccessor
{
    @Unique
    private final Object2IntOpenHashMap<DamageState> states2Depths = new Object2IntOpenHashMap<>();
    @Unique
    private int damageCallDepth = 0;

    @Inject(
        method = "<init>(Lnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;)V",
        at = @At("RETURN")
    )
    private void inject_init(RegistryEntry<DamageType> type, Entity source, Entity attacker, Vec3d position, CallbackInfo ci)
    {
        DamageSourceCreateCallback.EVENT.invoker().onDamageSourceCreate(type, source, attacker, position, (DamageSource) (Object) this);
    }

    @Inject(
        method = "isIn",
        at = @At("HEAD"),
        cancellable = true
    )
    private void inject_isIn(TagKey<DamageType> tag, CallbackInfoReturnable<Boolean> cir)
    {
        this.getState(TagDamageState.class, state -> state.tag() == tag)
            .ifPresent(state -> cir.setReturnValue(state.in()));
    }

    @Override
    public Collection<DamageState> getStates()
    {
        return Collections.unmodifiableSet(this.states2Depths.keySet());
    }

    @Override
    public boolean hasState(DamageState state)
    {
        return states2Depths.containsKey(state);
    }

    @Override
    public boolean hasState(Predicate<DamageState> predicate)
    {
        return states2Depths.keySet()
            .stream()
            .anyMatch(predicate);
    }

    @Override
    public <T extends DamageState> Optional<T> getState(Class<T> clazz, Predicate<T> predicate)
    {
        return states2Depths.keySet()
            .stream()
            .filter(clazz::isInstance)
            .map(clazz::cast)
            .filter(predicate)
            .findFirst();
    }

    @Override
    public Optional<DamageState> getState(Predicate<DamageState> predicate)
    {
        return states2Depths.keySet()
            .stream()
            .filter(predicate)
            .findFirst();
    }

    @Override
    public void addState(DamageState damageState)
    {
        int depth = Math.max(this.damageCallDepth, 1);
        int currentDepth = this.states2Depths.getInt(damageState);
        if (!this.states2Depths.containsKey(damageState) ||
            depth < currentDepth)
        {
            this.states2Depths.put(damageState, depth);
        }
    }

    @Override
    public void removeState(Predicate<DamageState> predicate)
    {
        states2Depths.keySet().removeIf(predicate);
    }

    @Override
    public void clearStates()
    {
        states2Depths.clear();
    }

    @Override
    public void leobrary$enterDamageCall()
    {
        this.damageCallDepth++;
    }

    @Override
    public void leobrary$exitDamageCall()
    {
        if (this.damageCallDepth <= 0)
        {
            this.damageCallDepth = 0;
            return;
        }

        this.damageCallDepth--;
        var iterator = this.states2Depths.object2IntEntrySet().iterator();
        while (iterator.hasNext())
        {
            var entry = iterator.next();
            if (entry.getIntValue() <= this.damageCallDepth)
            {
                continue;
            }
            iterator.remove();
        }
    }
}
