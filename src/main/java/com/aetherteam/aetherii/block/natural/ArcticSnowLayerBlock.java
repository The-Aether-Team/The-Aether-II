package com.aetherteam.aetherii.block.natural;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

public class ArcticSnowLayerBlock extends SnowLayerBlock {
    public ArcticSnowLayerBlock(Properties p_56585_) {
        super(p_56585_);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if (state.getBlock() instanceof Snowable snowy && !snowy.isSnowy(state)) {
            return state.setValue(BlockStateProperties.SNOWY, true);
        }
        return super.getStateForPlacement(context);
    }
}
