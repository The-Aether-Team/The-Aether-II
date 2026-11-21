package com.aetherteam.aetherii.block.natural;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;

public class HangingLeavesPlantBlock extends GrowingPlantBodyBlock implements CaveVines {
    public static final MapCodec<CaveVinesPlantBlock> CODEC = simpleCodec(CaveVinesPlantBlock::new);
    private final DeferredBlock<GrowingPlantHeadBlock> headBlock;

    @Override
    public MapCodec<CaveVinesPlantBlock> codec() {
        return CODEC;
    }

    public HangingLeavesPlantBlock(DeferredBlock<GrowingPlantHeadBlock> headBlock, BlockBehaviour.Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false);
        this.headBlock = headBlock;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos attachedToPos = pos.relative(this.growthDirection.getOpposite());
        BlockState attachedToState = level.getBlockState(attachedToPos);
        return this.canAttachTo(attachedToState) && (attachedToState.is(this.getHeadBlock()) || attachedToState.is(this.getBodyBlock()) || this.canSupportBlock(attachedToState) || attachedToState.isFaceSturdy(level, attachedToPos, this.growthDirection));
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return this.headBlock.get();
    }

    protected boolean canSupportBlock(BlockState state) {
        return state.is(BlockTags.LEAVES);
    }
}
