package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.renderer.level.HighlandsSpecialEffects;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {
    @Inject(method = "doAnimateTick(IIIILnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/block/Block;Lnet/minecraft/core/BlockPos$MutableBlockPos;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;trySpawnDripParticles(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/particles/ParticleOptions;Z)V", shift = At.Shift.BEFORE))
    private void doAnimateTick(int x, int y, int z, int range, RandomSource random, @Nullable Block block, BlockPos.MutableBlockPos blockPos, CallbackInfo ci, @Local LocalRef<ParticleOptions> particleOptions) {
        ClientLevel clientLevel = (ClientLevel) (Object) this;
        if (clientLevel.effects() instanceof HighlandsSpecialEffects) {
            if (particleOptions.get() == ParticleTypes.DRIPPING_WATER) {
                particleOptions.set(AetherIIParticleTypes.DRIPPING_WATER.get());
            }
        }
    }
}
