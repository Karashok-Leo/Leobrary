package karashokleo.leobrary.compat.patchouli;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import vazkii.patchouli.common.item.PatchouliItems;
import vazkii.patchouli.common.recipe.ShapelessBookRecipe;

import java.util.List;
import java.util.function.Consumer;

public class ShapelessBookRecipeJsonBuilder extends ShapelessRecipeJsonBuilder
{
    private final Identifier book;

    public ShapelessBookRecipeJsonBuilder(Identifier book)
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
        exporter.accept(new ShapelessBookRecipeJsonProvider(book, recipeId, this.inputs, this.advancementBuilder, recipeId.withPrefixedPath("recipes/")));
    }


    public record ShapelessBookRecipeJsonProvider(
            Identifier book,
            Identifier id,
            List<Ingredient> ingredients,
            Advancement.Builder advancement,
            Identifier advancementId
    ) implements RecipeJsonProvider
    {
        @Override
        public void serialize(JsonObject json)
        {
            JsonArray jsonarray = new JsonArray();
            for (Ingredient ingredient : this.ingredients)
                jsonarray.add(ingredient.toJson());
            json.add("ingredients", jsonarray);
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
            return ShapelessBookRecipe.SERIALIZER;
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
