package karashokleo.leobrary.datagen.util;

import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Stack;

@SuppressWarnings("all")
public class IdUtil
{
    public static Identifier getItemId(ItemConvertible item)
    {
        return Registries.ITEM.getId(item.asItem());
    }

    public static String getItemPath(ItemConvertible item)
    {
        return IdUtil.getItemId(item).getPath();
    }

    public static IdUtil of(String namespace)
    {
        return new IdUtil(namespace);
    }

    private String namespace;
    private final Stack<String> prefixes = new Stack<>();
    private String path = "";

    public IdUtil(String namespace)
    {
        this.namespace = namespace;
    }

    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }

    public String getPrefix()
    {
        StringBuilder builder = new StringBuilder();
        for (String prefix : this.prefixes)
            builder.append(prefix);
        return builder.toString();
    }

    public void pushPrefix(String prefix)
    {
        this.prefixes.push(prefix);
    }

    public void popPrefix()
    {
        this.prefixes.pop();
    }

    public void pushAndPop(String prefix, Runnable generate)
    {
        this.pushPrefix(prefix);
        generate.run();
        this.popPrefix();
    }

    public IdUtil clearPath()
    {
        this.path = "";
        return this;
    }

    public IdUtil path(String string)
    {
        this.path += string;
        return this;
    }

    public IdUtil path(ItemConvertible item)
    {
        return path(IdUtil.getItemPath(item));
    }

    public Identifier get()
    {
        Identifier id = new Identifier(this.namespace, this.getPrefix() + this.path);
        this.clearPath();
        return id;
    }

    public Identifier get(String string)
    {
        return this.path(string).get();
    }

    public Identifier get(ItemConvertible item)
    {
        return this.path(item).get();
    }
}
