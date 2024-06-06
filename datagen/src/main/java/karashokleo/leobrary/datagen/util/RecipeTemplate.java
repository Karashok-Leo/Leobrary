package karashokleo.leobrary.datagen.util;

import dev.xkmc.l2serial.util.Wrappers;
import karashokleo.leobrary.datagen.builder.MaterialSet;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;
import java.util.function.Consumer;

@SuppressWarnings("all")
public class RecipeTemplate
{
    private static final Ingredient TEMPLATE_PLACEHOLDER = Ingredient.EMPTY;

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

    public static void storage(Consumer<RecipeJsonProvider> exporter, MaterialSet set)
    {
        storage(exporter, set.nugget(), set.ingot(), set.blockSet().item(), "");
    }

    public static void storage(Consumer<RecipeJsonProvider> exporter, MaterialSet set, String prefix)
    {
        storage(exporter, set.nugget(), set.ingot(), set.blockSet().item(), prefix);
    }

    public static void storage(Consumer<RecipeJsonProvider> exporter, ItemConvertible nugget, ItemConvertible ingot, ItemConvertible block)
    {
        storage(exporter, nugget, ingot, block, "");
    }

    public static void storage(Consumer<RecipeJsonProvider> exporter, ItemConvertible nugget, ItemConvertible ingot, ItemConvertible block, String prefix)
    {
        storage(exporter, nugget, ingot, prefix);
        storage(exporter, ingot, block, prefix);
    }

    public static void storage(Consumer<RecipeJsonProvider> exporter, ItemConvertible from, ItemConvertible to)
    {
        storage(exporter, from, to, "");
    }

    public static void storage(Consumer<RecipeJsonProvider> exporter, ItemConvertible from, ItemConvertible to, String prefix)
    {
        shaped(to, 1, from.asItem())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .input('X', from)
                .offerTo(
                        exporter,
                        IdUtil.getItemId(to.asItem())
                                .withPrefixedPath(prefix)
                                .withSuffixedPath("_storage")
                );
        shapeless(from, 9, to.asItem())
                .input(to)
                .offerTo(
                        exporter,
                        IdUtil.getItemId(to.asItem())
                                .withPrefixedPath(prefix)
                                .withSuffixedPath("_unpack")
                );
    }

    public static void smithing(Consumer<RecipeJsonProvider> exporter, TagKey<Item> in, Item mat, Item out)
    {
        smithing(exporter, in, mat, out, IdUtil.getItemId(out));
    }

    public static void smithing(Consumer<RecipeJsonProvider> exporter, TagKey<Item> in, Item mat, Item out, Identifier id)
    {
        unlock(SmithingTransformRecipeJsonBuilder.create(TEMPLATE_PLACEHOLDER, Ingredient.fromTag(in), Ingredient.ofItems(mat), RecipeCategory.MISC, out)::criterion, mat)
                .offerTo(exporter, id);
    }

    public static void smithing(Consumer<RecipeJsonProvider> exporter, Item in, Item mat, Item out)
    {
        smithing(exporter, in, mat, out, IdUtil.getItemId(out));
    }

    public static void smithing(Consumer<RecipeJsonProvider> exporter, Item in, Item mat, Item out, Identifier id)
    {
        unlock(SmithingTransformRecipeJsonBuilder.create(TEMPLATE_PLACEHOLDER, Ingredient.ofItems(in), Ingredient.ofItems(mat), RecipeCategory.MISC, out)::criterion, mat)
                .offerTo(exporter, id);
    }

    public static void smelting(Consumer<RecipeJsonProvider> exporter, Item source, Item result, float experience)
    {
        smelting(exporter, source, result, experience, IdUtil.getItemId(source));
    }

    public static void smelting(Consumer<RecipeJsonProvider> exporter, Item source, Item result, float experience, Identifier id)
    {
        unlock(CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(source), RecipeCategory.MISC, result, experience, 200)::criterion, source)
                .offerTo(exporter, id);
    }

    public static void blasting(Consumer<RecipeJsonProvider> exporter, Item source, Item result, float experience)
    {
        blasting(exporter, source, result, experience, IdUtil.getItemId(source));
    }

    public static void blasting(Consumer<RecipeJsonProvider> exporter, Item source, Item result, float experience, Identifier id)
    {
        unlock(CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItems(source), RecipeCategory.MISC, result, experience, 200)::criterion, source)
                .offerTo(exporter, id);
    }
}
