package karashokleo.leobrary.damage.api.state;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;

@SuppressWarnings("unused")
public interface DefaultDamageStateProvider extends DamageStateProvider
{
    default void addTagState(TagKey<DamageType> tag)
    {
        addState(TagDamageState.in(tag));
    }

    default void setBypassArmor()
    {
        addTagState(DamageTypeTags.BYPASSES_ARMOR);
    }

    default void setBypassMagic()
    {
        setBypassEffects();
        setBypassResistance();
        setBypassEnchantments();
    }

    default void setBypassEffects()
    {
        addTagState(DamageTypeTags.BYPASSES_EFFECTS);
    }

    default void setBypassResistance()
    {
        addTagState(DamageTypeTags.BYPASSES_RESISTANCE);
    }

    default void setBypassEnchantments()
    {
        addTagState(DamageTypeTags.BYPASSES_ENCHANTMENTS);
    }

    default void setBypassShield()
    {
        addTagState(DamageTypeTags.BYPASSES_SHIELD);
    }

    default void setBypassCooldown()
    {
        addTagState(DamageTypeTags.BYPASSES_COOLDOWN);
    }

    default void setBypassInvulnerability()
    {
        addTagState(DamageTypeTags.BYPASSES_INVULNERABILITY);
    }
}
