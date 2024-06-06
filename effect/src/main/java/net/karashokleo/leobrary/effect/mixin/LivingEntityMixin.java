package net.karashokleo.leobrary.effect.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.karashokleo.leobrary.effect.api.event.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity
{
    protected LivingEntityMixin(EntityType<?> type, World world)
    {
        super(type, world);
    }

    @Inject(
            method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;",
                    shift = At.Shift.BY,
                    by = 3
            ),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void onEffectAdded(StatusEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir, StatusEffectInstance old)
    {
        EffectAdded.EVENT.invoker().onEffectAdded((LivingEntity) (Object) this, effect, old, source, cir);
    }

    @Inject(
            method = "canHaveStatusEffect",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onEffectApplicable(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir)
    {
        EffectApplicable.EVENT.invoker().onEffectApplicable((LivingEntity) (Object) this, effect, cir);
    }

    @Inject(
            method = "removeStatusEffect",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onEffectRemove(StatusEffect type, CallbackInfoReturnable<Boolean> cir)
    {
        EffectRemove.EVENT.invoker().onEffectRemove((LivingEntity) (Object) this, type, cir);
    }

    // NYI
//    @Inject(
//            method = "clearStatusEffects",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/entity/LivingEntity;onStatusEffectRemoved(Lnet/minecraft/entity/effect/StatusEffectInstance;)V"
//            ),
//            cancellable = true,
//            locals = LocalCapture.CAPTURE_FAILHARD
//    )
//    private void onEffectClear(CallbackInfoReturnable<Boolean> cir, Iterator<StatusEffectInstance> iterator, boolean bl, @Share("effectInstance") LocalRef<StatusEffectInstance> effectInstanceRef)
//    {
//        StatusEffectInstance effectInstance = iterator.next();
//        effectInstanceRef.set(effectInstance);
//        EffectRemove.EVENT.invoker().onEffectRemove((LivingEntity) (Object) this, effectInstance.getEffectType(), cir);
//    }

    // NYI
    @Inject(
            method = "tickStatusEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Iterator;remove()V"
            ),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void onEffectExpired(CallbackInfo ci, Iterator<StatusEffect> iterator, StatusEffect statusEffect, StatusEffectInstance statusEffectInstance)
    {
        EffectExpired.EVENT.invoker().onEffectExpired((LivingEntity) (Object) this, statusEffectInstance, ci);
    }

    @Inject(
            method = "heal",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onHeal(float amount, CallbackInfo ci, @Share("amount") LocalRef<Float> amountRef)
    {
        Float f = amount;
        LivingHeal.EVENT.invoker().onLivingHeal((LivingEntity) (Object) this, f, ci);
        amountRef.set(f);
    }

    @ModifyVariable(
            method = "heal",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;getHealth()F"
            ),
            ordinal = 0,
            argsOnly = true
    )
    private float inject_heal(float amount, @Share("amount") LocalRef<Float> amountRef)
    {
        return amountRef.get();
    }
}
