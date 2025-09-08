package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class AllowsLightLeavesBlock extends AetherLeavesBlock{
    public AllowsLightLeavesBlock(Properties properties, ParticleOptions leavesParticle, Holder<Block> leavesPile) {
        super(properties, leavesParticle, leavesPile);
    }

    @Override
    protected int getLightBlock(BlockState state) {
        return 0;
    }
}
