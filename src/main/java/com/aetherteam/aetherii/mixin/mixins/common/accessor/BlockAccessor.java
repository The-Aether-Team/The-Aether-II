package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Block.class)
public interface BlockAccessor {
    @Mutable
    @Accessor("stateDefinition")
    void aether_ii$setStateDefinition(StateDefinition<Block, BlockState> stateDefinition);
}
