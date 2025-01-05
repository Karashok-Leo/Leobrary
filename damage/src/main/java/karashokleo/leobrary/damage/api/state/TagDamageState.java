package karashokleo.leobrary.damage.api.state;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.tag.TagKey;

public record TagDamageState(TagKey<DamageType> tag, boolean in) implements DamageState
{
    public static TagDamageState in(TagKey<DamageType> tag)
    {
        return new TagDamageState(tag, true);
    }
}
