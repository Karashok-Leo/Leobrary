package karashokleo.leobrary.damage.api.modify;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

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

    private final Map<Integer, Consumer<DamageAccess>> LISTENERS = new TreeMap<>();

    public void addListener(int priority, Consumer<DamageAccess> listener)
    {
        while (LISTENERS.containsKey(priority))
            priority++;
        LISTENERS.put(priority, listener);
    }

    public float getFinalAmount(LivingEntity entity, DamageSource source, float amount)
    {
        DamageAccessImpl conditional = new DamageAccessImpl(entity, source, amount);
        for (Consumer<DamageAccess> consumer : LISTENERS.values())
            consumer.accept(conditional);
        return conditional.getModifiedDamage();
    }

    record DamageAccessImpl(
            LivingEntity entity,
            DamageSource source,
            float originalAmount,
            List<DamageModifier> modifiers
    ) implements DamageAccess
    {
        public DamageAccessImpl(LivingEntity entity, DamageSource source, float originalDamage)
        {
            this(entity, source, originalDamage, new ArrayList<>());
        }

        @Override
        public void addModifier(DamageModifier modifier)
        {
            this.modifiers.add(modifier);
        }

        @Override
        public LivingEntity getEntity()
        {
            return this.entity;
        }

        @Override
        public DamageSource getSource()
        {
            return this.source;
        }

        @Override
        public Entity getAttacker()
        {
            return this.source.getAttacker();
        }

        @Override
        public DamageType getDamageType()
        {
            return this.source.getType();
        }

        @Override
        public Vec3d getPosition()
        {
            return this.source.getPosition();
        }

        @Override
        public float getOriginalDamage()
        {
            return this.originalAmount;
        }

        @Override
        public float getModifiedDamage(float amount)
        {
            for (DamageModifier modifier : this.modifiers)
                amount = modifier.modify(amount);
            return amount;
        }

        @Deprecated
        @Override
        public List<DamageModifier> modifiers()
        {
            return ImmutableList.copyOf(modifiers);
        }
    }
}
