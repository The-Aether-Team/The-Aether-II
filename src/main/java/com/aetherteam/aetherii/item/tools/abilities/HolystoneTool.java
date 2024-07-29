package com.aetherteam.aetherii.item.tools.abilities;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface HolystoneTool {
    default void dropAmbrosium(Player player, Level level, BlockPos pos, ItemStack stack, BlockState state) {
        if (!level.isClientSide() && state.getDestroySpeed(level, pos) > 0 && stack.isCorrectToolForDrops(state) && (player.getRandom().nextInt(8) == 0 || state.is(AetherIITags.Blocks.HOLYSTONE_ABILITY_GUARANTEED))) {
            ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(AetherIIItems.AMBROSIUM_SHARD.get()));
            level.addFreshEntity(itemEntity);
        }
    }
}
