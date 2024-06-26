package com.aetherteam.aetherii.loot;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.LootContextParamSetsAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.function.Consumer;

public class AetherIILootContexts {
    public static final LootContextParamSet STRIPPING = register("stripping", (builder) -> builder.required(LootContextParams.TOOL).build());

    private static LootContextParamSet register(String pRegistryName, Consumer<LootContextParamSet.Builder> pBuilderConsumer) {
        LootContextParamSet.Builder lootcontextparamset$builder = new LootContextParamSet.Builder();
        pBuilderConsumer.accept(lootcontextparamset$builder);
        LootContextParamSet lootcontextparamset = lootcontextparamset$builder.build();
        ResourceLocation resourcelocation = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, pRegistryName);
        LootContextParamSet lootcontextparamset1 = LootContextParamSetsAccessor.getRegistry().put(resourcelocation, lootcontextparamset);
        if (lootcontextparamset1 != null) {
            throw new IllegalStateException("Loot table parameter set " + resourcelocation + " is already registered");
        } else {
            return lootcontextparamset;
        }
    }
}
