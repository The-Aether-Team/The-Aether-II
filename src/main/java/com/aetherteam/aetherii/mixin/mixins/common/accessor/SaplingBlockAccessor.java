package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SaplingBlock.class)
public interface SaplingBlockAccessor {
    @Accessor("treeGrower")
    TreeGrower aether_ii$getTreeGrower();
}
