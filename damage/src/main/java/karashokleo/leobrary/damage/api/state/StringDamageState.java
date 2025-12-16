package karashokleo.leobrary.damage.api.state;

public record StringDamageState(String userdata) implements DamageState
{
    public boolean equals(DamageState other)
    {
        if (!(other instanceof StringDamageState state))
        {
            return false;
        }
        return this.userdata.equals(state.userdata);
    }
}
