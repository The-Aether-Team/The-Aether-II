package com.aetherteam.aetherii.world.tree.foliage;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIFoliagePlacerTypes {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(BuiltInRegistries.FOLIAGE_PLACER_TYPE, AetherII.MODID);

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<LargeSkyrootFoliagePlacer>> LARGE_SKYROOT_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("large_skyroot_foliage_placer", () -> new FoliagePlacerType<>(LargeSkyrootFoliagePlacer.CODEC));
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<WisprootFoliagePlacer>> WISPROOT_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("wisproot_foliage_placer", () -> new FoliagePlacerType<>(WisprootFoliagePlacer.CODEC));
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<WisptopFoliagePlacer>> WISPTOP_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("wisptop_foliage_placer", () -> new FoliagePlacerType<>(WisptopFoliagePlacer.CODEC));
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<GreatrootFoliagePlacer>> GREATROOT_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("greatroot_foliage_placer", () -> new FoliagePlacerType<>(GreatrootFoliagePlacer.CODEC));
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<GreatoakFoliagePlacer>> GREATOAK_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("greatoak_foliage_placer", () -> new FoliagePlacerType<>(GreatoakFoliagePlacer.CODEC));
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<GreatboaFoliagePlacer>> GREATBOA_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("greatboa_foliage_placer", () -> new FoliagePlacerType<>(GreatboaFoliagePlacer.CODEC));
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<AmberootFoliagePlacer>> AMBEROOT_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("amberoot_foliage_placer", () -> new FoliagePlacerType<>(AmberootFoliagePlacer.CODEC));
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<SingularAmberootFoliagePlacer>> SINGULAR_AMBEROOT_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("singular_amberoot_foliage_placer", () -> new FoliagePlacerType<>(SingularAmberootFoliagePlacer.CODEC));
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<LargeAmberootFoliagePlacer>> LARGE_AMBEROOT_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("large_amberoot_foliage_placer", () -> new FoliagePlacerType<>(LargeAmberootFoliagePlacer.CODEC));
}