package karashokleo.leobrary.effect.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import karashokleo.leobrary.effect.api.event.LivingHealCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity
{
    protected LivingEntityMixin(EntityType<?> type, World world)
    {
        super(type, world);
    }

    @Inject(
            method = "heal",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onHeal(float amount, CallbackInfo ci, @Share("amount") LocalRef<Float> amountRef)
    {
        LivingHealCallback.LivingHeal event = new LivingHealCallback.LivingHeal((LivingEntity) (Object) this, amount);
        boolean pass = LivingHealCallback.EVENT.invoker().onLivingHeal(event);
        amountRef.set(event.getAmount());
        if (!pass) ci.cancel();
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
