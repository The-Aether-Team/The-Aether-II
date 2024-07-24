package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AetherTallGrassBlock extends TallGrassBlock implements Snowable {
    protected static final VoxelShape SHORT_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);
    protected static final VoxelShape MEDIUM_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 10.0, 14.0);
    protected static final VoxelShape LONG_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public static final EnumProperty<GrassType> TYPE = EnumProperty.create("grass_type", GrassType.class);

    public AetherTallGrassBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, GrassType.DEFAULT));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (level.getBlockState(pos.below()).is(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK)) {
            return this.defaultBlockState().setValue(TYPE, GrassType.ENCHANTED);
        }
        return super.getStateForPlacement(context);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.is(AetherIIBlocks.AETHER_SHORT_GRASS)) {
            return SHORT_SHAPE;
        } else if (state.is(AetherIIBlocks.AETHER_MEDIUM_GRASS)) {
            return MEDIUM_SHAPE;
        }
        return LONG_SHAPE;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (state.is(AetherIIBlocks.AETHER_SHORT_GRASS)) {
            level.setBlock(pos, AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState(), 2);
        } else if (state.is(AetherIIBlocks.AETHER_MEDIUM_GRASS)) {
            level.setBlock(pos, AetherIIBlocks.AETHER_LONG_GRASS.get().defaultBlockState(), 2);
        }
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
        if (this.isSnowy(state)) {
            level.setBlock(pos, AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState(), 1 | 2);
        }
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, level, pos, explosion);
        if (this.isSnowy(state)) {
            level.setBlock(pos, AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState(), 1 | 2);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TYPE);
    }

    @Override
    public boolean isSnowy(BlockState blockState) {
        return blockState.getValue(TYPE) == GrassType.SNOWY;
    }

    @Override
    public BlockState setSnowy(BlockState blockState) {
        return blockState.setValue(TYPE, GrassType.SNOWY);
    }

    public enum GrassType implements StringRepresentable {
        DEFAULT,
        SNOWY,
        ENCHANTED;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}
