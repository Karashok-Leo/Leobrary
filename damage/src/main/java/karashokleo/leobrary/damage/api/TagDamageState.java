package karashokleo.leobrary.damage.api;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.tag.TagKey;

public record TagDamageState(TagKey<DamageType> tag) implements DamageState<TagKey<DamageType>>
{
    @Override
    public TagKey<DamageType> get()
    {
        return tag;
    }
}
