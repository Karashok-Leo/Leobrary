package karashokleo.leobrary.damage.api;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;

public interface DefaultDamageStateProvider extends DamageStateProvider
{
    default void addState(TagKey<DamageType> tag)
    {
        addState(new TagDamageState(tag));
    }

    default void setBypassArmor()
    {
        addState(DamageTypeTags.BYPASSES_ARMOR);
    }

    default void setBypassMagic()
    {
        setBypassEffects();
        setBypassResistance();
        setBypassEnchantments();
    }

    default void setBypassEffects()
    {
        addState(DamageTypeTags.BYPASSES_EFFECTS);
    }

    default void setBypassResistance()
    {
        addState(DamageTypeTags.BYPASSES_RESISTANCE);
    }

    default void setBypassEnchantments()
    {
        addState(DamageTypeTags.BYPASSES_ENCHANTMENTS);
    }

    default void setBypassShield()
    {
        addState(DamageTypeTags.BYPASSES_SHIELD);
    }

    default void setBypassCooldown()
    {
        addState(DamageTypeTags.BYPASSES_COOLDOWN);
    }

    default void setBypassInvulnerability()
    {
        addState(DamageTypeTags.BYPASSES_INVULNERABILITY);
    }

    default void setNoImpact()
    {
        addState(DamageTypeTags.NO_IMPACT);
    }
}
