package com.aetherteam.aetherii.world.surfacerule;

import com.aetherteam.aetherii.AetherII;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIRuleSources {
    public static final DeferredRegister<MapCodec<? extends SurfaceRules.RuleSource>> RULE_SOURCES = DeferredRegister.create(BuiltInRegistries.MATERIAL_RULE, AetherII.MODID);

    public static DeferredHolder<MapCodec<? extends SurfaceRules.RuleSource>, MapCodec<DensityFunctionRule>> DENSITY_FUNCTION_RULE = RULE_SOURCES.register("density_function_rule", DensityFunctionRule.KEY_CODEC::codec);
}