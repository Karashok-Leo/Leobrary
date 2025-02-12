package karashokleo.leobrary.datagen.generator;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.stat.StatType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class LanguageGenerator implements CustomGenerator
{
    private final String languageCode;
    private final List<Entry> texts = new ArrayList<>();

    private record Entry(Supplier<String> keyGetter, String value)
    {
        public String key()
        {
            return keyGetter.get();
        }
    }

    public LanguageGenerator(String languageCode)
    {
        this.languageCode = languageCode;
    }

    public String getLanguageCode()
    {
        return languageCode;
    }

    private void addText(Supplier<String> keyGetter, String value)
    {
        texts.add(new Entry(keyGetter, value));
    }

    public void addText(String key, String value)
    {
        addText(() -> key, value);
    }

    public void addText(Identifier id, String value)
    {
        addText(id::toTranslationKey, value);
    }

    public void addItem(Item item, String s)
    {
        addText(item::getTranslationKey, s);
    }

    public void addBlock(Block block, String s)
    {
        addText(block::getTranslationKey, s);
    }

    public void addEffect(StatusEffect effect, String s)
    {
        addText(effect::getTranslationKey, s);
    }

    public void addEffectDesc(StatusEffect effect, String s)
    {
        addText(() -> effect.getTranslationKey() + ".desc", s);
    }

    public void addEnchantment(Enchantment enchantment, String s)
    {
        addText(enchantment::getTranslationKey, s);
    }

    public void addEnchantmentDesc(Enchantment enchantment, String s)
    {
        addText(() -> enchantment.getTranslationKey() + ".desc", s);
    }

    public void addEntityType(EntityType<?> entityType, String value)
    {
        addText(entityType::getTranslationKey, value);
    }

    public void addAttribute(EntityAttribute entityAttribute, String value)
    {
        addText(entityAttribute::getTranslationKey, value);
    }

    public void addStatType(StatType<?> statType, String value)
    {
        addText(statType::getTranslationKey, value);
    }

    @Override
    public void generate(FabricDataGenerator.Pack pack)
    {
        Map<String, String> map = new HashMap<>();
        texts.forEach(e -> map.put(e.key(), e.value()));
        texts.clear();
        pack.addProvider((FabricDataOutput output) -> new FabricLanguageProvider(output, languageCode)
        {
            @Override
            public void generateTranslations(TranslationBuilder translationBuilder)
            {
                map.forEach(translationBuilder::add);
            }
        });
    }
}
