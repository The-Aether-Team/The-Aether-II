package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.Mural;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIILanguageProvider;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.data.resources.registries.AetherIIMurals;
import com.aetherteam.aetherii.data.resources.registries.AetherIIStructures;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesBiomes;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import com.aetherteam.aetherii.item.AetherIICreativeTabs;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.TreeMap;

public class AetherIILanguageData extends AetherIILanguageProvider {
    public AetherIILanguageData(PackOutput output) {
        super(output, AetherII.MODID);
    }

    @Override
    protected void addTranslations() {
        this.addEntityTypes();
        this.addBlocks();
        this.addItems();
        this.addPerItemAbilityTooltips();
        this.addItemTooltips();
        this.addAccessorySlots();
        this.addBiomes();
        this.addStructures();
        this.addAttributes();
        this.addEffects();
        this.addCreativeTabs();
        this.addContainerTypes();
        this.addGuiText();
        this.addAdvancements();
        this.addBestiaryEntries();
        this.addEffectsDescriptions();
        this.addMusic();
        this.addSubtitles();
        this.addDeaths();
        this.addMuralTitles();
        this.addConfigs();

        // Dimensions
        this.addDimension(AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL, "Aether Holy Isles");

        // Misc
        this.addGeneric("slash", "§9Slash§r");
        this.addGeneric("impact", "§eImpact§r");
        this.addGeneric("pierce", "§cPierce§r");
        this.addGeneric("bedroll.too_dark", "You may not rest now; it is too dark to sleep here");
        this.addGeneric("message.campfire_added", "Outpost campfire respawn point set");
        this.addGeneric("message.campfire_respawn_failed", "Failed to locate a valid outpost campfire");
        this.addGeneric("message.passenger.onboard", "Press %1$s and %2$s to Dismount Passenger");
        this.add("mural.random", "Random variant");
        this.add("mural.dimensions", "%sx%s");
        this.add("mural.offset", "Section: (%s, %s)");

        // Packs
        this.addPackDescription("mod", "Aether II Resources");

        // Use Action
        this.addTooltip("item.modifiers.relic", "When wearing Relic:");
        this.addTooltip("item.modifiers.handwear", "When wearing Handwear:");
        this.addTooltip("item.modifiers.accessory", "When wearing Accessory:");
        this.addTooltip("item.modifiers.blocking", "When blocking:");
        this.addTooltip("item.modifiers.charms", "When attached:");

        // Keys
        this.add("key.category.aether_ii.general", "The Aether II");
        this.addKeyInfo("allow_dismounting_passenger.desc", "Allow Dismounting Passenger");

        // TODO WIP ALPHA THINGS
        this.addPerItemAbilityTooltip(AetherIIItems.KIRRID_PLATE.get(), 1, "§4§oWork in Progress§r§r");
        this.addPerItemAbilityTooltip(AetherIIItems.ZEPHYR_HUSK.get(), 1, "§4§oWork in Progress§r§r");
        this.addPerItemAbilityTooltip(AetherIIItems.CHARGE_CATALYST.get(), 1, "§4§oWork in Progress§r§r");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_CORE.get(), 1, "§4§oWork in Progress§r§r");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_CORE.get(), 1, "§4§oWork in Progress§r§r");
        this.addPerItemAbilityTooltip(AetherIIItems.EYE_OF_THE_MIMIC.get(), 1, "§4§oWork in Progress§r§r");
    }

    private void addBlocks() {
        // Portal
        this.addBlock(AetherIIBlocks.AETHER_PORTAL, "Aether Portal");

        // Dirt
        this.addBlock(AetherIIBlocks.AETHER_GRASS_BLOCK, "Aether Grass Block");
        this.addBlock(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK, "Enchanted Aether Grass Block");
        this.addBlock(AetherIIBlocks.AETHER_DIRT_PATH, "Aether Dirt Path");
        this.addBlock(AetherIIBlocks.AETHER_DIRT, "Aether Dirt");
        this.addBlock(AetherIIBlocks.COARSE_AETHER_DIRT, "Coarse Aether Dirt");
        this.addBlock(AetherIIBlocks.MYCELIAL_AETHER_DIRT, "Mycelial Aether Dirt");
        this.addBlock(AetherIIBlocks.AETHER_FARMLAND, "Aether Farmland");
        this.addBlock(AetherIIBlocks.SHIMMERING_SILT, "Shimmering Silt");

        // Underground
        this.addBlock(AetherIIBlocks.HOLYSTONE, "Holystone");
        this.addBlock(AetherIIBlocks.UNSTABLE_HOLYSTONE, "Unstable Holystone");
        this.addBlock(AetherIIBlocks.UNDERSHALE, "Undershale");
        this.addBlock(AetherIIBlocks.UNSTABLE_UNDERSHALE, "Unstable Undershale");
        this.addBlock(AetherIIBlocks.AGIOSITE, "Agiosite");
        this.addBlock(AetherIIBlocks.CRUDE_SCATTERGLASS, "Crude Scatterglass");
        this.addBlock(AetherIIBlocks.SKY_ROOTS, "Sky Roots");
        this.addBlock(AetherIIBlocks.ALKAHEST, "Alkahest");
        this.addBlock(AetherIIBlocks.HESTVEIL, "Hestveil");
        this.addBlock(AetherIIBlocks.POINTED_HOLYSTONE, "Pointed Holystone");
        this.addBlock(AetherIIBlocks.POINTED_ICHORITE, "Pointed Ichorite");

        // Highfields
        this.addBlock(AetherIIBlocks.QUICKSOIL, "Quicksoil");
        this.addBlock(AetherIIBlocks.MOSSY_HOLYSTONE, "Mossy Holystone");
        this.addBlock(AetherIIBlocks.BRYALINN_MOSS_BLOCK, "Bryalinn Moss Block");
        this.addBlock(AetherIIBlocks.BRYALINN_MOSS_CARPET, "Bryalinn Moss Carpet");
        this.addBlock(AetherIIBlocks.BRYALINN_MOSS_VINES, "Bryalinn Moss Vines");
        this.addBlock(AetherIIBlocks.BRYALINN_MOSS_FLOWERS, "Bryalinn Moss Flowers");
        this.addBlock(AetherIIBlocks.TANGLED_BRANCHES, "Tangled Branches");

        // Magnetic
        this.addBlock(AetherIIBlocks.FERROSITE_SAND, "Ferrosite Sand");
        this.addBlock(AetherIIBlocks.FERROSITE_MUD, "Ferrosite Mud");
        this.addBlock(AetherIIBlocks.FERROSITE, "Ferrosite");
        this.addBlock(AetherIIBlocks.RUSTED_FERROSITE, "Rusted Ferrosite");
        this.addBlock(AetherIIBlocks.MAGNETIC_SHROOM, "Magnetic Shroom");
        this.addBlock(AetherIIBlocks.MAGNETIC_SHROOM_BLOCK, "Magnetic Shroom Block");
        this.addBlock(AetherIIBlocks.SPOTTED_MAGNETIC_SHROOM_BLOCK, "Spotted Magnetic Shroom Block");
        this.addBlock(AetherIIBlocks.MAGNETIC_SHROOM_STEM, "Magnetic Shroom Stem");

        // Arctic
        this.addBlock(AetherIIBlocks.ARCTIC_SNOW_BLOCK, "Arctic Snow Block");
        this.addBlock(AetherIIBlocks.ARCTIC_SNOW, "Arctic Snow");
        this.addBlock(AetherIIBlocks.ARCTIC_ICE, "Arctic Ice");
        this.addBlock(AetherIIBlocks.FRAGILE_ARCTIC_ICE, "Fragile Arctic Ice");
        this.addBlock(AetherIIBlocks.ARCTIC_PACKED_ICE, "Arctic Packed Ice");
        this.addBlock(AetherIIBlocks.ICESTONE, "Icestone");
        this.addBlock(AetherIIBlocks.LARGE_ARCTIC_ICE_CRYSTAL, "Large Arctic Ice Crystal");
        this.addBlock(AetherIIBlocks.MEDIUM_ARCTIC_ICE_CRYSTAL, "Medium Arctic Ice Crystal");
        this.addBlock(AetherIIBlocks.SMALL_ARCTIC_ICE_CRYSTAL, "Small Arctic Ice Crystal");
        this.addBlock(AetherIIBlocks.SHAYELINN_MOSS_BLOCK, "Shayelinn Moss Block");
        this.addBlock(AetherIIBlocks.SHAYELINN_MOSS_CARPET, "Shayelinn Moss Carpet");
        this.addBlock(AetherIIBlocks.SHAYELINN_MOSS_VINES, "Shayelinn Moss Vines");

        // Irradiated
        this.addBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE, "Irradiated Holystone");
        this.addBlock(AetherIIBlocks.IRRADIATED_DUST_BLOCK, "Irradiated Dust Block");
        this.addBlock(AetherIIBlocks.AMBRELINN_MOSS_BLOCK, "Ambrelinn Moss Block");
        this.addBlock(AetherIIBlocks.AMBRELINN_MOSS_CARPET, "Ambrelinn Moss Carpet");
        this.addBlock(AetherIIBlocks.AMBRELINN_MOSS_VINES, "Ambrelinn Moss Vines");
        this.addBlock(AetherIIBlocks.TARAHESP_FLOWERS, "Tarahesp Flowers");

        // Ores
        this.addBlock(AetherIIBlocks.HOLYSTONE_QUARTZ_ORE, "Holystone Quartz Ore");
        this.addBlock(AetherIIBlocks.AMBROSIUM_ORE, "Ambrosium Ore");
        this.addBlock(AetherIIBlocks.ZANITE_ORE, "Zanite Ore");
        this.addBlock(AetherIIBlocks.ARKENIUM_ORE, "Arkenium Ore");
        this.addBlock(AetherIIBlocks.GRAVITITE_ORE, "Gravitite Ore");
        this.addBlock(AetherIIBlocks.GLINT_ORE, "Glint Ore");
        this.addBlock(AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE, "Undershale Ambrosium Ore");
        this.addBlock(AetherIIBlocks.UNDERSHALE_ZANITE_ORE, "Undershale Zanite Ore");
        this.addBlock(AetherIIBlocks.UNDERSHALE_ARKENIUM_ORE, "Undershale Arkenium Ore");
        this.addBlock(AetherIIBlocks.UNDERSHALE_GRAVITITE_ORE, "Undershale Gravitite Ore");
        this.addBlock(AetherIIBlocks.UNDERSHALE_GLINT_ORE, "Undershale Glint Ore");
        this.addBlock(AetherIIBlocks.CORROBONITE_ORE, "Corrobonite Ore");
        this.addBlock(AetherIIBlocks.CORROBONITE_CLUSTER, "Corrobonite Cluster");

        // Aerclouds
        this.addBlock(AetherIIBlocks.COLD_AERCLOUD, "Cold Aercloud");
        this.addBlock(AetherIIBlocks.BLUE_AERCLOUD, "Blue Aercloud");
        this.addBlock(AetherIIBlocks.GOLDEN_AERCLOUD, "Golden Aercloud");
        this.addBlock(AetherIIBlocks.GREEN_AERCLOUD, "Green Aercloud");
        this.addBlock(AetherIIBlocks.PURPLE_AERCLOUD, "Purple Aercloud");
        this.addBlock(AetherIIBlocks.STORM_AERCLOUD, "Storm Aercloud");

        // Nest Blocks
        this.addBlock(AetherIIBlocks.WOVEN_SKYROOT_STICKS, "Woven Skyroot Sticks");
        this.addBlock(AetherIIBlocks.ANIMAL_STASH, "Animal Stash");
        this.addBlock(AetherIIBlocks.MOA_EGG, "Moa Egg");

        // Logs
        this.addBlock(AetherIIBlocks.SKYROOT_LOG, "Skyroot Log");
        this.addBlock(AetherIIBlocks.STRIPPED_SKYROOT_LOG, "Stripped Skyroot Log");
        this.addBlock(AetherIIBlocks.GREATROOT_LOG, "Greatroot Log");
        this.addBlock(AetherIIBlocks.STRIPPED_GREATROOT_LOG, "Stripped Greatroot Log");
        this.addBlock(AetherIIBlocks.WISPROOT_LOG, "Wisproot Log");
        this.addBlock(AetherIIBlocks.MOSSY_WISPROOT_LOG, "Mossy Wisproot Log");
        this.addBlock(AetherIIBlocks.MOSSY_WISPROOT_LOG_BASE, "Mossy Wisproot Log Base");
        this.addBlock(AetherIIBlocks.STRIPPED_WISPROOT_LOG, "Stripped Wisproot Log");
        this.addBlock(AetherIIBlocks.AMBEROOT_LOG, "Amberoot Log");
        this.addBlock(AetherIIBlocks.AMBEROOT_DEPOSIT, "Amberoot Deposit");
        this.addBlock(AetherIIBlocks.STRIPPED_AMBEROOT_LOG, "Stripped Amberoot Log");
        this.addBlock(AetherIIBlocks.SKYROOT_WOOD, "Skyroot Wood");
        this.addBlock(AetherIIBlocks.STRIPPED_SKYROOT_WOOD, "Stripped Skyroot Wood");
        this.addBlock(AetherIIBlocks.GREATROOT_WOOD, "Greatroot Wood");
        this.addBlock(AetherIIBlocks.STRIPPED_GREATROOT_WOOD, "Stripped Greatroot Wood");
        this.addBlock(AetherIIBlocks.WISPROOT_WOOD, "Wisproot Wood");
        this.addBlock(AetherIIBlocks.MOSSY_WISPROOT_WOOD, "Mossy Wisproot Wood");
        this.addBlock(AetherIIBlocks.STRIPPED_WISPROOT_WOOD, "Stripped Wisproot Wood");
        this.addBlock(AetherIIBlocks.AMBEROOT_WOOD, "Amberoot Wood");
        this.addBlock(AetherIIBlocks.STRIPPED_AMBEROOT_WOOD, "Stripped Amberoot Wood");

        // Trunks
        this.addBlock(AetherIIBlocks.SKYROOT_TRUNK, "Skyroot Trunk");
        this.addBlock(AetherIIBlocks.STRIPPED_SKYROOT_TRUNK, "Stripped Skyroot Trunk");
        this.addBlock(AetherIIBlocks.GREATROOT_TRUNK, "Greatroot Trunk");
        this.addBlock(AetherIIBlocks.STRIPPED_GREATROOT_TRUNK, "Stripped Greatroot Trunk");
        this.addBlock(AetherIIBlocks.WISPROOT_TRUNK, "Wisproot Trunk");
        this.addBlock(AetherIIBlocks.MOSSY_WISPROOT_TRUNK, "Mossy Wisproot Trunk");
        this.addBlock(AetherIIBlocks.STRIPPED_WISPROOT_TRUNK, "Stripped Wisproot Trunk");
        this.addBlock(AetherIIBlocks.AMBEROOT_TRUNK, "Amberoot Trunk");
        this.addBlock(AetherIIBlocks.STRIPPED_AMBEROOT_TRUNK, "Stripped Amberoot Trunk");

        // Leaf Pile
        this.addBlock(AetherIIBlocks.SKYROOT_LEAF_PILE, "Skyroot Leaf Pile");
        this.addBlock(AetherIIBlocks.SKYPLANE_LEAF_PILE, "Skyplane Leaf Pile");
        this.addBlock(AetherIIBlocks.SKYBIRCH_LEAF_PILE, "Skybirch Leaf Pile");
        this.addBlock(AetherIIBlocks.SKYPINE_LEAF_PILE, "Skypine Leaf Pile");
        this.addBlock(AetherIIBlocks.WISPROOT_LEAF_PILE, "Wisproot Leaf Pile");
        this.addBlock(AetherIIBlocks.WISPTOP_LEAF_PILE, "Wisptop Leaf Pile");
        this.addBlock(AetherIIBlocks.GREATROOT_LEAF_PILE, "Greatroot Leaf Pile");
        this.addBlock(AetherIIBlocks.GREATOAK_LEAF_PILE, "Greatoak Leaf Pile");
        this.addBlock(AetherIIBlocks.GREATBOA_LEAF_PILE, "Greatboa Leaf Pile");
        this.addBlock(AetherIIBlocks.AMBEROOT_LEAF_PILE, "Amberoot Leaf Pile");
        this.addBlock(AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE, "Irradiated Skyroot Leaf Pile");
        this.addBlock(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE, "Irradiated Skyplane Leaf Pile");
        this.addBlock(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE, "Irradiated Skybirch Leaf Pile");
        this.addBlock(AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE, "Irradiated Skypine Leaf Pile");
        this.addBlock(AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE, "Irradiated Wisproot Leaf Pile");
        this.addBlock(AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE, "Irradiated Wisptop Leaf Pile");
        this.addBlock(AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE, "Irradiated Greatroot Leaf Pile");
        this.addBlock(AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE, "Irradiated Greatoak Leaf Pile");
        this.addBlock(AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE, "Irradiated Greatboa Leaf Pile");

        // Leaves
        this.addBlock(AetherIIBlocks.SKYROOT_LEAVES, "Skyroot Leaves");
        this.addBlock(AetherIIBlocks.SKYPLANE_LEAVES, "Skyplane Leaves");
        this.addBlock(AetherIIBlocks.SKYBIRCH_LEAVES, "Skybirch Leaves");
        this.addBlock(AetherIIBlocks.SKYPINE_LEAVES, "Skypine Leaves");
        this.addBlock(AetherIIBlocks.WISPROOT_LEAVES, "Wisproot Leaves");
        this.addBlock(AetherIIBlocks.WISPTOP_LEAVES, "Wisptop Leaves");
        this.addBlock(AetherIIBlocks.GREATROOT_LEAVES, "Greatroot Leaves");
        this.addBlock(AetherIIBlocks.GREATOAK_LEAVES, "Greatoak Leaves");
        this.addBlock(AetherIIBlocks.GREATBOA_LEAVES, "Greatboa Leaves");
        this.addBlock(AetherIIBlocks.AMBEROOT_LEAVES, "Amberoot Leaves");
        this.addBlock(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES, "Irradiated Skyroot Leaves");
        this.addBlock(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES, "Irradiated Skyplane Leaves");
        this.addBlock(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES, "Irradiated Skybirch Leaves");
        this.addBlock(AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES, "Irradiated Skypine Leaves");
        this.addBlock(AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES, "Irradiated Wisproot Leaves");
        this.addBlock(AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES, "Irradiated Wisptop Leaves");
        this.addBlock(AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES, "Irradiated Greatroot Leaves");
        this.addBlock(AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES, "Irradiated Greatoak Leaves");
        this.addBlock(AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES, "Irradiated Greatboa Leaves");

        // Saplings
        this.addBlock(AetherIIBlocks.SKYROOT_SAPLING, "Skyroot Sapling");
        this.addBlock(AetherIIBlocks.SKYPLANE_SAPLING, "Skyplane Sapling");
        this.addBlock(AetherIIBlocks.SKYBIRCH_SAPLING, "Skybirch Sapling");
        this.addBlock(AetherIIBlocks.SKYPINE_SAPLING, "Skypine Sapling");
        this.addBlock(AetherIIBlocks.WISPROOT_SAPLING, "Wisproot Sapling");
        this.addBlock(AetherIIBlocks.WISPTOP_SAPLING, "Wisptop Sapling");
        this.addBlock(AetherIIBlocks.GREATROOT_SAPLING, "Greatroot Sapling");
        this.addBlock(AetherIIBlocks.GREATOAK_SAPLING, "Greatoak Sapling");
        this.addBlock(AetherIIBlocks.GREATBOA_SAPLING, "Greatboa Sapling");
        this.addBlock(AetherIIBlocks.AMBEROOT_SAPLING, "Amberoot Sapling");

        // Potted Saplings
        this.addBlock(AetherIIBlocks.POTTED_SKYROOT_SAPLING, "Potted Skyroot Sapling");
        this.addBlock(AetherIIBlocks.POTTED_SKYPLANE_SAPLING, "Potted Skyplane Sapling");
        this.addBlock(AetherIIBlocks.POTTED_SKYBIRCH_SAPLING, "Potted Skybirch Sapling");
        this.addBlock(AetherIIBlocks.POTTED_SKYPINE_SAPLING, "Potted Skypine Sapling");
        this.addBlock(AetherIIBlocks.POTTED_WISPROOT_SAPLING, "Potted Wisproot Sapling");
        this.addBlock(AetherIIBlocks.POTTED_WISPTOP_SAPLING, "Potted Wisptop Sapling");
        this.addBlock(AetherIIBlocks.POTTED_GREATROOT_SAPLING, "Potted Greatroot Sapling");
        this.addBlock(AetherIIBlocks.POTTED_GREATOAK_SAPLING, "Potted Greatoak Sapling");
        this.addBlock(AetherIIBlocks.POTTED_GREATBOA_SAPLING, "Potted Greatboa Sapling");
        this.addBlock(AetherIIBlocks.POTTED_AMBEROOT_SAPLING, "Potted Amberoot Sapling");

        // Grasses
        this.addBlock(AetherIIBlocks.SHORT_AETHER_GRASS, "Short Aether Grass");
        this.addBlock(AetherIIBlocks.MEDIUM_AETHER_GRASS, "Medium Aether Grass");
        this.addBlock(AetherIIBlocks.TALL_AETHER_GRASS, "Tall Aether Grass");
        this.addBlock(AetherIIBlocks.AETHER_FERN, "Aether Fern");
        this.addBlock(AetherIIBlocks.SHIELD_FERN, "Shield Fern");

        // Flowers
        this.addBlock(AetherIIBlocks.HESPEROSE, "Hesperose");
        this.addBlock(AetherIIBlocks.TARABLOOM, "Tarabloom");
        this.addBlock(AetherIIBlocks.POASPROUT, "Poasprout");
        this.addBlock(AetherIIBlocks.LILICHIME, "Lilichime");
        this.addBlock(AetherIIBlocks.PLURACIAN, "Pluracian");
        this.addBlock(AetherIIBlocks.SATIVAL_SHOOT, "Satival Shoot");
        this.addBlock(AetherIIBlocks.HOLPUPEA, "Holpupea");
        this.addBlock(AetherIIBlocks.BLADE_POA, "Blade Poa");
        this.addBlock(AetherIIBlocks.AECHOR_CUTTING, "Aechor Cutting");
        this.addBlock(AetherIIBlocks.CARRION_CUTTING, "Carrion Cutting");

        // Potted Flowers
        this.addBlock(AetherIIBlocks.POTTED_MAGNETIC_SHROOM, "Potted Magnetic Shroom");
        this.addBlock(AetherIIBlocks.POTTED_AETHER_FERN, "Potted Aether Fern");
        this.addBlock(AetherIIBlocks.POTTED_SHIELD_FERN, "Potted Shield Fern");
        this.addBlock(AetherIIBlocks.POTTED_HESPEROSE, "Potted Hesperose");
        this.addBlock(AetherIIBlocks.POTTED_TARABLOOM, "Potted Tarabloom");
        this.addBlock(AetherIIBlocks.POTTED_POASPROUT, "Potted Poasprout");
        this.addBlock(AetherIIBlocks.POTTED_SATIVAL_SHOOT, "Potted Satival Shoot");
        this.addBlock(AetherIIBlocks.POTTED_LILICHIME, "Potted Lilichime");
        this.addBlock(AetherIIBlocks.POTTED_PLURACIAN, "Potted Pluracian");
        this.addBlock(AetherIIBlocks.POTTED_BLADE_POA, "Potted Blade Poa");
        this.addBlock(AetherIIBlocks.POTTED_AECHOR_CUTTING, "Potted Aechor Cutting");
        this.addBlock(AetherIIBlocks.POTTED_CARRION_CUTTING, "Potted Carrion Cutting");

        // Bushes
        this.addBlock(AetherIIBlocks.AETHER_BUSH, "Aether Bush");
        this.addBlock(AetherIIBlocks.BLUEBERRY_BUSH, "Blueberry Bush");
        this.addBlock(AetherIIBlocks.BLUEBERRY_BUSH_STEM, "Blueberry Bush Stem");

        // Orange Tree
        this.addBlock(AetherIIBlocks.ORANGE_TREE, "Orange Tree");

        // Valkyrie Sprout
        this.addBlock(AetherIIBlocks.VALKYRIE_SPROUT, "Valkyrie Sprout");

        // Brettl
        this.addBlock(AetherIIBlocks.BRETTL_PLANT, "Brettl Plant");
        this.addBlock(AetherIIBlocks.BRETTL_PLANT_TIP, "Brettl Plant Tip");
        this.addBlock(AetherIIBlocks.BRETTL_FLOWER, "Brettl Flower");

        // Lake
        this.addBlock(AetherIIBlocks.ARILUM_SHOOT, "Arilum");
        this.addBlock(AetherIIBlocks.ARILUM, "Arilum");
        this.addBlock(AetherIIBlocks.ARILUM_PLANT, "Arilum");
        this.addBlock(AetherIIBlocks.BLOOMING_ARILUM, "Blooming Arilum");
        this.addBlock(AetherIIBlocks.BLOOMING_ARILUM_PLANT, "Blooming Arilum");

        // Ground Decoration
        this.addBlock(AetherIIBlocks.SKYROOT_TWIG, "Skyroot Twig");
        this.addBlock(AetherIIBlocks.HOLYSTONE_ROCK, "Holystone Rock");

        // Skyroot Planks
        this.addBlock(AetherIIBlocks.SKYROOT_PLANKS, "Skyroot Planks");
        this.addBlock(AetherIIBlocks.SKYROOT_STAIRS, "Skyroot Stairs");
        this.addBlock(AetherIIBlocks.SKYROOT_SLAB, "Skyroot Slab");
        this.addBlock(AetherIIBlocks.SKYROOT_FENCE, "Skyroot Fence");
        this.addBlock(AetherIIBlocks.SKYROOT_FENCE_GATE, "Skyroot Fence Gate");
        this.addBlock(AetherIIBlocks.SKYROOT_DOOR, "Skyroot Door");
        this.addBlock(AetherIIBlocks.SKYROOT_TRAPDOOR, "Skyroot Trapdoor");
        this.addBlock(AetherIIBlocks.SKYROOT_BUTTON, "Skyroot Button");
        this.addBlock(AetherIIBlocks.SKYROOT_PRESSURE_PLATE, "Skyroot Pressure Plate");
        this.addBlock(AetherIIBlocks.SKYROOT_SHELF, "Skyroot Shelf");

        // Skyroot Decorative Blocks
        this.addBlock(AetherIIBlocks.SKYROOT_FLOORBOARDS, "Skyroot Floorboards");
        this.addBlock(AetherIIBlocks.SKYROOT_HIGHLIGHT, "Skyroot Highlight");
        this.addBlock(AetherIIBlocks.SKYROOT_SHINGLES, "Skyroot Shingles");
        this.addBlock(AetherIIBlocks.SKYROOT_SMALL_SHINGLES, "Skyroot Small Shingles");
        this.addBlock(AetherIIBlocks.SKYROOT_BASE_PLANKS, "Skyroot Base Planks");
        this.addBlock(AetherIIBlocks.SKYROOT_TOP_PLANKS, "Skyroot Top Planks");
        this.addBlock(AetherIIBlocks.SKYROOT_BASE_BEAM, "Skyroot Base Beam");
        this.addBlock(AetherIIBlocks.SKYROOT_TOP_BEAM, "Skyroot Top Beam");
        this.addBlock(AetherIIBlocks.SKYROOT_BEAM, "Skyroot Beam");
        this.addBlock(AetherIIBlocks.SECRET_SKYROOT_DOOR, "Secret Skyroot Door");
        this.addBlock(AetherIIBlocks.SECRET_SKYROOT_TRAPDOOR, "Secret Skyroot Trapdoor");

        // Greatroot Planks
        this.addBlock(AetherIIBlocks.GREATROOT_PLANKS, "Greatroot Planks");
        this.addBlock(AetherIIBlocks.GREATROOT_STAIRS, "Greatroot Stairs");
        this.addBlock(AetherIIBlocks.GREATROOT_SLAB, "Greatroot Slab");
        this.addBlock(AetherIIBlocks.GREATROOT_FENCE, "Greatroot Fence");
        this.addBlock(AetherIIBlocks.GREATROOT_FENCE_GATE, "Greatroot Fence Gate");
        this.addBlock(AetherIIBlocks.GREATROOT_DOOR, "Greatroot Door");
        this.addBlock(AetherIIBlocks.GREATROOT_TRAPDOOR, "Greatroot Trapdoor");
        this.addBlock(AetherIIBlocks.GREATROOT_BUTTON, "Greatroot Button");
        this.addBlock(AetherIIBlocks.GREATROOT_PRESSURE_PLATE, "Greatroot Pressure Plate");
        this.addBlock(AetherIIBlocks.GREATROOT_SHELF, "Greatroot Shelf");

        // Greatroot Decorative Blocks
        this.addBlock(AetherIIBlocks.GREATROOT_FLOORBOARDS, "Greatroot Floorboards");
        this.addBlock(AetherIIBlocks.GREATROOT_HIGHLIGHT, "Greatroot Highlight");
        this.addBlock(AetherIIBlocks.GREATROOT_SHINGLES, "Greatroot Shingles");
        this.addBlock(AetherIIBlocks.GREATROOT_SMALL_SHINGLES, "Greatroot Small Shingles");
        this.addBlock(AetherIIBlocks.GREATROOT_BASE_PLANKS, "Greatroot Base Planks");
        this.addBlock(AetherIIBlocks.GREATROOT_TOP_PLANKS, "Greatroot Top Planks");
        this.addBlock(AetherIIBlocks.GREATROOT_BASE_BEAM, "Greatroot Base Beam");
        this.addBlock(AetherIIBlocks.GREATROOT_TOP_BEAM, "Greatroot Top Beam");
        this.addBlock(AetherIIBlocks.GREATROOT_BEAM, "Greatroot Beam");
        this.addBlock(AetherIIBlocks.SECRET_GREATROOT_DOOR, "Secret Greatroot Door");
        this.addBlock(AetherIIBlocks.SECRET_GREATROOT_TRAPDOOR, "Secret Greatroot Trapdoor");

        // Wisproot Planks
        this.addBlock(AetherIIBlocks.WISPROOT_PLANKS, "Wisproot Planks");
        this.addBlock(AetherIIBlocks.WISPROOT_STAIRS, "Wisproot Stairs");
        this.addBlock(AetherIIBlocks.WISPROOT_SLAB, "Wisproot Slab");
        this.addBlock(AetherIIBlocks.WISPROOT_FENCE, "Wisproot Fence");
        this.addBlock(AetherIIBlocks.WISPROOT_FENCE_GATE, "Wisproot Fence Gate");
        this.addBlock(AetherIIBlocks.WISPROOT_DOOR, "Wisproot Door");
        this.addBlock(AetherIIBlocks.WISPROOT_TRAPDOOR, "Wisproot Trapdoor");
        this.addBlock(AetherIIBlocks.WISPROOT_BUTTON, "Wisproot Button");
        this.addBlock(AetherIIBlocks.WISPROOT_PRESSURE_PLATE, "Wisproot Pressure Plate");
        this.addBlock(AetherIIBlocks.WISPROOT_SHELF, "Wisproot Shelf");

        // Wisproot Decorative Blocks
        this.addBlock(AetherIIBlocks.WISPROOT_FLOORBOARDS, "Wisproot Floorboards");
        this.addBlock(AetherIIBlocks.WISPROOT_HIGHLIGHT, "Wisproot Highlight");
        this.addBlock(AetherIIBlocks.WISPROOT_SHINGLES, "Wisproot Shingles");
        this.addBlock(AetherIIBlocks.WISPROOT_SMALL_SHINGLES, "Wisproot Small Shingles");
        this.addBlock(AetherIIBlocks.WISPROOT_BASE_PLANKS, "Wisproot Base Planks");
        this.addBlock(AetherIIBlocks.WISPROOT_TOP_PLANKS, "Wisproot Top Planks");
        this.addBlock(AetherIIBlocks.WISPROOT_BASE_BEAM, "Wisproot Base Beam");
        this.addBlock(AetherIIBlocks.WISPROOT_TOP_BEAM, "Wisproot Top Beam");
        this.addBlock(AetherIIBlocks.WISPROOT_BEAM, "Wisproot Beam");
        this.addBlock(AetherIIBlocks.SECRET_WISPROOT_DOOR, "Secret Wisproot Door");
        this.addBlock(AetherIIBlocks.SECRET_WISPROOT_TRAPDOOR, "Secret Wisproot Trapdoor");

        // Amberoot Planks
        this.addBlock(AetherIIBlocks.AMBEROOT_PLANKS, "Amberoot Planks");
        this.addBlock(AetherIIBlocks.AMBEROOT_STAIRS, "Amberoot Stairs");
        this.addBlock(AetherIIBlocks.AMBEROOT_SLAB, "Amberoot Slab");
        this.addBlock(AetherIIBlocks.AMBEROOT_FENCE, "Amberoot Fence");
        this.addBlock(AetherIIBlocks.AMBEROOT_FENCE_GATE, "Amberoot Fence Gate");
        this.addBlock(AetherIIBlocks.AMBEROOT_DOOR, "Amberoot Door");
        this.addBlock(AetherIIBlocks.AMBEROOT_TRAPDOOR, "Amberoot Trapdoor");
        this.addBlock(AetherIIBlocks.AMBEROOT_BUTTON, "Amberoot Button");
        this.addBlock(AetherIIBlocks.AMBEROOT_PRESSURE_PLATE, "Amberoot Pressure Plate");
        this.addBlock(AetherIIBlocks.AMBEROOT_SHELF, "Amberoot Shelf");

        // Amberoot Decorative Blocks
        this.addBlock(AetherIIBlocks.AMBEROOT_FLOORBOARDS, "Amberoot Floorboards");
        this.addBlock(AetherIIBlocks.AMBEROOT_HIGHLIGHT, "Amberoot Highlight");
        this.addBlock(AetherIIBlocks.AMBEROOT_SHINGLES, "Amberoot Shingles");
        this.addBlock(AetherIIBlocks.AMBEROOT_SMALL_SHINGLES, "Amberoot Small Shingles");
        this.addBlock(AetherIIBlocks.AMBEROOT_BASE_PLANKS, "Amberoot Base Planks");
        this.addBlock(AetherIIBlocks.AMBEROOT_TOP_PLANKS, "Amberoot Top Planks");
        this.addBlock(AetherIIBlocks.AMBEROOT_BASE_BEAM, "Amberoot Base Beam");
        this.addBlock(AetherIIBlocks.AMBEROOT_TOP_BEAM, "Amberoot Top Beam");
        this.addBlock(AetherIIBlocks.AMBEROOT_BEAM, "Amberoot Beam");
        this.addBlock(AetherIIBlocks.SECRET_AMBEROOT_DOOR, "Secret Amberoot Door");
        this.addBlock(AetherIIBlocks.SECRET_AMBEROOT_TRAPDOOR, "Secret Amberoot Trapdoor");

        // Holystone
        this.addBlock(AetherIIBlocks.HOLYSTONE_STAIRS, "Holystone Stairs");
        this.addBlock(AetherIIBlocks.HOLYSTONE_SLAB, "Holystone Slab");
        this.addBlock(AetherIIBlocks.HOLYSTONE_WALL, "Holystone Wall");
        this.addBlock(AetherIIBlocks.HOLYSTONE_BUTTON, "Holystone Button");
        this.addBlock(AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE, "Holystone Pressure Plate");

        // Mossy Holystone
        this.addBlock(AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS, "Mossy Holystone Stairs");
        this.addBlock(AetherIIBlocks.MOSSY_HOLYSTONE_SLAB, "Mossy Holystone Slab");
        this.addBlock(AetherIIBlocks.MOSSY_HOLYSTONE_WALL, "Mossy Holystone Wall");

        // Irradiated Holystone
        this.addBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS, "Irradiated Holystone Stairs");
        this.addBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB, "Irradiated Holystone Slab");
        this.addBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL, "Irradiated Holystone Wall");

        // Holystone Bricks
        this.addBlock(AetherIIBlocks.HOLYSTONE_BRICKS, "Holystone Bricks");
        this.addBlock(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS, "Holystone Brick Stairs");
        this.addBlock(AetherIIBlocks.HOLYSTONE_BRICK_SLAB, "Holystone Brick Slab");
        this.addBlock(AetherIIBlocks.HOLYSTONE_BRICK_WALL, "Holystone Brick Wall");

        // Holystone Decorative Blocks
        this.addBlock(AetherIIBlocks.HOLYSTONE_FLAGSTONES, "Holystone Flagstones");
        this.addBlock(AetherIIBlocks.HOLYSTONE_HEADSTONE, "Holystone Headstone");
        this.addBlock(AetherIIBlocks.HOLYSTONE_KEYSTONE, "Holystone Keystone");
        this.addBlock(AetherIIBlocks.HOLYSTONE_BASE_BRICKS, "Holystone Base Bricks");
        this.addBlock(AetherIIBlocks.HOLYSTONE_CAPSTONE_BRICKS, "Holystone Capstone Bricks");
        this.addBlock(AetherIIBlocks.HOLYSTONE_BASE_PILLAR, "Holystone Base Pillar");
        this.addBlock(AetherIIBlocks.HOLYSTONE_CAPSTONE_PILLAR, "Holystone Capstone Pillar");
        this.addBlock(AetherIIBlocks.HOLYSTONE_PILLAR, "Holystone Pillar");

        // Faded Holystone Bricks
        this.addBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICKS, "Faded Holystone Bricks");
        this.addBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS, "Faded Holystone Brick Stairs");
        this.addBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB, "Faded Holystone Brick Slab");
        this.addBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL, "Faded Holystone Brick Wall");

        // Faded Holystone Decorative Blocks
        this.addBlock(AetherIIBlocks.FADED_HOLYSTONE_FLAGSTONES, "Faded Holystone Flagstones");
        this.addBlock(AetherIIBlocks.FADED_HOLYSTONE_HEADSTONE, "Faded Holystone Headstone");
        this.addBlock(AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE, "Faded Holystone Keystone");
        this.addBlock(AetherIIBlocks.FADED_HOLYSTONE_BASE_BRICKS, "Faded Holystone Base Bricks");
        this.addBlock(AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_BRICKS, "Faded Holystone Capstone Bricks");
        this.addBlock(AetherIIBlocks.FADED_HOLYSTONE_BASE_PILLAR, "Faded Holystone Base Pillar");
        this.addBlock(AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_PILLAR, "Faded Holystone Capstone Pillar");
        this.addBlock(AetherIIBlocks.FADED_HOLYSTONE_PILLAR, "Faded Holystone Pillar");

        // Undershale
        this.addBlock(AetherIIBlocks.UNDERSHALE_STAIRS, "Undershale Stairs");
        this.addBlock(AetherIIBlocks.UNDERSHALE_SLAB, "Undershale Slab");
        this.addBlock(AetherIIBlocks.UNDERSHALE_WALL, "Undershale Wall");

        // Undershale Bricks
        this.addBlock(AetherIIBlocks.UNDERSHALE_BRICKS, "Undershale Bricks");
        this.addBlock(AetherIIBlocks.UNDERSHALE_BRICK_STAIRS, "Undershale Brick Stairs");
        this.addBlock(AetherIIBlocks.UNDERSHALE_BRICK_SLAB, "Undershale Brick Slab");
        this.addBlock(AetherIIBlocks.UNDERSHALE_BRICK_WALL, "Undershale Brick Wall");
        this.addBlock(AetherIIBlocks.UNDERSHALE_BRICK_BUTTON, "Undershale Brick Button");
        this.addBlock(AetherIIBlocks.UNDERSHALE_BRICK_PRESSURE_PLATE, "Undershale Brick Pressure Plate");

        // Undershale Decorative Blocks
        this.addBlock(AetherIIBlocks.UNDERSHALE_FLAGSTONES, "Undershale Flagstones");
        this.addBlock(AetherIIBlocks.UNDERSHALE_TILE, "Undershale Tile");
        this.addBlock(AetherIIBlocks.UNDERSHALE_BASE_BRICKS, "Undershale Base Bricks");
        this.addBlock(AetherIIBlocks.UNDERSHALE_CAPSTONE_BRICKS, "Undershale Capstone Bricks");
        this.addBlock(AetherIIBlocks.UNDERSHALE_BASE_PILLAR, "Undershale Base Pillar");
        this.addBlock(AetherIIBlocks.UNDERSHALE_CAPSTONE_PILLAR, "Undershale Capstone Pillar");
        this.addBlock(AetherIIBlocks.UNDERSHALE_PILLAR, "Undershale Pillar");

        // Sentry Bricks
        this.addBlock(AetherIIBlocks.SENTRY_BRICKS, "Sentry Bricks");
        this.addBlock(AetherIIBlocks.SENTRY_BRICK_STAIRS, "Sentry Brick Stairs");
        this.addBlock(AetherIIBlocks.SENTRY_BRICK_SLAB, "Sentry Brick Slab");
        this.addBlock(AetherIIBlocks.SENTRY_BRICK_WALL, "Sentry Brick Wall");
        this.addBlock(AetherIIBlocks.SENTRY_BUTTON, "Sentry Button");

        // Sentry Decorative Blocks
        this.addBlock(AetherIIBlocks.SENTRY_LIGHTSTONE, "Sentry Lightstone");
        this.addBlock(AetherIIBlocks.SENTRY_FLAGSTONES, "Sentry Flagstones");
        this.addBlock(AetherIIBlocks.SENTRY_TILE, "Sentry Tile");
        this.addBlock(AetherIIBlocks.SENTRY_BASE_BRICKS, "Sentry Base Bricks");
        this.addBlock(AetherIIBlocks.SENTRY_CAPSTONE_BRICKS, "Sentry Capstone Bricks");
        this.addBlock(AetherIIBlocks.SENTRY_BASE_PILLAR, "Sentry Base Pillar");
        this.addBlock(AetherIIBlocks.SENTRY_CAPSTONE_PILLAR, "Sentry Capstone Pillar");
        this.addBlock(AetherIIBlocks.SENTRY_PILLAR, "Sentry Pillar");

        // Ichorite
        this.addBlock(AetherIIBlocks.ICHORITE, "Ichorite");
        this.addBlock(AetherIIBlocks.ICHORITE_STAIRS, "Ichorite Stairs");
        this.addBlock(AetherIIBlocks.ICHORITE_SLAB, "Ichorite Slab");
        this.addBlock(AetherIIBlocks.ICHORITE_WALL, "Ichorite Wall");

        // Smooth Ichorite
        this.addBlock(AetherIIBlocks.SMOOTH_ICHORITE, "Smooth Ichorite");
        this.addBlock(AetherIIBlocks.SMOOTH_ICHORITE_STAIRS, "Smooth Ichorite Stairs");
        this.addBlock(AetherIIBlocks.SMOOTH_ICHORITE_SLAB, "Smooth Ichorite Slab");
        this.addBlock(AetherIIBlocks.SMOOTH_ICHORITE_WALL, "Smooth Ichorite Wall");

        // Ichorite Bricks
        this.addBlock(AetherIIBlocks.ICHORITE_BRICKS, "Ichorite Bricks");
        this.addBlock(AetherIIBlocks.ICHORITE_BRICK_STAIRS, "Ichorite Brick Stairs");
        this.addBlock(AetherIIBlocks.ICHORITE_BRICK_SLAB, "Ichorite Brick Slab");
        this.addBlock(AetherIIBlocks.ICHORITE_BRICK_WALL, "Ichorite Brick Wall");

        // Ichorite Decorative Blocks
        this.addBlock(AetherIIBlocks.ICHORITE_FLAGSTONES, "Ichorite Flagstones");
        this.addBlock(AetherIIBlocks.ICHORITE_RUNESTONE, "Ichorite Runestone");
        this.addBlock(AetherIIBlocks.ICHORITE_KEYSTONE, "Ichorite Keystone");
        this.addBlock(AetherIIBlocks.ICHORITE_BASE_BRICKS, "Ichorite Base Bricks");
        this.addBlock(AetherIIBlocks.ICHORITE_CAPSTONE_BRICKS, "Ichorite Capstone Bricks");
        this.addBlock(AetherIIBlocks.ICHORITE_BASE_PILLAR, "Ichorite Base Pillar");
        this.addBlock(AetherIIBlocks.ICHORITE_CAPSTONE_PILLAR, "Ichorite Capstone Pillar");
        this.addBlock(AetherIIBlocks.ICHORITE_PILLAR, "Ichorite Pillar");

        // Marbled Ichorite
        this.addBlock(AetherIIBlocks.MARBLED_ICHORITE, "Marbled Ichorite");
        this.addBlock(AetherIIBlocks.MARBLED_ICHORITE_STAIRS, "Marbled Ichorite Stairs");
        this.addBlock(AetherIIBlocks.MARBLED_ICHORITE_SLAB, "Marbled Ichorite Slab");
        this.addBlock(AetherIIBlocks.MARBLED_ICHORITE_WALL, "Marbled Ichorite Wall");

        // Marbled Bricks
        this.addBlock(AetherIIBlocks.MARBLED_BRICKS, "Marbled Bricks");
        this.addBlock(AetherIIBlocks.MARBLED_BRICK_STAIRS, "Marbled Brick Stairs");
        this.addBlock(AetherIIBlocks.MARBLED_BRICK_SLAB, "Marbled Brick Slab");
        this.addBlock(AetherIIBlocks.MARBLED_BRICK_WALL, "Marbled Brick Wall");

        // Marbled Ichorite Decorative Blocks
        this.addBlock(AetherIIBlocks.MARBLED_FLAGSTONES, "Marbled Flagstones");
        this.addBlock(AetherIIBlocks.MARBLED_KEYSTONE, "Marbled Keystone");
        this.addBlock(AetherIIBlocks.MARBLED_BASE_BRICKS, "Marbled Base Bricks");
        this.addBlock(AetherIIBlocks.MARBLED_CAPSTONE_BRICKS, "Marbled Capstone Bricks");
        this.addBlock(AetherIIBlocks.MARBLED_BASE_PILLAR, "Marbled Base Pillar");
        this.addBlock(AetherIIBlocks.MARBLED_CAPSTONE_PILLAR, "Marbled Capstone Pillar");
        this.addBlock(AetherIIBlocks.MARBLED_PILLAR, "Marbled Pillar");

        // Agiosite
        this.addBlock(AetherIIBlocks.AGIOSITE_STAIRS, "Agiosite Stairs");
        this.addBlock(AetherIIBlocks.AGIOSITE_SLAB, "Agiosite Slab");
        this.addBlock(AetherIIBlocks.AGIOSITE_WALL, "Agiosite Wall");

        // Agiosite Bricks
        this.addBlock(AetherIIBlocks.AGIOSITE_BRICKS, "Agiosite Bricks");
        this.addBlock(AetherIIBlocks.AGIOSITE_BRICK_STAIRS, "Agiosite Brick Stairs");
        this.addBlock(AetherIIBlocks.AGIOSITE_BRICK_SLAB, "Agiosite Brick Slab");
        this.addBlock(AetherIIBlocks.AGIOSITE_BRICK_WALL, "Agiosite Brick Wall");

        // Agiosite Decorative Blocks
        this.addBlock(AetherIIBlocks.AGIOSITE_FLAGSTONES, "Agiosite Flagstones");
        this.addBlock(AetherIIBlocks.AGIOSITE_KEYSTONE, "Agiosite Keystone");
        this.addBlock(AetherIIBlocks.AGIOSITE_BASE_BRICKS, "Agiosite Base Bricks");
        this.addBlock(AetherIIBlocks.AGIOSITE_CAPSTONE_BRICKS, "Agiosite Capstone Bricks");
        this.addBlock(AetherIIBlocks.AGIOSITE_BASE_PILLAR, "Agiosite Base Pillar");
        this.addBlock(AetherIIBlocks.AGIOSITE_CAPSTONE_PILLAR, "Agiosite Capstone Pillar");
        this.addBlock(AetherIIBlocks.AGIOSITE_PILLAR, "Agiosite Pillar");

        // Icestone
        this.addBlock(AetherIIBlocks.ICESTONE_STAIRS, "Icestone Stairs");
        this.addBlock(AetherIIBlocks.ICESTONE_SLAB, "Icestone Slab");
        this.addBlock(AetherIIBlocks.ICESTONE_WALL, "Icestone Wall");

        // Icestone Bricks
        this.addBlock(AetherIIBlocks.ICESTONE_BRICKS, "Icestone Bricks");
        this.addBlock(AetherIIBlocks.ICESTONE_BRICK_STAIRS, "Icestone Brick Stairs");
        this.addBlock(AetherIIBlocks.ICESTONE_BRICK_SLAB, "Icestone Brick Slab");
        this.addBlock(AetherIIBlocks.ICESTONE_BRICK_WALL, "Icestone Brick Wall");

        // Icestone Decorative Blocks
        this.addBlock(AetherIIBlocks.ICESTONE_FLAGSTONES, "Icestone Flagstones");
        this.addBlock(AetherIIBlocks.ICESTONE_KEYSTONE, "Icestone Keystone");
        this.addBlock(AetherIIBlocks.ICESTONE_BASE_BRICKS, "Icestone Base Bricks");
        this.addBlock(AetherIIBlocks.ICESTONE_CAPSTONE_BRICKS, "Icestone Capstone Bricks");
        this.addBlock(AetherIIBlocks.ICESTONE_BASE_PILLAR, "Icestone Base Pillar");
        this.addBlock(AetherIIBlocks.ICESTONE_CAPSTONE_PILLAR, "Icestone Capstone Pillar");
        this.addBlock(AetherIIBlocks.ICESTONE_PILLAR, "Icestone Pillar");

        // Glass
        this.addBlock(AetherIIBlocks.QUICKSOIL_GLASS, "Quicksoil Glass");
        this.addBlock(AetherIIBlocks.TILED_QUICKSOIL_GLASS, "Tiled Quicksoil Glass");
        this.addBlock(AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS, "Gridded Quicksoil Glass");
        this.addBlock(AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS, "Skyroot Framed Crude Scatterglass");
        this.addBlock(AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS, "Arkenium Framed Crude Scatterglass");
        this.addBlock(AetherIIBlocks.SCATTERGLASS, "Scatterglass");
        this.addBlock(AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS, "Skyroot Framed Scatterglass");
        this.addBlock(AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS, "Arkenium Framed Scatterglass");

        // Glass Panes
        this.addBlock(AetherIIBlocks.QUICKSOIL_GLASS_PANE, "Quicksoil Glass Pane");
        this.addBlock(AetherIIBlocks.TILED_QUICKSOIL_GLASS_PANE, "Tiled Quicksoil Glass Pane");
        this.addBlock(AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS_PANE, "Gridded Quicksoil Glass Pane");
        this.addBlock(AetherIIBlocks.CRUDE_SCATTERGLASS_PANE, "Crude Scatterglass Pane");
        this.addBlock(AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS_PANE, "Skyroot Framed Crude Scatterglass Pane");
        this.addBlock(AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS_PANE, "Arkenium Framed Crude Scatterglass Pane");
        this.addBlock(AetherIIBlocks.SCATTERGLASS_PANE, "Scatterglass Pane");
        this.addBlock(AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS_PANE, "Skyroot Framed Scatterglass Pane");
        this.addBlock(AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS_PANE, "Arkenium Framed Scatterglass Pane");

        // Wool
        this.addBlock(AetherIIBlocks.CLOUDWOOL, "Cloudwool");
        this.addBlock(AetherIIBlocks.WHITE_CLOUDWOOL, "White Cloudwool");
        this.addBlock(AetherIIBlocks.ORANGE_CLOUDWOOL, "Orange Cloudwool");
        this.addBlock(AetherIIBlocks.MAGENTA_CLOUDWOOL, "Magenta Cloudwool");
        this.addBlock(AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL, "Light Blue Cloudwool");
        this.addBlock(AetherIIBlocks.YELLOW_CLOUDWOOL, "Yellow Cloudwool");
        this.addBlock(AetherIIBlocks.LIME_CLOUDWOOL, "Lime Cloudwool");
        this.addBlock(AetherIIBlocks.PINK_CLOUDWOOL, "Pink Cloudwool");
        this.addBlock(AetherIIBlocks.GRAY_CLOUDWOOL, "Gray Cloudwool");
        this.addBlock(AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL, "Light Gray Cloudwool");
        this.addBlock(AetherIIBlocks.CYAN_CLOUDWOOL, "Cyan Cloudwool");
        this.addBlock(AetherIIBlocks.PURPLE_CLOUDWOOL, "Purple Cloudwool");
        this.addBlock(AetherIIBlocks.BLUE_CLOUDWOOL, "Blue Cloudwool");
        this.addBlock(AetherIIBlocks.BROWN_CLOUDWOOL, "Brown Cloudwool");
        this.addBlock(AetherIIBlocks.GREEN_CLOUDWOOL, "Green Cloudwool");
        this.addBlock(AetherIIBlocks.RED_CLOUDWOOL, "Red Cloudwool");
        this.addBlock(AetherIIBlocks.BLACK_CLOUDWOOL, "Black Cloudwool");

        // Carpet
        this.addBlock(AetherIIBlocks.CLOUDWOOL_CARPET, "Cloudwool Carpet");
        this.addBlock(AetherIIBlocks.WHITE_CLOUDWOOL_CARPET, "White Cloudwool Carpet");
        this.addBlock(AetherIIBlocks.ORANGE_CLOUDWOOL_CARPET, "Orange Cloudwool Carpet");
        this.addBlock(AetherIIBlocks.MAGENTA_CLOUDWOOL_CARPET, "Magenta Cloudwool Carpet");
        this.addBlock(AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL_CARPET, "Light Blue Cloudwool Carpet");
        this.addBlock(AetherIIBlocks.YELLOW_CLOUDWOOL_CARPET, "Yellow Cloudwool Carpet");
        this.addBlock(AetherIIBlocks.LIME_CLOUDWOOL_CARPET, "Lime Cloudwool Carpet");
        this.addBlock(AetherIIBlocks.PINK_CLOUDWOOL_CARPET, "Pink Cloudwool Carpet");
        this.addBlock(AetherIIBlocks.GRAY_CLOUDWOOL_CARPET, "Gray Cloudwool Carpet");
        this.addBlock(AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL_CARPET, "Light Gray Cloudwool Carpet");
        this.addBlock(AetherIIBlocks.CYAN_CLOUDWOOL_CARPET, "Cyan Cloudwool Carpet");
        this.addBlock(AetherIIBlocks.PURPLE_CLOUDWOOL_CARPET, "Purple Cloudwool Carpet");
        this.addBlock(AetherIIBlocks.BLUE_CLOUDWOOL_CARPET, "Blue Cloudwool Carpet");
        this.addBlock(AetherIIBlocks.BROWN_CLOUDWOOL_CARPET, "Brown Cloudwool Carpet");
        this.addBlock(AetherIIBlocks.GREEN_CLOUDWOOL_CARPET, "Green Cloudwool Carpet");
        this.addBlock(AetherIIBlocks.RED_CLOUDWOOL_CARPET, "Red Cloudwool Carpet");
        this.addBlock(AetherIIBlocks.BLACK_CLOUDWOOL_CARPET, "Black Cloudwool Carpet");

        // Roofing
        this.addBlock(AetherIIBlocks.CLOUDWOOL_ROOFING, "Cloudwool Roofing");

        // Arkenium Blocks
        this.addBlock(AetherIIBlocks.ARKENIUM_DOOR, "Arkenium Door");
        this.addBlock(AetherIIBlocks.ARKENIUM_TRAPDOOR, "Arkenium Trapdoor");
        this.addBlock(AetherIIBlocks.ARKENIUM_BARS, "Arkenium Bars");
        this.addBlock(AetherIIBlocks.FLORAL_ARKENIUM_BARS, "Floral Arkenium Bars");
        this.addBlock(AetherIIBlocks.PATTERNED_ARKENIUM_BARS, "Patterned Arkenium Bars");
        this.addBlock(AetherIIBlocks.CURVED_ARKENIUM_BARS, "Curved Arkenium Bars");

        // Rustic Arkenium Blocks
        this.addBlock(AetherIIBlocks.RUSTIC_ARKENIUM_BARS, "Rustic Arkenium Bars");
        this.addBlock(AetherIIBlocks.RUSTIC_FLORAL_ARKENIUM_BARS, "Rustic Floral Arkenium Bars");
        this.addBlock(AetherIIBlocks.RUSTIC_PATTERNED_ARKENIUM_BARS, "Rustic Patterned Arkenium Bars");
        this.addBlock(AetherIIBlocks.RUSTIC_CURVED_ARKENIUM_BARS, "Rustic Curved Arkenium Bars");

        // Inert Mineral Blocks
        this.addBlock(AetherIIBlocks.INERT_ARKENIUM_BLOCK, "Block of Inert Arkenium");
        this.addBlock(AetherIIBlocks.INERT_GRAVITITE_BLOCK, "Block of Inert Gravitite");

        // Mineral Blocks
        this.addBlock(AetherIIBlocks.AMBROSIUM_BLOCK, "Block of Ambrosium");
        this.addBlock(AetherIIBlocks.ZANITE_BLOCK, "Block of Zanite");
        this.addBlock(AetherIIBlocks.ARKENIUM_BLOCK, "Block of Arkenium");
        this.addBlock(AetherIIBlocks.GRAVITITE_BLOCK, "Block of Gravitite");
        this.addBlock(AetherIIBlocks.CORROBONITE_BLOCK, "Block of Corrobonite");
        this.addBlock(AetherIIBlocks.GOLDEN_AMBER_BLOCK, "Block of Golden Amber");
        this.addBlock(AetherIIBlocks.GLINT_BLOCK, "Block of Glint");

        // Storage Blocks
        this.addBlock(AetherIIBlocks.BRETTL_GRASS_BUNDLE, "Brettl Grass Bundle");
        this.addBlock(AetherIIBlocks.GEL_BLOCK, "Gel Block");

        // Arilum Lanterns
        this.addBlock(AetherIIBlocks.WHITE_ARILUM_LANTERN, "White Arilum Lantern");
        this.addBlock(AetherIIBlocks.ORANGE_ARILUM_LANTERN, "Orange Arilum Lantern");
        this.addBlock(AetherIIBlocks.MAGENTA_ARILUM_LANTERN, "Magenta Arilum Lantern");
        this.addBlock(AetherIIBlocks.LIGHT_BLUE_ARILUM_LANTERN, "Light Blue Arilum Lantern");
        this.addBlock(AetherIIBlocks.YELLOW_ARILUM_LANTERN, "Yellow Arilum Lantern");
        this.addBlock(AetherIIBlocks.LIME_ARILUM_LANTERN, "Lime Arilum Lantern");
        this.addBlock(AetherIIBlocks.PINK_ARILUM_LANTERN, "Pink Arilum Lantern");
        this.addBlock(AetherIIBlocks.GRAY_ARILUM_LANTERN, "Gray Arilum Lantern");
        this.addBlock(AetherIIBlocks.LIGHT_GRAY_ARILUM_LANTERN, "Light Gray Arilum Lantern");
        this.addBlock(AetherIIBlocks.CYAN_ARILUM_LANTERN, "Cyan Arilum Lantern");
        this.addBlock(AetherIIBlocks.PURPLE_ARILUM_LANTERN, "Purple Arilum Lantern");
        this.addBlock(AetherIIBlocks.BLUE_ARILUM_LANTERN, "Blue Arilum Lantern");
        this.addBlock(AetherIIBlocks.BROWN_ARILUM_LANTERN, "Brown Arilum Lantern");
        this.addBlock(AetherIIBlocks.GREEN_ARILUM_LANTERN, "Green Arilum Lantern");
        this.addBlock(AetherIIBlocks.RED_ARILUM_LANTERN, "Red Arilum Lantern");
        this.addBlock(AetherIIBlocks.BLACK_ARILUM_LANTERN, "Black Arilum Lantern");

        // Utility
        this.addBlock(AetherIIBlocks.AMBROSIUM_TORCH, "Ambrosium Torch");
        this.addBlock(AetherIIBlocks.ARKENIUM_LANTERN, "Arkenium Lantern");
        this.addBlock(AetherIIBlocks.RUSTIC_ARKENIUM_LANTERN, "Rustic Arkenium Lantern");
        this.addBlock(AetherIIBlocks.ARKENIUM_CHAIN, "Arkenium Chain");
        this.addBlock(AetherIIBlocks.SKYROOT_CRAFTING_TABLE, "Skyroot Crafting Table");
        this.addBlock(AetherIIBlocks.HOLYSTONE_FURNACE, "Holystone Furnace");
        this.addBlock(AetherIIBlocks.HOLYSTONE_SMOKER, "Holystone Smoker");
        this.addBlock(AetherIIBlocks.AMBER_HOURGLASS, "Amber Hourglass");
        this.addBlock(AetherIIBlocks.ALTAR, "Altar");
        this.addBlock(AetherIIBlocks.ARTISANS_BENCH, "Artisan's Bench");
        this.addBlock(AetherIIBlocks.ARKENIUM_FORGE, "Arkenium Forge");
        this.addBlock(AetherIIBlocks.ALKAHEST_PURIFIER, "Alkahest Purifier");
        this.addBlock(AetherIIBlocks.AMBROSIUM_CAMPFIRE, "Ambrosium Campfire");
        this.addBlock(AetherIIBlocks.SKYROOT_CHEST, "Skyroot Chest");
        this.addBlock(AetherIIBlocks.SKYROOT_BARREL, "Skyroot Barrel");
        this.addBlock(AetherIIBlocks.SKYROOT_LADDER, "Skyroot Ladder");
        this.addBlock(AetherIIBlocks.CLOUDWOOL_BEDROLL, "Cloudwool Bedroll");

        this.addBlock(AetherIIBlocks.SKYROOT_BED, "Skyroot Bed");
        this.addBlock(AetherIIBlocks.WHITE_SKYROOT_BED, "White Skyroot Bed");
        this.addBlock(AetherIIBlocks.ORANGE_SKYROOT_BED, "Orange Skyroot Bed");
        this.addBlock(AetherIIBlocks.MAGENTA_SKYROOT_BED, "Magenta Skyroot Bed");
        this.addBlock(AetherIIBlocks.LIGHT_BLUE_SKYROOT_BED, "Light Blue Skyroot Bed");
        this.addBlock(AetherIIBlocks.YELLOW_SKYROOT_BED, "Yellow Skyroot Bed");
        this.addBlock(AetherIIBlocks.LIME_SKYROOT_BED, "Lime Skyroot Bed");
        this.addBlock(AetherIIBlocks.PINK_SKYROOT_BED, "Pink Skyroot Bed");
        this.addBlock(AetherIIBlocks.GRAY_SKYROOT_BED, "Gray Skyroot Bed");
        this.addBlock(AetherIIBlocks.LIGHT_GRAY_SKYROOT_BED, "Light Gray Skyroot Bed");
        this.addBlock(AetherIIBlocks.CYAN_SKYROOT_BED, "Cyan Skyroot Bed");
        this.addBlock(AetherIIBlocks.PURPLE_SKYROOT_BED, "Purple Skyroot Bed");
        this.addBlock(AetherIIBlocks.BLUE_SKYROOT_BED, "Blue Skyroot Bed");
        this.addBlock(AetherIIBlocks.BROWN_SKYROOT_BED, "Brown Skyroot Bed");
        this.addBlock(AetherIIBlocks.GREEN_SKYROOT_BED, "Green Skyroot Bed");
        this.addBlock(AetherIIBlocks.RED_SKYROOT_BED, "Red Skyroot Bed");
        this.addBlock(AetherIIBlocks.BLACK_SKYROOT_BED, "Black Skyroot Bed");

        this.addBlock(AetherIIBlocks.HOLYSTONE_VASE, "Holystone Vase");
        this.addBlock(AetherIIBlocks.VERADEXIAN_VASE, "Veradexian Vase");
        this.addBlock(AetherIIBlocks.BREXALLEN_VASE, "Brexallen Vase");

        this.addBlock(AetherIIBlocks.SENTRY_CRATE, "Sentry Crate");
        this.addBlock(AetherIIBlocks.SENTRY_SPAWNER, "Sentry Spawner");
        this.addBlock(AetherIIBlocks.SENTRY_TRAP, "Sentry Trap");

        this.addBlock(AetherIIBlocks.LOCKED_BLOCK, "Locked Block");
        this.addBlock(AetherIIBlocks.BOSS_DOORWAY_BLOCK, "Boss Doorway Block");
        this.addBlock(AetherIIBlocks.TREASURE_DOORWAY_BLOCK, "Treasure Doorway Block");

        this.addBlock(AetherIIBlocks.SKYROOT_SIGN, "Skyroot Sign");
        this.addBlock(AetherIIBlocks.SKYROOT_HANGING_SIGN, "Skyroot Hanging Sign");

        this.addBlock(AetherIIBlocks.GREATROOT_SIGN, "Greatroot Sign");
        this.addBlock(AetherIIBlocks.GREATROOT_HANGING_SIGN, "Greatroot Hanging Sign");

        this.addBlock(AetherIIBlocks.WISPROOT_SIGN, "Wisproot Sign");
        this.addBlock(AetherIIBlocks.WISPROOT_HANGING_SIGN, "Wisproot Hanging Sign");

        this.addBlock(AetherIIBlocks.AMBEROOT_SIGN, "Amberoot Sign");
        this.addBlock(AetherIIBlocks.AMBEROOT_HANGING_SIGN, "Amberoot Hanging Sign");

        this.addBlock(AetherIIBlocks.HOLYSTONE_LEVER, "Holystone Lever");

        // Bookshelves
        this.addBlock(AetherIIBlocks.SKYROOT_BOOKSHELF, "Skyroot Bookshelf");
        this.addBlock(AetherIIBlocks.GREATROOT_BOOKSHELF, "Greatroot Bookshelf");
        this.addBlock(AetherIIBlocks.WISPROOT_BOOKSHELF, "Wisproot Bookshelf");
        this.addBlock(AetherIIBlocks.AMBEROOT_BOOKSHELF, "Amberoot Bookshelf");
        this.addBlock(AetherIIBlocks.HOLYSTONE_BOOKSHELF, "Holystone Bookshelf");

        // Furniture
        this.addBlock(AetherIIBlocks.OUTPOST_CAMPFIRE, "Outpost Campfire");
        this.addBlock(AetherIIBlocks.MURAL, "Mural");

        // Melting Blocks
        this.addBlock(AetherIIBlocks.FROSTED_ICE, "Frosted Ice");
        this.addBlock(AetherIIBlocks.FROSTED_ARCTIC_ICE, "Frosted Arctic Ice");
        this.addBlock(AetherIIBlocks.UNSTABLE_OBSIDIAN, "Unstable Obsidian");

        // Infected Guardian Tree
        // Guardian Wood
        this.addBlock(AetherIIBlocks.GUARDIAN_LOG, "Guardian Log");
        this.addBlock(AetherIIBlocks.GUARDIAN_WOOD, "Guardian Wood");
        this.addBlock(AetherIIBlocks.STRIPPED_GUARDIAN_LOG, "Stripped Guardian Log");
        this.addBlock(AetherIIBlocks.STRIPPED_GUARDIAN_WOOD, "Stripped Guardian Wood");

        // Infected Wood
        this.addBlock(AetherIIBlocks.INFECTED_LOG, "Infected Log");
        this.addBlock(AetherIIBlocks.INFECTED_WOOD, "Infected Wood");
        this.addBlock(AetherIIBlocks.STRIPPED_INFECTED_LOG, "Stripped Infected Log");
        this.addBlock(AetherIIBlocks.STRIPPED_INFECTED_WOOD, "Stripped Infected Wood");

        // Guardian Slabs
        this.addBlock(AetherIIBlocks.GUARDIAN_LOG_SLAB, "Guardian Log Slab");
        this.addBlock(AetherIIBlocks.GUARDIAN_WOOD_SLAB, "Guardian Wood Slab");
        this.addBlock(AetherIIBlocks.STRIPPED_GUARDIAN_LOG_SLAB, "Stripped Guardian Log Slab");
        this.addBlock(AetherIIBlocks.STRIPPED_GUARDIAN_WOOD_SLAB, "Stripped Guardian Wood Slab");
        this.addBlock(AetherIIBlocks.INFECTED_LOG_SLAB, "Infected Log Slab");
        this.addBlock(AetherIIBlocks.INFECTED_WOOD_SLAB, "Infected Wood Slab");
        this.addBlock(AetherIIBlocks.STRIPPED_INFECTED_LOG_SLAB, "Stripped Infected Log Slab");
        this.addBlock(AetherIIBlocks.STRIPPED_INFECTED_WOOD_SLAB, "Stripped Infected Wood Slab");

        // Guardian Trunks
        this.addBlock(AetherIIBlocks.GUARDIAN_TRUNK, "Guardian Trunk");
        this.addBlock(AetherIIBlocks.STRIPPED_GUARDIAN_TRUNK, "Stripped Guardian Trunk");
        this.addBlock(AetherIIBlocks.INFECTED_TRUNK, "Infected Trunk");
        this.addBlock(AetherIIBlocks.STRIPPED_INFECTED_TRUNK, "Stripped Infected Trunk");

        // Guardian Root Blocks
        this.addBlock(AetherIIBlocks.GUARDIAN_ROOTS, "Guardian Roots");
        this.addBlock(AetherIIBlocks.UNSTABLE_GUARDIAN_ROOTS, "Unstable Guardian Roots");
        this.addBlock(AetherIIBlocks.LUCENT_GUARDIAN_ROOTS, "Lucent Guardian Roots");
        this.addBlock(AetherIIBlocks.GUARDIAN_LAMP, "Guardian Lamp");

        // Undergrowth Blocks
        this.addBlock(AetherIIBlocks.UNDERGROWTH_LEAVES, "Undergrowth Leaves");
        this.addBlock(AetherIIBlocks.UNDERGROWTH_VINES, "Undergrowth Vines");
        this.addBlock(AetherIIBlocks.HANGING_UNDERGROWTH, "Hanging Undergrowth");
        this.addBlock(AetherIIBlocks.HANGING_UNDERGROWTH_PLANT, "Hanging Undergrowth Plant");

        // Rotshroom Blocks
        this.addBlock(AetherIIBlocks.ROTSHROOM_BLOCK, "Rotshroom Block");
        this.addBlock(AetherIIBlocks.ROTSHROOM_SLAB, "Rotshroom Slab");
        this.addBlock(AetherIIBlocks.ROTSHROOM_STEM, "Rotshroom Stem");
        this.addBlock(AetherIIBlocks.SHELF_ROTSHROOM_SLAB, "Shelf Rotshroom Slab");
        this.addBlock(AetherIIBlocks.ROTSHROOM, "Rotshroom");
        this.addBlock(AetherIIBlocks.POTTED_ROTSHROOM, "Potted Rotshroom");
        this.addBlock(AetherIIBlocks.ROTSHROOM_CLUSTER, "Rotshroom Cluster");
        this.addBlock(AetherIIBlocks.ROTSHROOM_TOADSTOOL, "Rotshroom Toadstool");
        this.addBlock(AetherIIBlocks.SHELF_ROTSHROOM, "Shelf Rotshroom");
        this.addBlock(AetherIIBlocks.ROTGROWTH_VINES, "Rotgrowth Vines");

        // Dungeon Furniture
        this.addBlock(AetherIIBlocks.PRAYER_CANDLE, "Prayer Candle");
        this.addBlock(AetherIIBlocks.GUARDIAN_PEW, "Guardian Pew");
        this.addBlock(AetherIIBlocks.GUARDIAN_DONATION_BOX, "Guardian Donation Box");
        this.addBlock(AetherIIBlocks.ABANDONED_BAG, "Abandoned Bag");
        this.addBlock(AetherIIBlocks.FUNGAL_CACHE, "Fungal Cache");
        this.addBlock(AetherIIBlocks.SAGE_CHEST, "Sage Chest");
    }

    private void addItems() {
        // Tools
        this.addItem(AetherIIItems.SKYROOT_PICKAXE, "Skyroot Pickaxe");
        this.addItem(AetherIIItems.SKYROOT_AXE, "Skyroot Axe");
        this.addItem(AetherIIItems.SKYROOT_SHOVEL, "Skyroot Shovel");
        this.addItem(AetherIIItems.SKYROOT_TROWEL, "Skyroot Trowel");

        this.addItem(AetherIIItems.HOLYSTONE_PICKAXE, "Holystone Pickaxe");
        this.addItem(AetherIIItems.HOLYSTONE_AXE, "Holystone Axe");
        this.addItem(AetherIIItems.HOLYSTONE_SHOVEL, "Holystone Shovel");
        this.addItem(AetherIIItems.HOLYSTONE_TROWEL, "Holystone Trowel");

        this.addItem(AetherIIItems.ZANITE_PICKAXE, "Zanite Pickaxe");
        this.addItem(AetherIIItems.ZANITE_AXE, "Zanite Axe");
        this.addItem(AetherIIItems.ZANITE_SHOVEL, "Zanite Shovel");
        this.addItem(AetherIIItems.ZANITE_TROWEL, "Zanite Trowel");

        this.addItem(AetherIIItems.ARKENIUM_PICKAXE, "Arkenium Pickaxe");
        this.addItem(AetherIIItems.ARKENIUM_AXE, "Arkenium Axe");
        this.addItem(AetherIIItems.ARKENIUM_SHOVEL, "Arkenium Shovel");
        this.addItem(AetherIIItems.ARKENIUM_TROWEL, "Arkenium Trowel");

        this.addItem(AetherIIItems.GRAVITITE_PICKAXE, "Gravitite Pickaxe");
        this.addItem(AetherIIItems.GRAVITITE_AXE, "Gravitite Axe");
        this.addItem(AetherIIItems.GRAVITITE_SHOVEL, "Gravitite Shovel");
        this.addItem(AetherIIItems.GRAVITITE_TROWEL, "Gravitite Trowel");

        this.addItem(AetherIIItems.ZANITE_SHEARS, "Zanite Shears");

        // Combat
        this.addItem(AetherIIItems.SKYROOT_SHORTSWORD, "Skyroot Shortsword");
        this.addItem(AetherIIItems.SKYROOT_HAMMER, "Skyroot Hammer");
        this.addItem(AetherIIItems.SKYROOT_PIKE, "Skyroot Pike");
        this.addItem(AetherIIItems.SKYROOT_CROSSBOW, "Skyroot Crossbow");

        this.addItem(AetherIIItems.HOLYSTONE_SHORTSWORD, "Holystone Shortsword");
        this.addItem(AetherIIItems.HOLYSTONE_HAMMER, "Holystone Hammer");
        this.addItem(AetherIIItems.HOLYSTONE_PIKE, "Holystone Pike");
        this.addItem(AetherIIItems.HOLYSTONE_CROSSBOW, "Holystone Crossbow");

        this.addItem(AetherIIItems.ZANITE_SHORTSWORD, "Zanite Shortsword");
        this.addItem(AetherIIItems.ZANITE_HAMMER, "Zanite Hammer");
        this.addItem(AetherIIItems.ZANITE_PIKE, "Zanite Pike");
        this.addItem(AetherIIItems.ZANITE_CROSSBOW, "Zanite Crossbow");

        this.addItem(AetherIIItems.ARKENIUM_SHORTSWORD, "Arkenium Shortsword");
        this.addItem(AetherIIItems.ARKENIUM_HAMMER, "Arkenium Hammer");
        this.addItem(AetherIIItems.ARKENIUM_PIKE, "Arkenium Pike");
        this.addItem(AetherIIItems.ARKENIUM_CROSSBOW, "Arkenium Crossbow");

        this.addItem(AetherIIItems.GRAVITITE_SHORTSWORD, "Gravitite Shortsword");
        this.addItem(AetherIIItems.GRAVITITE_HAMMER, "Gravitite Hammer");
        this.addItem(AetherIIItems.GRAVITITE_PIKE, "Gravitite Pike");
        this.addItem(AetherIIItems.GRAVITITE_CROSSBOW, "Gravitite Crossbow");

        this.addItem(AetherIIItems.SKYROOT_SHIELD, "Skyroot Shield");
        this.addItem(AetherIIItems.BURRUKAI_PLATE_SHIELD, "Burrukai Plate Shield");
        this.addItem(AetherIIItems.ZANITE_SHIELD, "Zanite Shield");
        this.addItem(AetherIIItems.ARKENIUM_SHIELD, "Arkenium Shield");
        this.addItem(AetherIIItems.GRAVITITE_SHIELD, "Gravitite Shield");

        this.addItem(AetherIIItems.DART_SHOOTER, "Dart Shooter");
        this.addItem(AetherIIItems.AMBER_DARTS, "Amber Darts");
        this.addEffectDarts(AetherIIItems.AMBER_DARTS, "vulnerability", "Amber Darts");
        this.addEffectDarts(AetherIIItems.AMBER_DARTS, "wound", "Wound Darts");
        this.addEffectDarts(AetherIIItems.AMBER_DARTS, "stun", "Stun Darts");
        this.addEffectDarts(AetherIIItems.AMBER_DARTS, "ambrosium_poisoning", "Ambrosium Poisoning Darts");
        this.addEffectDarts(AetherIIItems.AMBER_DARTS, "fracture", "Fracture Darts");
        this.addEffectDarts(AetherIIItems.AMBER_DARTS, "toxin", "Toxin Darts");
        this.addEffectDarts(AetherIIItems.AMBER_DARTS, "venom", "Venom Darts");
        this.addEffectDarts(AetherIIItems.AMBER_DARTS, "charged", "Charged Darts");
        this.addEffectDarts(AetherIIItems.AMBER_DARTS, "webbed", "Webbed Darts");
        this.addEffectDarts(AetherIIItems.AMBER_DARTS, "immolation", "Immolation Darts");
        this.addEffectDarts(AetherIIItems.AMBER_DARTS, "frostbite", "Frostbite Darts");
        this.addEffectDarts(AetherIIItems.AMBER_DARTS, "fungal_rot", "Fungal Rot Darts");
        this.addEffectDarts(AetherIIItems.AMBER_DARTS, "crystallized", "Crystallized Darts");

        this.addItem(AetherIIItems.SCATTERGLASS_BOLT, "Scatterglass Bolt");

        this.addItem(AetherIIItems.HAMMER_OF_DEMOLITION, "Hammer of Demolition");

        // Armor
        this.addItem(AetherIIItems.BEAST_PELT_HELMET, "Beast Pelt Cap");
        this.addItem(AetherIIItems.BEAST_PELT_CHESTPLATE, "Beast Pelt Tunic");
        this.addItem(AetherIIItems.BEAST_PELT_LEGGINGS, "Beast Pelt Pants");
        this.addItem(AetherIIItems.BEAST_PELT_BOOTS, "Beast Pelt Boots");
        this.addItem(AetherIIItems.BEAST_PELT_GLOVES, "Beast Pelt Gloves");

        this.addItem(AetherIIItems.BURRUKAI_PLATE_HELMET, "Burrukai Plate Cap");
        this.addItem(AetherIIItems.BURRUKAI_PLATE_CHESTPLATE, "Burrukai Plate Tunic");
        this.addItem(AetherIIItems.BURRUKAI_PLATE_LEGGINGS, "Burrukai Plate Pants");
        this.addItem(AetherIIItems.BURRUKAI_PLATE_BOOTS, "Burrukai Plate Boots");
        this.addItem(AetherIIItems.BURRUKAI_PLATE_GLOVES, "Burrukai Plate Gloves");

        this.addItem(AetherIIItems.ZANITE_HELMET, "Zanite Helmet");
        this.addItem(AetherIIItems.ZANITE_CHESTPLATE, "Zanite Chestplate");
        this.addItem(AetherIIItems.ZANITE_LEGGINGS, "Zanite Leggings");
        this.addItem(AetherIIItems.ZANITE_BOOTS, "Zanite Boots");
        this.addItem(AetherIIItems.ZANITE_GLOVES, "Zanite Gauntlets");

        this.addItem(AetherIIItems.ARKENIUM_HELMET, "Arkenium Helmet");
        this.addItem(AetherIIItems.ARKENIUM_CHESTPLATE, "Arkenium Chestplate");
        this.addItem(AetherIIItems.ARKENIUM_LEGGINGS, "Arkenium Leggings");
        this.addItem(AetherIIItems.ARKENIUM_BOOTS, "Arkenium Boots");
        this.addItem(AetherIIItems.ARKENIUM_GLOVES, "Arkenium Gauntlets");

        this.addItem(AetherIIItems.GRAVITITE_HELMET, "Gravitite Helmet");
        this.addItem(AetherIIItems.GRAVITITE_CHESTPLATE, "Gravitite Chestplate");
        this.addItem(AetherIIItems.GRAVITITE_LEGGINGS, "Gravitite Leggings");
        this.addItem(AetherIIItems.GRAVITITE_BOOTS, "Gravitite Boots");
        this.addItem(AetherIIItems.GRAVITITE_GLOVES, "Gravitite Gauntlets");

        this.addItem(AetherIIItems.SENTRY_BOOTS, "Sentry Boots");

        this.addItem(AetherIIItems.NEPTUNE_HELMET, "Neptune Helmet");
        this.addItem(AetherIIItems.NEPTUNE_CHESTPLATE, "Neptune Chestplate");
        this.addItem(AetherIIItems.NEPTUNE_LEGGINGS, "Neptune Leggings");
        this.addItem(AetherIIItems.NEPTUNE_BOOTS, "Neptune Boots");
        this.addItem(AetherIIItems.NEPTUNE_GLOVES, "Neptune Gauntlets");

        // Relics
        this.addItem(AetherIIItems.KINETIC_THRUSTERS, "Kinetic Thrusters");

        // Accessories
        this.addItem(AetherIIItems.ZANITE_PENDANT, "Zanite Pendant");
        this.addItem(AetherIIItems.ICESTONE_PENDANT, "Icestone Pendant");

        // Charms
        this.addItem(AetherIIItems.CHARM_OF_EFFICIENCY_I, "Charm of Efficiency I");
        this.addItem(AetherIIItems.CHARM_OF_REACH_I, "Charm of Reach I");

        this.addItem(AetherIIItems.CHARM_OF_DAMAGE_I, "Charm of Damage I");
        this.addItem(AetherIIItems.CHARM_OF_DEXTERITY_I, "Charm of Dexterity I");
        this.addItem(AetherIIItems.CHARM_OF_KNOCKBACK_I, "Charm of Knockback I");

        this.addItem(AetherIIItems.CHARM_OF_HEALTH_I, "Charm of Health I");
        this.addItem(AetherIIItems.CHARM_OF_DEFENSE_I, "Charm of Defense I");
        this.addItem(AetherIIItems.CHARM_OF_TOUGHNESS_I, "Charm of Toughness I");
        this.addItem(AetherIIItems.CHARM_OF_RESISTANCE_I, "Charm of Resistance I");
        this.addItem(AetherIIItems.CHARM_OF_AGILITY_I, "Charm of Agility I");

        // Materials
        this.addItem(AetherIIItems.SKYROOT_STICK, "Skyroot Stick");
        this.addItem(AetherIIItems.SCATTERGLASS_SHARD, "Scatterglass Shard");
        this.addItem(AetherIIItems.AMBROSIUM_SHARD, "Ambrosium Shard");
        this.addItem(AetherIIItems.FOSSILIZED_ZANITE, "Fossilized Zanite");
        this.addItem(AetherIIItems.ZANITE_GEMSTONE, "Zanite Gemstone");
        this.addItem(AetherIIItems.INERT_ARKENIUM, "Inert Arkenium");
        this.addItem(AetherIIItems.ARKENIUM_PLATE, "Arkenium Plate");
        this.addItem(AetherIIItems.ARKENIUM_CHIP, "Arkenium Chip");
        this.addItem(AetherIIItems.INERT_GRAVITITE, "Inert Gravitite");
        this.addItem(AetherIIItems.GRAVITITE_PLATE, "Gravitite Plate");
        this.addItem(AetherIIItems.FOSSILIZED_CORROBONITE, "Fossilized Corrobonite");
        this.addItem(AetherIIItems.CORROBONITE_CRYSTAL, "Corrobonite Crystal");
        this.addItem(AetherIIItems.NEPTUNE_SCALE, "Neptune Scale");
        this.addItem(AetherIIItems.SENTRY_SERVO, "Sentry Servo");
        this.addItem(AetherIIItems.RESONANT_STONE, "Resonant Stone");
        this.addItem(AetherIIItems.FOSSILIZED_GLINT, "Fossilized Glint");
        this.addItem(AetherIIItems.GLINT_GEMSTONE, "Glint Gemstone");
        this.addItem(AetherIIItems.GOLDEN_AMBER, "Golden Amber");
        this.add("item.aether_ii.aether_quartz", "Aether Quartz");
        this.addItem(AetherIIItems.CLOUDTWINE, "Cloudtwine");
        this.addItem(AetherIIItems.BEAST_PELT, "Beast Pelt");
        this.addItem(AetherIIItems.BURRUKAI_PLATE, "Burrukai Plate");
        this.addItem(AetherIIItems.KIRRID_PLATE, "Kirrid Plate");
        this.addItem(AetherIIItems.SKYROOT_PINECONE, "Skyroot Pinecone");
        this.addItem(AetherIIItems.VALKYRIE_WINGS, "Valkyrie Wings");
        this.addItem(AetherIIItems.BRETTL_CANE, "Brettl Cane");
        this.addItem(AetherIIItems.BRETTL_GRASS, "Brettl Grass");
        this.addItem(AetherIIItems.BRETTL_ROPE, "Brettl Rope");
        this.addItem(AetherIIItems.AECHOR_PETAL, "Aechor Petal");
        this.addItem(AetherIIItems.ARILUM_BULBS, "Arilum Bulbs");
        this.addItem(AetherIIItems.ARCTIC_SNOWBALL, "Arctic Snowball");
        this.addItem(AetherIIItems.SWET_GEL, "Swet Gel");
        this.addItem(AetherIIItems.SWET_SUGAR, "Swet Sugar");
        this.addItem(AetherIIItems.PRISMALLARD_FEATHER, "Prismallard Feather");
        this.addItem(AetherIIItems.MOA_FEATHER, "Moa Feather");
        this.addItem(AetherIIItems.COCKATRICE_FEATHER, "Cockatrice Feather");
        this.addItem(AetherIIItems.SCATTERGLASS_VIAL, "Scatterglass Vial");
        this.addItem(AetherIIItems.ZEPHYR_HUSK, "Zephyr Husk");
        this.addItem(AetherIIItems.CHARGE_CATALYST, "Charge Catalyst");
        this.addItem(AetherIIItems.ARKENIUM_CORE, "Arkenium Core");
        this.addItem(AetherIIItems.GRAVITITE_CORE, "Gravitite Core");
        this.addItem(AetherIIItems.EYE_OF_THE_MIMIC, "Eye of the Mimic");

        // Irradiated Items
        this.addItem(AetherIIItems.IRRADIATED_ARMOR, "Irradiated Armor");
        this.addItem(AetherIIItems.IRRADIATED_WEAPON, "Irradiated Weapon");
        this.addItem(AetherIIItems.IRRADIATED_TOOL, "Irradiated Tool");
        this.addItem(AetherIIItems.IRRADIATED_CHUNK, "Irradiated Chunk");
        this.addItem(AetherIIItems.IRRADIATED_DUST, "Irradiated Dust");
        this.add("item.aether_ii.irradiated_armor_result", "Random Armor");
        this.add("item.aether_ii.irradiated_weapon_result", "Random Weapon");
        this.add("item.aether_ii.irradiated_tool_result", "Random Tool");
        this.add("item.aether_ii.irradiated_chunk_result", "Random Item");

        // Food
        this.addItem(AetherIIItems.BLUEBERRY, "Blueberry");
        this.addItem(AetherIIItems.ENCHANTED_BLUEBERRY, "Enchanted Blueberry");
        this.addItem(AetherIIItems.ORANGE, "Orange");
        this.addItem(AetherIIItems.ENCHANTED_ORANGE, "Enchanted Orange");
        this.addItem(AetherIIItems.WYNDBERRY, "Wyndberry");
        this.addItem(AetherIIItems.ENCHANTED_WYNDBERRY, "Enchanted Wyndberry");
        this.addItem(AetherIIItems.GOLDEN_WYNDBERRY, "Golden Wyndberry");
        this.addItem(AetherIIItems.SATIVAL_BULB, "Satival Bulb");
        this.addItem(AetherIIItems.SWET_JELLY, "Swet Jelly");
        this.addItem(AetherIIItems.ENCHANTED_SWET_JELLY, "Enchanted Swet Jelly");
        this.addItem(AetherIIItems.FRIED_PRISMALLARD_EGG, "Fried Prismallard Egg");
        this.addItem(AetherIIItems.PRISMALLARD_LEG, "Prismallard Leg");
        this.addItem(AetherIIItems.PRISMALLARD_ROAST, "Prismallard Roast");
        this.addItem(AetherIIItems.BURRUKAI_RIBS, "Burrukai Ribs");
        this.addItem(AetherIIItems.BURRUKAI_RIB_CUT, "Burrukai Rib Cut");
        this.addItem(AetherIIItems.KIRRID_CUTLET, "Kirrid Cutlet");
        this.addItem(AetherIIItems.KIRRID_LOIN, "Kirrid Loin");
        this.addItem(AetherIIItems.RAW_TAEGORE_MEAT, "Raw Taegore Meat");
        this.addItem(AetherIIItems.TAEGORE_STEAK, "Taegore Steak");
        this.addItem(AetherIIItems.SKYROOT_LIZARD_ON_A_STICK, "Skyroot Lizard on a Stick");
        this.addItem(AetherIIItems.ROASTED_SKYROOT_LIZARD_ON_A_STICK, "Roasted Skyroot Lizard on a Stick");

        // Consumables
        this.addItem(AetherIIItems.WATER_VIAL, "Water Vial");
        this.addItem(AetherIIItems.BANDAGE, "Bandage");
        this.addItem(AetherIIItems.SPLINT, "Splint");
        this.addItem(AetherIIItems.ANTITOXIN_VIAL, "Antitoxin Vial");
        this.addItem(AetherIIItems.ANTIVENOM_VIAL, "Antivenom Vial");
        this.addItem(AetherIIItems.VALKYRIE_TEA, "Valkyrie Tea");
        this.addItem(AetherIIItems.HEALING_STONE, "Healing Stone");

        // Utilities
        this.addItem(AetherIIItems.SHIFTING_GLASS, "Shifting Glass");

        // Companions
        this.addItem(AetherIIItems.AERBUNNY_BELL, "Aerbunny Bell");

        // Gliders
        this.addItem(AetherIIItems.COLD_AERCLOUD_GLIDER, "Cold Aercloud Glider");
        this.addItem(AetherIIItems.GOLDEN_AERCLOUD_GLIDER, "Golden Aercloud Glider");
        this.addItem(AetherIIItems.BLUE_AERCLOUD_GLIDER, "Blue Aercloud Glider");
        this.addItem(AetherIIItems.PURPLE_AERCLOUD_GLIDER, "Purple Aercloud Glider");

        // Skyroot Buckets
        this.addItem(AetherIIItems.SKYROOT_BUCKET, "Skyroot Bucket");
        this.addItem(AetherIIItems.SKYROOT_WATER_BUCKET, "Skyroot Water Bucket");
        this.addItem(AetherIIItems.SKYROOT_MILK_BUCKET, "Skyroot Milk Bucket");
        this.addItem(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET, "Skyroot Powder Snow Bucket");
        this.addItem(AetherIIItems.SKYROOT_COD_BUCKET, "Skyroot Bucket of Cod");
        this.addItem(AetherIIItems.SKYROOT_SALMON_BUCKET, "Skyroot Bucket of Salmon");
        this.addItem(AetherIIItems.SKYROOT_PUFFERFISH_BUCKET, "Skyroot Bucket of Pufferfish");
        this.addItem(AetherIIItems.SKYROOT_TROPICAL_FISH_BUCKET, "Skyroot Bucket of Tropical Fish");
        this.addItem(AetherIIItems.SKYROOT_AXOLOTL_BUCKET, "Skyroot Bucket of Axolotl");
        this.addItem(AetherIIItems.SKYROOT_TADPOLE_BUCKET, "Skyroot Bucket of Tadpole");

        // Arkenium Canisters
        this.addItem(AetherIIItems.ARKENIUM_CANISTER, "Arkenium Canister");
        this.addItem(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER, "Arkenium Alkahest Canister");
        this.addItem(AetherIIItems.ARKENIUM_HESTVEIL_CANISTER, "Arkenium Hestveil Canister");

        // Music Discs
        this.addItem(AetherIIItems.MUSIC_PLAYER, "Music Player");
        this.addItem(AetherIIItems.ENGRAVED_DISC_ASCENDING_DAWN, "Engraved Disc");
        this.addItem(AetherIIItems.ENGRAVED_DISC_AERWHALE, "Engraved Disc");
        this.addItem(AetherIIItems.ENGRAVED_DISC_APPROACHES, "Engraved Disc");
        this.addItem(AetherIIItems.ENGRAVED_DISC_DEMISE, "Engraved Disc");
        this.addItem(AetherIIItems.ENGRAVED_DISC_CHINCHILLA, "Engraved Disc");
        this.addItem(AetherIIItems.ENGRAVED_DISC_HIGH, "Engraved Disc");
        this.addItem(AetherIIItems.ENGRAVED_DISC_REVOLUTIONS, "Engraved Disc");

        // Spawn Eggs
        this.addSpawnEggItem(AetherIIItems.AERBUNNY_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.FLYING_COW_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.SHEEPUFF_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.PHYG_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.AERWHALE_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.HIGHFIELDS_TAEGORE_SPAWN_EGG, "Taegore Spawn Egg");
        this.addSpawnEggItem(AetherIIItems.MAGNETIC_TAEGORE_SPAWN_EGG, "Taegore Spawn Egg");
        this.addSpawnEggItem(AetherIIItems.ARCTIC_TAEGORE_SPAWN_EGG, "Taegore Spawn Egg");
        this.addSpawnEggItem(AetherIIItems.HIGHFIELDS_BURRUKAI_SPAWN_EGG, "Burrukai Spawn Egg");
        this.addSpawnEggItem(AetherIIItems.MAGNETIC_BURRUKAI_SPAWN_EGG, "Burrukai Spawn Egg");
        this.addSpawnEggItem(AetherIIItems.ARCTIC_BURRUKAI_SPAWN_EGG, "Burrukai Spawn Egg");
        this.addSpawnEggItem(AetherIIItems.HIGHFIELDS_KIRRID_SPAWN_EGG, "Kirrid Spawn Egg");
        this.addSpawnEggItem(AetherIIItems.MAGNETIC_KIRRID_SPAWN_EGG, "Kirrid Spawn Egg");
        this.addSpawnEggItem(AetherIIItems.ARCTIC_KIRRID_SPAWN_EGG, "Kirrid Spawn Egg");
        this.addSpawnEggItem(AetherIIItems.MOA_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.PRISMALLARD_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.SKYROOT_LIZARD_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.AECHOR_PLANT_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.CARRION_SPROUT_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.GLITTERWING_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.SHROUDWING_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.ZEPHYR_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.TEMPEST_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.COCKATRICE_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.BLUE_SWET_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.GOLDEN_SWET_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.SKEPHID_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.ARKENIUM_TALUTON_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.GRAVITITE_TALUTON_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.DETONATION_SENTRY_SPAWN_EGG);
        this.addSpawnEggItem(AetherIIItems.SENTRY_GOLEM_SPAWN_EGG);
        this.addItem(AetherIIItems.SENTRY_CRATE_MIMIC_SPAWN_EGG, "Sentry Crate Mimic Spawn Egg");
        this.addSpawnEggItem(AetherIIItems.SLIDER_SPAWN_EGG);

        // Misc
        this.addItem(AetherIIItems.BEAST_PELT_BUNDLE, "Beast Pelt Bundle");
        this.addItem(AetherIIItems.BRETTL_LASSO, "Brettl Lasso");
        this.addItem(AetherIIItems.PRISMALLARD_EGG, "Prismallard Egg");
        this.addItem(AetherIIItems.MOA_EGG, "Moa Egg");
        this.addItem(AetherIIItems.MOA_FEED, "Moa Feed");
        this.addItem(AetherIIItems.BLUEBERRY_MOA_FEED, "Blueberry Moa Feed");
        this.addItem(AetherIIItems.ENCHANTED_MOA_FEED, "Enchanted Moa Feed");
        this.addItem(AetherIIItems.MOA_SADDLE, "Moa Saddle");
        this.addItem(AetherIIItems.MOA_SADDLEBAG, "Moa Saddlebag");
        this.addItem(AetherIIItems.LARGE_MOA_SADDLEBAG, "Large Moa Saddlebag");
        this.addItem(AetherIIItems.CLOUD_SKIFF, "Cloud Skiff");
        this.addItem(AetherIIItems.GLINT_COIN, "Glint Coin");
        this.addItem(AetherIIItems.GUIDEBOOK_PAGE, "Guidebook Page");
        this.addItem(AetherIIItems.AETHER_PORTAL_FRAME, "Aether Portal Frame");
        this.addItem(AetherIIItems.MURAL_ITEM, "Mural");
        this.addItem(AetherIIItems.BROKEN_ITEM, "Broken Item");

        this.add("item.aether_ii.broken_item_template", "Broken %s");
    }

    private void addPerItemAbilityTooltips() {
        // Abilities
        this.addPerItemAbilityTooltip(AetherIIItems.SKYROOT_PICKAXE.get(), 1, "§9Ability:§r Increases Yield");
        this.addPerItemAbilityTooltip(AetherIIItems.SKYROOT_AXE.get(), 1, "§9Ability:§r Increases Yield");
        this.addPerItemAbilityTooltip(AetherIIItems.SKYROOT_SHOVEL.get(), 1, "§9Ability:§r Increases Yield");
        this.addPerItemAbilityTooltip(AetherIIItems.SKYROOT_TROWEL.get(), 1, "§9Ability:§r Increases Yield");
        this.addPerItemAbilityTooltip(AetherIIItems.SKYROOT_SHORTSWORD.get(), 1, "§9Ability:§r Increases Yield");
        this.addPerItemAbilityTooltip(AetherIIItems.SKYROOT_HAMMER.get(), 1, "§9Ability:§r Increases Yield");
        this.addPerItemAbilityTooltip(AetherIIItems.SKYROOT_PIKE.get(), 1, "§9Ability:§r Increases Yield");
        this.addPerItemAbilityTooltip(AetherIIItems.SKYROOT_CROSSBOW.get(), 1, "§9Ability:§r Double Shot");
        this.addPerItemAbilityTooltip(AetherIIItems.SKYROOT_CROSSBOW.get(), 2, "§3Use:§r Crouch-Use");

        this.addPerItemAbilityTooltip(AetherIIItems.HOLYSTONE_PICKAXE.get(), 1, "§9Ability:§r Sheds Ambrosium");
        this.addPerItemAbilityTooltip(AetherIIItems.HOLYSTONE_AXE.get(), 1, "§9Ability:§r Sheds Ambrosium");
        this.addPerItemAbilityTooltip(AetherIIItems.HOLYSTONE_SHOVEL.get(), 1, "§9Ability:§r Sheds Ambrosium");
        this.addPerItemAbilityTooltip(AetherIIItems.HOLYSTONE_TROWEL.get(), 1, "§9Ability:§r Sheds Ambrosium");
        this.addPerItemAbilityTooltip(AetherIIItems.HOLYSTONE_SHORTSWORD.get(), 1, "§9Ability:§r Sheds Ambrosium");
        this.addPerItemAbilityTooltip(AetherIIItems.HOLYSTONE_HAMMER.get(), 1, "§9Ability:§r Sheds Ambrosium");
        this.addPerItemAbilityTooltip(AetherIIItems.HOLYSTONE_PIKE.get(), 1, "§9Ability:§r Sheds Ambrosium");
        this.addPerItemAbilityTooltip(AetherIIItems.HOLYSTONE_CROSSBOW.get(), 1, "§9Ability:§r Spread Shot");
        this.addPerItemAbilityTooltip(AetherIIItems.HOLYSTONE_CROSSBOW.get(), 2, "§3Use:§r Crouch-Use");

        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_PICKAXE.get(), 1, "§9Ability:§r Grows Stronger");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_AXE.get(), 1, "§9Ability:§r Grows Stronger");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_SHOVEL.get(), 1, "§9Ability:§r Grows Stronger");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_TROWEL.get(), 1, "§9Ability:§r Grows Stronger");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_SHORTSWORD.get(), 1, "§9Ability:§r Grows Stronger");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_HAMMER.get(), 1, "§9Ability:§r Grows Stronger");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_PIKE.get(), 1, "§9Ability:§r Grows Stronger");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_CROSSBOW.get(), 1, "§9Ability:§r Grows Stronger");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_CROSSBOW.get(), 2, "§3Use:§r Crouch-Use");

        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_SHEARS.get(), 1, "§9Ability:§r Grows Stronger");

        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_PICKAXE.get(), 1, "§9Ability:§r Upgrades Further");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_AXE.get(), 1, "§9Ability:§r Upgrades Further");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_SHOVEL.get(), 1, "§9Ability:§r Upgrades Further");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_TROWEL.get(), 1, "§9Ability:§r Upgrades Further");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_SHORTSWORD.get(), 1, "§9Ability:§r Upgrades Further");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_HAMMER.get(), 1, "§9Ability:§r Upgrades Further");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_PIKE.get(), 1, "§9Ability:§r Upgrades Further");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_CROSSBOW.get(), 1, "§9Ability:§r Upgrades Further");

        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_PICKAXE.get(), 1, "§9Ability:§r Levitates Block");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_PICKAXE.get(), 2, "§3Use:§r Crouch-Interact");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_AXE.get(), 1, "§9Ability:§r Levitates Block");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_AXE.get(), 2, "§3Use:§r Crouch-Interact");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_SHOVEL.get(), 1, "§9Ability:§r Levitates Block");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_SHOVEL.get(), 2, "§3Use:§r Crouch-Interact");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_TROWEL.get(), 1, "§9Ability:§r Levitates Block");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_TROWEL.get(), 2, "§3Use:§r Crouch-Interact");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_SHORTSWORD.get(), 1, "§9Ability:§r Increases Gravity");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_HAMMER.get(), 1, "§9Ability:§r Increases Gravity");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_PIKE.get(), 1, "§9Ability:§r Increases Gravity");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_CROSSBOW.get(), 1, "§9Ability:§r Straight Shot");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_CROSSBOW.get(), 2, "§3Use:§r Crouch-Use");

        this.addPerItemAbilityTooltip(AetherIIItems.HAMMER_OF_DEMOLITION.get(), 1, "§9Ability:§r Shoots Explosive");
        this.addPerItemAbilityTooltip(AetherIIItems.HAMMER_OF_DEMOLITION.get(), 2, "§3Use:§r Crouch-Use");

        this.addPerItemAbilityTooltip(AetherIIItems.BEAST_PELT_BOOTS.get(), 1, "§9Ability:§r Calms Animals");
        this.addPerItemAbilityTooltip(AetherIIItems.BEAST_PELT_BOOTS.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.BEAST_PELT_LEGGINGS.get(), 1, "§9Ability:§r Calms Animals");
        this.addPerItemAbilityTooltip(AetherIIItems.BEAST_PELT_LEGGINGS.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.BEAST_PELT_CHESTPLATE.get(), 1, "§9Ability:§r Calms Animals");
        this.addPerItemAbilityTooltip(AetherIIItems.BEAST_PELT_CHESTPLATE.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.BEAST_PELT_HELMET.get(), 1, "§9Ability:§r Calms Animals");
        this.addPerItemAbilityTooltip(AetherIIItems.BEAST_PELT_HELMET.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.BEAST_PELT_GLOVES.get(), 1, "§9Ability:§r Calms Animals");
        this.addPerItemAbilityTooltip(AetherIIItems.BEAST_PELT_GLOVES.get(), 2, "§9Set Pieces:§r %s");

        this.addPerItemAbilityTooltip(AetherIIItems.BURRUKAI_PLATE_BOOTS.get(), 1, "§9Ability:§r Stun Resistance");
        this.addPerItemAbilityTooltip(AetherIIItems.BURRUKAI_PLATE_BOOTS.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.BURRUKAI_PLATE_LEGGINGS.get(), 1, "§9Ability:§r Stun Resistance");
        this.addPerItemAbilityTooltip(AetherIIItems.BURRUKAI_PLATE_LEGGINGS.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.BURRUKAI_PLATE_CHESTPLATE.get(), 1, "§9Ability:§r Stun Resistance");
        this.addPerItemAbilityTooltip(AetherIIItems.BURRUKAI_PLATE_CHESTPLATE.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.BURRUKAI_PLATE_HELMET.get(), 1, "§9Ability:§r Stun Resistance");
        this.addPerItemAbilityTooltip(AetherIIItems.BURRUKAI_PLATE_HELMET.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.BURRUKAI_PLATE_GLOVES.get(), 1, "§9Ability:§r Stun Resistance");
        this.addPerItemAbilityTooltip(AetherIIItems.BURRUKAI_PLATE_GLOVES.get(), 2, "§9Set Pieces:§r %s");

        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_BOOTS.get(), 1, "§9Ability:§r Speed Boost");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_BOOTS.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_LEGGINGS.get(), 1, "§9Ability:§r Speed Boost");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_LEGGINGS.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_CHESTPLATE.get(), 1, "§9Ability:§r Speed Boost");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_CHESTPLATE.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_HELMET.get(), 1, "§9Ability:§r Speed Boost");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_HELMET.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_GLOVES.get(), 1, "§9Ability:§r Speed Boost");
        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_GLOVES.get(), 2, "§9Set Pieces:§r %s");

        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_BOOTS.get(), 1, "§9Ability:§r Upgrades Further");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_BOOTS.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_LEGGINGS.get(), 1, "§9Ability:§r Upgrades Further");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_LEGGINGS.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_CHESTPLATE.get(), 1, "§9Ability:§r Upgrades Further");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_CHESTPLATE.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_HELMET.get(), 1, "§9Ability:§r Upgrades Further");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_HELMET.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_GLOVES.get(), 1, "§9Ability:§r Upgrades Further");
        this.addPerItemAbilityTooltip(AetherIIItems.ARKENIUM_GLOVES.get(), 2, "§9Set Pieces:§r %s");

        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_BOOTS.get(), 1, "§9Ability:§r Double Jump");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_BOOTS.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_LEGGINGS.get(), 1, "§9Ability:§r Double Jump");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_LEGGINGS.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_CHESTPLATE.get(), 1, "§9Ability:§r Double Jump");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_CHESTPLATE.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_HELMET.get(), 1, "§9Ability:§r Double Jump");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_HELMET.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_GLOVES.get(), 1, "§9Ability:§r Double Jump");
        this.addPerItemAbilityTooltip(AetherIIItems.GRAVITITE_GLOVES.get(), 2, "§9Set Pieces:§r %s");

        this.addPerItemAbilityTooltip(AetherIIItems.SENTRY_BOOTS.get(), 1, "§9Ability:§r Zephyr Protection");

        this.addPerItemAbilityTooltip(AetherIIItems.NEPTUNE_BOOTS.get(), 1, "§9Ability:§r Walk in Water");
        this.addPerItemAbilityTooltip(AetherIIItems.NEPTUNE_BOOTS.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.NEPTUNE_LEGGINGS.get(), 1, "§9Ability:§r Walk in Water");
        this.addPerItemAbilityTooltip(AetherIIItems.NEPTUNE_LEGGINGS.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.NEPTUNE_CHESTPLATE.get(), 1, "§9Ability:§r Walk in Water");
        this.addPerItemAbilityTooltip(AetherIIItems.NEPTUNE_CHESTPLATE.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.NEPTUNE_HELMET.get(), 1, "§9Ability:§r Walk in Water");
        this.addPerItemAbilityTooltip(AetherIIItems.NEPTUNE_HELMET.get(), 2, "§9Set Pieces:§r %s");
        this.addPerItemAbilityTooltip(AetherIIItems.NEPTUNE_GLOVES.get(), 1, "§9Ability:§r Walk in Water");
        this.addPerItemAbilityTooltip(AetherIIItems.NEPTUNE_GLOVES.get(), 2, "§9Set Pieces:§r %s");

        this.addPerItemAbilityTooltip(AetherIIItems.ZANITE_PENDANT.get(), 1, "§9Ability:§r Grows Stronger");
        this.addPerItemAbilityTooltip(AetherIIItems.ICESTONE_PENDANT.get(), 1, "§9Ability:§r Freezes Liquids");

        this.addPerItemAbilityTooltip(AetherIIItems.COLD_AERCLOUD_GLIDER.get(), 1, "§3Use:§r Click-Use");
        this.addPerItemAbilityTooltip(AetherIIItems.GOLDEN_AERCLOUD_GLIDER.get(), 1, "§3Use:§r Click-Use");
        this.addPerItemAbilityTooltip(AetherIIItems.BLUE_AERCLOUD_GLIDER.get(), 1, "§9Ability:§r Upwards Boost");
        this.addPerItemAbilityTooltip(AetherIIItems.BLUE_AERCLOUD_GLIDER.get(), 2, "§3Use:§r Click-Use");
        this.addPerItemAbilityTooltip(AetherIIItems.PURPLE_AERCLOUD_GLIDER.get(), 1, "§9Ability:§r Forwards Boost");
        this.addPerItemAbilityTooltip(AetherIIItems.PURPLE_AERCLOUD_GLIDER.get(), 2, "§3Use:§r Click-Use");

        this.addPerItemAbilityTooltip(AetherIIItems.SWET_GEL.get(), 1, "§9Ability:§r Grows Nature");
        this.addPerItemAbilityTooltip(AetherIIItems.AMBROSIUM_SHARD.get(), 1, "§9Ability:§r Enchants Nature");
        this.addPerItemAbilityTooltip(AetherIIItems.IRRADIATED_DUST.get(), 1, "§9Ability:§r Irradiates Nature");

        this.addPerItemAbilityTooltip(AetherIIItems.GOLDEN_WYNDBERRY.get(), 1, "§9Ability:§r Prevents Baby Animal Aging");

        this.addPerItemAbilityTooltip(AetherIIItems.SHIFTING_GLASS.get(), 1, "§9Ability:§r Directional Dash");
        this.addPerItemAbilityTooltip(AetherIIItems.SHIFTING_GLASS.get(), 2, "§3Use:§r Click-Use");
    }

    private void addItemTooltips() {
        // Miscellaneous Item Tooltips
        this.addItemTooltip("treasure.description", "Treasure Item");
        this.addItemTooltip("currency.description", "Converts to Currency:");
        this.addItemTooltip("currency.amount", "%s Glint");
        this.addItemTooltip("curative.removes", "Removes: %s");
        this.addItemTooltip("curative.reduces", "Reduces: %s");
        this.addItemTooltip("healing_stone.charges", "%s/5 Altar Charges");
        this.addItemTooltip("effect_buildup", "%s Buildup");
        this.addItemTooltip("effect_buildup.inflicts", "Inflicts: %s");
        this.addItemTooltip("reinforcement", "Reinforcement %s");
        this.addItemTooltip("broken", "Requires Repairing");
        this.addItemTooltip("charm.tier", "Tier %s");
        this.addItemTooltip("charm.type.tool", "Tool Charm");
        this.addItemTooltip("charm.type.weapon", "Weapon Charm");
        this.addItemTooltip("charm.type.armor", "Armor Charm");
        this.addItemTooltip("companion.status", "Status: %s");
        this.addItemTooltip("companion.status.empty", "Empty");
        this.addItemTooltip("companion.status.stored", "Stored");
        this.addItemTooltip("companion.status.recovering", "Recovering");
        this.addItemTooltip("companion.status.active", "Active");

        // Moa Egg Tooltips
        this.addItemTooltip("moa_egg.keratin", "%s Keratin");
        this.addItemTooltip("moa_egg.eyes", "%s Eyes");
        this.addItemTooltip("moa_egg.feathers", "%1$s %2$s Feathers");

        this.addKeratinColor(Moa.KeratinColor.BLUE, "Blue");
        this.addKeratinColor(Moa.KeratinColor.BROWN, "Brown");
        this.addKeratinColor(Moa.KeratinColor.GREEN, "Green");
        this.addKeratinColor(Moa.KeratinColor.GRAY, "Gray");
        this.addKeratinColor(Moa.KeratinColor.RED, "Red");
        this.addKeratinColor(Moa.KeratinColor.BLEY, "Bluish-Gray");

        this.addEyeColor(Moa.EyeColor.BLUE, "Blue");
        this.addEyeColor(Moa.EyeColor.GREEN, "Green");
        this.addEyeColor(Moa.EyeColor.YELLOW, "Yellow");
        this.addEyeColor(Moa.EyeColor.GOLD, "Gold");

        this.addFeatherColor(Moa.FeatherColor.BLACK, "Black");
        this.addFeatherColor(Moa.FeatherColor.BLOOMING_RED, "Blooming Red");
        this.addFeatherColor(Moa.FeatherColor.BLUE, "Blue");
        this.addFeatherColor(Moa.FeatherColor.BROWN, "Brown");
        this.addFeatherColor(Moa.FeatherColor.CLASSIC_BLACK, "Classic Black");
        this.addFeatherColor(Moa.FeatherColor.CYAN, "Cyan");
        this.addFeatherColor(Moa.FeatherColor.GRAY, "Gray");
        this.addFeatherColor(Moa.FeatherColor.GREEN, "Green");
        this.addFeatherColor(Moa.FeatherColor.LIGHT_BLUE, "Light Blue");
        this.addFeatherColor(Moa.FeatherColor.LIGHT_GRAY, "Light Gray");
        this.addFeatherColor(Moa.FeatherColor.LIME, "Lime");
        this.addFeatherColor(Moa.FeatherColor.MAGENTA, "Magenta");
        this.addFeatherColor(Moa.FeatherColor.ORANGE, "Orange");
        this.addFeatherColor(Moa.FeatherColor.PINK, "Pink");
        this.addFeatherColor(Moa.FeatherColor.PURPLE, "Purple");
        this.addFeatherColor(Moa.FeatherColor.RED, "Red");
        this.addFeatherColor(Moa.FeatherColor.WHITE, "White");
        this.addFeatherColor(Moa.FeatherColor.YELLOW, "Yellow");
        this.addFeatherColor(Moa.FeatherColor.DEEP_BLUE, "Deep Blue");

        this.addFeatherShape(Moa.FeatherShape.CURVED, "Curved");
        this.addFeatherShape(Moa.FeatherShape.FLAT, "Flat");
        this.addFeatherShape(Moa.FeatherShape.POINTED, "Pointed");
    }

    private void addAccessorySlots() {
        // Accessory Slots
        this.addAccessorySlot("relic_slot", "Relic");
        this.addAccessorySlot("handwear_slot", "Handwear");
        this.addAccessorySlot("accessory_slot", "Accessory");
    }

    private void addEntityTypes() {
        // Passive
        this.addEntityType(AetherIIEntityTypes.AERBUNNY, "Aerbunny");
        this.addEntityType(AetherIIEntityTypes.AERWHALE, "Aerwhale");
        this.addEntityType(AetherIIEntityTypes.PHYG, "Phyg");
        this.addEntityType(AetherIIEntityTypes.FLYING_COW, "Flying Cow");
        this.addEntityType(AetherIIEntityTypes.SHEEPUFF, "Sheepuff");
        this.addEntityType(AetherIIEntityTypes.HIGHFIELDS_TAEGORE, "Highfields Taegore");
        this.addEntityType(AetherIIEntityTypes.MAGNETIC_TAEGORE, "Magnetic Taegore");
        this.addEntityType(AetherIIEntityTypes.ARCTIC_TAEGORE, "Arctic Taegore");
        this.addEntityType(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI, "Highfields Burrukai");
        this.addEntityType(AetherIIEntityTypes.MAGNETIC_BURRUKAI, "Magnetic Burrukai");
        this.addEntityType(AetherIIEntityTypes.ARCTIC_BURRUKAI, "Arctic Burrukai");
        this.addEntityType(AetherIIEntityTypes.HIGHFIELDS_KIRRID, "Highfields Kirrid");
        this.addEntityType(AetherIIEntityTypes.MAGNETIC_KIRRID, "Magnetic Kirrid");
        this.addEntityType(AetherIIEntityTypes.ARCTIC_KIRRID, "Arctic Kirrid");
        this.addEntityType(AetherIIEntityTypes.MOA, "Moa");
        this.addEntityType(AetherIIEntityTypes.PRISMALLARD, "Prismallard");
        this.addEntityType(AetherIIEntityTypes.SKYROOT_LIZARD, "Skyroot Lizard");
        this.addEntityType(AetherIIEntityTypes.GLITTERWING, "Glitterwing");
        this.addEntityType(AetherIIEntityTypes.SHROUDWING, "Shroudwing");

        // Hostile
        this.addEntityType(AetherIIEntityTypes.AECHOR_PLANT, "Aechor Plant");
        this.addEntityType(AetherIIEntityTypes.CARRION_SPROUT, "Carrion Sprout");
        this.addEntityType(AetherIIEntityTypes.ZEPHYR, "Zephyr");
        this.addEntityType(AetherIIEntityTypes.TEMPEST, "Tempest");
        this.addEntityType(AetherIIEntityTypes.COCKATRICE, "Cockatrice");
        this.addEntityType(AetherIIEntityTypes.BLUE_SWET, "Blue Swet");
        this.addEntityType(AetherIIEntityTypes.GOLDEN_SWET, "Golden Swet");
        this.addEntityType(AetherIIEntityTypes.SKEPHID, "Skephid");
        this.addEntityType(AetherIIEntityTypes.ARKENIUM_TALUTON, "Arkenium Taluton");
        this.addEntityType(AetherIIEntityTypes.GRAVITITE_TALUTON, "Gravitite Taluton");
        this.addEntityType(AetherIIEntityTypes.MIMIC, "Mimic");
        this.addEntityType(AetherIIEntityTypes.DETONATION_SENTRY, "Detonation Sentry");
        this.addEntityType(AetherIIEntityTypes.DEMOLITION_PROJECTILE, "Detonation Projectile");
        this.addEntityType(AetherIIEntityTypes.SENTRY_GOLEM, "Sentry Golem");
        this.addEntityType(AetherIIEntityTypes.SLIDER, "Slider");
        this.addEntityType(AetherIIEntityTypes.BLADESHROOM_HUNTER, "Bladeshroom Hunter");

        // Projectile
        this.addEntityType(AetherIIEntityTypes.HOLYSTONE_ROCK, "Holystone Rock");
        this.addEntityType(AetherIIEntityTypes.ARCTIC_SNOWBALL, "Arctic Snowball");
        this.addEntityType(AetherIIEntityTypes.SKYROOT_PINECONE, "Skyroot Pinecone");
        this.addEntityType(AetherIIEntityTypes.PRISMALLARD_EGG, "Prismallard Egg");
        this.addEntityType(AetherIIEntityTypes.LASSO_LOOP, "Lasso Loop");
        this.addEntityType(AetherIIEntityTypes.SCATTERGLASS_BOLT, "Scatterglass Bolt");
        this.addEntityType(AetherIIEntityTypes.AMBER_DART, "Amber Dart");
        this.addEntityType(AetherIIEntityTypes.ZEPHYR_WEBBING_BALL, "Zephyr Webbing Ball");
        this.addEntityType(AetherIIEntityTypes.TEMPEST_THUNDERBALL, "Tempest Thunderball");
        this.addEntityType(AetherIIEntityTypes.SKEPHID_WEBBING_BALL, "Skephid Webbing Ball");
        this.addEntityType(AetherIIEntityTypes.TOXIC_DART, "Toxic Dart");
        this.addEntityType(AetherIIEntityTypes.VENOMOUS_DART, "Venomous Dart");
        this.addEntityType(AetherIIEntityTypes.GRAVITITE_DEBRIS_SHOT, "Gravitite Debris Shot");

        // NPCs
        this.addEntityType(AetherIIEntityTypes.EDWARD, "Edward");

        // Miscellaneous
        this.addEntityType(AetherIIEntityTypes.ELECTRIC_FIELD, "Electric Field");
    }

    private void addBiomes() {
        // Highfields
        this.addBiome(HolyIslesBiomes.FLOURISHING_FIELD, "Flourishing Field");
        this.addBiome(HolyIslesBiomes.VERDANT_WOODS, "Verdant Woods");
        this.addBiome(HolyIslesBiomes.SHROUDED_FOREST, "Shrouded Forest");
        this.addBiome(HolyIslesBiomes.SHIMMERING_BASIN, "Shimmering Basin");

        // Magnetic
        this.addBiome(HolyIslesBiomes.MAGNETIC_SCAR, "Magnetic Scar");
        this.addBiome(HolyIslesBiomes.TURQUOISE_FOREST, "Turquoise Forest");
        this.addBiome(HolyIslesBiomes.VIOLET_HIGHWOODS, "Violet Highwoods");
        this.addBiome(HolyIslesBiomes.GLISTENING_SWAMP, "Glistening Swamp");

        // Arctic
        this.addBiome(HolyIslesBiomes.FRIGID_SIERRA, "Frigid Sierra");
        this.addBiome(HolyIslesBiomes.ENDURING_WOODLAND, "Enduring Woodland");
        this.addBiome(HolyIslesBiomes.FROZEN_LAKES, "Frozen Lakes");
        this.addBiome(HolyIslesBiomes.SHEER_TUNDRA, "Sheer Tundra");

        // Irradiated
        this.addBiome(HolyIslesBiomes.CONTAMINATED_JUNGLE, "Contaminated Jungle");
        this.addBiome(HolyIslesBiomes.BATTLEGROUND_WASTES, "Battleground Wastes");

        // Expanse
        this.addBiome(HolyIslesBiomes.HIGHFIELDS_EXPANSE, "Highfields Expanse");
        this.addBiome(HolyIslesBiomes.MAGNETIC_EXPANSE, "Magnetic Expanse");
        this.addBiome(HolyIslesBiomes.ARCTIC_EXPANSE, "Arctic Expanse");
        this.addBiome(HolyIslesBiomes.IRRADIATED_EXPANSE, "Irradiated Expanse");

        // Caves
        this.addBiome(HolyIslesBiomes.HIGHFIELDS_UNDERCLOUD, "Highfields Undercloud");
        this.addBiome(HolyIslesBiomes.MAGNETIC_UNDERCLOUD, "Magnetic Undercloud");
        this.addBiome(HolyIslesBiomes.ARCTIC_UNDERCLOUD, "Arctic Undercloud");
        this.addBiome(HolyIslesBiomes.IRRADIATED_UNDERCLOUD, "Irradiated Undercloud");

        this.addBiome(HolyIslesBiomes.HESTVEIL_CAVERNS, "Hestveil Caverns");
    }

    private void addStructures() {
        // Structures
        this.addStructure(AetherIIStructures.OUTPOST, "Outpost");
        this.addStructure(AetherIIStructures.CAMP_HIGHFIELDS, "Highfields Camp");
        this.addStructure(AetherIIStructures.CAMP_MAGNETIC, "Magnetic Camp");
        this.addStructure(AetherIIStructures.CAMP_ARCTIC, "Arctic Camp");
        this.addStructure(AetherIIStructures.WATCHTOWER, "Watchtower");
        this.addStructure(AetherIIStructures.ANIMAL_DEN, "Animal Den");
        this.addStructure(AetherIIStructures.VERADEXIAN_RUINS_TEMPERATE, "Temperate Veradexian Ruins");
        this.addStructure(AetherIIStructures.VERADEXIAN_RUINS_ARCTIC, "Arctic Veradexian Ruins");
        this.addStructure(AetherIIStructures.VERADEXIAN_LIBRARY_TEMPERATE, "Temperate Veradexian Library");
        this.addStructure(AetherIIStructures.VERADEXIAN_LIBRARY_ARCTIC, "Arctic Veradexian Library");
        this.addStructure(AetherIIStructures.VERADEXIAN_AQUEDUCT, "Veradexian Aqueduct");
        this.addStructure(AetherIIStructures.BREXALLEN_RUINS, "Brexallen Ruins");
        this.addStructure(AetherIIStructures.UNDERCLOUD_MINESHAFT, "Undercloud Mineshaft");
        this.addStructure(AetherIIStructures.ANCIENT_HENGE, "Ancient Henge");
        this.addStructure(AetherIIStructures.IRRADIATED_BUNKER_REMNANTS, "Irradiated Bunker Remnants");
        this.addStructure(AetherIIStructures.IRRADIATED_SETTLEMENT_REMNANTS, "Irradiated Settlement Remnants");
        this.addStructure(AetherIIStructures.SENTRY_RUINS, "Sentry Ruins");
        this.addStructure(AetherIIStructures.INFECTED_GUARDIAN_TREE, "Infected Guardian Tree");
    }

    private void addAttributes() {
        // Attributes
        this.addAttribute(AetherIIAttributes.SLASH_DAMAGE.get(), "§9Slash§r Damage");
        this.addAttribute(AetherIIAttributes.IMPACT_DAMAGE.get(), "§eImpact§r Damage");
        this.addAttribute(AetherIIAttributes.PIERCE_DAMAGE.get(), "§cPierce§r Damage");
        this.addAttribute(AetherIIAttributes.SLASH_RANGED_DAMAGE.get(), "Ranged §9Slash§r Damage");
        this.addAttribute(AetherIIAttributes.IMPACT_RANGED_DAMAGE.get(), "Ranged §eImpact§r Damage");
        this.addAttribute(AetherIIAttributes.PIERCE_RANGED_DAMAGE.get(), "Ranged §cPierce§r Damage");
        this.addAttribute(AetherIIAttributes.SLASH_RESISTANCE.get(), "§9Slash§r Resistance");
        this.addAttribute(AetherIIAttributes.IMPACT_RESISTANCE.get(), "§eImpact§r Resistance");
        this.addAttribute(AetherIIAttributes.PIERCE_RESISTANCE.get(), "§cPierce§r Resistance");
        this.addAttribute(AetherIIAttributes.SWEEP_RANGE.get(), "Sweep Range");
        this.addAttribute(AetherIIAttributes.SWEEP_KNOCKBACK.get(), "Sweep Knockback");
        this.addAttribute(AetherIIAttributes.SWEEP_DAMAGE.get(), "Sweep Damage");
        this.addAttribute(AetherIIAttributes.SHOCK_RANGE.get(), "Shock Range");
        this.addAttribute(AetherIIAttributes.SHOCK_KNOCKBACK.get(), "Shock Knockback");
        this.addAttribute(AetherIIAttributes.SHOCK_DAMAGE.get(), "Shock Damage");
        this.addAttribute(AetherIIAttributes.STAB_RADIUS.get(), "Stab Radius");
        this.addAttribute(AetherIIAttributes.STAB_DISTANCE.get(), "Stab Distance");
        this.addAttribute(AetherIIAttributes.STAB_KNOCKBACK.get(), "Stab Knockback");
        this.addAttribute(AetherIIAttributes.STAB_DAMAGE.get(), "Stab Damage");
        this.addAttribute(AetherIIAttributes.MAXIMUM_ENDURANCE.get(), "Maximum Endurance");
        this.addAttribute(AetherIIAttributes.ENDURANCE_RECOVERY.get(), "Endurance Recovery");
        this.addAttribute(AetherIIAttributes.BLOCKING_STRENGTH.get(), "Blocking Strength");
        this.addAttribute(AetherIIAttributes.WOUND_EFFECT_RESISTANCE.get(), "%s Buildup Resistance");
        this.addAttribute(AetherIIAttributes.STUN_EFFECT_RESISTANCE.get(), "%s Buildup Resistance");
        this.addAttribute(AetherIIAttributes.FRACTURE_EFFECT_RESISTANCE.get(), "%s Buildup Resistance");
        this.addAttribute(AetherIIAttributes.AMBROSIUM_POISONING_EFFECT_RESISTANCE.get(), "%s Buildup Resistance");
        this.addAttribute(AetherIIAttributes.TOXIN_EFFECT_RESISTANCE.get(), "%s Buildup Resistance");
        this.addAttribute(AetherIIAttributes.VENOM_EFFECT_RESISTANCE.get(), "%s Buildup Resistance");
        this.addAttribute(AetherIIAttributes.CHARGED_EFFECT_RESISTANCE.get(), "%s Buildup Resistance");
        this.addAttribute(AetherIIAttributes.WEBBED_EFFECT_RESISTANCE.get(), "%s Buildup Resistance");
        this.addAttribute(AetherIIAttributes.IMMOLATION_EFFECT_RESISTANCE.get(), "%s Buildup Resistance");
        this.addAttribute(AetherIIAttributes.FROSTBITE_EFFECT_RESISTANCE.get(), "%s Buildup Resistance");
        this.addAttribute(AetherIIAttributes.FUNGAL_ROT_EFFECT_RESISTANCE.get(), "%s Buildup Resistance");
        this.addAttribute(AetherIIAttributes.CRYSTALLIZED_EFFECT_RESISTANCE.get(), "%s Buildup Resistance");
        this.addAttribute(AetherIIAttributes.SATURATION_BOOST.get(), "Food Saturation");
    }

    private void addEffects() {
        // Beneficial
        this.addEffect(AetherIIMobEffects.SATURATION_BOOST, "Saturation Boost");

        // Harmful
        this.addEffect(AetherIIMobEffects.VULNERABILITY, "Vulnerability");
        this.addEffect(AetherIIMobEffects.WOUND, "Wound");
        this.addEffect(AetherIIMobEffects.STUN, "Stun");
        this.addEffect(AetherIIMobEffects.FRACTURE, "Fracture");
        this.addEffect(AetherIIMobEffects.AMBROSIUM_POISONING, "Ambrosium Poisoning");
        this.addEffect(AetherIIMobEffects.TOXIN, "Toxin");
        this.addEffect(AetherIIMobEffects.VENOM, "Venom");
        this.addEffect(AetherIIMobEffects.CHARGED, "Charged");
        this.addEffect(AetherIIMobEffects.WEBBED, "Webbed");
        this.addEffect(AetherIIMobEffects.IMMOLATION, "Immolation");
        this.addEffect(AetherIIMobEffects.FROSTBITE, "Frostbite");
        this.addEffect(AetherIIMobEffects.FUNGAL_ROT, "Fungal Rot");
        this.addEffect(AetherIIMobEffects.CRYSTALLIZED, "Crystallized");

        this.addEffect(AetherIIMobEffects.NATURAL_CAMOUFLAGE, "Natural Camouflage");
        this.addEffect(AetherIIMobEffects.ELECTRIC_SHOCK, "Electric Shock");
        this.addEffect(AetherIIMobEffects.CARRION_TRAP, "Carrion Trap");
        this.addEffect(AetherIIMobEffects.HEALING_OVERFLOW, "Healing Overflow");
        this.addEffect(AetherIIMobEffects.GRAVITATIONAL_PULL, "Gravitational Pull");
    }

    private void addCreativeTabs() {
        // Creative Tabs
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_BUILDING_BLOCKS.get(), "Aether II Building Blocks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_COLORED_BLOCKS.get(), "Aether II Colored Blocks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_NATURAL_BLOCKS.get(), "Aether II Natural Blocks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_FUNCTIONAL_BLOCKS.get(), "Aether II Functional Blocks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_DUNGEON_BLOCKS.get(), "Aether II Dungeon Blocks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_TOOLS_AND_UTILITIES.get(), "Aether II Tools & Utilities");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_COMBAT_AND_EQUIPMENT.get(), "Aether II Combat & Equipment");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_CONSUMABLES.get(), "Aether II Consumables");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_INGREDIENTS.get(), "Aether II Ingredients");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_SPAWN_EGGS.get(), "Aether II Spawn Eggs");
    }

    private void addContainerTypes() {
        // Containers
        this.addContainerType(AetherIIMenuTypes.HOLYSTONE_FURNACE, "Holystone Furnace");
        this.addContainerType(AetherIIMenuTypes.HOLYSTONE_SMOKER, "Holystone Smoker");
        this.addContainerType(AetherIIMenuTypes.ARTISANS_BENCH, "Artisan's Bench");
        this.addContainerType(AetherIIMenuTypes.AMBER_HOURGLASS, "Amber Hourglass");
        this.addContainerType(AetherIIMenuTypes.ALTAR, "Altar");
        this.addContainerType(AetherIIMenuTypes.ARKENIUM_FORGE, "Arkenium Forge");
        this.addContainerType(AetherIIMenuTypes.ALKAHEST_PURIFIER, "Alkahest Purifier");
        this.addGeneric("container.animal_stash", "Animal Stash");
        this.addGeneric("container.sentry_crate", "Sentry Crate");
        this.addGeneric("container.abandoned_bag", "Abandoned Bag");
        this.addGeneric("container.fungal_cache", "Fungal Cache");
        this.addGeneric("container.sage_chest", "Sage Chest");
    }

    private void addGuiText() {
        // GUIs
        this.addGuiText("arkenium_forge.forge_button.tooltip", "Forge Item");
        this.addGuiText("arkenium_forge.charm_slot.tooltip", "Charm Slot");
        this.addGuiText("arkenium_forge.tooltip.durability", "+%s Max Durability");
        this.addGuiText("arkenium_forge.tooltip.charm", "%1$s %2$s Charm Slot");
        this.addGuiText("arkenium_forge.tooltip.charms", "%1$s %2$s Charm Slots");
        this.addGuiText("arkenium_forge.tooltip.tier", "Upgrade Item Tier");
        this.addGuiText("recipebook.toggleRecipes.restorable", "Showing Restorable");
        this.addGuiText("recipebook.toggleRecipes.enchantable", "Showing Enchantable");
        this.addGuiText("recipebook.toggleRecipes.purifiable", "Showing Purifiable");
        this.addGuiText("deathScreen.outpost_respawn", "Respawn at Outpost");
        this.addGuiText("guidebook.button.open", "Guidebook");
        this.addGuiText("guidebook.button.close", "Inventory");
        this.addGuiText("guidebook.description.button.open", "Read More");
        this.addGuiText("guidebook.description.button.close", "Go Back");
        this.addGuiText("guidebook.equipment.title", "Equipment");
        this.addGuiText("guidebook.equipment.pouch.tooltip.title", "Pouch");
        this.addGuiText("guidebook.equipment.pouch.tooltip.description", "%s Glint");
        this.addGuiText("guidebook.status.title", "Status");
        this.addGuiText("guidebook.status.mount.title", "Mount");
        this.addGuiText("guidebook.discovery.title", "Discovery");
        this.addGuiText("guidebook.discovery.bestiary.title", "Bestiary");
        this.addGuiText("guidebook.discovery.effects.title", "Effects");
        this.addGuiText("guidebook.discovery.exploration.title", "Exploration");
        this.addGuiText("guidebook.journal.title", "Journal");
        this.addGuiText("guidebook.rewards.title", "Rewards");
        this.addGuiText("guidebook.discovery.entry.unknown", "???");
        this.addGuiText("guidebook.discovery.bestiary.stat.health", "%s Health");
        this.addGuiText("guidebook.discovery.bestiary.stat.damage_weakness", "%1$s Damage from %2$s Attacks");
        this.addGuiText("guidebook.discovery.bestiary.stat.damage_resistance", "%1$s Damage from %2$s Attacks");
        this.addGuiText("guidebook.discovery.bestiary.stat.damage_none", "Standard Damage from %s Attacks");
        this.addGuiText("guidebook.discovery.bestiary.info.eats", "Eats:");
        this.addGuiText("guidebook.discovery.bestiary.info.drops", "Drops:");
        this.addGuiText("toast.guidebook.bestiary", "New Bestiary Entry!");
        this.addGuiText("toast.guidebook.effects", "New Effects Entry!");
        this.addGuiText("toast.guidebook.exploration", "New Exploration Entry!");
        this.addGuiText("toast.guidebook.description", "Check your Guidebook");
        this.addGuiText("plant.message.attack.invalid", "Hmm. Perhaps I should cut it with a Trowel?");
        this.addGuiText("slider.message.attack.invalid", "Hmm. Perhaps I need to attack it with a Pickaxe?");
        this.addGuiText("slider.title", "Slider %s");

        // Title Screen
        this.add("aether_ii.menu_title.the_aether_ii", "The Aether II");

        //Recipe Viewers
        this.addGuiText("jei.alkahest", "Alkahest Corrosion");
        this.addGuiText("jei.ambrosium", "Ambrosium Enchanting");
        this.addGuiText("jei.icestone", "Icestone Freezable");
        this.addGuiText("jei.irradiated_dust", "Dust Irradiation");
        this.addGuiText("jei.swet_gel", "Swet Gel Conversion");
        this.addGuiText("jei.alkahest_purifier", "Alkahest Purifier");
        this.addGuiText("jei.altar", "Altar Enchanting");
        this.addGuiText("jei.amber_hourglass", "Hourglass Restoring");
    }

    private void addAdvancements() {
        this.addAdvancement("the_holy_isles", "The Aether II");
        this.addAdvancement("enter_holy_isles", "Hostile Paradise");
        this.addAdvancement("aerbunny", "Marketable Plushie");
        this.addAdvancement("aerbunny_bell", "I Choose You!");
        this.addAdvancement("bedroll", "Under the Stars");
        this.addAdvancement("blue_aercloud", "To Infinity and Beyond!");
        this.addAdvancement("cloud_skiff", "Cloud Sailor");
        this.addAdvancement("aercloud_glider", "Amateur Flying");
        this.addAdvancement("shifting_glass", "Move Like the Wind");
        this.addAdvancement("obtain_egg", "Don't Count Your Moas...");
        this.addAdvancement("obtain_petal", "Baby Food");
        this.addAdvancement("moa_feed", "Healthy Diet");
        this.addAdvancement("skyroot_lizard", "Get Stickbugged!");
        this.addAdvancement("incubate_moa", "... Until They Hatch!");
        this.addAdvancement("explore_aether", "The World Above");
        this.addAdvancement("icestone", "Cold as Ice");
        this.addAdvancement("antitoxin", "The Cure");
        this.addAdvancement("engraved_discs", "The Full Album");
        this.addAdvancement("outpost_campfire", "Community Hotspot");
        this.addAdvancement("glint", "Who Wants to Be a Glintillionaire?");
        this.addAdvancement("bestiary", "The Beast");
        this.addAdvancement("trowel", "Highfields Valley");
        this.addAdvancement("enchanted_aether_grass", "Golden Thumb");
        this.addAdvancement("plant_cutting", "Weed Whacking");
        this.addAdvancement("golden_wyndberry", "Golden Delight");
        this.addAdvancement("ambrosium", "Enlightenment");
        this.addAdvancement("golden_amber", "Wisdom of the Ancients");
        this.addAdvancement("amber_hourglass", "What's Old is New Again");
        this.addAdvancement("zanite", "Exotic Hardware");
        this.addAdvancement("craft_altar", "Do You Believe in Magic?");
        this.addAdvancement("gravitite_plate", "Pink is the New Blue");
        this.addAdvancement("gravitite_armor", "Defying Gravity");
        this.addAdvancement("arkenium_plate", "The Steel of Gods");
        this.addAdvancement("alkahest_canister", "Handle with Care");
        this.addAdvancement("craft_alkahest_purifier", "Deep Clean");
        this.addAdvancement("irradiated_item", "Let's Go Gambling");
        this.addAdvancement("dart_shooter", "Pick Your Poison");
        this.addAdvancement("corrobonite_crystal", "Green Energy");
        this.addAdvancement("craft_arkenium_forge", "Upgrades, People, Upgrades!");
        this.addAdvancement("charm", "Charmed, I'm Sure");
        this.addAdvancement("slider", "Like a Bossaru!");
        this.addAdvancement("demolition_hammer_loot", "Controlled Demolition");
        this.addAdvancement("kill_golem_with_demolition_hammer", "Impactful Revenge");
        this.addAdvancement("neptune_armor_loot", "Ocean Man");
        this.addAdvancement("sentry_boots_fall", "Vertigo");

        this.addAdvancementDesc("the_holy_isles", "It's not dead!");
        this.addAdvancementDesc("enter_holy_isles", "Enter the Aether");
        this.addAdvancementDesc("aerbunny", "Interact with a tamed Aerbunny to put it on your head");
        this.addAdvancementDesc("aerbunny_bell", "Store an Aerbunny inside an Aerbunny Bell");
        this.addAdvancementDesc("bedroll", "Sleep in a Cloudwool Bedroll to pass the night without setting your spawn point");
        this.addAdvancementDesc("blue_aercloud", "Bounce on a Blue Aercloud");
        this.addAdvancementDesc("cloud_skiff", "Ride a Cloud Skiff for better movement on the Cloud Sea");
        this.addAdvancementDesc("aercloud_glider", "Use an Aercloud Glider for better mid-air movement");
        this.addAdvancementDesc("shifting_glass", "Perform a dash using the help of the Shifting Glass item");
        this.addAdvancementDesc("obtain_egg", "Obtain a Moa Egg from a Moa Nest");
        this.addAdvancementDesc("obtain_petal", "Harvest an Aechor Petal from an Aechor Plant");
        this.addAdvancementDesc("moa_feed", "Heal a Moa with Moa Feed");
        this.addAdvancementDesc("skyroot_lizard", "Skewer a Skyroot Lizard on a Skyroot Stick");
        this.addAdvancementDesc("incubate_moa", "Incubate a Moa Egg by placing it on top of Woven Skyroot Sticks");
        this.addAdvancementDesc("explore_aether", "Explore all Aether biomes");
        this.addAdvancementDesc("icestone", "Obtain Icestone");
        this.addAdvancementDesc("antitoxin", "Consume an Antitoxin or Antivenom Vial to reduce the buildup of poison effects");
        this.addAdvancementDesc("engraved_discs", "Collect all Aether engraved discs");
        this.addAdvancementDesc("outpost_campfire", "Set a secondary respawn point at an Outpost Campfire");
        this.addAdvancementDesc("glint", "Earn at least 1000 Glint Coins");
        this.addAdvancementDesc("bestiary", "Fill out the entire bestiary");
        this.addAdvancementDesc("trowel", "Harvest drops from a wild plant using any Trowel");
        this.addAdvancementDesc("enchanted_aether_grass", "Enchant an Aether Grass Block with an Ambrosium Shard to speed up plant growth above it");
        this.addAdvancementDesc("plant_cutting", "Harvest a cutting from an Aechor Plant or Carrion Sprout");
        this.addAdvancementDesc("golden_wyndberry", "Feed a Carrion Sprout Golden Amber to obtain a Golden Wyndberry");
        this.addAdvancementDesc("ambrosium", "Obtain an Ambrosium Shard");
        this.addAdvancementDesc("golden_amber", "Harvest Golden Amber from an Amberoot tree using at minimum a Holystone tier axe");
        this.addAdvancementDesc("amber_hourglass", "Craft an Amber Hourglass");
        this.addAdvancementDesc("zanite", "Restore Fossilized Zanite into a Zanite Gemstone using the Amber Hourglass");
        this.addAdvancementDesc("craft_altar", "Craft an Altar");
        this.addAdvancementDesc("gravitite_plate", "Enchant Inert Gravitite into a Gravitite Plate using the Altar");
        this.addAdvancementDesc("gravitite_armor", "Wear 3 pieces of Gravitite Armor to activate its set ability");
        this.addAdvancementDesc("arkenium_plate", "Enchant Inert Arkenium into an Arkenium Plate using the Altar");
        this.addAdvancementDesc("alkahest_canister", "Collect Alkahest using an Arkenium Canister");
        this.addAdvancementDesc("craft_alkahest_purifier", "Craft an Alkahest Purifier");
        this.addAdvancementDesc("irradiated_item", "Restore an Irradiated Item in an Alkahest Purifier and see what you get");
        this.addAdvancementDesc("dart_shooter", "Use a Dart Shooter's full ammo to inflict an effect on an enemy");
        this.addAdvancementDesc("corrobonite_crystal", "Acquire Corrobonite by mining it with a Gravitite Pickaxe");
        this.addAdvancementDesc("craft_arkenium_forge", "Craft an Arkenium Forge");
        this.addAdvancementDesc("charm", "Apply a Charm to a piece of equipment");
        this.addAdvancementDesc("slider", "Defeat the Slider");
        this.addAdvancementDesc("demolition_hammer_loot", "Obtain the Hammer of Demolition from the Sentry Ruins");
        this.addAdvancementDesc("kill_golem_with_demolition_hammer", "Kill a Sentry Golem with a Demolition Projectile");
        this.addAdvancementDesc("neptune_armor_loot", "Wear 3 pieces of Neptune Armor to activate its set ability");
        this.addAdvancementDesc("sentry_boots_fall", "Survive a critical fall with the help of Sentry Boots");
    }

    private void addBestiaryEntries() {
        final String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi nec scelerisque elit. Etiam commodo id ligula non tempus. In volutpat tempus maximus. Phasellus mattis nisi non risus facilisis semper. Pellentesque in tincidunt lorem. Maecenas malesuada augue eget purus placerat, at placerat lorem venenatis. Vestibulum eget massa in est luctus aliquam et ut quam. Ut tincidunt ipsum vel dui congue, quis consectetur elit porta. Maecenas et orci consequat, imperdiet ante eu, commodo nunc. Duis iaculis nisl in erat placerat tincidunt. Maecenas ipsum libero, ultricies eget tempor quis, eleifend eu ipsum. In lorem est, euismod at sem eu, varius convallis mauris. Aenean at erat at nisi iaculis fermentum vel non purus.\n" +
                "\n" +
                "Proin congue ipsum a ligula efficitur, eget pulvinar lacus elementum. Praesent eu dignissim ante. Nullam ac aliquet leo. Cras luctus odio eget malesuada euismod. Nulla metus magna, hendrerit et augue nec, fermentum hendrerit velit. Proin non pellentesque lorem. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.\n" +
                "\n" +
                "Sed fermentum tortor auctor ex tristique malesuada. Etiam a pulvinar arcu, id pharetra metus. Mauris volutpat fermentum eros. Pellentesque non diam nec diam ultricies gravida vel at lectus. Nulla non libero eu eros ornare tempor. Phasellus ac suscipit risus. Aenean cursus, purus consectetur auctor ullamcorper, erat enim aliquam lacus, non accumsan metus turpis quis est.\n" +
                "\n" +
                "Nunc odio lectus, semper vel eros bibendum, mollis ornare turpis. Maecenas in nibh ut est tincidunt sollicitudin ut vel nunc. Vestibulum erat velit, sagittis et ante id, convallis consequat turpis. Cras at risus vitae ex placerat ultricies. Maecenas id augue eleifend metus luctus ornare. Proin metus leo, imperdiet in cursus viverra, aliquet vel odio. Mauris non porta justo, eget pharetra nulla. Aliquam erat volutpat. Duis rhoncus et ante id egestas. Suspendisse sodales porttitor metus quis molestie. Phasellus ante turpis, finibus sit amet metus eu, condimentum ullamcorper lorem. Nullam sollicitudin euismod enim, ac lobortis neque finibus sed. Proin iaculis dui sed odio cursus, non porta neque vulputate.\n" +
                "\n" +
                "Pellentesque egestas turpis non orci feugiat viverra. Morbi faucibus sollicitudin erat. Quisque commodo rhoncus neque, at ullamcorper purus ornare nec. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vivamus vitae arcu dictum tortor mattis placerat blandit sit amet erat. Donec commodo neque sed elit eleifend condimentum. Mauris vitae rutrum augue.";

        final String taegore = "Taegore";
        final String burrukai = "Burrukai";
        final String kirrid = "Kirrid";
        final String highfields = "Highfields";
        final String magnetic = "Magnetic";
        final String arctic = "Arctic";

        this.addGeneric("highfields", highfields);
        this.addGeneric("magnetic", magnetic);
        this.addGeneric("arctic", arctic);

        this.addBestiaryEntry(AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(), taegore, highfields, """
“Peaceful creatures native to The Aether's Holy Isles.

Highfields taegore are notable for their golden armor plating and soft blue fur coat. Taegore will flee from potential threats, such as a person sprinting near them. Taegore will sometimes dig up buried vegetables.

They can be hunted for beast hide and meat.”""");
        this.addBestiaryEntry(AetherIIEntityTypes.MAGNETIC_TAEGORE.get(), taegore, magnetic, """
“Peaceful creatures native to The Aether's Holy Isles.

Magnetic taegore feature bronze colored plating and green fur. Taegore will flee from potential threats, such as a person sprinting near them. Taegore will sometimes dig up buried vegetables.

They can be hunted for beast hide and meat.”""");
        this.addBestiaryEntry(AetherIIEntityTypes.ARCTIC_TAEGORE.get(), taegore, arctic, """
“Peaceful creatures native to The Aether's Holy Isles.

Arctic taegore plating takes on an icy silver hue alongside their warm thick blue fur. Taegore will flee from potential threats, such as a person sprinting near them. Taegore will sometimes dig up buried vegetables.

They can be hunted for beast hide and meat.”""");
        this.addBestiaryEntry(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(), burrukai, highfields, """
“Protective creatures found roaming The Aether's Holy Isles.

Highfields burrukai stand out from their natural environment with dark blue fur and dark armor plates. Burrukai are largely peaceful creatures, but they will respond harshly to aggression, using their heavy weight and sharp horns to charge at potential threats.

Their armor plating is incredibly durable, making it a valuable material for crafting armor and shields.”""");
        this.addBestiaryEntry(AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(), burrukai, magnetic, """
“Protective creatures found roaming The Aether's Holy Isles.

Magnetic burrukai have striped fur and unique horn formations that help dispel static electricity. Burrukai are largely peaceful creatures, but they will respond harshly to aggression, using their heavy weight and sharp horns to charge at potential threats.

Their armor plating is incredibly durable, making it a valuable material for crafting armor and shields.”""");
        this.addBestiaryEntry(AetherIIEntityTypes.ARCTIC_BURRUKAI.get(), burrukai, arctic, """
“Protective creatures found roaming The Aether's Holy Isles.

Arctic burrukai have a thicker undercoat than other burrukai and their armor is an icy blue that helps hide their size and numbers in snowstorms. Burrukai are largely peaceful creatures, but they will respond harshly to aggression, using their heavy weight and sharp horns to charge at potential threats.

Their armor plating is incredibly durable, making it a valuable material for crafting armor and shields.”""");
        this.addBestiaryEntry(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), kirrid, highfields, """
“Peaceful creatures that travel in small flocks across The Aether's Holy Isles.

Highfields kirrid have a singular flat plate on their heads, the gold coloration of their plating and leg blades is thought to come from a high concentration of ambrosium in their diet. Kirrid will participate in friendly competition with their flock mates, ramming their head plates together until one breaks, this helps keep the fast-growing head plating trimmed down and aerodynamic.

Kirrid have a thick coat of cloudwool that can grow in a variety of natural colors.”""");
        this.addBestiaryEntry(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), kirrid, magnetic, """
“Peaceful creatures that travel in small flocks across The Aether's Holy Isles.

In the magnetic biomes, kirrid have adapted a unique plate shaping that helps draw small bolts of electricity to strike them, which protects their bodies from electrical hazards. Kirrid will participate in friendly competition with their flock mates, ramming their head plates together until one breaks, this helps keep the fast-growing head plating trimmed down and aerodynamic.

Kirrid have a thick coat of cloudwool that can grow in a variety of natural colors.”""");
        this.addBestiaryEntry(AetherIIEntityTypes.ARCTIC_KIRRID.get(), kirrid, arctic, """
“Peaceful creatures that travel in small flocks across The Aether's Holy Isles.

The harsh cold of the arctic isles of the Holy Isles have caused kirrid to adapt with much larger fur cover, to account for the extra weight their head plates grow much smaller, resembling symmetrical horns. Kirrid will participate in friendly competition with their flock mates, ramming their head plates together until one breaks, this helps keep the fast-growing head plating trimmed down and aerodynamic.

Kirrid have a thick coat of cloudwool that can grow in a variety of natural colors.”""");

        this.addBestiaryDescription(AetherIIEntityTypes.FLYING_COW.get(), """
“Peaceful creatures found in rare herds in The Aether's Holy Isles.

Thought to be a domesticated cousin to the burrukai from early valkyrie civilization, flying cows lack the aggressive behavioral traits of their larger cousins. Their peaceful disposition makes them ideal for farming their high quality meat, though the reduction in size and density of their plating means they cannot be used as a substitute for burrukai plates.

Flying cows represent a relatively low part of the wildlife population of the Holy Isles, making them a rare find in modern times.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.SHEEPUFF.get(), """
“Peaceful creatures found in rare flocks in The Aether's Holy Isles.

They are believed to be a domesticated cousin to the kirrid from early valkyrie civilization. The armor plating that traditionally forms a kirrid's leg blades grow much smaller on sheepuff, revealing more regular animal legs. This means sheepuff cannot bounce around the way a kirrid normally would, but instead they have developed a cloudwool coat rich in aercloud, similar to an aerbunny. If their coat of cloudwool sucks up enough compressed air, it can inflate and send the sheepuff high up into the sky, allowing them to traverse long distances or high cliffs.

Sheepuff represent a relatively low part of the wildlife population of the Holy Isles, making them a rare find in modern times.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.PHYG.get(), """
“Peaceful creatures found in rare sounders in The Aether's Holy Isles.

Valkyrie records show phygs being used as farm animals in the distant past, likely as a domesticated cousin to the taegore. Phygs lack the fear response taegore show towards potential predators, making them much easier to capture and hunt. Unfortunately, their soft skin and smaller bodies mean they aren't particularly valuable for harvesting beast hide, but they are known to be a good source of high quality meat.

Phygs represent a relatively low part of the wildlife population of the Holy Isles, making them a rare find in modern times.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.AERBUNNY.get(), """
“Friendly creatures that roam The Aether's Holy Isles.

They are deeply social creatures that enjoy the company of other species, including the people of The Aether and human visitors. an aerbunny's coat has a condensed layer of aercloud nestled beneath their fur, which allows them to float gently through the air.

Feeding an aerbunny its favorite fruit allows it to be tamed.  This allows them to be held, granting their natural gliding abilities to someone travelling The Aether.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.PRISMALLARD.get(), """
“Peaceful creatures native to The Aether's Holy Isles.

Prismallards enjoy hunting for small fish and insects while swimming on the surface of lakes and ponds around The Aether's highfields islands. While mostly harmless, if a prismallard feels threatened they will display their elaborate tail feathers in hopes of making themselves appear more dangerous to potential predators.

They frequently lay edible eggs and can be hunted for their meat and feathers.”""");

        this.addBestiaryDescription(AetherIIEntityTypes.MOA.get(), """
“Intelligent and territorial creatures that cannot be tamed from the wild, but can be raised from an egg to be tamed and ridden as a mount.

Wild moas will attempt to protect their nest from any potential intruders. They are versatile creatures that will build nests in any suitable location across multiple different climates and conditions, using woven sticks to create an insulated area perfect for raising their eggs.

Wild moas are smart but generally highly distrusting, so to tame and ride a moa you will need to steal a wild egg to hatch and raise yourself. Baby moas require a lot of nutrients to grow into healthy adults, but are typically quite picky eaters.

The petals from the toxic aechor plants are a favorite of baby moas, having adapted an immunity to their toxins. Once a baby moa has grown into an adult, it can be fitted with a moa saddle and ridden as a flying mount.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.SKYROOT_LIZARD.get(), """
“Peaceful creatures that nest across The Aether's Holy Isles.

They are small creatures that nest in the leaves of The Aether's trees, adapting effective camouflage that makes them incredibly hard to spot in dense canopies. As a tree's leaves decay, a nesting lizard may drop to the ground, causing it to panic and run away.

Skyroot lizards can be skewered on a skyroot stick and cooked into edible food, or fed to a moa, their natural predator.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.GLITTERWING.get(), """
“The Aether is home to a wide variety of small insects, one category of insect are collectively known as glitterwings. These are flying insects with thin, flat wings displaying a variety of colors and patterns.

Some adventurers enjoy collecting and documenting glitterwing patterns as a hobby.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.SHROUDWING.get(), """
“The Aether is home to a wide variety of small insects, one category of insect are collectively known as shroudwings. These are a group of species with hard wing casings and often sport small horns on their heads for competition and mating rituals.

Some adventurers enjoy collecting and documenting shroudwing shells as a hobby.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.AECHOR_PLANT.get(), """
“Hazardous plants that grow across The Aether's Holy Isles.

Aechor plants suck up a vast amount of nutrients and minerals from their surrounding territory, making them a precious food source for many animals in the Holy Isles. To defend themselves from potential predators, they developed toxic barbs that can be projected outward at targets they sense near their roots. While individually these barbs aren't very harmful, their toxins can build up and cause some negative effects if untreated.

The petals of aechor plants are a favorite food of baby moas, offering enough nutrients to help them grow into strong flight-ready adults.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.CARRION_SPROUT.get(), """
“Hazardous plants that grow across The Aether's Holy Isles.

During the day, carrion sprouts curl up and draw energy from the sunlight. However, at night they become carnivorous plants capable of extracting nutrients from both living and dead animals. The carrion sprouts have adapted camouflage that allows them to hide in tall grass and trap wandering creatures within its tight jaws if they step on their leaves.

Wyndberries can be harvested from carrion sprouts and enchanted into a valuable food source.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.ZEPHYR.get(), """
“Hostile pests found flying through The Aether's skies.

Often seen as elemental manifestations of The Aether's strong wind currents, zephyrs are insect-like creatures that are able to manipulate aercloud and airflow. Zephyrs encase themselves in a shell made up of aercloud enriched webbing that grants them the ability to float in the air.

When they sense potential threats, zephyrs will attempt to disable them by shooting at them with bundles of sticky webbing. If the webbing fails to incapacitate a predator, they are also capable of charging up a long concentrated burst of wind that can push threats away, often off the side of an island.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.BLUE_SWET.get(), """
“Aggressive, parasitic creatures that roam in The Aether's shadows.

Swets prefer dark environments, so are typically found during nighttime. They are small fish-like creatures that live in an outer shell of acidic gel, they use this gel for transportation, protection, and hunting. Swets can capture small creatures and plants inside their sticky gel, which allows them to extract nutrients from their prey. They are even known to feed on larger animals, attaching to the skin of larger prey and sapping them of their nutrients before detaching and attempting to escape.

Their gel layer dissolves in water, so if you find one attached to yourself, a quick dive into some nearby water will force the swet to detach.

Swet gel contains a lot of broken down nutrients and minerals that make it an excellent fertilizer, while the sugars stored inside a swet's body are often used as a sweetener in many foods. You can even combine a swet's gel and sugars together to make an almost palatable jelly.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.GOLDEN_SWET.get(), """
“Aggressive, parasitic creatures that roam in The Aether's shadows.

Swets prefer dark environments, so are typically found during nighttime. They are small fish-like creatures that live in an outer shell of acidic gel, they use this gel for transportation, protection, and hunting. Swets can capture small creatures and plants inside their sticky gel, which allows them to extract nutrients from their prey. They are even known to feed on larger animals, attaching to the skin of larger prey and sapping them of their nutrients before detaching and attempting to escape.

Their gel layer dissolves in water, so if you find one attached to yourself, a quick dive into some nearby water will force the swet to detach. Golden swets are far rarer than their blue counterparts.

Their gold coloration is caused by a much stronger concentration of alkahest in their gel layer, which also leads to a far higher concentration of sugars in their bodies. Golden swets can be found more frequently near alkahest ponds, deep underground.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.SKEPHID.get(), """
“Protective insects native to The Aether's cave systems.

Skephids wander through caverns and the undercloud looking for small bugs to hunt, moss deposits, and water sources, all of which they use to help support their secluded hives. If they feel threatened by a larger creature, they will use their spinnerets to shoot webbing at their target.

While generally non-predatory to larger creatures or people, skephids have been known to capture small animals in webbing traps when in desperate need of food sources.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.TEMPEST.get(), """
“Aggressive predators that roam The Aether's night skies and shadows.

Centuries ago the blight, a mutagenic plague, once infected a distant population of zephyrs. This infection twisted the biology of these zephyrs into a new species of aggressive predator, the tempests. In modern times, tempests roam The Aether's skies at night as an apex predator.

Their mutations have allowed tempests to generate bioelectrical projectiles they can shoot at potential prey to stun and incapacitate them.

The blight is inherently weak to sunlight, though, so at sunrise tempests retreat into the shadows.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.COCKATRICE.get(), """
“Aggressive predators that roam The Aether's caves and shadows.

In the distant past, an isolated colony of moas came into contact with the blight, a mutagenic plague. After prolonged exposure to its mutagenic effects, these moas developed highly aggressive features that turned them into a new species, the cockatrices. Cockatrices have largely lost their ability to fly, instead choosing to stalk the caves and dark forests of The Aether for their prey.

Their main adaptation is the development of venomous darts that grow from their ribcages, at a distance cockatrices can fire these darts to harm and slow down distant prey. At close range, they switch to using their sharp claws and beaks to slash and crush their target.

The blight is inherently weak to sunlight, though, so at sunrise cockatrices retreat into the shadows.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.ARKENIUM_TALUTON.get(), """
“Aggressive automatons native to The Aether's caves.

Soul crystals grow in rare pockets all across The Aether's world, and when they reach a certain state of maturity, they pull in material from their surroundings and form a creature from those materials.

When this process occurs deep underground, it will often pull in surrounding rocks and ores and constitute a body suited to those hard rocky minerals and metals. The arkenium taluton is primarily formed of holystone, undershale, and inert arkenium.

They're simple but aggressive creatures, protecting areas that are likely to form new soul crystals from potential hazards. Arkenium talutons use the extreme weight and size of their rocky arms to slam into nearby threats, making them a distinctly dangerous creature for miners and cave explorers.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.GRAVITITE_TALUTON.get(), """
“Aggressive automatons native to The Aether's caves.

Soul crystals grow in rare pockets all across The Aether's world, and when they reach a certain state of maturity, they pull in material from their surroundings and form a creature from those materials.

When this process occurs deep underground, it will often pull in surrounding rocks and ores and constitute a body suited to those hard rocky minerals and metals. The gravitite taluton is primarily formed of holystone, undershale, and inert gravitite.

They are cautious creatures capable of long ranged attacks. Gravitite talutons will use the weak gravitational energies from their inert gravitite to pull rocks and pebbles from the ground to launch at their targets, hoping they will dissuade wandering creatures from disturbing areas likely to form new soul crystals. A strong shield and stable footing are essential for navigating caves populated by gravitite talutons.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.MIMIC.get(), """
“Twisted, hostile monsters found in The Aether's many ruins.

Mimics are shapeshifting worm-like creatures that possess the unique ability to change their form to turn any empty container they find into a protective shell and camouflage. As a result, they are frequently found in old abandoned structures. They lie in wait for unsuspecting travelers to investigate the container, at which point they transform into a monstrous predatory form to hunt their newfound prey.

Mimics take many unique forms, but they almost always need an empty container to use as a shell, so be cautious around any abandoned chests, crates, or barrels you find on your travels.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.DETONATION_SENTRY.get(), """
“Aggressive mechanical relics found in sentry ruins.

An older model of sentry frequently found in ancient underground sentry structures, the detonation sentry is thought to potentially be a prototype for demolition equipment used when creating the ancient vaults.

These small machines will identify targets based on heat and, when within proximity, will begin a countdown to detonate a small explosive device embedded in their core.

Detonation sentries aren't noted to exist in the vaults, leading scholars to believe their design may have been discontinued by the time the vaults were completed.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.SENTRY_GOLEM.get(), """
“Aggressive mechanical relics found in sentry ruins.

An older model of sentry found exclusively in larger sentry structures, the sentry golem is thought to be a prototype for the security sentries found patrolling the ancient vaults.

Equipped with a mechanical hammer of demolition, sentry golems appear to have been programmed to seek out intruders and eliminate them using both ranged and melee attacks.

The hammer of demolition contains a small explosive charge fabricator in its handle, which can then launch explosives at distant intruders. Once the core has been replaced, it can also be used as a traditional hammer weapon.

Sentry golems interestingly feature prominent bird-like designs in their limbs and engravings.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.SLIDER.get(), """
“The boss of the sentry ruins dungeon.

The Slider is a security machine created for guarding the core of the ancient vaults. This prototype appears to be less efficient and durable than those found in the vaults, indicating that the sentry ruins may be a facility that was used to design and test sentry technology before the vaults were built.

The Slider uses giant super cooled gravitite gears inside its hard stone shell to alter the direction of its gravity, allowing it to float or fall in six directions. Its reactor core uses an incredible amount of energy to drive these processes, so large reinforced glass heat vents on its sides both keep it cool and house optical sensors for tracking targets.

The Slider's shell is made of a composite material derived from undershale and ferrosite, making it impervious to traditional weaponry. A strong pickaxe is required to chip away at the outer shell, but once it is compromised, the reactor core will become unstable and shut the Slider down.

As the Slider takes damage, it will send signals to detonation sentry containers around the facility to assist it in battle. While this prototype is significantly weaker than those found in the vaults, it is still a dangerous and unstable machine capable of dealing tremendous damage.”""");
        this.addBestiaryDescription(AetherIIEntityTypes.BLADESHROOM_HUNTER.get(), loremIpsum);
    }

    private void addEffectsDescriptions() {
        this.addEffectsDescription(AetherIIMobEffects.SATURATION_BOOST.get(), "Temporarily increases the time for which consumable items keep you full.");
        this.addEffectsDescription(AetherIIMobEffects.VULNERABILITY.get(), "Decreases Resistance to all Damage Types for the affected mob.");
        this.addEffectsDescription(AetherIIMobEffects.WOUND.get(), """
Inflicts a large amount of Damage instantly.

Use a Bandage to reduce Wound buildup.""");
        this.addEffectsDescription(AetherIIMobEffects.STUN.get(), """
Prevents the use of tools, blocks or items and slows movement.

Use a Bandage to reduce Stun buildup.""");
        this.addEffectsDescription(AetherIIMobEffects.FRACTURE.get(), """
Causes Damage when sprinting and increases Fall Damage.

Use a Bandage to reduce Fracture buildup. A Splint will cure the effect.""");
        this.addEffectsDescription(AetherIIMobEffects.AMBROSIUM_POISONING.get(), "Completely negates all forms of healing.");
        this.addEffectsDescription(AetherIIMobEffects.TOXIN.get(), """
Drains Health over time until minimum remaining Health is reached.

Drink from an Antitoxin Vial to reduce Toxin buildup.""");
        this.addEffectsDescription(AetherIIMobEffects.VENOM.get(), """
Drains both Health and Hunger over time until minimum remaining Health is reached.

Drink from an Antivenom Vial to reduce Venom buildup.""");
        this.addEffectsDescription(AetherIIMobEffects.CHARGED.get(), "When Charged, a Mob or Player will be shocked when coming into close contact with another source of Charge.");
        this.addEffectsDescription(AetherIIMobEffects.WEBBED.get(), """
Greatly impairs movement ability and prevents jumping.

Repeatedly attempting to jump reduces buildup.""");
        this.addEffectsDescription(AetherIIMobEffects.IMMOLATION.get(), "A fast acting burning that causes Damage and can also cause Immolation buildup on nearby entities.");
        this.addEffectsDescription(AetherIIMobEffects.FROSTBITE.get(), "Slows item use, attack and mining speed, as well as slightly slowing movement.");
        this.addEffectsDescription(AetherIIMobEffects.FUNGAL_ROT.get(), """
Deals Damage very slowly over time until death.

Decreases detection range from Fungal type mobs and causes extra interactions within the Infected Guardian Tree.""");
        this.addEffectsDescription(AetherIIMobEffects.CRYSTALLIZED.get(), """
Reduces effectiveness of Armor.

Movement faster than sneaking will alert Crystaline type mobs.""");
    }

    private void addMusic() {
        // Music
        this.addMusic("aether1", "Emile van Krieken - Welcome to Paradise");
        this.addMusic("aether2", "Emile van Krieken - Wings");
        this.addMusic("aether3", "Emile van Krieken - Meadow");
        this.addMusic("aether4", "Emile van Krieken - Moa's Song");
        this.addMusic("aether5", "Emile van Krieken - Clouds");
        this.addMusic("aether6", "Emile van Krieken - Brise");
        this.addMusic("polarity", "sunsette - Polarity");
        this.addMusic("sunbeam", "sunsette - Sunbeam");
        this.addMusic("thin_air", "sunsette - Thin Air");
        this.addMusic("waking_dream", "sunsette - Waking Dream");
        this.addMusic("eulogy_for_a_whale", "sunsette - Eulogy for a Whale");
        this.addMusic("exhale", "sunsette - Exhale");
        this.addMusic("aether_night1", "Emile van Krieken - A Light in the Night");
        this.addMusic("aether_night2", "Emile van Krieken - Crepusculum");
        this.addMusic("aether_sunrise", "Emile van Krieken - Sunrise");
        this.addMusic("aether_sunset", "Emile van Krieken - Sunset");
        this.addMusic("aether_ambience1", "Emile van Krieken - Sentience");
        this.addMusic("boss.slider_fight", "Emile van Krieken - Labyrinth's Vengeance");
        this.addMusic("title.resonance_of_the_gods_redux", "anankalisto - Resonance of the Gods - sunsette remix");

        // Jukebox Songs
        this.addJukeboxSong("ascending_dawn", "Emile van Krieken - Ascending Dawn");
        this.addJukeboxSong("aerwhale", "AetherAudio - Aerwhale");
        this.addJukeboxSong("approaches", "Emile van Krieken - Approaches");
        this.addJukeboxSong("demise", "Moorziey - Demise");
        this.addJukeboxSong("chinchilla", "RENREN - chinchilla");
        this.addJukeboxSong("high", "RENREN - high");
        this.addJukeboxSong("revolutions", "sunsette - revolutions");
    }

    private void addSubtitles() {
        // Blocks
        this.addSubtitle("block", "aether_portal.ambient", "Aether Portal whooshes");
        this.addSubtitle("block", "aether_portal.trigger", "Aether Portal noise intensifies");

        this.addSubtitle("block", "aercloud.blue_aercloud_bounce", "Blue Aercloud bounces");

        this.addSubtitle("block", "moa_egg.crack", "Moa Egg cracks");
        this.addSubtitle("block", "moa_egg.hatch", "Moa Egg hatches");

        this.addSubtitle("block", "bush.rustle", "Bush rustles");

        this.addSubtitle("block", "gel.slide", "Sliding down a gel block");

        this.addSubtitle("block", "hestveil.ignite", "Hestveil ignites");

        this.addSubtitle("block", "holystone_furnace.fire_crackle", "Holystone Furnace crackles");

        this.addSubtitle("block", "holystone_smoker.smoke", "Holystone Smoker smokes");

        this.addSubtitle("block", "arkenium_forge.use", "Arkenium Forge used");

        this.addSubtitle("block", "alkahest_purifier.open", "Alkahest Purifier opens");
        this.addSubtitle("block", "alkahest_purifier.close", "Alkahest Purifier closes");

        this.addSubtitle("block", "ground_trap.trigger", "Trap activates");

        this.addSubtitle("block", "guardian_donation_box.insert", "Guardian Donation Box fills");
        this.addSubtitle("block", "guardian_donation_box.insert_fail", "Guardian Donation Box wobbles");

        this.addSubtitle("block", "water.evaporate", "Water evaporated");

        // Items
        this.addSubtitle("item", "ambrosium_shard.use", "Ambrosium Shard used");
        this.addSubtitle("item", "swet_gel.use", "Swet Gel used");

        this.addSubtitle("item", "scatterglass_vial.fill", "Scatterglass Vial fills");
        this.addSubtitle("item", "scatterglass_vial.empty", "Scatterglass Vial empties");

        this.addSubtitle("item", "arkenium_canister.fill", "Arkenium Canister fills");
        this.addSubtitle("item", "arkenium_canister.empty", "Arkenium Canister empties");

        this.addSubtitle("item", "shifting_glass.use", "Shifting Glass used");

        this.addSubtitle("item", "aerbunny_bell.ring", "Aerbunny Bell rings");

        this.addSubtitle("item", "hammer_of_demolition.shoot", "Projectile fired");

        this.addSubtitle("item", "armor.equip_beast_pelt", "Beast Pelt armor rustles");
        this.addSubtitle("item", "armor.equip_burrukai_plate", "Burrukai Plate armor rustles");
        this.addSubtitle("item", "armor.equip_zanite", "Zanite armor clangs");
        this.addSubtitle("item", "armor.equip_arkenium", "Arkenium armor clanks");
        this.addSubtitle("item", "armor.equip_gravitite", "Gravitite armor clinks");
        this.addSubtitle("item", "armor.equip_sentry", "Sentry armor clanks");
        this.addSubtitle("item", "armor.equip_neptune", "Neptune armor clinks");

        this.addSubtitle("item", "accessory.equip_generic", "Accessory equips");

        // Player
        this.addSubtitle("entity", "player.damage.slash.correct", "Strong slashing attack");
        this.addSubtitle("entity", "player.damage.impact.correct", "Strong impacting attack");
        this.addSubtitle("entity", "player.damage.pierce.correct", "Strong piercing attack");
        this.addSubtitle("entity", "player.damage.slash.incorrect", "Weak slashing attack");
        this.addSubtitle("entity", "player.damage.impact.incorrect", "Weak impacting attack");
        this.addSubtitle("entity", "player.damage.pierce.incorrect", "Weak piercing attack");
        this.addSubtitle("entity", "player.attack.sweep", "Sweeping attack");
        this.addSubtitle("entity", "player.attack.shock", "Shocking attack");
        this.addSubtitle("entity", "player.attack.stab", "Stabbing attack");

        // Entities
        this.addSubtitle("entity", "phyg.ambient", "Phyg oinks");
        this.addSubtitle("entity", "phyg.death", "Phyg dies");
        this.addSubtitle("entity", "phyg.hurt", "Phyg hurts");
        this.addSubtitle("entity", "phyg.saddle", "Saddle equips");
        this.addSubtitle("entity", "phyg.step", "Footsteps");

        this.addSubtitle("entity", "flying_cow.ambient", "Flying Cow moos");
        this.addSubtitle("entity", "flying_cow.death", "Flying Cow dies");
        this.addSubtitle("entity", "flying_cow.hurt", "Flying Cow hurts");
        this.addSubtitle("entity", "flying_cow.saddle", "Saddle equips");
        this.addSubtitle("entity", "flying_cow.milk", "Flying Cow gets milked");
        this.addSubtitle("entity", "flying_cow.step", "Footsteps");

        this.addSubtitle("entity", "sheepuff.ambient", "Sheepuff baahs");
        this.addSubtitle("entity", "sheepuff.death", "Sheepuff dies");
        this.addSubtitle("entity", "sheepuff.hurt", "Sheepuff hurts");
        this.addSubtitle("entity", "sheepuff.step", "Footsteps");

        this.addSubtitle("entity", "aerwhale.ambient", "Aerwhale whistles");
        this.addSubtitle("entity", "aerwhale.hurt", "Aerwhale hurts");
        this.addSubtitle("entity", "aerwhale.death", "Aerwhale cries");

        this.addSubtitle("entity", "aerbunny.ambient", "Aerbunny squeaks");
        this.addSubtitle("entity", "aerbunny.death", "Aerbunny dies");
        this.addSubtitle("entity", "aerbunny.hurt", "Aerbunny hurts");
        this.addSubtitle("entity", "aerbunny.hop", "Aerbunny puffs");
        this.addSubtitle("entity", "aerbunny.lift", "Aerbunny squeals");
        this.addSubtitle("entity", "aerbunny.land", "Aerbunny squeals");

        this.addSubtitle("entity", "taegore.ambient", "Taegore snorts");
        this.addSubtitle("entity", "taegore.death", "Taegore dies");
        this.addSubtitle("entity", "taegore.hurt", "Taegore hurts");
        this.addSubtitle("entity", "taegore.searching", "Taegore searches");
        this.addSubtitle("entity", "taegore.digging", "Taegore digs");
        this.addSubtitle("entity", "taegore.digging_stop", "Taegore stops digging");
        this.addSubtitle("entity", "taegore.drop_seed", "Taegore drops seed");

        this.addSubtitle("entity", "burrukai.ambient", "Burrukai grunts");
        this.addSubtitle("entity", "burrukai.death", "Burrukai dies");
        this.addSubtitle("entity", "burrukai.hurt", "Burrukai hurts");
        this.addSubtitle("entity", "burrukai.ram_impact", "Burrukai rams");

        this.addSubtitle("entity", "kirrid.ambient", "Kirrid bleats");
        this.addSubtitle("entity", "kirrid.death", "Kirrid dies");
        this.addSubtitle("entity", "kirrid.hurt", "Kirrid hurts");
        this.addSubtitle("entity", "kirrid.jump", "Kirrid leaps");
        this.addSubtitle("entity", "kirrid.ram_impact", "Kirrid rams");

        this.addSubtitle("entity", "skyroot_lizard.ambient", "Skyroot Lizard hisses");
        this.addSubtitle("entity", "skyroot_lizard.death", "Skyroot Lizard dies");
        this.addSubtitle("entity", "skyroot_lizard.hurt", "Skyroot Lizard hurts");

        this.addSubtitle("entity", "moa.ambient", "Moa calls");
        this.addSubtitle("entity", "moa.death", "Moa dies");
        this.addSubtitle("entity", "moa.hurt", "Moa hurts");
        this.addSubtitle("entity", "moa.saddle", "Saddle equips");
        this.addSubtitle("entity", "moa.flap", "Moa flaps");
        this.addSubtitle("entity", "moa.egg", "Moa plops");

        this.addSubtitle("entity", "prismallard.ambient", "Prismallard chirps");
        this.addSubtitle("entity", "prismallard.death", "Prismallard dies");
        this.addSubtitle("entity", "prismallard.hurt", "Prismallard hurts");
        this.addSubtitle("entity", "prismallard.flap", "Prismallard flaps");
        this.addSubtitle("entity", "prismallard.egg", "Prismallard plops");

        this.addSubtitle("entity", "aechor_plant.shoot", "Aechor Plant shoots");
        this.addSubtitle("entity", "aechor_plant.hurt", "Aechor Plant hurts");
        this.addSubtitle("entity", "aechor_plant.death", "Aechor Plant dies");

        this.addSubtitle("entity", "carrion_sprout.trap", "Carrion Sprout closes");
        this.addSubtitle("entity", "carrion_sprout.hurt", "Carrion Sprout hurts");
        this.addSubtitle("entity", "carrion_sprout.death", "Carrion Sprout dies");

        this.addSubtitle("entity", "zephyr.shoot", "Zephyr spits");
        this.addSubtitle("entity", "zephyr.ambient", "Zephyr blows");
        this.addSubtitle("entity", "zephyr.death", "Zephyr dies");
        this.addSubtitle("entity", "zephyr.hurt", "Zephyr hurts");

        this.addSubtitle("entity", "tempest.shoot", "Tempest spits");
        this.addSubtitle("entity", "tempest.ambient", "Tempest sparks");
        this.addSubtitle("entity", "tempest.death", "Tempest dies");
        this.addSubtitle("entity", "tempest.hurt", "Tempest hurts");

        this.addSubtitle("entity", "cockatrice.shoot", "Cockatrice shoots");
        this.addSubtitle("entity", "cockatrice.ambient", "Cockatrice calls");
        this.addSubtitle("entity", "cockatrice.death", "Cockatrice dies");
        this.addSubtitle("entity", "cockatrice.hurt", "Cockatrice hurts");

        this.addSubtitle("entity", "swet.attack", "Swet attacks");
        this.addSubtitle("entity", "swet.death", "Swet dies");
        this.addSubtitle("entity", "swet.hurt", "Swet hurts");
        this.addSubtitle("entity", "swet.jump", "Swet squishes");
        this.addSubtitle("entity", "swet.squish", "Swet squishes");
        this.addSubtitle("entity", "swet.leech", "Swet leeches");

        this.addSubtitle("entity", "skephid.shoot", "Skephid shoots");
        this.addSubtitle("entity", "skephid.ambient", "Skephid chitters");
        this.addSubtitle("entity", "skephid.death", "Skephid dies");
        this.addSubtitle("entity", "skephid.hurt", "Skephid hurts");

        this.addSubtitle("entity", "arkenium_taluton.attack", "Arkenium Taluton attacks");
        this.addSubtitle("entity", "arkenium_taluton.ambient", "Arkenium Taluton drones");
        this.addSubtitle("entity", "arkenium_taluton.death", "Arkenium Taluton dies");
        this.addSubtitle("entity", "arkenium_taluton.hurt", "Arkenium Taluton hurts");

        this.addSubtitle("entity", "gravitite_taluton.shoot", "Gravitite Taluton shoots");
        this.addSubtitle("entity", "gravitite_taluton.ambient", "Gravitite Taluton drones");
        this.addSubtitle("entity", "gravitite_taluton.death", "Gravitite Taluton dies");
        this.addSubtitle("entity", "gravitite_taluton.hurt", "Gravitite Taluton hurts");

        this.addSubtitle("entity", "mimic.attack", "Mimic attacks");
        this.addSubtitle("entity", "mimic.death", "Mimic dies");
        this.addSubtitle("entity", "mimic.hurt", "Mimic hurts");
        this.addSubtitle("entity", "mimic.kill", "Mimic burps");

        this.addSubtitle("entity", "detonation_sentry.death", "Detonation Sentry dies");
        this.addSubtitle("entity", "detonation_sentry.hurt", "Detonation Sentry hurts");
        this.addSubtitle("entity", "detonation_sentry.jump", "Detonation Sentry squishes");
        this.addSubtitle("entity", "detonation_sentry.squish", "Detonation Sentry squishes");
        this.addSubtitle("entity", "detonation_sentry.beep", "Detonation Sentry beeps");

        this.addSubtitle("entity", "slider.awaken", "Slider awakens");
        this.addSubtitle("entity", "slider.ambient", "Slider drones");
        this.addSubtitle("entity", "slider.collide", "Slider smashes");
        this.addSubtitle("entity", "slider.move", "Slider slides");
        this.addSubtitle("entity", "slider.hurt", "Slider hurts");
        this.addSubtitle("entity", "slider.death", "Slider breaks");

        this.addSubtitle("entity", "sentry_golem.death", "Sentry Golem dies");
        this.addSubtitle("entity", "sentry_golem.hurt", "Sentry Golem hurts");
        this.addSubtitle("entity", "sentry_golem.say", "Sentry Golem drones");
        this.addSubtitle("entity", "sentry_golem.throw_bomb", "Sentry Golem throws explosive");

        this.addSubtitle("entity", "blighted.burn", "Something burns");

        // Projectiles
        this.addSubtitle("entity", "prismallard_egg.throw", "Prismallard Egg flies");
        this.addSubtitle("entity", "arctic_snowball.throw", "Arctic Snowball flies");
        this.addSubtitle("entity", "rock.throw", "Rock flies");
        this.addSubtitle("entity", "skyroot_pinecone.throw", "Skyroot Pinecone flies");
        this.addSubtitle("entity", "lasso.throw", "Lasso flies");

        // Miscellaneous
        this.addSubtitle("entity", "electric_field.create", "Electricity booms");

        // UI
        this.addSubtitle("ui", "artisans_bench.take_result", "Artisan's Bench used");
    }

    private void addDeaths() {
        // Deaths
        this.addDeath("crush", "%1$s was crushed by %2$s");
        this.addDeath("effect.wound", "%1$s was fatally wounded");
        this.addDeath("effect.wound.player", "%1$s was fatally wounded by %2$s");
        this.addDeath("effect.fracture", "Oof, owie, %1$s's bones");
        this.addDeath("effect.fracture.player", "%2$s oof owied %1$s's bones");
        this.addDeath("effect.toxin", "%1$s succumbed to toxin");
        this.addDeath("effect.toxin.player", "%1$s succumbed to toxin while trying to escape %2$s");
        this.addDeath("effect.venom", "%1$s succumbed to venom");
        this.addDeath("effect.venom.player", "%1$s succumbed to venom while trying to escape %2$s");
        this.addDeath("effect.charged", "%1$s was electrocuted");
        this.addDeath("effect.charged.player", "%1$s was electrocuted by %2$s");
        this.addDeath("effect.immolation", "%1$s burned to death");
        this.addDeath("effect.immolation.player", "%1$s burned to death while trying to escape %2$s");
        this.addDeath("alkahest", "%1$s dissolved in alkahest");
        this.addDeath("alkahest.player", "%1$s dissolved in alkahest while trying to escape %2$s");
        this.addDeath("shock", "%1$s was electrocuted");
        this.addDeath("shock.player", "%1$s was electrocuted by %2$s");
        this.addDeath("carrion_sprout", "%1$s was munched-up by %2$s");
        this.addDeath("retreat", "%1$s retreated");
    }

    private void addMuralTitles() {
        this.addMuralTitle(AetherIIMurals.TEST, "Test Mural");
    }

    private void addConfigs() {
        this.addConfig("title", "The Aether II Configuration");
        this.addConfig("section.aether.ii.common.toml", "Common Settings");
        this.addConfig("section.aether.ii.common.toml.title", "The Aether II Common Configuration");
        this.addConfig("section.aether.ii.server.toml", "Server Settings");
        this.addConfig("section.aether.ii.server.toml.title", "The Aether II Server Configuration");

        this.addConfig("Gameplay", "Gameplay");
        this.addConfig("Gameplay.tooltip", "Config options that affect gameplay in the mod");
        this.addConfig("Gameplay.button", "Options");

        this.addConfig("Modpack", "Modpack");
        this.addConfig("Modpack.tooltip", "Config options that may be useful for modpack makers");
        this.addConfig("Modpack.button", "Options");

        this.addServerConfig("modpack.disable_aether_portal", "Disables Aether Portal creation");
        this.addServerConfig("modpack.disable_aether_portal.tooltip", "Prevents the Aether Portal from being created normally in the mod");
        this.addServerConfig("modpack.portal_destination_dimension_ID", "Sets portal destination dimension");
        this.addServerConfig("modpack.portal_destination_dimension_ID.tooltip", "Sets the ID of the dimension that the Aether Portal will send the player to");
        this.addServerConfig("modpack.portal_return_dimension_ID", "Sets portal return dimension");
        this.addServerConfig("modpack.portal_return_dimension_ID.tooltip", "Sets the ID of the dimension that the Aether Portal will return the player to");

        this.addCommonConfig("gameplay.start_with_portal", "Gives player Aether Portal Frame item");
        this.addCommonConfig("gameplay.start_with_portal.tooltip", "On world creation, the player is given an Aether Portal Frame item to automatically go to the Aether with");
        this.addCommonConfig("gameplay.spawn_in_aether", "Spawns the player in the Aether");
        this.addCommonConfig("gameplay.spawn_in_aether.tooltip", "Spawns the player in the Aether dimension; this is best enabled alongside other modpack configuration to avoid issues");
        this.addCommonConfig("gameplay.show_alpha_message", "Alpha Message");
        this.addCommonConfig("gameplay.show_alpha_message.tooltip", "Displays info about the Aether II's alpha on world join (goes away after the first time a world is join in a modded instance)");
        this.addCommonConfig("gameplay.yellow_alpha_button", "Yellow Alpha Button");
        this.addCommonConfig("gameplay.yellow_alpha_button.tooltip", "Makes the alpha info button in the Guidebook have a yellow icon to make it stand out (turns to white after the first time its clicked)");
        this.addCommonConfig("gameplay.experimental_dungeon_content", "Enables experimental dungeon content");
        this.addCommonConfig("gameplay.experimental_dungeon_content.tooltip", "Enables currently disabled Infected Guardian Tree content. At the moment this only includes enabling the dungeon's blocks in the creative inventory");
    }

    // Utility methods

    public void addBestiaryEntry(EntityType<?> entityType, String typeName, String subspeciesName, String description) {
        this.addBestiaryEntry(entityType, subspeciesName + ' ' + typeName, typeName, subspeciesName, description);
    }

    public void addBestiaryEntry(EntityType<?> entityType, String name, String slotName, String slotSubtitle, String description) {
        this.addBestiaryName(entityType, name);
        this.addBestiarySlotName(entityType, slotName);
        this.addBestiarySlotSubtitle(entityType, slotSubtitle);
        this.addBestiaryDescription(entityType, description);
    }

    private final TreeMap<String, String> entityTypes = new TreeMap<>();

    @Override
    public void add(EntityType<?> key, String name) {
        super.add(key, name);
        this.entityTypes.put(key.getDescriptionId().replace("entity.aether_ii.", ""), name);
    }

    public void addSpawnEggItem(DeferredItem<? extends SpawnEggItem> key, String name) {
        this.addItem(key, name);
    }

    public void addSpawnEggItem(DeferredItem<? extends SpawnEggItem> key) {
        String id = key.getId().getPath().replace("_spawn_egg", "");
        this.add(key.asItem(), this.entityTypes.get(id).concat(" Spawn Egg"));
    }

    public void addMuralTitle(DeferredHolder<Mural, Mural> key, String title) {
        this.add(key.getKey().identifier().toLanguageKey("mural", "title"), title);
    }
}