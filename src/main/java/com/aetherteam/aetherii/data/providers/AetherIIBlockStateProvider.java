package com.aetherteam.aetherii.data.providers;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class AetherIIBlockStateProvider extends BlockStateProvider {
    public AetherIIBlockStateProvider(PackOutput output, String id, ExistingFileHelper helper) {
        super(output, id, helper);
    }
}
