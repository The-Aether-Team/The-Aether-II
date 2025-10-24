package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Optional;

public class MultiBlockEntity extends BlockEntity {
    private BlockPos levelOriginPos;

    public MultiBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        Optional<int[]> positions = input.getIntArray("origin");
        positions.ifPresent(ints -> this.levelOriginPos = new BlockPos(ints[0], ints[1], ints[2]));
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (this.levelOriginPos != null) {
            output.putIntArray("origin", new int[]{this.levelOriginPos.getX(), this.levelOriginPos.getY(), this.levelOriginPos.getZ()});
        }
    }

    @Override
    public void handleUpdateTag(ValueInput input) {
        this.loadAdditional(input);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag;
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(this.problemPath(), AetherII.LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(reporter, registries);
            this.saveAdditional(output);
            tag = output.buildResult();
        }
        return tag;
    }

    public void setLevelOriginPos(BlockPos levelOriginPos) {
        this.levelOriginPos = levelOriginPos;
    }

    public BlockPos getLevelOriginPos() {
        return this.levelOriginPos;
    }

    public boolean isOrigin() {
        return this.getLevelOriginPos() == this.getBlockPos();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}