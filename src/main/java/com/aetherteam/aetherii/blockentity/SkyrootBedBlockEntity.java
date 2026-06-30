package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SkyrootBedBlockEntity extends BlockEntity {
    private DyeColor color = DyeColor.WHITE;

    public SkyrootBedBlockEntity() {
        super(AetherIIBlockEntityTypes.SKYROOT_BED.get(), BlockPos.ZERO, AetherIIBlocks.SKYROOT_BED.get().defaultBlockState());
    }

    public SkyrootBedBlockEntity(DyeColor color) {
        super(AetherIIBlockEntityTypes.SKYROOT_BED.get(), BlockPos.ZERO, AetherIIBlocks.SKYROOT_BED.get().defaultBlockState());
        this.color = color;
    }

    public SkyrootBedBlockEntity(BlockPos pos, BlockState state) {
        super(AetherIIBlockEntityTypes.SKYROOT_BED.get(), pos, state);
        this.color = ((BedBlock) state.getBlock()).getColor();
    }

    public SkyrootBedBlockEntity(BlockPos pos, BlockState state, DyeColor color) {
        super(AetherIIBlockEntityTypes.SKYROOT_BED.get(), pos, state);
        this.color = color;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public DyeColor getColor() {
        return this.color;
    }

    public void setColor(DyeColor color) {
        this.color = color;
    }
}