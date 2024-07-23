package karashokleo.leobrary.damage.api.modify;

@SuppressWarnings("unused")
public interface DamageModifier
{
    float modify(float originalDamage);

    record Add(float operator) implements DamageModifier
    {
        @Override
        public float modify(float originalDamage)
        {
            return originalDamage + operator;
        }
    }

    record Multiply(float operator) implements DamageModifier
    {
        @Override
        public float modify(float originalDamage)
        {
            return originalDamage * operator;
        }
    }
}
