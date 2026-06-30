package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.blockentity.MusicBlockEntity;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.EngravedDisc;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class MusicBlock extends BaseEntityBlock {
    public static final BooleanProperty HAS_RECORD = BlockStateProperties.HAS_RECORD;

    public MusicBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HAS_RECORD, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new MusicBlockEntity(worldPosition, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return blockState.getValue(HAS_RECORD) ? createTickerHelper(type, AetherIIBlockEntityTypes.MUSIC_BLOCK.get(), MusicBlockEntity::tick) : null;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getValue(HAS_RECORD)) {
            if (!level.isClientSide() && level.getBlockEntity(pos) instanceof MusicBlockEntity blockEntity) {
                blockEntity.popOutTheItem();
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        ItemStack toInsert = player.getItemInHand(hand);
        EngravedDisc disc = AetherIIDataComponents.get(toInsert, AetherIIDataComponents.ENGRAVED_DISC);
        if (disc == null) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide()) {
            ItemStack inserted = toInsert.copyWithCount(1);
            toInsert.shrink(1);
            if (level.getBlockEntity(pos) instanceof MusicBlockEntity blockEntity) {
                blockEntity.setTheItem(inserted);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            }
            player.awardStat(Stats.PLAY_RECORD);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity by, ItemStack itemStack) {
        super.setPlacedBy(level, pos, state, by, itemStack);
        CompoundTag blockEntityData = BlockItem.getBlockEntityData(itemStack);
        if (blockEntityData != null && blockEntityData.contains("RecordItem")) {
            level.setBlock(pos, state.setValue(HAS_RECORD, true), 2);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof MusicBlockEntity blockEntity) {
                blockEntity.popOutTheItem();
            }
            level.updateNeighbourForOutputSignal(pos, this);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        if (level.getBlockEntity(pos) instanceof MusicBlockEntity blockEntity && blockEntity.getSongPlayer().isPlaying()) {
            return 15;
        }
        return 0;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof MusicBlockEntity blockEntity) {
            return blockEntity.getComparatorOutput();
        }
        return 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HAS_RECORD);
    }
}
