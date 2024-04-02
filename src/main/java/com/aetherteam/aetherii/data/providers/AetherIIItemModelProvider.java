package com.aetherteam.aetherii.data.providers;

import com.aetherteam.nitrogen.data.providers.NitrogenItemModelProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class AetherIIItemModelProvider extends NitrogenItemModelProvider {
    public AetherIIItemModelProvider(PackOutput output, String id, ExistingFileHelper helper) {
        super(output, id, helper);
    }
}
