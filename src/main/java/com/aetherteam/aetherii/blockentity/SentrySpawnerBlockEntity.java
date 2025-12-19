package com.aetherteam.aetherii.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import javax.annotation.Nullable;

public class SentrySpawnerBlockEntity extends WallSpawnerBlockEntity {
    public boolean triggerPiston;
    private int triggerTick;
    private float pistonScale;
    private float pistonScaleOld;
    private boolean active;


    public SentrySpawnerBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherIIBlockEntityTypes.SENTRY_SPAWNER.get(), pos, blockState);
    }

    @Override
    public void spawnTrigger(Level level, BlockPos blockPos) {
        this.triggerPiston = true;
        this.triggerTick = 20;
        level.playSound(null, blockPos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 1.25F, 1.5F);
        this.markUpdated();
    }

    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public void spawnerTriggerTick(Level level, BlockPos blockPos) {
        if (this.triggerPiston && --this.triggerTick <= 0) {
            this.spawnTriggerStop(level, blockPos);
        }
        this.pistonScaleOld = this.pistonScale;
        if (this.triggerPiston) {
            this.pistonScale = Mth.clamp(this.pistonScale + 0.1F, 0.0F, 1.0F);
        } else {
            this.pistonScale = Mth.clamp(this.pistonScale - 0.1F, 0.0F, 1.0F);
        }
    }

    public void spawnTriggerStop(Level level, BlockPos blockPos) {
        this.triggerPiston = false;
        level.playSound(null, blockPos, SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 1.25F, 1.5F);
    }

    public float getPistonAnimationScale(float partialTick) {
        return Mth.lerp(partialTick, this.pistonScaleOld, this.pistonScale);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, SentrySpawnerBlockEntity blockEntity) {
        WallSpawnerBlockEntity.clientTick(level, pos, state, blockEntity);
        blockEntity.spawnerTriggerTick(level, pos);
        blockEntity.active = blockEntity.isNearPlayer(level, pos);
    }

    private boolean isNearPlayer(Level level, BlockPos pos) {
        int range = 16;
        return level.hasNearbyAlivePlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, range);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SentrySpawnerBlockEntity blockEntity) {
        WallSpawnerBlockEntity.serverTick(level, pos, state, blockEntity);
        blockEntity.spawnerTriggerTick(level, pos);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putBoolean("trigger_piston", this.triggerPiston);
        output.putInt("trigger_tick", this.triggerTick);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.triggerPiston = input.getBooleanOr("trigger_piston", false);
        this.triggerTick = input.getIntOr("trigger_tick", 0);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = this.saveCustomOnly(provider);
        tag.remove("SpawnPotentials");
        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


    public boolean isActive() {
        return active;
    }
}
