package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.api.Mural;
import com.aetherteam.aetherii.blockentity.MuralBlockEntity;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class MuralBlock extends BaseEntityBlock {
    public static final MapCodec<MuralBlock> CODEC = simpleCodec(MuralBlock::new);
    public static final EnumProperty<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty X_OFFSET = IntegerProperty.create("x_offset", 0, Mural.MAX_SIZE);
    public static final IntegerProperty Y_OFFSET = IntegerProperty.create("y_offset", 0, Mural.MAX_SIZE);

    @Override
    public MapCodec<MuralBlock> codec() {
        return CODEC;
    }

    public MuralBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(X_OFFSET, 0).setValue(Y_OFFSET, 0));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var blockState = this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
        var muralSection = context.getItemInHand().get(AetherIIDataComponents.MURAL_SECTION);
        if (muralSection != null) {
            blockState = blockState.setValue(X_OFFSET, muralSection.offsetX()).setValue(Y_OFFSET, muralSection.offsetY());
        }
        return blockState;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MuralBlockEntity(pos, state);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player) {
        if (level.getBlockEntity(pos) instanceof MuralBlockEntity muralBlockEntity) {
            var mural = muralBlockEntity.getMural();
            return MuralBlockEntity.createMuralItem(mural.orElse(null), state.getValue(X_OFFSET), state.getValue(Y_OFFSET));
        } else {
            return super.getCloneItemStack(level, pos, state, includeData, player);
        }
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, X_OFFSET, Y_OFFSET);
    }
}