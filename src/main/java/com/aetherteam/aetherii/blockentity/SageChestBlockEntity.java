package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SageChestBlockEntity extends ChestBlockEntity {
    public SageChestBlockEntity() {
        this(AetherIIBlockEntityTypes.SAGE_CHEST.get(), BlockPos.ZERO, AetherIIBlocks.SAGE_CHEST.get().defaultBlockState());
    }

    public SageChestBlockEntity(BlockPos pos, BlockState state) {
        this(AetherIIBlockEntityTypes.SAGE_CHEST.get(), pos, state);
    }

    protected SageChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("aether_ii.container.sage_chest");
    }
}
