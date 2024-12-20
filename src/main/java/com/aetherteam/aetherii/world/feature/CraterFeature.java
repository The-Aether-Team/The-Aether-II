package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CraterFeature extends Feature<NoneFeatureConfiguration> {
    public CraterFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos pos = context.origin();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();

        int radius = UniformInt.of(4, 5).sample(random);
        for (int x = -radius; x < radius; x++) {
            for (int z = -radius; z < radius; z++) {
                for (int y = -radius; y < radius; y++) {
                    int volume = x * x + y * y + z * z;
                    int radiusSquared = (radius - 1) * (radius - 1);
                    int radiusOutlineSquared = radius * radius;
                    if (volume <= radiusOutlineSquared) {
                        BlockPos offsetPos = pos.offset(x, y, z);
                        if (y < 0) {
                            if (volume >= radiusSquared) {
                                if (!level.getBlockState(offsetPos).is(Blocks.WATER)) {
                                    BlockState state = random.nextInt(4) == 0 ? AetherIIBlocks.IRRADIATED_HOLYSTONE.get().defaultBlockState() : AetherIIBlocks.COARSE_AETHER_DIRT.get().defaultBlockState();
                                    level.setBlock(offsetPos, state, 3);
                                }
                            } else {
                                level.setBlock(offsetPos, Blocks.WATER.defaultBlockState(), 3);
                            }
                        } else {
                            level.setBlock(offsetPos, Blocks.AIR.defaultBlockState(), 3);
                        }
                        if (x == 0 && z == 0 && y == -radius + 2) {
                            level.setBlock(offsetPos, AetherIIBlocks.IRRADIATED_DUST_BLOCK.get().defaultBlockState(), 3);
                        }
                    }
                }
            }
        }

        return true;
    }
}
