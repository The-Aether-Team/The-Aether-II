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
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DRIPPING_WATER = PARTICLES.register("dripping_water", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FALLING_WATER = PARTICLES.register("falling_water", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPLASH = PARTICLES.register("splash", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AMBROSIUM_TORCH = PARTICLES.register("ambrosium_torch", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RAIN = PARTICLES.register("rain", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> IRRADIATION = PARTICLES.register("irradiation", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ZEPHYR_SNOWFLAKE = PARTICLES.register("zephyr_snowflake", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SLASH_ATTACK = PARTICLES.register("slash_attack", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> IMPACT_ATTACK = PARTICLES.register("impact_attack", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PIERCE_ATTACK = PARTICLES.register("pierce_attack", () -> new SimpleParticleType(false));

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
        event.registerSprite(DRIPPING_WATER.get(), (particleType, level, x, y, z, xSpeed, ySpeed, zSpeed) -> new DripParticle.DripHangParticle(level, x, y, z, Fluids.WATER, AetherIIParticleTypes.FALLING_WATER.get()) {
            @Override
            public ParticleRenderType getRenderType() {
                return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
            }
        });
        event.registerSprite(FALLING_WATER.get(), (particleType, level, x, y, z, xSpeed, ySpeed, zSpeed) -> new DripParticle.FallAndLandParticle(level, x, y, z, Fluids.WATER, AetherIIParticleTypes.SPLASH.get()) {
            @Override
            public ParticleRenderType getRenderType() {
                return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
            }
        });
        event.registerSpriteSet(SPLASH.get(), spriteSet -> (particle, level, x, y, z, xSpeed, ySpeed, zSpeed) -> {
            SplashParticle splashParticle = new SplashParticle(level, x, y, z, xSpeed, ySpeed, zSpeed) {
                @Override
                public ParticleRenderType getRenderType() {
                    return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
                }
            };
            splashParticle.pickSprite(spriteSet);
            return splashParticle;
        });
        event.registerSpriteSet(AMBROSIUM_TORCH.get(), AmbrosiumTorchParticle.Provider::new);

        event.registerSpriteSet(RAIN.get(), spriteSet -> (particle, level, x, y, z, xSpeed, ySpeed, zSpeed) -> {
            WaterDropParticle rainParticle = new WaterDropParticle(level, x, y, z) {
                @Override
                public ParticleRenderType getRenderType() {
                    return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
                }
            };
            rainParticle.pickSprite(spriteSet);
            return rainParticle;
        });
        event.registerSpriteSet(IRRADIATION.get(), IrradiationParticle.Factory::new);

        event.registerSpriteSet(SLASH_ATTACK.get(), DamageTypeParticle.Provider::new);
        event.registerSpriteSet(IMPACT_ATTACK.get(), DamageTypeParticle.Provider::new);
        event.registerSpriteSet(PIERCE_ATTACK.get(), DamageTypeParticle.Provider::new);
        event.registerSpriteSet(ZEPHYR_SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
    }
}