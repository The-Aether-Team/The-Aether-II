package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class AetherIIItemTagData extends ItemTagsProvider {
    public AetherIIItemTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper helper) {
        super(output, registries, blockTags, AetherII.MODID, helper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(HolderLookup.Provider provider) {
        // Aether
        this.copy(AetherIITags.Blocks.AETHER_DIRT, AetherIITags.Items.AETHER_DIRT);
        this.copy(AetherIITags.Blocks.HOLYSTONE, AetherIITags.Items.HOLYSTONE);
        this.copy(AetherIITags.Blocks.AERCLOUDS, AetherIITags.Items.AERCLOUDS);
        this.copy(AetherIITags.Blocks.SKYROOT_LOGS, AetherIITags.Items.SKYROOT_LOGS);
        this.copy(AetherIITags.Blocks.WISPROOT_LOGS, AetherIITags.Items.WISPROOT_LOGS);
        this.copy(AetherIITags.Blocks.GREATROOT_LOGS, AetherIITags.Items.GREATROOT_LOGS);
        this.copy(AetherIITags.Blocks.AMBEROOT_LOGS, AetherIITags.Items.AMBEROOT_LOGS);
        this.copy(AetherIITags.Blocks.SKYROOT_MASONRY_BLOCKS, AetherIITags.Items.SKYROOT_MASONRY_BLOCKS);
        this.copy(AetherIITags.Blocks.GREATROOT_MASONRY_BLOCKS, AetherIITags.Items.GREATROOT_MASONRY_BLOCKS);
        this.copy(AetherIITags.Blocks.WISPROOT_MASONRY_BLOCKS, AetherIITags.Items.WISPROOT_MASONRY_BLOCKS);
        this.tag(AetherIITags.Items.RODS_SKYROOT).add(AetherIIItems.SKYROOT_STICK.get());
        this.tag(AetherIITags.Items.GEMS_ZANITE).add(AetherIIItems.ZANITE_GEMSTONE.get());
        this.tag(AetherIITags.Items.PLATES_ARKENIUM).add(AetherIIItems.ARKENIUM_PLATE.get());
        this.tag(AetherIITags.Items.PLATES_GRAVITITE).add(AetherIIItems.GRAVITITE_PLATE.get());
        this.tag(AetherIITags.Items.TOOLS_SHORTSWORDS).add(
                AetherIIItems.SKYROOT_SHORTSWORD.get(),
                AetherIIItems.HOLYSTONE_SHORTSWORD.get(),
                AetherIIItems.ZANITE_SHORTSWORD.get(),
                AetherIIItems.ARKENIUM_SHORTSWORD.get(),
                AetherIIItems.GRAVITITE_SHORTSWORD.get()
        );
        this.tag(AetherIITags.Items.TOOLS_HAMMERS).add(
                AetherIIItems.SKYROOT_HAMMER.get(),
                AetherIIItems.HOLYSTONE_HAMMER.get(),
                AetherIIItems.ZANITE_HAMMER.get(),
                AetherIIItems.ARKENIUM_HAMMER.get(),
                AetherIIItems.GRAVITITE_HAMMER.get()
        );
        this.tag(AetherIITags.Items.TOOLS_SPEARS).add(
                AetherIIItems.SKYROOT_SPEAR.get(),
                AetherIIItems.HOLYSTONE_SPEAR.get(),
                AetherIIItems.ZANITE_SPEAR.get(),
                AetherIIItems.ARKENIUM_SPEAR.get(),
                AetherIIItems.GRAVITITE_SPEAR.get()
        );
        this.tag(AetherIITags.Items.UNIQUE_TOOLTIP_COLOR).add(
                AetherIIItems.SKYROOT_SHORTSWORD.get(),
                AetherIIItems.SKYROOT_HAMMER.get(),
                AetherIIItems.SKYROOT_SPEAR.get(),
                AetherIIItems.HOLYSTONE_SHORTSWORD.get(),
                AetherIIItems.HOLYSTONE_HAMMER.get(),
                AetherIIItems.HOLYSTONE_SPEAR.get(),
                AetherIIItems.ZANITE_SHORTSWORD.get(),
                AetherIIItems.ZANITE_HAMMER.get(),
                AetherIIItems.ZANITE_SPEAR.get(),
                AetherIIItems.ARKENIUM_SHORTSWORD.get(),
                AetherIIItems.ARKENIUM_HAMMER.get(),
                AetherIIItems.ARKENIUM_SPEAR.get(),
                AetherIIItems.GRAVITITE_SHORTSWORD.get(),
                AetherIIItems.GRAVITITE_HAMMER.get(),
                AetherIIItems.GRAVITITE_SPEAR.get(),
                AetherIIItems.SKYROOT_AXE.get(),
                AetherIIItems.HOLYSTONE_AXE.get(),
                AetherIIItems.ZANITE_AXE.get(),
                AetherIIItems.ARKENIUM_AXE.get(),
                AetherIIItems.GRAVITITE_AXE.get(),
                AetherIIItems.SKYROOT_PICKAXE.get(),
                AetherIIItems.HOLYSTONE_PICKAXE.get(),
                AetherIIItems.ZANITE_PICKAXE.get(),
                AetherIIItems.ARKENIUM_PICKAXE.get(),
                AetherIIItems.GRAVITITE_PICKAXE.get(),
                AetherIIItems.SKYROOT_SHOVEL.get(),
                AetherIIItems.HOLYSTONE_SHOVEL.get(),
                AetherIIItems.ZANITE_SHOVEL.get(),
                AetherIIItems.ARKENIUM_SHOVEL.get(),
                AetherIIItems.GRAVITITE_SHOVEL.get(),
                AetherIIItems.SKYROOT_TROWEL.get(),
                AetherIIItems.HOLYSTONE_TROWEL.get(),
                AetherIIItems.ZANITE_TROWEL.get(),
                AetherIIItems.ARKENIUM_TROWEL.get(),
                AetherIIItems.GRAVITITE_TROWEL.get()
        );
        this.tag(AetherIITags.Items.CRAFTS_SKYROOT_PLANKS).addTags(
                AetherIITags.Items.SKYROOT_LOGS,
                AetherIITags.Items.AMBEROOT_LOGS
        );
        this.tag(AetherIITags.Items.CRAFTS_GREATROOT_PLANKS).addTag(
                AetherIITags.Items.GREATROOT_LOGS
        );
        this.tag(AetherIITags.Items.CRAFTS_WISPROOT_PLANKS).addTag(
                AetherIITags.Items.WISPROOT_LOGS
        );
        this.tag(AetherIITags.Items.PLANKS_CRAFTING).add(
                AetherIIBlocks.SKYROOT_PLANKS.asItem(),
                AetherIIBlocks.GREATROOT_PLANKS.asItem(),
                AetherIIBlocks.WISPROOT_PLANKS.asItem()
        );
        this.tag(AetherIITags.Items.STONE_CRAFTING).add(
                AetherIIBlocks.HOLYSTONE.asItem(),
                AetherIIBlocks.UNDERSHALE.asItem()
        );
        this.tag(AetherIITags.Items.CRAFTS_SKYROOT_STICKS).add(
                AetherIIBlocks.SKYROOT_PLANKS.asItem(),
                AetherIIBlocks.GREATROOT_PLANKS.asItem(),
                AetherIIBlocks.WISPROOT_PLANKS.asItem()
        );
        this.tag(AetherIITags.Items.CRAFTS_SKYROOT_TOOLS).add(
                AetherIIBlocks.SKYROOT_PLANKS.asItem(),
                AetherIIBlocks.GREATROOT_PLANKS.asItem(),
                AetherIIBlocks.WISPROOT_PLANKS.asItem()
        );
        this.tag(AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS).add(
                AetherIIBlocks.HOLYSTONE.asItem(),
                AetherIIBlocks.UNDERSHALE.asItem()
        );
        this.tag(AetherIITags.Items.PHYG_TEMPTATION_ITEMS).add(
                AetherIIItems.ORANGE.get(),
                AetherIIItems.BLUEBERRY.get()
        );
        this.tag(AetherIITags.Items.AERBUNNY_TEMPTATION_ITEMS).add(
                AetherIIItems.ORANGE.get()
        );
        this.tag(AetherIITags.Items.SKYROOT_REPAIRING).addTag(AetherIITags.Items.CRAFTS_SKYROOT_TOOLS);
        this.tag(AetherIITags.Items.HOLYSTONE_REPAIRING).add(AetherIIBlocks.HOLYSTONE.asItem());
        this.tag(AetherIITags.Items.ZANITE_REPAIRING).add(AetherIIItems.ZANITE_GEMSTONE.get());
        this.tag(AetherIITags.Items.ARKENIUM_REPAIRING).add(AetherIIItems.ARKENIUM_PLATE.get());
        this.tag(AetherIITags.Items.GRAVITITE_REPAIRING).add(AetherIIItems.GRAVITITE_PLATE.get());
        this.tag(AetherIITags.Items.AETHER_PORTAL_ACTIVATION_ITEMS);
        this.tag(AetherIITags.Items.GOLDEN_AMBER_HARVESTERS).add(
                AetherIIItems.ZANITE_AXE.get(),
                AetherIIItems.ARKENIUM_AXE.get(),
                AetherIIItems.GRAVITITE_AXE.get()
        );

        // Vanilla
        this.tag(ItemTags.WOOL).add(AetherIIBlocks.CLOUDWOOL.asItem());
        this.tag(ItemTags.STONE_CRAFTING_MATERIALS).add(
                AetherIIBlocks.HOLYSTONE.asItem(),
                AetherIIBlocks.UNDERSHALE.asItem()
        );
        this.tag(ItemTags.WOODEN_STAIRS).add(
                AetherIIBlocks.SKYROOT_STAIRS.asItem(),
                AetherIIBlocks.GREATROOT_STAIRS.asItem(),
                AetherIIBlocks.WISPROOT_STAIRS.asItem()
        );
        this.tag(ItemTags.WOODEN_SLABS).add(
                AetherIIBlocks.SKYROOT_SLAB.asItem(),
                AetherIIBlocks.GREATROOT_STAIRS.asItem(),
                AetherIIBlocks.WISPROOT_STAIRS.asItem()
        );
        this.tag(ItemTags.WOODEN_FENCES).add(
                AetherIIBlocks.SKYROOT_FENCE.asItem(),
                AetherIIBlocks.GREATROOT_FENCE.asItem(),
                AetherIIBlocks.WISPROOT_FENCE.asItem()
        );
        this.tag(ItemTags.WOODEN_DOORS).add(
                AetherIIBlocks.SKYROOT_DOOR.asItem()
        );
        this.tag(ItemTags.WOODEN_TRAPDOORS).add(
                AetherIIBlocks.SKYROOT_TRAPDOOR.asItem()
        );
        this.tag(ItemTags.WOODEN_BUTTONS).add(
                AetherIIBlocks.SKYROOT_BUTTON.asItem(),
                AetherIIBlocks.GREATROOT_BUTTON.asItem(),
                AetherIIBlocks.WISPROOT_BUTTON.asItem()
        );
        this.tag(ItemTags.STONE_BUTTONS).add(AetherIIBlocks.HOLYSTONE_BUTTON.get().asItem());
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(
                AetherIIBlocks.SKYROOT_PRESSURE_PLATE.asItem(),
                AetherIIBlocks.GREATROOT_PRESSURE_PLATE.asItem(),
                AetherIIBlocks.WISPROOT_PRESSURE_PLATE.asItem()
        );
        this.tag(ItemTags.WOOL_CARPETS).add(AetherIIBlocks.CLOUDWOOL_CARPET.get().asItem());
        this.tag(ItemTags.SAPLINGS).add(
                AetherIIBlocks.SKYROOT_SAPLING.asItem(),
                AetherIIBlocks.WISPROOT_SAPLING.asItem(),
                AetherIIBlocks.WISPTOP_SAPLING.asItem(),
                AetherIIBlocks.GREATROOT_SAPLING.asItem(),
                AetherIIBlocks.GREATOAK_SAPLING.asItem(),
                AetherIIBlocks.GREATBOA_SAPLING.asItem(),
                AetherIIBlocks.AMBEROOT_SAPLING.asItem()
        );
        this.tag(ItemTags.LOGS_THAT_BURN).addTags(
                AetherIITags.Items.SKYROOT_LOGS,
                AetherIITags.Items.GREATROOT_LOGS,
                AetherIITags.Items.WISPROOT_LOGS,
                AetherIITags.Items.AMBEROOT_LOGS
        );
        this.tag(ItemTags.STAIRS).add(
                AetherIIBlocks.HOLYSTONE_STAIRS.asItem(),
                AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS.asItem(),
                AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS.asItem(),
                AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.asItem(),
                AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS.asItem(),
                AetherIIBlocks.UNDERSHALE_STAIRS.asItem(),
                AetherIIBlocks.AGIOSITE_STAIRS.asItem(),
                AetherIIBlocks.AGIOSITE_BRICK_STAIRS.asItem()
        );
        this.tag(ItemTags.SLABS).add(
                AetherIIBlocks.HOLYSTONE_SLAB.asItem(),
                AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.asItem(),
                AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB.asItem(),
                AetherIIBlocks.HOLYSTONE_BRICK_SLAB.asItem(),
                AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB.asItem(),
                AetherIIBlocks.UNDERSHALE_SLAB.asItem(),
                AetherIIBlocks.AGIOSITE_SLAB.asItem(),
                AetherIIBlocks.AGIOSITE_BRICK_SLAB.asItem()
        );
        this.tag(ItemTags.WALLS).add(
                AetherIIBlocks.HOLYSTONE_WALL.asItem(),
                AetherIIBlocks.MOSSY_HOLYSTONE_WALL.asItem(),
                AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL.asItem(),
                AetherIIBlocks.HOLYSTONE_BRICK_WALL.asItem(),
                AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL.asItem(),
                AetherIIBlocks.UNDERSHALE_WALL.asItem(),
                AetherIIBlocks.AGIOSITE_WALL.asItem(),
                AetherIIBlocks.AGIOSITE_BRICK_WALL.asItem()
        );
        this.tag(ItemTags.LEAVES).add(
                AetherIIBlocks.SKYROOT_LEAVES.asItem(),
                AetherIIBlocks.SKYPLANE_LEAVES.asItem(),
                AetherIIBlocks.SKYBIRCH_LEAVES.asItem(),
                AetherIIBlocks.SKYPINE_LEAVES.asItem(),
                AetherIIBlocks.WISPROOT_LEAVES.asItem(),
                AetherIIBlocks.WISPTOP_LEAVES.asItem(),
                AetherIIBlocks.GREATROOT_LEAVES.asItem(),
                AetherIIBlocks.GREATOAK_LEAVES.asItem(),
                AetherIIBlocks.GREATBOA_LEAVES.asItem(),
                AetherIIBlocks.AMBEROOT_LEAVES.asItem()
        );

        this.tag(AetherIITags.Items.PHYG_TEMPTATION_ITEMS).add(
                AetherIIItems.ORANGE.get(),
                AetherIIItems.BLUEBERRY.get()
        );

        this.tag(AetherIITags.Items.AERBUNNY_TEMPTATION_ITEMS).add(
                AetherIIItems.ORANGE.get()
        );

        this.tag(ItemTags.MUSIC_DISCS).add(
                AetherIIItems.MUSIC_DISC_AETHER_TUNE.get(),
                AetherIIItems.MUSIC_DISC_ASCENDING_DAWN.get(),
                AetherIIItems.MUSIC_DISC_AERWHALE.get(),
                AetherIIItems.MUSIC_DISC_APPROACHES.get(),
                AetherIIItems.MUSIC_DISC_DEMISE.get(),
                AetherIIItems.RECORDING_892.get()
        );
        this.tag(ItemTags.BEACON_PAYMENT_ITEMS).add(
                AetherIIItems.ZANITE_GEMSTONE.get(),
                AetherIIItems.ARKENIUM_PLATE.get(),
                AetherIIItems.GRAVITITE_PLATE.get()
        );
        this.tag(ItemTags.CLUSTER_MAX_HARVESTABLES).add(
                AetherIIItems.SKYROOT_PICKAXE.get(),
                AetherIIItems.HOLYSTONE_PICKAXE.get(),
                AetherIIItems.ZANITE_PICKAXE.get(),
                AetherIIItems.ARKENIUM_PICKAXE.get(),
                AetherIIItems.GRAVITITE_PICKAXE.get()
        );
        this.tag(ItemTags.SWORDS).addTag(AetherIITags.Items.TOOLS_SHORTSWORDS);
        this.tag(ItemTags.AXES).add(
                AetherIIItems.SKYROOT_AXE.get(),
                AetherIIItems.HOLYSTONE_AXE.get(),
                AetherIIItems.ZANITE_AXE.get(),
                AetherIIItems.ARKENIUM_AXE.get(),
                AetherIIItems.GRAVITITE_AXE.get()
        );
        this.tag(ItemTags.PICKAXES).add(
                AetherIIItems.SKYROOT_PICKAXE.get(),
                AetherIIItems.HOLYSTONE_PICKAXE.get(),
                AetherIIItems.ZANITE_PICKAXE.get(),
                AetherIIItems.ARKENIUM_PICKAXE.get(),
                AetherIIItems.GRAVITITE_PICKAXE.get()
        );
        this.tag(ItemTags.SHOVELS).add(
                AetherIIItems.SKYROOT_SHOVEL.get(),
                AetherIIItems.HOLYSTONE_SHOVEL.get(),
                AetherIIItems.ZANITE_SHOVEL.get(),
                AetherIIItems.ARKENIUM_SHOVEL.get(),
                AetherIIItems.GRAVITITE_SHOVEL.get()
        );
        this.tag(ItemTags.HOES).add(
                AetherIIItems.SKYROOT_TROWEL.get(),
                AetherIIItems.HOLYSTONE_TROWEL.get(),
                AetherIIItems.ZANITE_TROWEL.get(),
                AetherIIItems.ARKENIUM_TROWEL.get(),
                AetherIIItems.GRAVITITE_TROWEL.get()
        );

        // Forge
        this.tag(Tags.Items.BOOKSHELVES).add(
                AetherIIBlocks.SKYROOT_BOOKSHELF.asItem(),
                AetherIIBlocks.HOLYSTONE_BOOKSHELF.asItem()
        );
        this.tag(Tags.Items.FENCE_GATES_WOODEN).add(
                AetherIIBlocks.SKYROOT_FENCE_GATE.asItem(),
                AetherIIBlocks.GREATROOT_FENCE_GATE.asItem(),
                AetherIIBlocks.WISPROOT_FENCE_GATE.asItem()
        );
        this.tag(Tags.Items.FENCES_WOODEN).add(
                AetherIIBlocks.SKYROOT_FENCE.asItem(),
                AetherIIBlocks.GREATROOT_FENCE.asItem(),
                AetherIIBlocks.WISPROOT_FENCE.asItem()
        );
        this.tag(Tags.Items.FENCE_GATES).add(
                AetherIIBlocks.SKYROOT_FENCE_GATE.asItem(),
                AetherIIBlocks.GREATROOT_FENCE_GATE.asItem(),
                AetherIIBlocks.WISPROOT_FENCE_GATE.asItem()
        );
        this.tag(Tags.Items.FENCES).add(
                AetherIIBlocks.SKYROOT_FENCE.asItem(),
                AetherIIBlocks.GREATROOT_FENCE.asItem(),
                AetherIIBlocks.WISPROOT_FENCE.asItem()
        );
        this.tag(Tags.Items.GLASS_COLORLESS).add(
                AetherIIBlocks.QUICKSOIL_GLASS.asItem(),
                AetherIIBlocks.CRUDE_SCATTERGLASS.asItem(),
                AetherIIBlocks.SCATTERGLASS.asItem()
        );
        this.tag(Tags.Items.GLASS_PANES_COLORLESS).add(
                AetherIIBlocks.QUICKSOIL_GLASS_PANE.asItem(),
                AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.asItem(),
                AetherIIBlocks.SCATTERGLASS_PANE.asItem()
        );
        this.tag(Tags.Items.ORE_RATES_SINGULAR).add(
                AetherIIBlocks.AMBROSIUM_ORE.asItem(),
                AetherIIBlocks.ZANITE_ORE.asItem(),
                AetherIIBlocks.ARKENIUM_ORE.asItem(),
                AetherIIBlocks.GRAVITITE_ORE.asItem()
        );
        this.tag(Tags.Items.ORES).add(
                AetherIIBlocks.AMBROSIUM_ORE.asItem(),
                AetherIIBlocks.ZANITE_ORE.asItem(),
                AetherIIBlocks.ARKENIUM_ORE.asItem(),
                AetherIIBlocks.GRAVITITE_ORE.asItem()
        );
        this.tag(Tags.Items.RODS_WOODEN).add(AetherIIItems.SKYROOT_STICK.get());
        this.tag(Tags.Items.STORAGE_BLOCKS).add(
                AetherIIBlocks.AMBROSIUM_BLOCK.asItem(),
                AetherIIBlocks.ZANITE_BLOCK.asItem(),
                AetherIIBlocks.GRAVITITE_BLOCK.asItem()
        );
        this.tag(Tags.Items.TOOLS).addTags(
                AetherIITags.Items.TOOLS_SHORTSWORDS,
                AetherIITags.Items.TOOLS_HAMMERS,
                AetherIITags.Items.TOOLS_SPEARS
        );
        this.tag(Tags.Items.CHESTS_WOODEN).add(
                AetherIIBlocks.SKYROOT_CHEST.get().asItem()
        );
    }
}