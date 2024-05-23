package com.aetherteam.aetherii.data.generators.loot;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIIBlockLootSubProvider;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AetherIIBlockLoot extends AetherIIBlockLootSubProvider {
    private static final Set<Item> EXPLOSION_RESISTANT = Set.of();

    public AetherIIBlockLoot() {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        this.dropOther(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_DIRT.asItem());
        this.dropSelf(AetherIIBlocks.AETHER_DIRT.get());
        this.dropSelf(AetherIIBlocks.QUICKSOIL.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE.get());
        this.dropSelf(AetherIIBlocks.UNDERSHALE.get());

        this.dropWithFortune(AetherIIBlocks.AMBROSIUM_ORE.get(), AetherIIItems.AMBROSIUM_SHARD.get());
        this.dropWithFortune(AetherIIBlocks.ZANITE_ORE.get(), AetherIIItems.ZANITE_GEMSTONE.get());
        this.dropWithFortune(AetherIIBlocks.ARKENIUM_ORE.get(), AetherIIItems.RAW_ARKENIUM.get());
        this.dropWithFortune(AetherIIBlocks.GRAVITITE_ORE.get(), AetherIIItems.RAW_GRAVITITE.get());

        this.dropSelf(AetherIIBlocks.COLD_AERCLOUD.get());

        this.dropSelf(AetherIIBlocks.SKYROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_LOG.get());

        //todo
        this.dropNone(AetherIIBlocks.SKYROOT_LEAVES.get());
        this.dropNone(AetherIIBlocks.SKYPLANE_LEAVES.get());
        this.dropNone(AetherIIBlocks.SKYBIRCH_LEAVES.get());
        this.dropNone(AetherIIBlocks.SKYPINE_LEAVES.get());
        this.dropNone(AetherIIBlocks.WISPROOT_LEAVES.get());
        this.dropNone(AetherIIBlocks.WISPTOP_LEAVES.get());
        this.dropNone(AetherIIBlocks.GREATROOT_LEAVES.get());
        this.dropNone(AetherIIBlocks.GREATOAK_LEAVES.get());
        this.dropNone(AetherIIBlocks.GREATBOA_LEAVES.get());
        this.dropNone(AetherIIBlocks.AMBEROOT_LEAVES.get());
    }

    @Override
    public Iterable<Block> getKnownBlocks() {
        return AetherIIBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
    }
}
