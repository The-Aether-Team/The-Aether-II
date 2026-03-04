package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.blockentity.SkyrootBedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Util;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class SkyrootBedBlock extends BedBlock {
    private static final Map<Direction, VoxelShape> HEAD_SHAPES = Util.make(() -> {
        VoxelShape headboard1 = Block.box(0.0F, 3.0F, 0.0F, 16.0F, 13.0F, 2.0F);
        VoxelShape headboard2 = Block.box(1.0F, 13.0F, 0.0F, 15.0F, 15.0F, 2.0F);
        VoxelShape side1 = Block.box(0.0F, 0.0F, 0.0F, 2.0F, 3.0F, 16.0F);
        VoxelShape side2 = Block.box(14.0F, 0.0F, 0.0F, 16.0F, 3.0F, 16.0F);
        return Shapes.rotateHorizontal(Shapes.or(Block.column(16.0F, 3.0F, 9.0F), headboard1, headboard2, side1, side2));
    });
    private static final Map<Direction, VoxelShape> FOOT_SHAPES = Util.make(() -> {
        VoxelShape side1 = Block.box(0.0F, 0.0F, 0.0F, 2.0F, 3.0F, 16.0F);
        VoxelShape side2 = Block.box(14.0F, 0.0F, 0.0F, 16.0F, 3.0F, 16.0F);
        return Shapes.rotateHorizontal(Shapes.or(Block.column(16.0F, 3.0F, 9.0F), side1, side2));
    });

    public SkyrootBedBlock(DyeColor dyeColor, Properties properties) {
        super(dyeColor, properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(PART, BedPart.FOOT).setValue(OCCUPIED, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SkyrootBedBlockEntity(pos, state);
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(PART) == BedPart.HEAD) {
            return HEAD_SHAPES.get(getConnectedDirection(state).getOpposite());
        } else {
            return FOOT_SHAPES.get(getConnectedDirection(state).getOpposite());
        }
    }
}
