package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SaplingBlock.class)
public interface SaplingBlockAccessor {
    @Accessor("treeGrower")
    AbstractTreeGrower aether_ii$getTreeGrower();
}
