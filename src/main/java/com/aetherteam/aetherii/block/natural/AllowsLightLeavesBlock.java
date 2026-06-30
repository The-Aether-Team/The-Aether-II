package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AllowsLightLeavesBlock extends AetherLeavesBlock{
    public AllowsLightLeavesBlock(Properties properties, ParticleOptions leavesParticle, Holder<Block> leavesPile) {
        super(properties, leavesParticle, leavesPile);
    }

    public AllowsLightLeavesBlock(Properties properties, Supplier<? extends ParticleOptions> leavesParticle, Holder<Block> leavesPile) {
        super(properties, leavesParticle, leavesPile);
    }

    public AllowsLightLeavesBlock(Properties properties, ParticleOptions leavesParticle, RegistryObject<Block> leavesPile) {
        super(properties, leavesParticle, leavesPile);
    }

    public AllowsLightLeavesBlock(Properties properties, Supplier<? extends ParticleOptions> leavesParticle, RegistryObject<Block> leavesPile) {
        super(properties, leavesParticle, leavesPile);
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 0;
    }
}
