package net.karashokleo.leobrary.compat.patchouli;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import vazkii.patchouli.common.item.PatchouliItems;
import vazkii.patchouli.common.recipe.ShapedBookRecipe;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ShapedBookRecipeBuilder extends ShapedRecipeJsonBuilder
{
    private final Identifier book;

    public ShapedBookRecipeBuilder(Identifier book)
    {
        super(RecipeCategory.MISC, PatchouliItems.BOOK, 1);
        this.book = book;
    }

    @Override
    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId)
    {
        this.validate(recipeId);
        this.advancementBuilder.parent(ROOT)
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(CriterionMerger.OR);
        exporter.accept(new ShapedBookRecipeJsonProvider(book, recipeId, this.pattern, this.inputs, this.advancementBuilder, recipeId.withPrefixedPath("recipes/")));
    }

    public record ShapedBookRecipeJsonProvider(
            Identifier book,
            Identifier id,
            List<String> pattern,
            Map<Character, Ingredient> key,
            Advancement.Builder advancement,
            Identifier advancementId
    ) implements RecipeJsonProvider
    {
        @Override
        public void serialize(JsonObject json)
        {
            JsonArray jsonarray = new JsonArray();
            for (String s : this.pattern)
            {
                jsonarray.add(s);
            }
            json.add("pattern", jsonarray);
            JsonObject jsonobject = new JsonObject();
            for (Map.Entry<Character, Ingredient> entry : this.key.entrySet())
            {
                jsonobject.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
            }
            json.add("key", jsonobject);
            json.addProperty("book", book.toString());

        }

        @Override
        public Identifier getRecipeId()
        {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getSerializer()
        {
            return ShapedBookRecipe.SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject toAdvancementJson()
        {
            return this.advancement.toJson();
        }

        @Nullable
        @Override
        public Identifier getAdvancementId()
        {
            return this.advancementId;
        }
    }
}
