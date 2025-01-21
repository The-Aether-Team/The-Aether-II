package com.aetherteam.aetherii.block.construction;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.FrictionCapped;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class QuicksoilGlassBlock extends TransparentBlock implements FrictionCapped {
    public QuicksoilGlassBlock(Properties properties) {
        super(properties);
    }

    /**
     * @see FrictionCapped#getCappedFriction(Entity, float)
     */
    @Override
    public float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return this.getCappedFriction(entity, super.getFriction());
    }

    @Override
    protected boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.is(AetherIITags.Blocks.QUICKSOIL_GLASS) || super.skipRendering(state, adjacentBlockState, side);
    }
}
