package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BrettlPlantBlock extends GrowingPlantBodyBlock {
    public static final MapCodec<BrettlPlantBlock> CODEC = simpleCodec(BrettlPlantBlock::new);
    public static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

    @Override
    public MapCodec<BrettlPlantBlock> codec() {
        return CODEC;
    }

    public BrettlPlantBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.UP, SHAPE, false);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) AetherIIBlocks.BRETTL_PLANT_TIP.get();
    }
}