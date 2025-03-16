package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherLeavesBlock;
import com.aetherteam.aetherii.block.natural.BottomedVineBlock;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.mojang.serialization.MapCodec;
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
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class NoiseMossDecorator extends TreeDecorator {
    public static final MapCodec<NoiseMossDecorator> CODEC = MapCodec.unit(NoiseMossDecorator::new);

    public NoiseMossDecorator() { }

    @Override
    public void place(TreeDecorator.Context context) {
        LevelSimulatedReader level = context.level();
        BlockPos origin = context.logs().getFirst();

        int chunkX = origin.getX() - (origin.getX() % 16);
        int chunkZ = origin.getZ() - (origin.getZ() % 16);

        if (level instanceof WorldGenLevel worldGenLevel) {
            HolderGetter<DensityFunction> function = worldGenLevel.holderLookup(Registries.DENSITY_FUNCTION);
            DensityFunction noise =  AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.SNOW_NOISE);
            DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(worldGenLevel.getSeed());
            noise.mapAll(visitor);

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    int xCoord = chunkX + x;
                    int zCoord = chunkZ + z;
                    int yCoord = worldGenLevel.getHeight(Heightmap.Types.MOTION_BLOCKING, xCoord, zCoord);
                    BlockPos groundPos = new BlockPos(xCoord, yCoord, zCoord).below();
                    if (worldGenLevel.getBlockState(groundPos.above()).isAir()) {
                        double snowCalc = noise.compute(new DensityFunction.SinglePointContext(groundPos.getX(), groundPos.getY(), groundPos.getZ()));
                        if (snowCalc >= -1.0F) {
                            if ((context.leaves().contains(groundPos) && worldGenLevel.getBlockState(groundPos).is(AetherIIBlocks.SKYPLANE_LEAVES)) || worldGenLevel.getBlockState(groundPos).is(AetherIIBlocks.WOVEN_SKYROOT_STICKS)) {
                                worldGenLevel.setBlock(groundPos, worldGenLevel.getBlockState(groundPos).setValue(AetherLeavesBlock.MOSSY, AetherIIBlockStateProperties.Mossy.BRYALINN), 2);
                                for (Direction direction : Direction.Plane.HORIZONTAL) {
                                    if (worldGenLevel.getRandom().nextBoolean()) {
                                        BlockPos offsetPos = groundPos.relative(direction);
                                        if (worldGenLevel.getBlockState(offsetPos).isAir()) {
                                            BlockState blockState = AetherIIBlocks.BRYALINN_MOSS_VINES.get().defaultBlockState().setValue(VineBlock.getPropertyForFace(direction.getOpposite()), true).setValue(BottomedVineBlock.AGE, 25 - worldGenLevel.getRandom().nextInt(2));
                                            MossDecorator.addHangingVine(context, offsetPos, blockState);
                                        } else if (worldGenLevel.getBlockState(offsetPos).is(AetherIIBlocks.BRYALINN_MOSS_VINES.get())) {
                                            BlockState blockState = worldGenLevel.getBlockState(offsetPos).setValue(VineBlock.getPropertyForFace(direction.getOpposite()), true).setValue(BottomedVineBlock.AGE, 25 - worldGenLevel.getRandom().nextInt(2));
                                            MossDecorator.addHangingVine(context, offsetPos, blockState);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


//        if (level instanceof WorldGenLevel worldGenLevel) {
//            HolderGetter<DensityFunction> function = worldGenLevel.holderLookup(Registries.DENSITY_FUNCTION);
//            DensityFunction noise =  AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.SNOW_NOISE);
//            DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(worldGenLevel.getSeed());
//            noise.mapAll(visitor);
//
//            for (BlockPos pos : context.leaves()) {
//                BlockPos heightmapPos = context.level().getHeightmapPos(Heightmap.Types.OCEAN_FLOOR, pos);
//                BlockPos relativePos = pos.above();
//                if (heightmapPos.getY() == relativePos.getY()) {
//                    double snowCalc = noise.compute(new DensityFunction.SinglePointContext(relativePos.getX(), relativePos.getY(), relativePos.getZ()));
//                    if (snowCalc >= -0.1F && worldGenLevel.getBlockState(relativePos).isAir() && worldGenLevel.getBlockState(pos).is(AetherIIBlocks.SKYPLANE_LEAVES)) {
//                        worldGenLevel.setBlock(relativePos, AetherIIBlocks.BRYALINN_MOSS_CARPET.get().defaultBlockState(), 2);

//                    }
//                }
//            }
//        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.NOISE_MOSS.get();
    }
}
