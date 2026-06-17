package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FungalCacheBlockEntity extends ChestBlockEntity {
    private static final Component NAME = Component.translatable("aether_ii.container.fungal_cache");

    public FungalCacheBlockEntity() {
        this(AetherIIBlockEntityTypes.FUNGAL_CACHE.get(), BlockPos.ZERO, AetherIIBlocks.FUNGAL_CACHE.get().defaultBlockState());
    }

    public FungalCacheBlockEntity(BlockPos pos, BlockState state) {
        this(AetherIIBlockEntityTypes.FUNGAL_CACHE.get(), pos, state);
    }

    protected FungalCacheBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    protected Component getDefaultName() {
        return NAME;
    }
}