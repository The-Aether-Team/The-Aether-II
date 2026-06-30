package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class IrradiatedLeavesBlock extends AetherLeavesBlock {
    public static final IntegerProperty SHADE = IntegerProperty.create("shade", 0, 7);

    public IrradiatedLeavesBlock(Properties properties, ParticleOptions leavesParticle, Holder<Block> leavesPile) {
        super(properties, leavesParticle, leavesPile);
        this.registerDefaultState(this.defaultBlockState().setValue(SHADE, 7));
    }

    public IrradiatedLeavesBlock(Properties properties, Supplier<? extends ParticleOptions> leavesParticle, Holder<Block> leavesPile) {
        super(properties, leavesParticle, leavesPile);
        this.registerDefaultState(this.defaultBlockState().setValue(SHADE, 7));
    }

    public IrradiatedLeavesBlock(Properties properties, ParticleOptions leavesParticle, RegistryObject<Block> leavesPile) {
        super(properties, leavesParticle, leavesPile);
        this.registerDefaultState(this.defaultBlockState().setValue(SHADE, 7));
    }

    public IrradiatedLeavesBlock(Properties properties, Supplier<? extends ParticleOptions> leavesParticle, RegistryObject<Block> leavesPile) {
        super(properties, leavesParticle, leavesPile);
        this.registerDefaultState(this.defaultBlockState().setValue(SHADE, 7));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHADE);
        super.createBlockStateDefinition(builder);
    }
}
