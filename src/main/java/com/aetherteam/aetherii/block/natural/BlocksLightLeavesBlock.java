package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlocksLightLeavesBlock extends AetherLeavesBlock {
    public BlocksLightLeavesBlock(Properties properties, ParticleOptions leavesParticle, Holder<Block> leavesPile) {
        super(properties, leavesParticle, leavesPile);
    }

    public BlocksLightLeavesBlock(Properties properties, Supplier<? extends ParticleOptions> leavesParticle, Holder<Block> leavesPile) {
        super(properties, leavesParticle, leavesPile);
    }

    public BlocksLightLeavesBlock(Properties properties, ParticleOptions leavesParticle, RegistryObject<Block> leavesPile) {
        super(properties, leavesParticle, leavesPile);
    }

    public BlocksLightLeavesBlock(Properties properties, Supplier<? extends ParticleOptions> leavesParticle, RegistryObject<Block> leavesPile) {
        super(properties, leavesParticle, leavesPile);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, net.minecraft.world.level.BlockGetter level, net.minecraft.core.BlockPos pos) {
        return false;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 15;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }
}
