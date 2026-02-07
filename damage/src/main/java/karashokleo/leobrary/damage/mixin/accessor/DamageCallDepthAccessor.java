package karashokleo.leobrary.damage.mixin.accessor;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface DamageCallDepthAccessor
{
    void leobrary$enterDamageCall();

    void leobrary$exitDamageCall();
}
