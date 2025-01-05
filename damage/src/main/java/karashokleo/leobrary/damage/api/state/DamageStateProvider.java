package karashokleo.leobrary.damage.api.state;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public interface DamageStateProvider
{
    Collection<DamageState> getStates();

    boolean hasState(Predicate<DamageState> predicate);

    Optional<DamageState> getState(Predicate<DamageState> predicate);

    <T extends DamageState> Optional<T> getState(Class<T> clazz,Predicate<T> predicate);

    void addState(DamageState damageState);

    void removeState(Predicate<DamageState> predicate);

    void clearStates();
}
