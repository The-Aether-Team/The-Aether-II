package com.aetherteam.aetherii.loot;

import com.aetherteam.aetherii.mixin.mixins.common.accessor.LootContextParamSetsAccessor;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class AetherIILootContexts {
    public static final LootContextParamSet STRIPPING = LootContextParamSetsAccessor.callRegister("aether_ii:stripping", (builder) -> builder.required(LootContextParams.TOOL).build());
}
