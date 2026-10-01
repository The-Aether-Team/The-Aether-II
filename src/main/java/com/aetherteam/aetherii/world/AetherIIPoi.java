package com.aetherteam.aetherii.world;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Set;

public class AetherIIPoi {
    public static final DeferredRegister<PoiType> POI = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, AetherII.MODID);

    public static final DeferredHolder<PoiType, PoiType> AETHER_PORTAL = POI.register("aether_portal", () -> new PoiType(getBlockStates(AetherIIBlocks.AETHER_PORTAL.get()), 0, 1));
    public static final DeferredHolder<PoiType, PoiType> ZEPHYR_AVOID = POI.register("zephyr_avoid", () -> new PoiType(getLitOnlyBlockStates(AetherIIBlocks.AMBROSIUM_CAMPFIRE.get()), 0, 1));

    private static Set<BlockState> getBlockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }

    private static Set<BlockState> getLitOnlyBlockStates(Block block) {
        List<BlockState> blockStates = Lists.newArrayList(block.getStateDefinition().getPossibleStates());

        blockStates.removeIf(blockState -> {
            return !blockState.getValue(CampfireBlock.LIT);
        });

        return Set.copyOf(blockStates);
    }
}