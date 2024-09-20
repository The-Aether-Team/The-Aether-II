package com.aetherteam.aetherii.client.particle;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIIClient;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, AetherII.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AETHER_PORTAL = PARTICLES.register("aether_portal", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SKYROOT_LEAVES = PARTICLES.register("skyroot_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SKYPLANE_LEAVES = PARTICLES.register("skyplane_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SKYBIRCH_LEAVES = PARTICLES.register("skybirch_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SKYPINE_LEAVES = PARTICLES.register("skypine_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WISPROOT_LEAVES = PARTICLES.register("wisproot_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WISPTOP_LEAVES = PARTICLES.register("wisptop_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GREATROOT_LEAVES = PARTICLES.register("greatroot_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GREATOAK_LEAVES = PARTICLES.register("greatoak_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GREATBOA_LEAVES = PARTICLES.register("greatboa_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AMBEROOT_LEAVES = PARTICLES.register("amberoot_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> IRRADIATED_LEAVES = PARTICLES.register("irradiated_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DRIPPING_WATER = PARTICLES.register("dripping_water", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FALLING_WATER = PARTICLES.register("falling_water", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPLASH = PARTICLES.register("splash", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AMBROSIUM = PARTICLES.register("ambrosium_torch", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RAIN = PARTICLES.register("rain", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> IRRADIATION = PARTICLES.register("irradiation", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ZEPHYR_SNOWFLAKE = PARTICLES.register("zephyr_snowflake", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> TEMPEST_ELECTRICITY = PARTICLES.register("tempest_electricity.json", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SLASH_ATTACK = PARTICLES.register("slash_attack", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> IMPACT_ATTACK = PARTICLES.register("impact_attack", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PIERCE_ATTACK = PARTICLES.register("pierce_attack", () -> new SimpleParticleType(false));
}