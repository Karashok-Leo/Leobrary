package karashokleo.leobrary.damage.api.state;

import net.minecraft.util.Identifier;

public record IdentifierDamageState(Identifier id) implements DamageState
{
    public boolean equals(DamageState other)
    {
        if (!(other instanceof IdentifierDamageState state))
        {
            return false;
        }
        return this.id.equals(state.id);
    }
}
