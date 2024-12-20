package com.aetherteam.aetherii.block.natural;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public class AetherMossBlock extends Block implements BonemealableBlock {
    public static final MapCodec<AetherMossBlock> CODEC = RecordCodecBuilder.mapCodec(
            p_368425_ -> p_368425_.group(
                            ResourceKey.codec(Registries.CONFIGURED_FEATURE).fieldOf("feature").forGetter(p_304367_ -> p_304367_.mossFeature),
                            propertiesCodec()
                    )
                    .apply(p_368425_, AetherMossBlock::new)
    );
    private final ResourceKey<ConfiguredFeature<?, ?>> mossFeature;

    @Override
    public MapCodec<AetherMossBlock> codec() {
        return CODEC;
    }

    public AetherMossBlock(ResourceKey<ConfiguredFeature<?, ?>> mossFeature, BlockBehaviour.Properties properties) {
        super(properties);
        this.mossFeature = mossFeature;
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
        // Neo: Fire the BlockGrowFeatureEvent and change the ifPresent call to use the event's result.
        var featureHolder = this.getFeature(level).orElse(null);
        Optional.ofNullable(featureHolder).ifPresent(p_256352_ -> p_256352_.value().place(level, level.getChunkSource().getGenerator(), random, pos.above()));
    }

    private Optional<? extends Holder<ConfiguredFeature<?, ?>>> getFeature(LevelReader level) {
        return level.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE).get(this.mossFeature);
    }


    @Override
    public BonemealableBlock.Type getType() {
        return BonemealableBlock.Type.NEIGHBOR_SPREADER;
    }
}