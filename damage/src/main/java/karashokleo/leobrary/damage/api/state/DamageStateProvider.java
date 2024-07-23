package karashokleo.leobrary.damage.api.state;

import java.util.ArrayList;

public interface DamageStateProvider
{
    ArrayList<DamageState<?>> getStates();

    default void addState(DamageState<?> damageState)
    {
        this.getStates().add(damageState);
    }

    default void clearStates()
    {
        this.getStates().clear();
    }
}
