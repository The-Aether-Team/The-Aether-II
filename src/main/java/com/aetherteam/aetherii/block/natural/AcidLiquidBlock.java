package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.fluid.AcidFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

public class AcidLiquidBlock extends VolatileLiquidBlock {
    public AcidLiquidBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        level.scheduleTick(pos, state.getFluidState().getType(), this.fluid.getTickDelay(level));
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (this.fluid instanceof AcidFluid acidFluid && level instanceof ServerLevel serverLevel) {
            acidFluid.entityInside(state, serverLevel, pos, entity);
        }
        super.entityInside(state, level, pos, entity);
    }
}
