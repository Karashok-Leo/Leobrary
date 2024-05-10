package net.karashokleo.leobrary.effect.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.MilkBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MilkBucketItem.class)
public abstract class MilkBucketItemMixin
{
//    @WrapOperation(
//            method = "finishUsing",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"
//            )
//    )
//    private boolean wrap_clearStatusEffects(LivingEntity entity, Operation<Boolean> original)
//    {
//        return entity.clearStatusEffects(effect -> effect.getEffectType.isIn(EffectTags.CAN_BE_CURED_BY_MILK));
//    }
}
