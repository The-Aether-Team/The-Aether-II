package com.aetherteam.aetherii.loot;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.ContextKeySetSetsAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.function.Consumer;

public class AetherIILootContexts {
    public static final ContextKeySet STRIPPING = register("stripping", (builder) -> builder.required(LootContextParams.TOOL).build());

    private static ContextKeySet register(String name, Consumer<ContextKeySet.Builder> consumer) {
        ContextKeySet.Builder lootcontextparamset$builder = new ContextKeySet.Builder();
        consumer.accept(lootcontextparamset$builder);
        ContextKeySet contextKeySet = lootcontextparamset$builder.build();
        ResourceLocation resourcelocation = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name);
        ContextKeySet contextKeySet1 = ContextKeySetSetsAccessor.getRegistry().put(resourcelocation, contextKeySet);
        if (contextKeySet1 != null) {
            throw new IllegalStateException("Loot table parameter set " + resourcelocation + " is already registered");
        } else {
            return contextKeySet;
        }
    }
}