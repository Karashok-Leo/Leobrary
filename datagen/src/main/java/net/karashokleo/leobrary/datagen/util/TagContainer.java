package net.karashokleo.leobrary.datagen.util;

import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagContainer<T>
{
    private final Map<TagKey<T>, List<T>> entries = new HashMap<>();
    private final Map<TagKey<T>, List<TagKey<T>>> tags = new HashMap<>();

    public Map<TagKey<T>, List<T>> getEntries()
    {
        return entries;
    }

    public Map<TagKey<T>, List<TagKey<T>>> getTags()
    {
        return tags;
    }

    @SafeVarargs
    public final void add(TagKey<T> key, T... values)
    {
        entries.putIfAbsent(key, new ArrayList<>());
        for (T type : values)
            entries.get(key).add(type);
    }

    @SafeVarargs
    public final void add(TagKey<T> key, TagKey<T>... values)
    {
        tags.putIfAbsent(key, new ArrayList<>());
        for (TagKey<T> type : values)
            tags.get(key).add(type);
    }
}
