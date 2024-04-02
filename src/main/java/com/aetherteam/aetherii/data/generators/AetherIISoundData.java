package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class AetherIISoundData extends SoundDefinitionsProvider {
    public AetherIISoundData(PackOutput output, ExistingFileHelper helper) {
        super(output, AetherII.MODID, helper);
    }

    @Override
    public void registerSounds() {

    }
}
