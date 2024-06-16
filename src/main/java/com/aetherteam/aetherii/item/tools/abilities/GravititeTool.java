package com.aetherteam.aetherii.item.tools.abilities;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.block.HoveringBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public interface GravititeTool {
    default boolean levitateBlock(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        ItemStack itemStack = context.getItemInHand();
        BlockState blockState = level.getBlockState(blockPos);
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        if (itemStack.getItem() instanceof TieredItem tieredItem) {
            if (player != null) { //todo account for stripping and other block interactions
                if (blockState.getMenuProvider(level, blockPos) == null || player.isShiftKeyDown()) {
                    if ((itemStack.getDestroySpeed(blockState) == tieredItem.getTier().getSpeed() || itemStack.isCorrectToolForDrops(blockState))) {
                        if (blockState.getDestroySpeed(level, blockPos) >= 0.0F && !blockState.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF) && !blockState.is(AetherIITags.Blocks.GRAVITITE_ABILITY_BLACKLIST)) {
                            if (!level.isClientSide()) {
                                HoveringBlockEntity floatingBlockEntity = new HoveringBlockEntity(level, (double) blockPos.getX() + 0.5, blockPos.getY(), (double) blockPos.getZ() + 0.5, blockState);
                                if (blockState.hasBlockEntity()) {
                                    BlockEntity blockEntity = level.getBlockEntity(blockPos);
                                    if (blockEntity != null) {
                                        floatingBlockEntity.blockData = blockEntity.saveWithoutMetadata();
                                    }
                                }
                                floatingBlockEntity.setHoldingPlayer(player);
                                level.addFreshEntity(floatingBlockEntity);
                                level.removeBlockEntity(blockPos);
                                level.removeBlock(blockPos, false);
                                itemStack.hurtAndBreak(4, player, (p) -> p.broadcastBreakEvent(hand));
                            } else {
                                player.swing(hand);
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
