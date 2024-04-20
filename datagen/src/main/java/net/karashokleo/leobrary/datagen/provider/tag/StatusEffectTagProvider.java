package net.karashokleo.leobrary.datagen.provider.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.karashokleo.leobrary.datagen.util.TagContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class StatusEffectTagProvider extends FabricTagProvider<StatusEffect>
{
    public static final TagContainer<StatusEffect> CONTAINER = new TagContainer<>();

    public StatusEffectTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
    {
        super(output, RegistryKeys.STATUS_EFFECT, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg)
    {
        for (var entry : CONTAINER.getEntries().entrySet())
        {
            var builder = getOrCreateTagBuilder(entry.getKey());
            for (var value : entry.getValue())
                builder.add(value);
        }
        for (var entry : CONTAINER.getTags().entrySet())
        {
            var builder = getOrCreateTagBuilder(entry.getKey());
            for (var value : entry.getValue())
                builder.forceAddTag(value);
        }
    }

    @Override
    protected RegistryKey<StatusEffect> reverseLookup(StatusEffect element)
    {
        return Registries.STATUS_EFFECT.getKey(element).orElseThrow(() -> new IllegalArgumentException("Status Effect " + element + " is not registered"));
    }
}
