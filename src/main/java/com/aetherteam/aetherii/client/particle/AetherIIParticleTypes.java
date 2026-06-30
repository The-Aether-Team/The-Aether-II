package com.aetherteam.aetherii.client.particle;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.particle.options.AttackShockParticleOption;
import com.aetherteam.aetherii.client.particle.options.AttackStabParticleOption;
import com.aetherteam.aetherii.client.particle.options.ColorParticleOption;
import com.aetherteam.aetherii.client.particle.options.GravityDustParticleOption;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.*;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Function;

public class AetherIIParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, AetherII.MODID);

    public static final RegistryObject<SimpleParticleType> AETHER_PORTAL = PARTICLES.register("aether_portal", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SKYROOT_LEAVES = PARTICLES.register("skyroot_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SKYPLANE_LEAVES = PARTICLES.register("skyplane_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SKYBIRCH_LEAVES = PARTICLES.register("skybirch_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SKYPINE_LEAVES = PARTICLES.register("skypine_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> WISPROOT_LEAVES = PARTICLES.register("wisproot_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> WISPTOP_LEAVES = PARTICLES.register("wisptop_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> GREATROOT_LEAVES = PARTICLES.register("greatroot_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> GREATOAK_LEAVES = PARTICLES.register("greatoak_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> GREATBOA_LEAVES = PARTICLES.register("greatboa_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> AMBEROOT_LEAVES = PARTICLES.register("amberoot_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> IRRADIATED_LEAVES = PARTICLES.register("irradiated_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DRIPPING_WATER = PARTICLES.register("dripping_water", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_WATER = PARTICLES.register("falling_water", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SPLASH = PARTICLES.register("splash", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> AMBROSIUM = PARTICLES.register("ambrosium_torch", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> GLASS_FEATHERS = PARTICLES.register("glass_feathers", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ALKAHEST = PARTICLES.register("alkahest", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> HESTVEIL = PARTICLES.register("hestveil", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DRIPPING_ALKAHEST = PARTICLES.register("dripping_alkahest", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_ALKAHEST = PARTICLES.register("falling_alkahest", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DRIPPING_DRIPSTONE_ALKAHEST = PARTICLES.register("dripping_dripstone_alkahest", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_DRIPSTONE_ALKAHEST = PARTICLES.register("falling_dripstone_alkahest", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<GravityDustParticleOption>> GRAVITY_DUST = register("gravity_dust", false, GravityDustParticleOption.DESERIALIZER, p -> GravityDustParticleOption.CODEC);

    public static final RegistryObject<SimpleParticleType> RAIN = PARTICLES.register("rain", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> IRRADIATION = PARTICLES.register("irradiation", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> ZEPHYR_SNOWFLAKE = PARTICLES.register("zephyr_snowflake", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> TEMPEST_ELECTRICITY = PARTICLES.register("tempest_electricity.json", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SLASH_DAMAGE = PARTICLES.register("slash_damage", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> IMPACT_DAMAGE = PARTICLES.register("impact_damage", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> PIERCE_DAMAGE = PARTICLES.register("pierce_damage", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SWEEP_ATTACK = PARTICLES.register("sweep_attack", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<AttackShockParticleOption>> SHOCK_ATTACK = register("shock_attack", false, AttackShockParticleOption.DESERIALIZER, p -> AttackShockParticleOption.CODEC);
    public static final RegistryObject<ParticleType<AttackStabParticleOption>> STAB_ATTACK = register("stab_attack", false, AttackStabParticleOption.DESERIALIZER, p -> AttackStabParticleOption.CODEC);
    public static final RegistryObject<ParticleType<ColorParticleOption>> EFFECT_BUILDUP = register("effect_buildup", false, ColorParticleOption.deserializer(), ColorParticleOption::codec);

    public static final RegistryObject<SimpleParticleType> TEMPEST_SMOKE = PARTICLES.register("tempest_smoke", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> MOA_HUNGRY = PARTICLES.register("moa_hungry", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> LOCKED_BLOCK = PARTICLES.register("locked_block", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BOSS_DOORWAY_BLOCK = PARTICLES.register("boss_doorway_block", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> TREASURE_DOORWAY_BLOCK = PARTICLES.register("treasure_doorway_block", () -> new SimpleParticleType(true));

    private static <T extends ParticleOptions> RegistryObject<ParticleType<T>> register(String name, boolean overrideLimiter, ParticleOptions.Deserializer<T> deserializer, final Function<ParticleType<T>, Codec<T>> codecGetter) {
        return PARTICLES.register(name, () -> new ParticleType<T>(overrideLimiter, deserializer) {
            @Override
            public Codec<T> codec() {
                return codecGetter.apply(this);
            }
        });
    }
}
