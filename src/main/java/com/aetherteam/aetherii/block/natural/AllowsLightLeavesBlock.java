package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class AllowsLightLeavesBlock extends AetherLeavesBlock{
    public AllowsLightLeavesBlock(Properties properties, Supplier<SimpleParticleType> leavesParticle, Supplier<Block> leavesPile) {
        super(properties, leavesParticle, leavesPile);
    }

    @Override
    protected int getLightBlock(BlockState state) {
        return 0;
    }
}
