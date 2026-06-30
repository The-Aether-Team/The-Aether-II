package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.resources.ResourceLocation;

public final class AetherIIEquipmentAssets {
    public static final ResourceLocation BEAST_PELT = create("beast_pelt");
    public static final ResourceLocation BURRUKAI_PLATE = create("burrukai_plate");
    public static final ResourceLocation ZANITE = create("zanite");
    public static final ResourceLocation ARKENIUM = create("arkenium");
    public static final ResourceLocation GRAVITITE = create("gravitite");
    public static final ResourceLocation SENTRY = create("sentry");
    public static final ResourceLocation NEPTUNE = create("neptune");

    private AetherIIEquipmentAssets() {
    }

    private static ResourceLocation create(String name) {
        return new ResourceLocation(AetherII.MODID, name);
    }
}
