package com.aetherteam.aetherii.world.surfacerule;

import com.aetherteam.aetherii.AetherII;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AetherIISurfaceRules {
    public static final DeferredRegister<Codec<? extends SurfaceRules.RuleSource>> MATERIAL_RULES = DeferredRegister.create(Registries.MATERIAL_RULE, AetherII.MODID);

    public static RegistryObject<Codec<NoisePalette3DPlacementRule>> NOISE_PALETTE_3D = MATERIAL_RULES.register("noise_palette_3d", NoisePalette3DPlacementRule.KEY_CODEC::codec);
}
