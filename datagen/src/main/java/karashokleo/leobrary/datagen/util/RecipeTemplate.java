package karashokleo.leobrary.datagen.util;

import dev.xkmc.l2serial.util.Wrappers;
import karashokleo.leobrary.datagen.object.MaterialSet;
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

    public static void storage(Consumer<RecipeJsonProvider> exporter, MaterialSet set, IdUtil idUtil)
    {
        storage(exporter, set.nugget(), set.ingot(), set.blockSet().item(), idUtil);
    }

    public static void storage(Consumer<RecipeJsonProvider> exporter, ItemConvertible nugget, ItemConvertible ingot, ItemConvertible block, IdUtil idUtil)
    {
        storage(exporter, nugget, ingot, idUtil);
        storage(exporter, ingot, block, idUtil);
    }

    public static void storage(Consumer<RecipeJsonProvider> exporter, ItemConvertible from, ItemConvertible to, IdUtil idUtil)
    {
        compose(exporter, from, to, idUtil.path(to).get("_compose"));
        decompose(exporter, to, from, idUtil.path(to).get("_decompose"));
    }

    public static void storage(Consumer<RecipeJsonProvider> exporter, ItemConvertible from, ItemConvertible to, Identifier composeId, Identifier decomposeId)
    {
        compose(exporter, from, to, composeId);
        decompose(exporter, to, from, decomposeId);
    }

    public static void compose(Consumer<RecipeJsonProvider> exporter, ItemConvertible input, ItemConvertible output, Identifier composeId)
    {
        shaped(output, 1, input.asItem())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .input('X', input)
                .offerTo(exporter, composeId);
    }

    public static void decompose(Consumer<RecipeJsonProvider> exporter, ItemConvertible input, ItemConvertible output, Identifier decomposeId)
    {
        shapeless(output, 9, input.asItem())
                .input(input)
                .offerTo(exporter, decomposeId);
    }

    public static void smithing(Consumer<RecipeJsonProvider> exporter, TagKey<Item> in, Item mat, Item out, IdUtil idUtil)
    {
        smithing(exporter, in, mat, out, idUtil.get(out));
    }

    public static void smithing(Consumer<RecipeJsonProvider> exporter, TagKey<Item> in, Item mat, Item out, Identifier id)
    {
        unlock(SmithingTransformRecipeJsonBuilder.create(TEMPLATE_PLACEHOLDER, Ingredient.fromTag(in), Ingredient.ofItems(mat), RecipeCategory.MISC, out)::criterion, mat)
                .offerTo(exporter, id);
    }

    public static void smithing(Consumer<RecipeJsonProvider> exporter, Item in, Item mat, Item out, IdUtil idUtil)
    {
        smithing(exporter, in, mat, out, idUtil.get(out));
    }

    public static void smithing(Consumer<RecipeJsonProvider> exporter, Item in, Item mat, Item out, Identifier id)
    {
        unlock(SmithingTransformRecipeJsonBuilder.create(TEMPLATE_PLACEHOLDER, Ingredient.ofItems(in), Ingredient.ofItems(mat), RecipeCategory.MISC, out)::criterion, mat)
                .offerTo(exporter, id);
    }

    public static void smelting(Consumer<RecipeJsonProvider> exporter, Item source, Item result, float experience, IdUtil idUtil)
    {
        smelting(exporter, source, result, experience, idUtil.get(source));
    }

    public static void smelting(Consumer<RecipeJsonProvider> exporter, Item source, Item result, float experience, Identifier id)
    {
        unlock(CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(source), RecipeCategory.MISC, result, experience, 200)::criterion, source)
                .offerTo(exporter, id);
    }

    public static void blasting(Consumer<RecipeJsonProvider> exporter, Item source, Item result, float experience, IdUtil idUtil)
    {
        blasting(exporter, source, result, experience, idUtil.get(source));
    }

    public static void blasting(Consumer<RecipeJsonProvider> exporter, Item source, Item result, float experience, Identifier id)
    {
        unlock(CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItems(source), RecipeCategory.MISC, result, experience, 200)::criterion, source)
                .offerTo(exporter, id);
    }
}
