package karashokleo.leobrary.datagen.builder;

import karashokleo.leobrary.datagen.builder.provider.DefaultLanguageGeneratorProvider;
import karashokleo.leobrary.datagen.util.StringUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public abstract class ItemGroupBuilder
        extends NamedEntryBuilder<Void>
        implements DefaultLanguageGeneratorProvider
{
    public final RegistryKey<ItemGroup> registryKey;
    private Supplier<ItemStack> iconSupplier;
    protected final List<ItemStack> entries = new ArrayList<>();
    protected final String translationKey;

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
        this.getEnglishGenerator().addText(translationKey, en);
        return this;
    }

    public ItemGroupBuilder addZH(String zh)
    {
        this.getChineseGenerator().addText(translationKey, zh);
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
