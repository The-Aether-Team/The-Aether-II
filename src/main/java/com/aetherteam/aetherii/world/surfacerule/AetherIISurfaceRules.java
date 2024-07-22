package com.aetherteam.aetherii.world.surfacerule;

import com.aetherteam.aetherii.AetherII;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIISurfaceRules {
    public static final DeferredRegister<MapCodec<? extends SurfaceRules.RuleSource>> MATERIAL_RULES = DeferredRegister.create(BuiltInRegistries.MATERIAL_RULE, AetherII.MODID);

    public static DeferredHolder<MapCodec<? extends SurfaceRules.RuleSource>, MapCodec<NoisePalette3DPlacementRule>> NOISE_PALETTE_3D = MATERIAL_RULES.register("noise_palette_3d", NoisePalette3DPlacementRule.KEY_CODEC::codec);
}