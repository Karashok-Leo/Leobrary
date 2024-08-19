package karashokleo.leobrary.datagen.builder;

import karashokleo.leobrary.datagen.builder.provider.DefaultLanguageGeneratorProvider;
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

import java.util.Objects;

@SuppressWarnings("unused")
public abstract class PotionBuilder
        extends NamedEntryBuilder<StatusEffect>
        implements DefaultLanguageGeneratorProvider
{
    protected Potion potion;
    protected Potion longPotion;
    protected Potion strongPotion;

    public PotionBuilder(String name, StatusEffect content)
    {
        super(name, content);
    }

    public PotionSet build()
    {
        return new PotionSet(potion, longPotion, strongPotion);
    }

    public PotionBuilder register()
    {
        return register(3600, 0);
    }

    public PotionBuilder register(int duration, int amplifier)
    {
        this.potion = Registry.register(Registries.POTION, getId(), new Potion(new StatusEffectInstance(content, duration, amplifier)));
        return this;
    }

    public PotionBuilder registerLong()
    {
        return registerLong(9600, 0);
    }

    public PotionBuilder registerLong(int duration, int amplifier)
    {
        this.longPotion = Registry.register(Registries.POTION, getId().withPrefixedPath("long_"), new Potion(new StatusEffectInstance(content, duration, amplifier)));
        return this;
    }

    public PotionBuilder registerStrong()
    {
        return registerStrong(3600, 1);
    }

    public PotionBuilder registerStrong(int duration, int amplifier)
    {
        this.strongPotion = Registry.register(Registries.POTION, getId().withPrefixedPath("strong_"), new Potion(new StatusEffectInstance(content, duration, amplifier)));
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
        return recipe(input, item, potion);
    }

    public PotionBuilder recipeLong()
    {
        return recipeLong(Items.REDSTONE);
    }

    public PotionBuilder recipeLong(Item item)
    {
        return recipeLong(potion, item);
    }

    public PotionBuilder recipeLong(Potion input, Item item)
    {
        return recipe(input, item, longPotion);
    }

    public PotionBuilder recipeStrong()
    {
        return recipeStrong(Items.GLOWSTONE_DUST);
    }

    public PotionBuilder recipeStrong(Item item)
    {
        return recipeStrong(potion, item);
    }

    public PotionBuilder recipeStrong(Potion input, Item item)
    {
        return recipe(input, item, strongPotion);
    }

    public PotionBuilder addEN()
    {
        return addEN(StringUtil.defaultName(name));
    }

    public PotionBuilder addEN(String en)
    {
        return addPotionEN("Potion of " + en)
                .addSplashEN("Splash Potion of " + en)
                .addLingeringEN("Lingering Potion of " + en)
                .addTippedArrowEN("Arrow of " + en);
    }

    public PotionBuilder addZH(String zh)
    {
        return addPotionZH(zh + "药水")
                .addSplashZH("喷溅型" + zh + "药水")
                .addLingeringZH("滞留型" + zh + "药水")
                .addTippedArrowZH(zh + "之箭");
    }

    public PotionBuilder addPotionEN()
    {
        return addPotionEN("Potion of " + StringUtil.defaultName(name));
    }

    public PotionBuilder addPotionEN(String en)
    {
        this.getEnglishGenerator().addText("item.minecraft.potion.effect." + name, en);
        return this;
    }

    public PotionBuilder addPotionZH(String zh)
    {
        this.getChineseGenerator().addText("item.minecraft.potion.effect." + name, zh);
        return this;
    }

    public PotionBuilder addSplashEN()
    {
        return addSplashEN("Splash Potion of " + StringUtil.defaultName(name));
    }

    public PotionBuilder addSplashEN(String en)
    {
        this.getEnglishGenerator().addText("item.minecraft.splash_potion.effect." + name, en);
        return this;
    }

    public PotionBuilder addSplashZH(String zh)
    {
        this.getChineseGenerator().addText("item.minecraft.splash_potion.effect." + name, zh);
        return this;
    }

    public PotionBuilder addLingeringEN()
    {
        return addLingeringEN("Lingering Potion of " + StringUtil.defaultName(name));
    }

    public PotionBuilder addLingeringEN(String en)
    {
        this.getEnglishGenerator().addText("item.minecraft.lingering_potion.effect." + name, en);
        return this;
    }

    public PotionBuilder addLingeringZH(String zh)
    {
        this.getChineseGenerator().addText("item.minecraft.lingering_potion.effect." + name, zh);
        return this;
    }

    public PotionBuilder addTippedArrowEN()
    {
        return addTippedArrowEN("Arrow of " + StringUtil.defaultName(name));
    }

    public PotionBuilder addTippedArrowEN(String en)
    {
        this.getEnglishGenerator().addText("item.minecraft.tipped_arrow.effect." + name, en);
        return this;
    }

    public PotionBuilder addTippedArrowZH(String zh)
    {
        this.getChineseGenerator().addText("item.minecraft.tipped_arrow.effect." + name, zh);
        return this;
    }
}
