package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.renderer.level.HighlandsSpecialEffects;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Entity.class)
public class EntityMixin {
    @ModifyArg(method = "doWaterSplashEffect()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"), index = 0)
    private ParticleOptions injected(ParticleOptions particleOptions) {
        Entity entity = (Entity) (Object) this;
        if (entity.level() instanceof ClientLevel clientLevel && clientLevel.effects() instanceof HighlandsSpecialEffects) {
            if (particleOptions == ParticleTypes.SPLASH) {
                return AetherIIParticleTypes.SPLASH.get();
            }
        }
        return particleOptions;
    }
}
