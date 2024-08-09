package karashokleo.leobrary.damage.api.modify;

@SuppressWarnings("unused")
public interface DamageModifier
{
    float modify(float originalDamage);

    static DamageModifier add(float operator)
    {
        return originalDamage -> originalDamage + operator;
    }

    static DamageModifier multiply(float operator)
    {
        return originalDamage -> originalDamage * operator;
    }
}
