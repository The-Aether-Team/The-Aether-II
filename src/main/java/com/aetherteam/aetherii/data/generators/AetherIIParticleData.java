package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.data.ParticleDescriptionProvider;

public class AetherIIParticleData extends ParticleDescriptionProvider {
    public AetherIIParticleData(PackOutput output) {
        super(output);
    }

    @Override
    protected void addDescriptions() {
        this.spriteSet(AetherIIParticleTypes.AETHER_PORTAL.get(), Identifier.withDefaultNamespace("generic"), 8, false);
        this.spriteSet(AetherIIParticleTypes.SKYROOT_LEAVES.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.SKYPLANE_LEAVES.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.SKYBIRCH_LEAVES.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.SKYPINE_LEAVES.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.WISPROOT_LEAVES.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.WISPTOP_LEAVES.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.GREATROOT_LEAVES.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.GREATOAK_LEAVES.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.GREATBOA_LEAVES.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.AMBEROOT_LEAVES.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.IRRADIATED_LEAVES.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.DRIPPING_WATER.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "dripping_water"));
        this.spriteSet(AetherIIParticleTypes.FALLING_WATER.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "falling_water"));
        this.spriteSet(AetherIIParticleTypes.SPLASH.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "splash"), 4, false);
        this.spriteSet(AetherIIParticleTypes.AMBROSIUM.get(), Identifier.withDefaultNamespace("generic_0"), Identifier.withDefaultNamespace("generic_1"), Identifier.fromNamespaceAndPath(AetherII.MODID, "generic_1_mirrored"));
        this.spriteSet(AetherIIParticleTypes.GLASS_FEATHERS.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "glass_feathers"), 6, false);
        this.spriteSet(AetherIIParticleTypes.ALKAHEST.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "alkahest"), 6, false);
        this.spriteSet(AetherIIParticleTypes.HESTVEIL.get(), Identifier.withDefaultNamespace("generic_0"));
        this.spriteSet(AetherIIParticleTypes.DRIPPING_ALKAHEST.get(), Identifier.withDefaultNamespace("drip_hang"));
        this.spriteSet(AetherIIParticleTypes.FALLING_ALKAHEST.get(), Identifier.withDefaultNamespace("drip_fall"));
        this.spriteSet(AetherIIParticleTypes.DRIPPING_DRIPSTONE_ALKAHEST.get(), Identifier.withDefaultNamespace("drip_hang"));
        this.spriteSet(AetherIIParticleTypes.FALLING_DRIPSTONE_ALKAHEST.get(), Identifier.withDefaultNamespace("drip_fall"));
        this.spriteSet(AetherIIParticleTypes.GRAVITY_DUST.get(), Identifier.withDefaultNamespace("generic"), 8, true);

        this.spriteSet(AetherIIParticleTypes.RAIN.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "splash"), 4, false);
        this.spriteSet(AetherIIParticleTypes.IRRADIATION.get(), Identifier.withDefaultNamespace("generic_0"));

        this.spriteSet(AetherIIParticleTypes.ZEPHYR_SNOWFLAKE.get(), Identifier.withDefaultNamespace("generic"), 8, true);
        this.spriteSet(AetherIIParticleTypes.TEMPEST_ELECTRICITY.get(), Identifier.withDefaultNamespace("generic"), 8, true);
        this.spriteSet(AetherIIParticleTypes.SLASH_DAMAGE.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "slash_damage"));
        this.spriteSet(AetherIIParticleTypes.IMPACT_DAMAGE.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "impact_damage"));
        this.spriteSet(AetherIIParticleTypes.PIERCE_DAMAGE.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "pierce_damage"));
        this.spriteSet(AetherIIParticleTypes.SWEEP_ATTACK.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "sweep_attack"), 8, false);
        this.spriteSet(AetherIIParticleTypes.SHOCK_ATTACK.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "shock_attack"), 4, false);
        this.spriteSet(AetherIIParticleTypes.STAB_ATTACK.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "stab_attack"), 10, false);
        this.spriteSet(AetherIIParticleTypes.EFFECT_BUILDUP.get(), Identifier.withDefaultNamespace("generic"), 8, true);

        this.spriteSet(AetherIIParticleTypes.TEMPEST_SMOKE.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "tempest_smoke"), 12, false);
        this.spriteSet(AetherIIParticleTypes.MOA_HUNGRY.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "moa_hungry"));

        this.spriteSet(AetherIIParticleTypes.LOCKED_BLOCK.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "dungeon_lock"));
        this.spriteSet(AetherIIParticleTypes.BOSS_DOORWAY_BLOCK.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "dungeon_doorway"));
        this.spriteSet(AetherIIParticleTypes.TREASURE_DOORWAY_BLOCK.get(), Identifier.fromNamespaceAndPath(AetherII.MODID, "dungeon_treasure"));
    }
}