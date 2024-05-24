package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIIItemModelProvider;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class AetherIIItemModelData extends AetherIIItemModelProvider {
    public AetherIIItemModelData(PackOutput output, ExistingFileHelper helper) {
        super(output, AetherII.MODID, helper);
    }

    @Override
    protected void registerModels() {
        this.handheldItem(AetherIIItems.SKYROOT_SWORD.get(), "weapons/");
        this.handheldItem(AetherIIItems.SKYROOT_HAMMER.get(), "weapons/");
        this.handheldItem(AetherIIItems.SKYROOT_SPEAR.get(), "weapons/");
        this.handheldItem(AetherIIItems.SKYROOT_PICKAXE.get(), "tools/");
        this.handheldItem(AetherIIItems.SKYROOT_AXE.get(), "tools/");
        this.handheldItem(AetherIIItems.SKYROOT_SHOVEL.get(), "tools/");
        this.handheldItem(AetherIIItems.SKYROOT_TROWEL.get(), "tools/");

        this.handheldItem(AetherIIItems.HOLYSTONE_SWORD.get(), "weapons/");
        this.handheldItem(AetherIIItems.HOLYSTONE_HAMMER.get(), "weapons/");
        this.handheldItem(AetherIIItems.HOLYSTONE_SPEAR.get(), "weapons/");
        this.handheldItem(AetherIIItems.HOLYSTONE_PICKAXE.get(), "tools/");
        this.handheldItem(AetherIIItems.HOLYSTONE_AXE.get(), "tools/");
        this.handheldItem(AetherIIItems.HOLYSTONE_SHOVEL.get(), "tools/");
        this.handheldItem(AetherIIItems.HOLYSTONE_TROWEL.get(), "tools/");

        this.handheldItem(AetherIIItems.ZANITE_SWORD.get(), "weapons/");
        this.handheldItem(AetherIIItems.ZANITE_HAMMER.get(), "weapons/");
        this.handheldItem(AetherIIItems.ZANITE_SPEAR.get(), "weapons/");
        this.handheldItem(AetherIIItems.ZANITE_PICKAXE.get(), "tools/");
        this.handheldItem(AetherIIItems.ZANITE_AXE.get(), "tools/");
        this.handheldItem(AetherIIItems.ZANITE_SHOVEL.get(), "tools/");
        this.handheldItem(AetherIIItems.ZANITE_TROWEL.get(), "tools/");

        this.handheldItem(AetherIIItems.ARKENIUM_SWORD.get(), "weapons/");
        this.handheldItem(AetherIIItems.ARKENIUM_HAMMER.get(), "weapons/");
        this.handheldItem(AetherIIItems.ARKENIUM_SPEAR.get(), "weapons/");
        this.handheldItem(AetherIIItems.ARKENIUM_PICKAXE.get(), "tools/");
        this.handheldItem(AetherIIItems.ARKENIUM_AXE.get(), "tools/");
        this.handheldItem(AetherIIItems.ARKENIUM_SHOVEL.get(), "tools/");
        this.handheldItem(AetherIIItems.ARKENIUM_TROWEL.get(), "tools/");

        this.handheldItem(AetherIIItems.GRAVITITE_SWORD.get(), "weapons/");
        this.handheldItem(AetherIIItems.GRAVITITE_HAMMER.get(), "weapons/");
        this.handheldItem(AetherIIItems.GRAVITITE_SPEAR.get(), "weapons/");
        this.handheldItem(AetherIIItems.GRAVITITE_PICKAXE.get(), "tools/");
        this.handheldItem(AetherIIItems.GRAVITITE_AXE.get(), "tools/");
        this.handheldItem(AetherIIItems.GRAVITITE_SHOVEL.get(), "tools/");
        this.handheldItem(AetherIIItems.GRAVITITE_TROWEL.get(), "tools/");
        
        this.handheldItem(AetherIIItems.SKYROOT_STICK.get(), "materials/");
        this.item(AetherIIItems.AMBROSIUM_SHARD.get(), "materials/");
        this.item(AetherIIItems.ZANITE_GEMSTONE.get(), "materials/");
        this.item(AetherIIItems.RAW_ARKENIUM.get(), "materials/");
        this.item(AetherIIItems.ARKENIUM_PLATE.get(), "materials/");
        this.item(AetherIIItems.RAW_GRAVITITE.get(), "materials/");
        this.item(AetherIIItems.GRAVITITE_PLATE.get(), "materials/");

        this.itemBlock(AetherIIBlocks.AETHER_GRASS_BLOCK.get());
        this.itemBlock(AetherIIBlocks.AETHER_DIRT.get());
        this.itemBlock(AetherIIBlocks.QUICKSOIL.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE.get());
        this.itemBlock(AetherIIBlocks.UNDERSHALE.get());
        this.itemBlock(AetherIIBlocks.AMBROSIUM_ORE.get());
        this.itemBlock(AetherIIBlocks.ZANITE_ORE.get());
        this.itemBlock(AetherIIBlocks.ARKENIUM_ORE.get());
        this.itemBlock(AetherIIBlocks.GRAVITITE_ORE.get());
        this.aercloudItem(AetherIIBlocks.COLD_AERCLOUD.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.AMBEROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_WOOD.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_WOOD.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_WOOD.get());
        this.itemBlock(AetherIIBlocks.AMBEROOT_WOOD.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_LEAVES.get());
        this.itemBlock(AetherIIBlocks.SKYPLANE_LEAVES.get());
        this.itemBlock(AetherIIBlocks.SKYBIRCH_LEAVES.get());
        this.itemBlock(AetherIIBlocks.SKYPINE_LEAVES.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_LEAVES.get());
        this.itemBlock(AetherIIBlocks.WISPTOP_LEAVES.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_LEAVES.get());
        this.itemBlock(AetherIIBlocks.GREATOAK_LEAVES.get());
        this.itemBlock(AetherIIBlocks.GREATBOA_LEAVES.get());
        this.itemBlock(AetherIIBlocks.AMBEROOT_LEAVES.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_PLANKS.get());
        this.itemFence(AetherIIBlocks.SKYROOT_FENCE.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.SKYROOT_FENCE_GATE.get());
        this.itemButton(AetherIIBlocks.SKYROOT_BUTTON.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_STAIRS.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_SLAB.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_PLANKS.get());
        this.itemFence(AetherIIBlocks.GREATROOT_FENCE.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.GREATROOT_FENCE_GATE.get());
        this.itemButton(AetherIIBlocks.GREATROOT_BUTTON.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_STAIRS.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_SLAB.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_PLANKS.get());
        this.itemFence(AetherIIBlocks.WISPROOT_FENCE.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.WISPROOT_FENCE_GATE.get());
        this.itemButton(AetherIIBlocks.WISPROOT_BUTTON.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_STAIRS.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_SLAB.get());
        this.itemButton(AetherIIBlocks.HOLYSTONE_BUTTON.get(), AetherIIBlocks.HOLYSTONE.get(), "natural/");
        this.itemBlock(AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get());
        this.itemWallBlock(AetherIIBlocks.HOLYSTONE_WALL.get(), AetherIIBlocks.HOLYSTONE.get(), "natural/");
        this.itemBlock(AetherIIBlocks.HOLYSTONE_STAIRS.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_SLAB.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.itemWallBlock(AetherIIBlocks.HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get());
        this.itemBlock(AetherIIBlocks.QUICKSOIL_GLASS.get());
        this.pane(AetherIIBlocks.QUICKSOIL_GLASS_PANE.get(), AetherIIBlocks.QUICKSOIL_GLASS.get(), "construction/");
    }
}
