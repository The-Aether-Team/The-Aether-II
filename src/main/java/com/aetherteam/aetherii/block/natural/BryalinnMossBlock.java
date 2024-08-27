package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsConfiguredFeatures;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MossBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BryalinnMossBlock extends Block implements BonemealableBlock {
    public static final MapCodec<MossBlock> CODEC = simpleCodec(MossBlock::new);

    @Override
    public MapCodec<MossBlock> codec() {
        return CODEC;
    }

    public BryalinnMossBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.registryAccess()
                .registry(Registries.CONFIGURED_FEATURE)
                .flatMap(p_258973_ -> p_258973_.getHolder(HighlandsConfiguredFeatures.BRYALINN_MOSS_FLOOR))
                .ifPresent(p_255669_ -> p_255669_.value().place(level, level.getChunkSource().getGenerator(), random, pos.above()));
    }

    @Override
    public BonemealableBlock.Type getType() {
        return BonemealableBlock.Type.NEIGHBOR_SPREADER;
    }
}