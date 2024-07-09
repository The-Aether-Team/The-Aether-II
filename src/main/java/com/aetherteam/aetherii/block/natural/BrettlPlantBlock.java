package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class BrettlPlantBlock extends Block {
    public static final MapCodec<BrettlPlantBlock> CODEC = simpleCodec(BrettlPlantBlock::new);
    public static final EnumProperty<BrettlPlantBlock.BrettlType> TYPE = EnumProperty.create("brettl_type", BrettlPlantBlock.BrettlType.class);
    public static final BooleanProperty GROWN = AetherIIBlockStateProperties.BRETTL_GROWN;
    protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);


    public BrettlPlantBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, BrettlType.BASE).setValue(GROWN, false));
    }

    @Override
    public MapCodec<BrettlPlantBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState stateBelow = level.getBlockState(pos.below());
        return stateBelow.is(AetherIIBlocks.QUICKSOIL) || stateBelow.getValue(TYPE) == BrettlType.BASE || stateBelow.getValue(TYPE) == BrettlType.MIDDLE;
    }

    /*
    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return this.canSurvive(state, level, currentPos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

     */

    public @NotNull ItemStack getCloneItemStack(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return new ItemStack(AetherIIItems.BRETTL_CANE.get());
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(TYPE) == BrettlType.TIP;
    }

    public void randomTick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(TYPE) == BrettlType.TIP) {
            if (level.getRawBrightness(pos.above(), 0) >= 9 && CommonHooks.canCropGrow(level, pos, state, random.nextInt(5) == 0)) {
                BlockState brettlMiddle = state.setValue(TYPE, BrettlType.MIDDLE);
                BlockState brettlTip = state.setValue(TYPE, BrettlType.TIP);
                BlockState brettlFlower = AetherIIBlocks.BRETTL_FLOWER.get().defaultBlockState();

                if (level.getBlockState(pos.below()).getValue(TYPE) == BrettlType.BASE) {
                    if (level.getBlockState(pos.above()).isAir()) {
                        level.setBlock(pos, brettlMiddle, 2);
                        level.setBlock(pos.above(), brettlTip, 2);
                        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(brettlMiddle));
                        CommonHooks.fireCropGrowPost(level, pos, state);
                    }
                } else if (level.getBlockState(pos.below()).getValue(TYPE) == BrettlType.MIDDLE) {
                    level.setBlock(pos, brettlFlower, 2);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(brettlFlower));
                    CommonHooks.fireCropGrowPost(level, pos, state);
                }
            }
        }
    }


    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        LevelAccessor level = context.getLevel();
        if (level.getBlockState(pos.above()).isAir()) {
            if (canSurvive(level.getBlockState(pos.below()), level, pos)) {
                level.setBlock(pos.above(), AetherIIBlocks.BRETTL_PLANT.get().defaultBlockState().setValue(TYPE, BrettlType.TIP), 2);
                return AetherIIBlocks.BRETTL_PLANT.get().defaultBlockState();
            }
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, GROWN);
    }

    public enum BrettlType implements StringRepresentable {
        BASE,
        MIDDLE,
        TIP;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}