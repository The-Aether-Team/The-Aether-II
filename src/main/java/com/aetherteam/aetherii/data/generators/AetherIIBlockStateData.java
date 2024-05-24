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
        this.block(AetherIIBlocks.AETHER_DIRT.get(), "natural/");
        this.block(AetherIIBlocks.QUICKSOIL.get(), "natural/");
        this.block(AetherIIBlocks.HOLYSTONE.get(), "natural/");
        this.block(AetherIIBlocks.UNDERSHALE.get(), "natural/");
        this.block(AetherIIBlocks.AMBROSIUM_ORE.get(), "natural/");
        this.block(AetherIIBlocks.ZANITE_ORE.get(), "natural/");
        this.block(AetherIIBlocks.ARKENIUM_ORE.get(), "natural/");
        this.block(AetherIIBlocks.GRAVITITE_ORE.get(), "natural/");

        this.aercloudAll(AetherIIBlocks.COLD_AERCLOUD.get(), "natural/");

        this.log(AetherIIBlocks.SKYROOT_LOG.get());
        this.log(AetherIIBlocks.GREATROOT_LOG.get());
        this.log(AetherIIBlocks.WISPROOT_LOG.get());
        this.logDifferentTop(AetherIIBlocks.AMBEROOT_LOG.get(), AetherIIBlocks.SKYROOT_LOG.get());
        this.wood(AetherIIBlocks.SKYROOT_WOOD.get(), AetherIIBlocks.SKYROOT_LOG.get());
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

        this.block(AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.fence(AetherIIBlocks.SKYROOT_FENCE.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.fenceGateBlock(AetherIIBlocks.SKYROOT_FENCE_GATE.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.buttonBlock(AetherIIBlocks.SKYROOT_BUTTON.get(), this.texture(this.name(AetherIIBlocks.SKYROOT_PLANKS.get()), "construction/"));
        this.pressurePlateBlock(AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get(), this.texture(this.name(AetherIIBlocks.SKYROOT_PLANKS.get()), "construction/"));
        this.stairs(AetherIIBlocks.SKYROOT_STAIRS.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.slab(AetherIIBlocks.SKYROOT_SLAB.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");

        this.block(AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.fence(AetherIIBlocks.GREATROOT_FENCE.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.fenceGateBlock(AetherIIBlocks.GREATROOT_FENCE_GATE.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.buttonBlock(AetherIIBlocks.GREATROOT_BUTTON.get(), this.texture(this.name(AetherIIBlocks.GREATROOT_PLANKS.get()), "construction/"));
        this.pressurePlateBlock(AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get(), this.texture(this.name(AetherIIBlocks.GREATROOT_PLANKS.get()), "construction/"));
        this.stairs(AetherIIBlocks.GREATROOT_STAIRS.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.slab(AetherIIBlocks.GREATROOT_SLAB.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");

        this.block(AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.fence(AetherIIBlocks.WISPROOT_FENCE.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.fenceGateBlock(AetherIIBlocks.WISPROOT_FENCE_GATE.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.buttonBlock(AetherIIBlocks.WISPROOT_BUTTON.get(), this.texture(this.name(AetherIIBlocks.WISPROOT_PLANKS.get()), "construction/"));
        this.pressurePlateBlock(AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get(), this.texture(this.name(AetherIIBlocks.WISPROOT_PLANKS.get()), "construction/"));
        this.stairs(AetherIIBlocks.WISPROOT_STAIRS.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.slab(AetherIIBlocks.WISPROOT_SLAB.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");

        this.block(AetherIIBlocks.HOLYSTONE_BRICKS.get(), "construction/");
    }
}
