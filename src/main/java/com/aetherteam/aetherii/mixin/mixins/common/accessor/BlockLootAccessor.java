package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.data.loot.BlockLootSubProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockLootSubProvider.class)
public interface BlockLootAccessor {
    @Accessor("NORMAL_LEAVES_SAPLING_CHANCES")
    static float[] aether_ii$getNormalLeavesSaplingChances() {
        throw new AssertionError();
    }
}