package net.karashokleo.leobrary.datagen.builder;

import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public abstract class NamedEntryBuilder<T>
{
    protected String name;
    protected T content;

    public NamedEntryBuilder(String name, T content)
    {
        this.name = name;
        this.content = content;
    }

    public Identifier getId()
    {
        return new Identifier(getNameSpace(), name);
    }

    public String getTranslationKey(String prefix)
    {
        return getId().toTranslationKey(prefix);
    }

    protected abstract String getNameSpace();
}
