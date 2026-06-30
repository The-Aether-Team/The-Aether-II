package com.aetherteam.aetherii.world.tree.foliage;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.world.tree.foliage.amberoot.AmberootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.amberoot.LargeAmberootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.amberoot.SingularAmberootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.greatroot.GreatboaFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.greatroot.GreatoakFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.greatroot.GreatrootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.skyroot.*;
import com.aetherteam.aetherii.world.tree.foliage.wisproot.WisprootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.wisproot.WisptopFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AetherIIFoliagePlacerTypes {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, AetherII.MODID);

    public static final RegistryObject<FoliagePlacerType<LargeSkyrootFoliagePlacer>> LARGE_SKYROOT_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("large_skyroot_foliage_placer", () -> new FoliagePlacerType<>(LargeSkyrootFoliagePlacer.CODEC.codec()));
    public static final RegistryObject<FoliagePlacerType<NestSkyrootFoliagePlacer>> NEST_SKYROOT_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("nest_skyroot_foliage_placer", () -> new FoliagePlacerType<>(NestSkyrootFoliagePlacer.CODEC.codec()));
    public static final RegistryObject<FoliagePlacerType<SkyplaneFoliagePlacer>> SKYPLANE_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("skyplane_foliage_placer", () -> new FoliagePlacerType<>(SkyplaneFoliagePlacer.CODEC.codec()));
    public static final RegistryObject<FoliagePlacerType<SkybirchFoliagePlacer>> SKYBIRCH_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("skybirch_foliage_placer", () -> new FoliagePlacerType<>(SkybirchFoliagePlacer.CODEC.codec()));
    public static final RegistryObject<FoliagePlacerType<SkypineFoliagePlacer>> SKYPINE_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("skypine_foliage_placer", () -> new FoliagePlacerType<>(SkypineFoliagePlacer.CODEC.codec()));

    public static final RegistryObject<FoliagePlacerType<WisprootFoliagePlacer>> WISPROOT_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("wisproot_foliage_placer", () -> new FoliagePlacerType<>(WisprootFoliagePlacer.CODEC.codec()));
    public static final RegistryObject<FoliagePlacerType<WisptopFoliagePlacer>> WISPTOP_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("wisptop_foliage_placer", () -> new FoliagePlacerType<>(WisptopFoliagePlacer.CODEC.codec()));

    public static final RegistryObject<FoliagePlacerType<GreatrootFoliagePlacer>> GREATROOT_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("greatroot_foliage_placer", () -> new FoliagePlacerType<>(GreatrootFoliagePlacer.CODEC.codec()));
    public static final RegistryObject<FoliagePlacerType<GreatoakFoliagePlacer>> GREATOAK_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("greatoak_foliage_placer", () -> new FoliagePlacerType<>(GreatoakFoliagePlacer.CODEC.codec()));
    public static final RegistryObject<FoliagePlacerType<GreatboaFoliagePlacer>> GREATBOA_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("greatboa_foliage_placer", () -> new FoliagePlacerType<>(GreatboaFoliagePlacer.CODEC.codec()));

    public static final RegistryObject<FoliagePlacerType<AmberootFoliagePlacer>> AMBEROOT_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("amberoot_foliage_placer", () -> new FoliagePlacerType<>(AmberootFoliagePlacer.CODEC.codec()));
    public static final RegistryObject<FoliagePlacerType<SingularAmberootFoliagePlacer>> SINGULAR_AMBEROOT_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("singular_amberoot_foliage_placer", () -> new FoliagePlacerType<>(SingularAmberootFoliagePlacer.CODEC.codec()));
    public static final RegistryObject<FoliagePlacerType<LargeAmberootFoliagePlacer>> LARGE_AMBEROOT_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("large_amberoot_foliage_placer", () -> new FoliagePlacerType<>(LargeAmberootFoliagePlacer.CODEC.codec()));
}
