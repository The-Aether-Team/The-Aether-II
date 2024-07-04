package com.aetherteam.aetherii.world.density;

import com.aetherteam.aetherii.AetherII;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIDensityFunctionTypes {
    public static final DeferredRegister<MapCodec<? extends DensityFunction>> DENSITY_FUNCTION_TYPES = DeferredRegister.create(BuiltInRegistries.DENSITY_FUNCTION_TYPE, AetherII.MODID);

    public static DeferredHolder<MapCodec<? extends DensityFunction>, ? extends MapCodec<? extends DensityFunction>> PERLIN_NOISE = DENSITY_FUNCTION_TYPES.register("perlin_noise", PerlinNoiseFunction.CODEC::codec);
}