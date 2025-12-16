package karashokleo.leobrary.damage.api.state;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public interface DamageStateProvider
{
    default Collection<DamageState> getStates()
    {
        throw new UnsupportedOperationException();
    }

    default boolean hasState(DamageState state)
    {
        throw new UnsupportedOperationException();
    }

    default boolean hasState(Predicate<DamageState> predicate)
    {
        throw new UnsupportedOperationException();
    }

    default Optional<DamageState> getState(Predicate<DamageState> predicate)
    {
        throw new UnsupportedOperationException();
    }

    default <T extends DamageState> Optional<T> getState(Class<T> clazz, Predicate<T> predicate)
    {
        throw new UnsupportedOperationException();
    }

    default void addState(DamageState damageState)
    {
        throw new UnsupportedOperationException();
    }

    default void removeState(Predicate<DamageState> predicate)
    {
        throw new UnsupportedOperationException();
    }

    default void clearStates()
    {
        throw new UnsupportedOperationException();
    }
}
