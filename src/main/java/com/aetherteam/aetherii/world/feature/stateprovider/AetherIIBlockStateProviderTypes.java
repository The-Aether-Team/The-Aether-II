package com.aetherteam.aetherii.world.feature.stateprovider;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIBlockStateProviderTypes {
    public static final DeferredRegister<BlockStateProviderType<?>> BLOCK_STATE_PROVIDER_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE, AetherII.MODID);

    public static DeferredHolder<BlockStateProviderType<?>, BlockStateProviderType<DensityFunctionProvider>> DENSITY_FUNCTION_PROVIDER = BLOCK_STATE_PROVIDER_TYPES.register("density_function_provider", () -> new BlockStateProviderType<>(DensityFunctionProvider.CODEC));
}
