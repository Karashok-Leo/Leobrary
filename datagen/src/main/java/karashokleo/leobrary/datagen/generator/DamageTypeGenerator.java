package karashokleo.leobrary.datagen.generator;

import karashokleo.leobrary.datagen.builder.provider.DefaultLanguageGeneratorProvider;
import karashokleo.leobrary.datagen.builder.provider.TagGeneratorProvider;
import karashokleo.leobrary.datagen.generator.init.GeneratorStorageView;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import java.util.Objects;

public record DamageTypeGenerator(String namespace) implements DefaultLanguageGeneratorProvider, TagGeneratorProvider
{
    @SafeVarargs
    public final void register(
            RegistryKey<DamageType> registryKey,
            DamageType damageType,
            String deathMsgEn,
            String deathMsgPlayerEn,
            String deathMsgZh,
            String deathMsgPlayerZh,
            TagKey<DamageType>... tags
    )
    {
        DynamicRegistryGenerator<DamageType> generator = GeneratorStorageView
                .getInstance(namespace)
                .getDynamicRegistryGenerator(RegistryKeys.DAMAGE_TYPE);
        Objects.requireNonNull(generator, "Cannot find DynamicRegistryGenerator<DamageType>");
        generator.add(registryKey, damageType);
        generateDeathMsg(damageType.msgId(), deathMsgEn, deathMsgPlayerEn, deathMsgZh, deathMsgPlayerZh);
        if (tags.length == 0)
            return;
        TagGenerator<DamageType> tagGenerator = getTagGenerator(RegistryKeys.DAMAGE_TYPE);
        for (TagKey<DamageType> tag : tags)
            tagGenerator.getOrCreateContainer(tag).add(registryKey);
    }

    public void generateDeathMsg(String msgId, String deathMsgEn, String deathMsgPlayerEn, String deathMsgZh, String deathMsgPlayerZh)
    {
        String deathMsg = "death.attack." + msgId;
        String deathMsgPlayer = "death.attack." + msgId + ".player";
        LanguageGenerator en = getEnglishGenerator();
        en.addText(deathMsg, deathMsgEn);
        en.addText(deathMsgPlayer, deathMsgPlayerEn);
        LanguageGenerator ch = getChineseGenerator();
        ch.addText(deathMsg, deathMsgZh);
        ch.addText(deathMsgPlayer, deathMsgPlayerZh);
    }

    @Override
    public String getNameSpace()
    {
        return namespace;
    }
}
