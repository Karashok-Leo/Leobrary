package karashokleo.leobrary.datagen.util;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.state.property.Property;

@SuppressWarnings("unused")
public class LootTableTemplate
{
    public static LootPool.Builder getPool(int roll, int bonus)
    {
        return LootPool
                .builder()
                .rolls(ConstantLootNumberProvider.create(roll))
                .bonusRolls(ConstantLootNumberProvider.create(0));
    }

    public static LeafEntry.Builder<?> getItem(Item item, int count)
    {
        return ItemEntry
                .builder(item)
                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(count)));
    }

    public static LeafEntry.Builder<?> getItem(Item item, int min, int max)
    {
        return ItemEntry
                .builder(item)
                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(min, max)));
    }

    public static LeafEntry.Builder<?> getItem(Item item, int min, int max, int add)
    {
        return ItemEntry
                .builder(item)
                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(min, max)))
                .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0, add)));
    }

    public static LootCondition.Builder byPlayer()
    {
        return KilledByPlayerLootCondition
                .builder();
    }

    public static LootCondition.Builder chance(float chance)
    {
        return RandomChanceLootCondition
                .builder(chance);
    }

    public static LootCondition.Builder chance(float chance, float add)
    {
        return RandomChanceWithLootingLootCondition
                .builder(chance, add);
    }

//    public static BlockStatePropertyLootCondition.Builder withBlockState(Block block, Property<Integer> prop, int low, int high) {
//        StatePredicate.Builder builder = StatePredicate.Builder.create();
//        builder.matchers.add(new StatePredicate.RangedValueCondition(prop.getName(), Integer.toString(low), Integer.toString(high)));
//        return BlockStatePropertyLootCondition.builder(block).properties(builder);
//    }

    public static BlockStatePropertyLootCondition.Builder withBlockState(Block block, Property<Integer> prop, int val)
    {
        return BlockStatePropertyLootCondition
                .builder(block)
                .properties(StatePredicate.Builder.create().exactMatch(prop, val));
    }

    public static BlockStatePropertyLootCondition.Builder withBlockState(Block block, Property<Boolean> prop, boolean val)
    {
        return BlockStatePropertyLootCondition
                .builder(block)
                .properties(StatePredicate.Builder.create().exactMatch(prop, val));
    }

    public static BlockStatePropertyLootCondition.Builder withBlockState(Block block, Property<?> prop, String val)
    {
        return BlockStatePropertyLootCondition
                .builder(block)
                .properties(StatePredicate.Builder.create().exactMatch(prop, val));
    }

    public static LeafEntry.Builder<?> cropDrop(Item item)
    {
        return ItemEntry
                .builder(item)
                .apply(ApplyBonusLootFunction.binomialWithBonusCount(Enchantments.FORTUNE, 0.57f, 3));
    }

    public static EnchantmentPredicate hasEnchantment(Enchantment enchant, int min)
    {
        return new EnchantmentPredicate(enchant, NumberRange.IntRange.atLeast(min));
    }

    public static LootCondition.Builder shearOrSilk(boolean inverted)
    {
        AnyOfLootCondition.Builder ans = AnyOfLootCondition.builder(
                MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ConventionalItemTags.SHEARS)),
                MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(hasEnchantment(Enchantments.SILK_TOUCH, 1)))
        );
        return inverted ? ans.invert() : ans;
    }

    public static LootCondition.Builder silk(boolean inverted)
    {
        LootCondition.Builder ans = MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(hasEnchantment(Enchantments.SILK_TOUCH, 1)));
        return inverted ? ans.invert() : ans;
    }

    public static LootTable.Builder selfOrOther(Block block, Block base, Item other, int count)
    {
        return LootTable
                .builder()
                .pool(LootTableTemplate
                        .getPool(1, 0)
                        .with(LootTableTemplate.getItem(base.asItem(), 1))
                        .conditionally(SurvivesExplosionLootCondition.builder())
                        .conditionally(LootTableTemplate.silk(true)))
                .pool(LootTableTemplate
                        .getPool(1, 0)
                        .with(LootTableTemplate.getItem(other, count))
                        .conditionally(SurvivesExplosionLootCondition.builder())
                        .conditionally(LootTableTemplate.silk(true)))
                .pool(LootTableTemplate
                        .getPool(1, 0)
                        .with(LootTableTemplate.getItem(block.asItem(), 1))
                        .conditionally(LootTableTemplate.silk(false)));
    }
}
