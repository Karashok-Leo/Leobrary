package karashokleo.leobrary.damage.api.modify;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.util.math.Vec3d;

public interface DamageAccess
{
    void addModifier(DamageModifier modifier);

    LivingEntity getEntity();

    DamageSource getSource();

    Entity getAttacker();

    DamageType getDamageType();

    Vec3d getPosition();

    float getOriginalDamage();

    float getModifiedDamage(float originalDamage);

    default float getModifiedDamage()
    {
        return getModifiedDamage(getOriginalDamage());
    }
}
