package com.aetherteam.aetherii.world.tree.trunk;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AetherIITrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, AetherII.MODID);

    public static final RegistryObject<TrunkPlacerType<MultiTreeTrunkPlacer>> MULTI_TREE_TRUNK_PLACER = TRUNK_PLACERS.register("multi_tree_trunk_placer", () -> new TrunkPlacerType<>(MultiTreeTrunkPlacer.CODEC.codec()));
}
