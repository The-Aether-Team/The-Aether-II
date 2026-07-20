package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class LitLanternBlock extends LanternBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public LitLanternBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!state.getValue(LIT)) {
            if (stack.is(AetherIIItems.AMBROSIUM_SHARD)) {
                BlockState litState = state.setValue(LIT, true);
                level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                level.setBlock(pos, litState, 11);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        } else {
            if (stack.isEmpty() && player.isShiftKeyDown()) {
                BlockState litState = state.setValue(LIT, false);
                level.levelEvent(null, 1009, pos, 0);
                level.setBlock(pos, litState, 11);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HANGING, LIT, WATERLOGGED);
    }
}
