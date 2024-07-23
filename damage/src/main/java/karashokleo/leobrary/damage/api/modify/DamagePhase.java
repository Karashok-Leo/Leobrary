package karashokleo.leobrary.damage.api.modify;

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
    APPLY
}
