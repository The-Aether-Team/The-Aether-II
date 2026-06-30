package com.aetherteam.aetherii.item.equipment.accessories.abilities;

import com.aetherteam.aetherii.block.FreezingBehavior;
import com.aetherteam.aetherii.event.AetherEventDispatch;
import com.aetherteam.aetherii.event.FreezeEvent;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.block.AccessoryFreezableRecipe;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;

import java.util.Optional;

public interface FreezingAccessory extends FreezingBehavior<ItemStack> {
    /**
     * Freezes blocks around the wearer in a radius of 1.9 as long as they aren't flying or in spectator. This also damages the Ice accessory for every 4 blocks frozen.
     *
     * @param wearer The {@link LivingEntity wearer} of the accessory.
     * @param stack  The accessory {@link ItemStack}.
     */
    default void freezeTick(LivingEntity wearer, ItemStack stack) {
        if (wearer instanceof Player player && (player.getAbilities().flying || player.isSpectator())) {
            return;
        }

        int damage = this.freezeBlocks(wearer.level(), wearer.blockPosition(), stack, 1.9F);

        if (!(wearer.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        if (!stack.is(AetherIIItems.ICESTONE_PENDANT.get())) {
            return;
        }
        if (!(wearer instanceof ServerPlayer serverPlayer)) {
            return;
        }

        ItemStack copyStack = stack.copy();
        stack.hurtAndBreak(damage / 3, wearer, entity -> AccessoryUtil.breakAccessory(copyStack.getItem(), copyStack, serverPlayer));
    }

    /**
     * Freezes blocks from one block to another using the {@link AetherIIRecipeTypes#ACCESSORY_FREEZABLE} recipe type.
     *
     * @param level  The {@link Level} to freeze the blocks in.
     * @param pos    The {@link BlockPos} the freezing occurred at.
     * @param origin The {@link BlockPos} of the source that is causing the freezing.
     * @param source The {@link ItemStack} that was the source of the freezing.
     * @param flag   The {@link Integer} representing the block placement flag (see {@link net.minecraft.world.level.LevelWriter#setBlock(BlockPos, BlockState, int)}).
     * @return An {@link Integer} 1 if a block was successfully frozen, or a 0 if it wasn't.
     */
    @Override
    default int freezeFromRecipe(Level level, BlockPos pos, BlockPos origin, ItemStack source, int flag) {
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

            AccessoryFreezableRecipe freezableRecipe = null;

            for (AccessoryFreezableRecipe holder : serverLevel.getRecipeManager().getAllRecipesFor(AetherIIRecipeTypes.ACCESSORY_FREEZABLE.get())) {
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
    default FreezeEvent onFreeze(LevelAccessor level, BlockPos pos, BlockPos origin, BlockState oldBlockState, BlockState newBlockState, ItemStack source) {
        return AetherEventDispatch.onItemFreezeFluid(level, pos, oldBlockState, newBlockState, source);
    }
}
