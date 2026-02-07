package karashokleo.leobrary.damage.accessor;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface DamageCallDepthAccessor
{
    void leobrary$enterDamageCall();

    void leobrary$exitDamageCall();
}
