package karashokleo.leobrary.damage.api.modify;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

import java.util.Map;
import java.util.TreeMap;

public enum DamagePhase
{
    /**
     * Before damage is blocked by shield.
     */
    SHIELD,

    /**
     * Before armor is applied.
     */
    ARMOR,

    /**
     * After ARMOR phase and before effect (like resistance) is applied.
     */
    EFFECT,

    /**
     * After EFFECT phase and before enchantment (like protection) is applied.
     */
    ENCHANTMENT,

    /**
     * After ENCHANTMENT phase and before damage deals absorbed.
     */
    ABSORB,

    /**
     * After ABSORB phase and right before setting the health value.
     */
    APPLY;

    private final Map<Integer, ConditionalDamageModifier> MODIFIERS = new TreeMap<>();

    public void registerModifier(int priority, DamageModifier modifier, ModifyCondition condition)
    {
        while (MODIFIERS.containsKey(priority))
            priority++;
        MODIFIERS.put(priority, new ConditionalDamageModifier(modifier, condition));
    }

    public float getFinalAmount(LivingEntity entity, DamageSource source, float amount)
    {
        for (ConditionalDamageModifier conditional : MODIFIERS.values())
            if (conditional.condition().shouldModify(entity, source, amount))
                amount = conditional.modifier().modify(amount);
        return amount;
    }

    public record ConditionalDamageModifier(DamageModifier modifier, ModifyCondition condition)
    {
    }
}
