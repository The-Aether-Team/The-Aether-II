package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;

public class AetherIIAtlases {
    public static Material SKYROOT_CHEST_MATERIAL;
    public static Material SKYROOT_CHEST_LEFT_MATERIAL;
    public static Material SKYROOT_CHEST_RIGHT_MATERIAL;

    public static void registerSkyrootChestAtlases() {
        SKYROOT_CHEST_MATERIAL = getChestMaterial("skyroot_chest");
        SKYROOT_CHEST_LEFT_MATERIAL = getChestMaterial("skyroot_chest_left");
        SKYROOT_CHEST_RIGHT_MATERIAL = getChestMaterial("skyroot_chest_right");
    }

    public static Material getChestMaterial(String chestName) {
        return new Material(Sheets.CHEST_SHEET, new ResourceLocation(AetherII.MODID, "entity/tiles/chest/" + chestName));
    }
}