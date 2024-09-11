package com.aetherteam.aetherii.client.particle;

import com.aetherteam.aetherii.client.AetherIIClient;
import net.minecraft.client.particle.*;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public class AetherIIParticleFactories {
    /**
     * @see AetherIIClient#eventSetup()
     */
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(AetherIIParticleTypes.AETHER_PORTAL.get(), AetherPortalParticle.Factory::new);
        event.registerSpriteSet(AetherIIParticleTypes.SKYROOT_LEAVES.get(), AetherLeafParticle.SkyrootFactory::new);
        event.registerSpriteSet(AetherIIParticleTypes.SKYPLANE_LEAVES.get(), AetherLeafParticle.SkyplaneFactory::new);
        event.registerSpriteSet(AetherIIParticleTypes.SKYBIRCH_LEAVES.get(), AetherLeafParticle.SkybirchFactory::new);
        event.registerSpriteSet(AetherIIParticleTypes.SKYPINE_LEAVES.get(), AetherLeafParticle.SkypineFactory::new);
        event.registerSpriteSet(AetherIIParticleTypes.WISPROOT_LEAVES.get(), AetherLeafParticle.WisprootFactory::new);
        event.registerSpriteSet(AetherIIParticleTypes.WISPTOP_LEAVES.get(), AetherLeafParticle.WisptopFactory::new);
        event.registerSpriteSet(AetherIIParticleTypes.GREATROOT_LEAVES.get(), AetherLeafParticle.GreatrootFactory::new);
        event.registerSpriteSet(AetherIIParticleTypes.GREATOAK_LEAVES.get(), AetherLeafParticle.GreatoakFactory::new);
        event.registerSpriteSet(AetherIIParticleTypes.GREATBOA_LEAVES.get(), AetherLeafParticle.GreatboaFactory::new);
        event.registerSpriteSet(AetherIIParticleTypes.AMBEROOT_LEAVES.get(), AetherLeafParticle.AmberootFactory::new);
        event.registerSpriteSet(AetherIIParticleTypes.IRRADIATED_LEAVES.get(), AetherLeafParticle.AmberootFactory::new);
        event.registerSprite(AetherIIParticleTypes.DRIPPING_WATER.get(), (particleType, level, x, y, z, xSpeed, ySpeed, zSpeed) -> new DripParticle.DripHangParticle(level, x, y, z, Fluids.WATER, AetherIIParticleTypes.FALLING_WATER.get()) {
            @Override
            public ParticleRenderType getRenderType() {
                return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
            }
        });
        event.registerSprite(AetherIIParticleTypes.FALLING_WATER.get(), (particleType, level, x, y, z, xSpeed, ySpeed, zSpeed) -> new DripParticle.FallAndLandParticle(level, x, y, z, Fluids.WATER, AetherIIParticleTypes.SPLASH.get()) {
            @Override
            public ParticleRenderType getRenderType() {
                return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
            }
        });
        event.registerSpriteSet(AetherIIParticleTypes.SPLASH.get(), spriteSet -> (particle, level, x, y, z, xSpeed, ySpeed, zSpeed) -> {
            SplashParticle splashParticle = new SplashParticle(level, x, y, z, xSpeed, ySpeed, zSpeed) {
                @Override
                public ParticleRenderType getRenderType() {
                    return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
                }
            };
            splashParticle.pickSprite(spriteSet);
            return splashParticle;
        });
        event.registerSpriteSet(AetherIIParticleTypes.AMBROSIUM.get(), AmbrosiumParticle.Provider::new);

        event.registerSpriteSet(AetherIIParticleTypes.RAIN.get(), spriteSet -> (particle, level, x, y, z, xSpeed, ySpeed, zSpeed) -> {
            WaterDropParticle rainParticle = new WaterDropParticle(level, x, y, z) {
                @Override
                public ParticleRenderType getRenderType() {
                    return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
                }
            };
            rainParticle.pickSprite(spriteSet);
            return rainParticle;
        });
        event.registerSpriteSet(AetherIIParticleTypes.IRRADIATION.get(), IrradiationParticle.Factory::new);

        event.registerSpriteSet(AetherIIParticleTypes.SLASH_ATTACK.get(), DamageTypeParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.IMPACT_ATTACK.get(), DamageTypeParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.PIERCE_ATTACK.get(), DamageTypeParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.ZEPHYR_SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
    }
}
