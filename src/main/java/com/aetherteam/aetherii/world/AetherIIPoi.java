package com.aetherteam.aetherii.world;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

public class AetherIIPoi {
    public static final DeferredRegister<PoiType> POI = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, AetherII.MODID);

    public static final DeferredHolder<PoiType, PoiType> AETHER_PORTAL = POI.register("aether_portal", () -> new PoiType(getBlockStates(AetherIIBlocks.AETHER_PORTAL.get()), 0, 1));

    private static Set<BlockState> getBlockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }
}