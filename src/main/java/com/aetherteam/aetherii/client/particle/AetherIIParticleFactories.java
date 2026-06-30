package com.aetherteam.aetherii.client.particle;

import com.aetherteam.aetherii.client.AetherIIClient;
import net.minecraft.client.particle.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;

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
        event.registerSpriteSet(AetherIIParticleTypes.DRIPPING_WATER.get(), AlkahestParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.FALLING_WATER.get(), AlkahestParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.SPLASH.get(), AlkahestParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.GLASS_FEATHERS.get(), GlassFeathersParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.AMBROSIUM.get(), AmbrosiumParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.ALKAHEST.get(), AlkahestParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.HESTVEIL.get(), HestveilParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.DRIPPING_ALKAHEST.get(), AlkahestParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.FALLING_ALKAHEST.get(), AlkahestParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.DRIPPING_DRIPSTONE_ALKAHEST.get(), AlkahestParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.FALLING_DRIPSTONE_ALKAHEST.get(), AlkahestParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.GRAVITY_DUST.get(), GravityDustParticle.Provider::new);

        event.registerSpriteSet(AetherIIParticleTypes.RAIN.get(), AlkahestParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.IRRADIATION.get(), IrradiationParticle.Factory::new);

        event.registerSpriteSet(AetherIIParticleTypes.SLASH_DAMAGE.get(), DamageTypeParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.IMPACT_DAMAGE.get(), DamageTypeParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.PIERCE_DAMAGE.get(), DamageTypeParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.SWEEP_ATTACK.get(), BlueAttackSweepParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.SHOCK_ATTACK.get(), YellowAttackShockParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.STAB_ATTACK.get(), RedAttackStabParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.EFFECT_BUILDUP.get(), EffectBuildupParticle.Provider::new);

        event.registerSpriteSet(AetherIIParticleTypes.ZEPHYR_SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.TEMPEST_SMOKE.get(), TempestSmokeParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.MOA_HUNGRY.get(), HeartParticle.Provider::new);

        event.registerSpriteSet(AetherIIParticleTypes.LOCKED_BLOCK.get(), CopyBlockParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.BOSS_DOORWAY_BLOCK.get(), CopyBlockParticle.Provider::new);
        event.registerSpriteSet(AetherIIParticleTypes.TREASURE_DOORWAY_BLOCK.get(), CopyBlockParticle.Provider::new);
    }
}
