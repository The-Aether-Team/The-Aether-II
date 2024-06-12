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
        this.spriteSet(AetherIIParticleTypes.AETHER_PORTAL.get(), new ResourceLocation("generic"), 8, false);
        this.sprite(AetherIIParticleTypes.SKYROOT_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.SKYPLANE_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.SKYBIRCH_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.SKYPINE_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.WISPROOT_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.WISPTOP_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.GREATROOT_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.GREATOAK_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.GREATBOA_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.AMBEROOT_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaves"));
        this.sprite(AetherIIParticleTypes.DRIPPING_WATER.get(), new ResourceLocation(AetherII.MODID, "dripping_water"));
        this.sprite(AetherIIParticleTypes.FALLING_WATER.get(), new ResourceLocation(AetherII.MODID, "falling_water"));
        this.spriteSet(AetherIIParticleTypes.SPLASH.get(),  new ResourceLocation(AetherII.MODID, "splash"), 4, false);
        this.sprite(AetherIIParticleTypes.AMBROSIUM_TORCH.get(), new ResourceLocation("generic_0"));

        this.spriteSet(AetherIIParticleTypes.RAIN.get(), new ResourceLocation(AetherII.MODID, "splash"), 4, false);
        this.sprite(AetherIIParticleTypes.IRRADIATION.get(), new ResourceLocation("generic_0"));

        this.spriteSet(AetherIIParticleTypes.ZEPHYR_SNOWFLAKE.get(), new ResourceLocation("generic"), 8, true);
        this.sprite(AetherIIParticleTypes.SLASH_ATTACK.get(), new ResourceLocation(AetherII.MODID, "slash_attack"));
        this.sprite(AetherIIParticleTypes.IMPACT_ATTACK.get(), new ResourceLocation(AetherII.MODID, "impact_attack"));
        this.sprite(AetherIIParticleTypes.PIERCE_ATTACK.get(), new ResourceLocation(AetherII.MODID, "pierce_attack"));
    }
}
