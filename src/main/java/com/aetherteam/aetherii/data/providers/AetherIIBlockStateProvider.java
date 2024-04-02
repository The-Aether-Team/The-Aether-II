package com.aetherteam.aetherii.data.providers;

import com.aetherteam.nitrogen.data.providers.NitrogenBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class AetherIIBlockStateProvider extends NitrogenBlockStateProvider {
    public AetherIIBlockStateProvider(PackOutput output, String id, ExistingFileHelper helper) {
        super(output, id, helper);
    }
}
