package com.aetherteam.aetherii.world.tree.foliage;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIFoliagePlacerTypes {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(BuiltInRegistries.FOLIAGE_PLACER_TYPE, AetherII.MODID);

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<LargeSkyrootFoliagePlacer>> LARGE_SKYROOT_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("large_skyroot_foliage_placer", () -> new FoliagePlacerType<>(LargeSkyrootFoliagePlacer.CODEC));
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<AmberootFoliagePlacer>> AMBEROOT_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("amberoot_foliage_placer", () -> new FoliagePlacerType<>(AmberootFoliagePlacer.CODEC));
}