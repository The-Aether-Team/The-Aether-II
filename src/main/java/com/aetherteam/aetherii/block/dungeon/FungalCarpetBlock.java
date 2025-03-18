package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MossyCarpetBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class FungalCarpetBlock extends MossyCarpetBlock {
    public static final MapCodec<FungalCarpetBlock> CODEC = simpleCodec(FungalCarpetBlock::new);
    public static final BooleanProperty BASE = BlockStateProperties.BOTTOM;
    private static final EnumProperty<WallSide> NORTH = BlockStateProperties.NORTH_WALL;
    private static final EnumProperty<WallSide> EAST = BlockStateProperties.EAST_WALL;
    private static final EnumProperty<WallSide> SOUTH = BlockStateProperties.SOUTH_WALL;
    private static final EnumProperty<WallSide> WEST = BlockStateProperties.WEST_WALL;
    private static final ImmutableMap PROPERTY_BY_DIRECTION = ImmutableMap.copyOf(Util.make(Maps.newEnumMap(Direction.class), (map) -> {
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
    }));

    public FungalCarpetBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BASE, true).setValue(NORTH, WallSide.NONE).setValue(EAST, WallSide.NONE).setValue(SOUTH, WallSide.NONE).setValue(WEST, WallSide.NONE));
    }

    private static BlockState getUpdatedState(BlockState state, BlockGetter blockGetter, BlockPos pos, boolean base) {
        BlockState stateAbove = null;
        BlockState stateBelow = null;
        base |= state.getValue(BASE);

        EnumProperty<WallSide> wallProperty;
        WallSide wallSide;
        for(Iterator<Direction> var6 = Direction.Plane.HORIZONTAL.iterator(); var6.hasNext(); state = state.setValue(wallProperty, wallSide)) {
            Direction direction = var6.next();
            wallProperty = getPropertyForFace(direction);
            wallSide = canSupportAtFace(blockGetter, pos, direction) ? (base ? WallSide.LOW : state.getValue(wallProperty)) : WallSide.NONE;
            if (wallSide == WallSide.LOW) {
                if (stateAbove == null) {
                    stateAbove = blockGetter.getBlockState(pos.above());
                }

                if (stateAbove.is(AetherIIBlocks.FUNGAL_CARPET) && stateAbove.getValue(wallProperty) != WallSide.NONE && !(Boolean)stateAbove.getValue(BASE)) {
                    wallSide = WallSide.TALL;
                }

                if (!(Boolean)state.getValue(BASE)) {
                    if (stateBelow == null) {
                        stateBelow = blockGetter.getBlockState(pos.below());
                    }

                    if (stateBelow.is(AetherIIBlocks.FUNGAL_CARPET) && stateBelow.getValue(wallProperty) == WallSide.NONE) {
                        wallSide = WallSide.NONE;
                    }
                }
            }
        }
        return state;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getUpdatedState(this.defaultBlockState(), context.getLevel(), context.getClickedPos(), true);
    }

    public static void placeAt(LevelAccessor level, BlockPos pos, RandomSource random, int flags) {
        BlockState state = AetherIIBlocks.FUNGAL_CARPET.get().defaultBlockState();
        BlockState stateUpdated = getUpdatedState(state, level, pos, true);
        level.setBlock(pos, stateUpdated, 3);
        Objects.requireNonNull(random);
        BlockState stateAbove = createTopperWithSideChance(level, pos, random::nextBoolean);
        if (!stateAbove.isAir()) {
            level.setBlock(pos.above(), stateAbove, flags);
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (!level.isClientSide) {
            RandomSource randomsource = level.getRandom();
            Objects.requireNonNull(randomsource);
            BlockState stateAbove = createTopperWithSideChance(level, pos, randomsource::nextBoolean);
            if (!stateAbove.isAir()) {
                level.setBlock(pos.above(), stateAbove, 3);
            }
        }
    }

    private static BlockState createTopperWithSideChance(BlockGetter blockGetter, BlockPos pos, BooleanSupplier booleanSupplier) {
        BlockPos posAbove = pos.above();
        BlockState stateAbove = blockGetter.getBlockState(posAbove);
        boolean flag = stateAbove.is(AetherIIBlocks.FUNGAL_CARPET.get());
        if ((!flag || !(Boolean)stateAbove.getValue(BASE)) && (flag || stateAbove.canBeReplaced())) {
            BlockState state = AetherIIBlocks.FUNGAL_CARPET.get().defaultBlockState().setValue(BASE, false);
            BlockState stateUpdated = getUpdatedState(state, blockGetter, pos.above(), true);

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                EnumProperty<WallSide> wallProperty = getPropertyForFace(direction);
                if (stateUpdated.getValue(wallProperty) != WallSide.NONE && !booleanSupplier.getAsBoolean()) {
                    stateUpdated = stateUpdated.setValue(wallProperty, WallSide.NONE);
                }
            }

            return hasFaces(stateUpdated) && stateUpdated != stateAbove ? stateUpdated : Blocks.AIR.defaultBlockState();
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    private static boolean hasFaces(BlockState state) {
        if (!state.getValue(BASE)) {
            Iterator var1 = PROPERTY_BY_DIRECTION.values().iterator();

            EnumProperty wallProperty;
            do {
                if (!var1.hasNext()) {
                    return false;
                }

                wallProperty = (EnumProperty) var1.next();
            } while (state.getValue(wallProperty) == WallSide.NONE);

        }
        return true;
    }

    private static boolean canSupportAtFace(BlockGetter blockGetter, BlockPos pos, Direction direction) {
        return direction != Direction.UP && MultifaceBlock.canAttachTo(blockGetter, pos, direction);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BASE, NORTH, EAST, SOUTH, WEST);
    }
}