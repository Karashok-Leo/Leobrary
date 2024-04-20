package net.karashokleo.leobrary.datagen.util;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemGroupContainer
{
    private static final Map<RegistryKey<ItemGroup>, List<ItemConvertible>> entries = new HashMap<>();

    public static void add(RegistryKey<ItemGroup> group, ItemConvertible... items)
    {
        entries.putIfAbsent(group, new ArrayList<>());
        for (ItemConvertible item : items)
            entries.get(group).add(item);
    }

    public static void register()
    {
        entries.forEach((group, items) ->
                ItemGroupEvents.modifyEntriesEvent(group).register(entries1 ->
                        items.forEach(entries1::add)));
    }
}
