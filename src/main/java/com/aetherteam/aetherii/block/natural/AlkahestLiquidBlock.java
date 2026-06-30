package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.fluid.AlkahestFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;

public class AlkahestLiquidBlock extends VolatileLiquidBlock {
    public AlkahestLiquidBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        this.createHestveil(level, pos);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        FluidState fluidState = level.getFluidState(pos);
        if (level.getBlockState(pos.above()).isAir() && fluidState.isSource()) {
            level.scheduleTick(pos, state.getBlock(), 25);
        }
    }

    public void createHestveil(Level level, BlockPos pos) {
        BlockPos above = pos.above();
        if (level.getBlockState(above).isAir()) {
            level.setBlock(above, AetherIIBlocks.HESTVEIL.get().defaultBlockState(), 3);
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        level.scheduleTick(pos, state.getFluidState().getType(), this.getFluid().getTickDelay(level));
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (this.getFluid() instanceof AlkahestFluid alkahestFluid && level instanceof ServerLevel serverLevel) {
            alkahestFluid.entityInside(state, serverLevel, pos, entity);
        }
        super.entityInside(state, level, pos, entity);
    }
}
