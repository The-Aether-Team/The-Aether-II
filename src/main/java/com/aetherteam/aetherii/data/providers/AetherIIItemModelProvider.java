package com.aetherteam.aetherii.data.providers;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class AetherIIItemModelProvider extends ItemModelProvider {
    public AetherIIItemModelProvider(PackOutput output, String id, ExistingFileHelper helper) {
        super(output, id, helper);
    }
}
