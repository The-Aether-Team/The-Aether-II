package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.blockentity.LockedBlockEntity;
import com.aetherteam.aetherii.blockentity.CopyBlockEntity;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class LockedBlock extends CopyBlock {
public LockedBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CopyBlock.WATERLOGGED, false).setValue(CopyBlock.EMPTY, true));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new LockedBlockEntity(blockPos, blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CopyBlock.WATERLOGGED, CopyBlock.EMPTY);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.gameMode != null && minecraft.gameMode.getPlayerMode() == GameType.CREATIVE && minecraft.player != null && minecraft.level != null) {
            ItemStack itemStack = minecraft.player.getMainHandItem();
            Item item = itemStack.getItem();
            BlockEntity blockEntity = level.getBlockEntity(pos);
            boolean hasCopyState = blockEntity instanceof CopyBlockEntity copyBlockEntity && copyBlockEntity.getCopyState() != null;
            if (item instanceof BlockItem blockItem && !hasCopyState) {
                if (blockItem.getBlock() == this) {
                    minecraft.level.addParticle(AetherIIParticleTypes.LOCKED_BLOCK.get(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
                }
            }
        }
    }
}
