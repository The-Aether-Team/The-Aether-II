package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AbandonedBagBlockEntity extends ChestBlockEntity {
    private static final Component NAME = Component.translatable("aether_ii.container.abandoned_bag");

    public AbandonedBagBlockEntity() {
        this(AetherIIBlockEntityTypes.ABANDONED_BAG.get(), BlockPos.ZERO, AetherIIBlocks.ABANDONED_BAG.get().defaultBlockState());
    }

    public AbandonedBagBlockEntity(BlockPos pos, BlockState state) {
        this(AetherIIBlockEntityTypes.ABANDONED_BAG.get(), pos, state);
    }

    protected AbandonedBagBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    protected Component getDefaultName() {
        return NAME;
    }
}