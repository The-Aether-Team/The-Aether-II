package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public abstract class GroundTrapBlock extends BaseEntityBlock {
    public static final BooleanProperty LOCKED = BlockStateProperties.LOCKED;
    public static final EnumProperty<AetherIIBlockStateProperties.TrapState> TRAP_STATE = AetherIIBlockStateProperties.TRAP_STATE;

    public GroundTrapBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(LOCKED, false).setValue(TRAP_STATE, AetherIIBlockStateProperties.TrapState.LOADED));
    }
    @Override
    public boolean canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        return !state.getValue(LOCKED) && super.canEntityDestroy(state, level, pos, entity);
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        if (state.getValue(LOCKED)) {
            return 0.0F;
        }
        return super.getDestroyProgress(state, player, level, pos);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        if (!state.getValue(LOCKED)) {
            if (entity instanceof Player && state.getValue(TRAP_STATE) == AetherIIBlockStateProperties.TrapState.LOADED) {
                level.setBlock(pos, level.getBlockState(pos).setValue(TRAP_STATE, AetherIIBlockStateProperties.TrapState.TRIGGERED), 1 | 2);
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.playSound(null, pos, AetherIISoundEvents.BLOCK_GROUND_TRAP_TRIGGER.get(), SoundSource.BLOCKS, 0.5F, level.getRandom().nextFloat() * 0.1F + 0.9F);
                }
            }
        }
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(TRAP_STATE) == AetherIIBlockStateProperties.TrapState.SPAWNED ? super.getLightEmission(state, level, pos) : 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LOCKED, TRAP_STATE);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return level.getBlockState(pos).getValue(TRAP_STATE) == AetherIIBlockStateProperties.TrapState.SPAWNED ? 15 : 0;
    }
}
