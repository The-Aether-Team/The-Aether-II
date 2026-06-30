package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ParticleDescriptionProvider;

public class AetherIIParticleData extends ParticleDescriptionProvider {
    public AetherIIParticleData(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper);
    }

    @Override
    protected void addDescriptions() {
        this.spriteSet(AetherIIParticleTypes.AETHER_PORTAL.get(), new ResourceLocation("generic"), 8, false);
        this.spriteSet(AetherIIParticleTypes.SKYROOT_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.SKYPLANE_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.SKYBIRCH_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.SKYPINE_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.WISPROOT_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.WISPTOP_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.GREATROOT_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.GREATOAK_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.GREATBOA_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.AMBEROOT_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.IRRADIATED_LEAVES.get(), new ResourceLocation(AetherII.MODID, "leaf"), 4, false);
        this.spriteSet(AetherIIParticleTypes.DRIPPING_WATER.get(), new ResourceLocation(AetherII.MODID, "dripping_water"));
        this.spriteSet(AetherIIParticleTypes.FALLING_WATER.get(), new ResourceLocation(AetherII.MODID, "falling_water"));
        this.spriteSet(AetherIIParticleTypes.SPLASH.get(), new ResourceLocation(AetherII.MODID, "splash"), 4, false);
        this.spriteSet(AetherIIParticleTypes.AMBROSIUM.get(), new ResourceLocation("generic_0"), new ResourceLocation("generic_1"), new ResourceLocation(AetherII.MODID, "generic_1_mirrored"));
        this.spriteSet(AetherIIParticleTypes.GLASS_FEATHERS.get(), new ResourceLocation(AetherII.MODID, "glass_feathers"), 6, false);
        this.spriteSet(AetherIIParticleTypes.ALKAHEST.get(), new ResourceLocation(AetherII.MODID, "alkahest"), 6, false);
        this.spriteSet(AetherIIParticleTypes.HESTVEIL.get(), new ResourceLocation("generic_0"));
        this.spriteSet(AetherIIParticleTypes.DRIPPING_ALKAHEST.get(), new ResourceLocation("drip_hang"));
        this.spriteSet(AetherIIParticleTypes.FALLING_ALKAHEST.get(), new ResourceLocation("drip_fall"));
        this.spriteSet(AetherIIParticleTypes.DRIPPING_DRIPSTONE_ALKAHEST.get(), new ResourceLocation("drip_hang"));
        this.spriteSet(AetherIIParticleTypes.FALLING_DRIPSTONE_ALKAHEST.get(), new ResourceLocation("drip_fall"));
        this.spriteSet(AetherIIParticleTypes.GRAVITY_DUST.get(), new ResourceLocation("generic"), 8, true);

        this.spriteSet(AetherIIParticleTypes.RAIN.get(), new ResourceLocation(AetherII.MODID, "splash"), 4, false);
        this.spriteSet(AetherIIParticleTypes.IRRADIATION.get(), new ResourceLocation("generic_0"));

        this.spriteSet(AetherIIParticleTypes.ZEPHYR_SNOWFLAKE.get(), new ResourceLocation("generic"), 8, true);
        this.spriteSet(AetherIIParticleTypes.TEMPEST_ELECTRICITY.get(), new ResourceLocation("generic"), 8, true);
        this.spriteSet(AetherIIParticleTypes.SLASH_DAMAGE.get(), new ResourceLocation(AetherII.MODID, "slash_damage"));
        this.spriteSet(AetherIIParticleTypes.IMPACT_DAMAGE.get(), new ResourceLocation(AetherII.MODID, "impact_damage"));
        this.spriteSet(AetherIIParticleTypes.PIERCE_DAMAGE.get(), new ResourceLocation(AetherII.MODID, "pierce_damage"));
        this.spriteSet(AetherIIParticleTypes.SWEEP_ATTACK.get(), new ResourceLocation(AetherII.MODID, "sweep_attack"), 8, false);
        this.spriteSet(AetherIIParticleTypes.SHOCK_ATTACK.get(), new ResourceLocation(AetherII.MODID, "shock_attack"), 4, false);
        this.spriteSet(AetherIIParticleTypes.STAB_ATTACK.get(), new ResourceLocation(AetherII.MODID, "stab_attack"), 10, false);
        this.spriteSet(AetherIIParticleTypes.EFFECT_BUILDUP.get(), new ResourceLocation("generic"), 8, true);

        this.spriteSet(AetherIIParticleTypes.TEMPEST_SMOKE.get(), new ResourceLocation(AetherII.MODID, "tempest_smoke"), 12, false);
        this.spriteSet(AetherIIParticleTypes.MOA_HUNGRY.get(), new ResourceLocation(AetherII.MODID, "moa_hungry"));

        this.spriteSet(AetherIIParticleTypes.LOCKED_BLOCK.get(), new ResourceLocation(AetherII.MODID, "dungeon_lock"));
        this.spriteSet(AetherIIParticleTypes.BOSS_DOORWAY_BLOCK.get(), new ResourceLocation(AetherII.MODID, "dungeon_doorway"));
        this.spriteSet(AetherIIParticleTypes.TREASURE_DOORWAY_BLOCK.get(), new ResourceLocation(AetherII.MODID, "dungeon_treasure"));
    }
}
