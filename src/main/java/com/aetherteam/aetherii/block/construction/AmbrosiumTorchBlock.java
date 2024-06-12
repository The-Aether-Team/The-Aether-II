package com.aetherteam.aetherii.block.construction;

import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AmbrosiumTorchBlock extends TorchBlock {
    public AmbrosiumTorchBlock(Properties properties) {
        super(ParticleTypes.SMOKE, properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double d0 = (double) pos.getX() + 0.5;
        double d1 = (double) pos.getY() + 0.7;
        double d2 = (double) pos.getZ() + 0.5;
        if (random.nextInt(7) == 0) {
            level.addParticle(AetherIIParticleTypes.AMBROSIUM.get(), d0, d1, d2, 0.0, 0.0, 0.0);
        }
    }
}
