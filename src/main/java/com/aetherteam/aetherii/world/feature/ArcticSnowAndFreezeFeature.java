package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherGrassBlock;
import com.aetherteam.aetherii.block.natural.Snowable;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.SnowyBlock;
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

        HolderGetter<DensityFunction> function = context.level().holderLookup(Registries.DENSITY_FUNCTION);
        DensityFunction noise =  AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.ENVIRONMENTAL_SNOW);
        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(level.getSeed());
        noise.mapAll(visitor);

        ChunkPos chunkPos = ChunkPos.containing(context.origin());

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int xCoord = chunkPos.getMinBlockX() + x;
                int zCoord = chunkPos.getMinBlockZ() + z;
                int yCoord = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, xCoord, zCoord);
                BlockPos posAbove = new BlockPos(xCoord, yCoord, zCoord);
                BlockPos posBelow = posAbove.below();
                Biome biome = level.getBiome(posAbove).value();

                double snowCalc = noise.compute(new DensityFunction.SinglePointContext(xCoord, yCoord, zCoord));
                if (snowCalc < 0.5) {
                    BlockState state = level.getBlockState(posAbove);
                    BlockState ground = level.getBlockState(posBelow);
                    boolean snowed = false;
                    if (!ground.is(AetherIITags.Blocks.CANNOT_SUPPORT_SNOWFALL)) {
                        if (AetherGrassBlock.plantNotSnowed(state) && state.getBlock() instanceof Snowable snowable) {
                            level.setBlock(posAbove, snowable.setSnowy(state), 2);
                            snowed = true;
                        } else if (!state.isSolid()) {
                            level.setBlock(posAbove, AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState(), 2);
                            snowed = true;
                        }
                    }
                    if (snowed) {
                        if (ground.hasProperty(SnowyBlock.SNOWY)) {
                            level.setBlock(posBelow, ground.setValue(SnowyBlock.SNOWY, Boolean.TRUE), 2);
                        }
                    }
                }
                if (biome.shouldFreeze(level, posBelow, false)) {
                    level.setBlock(posBelow, AetherIIBlocks.ARCTIC_ICE.get().defaultBlockState(), 2);
                }
            }
        }
        return true;
    }
}