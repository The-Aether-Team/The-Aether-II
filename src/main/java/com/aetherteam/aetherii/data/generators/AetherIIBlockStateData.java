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
    }
}
