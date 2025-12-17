package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.data.ParticleDescriptionProvider;

public class AetherIIParticleData extends ParticleDescriptionProvider {
    public AetherIIParticleData(PackOutput output) {
        super(output);
    }

    @Override
    protected void addDescriptions() {
        this.spriteSet(AetherIIParticleTypes.AETHER_PORTAL.get(), ResourceLocation.withDefaultNamespace("generic"), 8, false);
        this.spriteSet(AetherIIParticleTypes.SKYROOT_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.SKYPLANE_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.SKYBIRCH_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.SKYPINE_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.WISPROOT_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.WISPTOP_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.GREATROOT_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.GREATOAK_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.GREATBOA_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.AMBEROOT_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.IRRADIATED_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.sprite(AetherIIParticleTypes.DRIPPING_WATER.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "dripping_water"));
        this.sprite(AetherIIParticleTypes.FALLING_WATER.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "falling_water"));
        this.spriteSet(AetherIIParticleTypes.SPLASH.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "splash"), 4, false);
        this.spriteSet(AetherIIParticleTypes.AMBROSIUM.get(), ResourceLocation.withDefaultNamespace("generic_0"), ResourceLocation.withDefaultNamespace("generic_1"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "generic_1_mirrored"));
        this.spriteSet(AetherIIParticleTypes.ALKAHEST.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "alkahest"), 6, false);
        this.sprite(AetherIIParticleTypes.HESTVEIL.get(), ResourceLocation.withDefaultNamespace("generic_0"));
        this.sprite(AetherIIParticleTypes.DRIPPING_ALKAHEST.get(), ResourceLocation.withDefaultNamespace("drip_hang"));
        this.sprite(AetherIIParticleTypes.FALLING_ALKAHEST.get(), ResourceLocation.withDefaultNamespace("drip_fall"));
        this.sprite(AetherIIParticleTypes.DRIPPING_DRIPSTONE_ALKAHEST.get(), ResourceLocation.withDefaultNamespace("drip_hang"));
        this.sprite(AetherIIParticleTypes.FALLING_DRIPSTONE_ALKAHEST.get(), ResourceLocation.withDefaultNamespace("drip_fall"));

        this.spriteSet(AetherIIParticleTypes.RAIN.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "splash"), 4, false);
        this.sprite(AetherIIParticleTypes.IRRADIATION.get(), ResourceLocation.withDefaultNamespace("generic_0"));

        this.spriteSet(AetherIIParticleTypes.ZEPHYR_SNOWFLAKE.get(), ResourceLocation.withDefaultNamespace("generic"), 8, true);
        this.spriteSet(AetherIIParticleTypes.TEMPEST_ELECTRICITY.get(), ResourceLocation.withDefaultNamespace("generic"), 8, true);
        this.sprite(AetherIIParticleTypes.SLASH_DAMAGE.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "slash_damage"));
        this.sprite(AetherIIParticleTypes.IMPACT_DAMAGE.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "impact_damage"));
        this.sprite(AetherIIParticleTypes.PIERCE_DAMAGE.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "pierce_damage"));
        this.spriteSet(AetherIIParticleTypes.SWEEP_ATTACK.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sweep_attack"), 8, false);
        this.spriteSet(AetherIIParticleTypes.SHOCK_ATTACK.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "shock_attack"), 4, false);
        this.spriteSet(AetherIIParticleTypes.STAB_ATTACK.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "stab_attack"), 10, false);
        this.spriteSet(AetherIIParticleTypes.EFFECT_BUILDUP.get(), ResourceLocation.withDefaultNamespace("generic"), 8, true);

        this.spriteSet(AetherIIParticleTypes.TEMPEST_SMOKE.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "tempest_smoke"), 12, false);
        this.sprite(AetherIIParticleTypes.MOA_HUNGRY.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_hungry"));

        this.sprite(AetherIIParticleTypes.LOCKED_BLOCK.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "dungeon_lock"));
        this.sprite(AetherIIParticleTypes.BOSS_DOORWAY_BLOCK.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "dungeon_doorway"));
    }
}