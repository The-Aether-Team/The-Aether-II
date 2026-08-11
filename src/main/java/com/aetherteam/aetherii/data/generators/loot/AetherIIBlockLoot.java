package com.aetherteam.aetherii.data.generators.loot;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIIBlockLootSubProvider;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.BlockLootAccessor;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AetherIIBlockLoot extends AetherIIBlockLootSubProvider {
    protected static final float[] AMBEROOT_LEAVES_SAPLING_CHANCES = new float[]{0.0375F, 0.042F, 0.048F, 0.0615F, 0.1F};
    private static final Set<Item> EXPLOSION_RESISTANT = Set.of();

    public AetherIIBlockLoot(HolderLookup.Provider registries) {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {
        HolderGetter<Item> getter = this.registries.lookupOrThrow(Registries.ITEM);

        // Dirt
        this.add(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, AetherIIBlocks.AETHER_DIRT.get()));
        this.add(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, AetherIIBlocks.AETHER_DIRT.get()));
        this.dropOther(AetherIIBlocks.AETHER_DIRT_PATH.get(), AetherIIBlocks.AETHER_DIRT.get());
        this.dropSelf(AetherIIBlocks.AETHER_DIRT.get());
        this.dropSelf(AetherIIBlocks.COARSE_AETHER_DIRT.get());
        this.dropSelf(AetherIIBlocks.MYCELIAL_AETHER_DIRT.get());
        this.dropOther(AetherIIBlocks.AETHER_FARMLAND.get(), AetherIIBlocks.AETHER_DIRT.get());
        this.dropSelf(AetherIIBlocks.SHIMMERING_SILT.get());

        // Underground
        this.dropSelf(AetherIIBlocks.HOLYSTONE.get());
        this.dropWhenSilkTouch(AetherIIBlocks.UNSTABLE_HOLYSTONE.get());
        this.dropSelf(AetherIIBlocks.UNDERSHALE.get());
        this.dropWhenSilkTouch(AetherIIBlocks.UNSTABLE_UNDERSHALE.get());
        this.dropSelf(AetherIIBlocks.AGIOSITE.get());
        this.add(AetherIIBlocks.CRUDE_SCATTERGLASS.get(), block -> this.createSingleItemTableWithSilkTouch(block, AetherIIItems.SCATTERGLASS_SHARD, UniformGenerator.between(1.0F, 2.0F)));
        this.add(AetherIIBlocks.SKY_ROOTS.get(), this::createSkyRootsDrops);
        this.dropSelf(AetherIIBlocks.ICHORITE.get());
        this.dropSelf(AetherIIBlocks.POINTED_HOLYSTONE.get());
        this.dropSelf(AetherIIBlocks.POINTED_ICHORITE.get());

        // Highfields
        this.dropSelf(AetherIIBlocks.QUICKSOIL.get());
        this.dropSelf(AetherIIBlocks.MOSSY_HOLYSTONE.get());
        this.dropSelf(AetherIIBlocks.BRYALINN_MOSS_BLOCK.get());
        this.dropSelf(AetherIIBlocks.BRYALINN_MOSS_CARPET.get());
        this.add(AetherIIBlocks.BRYALINN_MOSS_VINES.get(), this::createShearsOnlyDrop);
        this.add(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get(), this.createSegmentedBlockDrops(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get()));
        this.dropSelf(AetherIIBlocks.TANGLED_BRANCHES.get());

        // Magnetic
        this.dropSelf(AetherIIBlocks.FERROSITE_SAND.get());
        this.dropSelf(AetherIIBlocks.FERROSITE_MUD.get());
        this.dropSelf(AetherIIBlocks.FERROSITE.get());
        this.dropSelf(AetherIIBlocks.RUSTED_FERROSITE.get());
        this.dropSelf(AetherIIBlocks.MAGNETIC_SHROOM.get());
        this.add(AetherIIBlocks.MAGNETIC_SHROOM_BLOCK.get(), block -> this.createMushroomBlockDrop(block, AetherIIBlocks.MAGNETIC_SHROOM.get()));
        this.add(AetherIIBlocks.SPOTTED_MAGNETIC_SHROOM_BLOCK.get(), block -> this.createMushroomBlockDrop(block, AetherIIBlocks.MAGNETIC_SHROOM.get()));
        this.add(AetherIIBlocks.MAGNETIC_SHROOM_STEM.get(), block -> this.createMushroomBlockDrop(block, AetherIIBlocks.MAGNETIC_SHROOM.get()));

        // Arctic
        this.add(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, AetherIIItems.ARCTIC_SNOWBALL, ConstantValue.exactly(4.0F)));
        this.add(AetherIIBlocks.ARCTIC_SNOW.get(), this::droppingSnowLayer);
        this.dropWhenSilkTouch(AetherIIBlocks.ARCTIC_ICE.get());
        this.dropWhenSilkTouch(AetherIIBlocks.FRAGILE_ARCTIC_ICE.get());
        this.dropWhenSilkTouch(AetherIIBlocks.ARCTIC_PACKED_ICE.get());
        this.dropSelf(AetherIIBlocks.ICESTONE.get());
        this.dropWhenSilkTouch(AetherIIBlocks.LARGE_ARCTIC_ICE_CRYSTAL.get());
        this.dropWhenSilkTouch(AetherIIBlocks.MEDIUM_ARCTIC_ICE_CRYSTAL.get());
        this.dropWhenSilkTouch(AetherIIBlocks.SMALL_ARCTIC_ICE_CRYSTAL.get());
        this.dropSelf(AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get());
        this.dropSelf(AetherIIBlocks.SHAYELINN_MOSS_CARPET.get());
        this.add(AetherIIBlocks.SHAYELINN_MOSS_VINES.get(), this::createShearsOnlyDrop);

        // Irradiated
        this.dropSelf(AetherIIBlocks.IRRADIATED_HOLYSTONE.get());
        this.add(AetherIIBlocks.IRRADIATED_DUST_BLOCK.get(), this::droppingIrradiatedDustLoot);
        this.dropSelf(AetherIIBlocks.AMBRELINN_MOSS_BLOCK.get());
        this.dropSelf(AetherIIBlocks.AMBRELINN_MOSS_CARPET.get());
        this.add(AetherIIBlocks.AMBRELINN_MOSS_VINES.get(), this::createShearsOnlyDrop);
        this.add(AetherIIBlocks.TARAHESP_FLOWERS.get(), this.createSegmentedBlockDrops(AetherIIBlocks.TARAHESP_FLOWERS.get()));

        // Ores
        this.add(AetherIIBlocks.HOLYSTONE_QUARTZ_ORE.get(), this::createQuartzOreDrops);
        this.dropWithFortune(AetherIIBlocks.AMBROSIUM_ORE.get(), AetherIIItems.AMBROSIUM_SHARD.get());
        this.dropWithFortune(AetherIIBlocks.ZANITE_ORE.get(), AetherIIItems.FOSSILIZED_ZANITE.get());
        this.dropWithFortune(AetherIIBlocks.GLINT_ORE.get(), AetherIIItems.FOSSILIZED_GLINT.get());
        this.dropWithFortune(AetherIIBlocks.ARKENIUM_ORE.get(), AetherIIItems.INERT_ARKENIUM.get());
        this.dropWithFortune(AetherIIBlocks.GRAVITITE_ORE.get(), AetherIIItems.INERT_GRAVITITE.get());
        this.dropWithFortune(AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE.get(), AetherIIItems.AMBROSIUM_SHARD.get());
        this.dropWithFortune(AetherIIBlocks.UNDERSHALE_ZANITE_ORE.get(), AetherIIItems.FOSSILIZED_ZANITE.get());
        this.dropWithFortune(AetherIIBlocks.UNDERSHALE_GLINT_ORE.get(), AetherIIItems.FOSSILIZED_GLINT.get());
        this.dropWithFortune(AetherIIBlocks.UNDERSHALE_ARKENIUM_ORE.get(), AetherIIItems.INERT_ARKENIUM.get());
        this.dropWithFortune(AetherIIBlocks.UNDERSHALE_GRAVITITE_ORE.get(), AetherIIItems.INERT_GRAVITITE.get());
        this.dropWithFortune(AetherIIBlocks.CORROBONITE_ORE.get(), AetherIIItems.FOSSILIZED_CORROBONITE.get());
        this.dropNone(AetherIIBlocks.CORROBONITE_CLUSTER.get());

        // Aerclouds
        this.dropSelf(AetherIIBlocks.COLD_AERCLOUD.get());
        this.dropSelf(AetherIIBlocks.BLUE_AERCLOUD.get());
        this.dropSelf(AetherIIBlocks.GOLDEN_AERCLOUD.get());
        this.dropSelf(AetherIIBlocks.GREEN_AERCLOUD.get());
        this.dropSelf(AetherIIBlocks.PURPLE_AERCLOUD.get());
        this.dropSelf(AetherIIBlocks.STORM_AERCLOUD.get());

        // Nest Blocks
        this.dropSelf(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get());
        this.dropSelf(AetherIIBlocks.ANIMAL_STASH.get());
        this.add(AetherIIBlocks.MOA_EGG.get(), this::droppingMoaEgg);

        // Logs
        this.dropSelf(AetherIIBlocks.SKYROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_GREATROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_WISPROOT_LOG.get());
        this.add(AetherIIBlocks.MOSSY_WISPROOT_LOG.get(), block -> this.createSingleItemTableWithSilkTouch(block, AetherIIBlocks.WISPROOT_LOG.get()));
        this.add(AetherIIBlocks.MOSSY_WISPROOT_LOG_BASE.get(), block -> this.createSingleItemTableWithSilkTouch(block, AetherIIBlocks.WISPROOT_LOG.get()));
        this.add(AetherIIBlocks.MOSSY_WISPROOT_WOOD.get(), block -> this.createSingleItemTableWithSilkTouch(block, AetherIIBlocks.WISPROOT_WOOD.get()));
        this.dropSelf(AetherIIBlocks.AMBEROOT_LOG.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_AMBEROOT_LOG.get());
        this.add(AetherIIBlocks.AMBEROOT_DEPOSIT.get(), (wood) -> this.droppingAmberoot(getter, wood, AetherIIBlocks.AMBEROOT_LOG.get(), AetherIIItems.GOLDEN_AMBER.get()));
        this.dropSelf(AetherIIBlocks.SKYROOT_WOOD.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_WOOD.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_GREATROOT_WOOD.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_WOOD.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_WISPROOT_WOOD.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_WOOD.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_AMBEROOT_WOOD.get());

        // Trunks
        this.dropSelf(AetherIIBlocks.SKYROOT_TRUNK.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_SKYROOT_TRUNK.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_TRUNK.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_GREATROOT_TRUNK.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_TRUNK.get());
        this.add(AetherIIBlocks.MOSSY_WISPROOT_TRUNK.get(), block -> this.createSingleItemTableWithSilkTouch(block, AetherIIBlocks.WISPROOT_TRUNK.get()));
        this.dropSelf(AetherIIBlocks.STRIPPED_WISPROOT_TRUNK.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_TRUNK.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_AMBEROOT_TRUNK.get());

        // Leaf Pile
        this.add(AetherIIBlocks.SKYROOT_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.SKYROOT_LEAVES.get()));
        this.add(AetherIIBlocks.SKYPLANE_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.SKYPLANE_LEAVES.get()));
        this.add(AetherIIBlocks.SKYBIRCH_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.SKYBIRCH_LEAVES.get()));
        this.add(AetherIIBlocks.SKYPINE_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.SKYPINE_LEAVES.get()));
        this.add(AetherIIBlocks.WISPROOT_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.WISPROOT_LEAVES.get()));
        this.add(AetherIIBlocks.WISPTOP_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.WISPTOP_LEAVES.get()));
        this.add(AetherIIBlocks.GREATROOT_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.GREATROOT_LEAVES.get()));
        this.add(AetherIIBlocks.GREATOAK_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.GREATOAK_LEAVES.get()));
        this.add(AetherIIBlocks.GREATBOA_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.GREATBOA_LEAVES.get()));
        this.add(AetherIIBlocks.AMBEROOT_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.AMBEROOT_LEAVES.get()));
        this.add(AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get()));
        this.add(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get()));
        this.add(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get()));
        this.add(AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get()));
        this.add(AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get()));
        this.add(AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get()));
        this.add(AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get()));
        this.add(AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get()));
        this.add(AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE.get(), (block) -> this.droppingLeafPile(block, AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get()));

        // Leaves
        this.add(AetherIIBlocks.SKYROOT_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticksWithLizard(leaves, AetherIIBlocks.SKYROOT_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.SKYPLANE_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticksWithLizard(leaves, AetherIIBlocks.SKYPLANE_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.SKYBIRCH_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticksWithLizard(leaves, AetherIIBlocks.SKYBIRCH_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.SKYPINE_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticksWithLizard(leaves, AetherIIBlocks.SKYPINE_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.WISPROOT_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticksWithLizard(leaves, AetherIIBlocks.WISPROOT_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.WISPTOP_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticksWithLizard(leaves, AetherIIBlocks.WISPTOP_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.GREATROOT_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticksWithLizard(leaves, AetherIIBlocks.GREATROOT_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.GREATOAK_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticksWithLizard(leaves, AetherIIBlocks.GREATOAK_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.GREATBOA_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticksWithLizard(leaves, AetherIIBlocks.GREATBOA_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.AMBEROOT_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticksWithLizard(leaves, AetherIIBlocks.AMBEROOT_SAPLING.get(), AMBEROOT_LEAVES_SAPLING_CHANCES));
        this.add(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticks(leaves, AetherIIBlocks.SKYROOT_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances())); //TODO
        this.add(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticks(leaves, AetherIIBlocks.SKYPLANE_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticks(leaves, AetherIIBlocks.SKYBIRCH_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticks(leaves, AetherIIBlocks.SKYPINE_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticks(leaves, AetherIIBlocks.WISPROOT_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticks(leaves, AetherIIBlocks.WISPTOP_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticks(leaves, AetherIIBlocks.GREATROOT_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticks(leaves, AetherIIBlocks.GREATOAK_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));
        this.add(AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get(), (leaves) -> this.droppingWithChancesAndSkyrootSticks(leaves, AetherIIBlocks.GREATBOA_SAPLING.get(), BlockLootAccessor.aether_ii$getNormalLeavesSaplingChances()));

        // Saplings
        this.dropSelf(AetherIIBlocks.SKYROOT_SAPLING.get());
        this.dropSelf(AetherIIBlocks.SKYPLANE_SAPLING.get());
        this.dropSelf(AetherIIBlocks.SKYBIRCH_SAPLING.get());
        this.dropSelf(AetherIIBlocks.SKYPINE_SAPLING.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_SAPLING.get());
        this.dropSelf(AetherIIBlocks.WISPTOP_SAPLING.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_SAPLING.get());
        this.dropSelf(AetherIIBlocks.GREATOAK_SAPLING.get());
        this.dropSelf(AetherIIBlocks.GREATBOA_SAPLING.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_SAPLING.get());

        // Potted Saplings
        this.dropPottedContents(AetherIIBlocks.POTTED_SKYROOT_SAPLING.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_SKYPLANE_SAPLING.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_SKYBIRCH_SAPLING.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_SKYPINE_SAPLING.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_WISPROOT_SAPLING.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_WISPTOP_SAPLING.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_GREATROOT_SAPLING.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_GREATOAK_SAPLING.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_GREATBOA_SAPLING.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_AMBEROOT_SAPLING.get());

        // Grasses
        this.add(AetherIIBlocks.SHORT_AETHER_GRASS.get(), this::createShearsOnlyDrop);
        this.add(AetherIIBlocks.MEDIUM_AETHER_GRASS.get(), this::createShearsOnlyDrop);
        this.add(AetherIIBlocks.TALL_AETHER_GRASS.get(), this::createShearsOnlyDrop);
        this.add(AetherIIBlocks.AETHER_FERN.get(), this::createShearsOnlyDrop);
        this.add(AetherIIBlocks.SHIELD_FERN.get(), this::createShearsOnlyDrop);

        // Flowers
        this.dropSelf(AetherIIBlocks.HESPEROSE.get());
        this.dropSelf(AetherIIBlocks.TARABLOOM.get());
        this.dropSelf(AetherIIBlocks.POASPROUT.get());
        this.dropSelf(AetherIIBlocks.LILICHIME.get());
        this.dropSelf(AetherIIBlocks.PLURACIAN.get());
        this.add(AetherIIBlocks.SATIVAL_SHOOT.get(), (shoot) -> this.droppingSativalShoot(getter, shoot, AetherIIItems.SATIVAL_BULB.get()));
        this.add(AetherIIBlocks.HOLPUPEA.get(), this.createSegmentedBlockDrops(AetherIIBlocks.HOLPUPEA.get()));
        this.add(AetherIIBlocks.BLADE_POA.get(), this::createShearsOnlyDrop);
        this.dropSelf(AetherIIBlocks.AECHOR_CUTTING.get());
        this.dropSelf(AetherIIBlocks.CARRION_CUTTING.get());

        // Potted Flowers
        this.dropPottedContents(AetherIIBlocks.POTTED_MAGNETIC_SHROOM.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_AETHER_FERN.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_SHIELD_FERN.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_HESPEROSE.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_TARABLOOM.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_POASPROUT.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_SATIVAL_SHOOT.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_LILICHIME.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_PLURACIAN.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_BLADE_POA.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_AECHOR_CUTTING.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_CARRION_CUTTING.get());

        // Bushes
        this.dropSelf(AetherIIBlocks.AETHER_BUSH.get());
        this.add(AetherIIBlocks.BLUEBERRY_BUSH.get(), (bush) -> this.droppingBerryBush(bush, AetherIIItems.BLUEBERRY.get()));
        this.dropSelf(AetherIIBlocks.BLUEBERRY_BUSH_STEM.get());

        // Potted Bushes
        this.dropPottedContents(AetherIIBlocks.POTTED_AETHER_BUSH.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_BLUEBERRY_BUSH.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_BLUEBERRY_BUSH_STEM.get());

        // Orange Tree
        this.add(AetherIIBlocks.ORANGE_TREE.get(), (tree) -> this.droppingOrangeTree(getter, tree, AetherIIItems.ORANGE.get()));

        // Potted Orange Tree
        this.dropPottedContents(AetherIIBlocks.POTTED_ORANGE_TREE.get());

        // Valkyrie Sprout
        this.add(AetherIIBlocks.VALKYRIE_SPROUT.get(), (sprout) -> this.droppingValkyrieSprout(sprout, AetherIIItems.VALKYRIE_WINGS.get()));

        // Brettl
        this.add(AetherIIBlocks.BRETTL_PLANT.get(), (brettl) -> this.droppingBrettlPlant(getter, brettl, AetherIIItems.BRETTL_CANE.get(), AetherIIItems.BRETTL_GRASS.get()));
        this.add(AetherIIBlocks.BRETTL_PLANT_TIP.get(), (brettl) -> this.droppingBrettlPlantTip(getter, brettl, AetherIIItems.BRETTL_CANE.get(), AetherIIBlocks.BRETTL_FLOWER.get()));
        this.dropSelf(AetherIIBlocks.BRETTL_FLOWER.get());

        // Lake
        this.dropOther(AetherIIBlocks.ARILUM_SHOOT.get(), AetherIIItems.ARILUM_BULBS.get());
        this.add(AetherIIBlocks.ARILUM.get(), this::createSilkTouchOrShearsTable);
        this.add(AetherIIBlocks.ARILUM_PLANT.get(), (plant) -> this.createSilkTouchOrShearsTable(AetherIIBlocks.ARILUM.get()));
        this.add(AetherIIBlocks.BLOOMING_ARILUM.get(), (plant) -> this.droppingArilumBulbs(getter, plant, AetherIIItems.ARILUM_BULBS.get()));
        this.add(AetherIIBlocks.BLOOMING_ARILUM_PLANT.get(), (plant) -> this.droppingArilumBulbs(getter, AetherIIBlocks.BLOOMING_ARILUM.get(), AetherIIItems.ARILUM_BULBS.get()));

        // Ground Decoration
        this.add(AetherIIBlocks.SKYROOT_TWIG.get(), this::dropTwigs);
        this.add(AetherIIBlocks.HOLYSTONE_ROCK.get(), this::dropRocks);

        // Skyroot Planks
        this.dropSelf(AetherIIBlocks.SKYROOT_PLANKS.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_STAIRS.get());
        this.add(AetherIIBlocks.SKYROOT_SLAB.get(), this::createSlabItemTable);
        this.add(AetherIIBlocks.SKYROOT_DOOR.get(), createDoorTable(AetherIIBlocks.SKYROOT_DOOR.get()));
        this.dropSelf(AetherIIBlocks.SKYROOT_TRAPDOOR.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_FENCE.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_FENCE_GATE.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_BUTTON.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_SHELF.get());

        // Skyroot Decorative Blocks
        this.dropSelf(AetherIIBlocks.SKYROOT_FLOORBOARDS.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_SHINGLES.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_SMALL_SHINGLES.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_BASE_PLANKS.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_TOP_PLANKS.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_BASE_BEAM.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_TOP_BEAM.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_BEAM.get());
        this.add(AetherIIBlocks.SECRET_SKYROOT_DOOR.get(), createDoorTable(AetherIIBlocks.SECRET_SKYROOT_DOOR.get()));
        this.dropSelf(AetherIIBlocks.SECRET_SKYROOT_TRAPDOOR.get());

        // Greatroot Planks
        this.dropSelf(AetherIIBlocks.GREATROOT_PLANKS.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_STAIRS.get());
        this.add(AetherIIBlocks.GREATROOT_SLAB.get(), this::createSlabItemTable);
        this.add(AetherIIBlocks.GREATROOT_DOOR.get(), createDoorTable(AetherIIBlocks.GREATROOT_DOOR.get()));
        this.dropSelf(AetherIIBlocks.GREATROOT_TRAPDOOR.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_FENCE.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_FENCE_GATE.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_BUTTON.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_SHELF.get());

        // Greatroot Decorative Blocks
        this.dropSelf(AetherIIBlocks.GREATROOT_FLOORBOARDS.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_SHINGLES.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_SMALL_SHINGLES.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_BASE_PLANKS.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_TOP_PLANKS.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_BASE_BEAM.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_TOP_BEAM.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_BEAM.get());
        this.add(AetherIIBlocks.SECRET_GREATROOT_DOOR.get(), createDoorTable(AetherIIBlocks.SECRET_GREATROOT_DOOR.get()));
        this.dropSelf(AetherIIBlocks.SECRET_GREATROOT_TRAPDOOR.get());

        // Wisproot Planks
        this.dropSelf(AetherIIBlocks.WISPROOT_PLANKS.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_STAIRS.get());
        this.add(AetherIIBlocks.WISPROOT_SLAB.get(), this::createSlabItemTable);
        this.add(AetherIIBlocks.WISPROOT_DOOR.get(), createDoorTable(AetherIIBlocks.WISPROOT_DOOR.get()));
        this.dropSelf(AetherIIBlocks.WISPROOT_TRAPDOOR.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_FENCE.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_FENCE_GATE.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_BUTTON.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_SHELF.get());

        // Wisproot Decorative Blocks
        this.dropSelf(AetherIIBlocks.WISPROOT_FLOORBOARDS.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_SHINGLES.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_SMALL_SHINGLES.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_BASE_PLANKS.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_TOP_PLANKS.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_BASE_BEAM.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_TOP_BEAM.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_BEAM.get());
        this.add(AetherIIBlocks.SECRET_WISPROOT_DOOR.get(), createDoorTable(AetherIIBlocks.SECRET_WISPROOT_DOOR.get()));
        this.dropSelf(AetherIIBlocks.SECRET_WISPROOT_TRAPDOOR.get());

        // Amberoot Planks
        this.dropSelf(AetherIIBlocks.AMBEROOT_PLANKS.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_STAIRS.get());
        this.add(AetherIIBlocks.AMBEROOT_SLAB.get(), this::createSlabItemTable);
        this.add(AetherIIBlocks.AMBEROOT_DOOR.get(), createDoorTable(AetherIIBlocks.AMBEROOT_DOOR.get()));
        this.dropSelf(AetherIIBlocks.AMBEROOT_TRAPDOOR.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_FENCE.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_FENCE_GATE.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_BUTTON.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_PRESSURE_PLATE.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_SHELF.get());

        // Amberoot Decorative Blocks
        this.dropSelf(AetherIIBlocks.AMBEROOT_FLOORBOARDS.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_HIGHLIGHT.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_SHINGLES.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_SMALL_SHINGLES.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_BASE_PLANKS.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_TOP_PLANKS.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_BASE_BEAM.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_TOP_BEAM.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_BEAM.get());
        this.add(AetherIIBlocks.SECRET_AMBEROOT_DOOR.get(), createDoorTable(AetherIIBlocks.SECRET_AMBEROOT_DOOR.get()));
        this.dropSelf(AetherIIBlocks.SECRET_AMBEROOT_TRAPDOOR.get());

        // Holystone
        this.dropSelf(AetherIIBlocks.HOLYSTONE_STAIRS.get());
        this.add(AetherIIBlocks.HOLYSTONE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.HOLYSTONE_WALL.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_BUTTON.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get());

        // Mossy Holystone
        this.dropSelf(AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS.get());
        this.add(AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get());

        // Irradiated Holystone
        this.dropSelf(AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS.get());
        this.add(AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL.get());

        // Holystone Bricks
        this.dropSelf(AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get());
        this.add(AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.HOLYSTONE_BRICK_WALL.get());

        // Holystone Decorative Blocks
        this.dropSelf(AetherIIBlocks.HOLYSTONE_FLAGSTONES.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_HEADSTONE.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_BASE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_CAPSTONE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_BASE_PILLAR.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_CAPSTONE_PILLAR.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_PILLAR.get());

        // Faded Holystone Bricks
        this.dropSelf(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS.get());
        this.add(AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL.get());

        // Faded Holystone Decorative Blocks
        this.dropSelf(AetherIIBlocks.FADED_HOLYSTONE_FLAGSTONES.get());
        this.dropSelf(AetherIIBlocks.FADED_HOLYSTONE_HEADSTONE.get());
        this.dropSelf(AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
        this.dropSelf(AetherIIBlocks.FADED_HOLYSTONE_BASE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.FADED_HOLYSTONE_BASE_PILLAR.get());
        this.dropSelf(AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_PILLAR.get());
        this.dropSelf(AetherIIBlocks.FADED_HOLYSTONE_PILLAR.get());

        // Undershale
        this.dropSelf(AetherIIBlocks.UNDERSHALE_STAIRS.get());
        this.add(AetherIIBlocks.UNDERSHALE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.UNDERSHALE_WALL.get());

        // Undershale Bricks
        this.dropSelf(AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.UNDERSHALE_BRICK_STAIRS.get());
        this.add(AetherIIBlocks.UNDERSHALE_BRICK_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.UNDERSHALE_BRICK_WALL.get());
        this.dropSelf(AetherIIBlocks.UNDERSHALE_BRICK_BUTTON.get());
        this.dropSelf(AetherIIBlocks.UNDERSHALE_BRICK_PRESSURE_PLATE.get());

        // Undershale Decorative Blocks
        this.dropSelf(AetherIIBlocks.UNDERSHALE_FLAGSTONES.get());
        this.dropSelf(AetherIIBlocks.UNDERSHALE_TILE.get());
        this.dropSelf(AetherIIBlocks.UNDERSHALE_BASE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.UNDERSHALE_CAPSTONE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.UNDERSHALE_BASE_PILLAR.get());
        this.dropSelf(AetherIIBlocks.UNDERSHALE_CAPSTONE_PILLAR.get());
        this.dropSelf(AetherIIBlocks.UNDERSHALE_PILLAR.get());

        // Sentry Bricks
        this.dropSelf(AetherIIBlocks.SENTRY_BRICKS.get());
        this.dropSelf(AetherIIBlocks.SENTRY_BRICK_STAIRS.get());
        this.add(AetherIIBlocks.SENTRY_BRICK_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.SENTRY_BRICK_WALL.get());
        this.dropSelf(AetherIIBlocks.SENTRY_BUTTON.get());

        // Sentry Decorative Blocks
        this.dropSelf(AetherIIBlocks.SENTRY_LIGHTSTONE.get());
        this.dropSelf(AetherIIBlocks.SENTRY_FLAGSTONES.get());
        this.dropSelf(AetherIIBlocks.SENTRY_TILE.get());
        this.dropSelf(AetherIIBlocks.SENTRY_BASE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.SENTRY_BASE_PILLAR.get());
        this.dropSelf(AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get());
        this.dropSelf(AetherIIBlocks.SENTRY_PILLAR.get());

        // Ichorite
        this.dropSelf(AetherIIBlocks.ICHORITE.get());
        this.dropSelf(AetherIIBlocks.ICHORITE_STAIRS.get());
        this.dropSelf(AetherIIBlocks.ICHORITE_SLAB.get());
        this.dropSelf(AetherIIBlocks.ICHORITE_WALL.get());

        // Smooth Ichorite
        this.dropSelf(AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.dropSelf(AetherIIBlocks.SMOOTH_ICHORITE_STAIRS.get());
        this.dropSelf(AetherIIBlocks.SMOOTH_ICHORITE_SLAB.get());
        this.dropSelf(AetherIIBlocks.SMOOTH_ICHORITE_WALL.get());

        // Ichorite Bricks
        this.dropSelf(AetherIIBlocks.ICHORITE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.ICHORITE_BRICK_STAIRS.get());
        this.dropSelf(AetherIIBlocks.ICHORITE_BRICK_SLAB.get());
        this.dropSelf(AetherIIBlocks.ICHORITE_BRICK_WALL.get());

        // Ichorite Decorative Blocks
        this.dropSelf(AetherIIBlocks.ICHORITE_FLAGSTONES.get());
        this.dropSelf(AetherIIBlocks.ICHORITE_RUNESTONE.get());
        this.dropSelf(AetherIIBlocks.ICHORITE_KEYSTONE.get());
        this.dropSelf(AetherIIBlocks.ICHORITE_BASE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.ICHORITE_CAPSTONE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.ICHORITE_BASE_PILLAR.get());
        this.dropSelf(AetherIIBlocks.ICHORITE_CAPSTONE_PILLAR.get());
        this.dropSelf(AetherIIBlocks.ICHORITE_PILLAR.get());

        // Marbled Ichorite
        this.dropSelf(AetherIIBlocks.MARBLED_ICHORITE.get());
        this.dropSelf(AetherIIBlocks.MARBLED_ICHORITE_STAIRS.get());
        this.dropSelf(AetherIIBlocks.MARBLED_ICHORITE_SLAB.get());
        this.dropSelf(AetherIIBlocks.MARBLED_ICHORITE_WALL.get());

        // Marbled Bricks
        this.dropSelf(AetherIIBlocks.MARBLED_BRICKS.get());
        this.dropSelf(AetherIIBlocks.MARBLED_BRICK_STAIRS.get());
        this.dropSelf(AetherIIBlocks.MARBLED_BRICK_SLAB.get());
        this.dropSelf(AetherIIBlocks.MARBLED_BRICK_WALL.get());

        // Marbled Ichorite Decorative Blocks
        this.dropSelf(AetherIIBlocks.MARBLED_FLAGSTONES.get());
        this.dropSelf(AetherIIBlocks.MARBLED_KEYSTONE.get());
        this.dropSelf(AetherIIBlocks.MARBLED_BASE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.MARBLED_CAPSTONE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.MARBLED_BASE_PILLAR.get());
        this.dropSelf(AetherIIBlocks.MARBLED_CAPSTONE_PILLAR.get());
        this.dropSelf(AetherIIBlocks.MARBLED_PILLAR.get());

        // Agiosite
        this.dropSelf(AetherIIBlocks.AGIOSITE_STAIRS.get());
        this.add(AetherIIBlocks.AGIOSITE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.AGIOSITE_WALL.get());

        // Agiosite Bricks
        this.dropSelf(AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.AGIOSITE_BRICK_STAIRS.get());
        this.add(AetherIIBlocks.AGIOSITE_BRICK_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.AGIOSITE_BRICK_WALL.get());

        // Agiosite Decorative Blocks
        this.dropSelf(AetherIIBlocks.AGIOSITE_FLAGSTONES.get());
        this.dropSelf(AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.dropSelf(AetherIIBlocks.AGIOSITE_BASE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.AGIOSITE_CAPSTONE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.AGIOSITE_BASE_PILLAR.get());
        this.dropSelf(AetherIIBlocks.AGIOSITE_CAPSTONE_PILLAR.get());
        this.dropSelf(AetherIIBlocks.AGIOSITE_PILLAR.get());

        // Icestone
        this.dropSelf(AetherIIBlocks.ICESTONE_STAIRS.get());
        this.add(AetherIIBlocks.ICESTONE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.ICESTONE_WALL.get());

        // Icestone Bricks
        this.dropSelf(AetherIIBlocks.ICESTONE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.ICESTONE_BRICK_STAIRS.get());
        this.add(AetherIIBlocks.ICESTONE_BRICK_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.ICESTONE_BRICK_WALL.get());

        // Icestone Decorative Blocks
        this.dropSelf(AetherIIBlocks.ICESTONE_FLAGSTONES.get());
        this.dropSelf(AetherIIBlocks.ICESTONE_KEYSTONE.get());
        this.dropSelf(AetherIIBlocks.ICESTONE_BASE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.ICESTONE_CAPSTONE_BRICKS.get());
        this.dropSelf(AetherIIBlocks.ICESTONE_BASE_PILLAR.get());
        this.dropSelf(AetherIIBlocks.ICESTONE_CAPSTONE_PILLAR.get());
        this.dropSelf(AetherIIBlocks.ICESTONE_PILLAR.get());

        // Glass
        this.dropWhenSilkTouch(AetherIIBlocks.QUICKSOIL_GLASS.get());
        this.dropWhenSilkTouch(AetherIIBlocks.TILED_QUICKSOIL_GLASS.get());
        this.dropWhenSilkTouch(AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS.get());
        this.dropWhenSilkTouch(AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS.get());
        this.dropWhenSilkTouch(AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS.get());
        this.dropSelf(AetherIIBlocks.SCATTERGLASS.get());
        this.dropWhenSilkTouch(AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS.get());
        this.dropWhenSilkTouch(AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS.get());

        // Glass Panes
        this.dropWhenSilkTouch(AetherIIBlocks.QUICKSOIL_GLASS_PANE.get());
        this.dropWhenSilkTouch(AetherIIBlocks.TILED_QUICKSOIL_GLASS_PANE.get());
        this.dropWhenSilkTouch(AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS_PANE.get());
        this.dropSelf(AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get());
        this.dropWhenSilkTouch(AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS_PANE.get());
        this.dropWhenSilkTouch(AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS_PANE.get());
        this.dropSelf(AetherIIBlocks.SCATTERGLASS_PANE.get());
        this.dropWhenSilkTouch(AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS_PANE.get());
        this.dropWhenSilkTouch(AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS_PANE.get());

        // Wool
        this.dropSelf(AetherIIBlocks.CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.WHITE_CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.ORANGE_CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.MAGENTA_CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.YELLOW_CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.LIME_CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.PINK_CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.GRAY_CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.CYAN_CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.PURPLE_CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.BLUE_CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.BROWN_CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.GREEN_CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.RED_CLOUDWOOL.get());
        this.dropSelf(AetherIIBlocks.BLACK_CLOUDWOOL.get());

        // Carpet
        this.dropSelf(AetherIIBlocks.CLOUDWOOL_CARPET.get());
        this.dropSelf(AetherIIBlocks.WHITE_CLOUDWOOL_CARPET.get());
        this.dropSelf(AetherIIBlocks.ORANGE_CLOUDWOOL_CARPET.get());
        this.dropSelf(AetherIIBlocks.MAGENTA_CLOUDWOOL_CARPET.get());
        this.dropSelf(AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL_CARPET.get());
        this.dropSelf(AetherIIBlocks.YELLOW_CLOUDWOOL_CARPET.get());
        this.dropSelf(AetherIIBlocks.LIME_CLOUDWOOL_CARPET.get());
        this.dropSelf(AetherIIBlocks.PINK_CLOUDWOOL_CARPET.get());
        this.dropSelf(AetherIIBlocks.GRAY_CLOUDWOOL_CARPET.get());
        this.dropSelf(AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL_CARPET.get());
        this.dropSelf(AetherIIBlocks.CYAN_CLOUDWOOL_CARPET.get());
        this.dropSelf(AetherIIBlocks.PURPLE_CLOUDWOOL_CARPET.get());
        this.dropSelf(AetherIIBlocks.BLUE_CLOUDWOOL_CARPET.get());
        this.dropSelf(AetherIIBlocks.BROWN_CLOUDWOOL_CARPET.get());
        this.dropSelf(AetherIIBlocks.GREEN_CLOUDWOOL_CARPET.get());
        this.dropSelf(AetherIIBlocks.RED_CLOUDWOOL_CARPET.get());
        this.dropSelf(AetherIIBlocks.BLACK_CLOUDWOOL_CARPET.get());

        // Roofing
        this.dropSelf(AetherIIBlocks.CLOUDWOOL_ROOFING.get());

        // Arkenium Blocks
        this.add(AetherIIBlocks.ARKENIUM_DOOR.get(), createDoorTable(AetherIIBlocks.ARKENIUM_DOOR.get()));
        this.dropSelf(AetherIIBlocks.ARKENIUM_TRAPDOOR.get());
        this.dropSelf(AetherIIBlocks.ARKENIUM_BARS.get());
        this.dropSelf(AetherIIBlocks.FLORAL_ARKENIUM_BARS.get());
        this.dropSelf(AetherIIBlocks.PATTERNED_ARKENIUM_BARS.get());
        this.dropSelf(AetherIIBlocks.CURVED_ARKENIUM_BARS.get());

        // Rustic Arkenium Blocks
        this.dropSelf(AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get());
        this.dropSelf(AetherIIBlocks.RUSTIC_FLORAL_ARKENIUM_BARS.get());
        this.dropSelf(AetherIIBlocks.RUSTIC_PATTERNED_ARKENIUM_BARS.get());
        this.dropSelf(AetherIIBlocks.RUSTIC_CURVED_ARKENIUM_BARS.get());

        // Inert Blocks
        this.dropSelf(AetherIIBlocks.INERT_ARKENIUM_BLOCK.get());
        this.dropSelf(AetherIIBlocks.INERT_GRAVITITE_BLOCK.get());

        // Mineral Blocks
        this.dropSelf(AetherIIBlocks.AMBROSIUM_BLOCK.get());
        this.dropSelf(AetherIIBlocks.ZANITE_BLOCK.get());
        this.dropSelf(AetherIIBlocks.ARKENIUM_BLOCK.get());
        this.dropSelf(AetherIIBlocks.GRAVITITE_BLOCK.get());
        this.dropSelf(AetherIIBlocks.GLINT_BLOCK.get());
        this.dropSelf(AetherIIBlocks.CORROBONITE_BLOCK.get());
        this.dropSelf(AetherIIBlocks.GOLDEN_AMBER_BLOCK.get());

        // Storage Blocks
        this.dropSelf(AetherIIBlocks.BRETTL_GRASS_BUNDLE.get());
        this.dropSelf(AetherIIBlocks.GEL_BLOCK.get());

        // Arilum Lantern
        this.dropSelf(AetherIIBlocks.WHITE_ARILUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.ORANGE_ARILUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.MAGENTA_ARILUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.LIGHT_BLUE_ARILUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.YELLOW_ARILUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.LIME_ARILUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.PINK_ARILUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.GRAY_ARILUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.LIGHT_GRAY_ARILUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.CYAN_ARILUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.PURPLE_ARILUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.BLUE_ARILUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.BROWN_ARILUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.GREEN_ARILUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.RED_ARILUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.BLACK_ARILUM_LANTERN.get());

        // Utility
        this.dropSelf(AetherIIBlocks.AMBROSIUM_TORCH.get());
        this.dropSelf(AetherIIBlocks.ARKENIUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.RUSTIC_ARKENIUM_LANTERN.get());
        this.dropSelf(AetherIIBlocks.ARKENIUM_CHAIN.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_FURNACE.get());
        this.dropSelf(AetherIIBlocks.HOLYSTONE_SMOKER.get());
        this.dropSelf(AetherIIBlocks.AMBER_HOURGLASS.get());
        this.dropSelf(AetherIIBlocks.ALTAR.get());
        this.dropSelf(AetherIIBlocks.ARKENIUM_FORGE.get());
        this.dropSelf(AetherIIBlocks.ARTISANS_BENCH.get());
        this.dropSelf(AetherIIBlocks.ALKAHEST_PURIFIER.get());
        this.dropSelf(AetherIIBlocks.MUSIC_BLOCK.get());
        this.add(AetherIIBlocks.AMBROSIUM_CAMPFIRE.get(), (block) -> this.createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(AetherIIItems.AMBROSIUM_SHARD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))));
        this.dropSelf(AetherIIBlocks.SKYROOT_CHEST.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_BARREL.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_LADDER.get());
        this.dropNone(AetherIIBlocks.BRETTL_ROPE_STAKE.get());
        this.dropNone(AetherIIBlocks.BRETTL_ROPE.get());
        this.add(AetherIIBlocks.CLOUDWOOL_BEDROLL.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));

        this.add(AetherIIBlocks.SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        this.add(AetherIIBlocks.WHITE_SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        this.add(AetherIIBlocks.ORANGE_SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        this.add(AetherIIBlocks.MAGENTA_SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        this.add(AetherIIBlocks.LIGHT_BLUE_SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        this.add(AetherIIBlocks.YELLOW_SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        this.add(AetherIIBlocks.LIME_SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        this.add(AetherIIBlocks.PINK_SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        this.add(AetherIIBlocks.GRAY_SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        this.add(AetherIIBlocks.LIGHT_GRAY_SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        this.add(AetherIIBlocks.CYAN_SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        this.add(AetherIIBlocks.PURPLE_SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        this.add(AetherIIBlocks.BLUE_SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        this.add(AetherIIBlocks.BROWN_SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        this.add(AetherIIBlocks.GREEN_SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        this.add(AetherIIBlocks.RED_SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        this.add(AetherIIBlocks.BLACK_SKYROOT_BED.get(), (bed) -> this.createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));

        this.dropWhenSilkTouch(AetherIIBlocks.HOLYSTONE_VASE.get());
        this.dropWhenSilkTouch(AetherIIBlocks.VERADEXIAN_VASE.get());
        this.dropWhenSilkTouch(AetherIIBlocks.BREXALLEN_VASE.get());

        this.dropSelf(AetherIIBlocks.SENTRY_CRATE.get());
        this.dropNone(AetherIIBlocks.SENTRY_SPAWNER.get());
        this.dropNone(AetherIIBlocks.SENTRY_TRAP.get());

        this.dropOther(AetherIIBlocks.SKYROOT_WALL_SIGN.get(), AetherIIBlocks.SKYROOT_SIGN.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_SIGN.get());

        this.dropOther(AetherIIBlocks.SKYROOT_WALL_HANGING_SIGN.get(), AetherIIBlocks.SKYROOT_HANGING_SIGN.get());
        this.dropSelf(AetherIIBlocks.SKYROOT_HANGING_SIGN.get());

        this.dropOther(AetherIIBlocks.GREATROOT_WALL_SIGN.get(), AetherIIBlocks.GREATROOT_SIGN.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_SIGN.get());

        this.dropOther(AetherIIBlocks.GREATROOT_WALL_HANGING_SIGN.get(), AetherIIBlocks.GREATROOT_HANGING_SIGN.get());
        this.dropSelf(AetherIIBlocks.GREATROOT_HANGING_SIGN.get());

        this.dropOther(AetherIIBlocks.WISPROOT_WALL_SIGN.get(), AetherIIBlocks.WISPROOT_SIGN.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_SIGN.get());

        this.dropOther(AetherIIBlocks.WISPROOT_WALL_HANGING_SIGN.get(), AetherIIBlocks.WISPROOT_HANGING_SIGN.get());
        this.dropSelf(AetherIIBlocks.WISPROOT_HANGING_SIGN.get());

        this.dropOther(AetherIIBlocks.AMBEROOT_WALL_SIGN.get(), AetherIIBlocks.AMBEROOT_SIGN.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_SIGN.get());

        this.dropOther(AetherIIBlocks.AMBEROOT_WALL_HANGING_SIGN.get(), AetherIIBlocks.AMBEROOT_HANGING_SIGN.get());
        this.dropSelf(AetherIIBlocks.AMBEROOT_HANGING_SIGN.get());

        this.dropSelf(AetherIIBlocks.HOLYSTONE_LEVER.get());

        // Bookshelves
        this.add(AetherIIBlocks.SKYROOT_BOOKSHELF.get(), (bookshelf) -> this.createSingleItemTableWithSilkTouch(bookshelf, Items.BOOK, ConstantValue.exactly(3)));
        this.add(AetherIIBlocks.GREATROOT_BOOKSHELF.get(), (bookshelf) -> this.createSingleItemTableWithSilkTouch(bookshelf, Items.BOOK, ConstantValue.exactly(3)));
        this.add(AetherIIBlocks.WISPROOT_BOOKSHELF.get(), (bookshelf) -> this.createSingleItemTableWithSilkTouch(bookshelf, Items.BOOK, ConstantValue.exactly(3)));
        this.add(AetherIIBlocks.AMBEROOT_BOOKSHELF.get(), (bookshelf) -> this.createSingleItemTableWithSilkTouch(bookshelf, Items.BOOK, ConstantValue.exactly(3)));
        this.add(AetherIIBlocks.HOLYSTONE_BOOKSHELF.get(), (bookshelf) -> this.createSingleItemTableWithSilkTouch(bookshelf, Items.BOOK, ConstantValue.exactly(3)));

        // Furniture
        this.dropNone(AetherIIBlocks.OUTPOST_CAMPFIRE.get());
        this.add(AetherIIBlocks.MURAL.get(), (mural) -> LootTable.lootTable()
            .withPool(this.applyExplosionCondition(mural, LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1.0F))
                    .add(LootItem.lootTableItem(mural)))
                .apply(CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
                    .include(AetherIIDataComponents.MURAL_SECTION.get())))
        );

        // Infected Guardian Tree
        // Guardian Wood
        this.dropSelf(AetherIIBlocks.GUARDIAN_LOG.get());
        this.dropSelf(AetherIIBlocks.GUARDIAN_WOOD.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_GUARDIAN_LOG.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_GUARDIAN_WOOD.get());

        // Infected Wood
        this.dropSelf(AetherIIBlocks.INFECTED_LOG.get());
        this.dropSelf(AetherIIBlocks.INFECTED_WOOD.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_INFECTED_LOG.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_INFECTED_WOOD.get());

        // Guardian Slabs
        this.add(AetherIIBlocks.GUARDIAN_LOG_SLAB.get(), this::createSlabItemTable);
        this.add(AetherIIBlocks.GUARDIAN_WOOD_SLAB.get(), this::createSlabItemTable);
        this.add(AetherIIBlocks.STRIPPED_GUARDIAN_LOG_SLAB.get(), this::createSlabItemTable);
        this.add(AetherIIBlocks.STRIPPED_GUARDIAN_WOOD_SLAB.get(), this::createSlabItemTable);
        this.add(AetherIIBlocks.INFECTED_LOG_SLAB.get(), this::createSlabItemTable);
        this.add(AetherIIBlocks.INFECTED_WOOD_SLAB.get(), this::createSlabItemTable);
        this.add(AetherIIBlocks.STRIPPED_INFECTED_LOG_SLAB.get(), this::createSlabItemTable);
        this.add(AetherIIBlocks.STRIPPED_INFECTED_WOOD_SLAB.get(), this::createSlabItemTable);

        // Guardian Trunks
        this.dropSelf(AetherIIBlocks.GUARDIAN_TRUNK.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_GUARDIAN_TRUNK.get());
        this.dropSelf(AetherIIBlocks.INFECTED_TRUNK.get());
        this.dropSelf(AetherIIBlocks.STRIPPED_INFECTED_TRUNK.get());

        // Guardian Root Blocks
        this.dropSelf(AetherIIBlocks.GUARDIAN_ROOTS.get());
        this.dropWhenSilkTouch(AetherIIBlocks.UNSTABLE_GUARDIAN_ROOTS.get());
        this.dropSelf(AetherIIBlocks.LUCENT_GUARDIAN_ROOTS.get());
        this.dropSelf(AetherIIBlocks.GUARDIAN_LAMP.get());

        // Undergrowth Blocks
        this.dropWhenSilkTouch(AetherIIBlocks.UNDERGROWTH_LEAVES.get());
        this.dropWhenSilkTouch(AetherIIBlocks.UNDERGROWTH_VINES.get());
        this.dropWhenSilkTouch(AetherIIBlocks.HANGING_UNDERGROWTH.get());
        this.otherWhenSilkTouch(AetherIIBlocks.HANGING_UNDERGROWTH_PLANT.get(), AetherIIBlocks.HANGING_UNDERGROWTH.get());

        // Rotshroom Blocks
        this.dropSelf(AetherIIBlocks.ROTSHROOM_BLOCK.get());
        this.add(AetherIIBlocks.ROTSHROOM_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(AetherIIBlocks.ROTSHROOM_STEM.get());
        this.dropSelf(AetherIIBlocks.SHELF_ROTSHROOM_SLAB.get());
        this.dropSelf(AetherIIBlocks.ROTSHROOM.get());
        this.dropPottedContents(AetherIIBlocks.POTTED_ROTSHROOM.get());
        this.dropSelf(AetherIIBlocks.ROTSHROOM_CLUSTER.get());
        this.dropSelf(AetherIIBlocks.ROTSHROOM_TOADSTOOL.get());
        this.dropSelf(AetherIIBlocks.SHELF_ROTSHROOM.get());

        // Dungeon Furniture
        this.dropSelf(AetherIIBlocks.PRAYER_CANDLE.get());
        this.dropSelf(AetherIIBlocks.GUARDIAN_PEW.get());
        this.dropSelf(AetherIIBlocks.GUARDIAN_DONATION_BOX.get());
        this.dropSelf(AetherIIBlocks.ABANDONED_BAG.get());
        this.dropSelf(AetherIIBlocks.FUNGAL_CACHE.get());
        this.dropSelf(AetherIIBlocks.SAGE_CHEST.get());
    }

    @Override
    public Iterable<Block> getKnownBlocks() {
        return AetherIIBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
    }
}