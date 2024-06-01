package com.aetherteam.aetherii.world.tree.trunk;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIITrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, AetherII.MODID);

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<AmberootTrunkPlacer>> AMBEROOT_TRUNK_PLACER = TRUNK_PLACERS.register("amberoot_trunk_placer", () -> new TrunkPlacerType<>(AmberootTrunkPlacer.CODEC));
}