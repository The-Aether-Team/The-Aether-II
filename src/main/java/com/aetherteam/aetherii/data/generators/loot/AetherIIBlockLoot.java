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
        this.dropSelf(AetherIIBlocks.MOSSY_HOLYSTONE.get());
        this.dropSelf(AetherIIBlocks.UNDERSHALE.get());

        this.dropWithFortune(AetherIIBlocks.AMBROSIUM_ORE.get(), AetherIIItems.AMBROSIUM_SHARD.get());
        this.dropWithFortune(AetherIIBlocks.ZANITE_ORE.get(), AetherIIItems.ZANITE_GEMSTONE.get());
        this.dropWithFortune(AetherIIBlocks.ARKENIUM_ORE.get(), AetherIIItems.RAW_ARKENIUM.get());
        this.dropWithFortune(AetherIIBlocks.GRAVITITE_ORE.get(), AetherIIItems.RAW_GRAVITITE.get());

        this.dropSelf(AetherIIBlocks.COLD_AERCLOUD.get());

        this.dropSelf(AetherIIBlocks.SKYROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_WOOD.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_WOOD.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_WOOD.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_WOOD.get());

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

        this.dropSelf(AetherIIBlocks.SKYROOT_PLANKS.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_STAIRS.get());
        this.add(AetherIIBlocks.SKYROOT_SLAB.get(), this::createSlabItemTable);
        this.add(AetherIIBlocks.SKYROOT_DOOR.get(), createDoorTable(AetherIIBlocks.SKYROOT_DOOR.get()));
        this.dropSelf(AetherIIBlocks.SKYROOT_TRAPDOOR.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_FENCE.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_FENCE_GATE.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_BUTTON.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get());

        this.dropSelf(AetherIIBlocks.GREATROOT_PLANKS.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_STAIRS.get());
        this.add(AetherIIBlocks.GREATROOT_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.GREATROOT_FENCE.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_FENCE_GATE.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_BUTTON.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get());

        this.dropSelf(AetherIIBlocks.WISPROOT_PLANKS.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_STAIRS.get());
        this.add(AetherIIBlocks.WISPROOT_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.WISPROOT_FENCE.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_FENCE_GATE.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_BUTTON.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get());

        this.dropSelf(AetherIIBlocks.HOLYSTONE_BUTTON.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_STAIRS.get());
        this.add(AetherIIBlocks.HOLYSTONE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.HOLYSTONE_WALL.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get());

        this.dropSelf(AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS.get());
        this.add(AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get());

        this.dropSelf(AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get());
        this.add(AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.HOLYSTONE_BRICK_WALL.get());

        this.dropWhenSilkTouch(AetherIIBlocks.QUICKSOIL_GLASS.get());
        this.dropWhenSilkTouch(AetherIIBlocks.QUICKSOIL_GLASS_PANE.get());

        this.dropSelf(AetherIIBlocks.CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.CLOUDWOOL_CARPET.get());

        this.dropSelf(AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_LADDER.get());
    }

    @Override
    public Iterable<Block> getKnownBlocks() {
        return AetherIIBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
    }
}