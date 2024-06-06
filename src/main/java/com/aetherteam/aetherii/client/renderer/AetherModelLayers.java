package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class AetherModelLayers {
    public static final ModelLayerLocation PHYG = register("phyg");
    public static final ModelLayerLocation AERBUNNY = register("aerbunny");
    public static final ModelLayerLocation AERBUNNY_COLLAR = register("aerbunny", "collar");
    public static final ModelLayerLocation FLYING_COW = register("flying_cow");
    public static final ModelLayerLocation SHEEPUFF = register("sheepuff");
    public static final ModelLayerLocation KIRRID = register("kirrid");
    public static final ModelLayerLocation KIRRID_BABY = register("kirrid_baby");
    public static final ModelLayerLocation MOA = register("moa");
    public static final ModelLayerLocation MOA_BABY = register("moa_baby");

    public static final ModelLayerLocation ZEPHYR = register("zephyr");
    public static final ModelLayerLocation ZEPHYR_TRANSPARENCY = register("zephyr", "transparency");


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
