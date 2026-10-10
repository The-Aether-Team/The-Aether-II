package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TriState;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;

import java.util.List;

public class CrystalAppleBlock extends Block {
    public static final MapCodec<CrystalAppleBlock> CODEC = simpleCodec(CrystalAppleBlock::new);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    private static final List<VoxelShape> SHAPES = List.of(
            Block.column(5.0, 7.0, 16.0),
            Block.column(6.0, 6.0, 16.0),
            Block.column(7.0, 4.0, 16.0)
    );
    private static final VoxelShape COLLISION_SHAPE = Block.column(3.0, 8.0, 16.0);

    @Override
    public MapCodec<CrystalAppleBlock> codec() {
        return CODEC;
    }

    public CrystalAppleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState relativeState = level.getBlockState(pos.relative(Direction.UP));
        TriState soilDecision = relativeState.canSustainPlant(level, pos.relative(Direction.UP), Direction.DOWN, state);
        return !soilDecision.isDefault() ? soilDecision.isTrue() : relativeState.is(AetherIIBlocks.BUDDING_CRYSTALROOT);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (CommonHooks.canCropGrow(level, pos, state, random.nextInt(15) == 0)) {
            level.setBlockAndUpdate(pos, state.setValue(AGE, Math.min(state.getValue(AGE) + 1, 2)));
            CommonHooks.fireCropGrowPost(level, pos, state);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 2;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        return directionToNeighbour == Direction.UP && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(AGE));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(AGE) == 0) {
            return Shapes.empty();
        } else {
            return COLLISION_SHAPE;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
