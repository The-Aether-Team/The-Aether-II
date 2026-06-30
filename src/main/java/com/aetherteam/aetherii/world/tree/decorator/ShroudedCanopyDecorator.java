package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.block.natural.BottomedVineBlock;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesConfiguredFeatures;
import com.google.common.collect.HashMultimap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.*;

public class ShroudedCanopyDecorator extends TreeDecorator {
    public static final MapCodec<ShroudedCanopyDecorator> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("canopy_top_state").forGetter((decorator) -> decorator.canopyTopState),
            BlockStateProvider.CODEC.fieldOf("canopy_branch_state").forGetter((decorator) -> decorator.canopyBranchState),
            BlockStateProvider.CODEC.fieldOf("moss_carpet_state").forGetter((decorator) -> decorator.mossCarpetState),
            BlockStateProvider.CODEC.fieldOf("moss_vine_state").forGetter((decorator) -> decorator.mossVineState),
            IntProvider.CODEC.fieldOf("canopy_radius").forGetter((decorator) -> decorator.canopyRadius),
            IntProvider.CODEC.fieldOf("branch_amount").forGetter((decorator) -> decorator.branchAmount),
            IntProvider.CODEC.fieldOf("branch_height").forGetter((decorator) -> decorator.branchHeight),
            Codec.doubleRange(0.0, 1.0).fieldOf("nest_chance").forGetter((decorator) -> decorator.nestChance)
    ).apply(instance, ShroudedCanopyDecorator::new));

    private final BlockStateProvider canopyTopState;
    private final BlockStateProvider canopyBranchState;
    private final BlockStateProvider mossCarpetState;
    private final BlockStateProvider mossVineState;
    private final IntProvider canopyRadius;
    private final IntProvider branchAmount;
    private final IntProvider branchHeight;
    private final double nestChance;

    public ShroudedCanopyDecorator(BlockStateProvider canopyTopState, BlockStateProvider canopyBranchState, BlockStateProvider mossCarpetState, BlockStateProvider mossVineState, IntProvider canopyRadius, IntProvider branchAmount, IntProvider branchHeight, double nestChance) {
        this.canopyTopState = canopyTopState;
        this.canopyBranchState = canopyBranchState;
        this.mossCarpetState = mossCarpetState;
        this.mossVineState = mossVineState;
        this.canopyRadius = canopyRadius;
        this.branchAmount = branchAmount;
        this.branchHeight = branchHeight;
        this.nestChance = nestChance;
    }

    @Override
    public void place(Context context) {
        HashMultimap<Coordinate, BlockPos> trunks = HashMultimap.create();
        for (BlockPos pos : context.logs()) {
            Coordinate coordinate = new Coordinate(pos.getX(), pos.getZ());
            trunks.put(coordinate, pos);
        }
        HashMap<Coordinate, BlockPos> topPoints = new HashMap<>();
        trunks.forEach((coordinate, pos) -> {
            if (topPoints.get(coordinate) == null) {
                topPoints.put(coordinate, pos);
            } else if (topPoints.get(coordinate).getY() < pos.getY()) {
                topPoints.replace(coordinate, pos);
            }
        });
        List<BlockPos> topPositions = new ArrayList<>(topPoints.values());
        Optional<Integer> y = topPositions.stream().min(Comparator.comparingDouble(Vec3i::getY)).map(BlockPos::getY);
        if (topPositions.size() > 2) {
            double centroidX = 0, centroidZ = 0;

            for (BlockPos pos : topPositions) {
                centroidX += pos.getX();
                centroidZ += pos.getZ();
            }

            int centerX = (int) Math.round(centroidX / topPositions.size());
            int centerZ = (int) Math.round(centroidZ / topPositions.size());

            BlockPos center = new BlockPos(centerX, y.get(), centerZ);

            int radius = this.canopyRadius.sample(context.random());

            this.createCircle(context, center, radius + 1, this.canopyTopState.getState(context.random(), center), false);

            this.createBranches(context, center.below(), List.copyOf(topPoints.keySet()), radius - 1, this.branchAmount.sample(context.random()), this.canopyBranchState.getState(context.random(), center.below()));

            this.createCircle(context, center.below(), radius, this.canopyTopState.getState(context.random(), center.below()), false);
            this.createCircle(context, center.below(2), radius - 1, this.canopyTopState.getState(context.random(), center.below(2)), true);

            if (context.level() instanceof WorldGenLevel worldGenLevel && context.random().nextDouble() <= this.nestChance) {
                ChunkGenerator chunk = worldGenLevel.getLevel().getChunkSource().getGenerator();
                ConfiguredFeature<?, ?> nest = Objects.requireNonNull(worldGenLevel.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE).get(HolyIslesConfiguredFeatures.MOA_NEST).orElse(null)).value();
                nest.place(worldGenLevel, chunk, context.random(), center.above(2));
            }
        }
    }

    private void createCircle(Context context, BlockPos center, int radius, BlockState blockState, boolean outline) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                double pow = Math.pow(z, 2) + Math.pow(x, 2);
                if (outline ? pow == Math.pow(radius + context.random().nextInt(2), 2) : pow <= Math.pow(radius + context.random().nextInt(2), 2)) {
                    BlockPos offset = center.offset(x, 0, z);
                    if (context.isAir(offset)) {
                        context.setBlock(offset, blockState);
                    }
                }
            }
        }
    }

    private void createBranches(Context context, BlockPos center, List<Coordinate> goals, int radius, int amount, BlockState blockState) {
        for (BlockPos start : BlockPos.randomBetweenClosed(context.random(), amount, center.getX() - radius, center.getY(), center.getZ() - radius, center.getX() + radius, center.getY() + 1, center.getZ() + radius)) {
            this.createBranch(context, start, goals, this.branchHeight.sample(context.random()), blockState);
        }
    }

    private void createBranch(Context context, BlockPos start, List<Coordinate> goals, int height, BlockState blockState) {
        Coordinate target = null;
        for (Coordinate coordinate : goals) {
            if (target == null) target = coordinate;

            BlockPos targetPos = new BlockPos(target.x(), 0, target.z());
            BlockPos coordinatePos = new BlockPos(coordinate.x(), 0, coordinate.z());

            if (coordinatePos.distSqr(start) < targetPos.distSqr(start)) {
                target = coordinate;
            }
        }
        if (target != null) {
            List<BlockPos> branchPositions = new ArrayList<>();

            if (context.level().isStateAtPosition(new BlockPos(target.x(), start.below().below(height).getY(), target.z()), (blockState1) -> blockState1.is(BlockTags.LOGS))) {
                int xDist = target.x() - start.getX();
                int zDist = target.z() - start.getZ();

                for (int y = 0; y < height; y++) {
                    start = start.below(1);
                    branchPositions.add(start);
                }

                if (context.random().nextBoolean()) {
                    for (int x = 0; x < Math.abs(xDist); x++) {
                        start = start.offset(xDist < 0 ? -1 : 1, 0, 0);
                        branchPositions.add(start);
                        if (context.random().nextInt(4) == 0) {
                            start = start.below(1);
                            branchPositions.add(start);
                        }
                    }
                    for (int z = 0; z < Math.abs(zDist); z++) {
                        start = start.offset(0, 0, zDist < 0 ? -1 : 1);
                        branchPositions.add(start);
                        if (context.random().nextInt(4) == 0) {
                            start = start.below(1);
                            branchPositions.add(start);
                        }
                    }
                } else {
                    for (int z = 0; z < Math.abs(zDist); z++) {
                        start = start.offset(0, 0, zDist < 0 ? -1 : 1);
                        branchPositions.add(start);
                        if (context.random().nextInt(4) == 0) {
                            start = start.below(1);
                            branchPositions.add(start);
                        }
                    }
                    for (int x = 0; x < Math.abs(xDist); x++) {
                        start = start.offset(xDist < 0 ? -1 : 1, 0, 0);
                        branchPositions.add(start);
                        if (context.random().nextInt(4) == 0) {
                            start = start.below(1);
                            branchPositions.add(start);
                        }
                    }
                }

                branchPositions.removeIf(pos -> context.level().isStateAtPosition(pos, state -> state.is(BlockTags.LOGS)));

                for (BlockPos pos : branchPositions) {
                    if (!this.isValidBranchPos(context, pos)) {
                        return;
                    }
                }

                for (BlockPos pos : branchPositions) {
                    context.setBlock(pos, blockState);
                }

                for (BlockPos pos : branchPositions) {
                    if (context.random().nextBoolean()) {
                        if (context.isAir(pos.above())) {
                            context.setBlock(pos.above(), this.mossCarpetState.getState(context.random(), pos.above()));
                        }
                    }

                    if (context.random().nextInt(3) == 0) {
                        if (context.isAir(pos.above())) {
                            context.setBlock(pos.above(), this.mossCarpetState.getState(context.random(), pos.above()));
                        }
                        this.createVines(context, pos);
                    }
                }
            }
        }
    }

    private boolean isValidBranchPos(Context context, BlockPos pos) {
        return context.isAir(pos) || context.level().isStateAtPosition(pos, state -> state.is(BlockTags.LEAVES));
    }

    private void createVines(Context context, BlockPos pos) {
        for (Direction offsetDirection : Direction.Plane.HORIZONTAL.stream().toList()) {
            BlockPos newPos = pos.relative(offsetDirection);
            if (context.isAir(newPos)) {
                Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(context.random());
                if (context.level().isStateAtPosition(newPos.relative(direction), BlockBehaviour.BlockStateBase::isSolid)) {
                    BlockState vineState = this.mossVineState.getState(context.random(), newPos);
                    vineState = vineState.setValue(VineBlock.getPropertyForFace(direction), true);
                    if (context.random().nextInt(4) == 0) {
                        vineState = vineState.setValue(BottomedVineBlock.AGE, 25);
                    } else {
                        vineState = vineState.setValue(BottomedVineBlock.AGE, 20 + context.random().nextInt(5));
                    }
                    BlockState finalBlockState = vineState;
                    if (context.level().isStateAtPosition(newPos.above(), (state) -> !state.is(finalBlockState.getBlock()) || (state.hasProperty(BottomedVineBlock.AGE) && state.getValue(BottomedVineBlock.AGE) < 25))) {
                        MossDecorator.addHangingVine(context, newPos, vineState);
                    }
                }
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.SHROUDED_CANOPY.get();
    }

    private record Coordinate(int x, int z) { }
}
