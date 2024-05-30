package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class AetherModelLayers {
    public static final ModelLayerLocation PHYG = register("phyg");
    public static final ModelLayerLocation PHYG_WINGS = register("phyg", "wings");
    public static final ModelLayerLocation PHYG_SADDLE = register("phyg", "saddle");
    public static final ModelLayerLocation AERBUNNY = register("aerbunny");
    public static final ModelLayerLocation AERBUNNY_COLLAR = register("aerbunny", "collar");

    private static ModelLayerLocation register(String name) {
        return register(name, "main");
    }

    private static ModelLayerLocation register(String name, String type) {
        return register(new ResourceLocation(AetherII.MODID, name), type);
    }

    private static ModelLayerLocation register(ResourceLocation location, String type) {
        return new ModelLayerLocation(location, type);
    }
}
