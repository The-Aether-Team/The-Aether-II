package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class IrradiatedBlock extends Block {
    public IrradiatedBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (level.isClientSide()) {
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, AetherIIParticleTypes.IRRADIATION.get(), UniformInt.of(50, 100));
        }
        player.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(EffectBuildupPresets.AMBROSIUM_POISONING, 350);
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
