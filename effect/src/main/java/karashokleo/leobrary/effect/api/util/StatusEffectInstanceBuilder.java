package karashokleo.leobrary.effect.api.util;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

@SuppressWarnings("unused")
public class StatusEffectInstanceBuilder
{
    private StatusEffect type;
    private int amplifier;
    private int duration;
    private boolean ambient;
    private boolean showParticles;
    private boolean showIcon;

    public StatusEffectInstanceBuilder(StatusEffectInstance ins)
    {
        this(
                ins.getEffectType(),
                ins.getAmplifier(),
                ins.getDuration(),
                ins.isAmbient(),
                ins.shouldShowParticles(),
                ins.shouldShowIcon()
        );
    }

    public StatusEffectInstanceBuilder(StatusEffect type, int amplifier, int duration, boolean ambient, boolean showParticles, boolean showIcon)
    {
        this.type = type;
        this.amplifier = amplifier;
        this.duration = duration;
        this.ambient = ambient;
        this.showParticles = showParticles;
        this.showIcon = showIcon;
    }

    public StatusEffectInstanceBuilder setEffectType(StatusEffect type)
    {
        this.type = type;
        return this;
    }

    public StatusEffectInstanceBuilder setAmplifier(int amplifier)
    {
        this.amplifier = amplifier;
        return this;
    }

    public StatusEffectInstanceBuilder setDuration(int duration)
    {
        this.duration = duration;
        return this;
    }

    public StatusEffectInstanceBuilder setAmbient(boolean ambient)
    {
        this.ambient = ambient;
        return this;
    }

    public StatusEffectInstanceBuilder setShowParticles(boolean showParticles)
    {
        this.showParticles = showParticles;
        return this;
    }

    public StatusEffectInstanceBuilder setShowIcon(boolean showIcon)
    {
        this.showIcon = showIcon;
        return this;
    }

    public StatusEffectInstance build()
    {
        return new StatusEffectInstance(type, duration, amplifier, ambient, showParticles, showIcon);
    }
}
