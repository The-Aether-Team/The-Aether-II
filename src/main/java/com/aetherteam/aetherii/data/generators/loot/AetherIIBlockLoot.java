package com.aetherteam.aetherii.data.generators.loot;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIIBlockLootSubProvider;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.BlockLootAccessor;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

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
        this.dropOther(AetherIIBlocks.AETHER_FARMLAND.get(), AetherIIBlocks.AETHER_DIRT.get());
        this.dropOther(AetherIIBlocks.AETHER_DIRT_PATH.get(), AetherIIBlocks.AETHER_DIRT.get());

        this.dropSelf(AetherIIBlocks.QUICKSOIL.get());
        this.dropSelf(AetherIIBlocks.FERROSITE_SAND.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE.get());
        this.dropSelf(AetherIIBlocks.MOSSY_HOLYSTONE.get());
        this.dropSelf(AetherIIBlocks.IRRADIATED_HOLYSTONE.get());
        this.dropSelf(AetherIIBlocks.UNDERSHALE.get());
        this.dropSelf(AetherIIBlocks.AGIOSITE.get());

        this.dropWithFortune(AetherIIBlocks.AMBROSIUM_ORE.get(), AetherIIItems.AMBROSIUM_SHARD.get());
        this.dropWithFortune(AetherIIBlocks.ZANITE_ORE.get(), AetherIIItems.ZANITE_GEMSTONE.get());
        this.dropWithFortune(AetherIIBlocks.ARKENIUM_ORE.get(), AetherIIItems.RAW_ARKENIUM.get());
        this.dropWithFortune(AetherIIBlocks.GRAVITITE_ORE.get(), AetherIIItems.RAW_GRAVITITE.get());

        this.dropSelf(AetherIIBlocks.COLD_AERCLOUD.get());
        this.dropSelf(AetherIIBlocks.BLUE_AERCLOUD.get());
        this.dropSelf(AetherIIBlocks.GOLDEN_AERCLOUD.get());
        this.dropSelf(AetherIIBlocks.GREEN_AERCLOUD.get());
        this.dropSelf(AetherIIBlocks.PURPLE_AERCLOUD.get());
        this.dropSelf(AetherIIBlocks.STORM_AERCLOUD.get());

        this.dropSelf(AetherIIBlocks.SKYROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_WOOD.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_WOOD.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_WOOD.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_WOOD.get());

        //TODO: Leaves Loot Tables
        this.add(AetherIIBlocks.SKYROOT_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, AetherIIBlocks.SKYROOT_SAPLING.get(), BlockLootAccessor.aetherII$getNormalLeavesSaplingChances()));
        this.dropNone(AetherIIBlocks.SKYPLANE_LEAVES.get());
        this.dropNone(AetherIIBlocks.SKYBIRCH_LEAVES.get());
        this.dropNone(AetherIIBlocks.SKYPINE_LEAVES.get());
        this.add(AetherIIBlocks.WISPROOT_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, AetherIIBlocks.WISPROOT_SAPLING.get(), BlockLootAccessor.aetherII$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.WISPTOP_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, AetherIIBlocks.WISPTOP_SAPLING.get(), BlockLootAccessor.aetherII$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.GREATROOT_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, AetherIIBlocks.GREATROOT_SAPLING.get(), BlockLootAccessor.aetherII$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.GREATOAK_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, AetherIIBlocks.GREATOAK_SAPLING.get(), BlockLootAccessor.aetherII$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.GREATBOA_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, AetherIIBlocks.GREATBOA_SAPLING.get(), BlockLootAccessor.aetherII$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.AMBEROOT_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, AetherIIBlocks.AMBEROOT_SAPLING.get(), BlockLootAccessor.aetherII$getNormalLeavesSaplingChances()));

        this.dropSelf(AetherIIBlocks.SKYROOT_SAPLING.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_SAPLING.get());
        this.dropSelf(AetherIIBlocks.WISPTOP_SAPLING.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_SAPLING.get());
        this.dropSelf(AetherIIBlocks.GREATOAK_SAPLING.get());
        this.dropSelf(AetherIIBlocks.GREATBOA_SAPLING.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_SAPLING.get());

        this.dropPottedContents(AetherIIBlocks.POTTED_SKYROOT_SAPLING.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_WISPROOT_SAPLING.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_WISPTOP_SAPLING.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_GREATROOT_SAPLING.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_GREATOAK_SAPLING.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_GREATBOA_SAPLING.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_AMBEROOT_SAPLING.get());

        //TODO
        this.dropNone(AetherIIBlocks.AETHER_SHORT_GRASS.get());
        this.dropNone(AetherIIBlocks.AETHER_MEDIUM_GRASS.get());
        this.dropNone(AetherIIBlocks.AETHER_LONG_GRASS.get());

        this.dropSelf(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get());

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

        this.dropSelf(AetherIIBlocks.HOLYSTONE_STAIRS.get());
        this.add(AetherIIBlocks.HOLYSTONE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.HOLYSTONE_WALL.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_BUTTON.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get());

        this.dropSelf(AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS.get());
        this.add(AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get());

        this.dropSelf(AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS.get());
        this.add(AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL.get());

        this.dropSelf(AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get());
        this.add(AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.HOLYSTONE_BRICK_WALL.get());

        this.dropSelf(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS.get());
        this.add(AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL.get());

        this.dropSelf(AetherIIBlocks.UNDERSHALE_STAIRS.get());
        this.add(AetherIIBlocks.UNDERSHALE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.UNDERSHALE_WALL.get());

        this.dropSelf(AetherIIBlocks.AGIOSITE_STAIRS.get());
        this.add(AetherIIBlocks.AGIOSITE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.AGIOSITE_WALL.get());

        this.dropSelf(AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.AGIOSITE_BRICK_STAIRS.get());
        this.add(AetherIIBlocks.AGIOSITE_BRICK_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.AGIOSITE_BRICK_WALL.get());

        this.dropWhenSilkTouch(AetherIIBlocks.QUICKSOIL_GLASS.get());
        this.dropWhenSilkTouch(AetherIIBlocks.QUICKSOIL_GLASS_PANE.get());

        this.dropSelf(AetherIIBlocks.CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.CLOUDWOOL_CARPET.get());

        this.dropSelf(AetherIIBlocks.AMBROSIUM_BLOCK.get());
        this.dropSelf(AetherIIBlocks.ZANITE_BLOCK.get());
        this.dropSelf(AetherIIBlocks.GRAVITITE_BLOCK.get());

        this.dropSelf(AetherIIBlocks.AMBROSIUM_TORCH.get());
        this.dropSelf(AetherIIBlocks.AMBROSIUM_WALL_TORCH.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_FURNACE.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_CHEST.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_LADDER.get());

        this.add(AetherIIBlocks.SKYROOT_BOOKSHELF.get(),
                (bookshelf) -> createSingleItemTableWithSilkTouch(bookshelf, Items.BOOK, ConstantValue.exactly(3)));
        this.add(AetherIIBlocks.HOLYSTONE_BOOKSHELF.get(),
                (bookshelf) -> createSingleItemTableWithSilkTouch(bookshelf, Items.BOOK, ConstantValue.exactly(3)));
    }

    @Override
    public Iterable<Block> getKnownBlocks() {
        return AetherIIBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
    }
}