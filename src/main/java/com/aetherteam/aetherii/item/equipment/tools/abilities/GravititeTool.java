package com.aetherteam.aetherii.item.equipment.tools.abilities;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.AbilityBehaviorAttachment;
import com.aetherteam.aetherii.entity.block.HoveringBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;

public interface GravititeTool {
    default boolean levitateBlock(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        ItemStack itemStack = context.getItemInHand();
        BlockState blockState = level.getBlockState(blockPos);
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        if (player != null && player.isShiftKeyDown()) {
            if ((itemStack.isCorrectToolForDrops(blockState))) {
                if (blockState.getDestroySpeed(level, blockPos) >= 0.0F
                        && !blockState.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)
                        && (!blockState.hasProperty(BlockStateProperties.CHEST_TYPE) || blockState.getValue(BlockStateProperties.CHEST_TYPE) == ChestType.SINGLE)
                        && (!blockState.hasProperty(BlockStateProperties.EXTENDED) || !blockState.getValue(BlockStateProperties.EXTENDED))
                        && !blockState.is(AetherIITags.Blocks.GRAVITITE_ABILITY_BLACKLIST)) {
                    AbilityBehaviorAttachment attachment = AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR);
                    if (!attachment.isGravititeHoldingFloatingBlock()) {
                        attachment.setGravititeHoldingFloatingBlock(true);
                        if (!level.isClientSide()) {
                            HoveringBlockEntity floatingBlockEntity = new HoveringBlockEntity(level, (double) blockPos.getX() + 0.5, blockPos.getY(), (double) blockPos.getZ() + 0.5, blockState.getBlock().defaultBlockState());
                            if (blockState.hasBlockEntity()) {
                                BlockEntity blockEntity = level.getBlockEntity(blockPos);
                                if (blockEntity != null) {
                                    floatingBlockEntity.setBlockEntityData(blockEntity.saveWithoutMetadata());
                                }
                            }
                            floatingBlockEntity.setHoldingPlayer(player);
                            level.addFreshEntity(floatingBlockEntity);
                            level.removeBlockEntity(blockPos);
                            level.removeBlock(blockPos, false);
                            itemStack.hurtAndBreak(4, player, entity -> entity.broadcastBreakEvent(hand));
                        } else {
                            player.swing(hand);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
