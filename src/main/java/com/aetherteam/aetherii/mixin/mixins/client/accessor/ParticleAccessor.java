package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Particle.class)
public interface ParticleAccessor {
    @Accessor("alpha")
    void aether_ii$setAlpha(float alpha);
}
