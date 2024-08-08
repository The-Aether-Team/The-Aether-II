package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherGrassBlock;
import com.aetherteam.aetherii.block.natural.Snowable;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
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
        RandomSource random = context.random();

        HolderGetter<DensityFunction> function = context.level().holderLookup(Registries.DENSITY_FUNCTION);
        DensityFunction noise =  AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.SNOW_NOISE);
        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(level.getSeed());
        noise.mapAll(visitor);

        BlockPos.MutableBlockPos posAbove = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos posBelow = new BlockPos.MutableBlockPos();

        int chunkX = context.origin().getX() - (context.origin().getX() % 16);
        int chunkZ = context.origin().getZ() - (context.origin().getZ() % 16);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int xCoord = chunkX + x;
                int zCoord = chunkZ + z;
                int yCoord = level.getHeight(Heightmap.Types.MOTION_BLOCKING, xCoord, zCoord);
                posAbove.set(xCoord, yCoord, zCoord);
                posBelow.set(posAbove).move(Direction.DOWN, 1);
                Biome biome = level.getBiome(posAbove).value();

                double cloudCalc = noise.compute(new DensityFunction.SinglePointContext(xCoord, yCoord, zCoord));
                if (cloudCalc >= 0.25 || (cloudCalc >= 0.1 && random.nextInt(8) > 0)) {
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
        }
        return true;
    }
}