package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.MoaRenderer;
import com.aetherteam.aetherii.item.components.ArmorStyle;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterMaterialAtlasesEvent;

public class AetherIIAtlases {
    public static final ResourceLocation MOA_FEATHER_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/moa_feather.png");
    public static final ResourceLocation MOA_EYES_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/moa_eyes.png");
    public static final ResourceLocation MOA_KERATIN_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/moa_keratin.png");
    public static final ResourceLocation ARMOR_STYLES_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/armor_styles.png");

    public static Material SKYROOT_CHEST_MATERIAL;
    public static Material SKYROOT_CHEST_LEFT_MATERIAL;
    public static Material SKYROOT_CHEST_RIGHT_MATERIAL;

    public static void registerSkyrootChestAtlases() {
        SKYROOT_CHEST_MATERIAL = getChestMaterial("skyroot_chest");
        SKYROOT_CHEST_LEFT_MATERIAL = getChestMaterial("skyroot_chest_left");
        SKYROOT_CHEST_RIGHT_MATERIAL = getChestMaterial("skyroot_chest_right");
    }

    public static Material getChestMaterial(String chestName) {
        return new Material(Sheets.CHEST_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "entity/chest/" + chestName));
    }

    public static void registerAtlases(RegisterMaterialAtlasesEvent event) {
        event.register(MOA_FEATHER_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_feather"));
        event.register(MOA_EYES_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_eyes"));
        event.register(MOA_KERATIN_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_keratin"));
        event.register(ARMOR_STYLES_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_styles"));
    }
}