package com.aetherteam.aetherii.client.particle;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIFluids;
import com.aetherteam.aetherii.block.fluid.AlkahestFluid;
import com.aetherteam.aetherii.client.AetherIIClient;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public class AetherIIParticleFactories {
    /**
     * @see AetherIIClient#eventSetup(IEventBus) 
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
        event.registerSpriteSet(AetherIIParticleTypes.DRIPPING_WATER.get(), spriteSet -> (particleType, level, x, y, z, xSpeed, ySpeed, zSpeed, random) -> new DripParticle.DripHangParticle(level, x, y, z, Fluids.WATER, AetherIIParticleTypes.FALLING_WATER.get(), spriteSet.get(random)) {
            @Override
            public SingleQuadParticle.Layer getLayer() {
                return Layer.TRANSLUCENT;
            }
        });
        event.registerSpriteSet(AetherIIParticleTypes.FALLING_WATER.get(), spriteSet -> (particleType, level, x, y, z, xSpeed, ySpeed, zSpeed, random) -> new DripParticle.FallAndLandParticle(level, x, y, z, Fluids.WATER, AetherIIParticleTypes.SPLASH.get(), spriteSet.get(random)) {
            @Override
            public SingleQuadParticle.Layer getLayer() {
                return Layer.TRANSLUCENT;
            }
        });
        event.registerSpriteSet(AetherIIParticleTypes.SPLASH.get(), spriteSet -> (particle, level, x, y, z, xSpeed, ySpeed, zSpeed, random) -> {
            SplashParticle splashParticle = new SplashParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet.get(random)) {
                @Override
                public SingleQuadParticle.Layer getLayer() {
                    return Layer.TRANSLUCENT;
                }
            };
            return splashParticle;
        });
        event.registerSpriteSet(AetherIIParticleTypes.GLASS_FEATHERS.get(), GlassFeathersParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.AMBROSIUM.get(), AmbrosiumParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.ALKAHEST.get(), AlkahestParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.HESTVEIL.get(), HestveilParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.DRIPPING_ALKAHEST.get(), spriteSet -> (particleType, level, x, y, z, xSpeed, ySpeed, zSpeed, random) -> {
            DripParticle particle = new DripParticle.DripHangParticle(level, x, y, z, AetherIIFluids.ALKAHEST.get(), AetherIIParticleTypes.FALLING_ALKAHEST.get(), spriteSet.get(random));
            particle.setColor(0.65F, 0.9F, 0.6F);
            return particle;
        });
        event.registerSpriteSet(AetherIIParticleTypes.FALLING_ALKAHEST.get(), spriteSet -> (particleType, level, x, y, z, xSpeed, ySpeed, zSpeed, random) -> {
            DripParticle particle = new DripParticle.FallAndLandParticle(level, x, y, z, AetherIIFluids.ALKAHEST.get(), ParticleTypes.WHITE_SMOKE, spriteSet.get(random));
            particle.setColor(0.65F, 0.9F, 0.6F);
            return particle;
        });
        event.registerSpriteSet(AetherIIParticleTypes.DRIPPING_DRIPSTONE_ALKAHEST.get(), spriteSet -> (particleType, level, x, y, z, xSpeed, ySpeed, zSpeed, random) -> {
            DripParticle particle = new DripParticle.DripHangParticle(level, x, y, z, AetherIIFluids.ALKAHEST.get(), AetherIIParticleTypes.FALLING_DRIPSTONE_ALKAHEST.get(), spriteSet.get(random));
            particle.setColor(0.65F, 0.9F, 0.6F);
            return particle;
        });
        event.registerSpriteSet(AetherIIParticleTypes.FALLING_DRIPSTONE_ALKAHEST.get(), spriteSet -> (particleType, level, x, y, z, xSpeed, ySpeed, zSpeed, random) -> {
            DripParticle particle = new DripParticle.DripstoneFallAndLandParticle(level, x, y, z, AetherIIFluids.ALKAHEST.get(), ParticleTypes.WHITE_SMOKE, spriteSet.get(random)) {
                @Override
                protected void postMoveUpdate() {
                    if (this.onGround) {
                        BlockPos pos = BlockPos.containing(this.getPos()).below();
                        if (this.level.getBlockState(pos).isSolid() && !this.level.getBlockState(pos).is(AetherIITags.Blocks.ALKAHEST_RESISTANT)) {
                            AlkahestFluid.progressivelyDestroyBlock(this.level, pos, 3, true);
                        }
                    }
                    super.postMoveUpdate();
                }
            };
            particle.setColor(0.65F, 0.9F, 0.6F);
            return particle;
        });
        event.registerSpriteSet(AetherIIParticleTypes.GRAVITY_DUST.get(), GravityDustParticle.Provider::new);

        event.registerSpriteSet(AetherIIParticleTypes.RAIN.get(), spriteSet -> (particle, level, x, y, z, xSpeed, ySpeed, zSpeed, random) -> {
            WaterDropParticle rainParticle = new WaterDropParticle(level, x, y, z, spriteSet.get(random)) {
                @Override
                public SingleQuadParticle.Layer getLayer() {
                    return Layer.TRANSLUCENT;
                }
            };
//            rainParticle.pickSprite(spriteSet);
            return rainParticle;
        });
        event.registerSpriteSet(AetherIIParticleTypes.IRRADIATION.get(), IrradiationParticle.Factory::new);

        event.registerSpriteSet(AetherIIParticleTypes.SLASH_DAMAGE.get(), DamageTypeParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.IMPACT_DAMAGE.get(), DamageTypeParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.PIERCE_DAMAGE.get(), DamageTypeParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.SWEEP_ATTACK.get(), BlueAttackSweepParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.SHOCK_ATTACK.get(), YellowAttackShockParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.STAB_ATTACK.get(), RedAttackStabParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.EFFECT_BUILDUP.get(), SpellParticle.MobEffectProvider::new);

        event.registerSpriteSet(AetherIIParticleTypes.ZEPHYR_SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.TEMPEST_SMOKE.get(), TempestSmokeParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.MOA_HUNGRY.get(), HeartParticle.Provider::new);

        event.registerSpriteSet(AetherIIParticleTypes.LOCKED_BLOCK.get(), CopyBlockParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.BOSS_DOORWAY_BLOCK.get(), CopyBlockParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.TREASURE_DOORWAY_BLOCK.get(), CopyBlockParticle.Provider::new);
    }
}
