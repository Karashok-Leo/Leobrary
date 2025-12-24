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

    static DamageModifier reduce(float operator)
    {
        return originalDamage -> Math.max(originalDamage - operator, 0);
    }

    static DamageModifier zero(float operator)
    {
        return originalDamage -> 0;
    }

    static DamageModifier min(float operator)
    {
        return originalDamage -> Math.min(originalDamage, operator);
    }

    static DamageModifier max(float operator)
    {
        return originalDamage -> Math.max(originalDamage, operator);
    }
}
