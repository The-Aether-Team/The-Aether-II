package com.aetherteam.aetherii.block.furniture;

import com.aetherteam.aetherii.blockentity.VaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class VaseBlock extends DecoratedPotBlock {
    public VaseBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new VaseBlockEntity(pos, state);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder lootParams) {
        return super.getDrops(state, lootParams);
    }
}