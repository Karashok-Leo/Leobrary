package karashokleo.leobrary.datagen.builder;

import karashokleo.leobrary.datagen.builder.provider.DefaultLanguageGeneratorProvider;
import karashokleo.leobrary.datagen.object.PotionEffectType;
import karashokleo.leobrary.datagen.object.PotionItemType;
import karashokleo.leobrary.datagen.object.PotionSet;
import karashokleo.leobrary.datagen.util.StringUtil;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Objects;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public abstract class PotionBuilder
        extends NamedEntryBuilder<StatusEffect>
        implements DefaultLanguageGeneratorProvider
{
    protected final EnumMap<PotionEffectType, Potion> potions = new EnumMap<>(PotionEffectType.class);

    public PotionBuilder(String name, StatusEffect content)
    {
        super(name, content);
    }

    @Nullable
    public Potion potion()
    {
        return potions.get(PotionEffectType.NORMAL);
    }

    @Nullable
    public Potion longPotion()
    {
        return potions.get(PotionEffectType.LONG);
    }

    @Nullable
    public Potion strongPotion()
    {
        return potions.get(PotionEffectType.STRONG);
    }

    public PotionSet build()
    {
        return new PotionSet(potion(), longPotion(), strongPotion());
    }

    public PotionBuilder register()
    {
        return register(PotionEffectType.NORMAL)
                .register(PotionEffectType.LONG)
                .register(PotionEffectType.STRONG);
    }

    public PotionBuilder register(PotionEffectType effectType)
    {
        return register(effectType.getDefaultDuration(), effectType.getDefaultAmplifier(), effectType);
    }

    public PotionBuilder register(int duration, int amplifier, PotionEffectType effectType)
    {
        Potion strongPotion = Registry.register(Registries.POTION, getId().withPrefixedPath(effectType.getLangPrefix()), new Potion(new StatusEffectInstance(content, duration, amplifier)));
        this.potions.put(effectType, strongPotion);
        return this;
    }

    public PotionBuilder recipe(Potion input, Item item, Potion output)
    {
        Objects.requireNonNull(input, "Input potion not yet registered!");
        Objects.requireNonNull(output, "Output potion not yet registered!");
        BrewingRecipeRegistry.registerPotionRecipe(input, item, output);
        return this;
    }

    public PotionBuilder recipe(Item item)
    {
        return recipe(Potions.AWKWARD, item);
    }

    public PotionBuilder recipe(Potion input, Item item)
    {
        return recipe(input, item, potion());
    }

    public PotionBuilder recipeLong()
    {
        return recipeLong(Items.REDSTONE);
    }

    public PotionBuilder recipeLong(Item item)
    {
        return recipeLong(potion(), item);
    }

    public PotionBuilder recipeLong(Potion input, Item item)
    {
        return recipe(input, item, longPotion());
    }

    public PotionBuilder recipeStrong()
    {
        return recipeStrong(Items.GLOWSTONE_DUST);
    }

    public PotionBuilder recipeStrong(Item item)
    {
        return recipeStrong(potion(), item);
    }

    public PotionBuilder recipeStrong(Potion input, Item item)
    {
        return recipe(input, item, strongPotion());
    }

    protected String getTranslationKey(PotionItemType itemType, PotionEffectType effectType)
    {
        return "item.minecraft.%s.effect.%s%s".formatted(itemType.getLangKey(), effectType.getLangPrefix(), name);
    }

    public PotionBuilder addEN(String text, PotionItemType itemType, PotionEffectType effectType)
    {
        String key = getTranslationKey(itemType, effectType);
        this.getEnglishGenerator().addText(key, text);
        return this;
    }

    public PotionBuilder addZH(String text, PotionItemType itemType, PotionEffectType effectType)
    {
        String key = getTranslationKey(itemType, effectType);
        this.getChineseGenerator().addText(key, text);
        return this;
    }

    public PotionBuilder addEN()
    {
        return addEN(StringUtil.defaultName(name));
    }

    public PotionBuilder addEN(String en)
    {
        for (PotionEffectType effectType : potions.keySet())
            for (PotionItemType itemType : PotionItemType.values())
                addEN(itemType.getEN(en), itemType, effectType);
        return this;
    }

    public PotionBuilder addEN(String en, PotionItemType itemType)
    {
        for (PotionEffectType effectType : potions.keySet())
            addEN(itemType.getEN(en), itemType, effectType);
        return this;
    }

    public PotionBuilder addEN(String en, PotionEffectType effectType)
    {
        for (PotionItemType itemType : PotionItemType.values())
            addEN(itemType.getEN(en), itemType, effectType);
        return this;
    }

    public PotionBuilder addZH(String zh)
    {
        for (PotionEffectType effectType : potions.keySet())
            for (PotionItemType itemType : PotionItemType.values())
                addZH(itemType.getZH(zh), itemType, effectType);
        return this;
    }

    public PotionBuilder addZH(String zh, PotionItemType itemType)
    {
        for (PotionEffectType effectType : potions.keySet())
            addZH(itemType.getZH(zh), itemType, effectType);
        return this;
    }

    public PotionBuilder addZH(String zh, PotionEffectType effectType)
    {
        for (PotionItemType itemType : PotionItemType.values())
            addZH(itemType.getZH(zh), itemType, effectType);
        return this;
    }

    protected void assertPotion()
    {
        Objects.requireNonNull(potion(), "Potion not yet registered!");
    }

    protected void assertLongPotion()
    {
        Objects.requireNonNull(longPotion(), "Long potion not yet registered!");
    }

    protected void assertStrongPotion()
    {
        Objects.requireNonNull(strongPotion(), "Strong potion not yet registered!");
    }
}
