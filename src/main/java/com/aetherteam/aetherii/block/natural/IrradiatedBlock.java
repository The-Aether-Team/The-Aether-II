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
import net.minecraft.world.phys.Vec3;

public class IrradiatedBlock extends Block {
    public IrradiatedBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (level.isClientSide()) {
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, AetherIIParticleTypes.IRRADIATION.get(), UniformInt.of(50, 100));
        }
        if (!player.isCreative()) {
            double dist = Math.sqrt(player.distanceToSqr(Vec3.atCenterOf(pos)));
            AetherIIDataAttachments.get(player, AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(player, EffectBuildupPresets.AMBROSIUM_POISONING, (int) (350 / dist));
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
