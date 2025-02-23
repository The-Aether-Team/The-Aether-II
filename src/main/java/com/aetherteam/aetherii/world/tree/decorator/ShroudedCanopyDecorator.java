package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.BottomedVineBlock;
import com.aetherteam.aetherii.block.natural.MossFlowersBlock;
import com.google.common.collect.HashMultimap;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.BlockTypes;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.common.Tags;

import java.util.*;

public class ShroudedCanopyDecorator extends TreeDecorator { //todo clean up
    public static final MapCodec<ShroudedCanopyDecorator> CODEC = MapCodec.unit(ShroudedCanopyDecorator::new);

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

            int radius = context.random().nextInt(4) + 2;
            this.createCircle(context, center.below(2), radius - 1, AetherIIBlocks.TANGLED_BRANCHES.get().defaultBlockState(), true);
            this.createCircle(context, center.below(), radius, AetherIIBlocks.TANGLED_BRANCHES.get().defaultBlockState(), false);
            this.createCircle(context, center, radius + 1, AetherIIBlocks.WOVEN_SKYROOT_STICKS.get().defaultBlockState(), false);

            this.createMoss(context, center, radius + 3, context.random().nextInt(5) + 3, context.random().nextInt(5) + 1);

            this.createBranches(context, center.below(), List.copyOf(topPoints.keySet()), radius - 1, context.random().nextInt(4) + 4, context.random().nextInt(5) + 3, AetherIIBlocks.TANGLED_BRANCHES.get().defaultBlockState());
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

    private void createMoss(Context context, BlockPos center, int offset, int radius, int amount) {
        SimpleWeightedRandomList.Builder<BlockState> bryallinMossFlowers = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                bryallinMossFlowers.add(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get().defaultBlockState().setValue(MossFlowersBlock.AMOUNT, i).setValue(MossFlowersBlock.FACING, direction), 1);
            }
        }

        for (BlockPos start : BlockPos.randomBetweenClosed(context.random(), amount, center.getX() - offset, center.getY(), center.getZ() - offset, center.getX() + offset, center.getY() + 1, center.getZ() + offset)) {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    if (Math.pow(z, 2) + Math.pow(x, 2) <= Math.pow(radius + context.random().nextInt(2), 2)) {
                        BlockPos offsetPos = start.offset(x, 0, z);
                        if (context.isAir(offsetPos) && context.level().isStateAtPosition(offsetPos.below(), (blockState) -> blockState.is(AetherIIBlocks.WOVEN_SKYROOT_STICKS) || blockState.is(AetherIIBlocks.SKYPLANE_LEAVES))) {
                            if (context.random().nextInt(4) == 0) {
                                context.setBlock(offsetPos, new WeightedStateProvider(bryallinMossFlowers).getState(context.random(), offsetPos)); //todo state codec parameters
                            } else {
                                context.setBlock(offsetPos, AetherIIBlocks.BRYALINN_MOSS_CARPET.get().defaultBlockState());
                            }
                            context.setBlock(offsetPos.below(), AetherIIBlocks.BRYALINN_MOSS_BLOCK.get().defaultBlockState());
                            if (context.random().nextInt(3) == 0) {
                                for (Direction offsetDirection : Direction.Plane.HORIZONTAL.stream().toList()) {
                                    BlockPos newPos = offsetPos.below().relative(offsetDirection);
                                    if (context.isAir(newPos)) {
                                        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(context.random());
                                        if (context.level().isStateAtPosition(newPos.relative(direction), BlockBehaviour.BlockStateBase::isSolid)) {
                                            BlockState vineState = AetherIIBlocks.BRYALINN_MOSS_VINES.get().defaultBlockState();
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
                        }
                    }
                }
            }
        }
    }

    private void createBranches(Context context, BlockPos center, List<Coordinate> goals, int radius, int amount, int height, BlockState blockState) {
        for (BlockPos start : BlockPos.randomBetweenClosed(context.random(), amount, center.getX() - radius, center.getY(), center.getZ() - radius, center.getX() + radius, center.getY() + 1, center.getZ() + radius)) {
            this.createBranch(context, start, goals, height, blockState);
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

            if (context.checkBlock(new BlockPos(target.x(), start.below().below(height).getY(), target.z()), (blockState1) -> blockState1.is(BlockTags.LOGS))) {
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

                branchPositions.removeIf(pos -> context.checkBlock(pos, state -> state.is(BlockTags.LOGS)));

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
                            context.setBlock(pos.above(), AetherIIBlocks.BRYALINN_MOSS_CARPET.get().defaultBlockState());
                        }
                    }

                    if (context.random().nextInt(3) == 0) {
                        if (context.isAir(pos.above())) {
                            context.setBlock(pos.above(), AetherIIBlocks.BRYALINN_MOSS_CARPET.get().defaultBlockState());
                        }
                        for (Direction offsetDirection : Direction.Plane.HORIZONTAL.stream().toList()) {
                            BlockPos newPos = pos.relative(offsetDirection);
                            if (context.isAir(newPos)) {
                                Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(context.random());
                                if (context.level().isStateAtPosition(newPos.relative(direction), BlockBehaviour.BlockStateBase::isSolid)) {
                                    BlockState vineState = AetherIIBlocks.BRYALINN_MOSS_VINES.get().defaultBlockState();
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
                }
            }
        }
    }

    private boolean isValidBranchPos(Context context, BlockPos pos) {
        return context.isAir(pos) || context.checkBlock(pos, state -> state.is(BlockTags.LEAVES));
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.SHROUDED_CANOPY.get();
    }

    private record Coordinate(int x, int z) { }
}