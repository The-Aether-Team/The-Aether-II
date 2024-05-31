package com.aetherteam.aetherii.client.particle;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIIClient;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
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
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> IRRADIATION = PARTICLES.register("irradiation", () -> new SimpleParticleType(false));

    /**
     * @see AetherIIClient#eventSetup()
     */
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(AETHER_PORTAL.get(), AetherPortalParticle.Factory::new);
        event.registerSpriteSet(SKYROOT_LEAVES.get(), AetherLeafParticle.SkyrootFactory::new);
        event.registerSpriteSet(SKYPLANE_LEAVES.get(), AetherLeafParticle.SkyplaneFactory::new);
        event.registerSpriteSet(SKYBIRCH_LEAVES.get(), AetherLeafParticle.SkybirchFactory::new);
        event.registerSpriteSet(SKYPINE_LEAVES.get(), AetherLeafParticle.SkypineFactory::new);
        event.registerSpriteSet(WISPROOT_LEAVES.get(), AetherLeafParticle.WisprootFactory::new);
        event.registerSpriteSet(WISPTOP_LEAVES.get(), AetherLeafParticle.WisptopFactory::new);
        event.registerSpriteSet(GREATROOT_LEAVES.get(), AetherLeafParticle.GreatrootFactory::new);
        event.registerSpriteSet(GREATOAK_LEAVES.get(), AetherLeafParticle.GreatoakFactory::new);
        event.registerSpriteSet(GREATBOA_LEAVES.get(), AetherLeafParticle.GreatboaFactory::new);
        event.registerSpriteSet(AMBEROOT_LEAVES.get(), AetherLeafParticle.AmberootFactory::new);
        event.registerSpriteSet(IRRADIATION.get(), IrradiationParticle.Factory::new);
    }
}