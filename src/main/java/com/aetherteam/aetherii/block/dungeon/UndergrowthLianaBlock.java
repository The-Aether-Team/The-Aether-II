package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UndergrowthLianaBlock extends GrowingPlantHeadBlock {
    public static final MapCodec<UndergrowthLianaBlock> CODEC = simpleCodec(UndergrowthLianaBlock::new);
    protected static final VoxelShape SHAPE = Block.box(4.0, 9.0, 4.0, 12.0, 16.0, 12.0);

    public MapCodec<UndergrowthLianaBlock> codec() {
        return CODEC;
    }

    public UndergrowthLianaBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false, 0.1);
    }

    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return 1;
    }

    protected Block getBodyBlock() {
        return AetherIIBlocks.UNDERGROWTH_LIANA_PLANT.get();
    }

    protected boolean canGrowInto(BlockState state) {
        return state.isAir();
    }
}