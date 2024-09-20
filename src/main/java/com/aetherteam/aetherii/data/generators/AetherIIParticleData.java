package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

public class AetherIIParticleData extends ParticleDescriptionProvider {
    public AetherIIParticleData(PackOutput output, ExistingFileHelper helper) {
        super(output, helper);
    }

    @Override
    protected void addDescriptions() {
        this.spriteSet(AetherIIParticleTypes.AETHER_PORTAL.get(), ResourceLocation.withDefaultNamespace("generic"), 8, false);
        this.sprite(AetherIIParticleTypes.SKYROOT_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.SKYPLANE_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.SKYBIRCH_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.SKYPINE_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.WISPROOT_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.WISPTOP_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.GREATROOT_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.GREATOAK_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.GREATBOA_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.AMBEROOT_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.IRRADIATED_LEAVES.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.DRIPPING_WATER.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "dripping_water"));
        this.sprite(AetherIIParticleTypes.FALLING_WATER.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "falling_water"));
        this.spriteSet(AetherIIParticleTypes.SPLASH.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "splash"), 4, false);
        this.spriteSet(AetherIIParticleTypes.AMBROSIUM.get(), ResourceLocation.withDefaultNamespace("generic_0"), ResourceLocation.withDefaultNamespace("generic_1"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "generic_1_mirrored"));

        this.spriteSet(AetherIIParticleTypes.RAIN.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "splash"), 4, false);
        this.sprite(AetherIIParticleTypes.IRRADIATION.get(), ResourceLocation.withDefaultNamespace("generic_0"));

        this.spriteSet(AetherIIParticleTypes.ZEPHYR_SNOWFLAKE.get(), ResourceLocation.withDefaultNamespace("generic"), 8, true);
        this.spriteSet(AetherIIParticleTypes.TEMPEST_ELECTRICITY.get(), ResourceLocation.withDefaultNamespace("generic"), 8, true);
        this.sprite(AetherIIParticleTypes.SLASH_ATTACK.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "slash_attack"));
        this.sprite(AetherIIParticleTypes.IMPACT_ATTACK.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "impact_attack"));
        this.sprite(AetherIIParticleTypes.PIERCE_ATTACK.get(), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "pierce_attack"));
    }
}