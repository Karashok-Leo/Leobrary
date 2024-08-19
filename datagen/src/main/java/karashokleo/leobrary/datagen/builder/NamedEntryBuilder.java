package karashokleo.leobrary.datagen.builder;

import karashokleo.leobrary.datagen.builder.provider.NameSpaceProvider;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public abstract class NamedEntryBuilder<T> implements NameSpaceProvider
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
        return new Identifier(this.getNameSpace(), name);
    }

    public String getTranslationKey(String prefix)
    {
        return this.getId().toTranslationKey(prefix);
    }
}
