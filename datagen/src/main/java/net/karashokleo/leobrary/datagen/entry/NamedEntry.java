package net.karashokleo.leobrary.datagen.entry;

import net.minecraft.util.Identifier;

public class NamedEntry<T>
{
    protected String name;
    protected T content;

    public NamedEntry(String name, T content)
    {
        this.name = name;
        this.content = content;
    }

    public Identifier getId()
    {
        return new Identifier(name);
    }
}
