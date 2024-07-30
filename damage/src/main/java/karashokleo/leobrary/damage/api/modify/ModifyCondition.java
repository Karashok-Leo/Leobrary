package karashokleo.leobrary.damage.api.modify;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public interface ModifyCondition
{
    boolean shouldModify(LivingEntity entity, DamageSource source, float amount);
}
