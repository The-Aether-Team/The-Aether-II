package com.aetherteam.aetherii.block.natural;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;

public class HangingLeavesBlock extends GrowingPlantHeadBlock implements CaveVines {
    public static final MapCodec<CaveVinesBlock> CODEC = simpleCodec(CaveVinesBlock::new);
    private final DeferredBlock<GrowingPlantBodyBlock> bodyBlock;

    @Override
    public MapCodec<CaveVinesBlock> codec() {
        return CODEC;
    }

    public HangingLeavesBlock(DeferredBlock<GrowingPlantBodyBlock> bodyBlock, BlockBehaviour.Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false, 0.1);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
        this.bodyBlock = bodyBlock;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos attachedToPos = pos.relative(this.growthDirection.getOpposite());
        BlockState attachedToState = level.getBlockState(attachedToPos);
        return this.canAttachTo(attachedToState) && (attachedToState.is(this.getHeadBlock()) || attachedToState.is(this.getBodyBlock()) || this.canSupportBlock(attachedToState) || attachedToState.isFaceSturdy(level, attachedToPos, this.growthDirection));
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return 1;
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    @Override
    protected Block getBodyBlock() {
        return this.bodyBlock.get();
    }

    protected boolean canSupportBlock(BlockState state) {
        return state.is(BlockTags.LEAVES);
    }
}
