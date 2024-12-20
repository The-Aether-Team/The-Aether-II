package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.joml.Vector3i;

import java.util.*;

public class GasFeature extends Feature<NoneFeatureConfiguration> {
    public GasFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos pos = context.origin();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();

        List<BlockPos> positions = new ArrayList<>(List.of(pos));
        Set<BlockPos> visited = new HashSet<>();

        while (!positions.isEmpty()) {
            BlockPos storedPos = positions.removeLast();
            for (Direction direction : Direction.values()) {
                BlockPos offsetPos = storedPos.relative(direction);
                if (Vector3i.distance(pos.getX(), 0, pos.getZ(), offsetPos.getX(), 0, offsetPos.getZ()) <= 4 + random.nextInt(5) && level.getBlockState(offsetPos).isAir() && !visited.contains(offsetPos)) {
                    boolean betweenCeiling = false;
                    boolean betweenFloor = false;
                    for (int y = 0; y < 8; y++) {
                        if (level.getBlockState(offsetPos.above(y)).is(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS)) {
                            betweenCeiling = true;
                        }
                        if (level.getBlockState(offsetPos.below(y)).is(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS)) {
                            betweenFloor = true;
                        }
                    }
                    if (betweenCeiling && betweenFloor) {
                        positions.add(offsetPos);
                    }
                }
            }
            if (level.getBlockState(storedPos).isAir()) {
                level.setBlock(storedPos, AetherIIBlocks.GAS.get().defaultBlockState(), 3);
            }
            visited.add(storedPos);
        }

        return true;
    }
}
