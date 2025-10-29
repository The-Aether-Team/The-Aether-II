package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HangingRotgrowthPlantBlock extends GrowingPlantBodyBlock {
    public static final MapCodec<HangingRotgrowthPlantBlock> CODEC = simpleCodec(HangingRotgrowthPlantBlock::new);
    public static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public MapCodec<HangingRotgrowthPlantBlock> codec() {
        return CODEC;
    }

    public HangingRotgrowthPlantBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false);
    }

    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) AetherIIBlocks.HANGING_ROTGROWTH.get();
    }
}