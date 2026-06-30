package com.aetherteam.aetherii.block;

import com.aetherteam.aetherii.event.AetherEventDispatch;
import com.aetherteam.aetherii.event.FreezeEvent;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.block.IcestoneFreezableRecipe;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;

import java.util.*;

public interface FreezingBlock extends FreezingBehavior<BlockState> {
    /**
     * This magic number comes from b1.7.3 code that checks if the Euclidean distance of a coordinate exceeds 8 for a spherical function.
     */
    float SQRT_8 = Mth.sqrt(8);

    List<Block> cachedBlocks = new ArrayList<>();
    List<Block> cachedResults = new ArrayList<>();

    /**
     * Freezes blocks from one block to another using the {@link AetherIIRecipeTypes#ICESTONE_FREEZABLE} recipe type.
     *
     * @param level  The {@link Level} to freeze the blocks in.
     * @param pos    The {@link BlockPos} the freezing occurred at.
     * @param origin The {@link BlockPos} of the source that is causing the freezing.
     * @param source The {@link ItemStack} that was the source of the freezing.
     * @param flag   The {@link Integer} representing the block placement flag (see {@link net.minecraft.world.level.LevelWriter#setBlock(BlockPos, BlockState, int)}).
     * @return An {@link Integer} 1 if a block was successfully frozen, or a 0 if it wasn't.
     */
    @Override
    default int freezeFromRecipe(Level level, BlockPos pos, BlockPos origin, BlockState source, int flag) {
        if (level instanceof ServerLevel serverLevel) {
            BlockState oldBlockState = level.getBlockState(pos);
            FluidState fluidState = level.getFluidState(pos);
            boolean shouldDestroy = false;
            if (!fluidState.isEmpty() && !oldBlockState.is(fluidState.createLegacyBlock().getBlock()) && !oldBlockState.hasProperty(BlockStateProperties.WATERLOGGED)) { // Breaks a block before freezing if it has a FluidState attached by default (this is different from waterlogging for blocks like Kelp and Seagrass).
                oldBlockState = fluidState.createLegacyBlock();
                shouldDestroy = true;
            }
            if (shouldDestroy) {
                level.destroyBlock(pos, true);
            }
            BlockState finalOldBlockState = oldBlockState;

            IcestoneFreezableRecipe freezableRecipe = null;

            for (IcestoneFreezableRecipe holder : serverLevel.getRecipeManager().getAllRecipesFor(AetherIIRecipeTypes.ICESTONE_FREEZABLE.get())) {
                if (freezableRecipe == null || (freezableRecipe.getBiome().isEmpty() && holder.getBiome().isPresent())) {
                    if (holder.matches(level, pos, finalOldBlockState)) {
                        freezableRecipe = holder;
                    }
                }
            }
            if (freezableRecipe != null) {
                BlockState newBlockState = freezableRecipe.getResultState(oldBlockState);
                CommandFunction.CacheableFunction function = freezableRecipe.getFunction();
                return this.freezeBlockAt(level, pos, origin, oldBlockState, newBlockState, function, source, flag);
            }
        }
        return 0;
    }

    @Override
    default FreezeEvent onFreeze(LevelAccessor level, BlockPos pos, BlockPos origin, BlockState oldBlockState, BlockState newBlockState, BlockState source) {
        return AetherEventDispatch.onBlockFreezeFluid(level, pos, origin, oldBlockState, newBlockState, source);
    }
}