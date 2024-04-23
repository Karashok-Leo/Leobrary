package net.karashokleo.leobrary.datagen.util;

import dev.xkmc.l2serial.util.Wrappers;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class RecipeGenUtil
{
    private static final Ingredient TEMPLATE_PLACEHOLDER = Ingredient.EMPTY;

    public static Identifier getID(ItemConvertible item)
    {
        return Registries.ITEM.getId(item.asItem());
    }

    public static <T> T unlock(BiFunction<String, InventoryChangedCriterion.Conditions, T> func, Item item)
    {
        return func.apply(FabricRecipeProvider.hasItem(item), FabricRecipeProvider.conditionsFromItem(item));
    }

    public static <T extends CraftingRecipeJsonBuilder> T crafting(T builder, Item item)
    {
        return Wrappers.cast(unlock(builder::criterion, item));
    }

    public static ShapedRecipeJsonBuilder shaped(ItemConvertible output, int count, Item item)
    {
        return crafting(ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, output, count), item);
    }

    public static ShapelessRecipeJsonBuilder shapeless(ItemConvertible output, int count, Item item)
    {
        return crafting(ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, output, count), item);
    }

    private static void storage(Consumer<RecipeJsonProvider> exporter, ItemConvertible nugget, ItemConvertible ingot, ItemConvertible block)
    {
        storage(exporter, nugget, ingot);
        storage(exporter, ingot, block);
    }

    public static void storage(Consumer<RecipeJsonProvider> exporter, ItemConvertible from, ItemConvertible to)
    {
        shaped(to, 1, from.asItem())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .input('X', from)
                .offerTo(exporter, getID(to.asItem()) + "_storage");
        shapeless(from, 9, to.asItem())
                .input(to)
                .offerTo(exporter, getID(to.asItem()) + "_unpack");
    }

    public static void smithing(Consumer<RecipeJsonProvider> exporter, TagKey<Item> in, Item mat, Item out)
    {
        unlock(SmithingTransformRecipeJsonBuilder.create(TEMPLATE_PLACEHOLDER, Ingredient.fromTag(in), Ingredient.ofItems(mat), RecipeCategory.MISC, out)::criterion, mat)
                .offerTo(exporter, getID(out));
    }

    public static void smithing(Consumer<RecipeJsonProvider> exporter, Item in, Item mat, Item out)
    {
        unlock(SmithingTransformRecipeJsonBuilder.create(TEMPLATE_PLACEHOLDER, Ingredient.ofItems(in), Ingredient.ofItems(mat), RecipeCategory.MISC, out)::criterion, mat)
                .offerTo(exporter, getID(out));
    }

    public static void smelting(Consumer<RecipeJsonProvider> exporter, Item source, Item result, float experience)
    {
        unlock(CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(source), RecipeCategory.MISC, result, experience, 200)::criterion, source)
                .offerTo(exporter, getID(source));
    }

    public static void blasting(Consumer<RecipeJsonProvider> exporter, Item source, Item result, float experience)
    {
        unlock(CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItems(source), RecipeCategory.MISC, result, experience, 200)::criterion, source)
                .offerTo(exporter, getID(source));
    }
}
