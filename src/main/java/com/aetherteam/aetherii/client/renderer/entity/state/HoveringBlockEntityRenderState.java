package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.EmptyBlockAndTintGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;

import javax.annotation.Nullable;

public class HoveringBlockEntityRenderState extends EntityRenderState implements BlockAndTintGetter {
    public BlockEntity blockEntityDummy;
    public BlockState blockState = Blocks.SAND.defaultBlockState();

    public BlockPos startBlockPos = BlockPos.ZERO;
    public BlockPos blockPos = BlockPos.ZERO;
    @javax.annotation.Nullable
    public Holder<Biome> biome;
    public BlockAndTintGetter level = EmptyBlockAndTintGetter.INSTANCE;

    @Override
    public float getShade(Direction p_362170_, boolean p_362179_) {
        return this.level.getShade(p_362170_, p_362179_);
    }

    @Override
    public LevelLightEngine getLightEngine() {
        return this.level.getLightEngine();
    }

    @Override
    public int getBlockTint(BlockPos p_363218_, ColorResolver p_362552_) {
        return this.biome == null ? -1 : p_362552_.getColor(this.biome.value(), (double) p_363218_.getX(), (double) p_363218_.getZ());
    }

    @Nullable
    @Override
    public BlockEntity getBlockEntity(BlockPos p_364380_) {
        return this.blockEntityDummy;
    }

    @Override
    public BlockState getBlockState(BlockPos p_365153_) {
        return p_365153_.equals(this.blockPos) ? this.blockState : Blocks.AIR.defaultBlockState();
    }

    @Override
    public FluidState getFluidState(BlockPos p_362887_) {
        return this.getBlockState(p_362887_).getFluidState();
    }

    @Override
    public int getHeight() {
        return 1;
    }

    @Override
    public int getMinY() {
        return this.blockPos.getY();
    }
}
