package com.aetherteam.aetherii.client.particle;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.particle.CritParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, AetherII.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SLASH_ATTACK = PARTICLES.register("slash_attack", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> IMPACT_ATTACK = PARTICLES.register("impact_attack", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PIERCE_ATTACK = PARTICLES.register("pierce_attack", () -> new SimpleParticleType(false));

    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(SLASH_ATTACK.get(), DamageTypeParticle.Provider::new);
        event.registerSpriteSet(IMPACT_ATTACK.get(), DamageTypeParticle.Provider::new);
        event.registerSpriteSet(PIERCE_ATTACK.get(), DamageTypeParticle.Provider::new);
    }
}
