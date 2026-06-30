package com.aetherteam.aetherii.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;

public class AetherEventDispatch {
    /**
     * @see FreezeEvent.FreezeFromBlock
     */
    public static FreezeEvent.FreezeFromBlock onBlockFreezeFluid(LevelAccessor level, BlockPos pos, BlockPos origin, BlockState fluidState, BlockState blockState, BlockState sourceBlock) {
        FreezeEvent.FreezeFromBlock event = new FreezeEvent.FreezeFromBlock(level, pos, origin, fluidState, blockState, sourceBlock);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }

    /**
     * @see FreezeEvent.FreezeFromItem
     */
    public static FreezeEvent.FreezeFromItem onItemFreezeFluid(LevelAccessor level, BlockPos pos, BlockState fluidState, BlockState blockState, ItemStack sourceItem) {
        FreezeEvent.FreezeFromItem event = new FreezeEvent.FreezeFromItem(level, pos, fluidState, blockState, sourceItem);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }

    /**
     * @see ItemUseConvertEvent
     */
    public static ItemUseConvertEvent onItemUseConvert(@Nullable Player player, LevelAccessor level, BlockPos pos, @Nullable ItemStack stack, BlockState oldState, BlockState newState, RecipeType<?> recipeType) {
        ItemUseConvertEvent event = new ItemUseConvertEvent(player, level, pos, stack, oldState, newState, recipeType);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }
}
