package com.aetherteam.aetherii.world.density;

import com.aetherteam.aetherii.AetherII;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AetherIIDensityFunctionTypes {
    public static final DeferredRegister<Codec<? extends DensityFunction>> DENSITY_FUNCTION_TYPES = DeferredRegister.create(Registries.DENSITY_FUNCTION_TYPE, AetherII.MODID);

    public static RegistryObject<? extends Codec<? extends DensityFunction>> PERLIN_NOISE = DENSITY_FUNCTION_TYPES.register("perlin_noise", PerlinNoiseFunction.CODEC::codec);
    public static RegistryObject<? extends Codec<? extends DensityFunction>> FIND_TOP_SURFACE = DENSITY_FUNCTION_TYPES.register("find_top_surface", FindTopSurfaceFunction.CODEC::codec);
}
