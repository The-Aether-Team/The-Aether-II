package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherLeavesBlock;
import com.aetherteam.aetherii.block.natural.BottomedVineBlock;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesBiomes;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TreeMossCoverFeature extends Feature<NoneFeatureConfiguration> {
    public TreeMossCoverFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        LevelSimulatedReader level = context.level();
        BlockPos origin = context.origin();

        int chunkX = origin.getX() - (origin.getX() % 16);
        int chunkZ = origin.getZ() - (origin.getZ() % 16);

        if (level instanceof WorldGenLevel worldGenLevel) {
            HolderGetter<DensityFunction> function = worldGenLevel.holderLookup(Registries.DENSITY_FUNCTION);
            DensityFunction noise =  AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.ENVIRONMENTAL_TREE_MOSS);
            DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(worldGenLevel.getSeed());
            noise.mapAll(visitor);

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    int xCoord = chunkX + x;
                    int zCoord = chunkZ + z;
                    int yCoord = worldGenLevel.getHeight(Heightmap.Types.MOTION_BLOCKING, xCoord, zCoord);
                    BlockPos groundPos = new BlockPos(xCoord, yCoord, zCoord).below();
                    if (worldGenLevel.getBiome(groundPos).is(HolyIslesBiomes.SHROUDED_FOREST) && worldGenLevel.getBlockState(groundPos.above()).isAir()) {
                        double calc = noise.compute(new DensityFunction.SinglePointContext(groundPos.getX(), groundPos.getY(), groundPos.getZ()));
                        if (calc >= 0.05F) {
                            if ((worldGenLevel.getBlockState(groundPos).is(AetherIIBlocks.SKYPLANE_LEAVES.get())) || worldGenLevel.getBlockState(groundPos).is(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get())) {
                                worldGenLevel.setBlock(groundPos, worldGenLevel.getBlockState(groundPos).setValue(AetherLeavesBlock.MOSSY, AetherIIBlockStateProperties.Mossy.BRYALINN), 2);
                                for (Direction direction : Direction.Plane.HORIZONTAL) {
                                    if (worldGenLevel.getRandom().nextBoolean()) {
                                        BlockPos offsetPos = groundPos.relative(direction);
                                        if (worldGenLevel.getBlockState(offsetPos).isAir()) {
                                            BlockState blockState = AetherIIBlocks.BRYALINN_MOSS_VINES.get().defaultBlockState().setValue(VineBlock.getPropertyForFace(direction.getOpposite()), true).setValue(BottomedVineBlock.AGE, 25 - worldGenLevel.getRandom().nextInt(2));
                                            addHangingVine(context, offsetPos, blockState);
                                        } else if (worldGenLevel.getBlockState(offsetPos).is(AetherIIBlocks.BRYALINN_MOSS_VINES.get())) {
                                            BlockState blockState = worldGenLevel.getBlockState(offsetPos).setValue(VineBlock.getPropertyForFace(direction.getOpposite()), true).setValue(BottomedVineBlock.AGE, 25 - worldGenLevel.getRandom().nextInt(2));
                                            addHangingVine(context, offsetPos, blockState);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public static void addHangingVine(FeaturePlaceContext<NoneFeatureConfiguration> context, BlockPos pos, BlockState blockState) {
        context.level().setBlock(pos, blockState, 3);
        int i = 10;

        for (BlockPos blockpos = pos.below(); context.level().getBlockState(blockpos).isAir() && i > 0; i--) {
            if (blockState.getValue(BottomedVineBlock.AGE) + 1 <= 25) {
                blockState = blockState.setValue(BottomedVineBlock.AGE, blockState.getValue(BottomedVineBlock.AGE) + 1);
                context.level().setBlock(blockpos, blockState, 3);
                blockpos = blockpos.below();
            } else {
                break;
            }
        }
    }
}
