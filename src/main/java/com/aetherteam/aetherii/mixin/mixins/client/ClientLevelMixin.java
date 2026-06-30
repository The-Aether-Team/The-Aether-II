package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {
    @ModifyVariable(method = "trySpawnDripParticles(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/particles/ParticleOptions;Z)V", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private ParticleOptions trySpawnDripParticles(ParticleOptions dripParticle, BlockPos pos, BlockState state, ParticleOptions originalDripParticle, boolean isTopSolid) {
        ClientLevel clientLevel = (ClientLevel) (Object) this;
        if (clientLevel.getBiome(pos).is(AetherIITags.Biomes.THE_AETHER) && dripParticle == ParticleTypes.DRIPPING_WATER) {
            return AetherIIParticleTypes.DRIPPING_WATER.get();
        }
        return dripParticle;
    }
}
