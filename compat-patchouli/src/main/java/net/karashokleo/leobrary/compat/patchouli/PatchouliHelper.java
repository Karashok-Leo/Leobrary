package net.karashokleo.leobrary.compat.patchouli;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetNbtLootFunction;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import vazkii.patchouli.common.item.ItemModBook;
import vazkii.patchouli.common.item.PatchouliItems;

import java.util.function.BiFunction;

@SuppressWarnings("unused")
public class PatchouliHelper
{
    public static ItemStack book(Identifier id)
    {
        return ItemModBook.forBook(id);
    }

    public static void model(FabricDataGenerator.Pack pack, Identifier... books)
    {
        pack.addProvider((FabricDataOutput output) -> new FabricModelProvider(output)
        {
            @Override
            public String getName()
            {
                return "Patchouli Book Models";
            }

            @Override
            public void generateBlockStateModels(BlockStateModelGenerator generator)
            {
            }

            @Override
            public void generateItemModels(ItemModelGenerator generator)
            {
                for (Identifier book : books)
                {
                    Identifier id = book.withPrefixedPath("item/");
                    Models.GENERATED.upload(id, TextureMap.layer0(id), generator.writer);
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    public static LootTable.Builder loot(Identifier book)
    {
        NbtCompound tag = new NbtCompound();
        tag.putString("patchouli:book", book.toString());
        return LootTable.builder().pool(
                LootPool.builder().with(ItemEntry.builder(PatchouliItems.BOOK)
                        .apply(SetNbtLootFunction.builder(tag))));
    }

    public static ShapedRecipeJsonBuilder shaped(Identifier book, Item item)
    {
        return unlock(new ShapedBookRecipeBuilder(book)::criterion, item);
    }

    public static ShapelessRecipeJsonBuilder shapeless(Identifier book, Item item)
    {
        return unlock(new ShapelessBookRecipeJsonBuilder(book)::criterion, item);
    }

    public static <T> T unlock(BiFunction<String, InventoryChangedCriterion.Conditions, T> func, Item item)
    {
        return func.apply(FabricRecipeProvider.hasItem(item), FabricRecipeProvider.conditionsFromItem(item));
    }
}
