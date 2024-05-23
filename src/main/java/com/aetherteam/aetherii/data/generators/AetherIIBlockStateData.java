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
        this.block(AetherIIBlocks.HOLYSTONE.get(), "natural/");
    }
}
