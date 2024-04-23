package net.karashokleo.leobrary.datagen.builder;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.karashokleo.leobrary.datagen.util.StringUtil;
import net.karashokleo.leobrary.datagen.generator.LanguageGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public abstract class ItemGroupBuilder extends NamedEntryBuilder<Void>
{
    @Nullable
    public abstract LanguageGenerator getEnglishGenerator();

    @Nullable
    public abstract LanguageGenerator getChineseGenerator();

    public final RegistryKey<ItemGroup> registryKey;
    private Supplier<ItemStack> iconSupplier;
    private final List<ItemStack> entries = new ArrayList<>();
    private final String translationKey;

    public ItemGroupBuilder(String name, Supplier<ItemStack> iconSupplier)
    {
        super(name, null);
        this.registryKey = RegistryKey.of(RegistryKeys.ITEM_GROUP, getId());
        this.iconSupplier = iconSupplier;
        this.translationKey = Util.createTranslationKey("itemGroup", registryKey.getValue());
    }

    public ItemGroupBuilder(String name)
    {
        this(name, () -> ItemStack.EMPTY);
    }

    public void add(ItemStack... stacks)
    {
        entries.addAll(Arrays.asList(stacks));
    }

    public void add(ItemConvertible... items)
    {
        for (ItemConvertible item : items)
            entries.add(item.asItem().getDefaultStack());
    }

    public ItemGroupBuilder setIcon(Supplier<ItemStack> iconSupplier)
    {
        this.iconSupplier = iconSupplier;
        return this;
    }

    public ItemGroupBuilder addEN()
    {
        return addEN(StringUtil.defaultName(name));
    }

    public ItemGroupBuilder addEN(String en)
    {
        if (getEnglishGenerator() == null)
            throw new UnsupportedOperationException();
        getEnglishGenerator().addText(translationKey, en);
        return this;
    }

    public ItemGroupBuilder addZH(String zh)
    {
        if (getChineseGenerator() == null)
            throw new UnsupportedOperationException();
        getChineseGenerator().addText(translationKey, zh);
        return this;
    }

    public void register()
    {
        Registry.register(
                Registries.ITEM_GROUP,
                registryKey,
                FabricItemGroup.builder()
                        .icon(iconSupplier)
                        .displayName(Text.translatable(translationKey))
                        .entries((displayContext, entries) -> entries.addAll(this.entries))
                        .build()
        );
    }
}
