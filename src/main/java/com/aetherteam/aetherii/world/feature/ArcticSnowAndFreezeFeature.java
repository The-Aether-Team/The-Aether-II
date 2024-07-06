package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherGrassBlock;
import com.aetherteam.aetherii.block.natural.Snowable;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ArcticSnowAndFreezeFeature extends Feature<NoneFeatureConfiguration> {
    public ArcticSnowAndFreezeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        BlockPos.MutableBlockPos posAbove = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos posBelow = new BlockPos.MutableBlockPos();

        for(int i = 0; i < 16; ++i) {
            for(int j = 0; j < 16; ++j) {
                int k = pos.getX() + i;
                int l = pos.getZ() + j;
                int heightMap = level.getHeight(Heightmap.Types.MOTION_BLOCKING, k, l);
                posAbove.set(k, heightMap, l);
                posBelow.set(posAbove).move(Direction.DOWN, 1);
                Biome biome = level.getBiome(posAbove).value();
                if (biome.shouldFreeze(level, posBelow, false)) {
                    level.setBlock(posBelow, AetherIIBlocks.ARCTIC_ICE.get().defaultBlockState(), 2);
                }

                if (AetherGrassBlock.shouldSnow(biome, level, posAbove)) {
                    BlockState state = level.getBlockState(posAbove);
                    BlockState ground = level.getBlockState(posBelow);
                    if (AetherGrassBlock.plantNotSnowed(state) && state.getBlock() instanceof Snowable snowable) {
                        level.setBlock(posAbove, snowable.setSnowy(state), 2);
                    } else {
                        level.setBlock(posAbove, AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState(), 2);
                    }
                    if (ground.hasProperty(SnowyDirtBlock.SNOWY)) {
                        level.setBlock(posBelow, ground.setValue(SnowyDirtBlock.SNOWY, Boolean.TRUE), 2);
                    }
                }
            }
        }
        return true;
    }
}