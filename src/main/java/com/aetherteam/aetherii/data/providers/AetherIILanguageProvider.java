package com.aetherteam.aetherii.data.providers;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public abstract class AetherIILanguageProvider extends LanguageProvider {
    public AetherIILanguageProvider(PackOutput output, String id) {
        super(output, id, "en_us");
    }

}
