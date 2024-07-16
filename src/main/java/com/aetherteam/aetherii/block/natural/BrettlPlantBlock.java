package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BrettlPlantBlock extends GrowingPlantBodyBlock {
    public static final MapCodec<BrettlPlantBlock> CODEC = simpleCodec(BrettlPlantBlock::new);
    public static final BooleanProperty GROWN = AetherIIBlockStateProperties.BRETTL_GROWN;
    public static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

    @Override
    public MapCodec<BrettlPlantBlock> codec() {
        return CODEC;
    }

    public BrettlPlantBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.UP, SHAPE, false);
        this.registerDefaultState(this.stateDefinition.any().setValue(GROWN, Boolean.FALSE));
    }

    protected BlockState updateHeadAfterConvertedFromBody(BlockState head, BlockState body) {
        return head;
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) AetherIIBlocks.BRETTL_PLANT_TIP.get();
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos checkedPos = pos.relative(this.growthDirection.getOpposite());
        BlockState checkedState = level.getBlockState(checkedPos);
        return this.canAttachTo(checkedState) && (checkedState.is(this.getHeadBlock()) || checkedState.is(this.getBodyBlock()) || checkedState.is(AetherIITags.Blocks.BRETTL_PLANT_SURVIVES_ON));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GROWN);
    }
}