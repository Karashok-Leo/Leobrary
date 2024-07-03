package karashokleo.leobrary.damage.mixin;

import karashokleo.leobrary.damage.api.DamageState;
import karashokleo.leobrary.damage.api.DefaultDamageStateProvider;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(DamageSource.class)
public abstract class DamageSourceMixin implements DefaultDamageStateProvider
{
    @Unique
    private final ArrayList<DamageState<?>> damageStates = new ArrayList<>();

    @Inject(
            method = "isIn",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_isIn(TagKey<DamageType> tag, CallbackInfoReturnable<Boolean> cir)
    {
        for (DamageState<?> state : this.getStates())
            if (state.get() == tag)
                cir.setReturnValue(true);
    }

    @Override
    public ArrayList<DamageState<?>> getStates()
    {
        return damageStates;
    }
}
