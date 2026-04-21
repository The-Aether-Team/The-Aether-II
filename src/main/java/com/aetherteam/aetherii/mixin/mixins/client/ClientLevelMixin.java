package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {
    @WrapMethod(method = "trySpawnDripParticles(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/particles/ParticleOptions;Z)V")
    private void trySpawnDripParticles(BlockPos pos, BlockState state, ParticleOptions dripParticle, boolean isTopSolid, Operation<Void> original) {
        ClientLevel clientLevel = (ClientLevel) (Object) this;
        if (clientLevel.getBiome(pos).is(AetherIITags.Biomes.THE_AETHER)) {
            if (dripParticle == ParticleTypes.DRIPPING_WATER) {
                original.call(pos, state, AetherIIParticleTypes.DRIPPING_WATER.get(), isTopSolid);
                return;
            }
        }
        original.call(pos, state, dripParticle, isTopSolid);
    }
}