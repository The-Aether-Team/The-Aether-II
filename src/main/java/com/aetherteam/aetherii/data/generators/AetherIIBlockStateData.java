package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIIBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class AetherIIBlockStateData extends AetherIIBlockStateProvider {
    public AetherIIBlockStateData(PackOutput output, ExistingFileHelper helper) {
        super(output, AetherII.MODID, helper);
    }

    @Override
    public void registerStatesAndModels() {
        this.portal(AetherIIBlocks.AETHER_PORTAL.get());

        this.block(AetherIIBlocks.AETHER_DIRT.get(), "natural/");
        this.farmland(AetherIIBlocks.AETHER_FARMLAND.get(), AetherIIBlocks.AETHER_DIRT.get());
        this.dirtPath(AetherIIBlocks.AETHER_DIRT_PATH.get(), AetherIIBlocks.AETHER_DIRT.get());

        this.block(AetherIIBlocks.QUICKSOIL.get(), "natural/");
        this.block(AetherIIBlocks.FERROSITE_SAND.get(), "natural/");
        this.block(AetherIIBlocks.HOLYSTONE.get(), "natural/");
        this.block(AetherIIBlocks.MOSSY_HOLYSTONE.get(), "natural/");
        this.block(AetherIIBlocks.UNDERSHALE.get(), "natural/");
        this.block(AetherIIBlocks.AGIOSITE.get(), "natural/");

        this.block(AetherIIBlocks.AMBROSIUM_ORE.get(), "natural/");
        this.block(AetherIIBlocks.ZANITE_ORE.get(), "natural/");
        this.block(AetherIIBlocks.ARKENIUM_ORE.get(), "natural/");
        this.block(AetherIIBlocks.GRAVITITE_ORE.get(), "natural/");

        this.aercloudAll(AetherIIBlocks.COLD_AERCLOUD.get(), "natural/");
        this.aercloudAll(AetherIIBlocks.BLUE_AERCLOUD.get(), "natural/");
        this.aercloudAll(AetherIIBlocks.GOLDEN_AERCLOUD.get(), "natural/");
        this.aercloudAll(AetherIIBlocks.GREEN_AERCLOUD.get(), "natural/");
        this.purpleAercloud(AetherIIBlocks.PURPLE_AERCLOUD.get());
        this.aercloudAll(AetherIIBlocks.STORM_AERCLOUD.get(), "natural/");

        this.log(AetherIIBlocks.SKYROOT_LOG.get());
        this.log(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get());
        this.log(AetherIIBlocks.GREATROOT_LOG.get());
        this.wisprootLog(AetherIIBlocks.WISPROOT_LOG.get());
        this.logDifferentTop(AetherIIBlocks.AMBEROOT_LOG.get(), AetherIIBlocks.SKYROOT_LOG.get());
        this.wood(AetherIIBlocks.SKYROOT_WOOD.get(), AetherIIBlocks.SKYROOT_LOG.get());
        this.wood(AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get(), AetherIIBlocks.STRIPPED_SKYROOT_LOG.get());
        this.wood(AetherIIBlocks.GREATROOT_WOOD.get(), AetherIIBlocks.GREATROOT_LOG.get());
        this.wood(AetherIIBlocks.WISPROOT_WOOD.get(), AetherIIBlocks.WISPROOT_LOG.get());
        this.wood(AetherIIBlocks.AMBEROOT_WOOD.get(), AetherIIBlocks.AMBEROOT_LOG.get());

        this.block(AetherIIBlocks.SKYROOT_LEAVES.get(), "natural/");
        this.block(AetherIIBlocks.SKYPLANE_LEAVES.get(), "natural/");
        this.block(AetherIIBlocks.SKYBIRCH_LEAVES.get(), "natural/");
        this.block(AetherIIBlocks.SKYPINE_LEAVES.get(), "natural/");
        this.block(AetherIIBlocks.WISPROOT_LEAVES.get(), "natural/");
        this.block(AetherIIBlocks.WISPTOP_LEAVES.get(), "natural/");
        this.block(AetherIIBlocks.GREATROOT_LEAVES.get(), "natural/");
        this.block(AetherIIBlocks.GREATOAK_LEAVES.get(), "natural/");
        this.block(AetherIIBlocks.GREATBOA_LEAVES.get(), "natural/");
        this.block(AetherIIBlocks.AMBEROOT_LEAVES.get(), "natural/");

        this.saplingBlock(AetherIIBlocks.SKYROOT_SAPLING.get(), "natural/");
        this.saplingBlock(AetherIIBlocks.WISPROOT_SAPLING.get(), "natural/");
        this.saplingBlock(AetherIIBlocks.WISPTOP_SAPLING.get(), "natural/");
        this.saplingBlock(AetherIIBlocks.GREATROOT_SAPLING.get(), "natural/");
        this.saplingBlock(AetherIIBlocks.GREATOAK_SAPLING.get(), "natural/");
        this.saplingBlock(AetherIIBlocks.GREATBOA_SAPLING.get(), "natural/");
        this.saplingBlock(AetherIIBlocks.AMBEROOT_SAPLING.get(), "natural/");

        this.pottedPlant(AetherIIBlocks.POTTED_SKYROOT_SAPLING.get(), AetherIIBlocks.SKYROOT_SAPLING.get(), "natural/");
        this.pottedPlant(AetherIIBlocks.POTTED_WISPROOT_SAPLING.get(), AetherIIBlocks.WISPTOP_SAPLING.get(), "natural/");
        this.pottedPlant(AetherIIBlocks.POTTED_WISPTOP_SAPLING.get(), AetherIIBlocks.WISPTOP_SAPLING.get(), "natural/");
        this.pottedPlant(AetherIIBlocks.POTTED_GREATROOT_SAPLING.get(), AetherIIBlocks.GREATROOT_LEAVES.get(), "natural/");
        this.pottedPlant(AetherIIBlocks.POTTED_GREATOAK_SAPLING.get(), AetherIIBlocks.GREATOAK_SAPLING.get(), "natural/");
        this.pottedPlant(AetherIIBlocks.POTTED_GREATBOA_SAPLING.get(), AetherIIBlocks.GREATBOA_SAPLING.get(), "natural/");
        this.pottedPlant(AetherIIBlocks.POTTED_AMBEROOT_SAPLING.get(), AetherIIBlocks.AMBEROOT_SAPLING.get(), "natural/");

        this.grass(AetherIIBlocks.AETHER_SHORT_GRASS.get());
        this.grass(AetherIIBlocks.AETHER_MEDIUM_GRASS.get());
        this.grass(AetherIIBlocks.AETHER_LONG_GRASS.get());

        this.pebble(AetherIIBlocks.HOLYSTONE_PEBBLE.get(), AetherIIBlocks.HOLYSTONE.get());

        this.block(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get(), "natural/");

        this.block(AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.stairs(AetherIIBlocks.SKYROOT_STAIRS.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.slab(AetherIIBlocks.SKYROOT_SLAB.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.fence(AetherIIBlocks.SKYROOT_FENCE.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.fenceGateBlock(AetherIIBlocks.SKYROOT_FENCE_GATE.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.doorBlock(AetherIIBlocks.SKYROOT_DOOR.get(), texture(name(AetherIIBlocks.SKYROOT_DOOR.get()), "construction/", "_bottom"), texture(name(AetherIIBlocks.SKYROOT_DOOR.get()), "construction/", "_top"));
        this.trapdoorBlock(AetherIIBlocks.SKYROOT_TRAPDOOR.get(), texture(name(AetherIIBlocks.SKYROOT_TRAPDOOR.get()), "construction/"), true);
        this.buttonBlock(AetherIIBlocks.SKYROOT_BUTTON.get(), this.texture(this.name(AetherIIBlocks.SKYROOT_PLANKS.get()), "construction/"));
        this.pressurePlateBlock(AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get(), this.texture(this.name(AetherIIBlocks.SKYROOT_PLANKS.get()), "construction/"));

        this.block(AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.stairs(AetherIIBlocks.GREATROOT_STAIRS.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.slab(AetherIIBlocks.GREATROOT_SLAB.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.fence(AetherIIBlocks.GREATROOT_FENCE.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.fenceGateBlock(AetherIIBlocks.GREATROOT_FENCE_GATE.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.buttonBlock(AetherIIBlocks.GREATROOT_BUTTON.get(), this.texture(this.name(AetherIIBlocks.GREATROOT_PLANKS.get()), "construction/"));
        this.pressurePlateBlock(AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get(), this.texture(this.name(AetherIIBlocks.GREATROOT_PLANKS.get()), "construction/"));

        this.block(AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.stairs(AetherIIBlocks.WISPROOT_STAIRS.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.slab(AetherIIBlocks.WISPROOT_SLAB.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.fence(AetherIIBlocks.WISPROOT_FENCE.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.fenceGateBlock(AetherIIBlocks.WISPROOT_FENCE_GATE.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.buttonBlock(AetherIIBlocks.WISPROOT_BUTTON.get(), this.texture(this.name(AetherIIBlocks.WISPROOT_PLANKS.get()), "construction/"));
        this.pressurePlateBlock(AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get(), this.texture(this.name(AetherIIBlocks.WISPROOT_PLANKS.get()), "construction/"));

        this.stairs(AetherIIBlocks.HOLYSTONE_STAIRS.get(), AetherIIBlocks.HOLYSTONE.get(), "natural/");
        this.slab(AetherIIBlocks.HOLYSTONE_SLAB.get(), AetherIIBlocks.HOLYSTONE.get(), "natural/");
        this.wallBlock(AetherIIBlocks.HOLYSTONE_WALL.get(), AetherIIBlocks.HOLYSTONE.get(), "natural/");
        this.buttonBlock(AetherIIBlocks.HOLYSTONE_BUTTON.get(), this.texture(this.name(AetherIIBlocks.HOLYSTONE.get()), "natural/"));
        this.pressurePlateBlock(AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get(), this.texture(this.name(AetherIIBlocks.HOLYSTONE.get()), "natural/"));

        this.stairs(AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get(), "natural/");
        this.slab(AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get(), "natural/");
        this.wallBlock(AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get(), "natural/");

        this.block(AetherIIBlocks.HOLYSTONE_BRICKS.get(), "construction/");
        this.stairs(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get(), "construction/");
        this.slab(AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get(), "construction/");
        this.wallBlock(AetherIIBlocks.HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get(), "construction/");

        this.stairs(AetherIIBlocks.UNDERSHALE_STAIRS.get(), AetherIIBlocks.UNDERSHALE.get(), "natural/");
        this.slab(AetherIIBlocks.UNDERSHALE_SLAB.get(), AetherIIBlocks.UNDERSHALE.get(), "natural/");
        this.wallBlock(AetherIIBlocks.UNDERSHALE_WALL.get(), AetherIIBlocks.UNDERSHALE.get(), "natural/");

        this.stairs(AetherIIBlocks.AGIOSITE_STAIRS.get(), AetherIIBlocks.AGIOSITE.get(), "natural/");
        this.slab(AetherIIBlocks.AGIOSITE_SLAB.get(), AetherIIBlocks.AGIOSITE.get(), "natural/");
        this.wallBlock(AetherIIBlocks.AGIOSITE_WALL.get(), AetherIIBlocks.AGIOSITE.get(), "natural/");

        this.block(AetherIIBlocks.AGIOSITE_BRICKS.get(), "construction/");
        this.stairs(AetherIIBlocks.AGIOSITE_BRICK_STAIRS.get(), AetherIIBlocks.AGIOSITE_BRICKS.get(), "construction/");
        this.slab(AetherIIBlocks.AGIOSITE_BRICK_SLAB.get(), AetherIIBlocks.AGIOSITE_BRICKS.get(), "construction/");
        this.wallBlock(AetherIIBlocks.AGIOSITE_BRICK_WALL.get(), AetherIIBlocks.AGIOSITE_BRICKS.get(), "construction/");

        this.translucentBlock(AetherIIBlocks.QUICKSOIL_GLASS.get(), "construction/");
        this.pane(AetherIIBlocks.QUICKSOIL_GLASS_PANE.get(), AetherIIBlocks.QUICKSOIL_GLASS.get(), "construction/");

        this.block(AetherIIBlocks.CLOUDWOOL.get(), "construction/");
        this.carpet(AetherIIBlocks.CLOUDWOOL_CARPET.get(), AetherIIBlocks.CLOUDWOOL.get(),"construction/");

        this.block(AetherIIBlocks.AMBROSIUM_BLOCK.get(), "construction/");
        this.block(AetherIIBlocks.ZANITE_BLOCK.get(), "construction/");
        this.block(AetherIIBlocks.GRAVITITE_BLOCK.get(), "construction/");

        this.torchBlock(AetherIIBlocks.AMBROSIUM_TORCH.get(), AetherIIBlocks.AMBROSIUM_WALL_TORCH.get());
        this.skyrootCraftingTable(AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.holystoneFurnace(AetherIIBlocks.HOLYSTONE_FURNACE.get());
        this.skyrootChest(AetherIIBlocks.SKYROOT_CHEST.get());
        this.skyrootLadder(AetherIIBlocks.SKYROOT_LADDER.get());
    }
}
