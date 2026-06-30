package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;

public class AetherIIAtlases {
    public static final ResourceLocation MOA_FEATHER_ID = new ResourceLocation(AetherII.MODID, "moa_feather");
    public static final ResourceLocation MOA_EYES_ID = new ResourceLocation(AetherII.MODID, "moa_eyes");
    public static final ResourceLocation MOA_KERATIN_ID = new ResourceLocation(AetherII.MODID, "moa_keratin");
    public static final ResourceLocation MOA_FEATHER_SHEET = new ResourceLocation(AetherII.MODID, "textures/atlas/moa_feather.png");
    public static final ResourceLocation MOA_EYES_SHEET = new ResourceLocation(AetherII.MODID, "textures/atlas/moa_eyes.png");
    public static final ResourceLocation MOA_KERATIN_SHEET = new ResourceLocation(AetherII.MODID, "textures/atlas/moa_keratin.png");

    private static final Material[] ALKAHEST_PURIFIER_MATERIALS = new Material[] {
            getAlkahestPurifierMaterial("alkahest_purifier_0"),
            getAlkahestPurifierMaterial("alkahest_purifier_1"),
            getAlkahestPurifierMaterial("alkahest_purifier_2"),
            getAlkahestPurifierMaterial("alkahest_purifier_3"),
            getAlkahestPurifierMaterial("alkahest_purifier_4")
    };
    public static Material SKYROOT_CHEST_MATERIAL;
    public static Material SKYROOT_CHEST_LEFT_MATERIAL;
    public static Material SKYROOT_CHEST_RIGHT_MATERIAL;
    private static AetherIIAtlasHolder moaFeatherHolder;
    private static AetherIIAtlasHolder moaEyesHolder;
    private static AetherIIAtlasHolder moaKeratinHolder;

    public static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        moaFeatherHolder = new AetherIIAtlasHolder(textureManager, MOA_FEATHER_SHEET, MOA_FEATHER_ID);
        moaEyesHolder = new AetherIIAtlasHolder(textureManager, MOA_EYES_SHEET, MOA_EYES_ID);
        moaKeratinHolder = new AetherIIAtlasHolder(textureManager, MOA_KERATIN_SHEET, MOA_KERATIN_ID);
        event.registerReloadListener(moaFeatherHolder);
        event.registerReloadListener(moaEyesHolder);
        event.registerReloadListener(moaKeratinHolder);
    }

    public static void registerSkyrootChestAtlases() {
        SKYROOT_CHEST_MATERIAL = getChestMaterial("skyroot_chest");
        SKYROOT_CHEST_LEFT_MATERIAL = getChestMaterial("skyroot_chest_left");
        SKYROOT_CHEST_RIGHT_MATERIAL = getChestMaterial("skyroot_chest_right");
    }

    public static Material getChestMaterial(String chestName) {
        return new Material(Sheets.CHEST_SHEET, new ResourceLocation(AetherII.MODID, "entity/chest/" + chestName));
    }

    public static Material getAlkahestPurifierMaterial(int level) {
        return ALKAHEST_PURIFIER_MATERIALS[Math.max(0, Math.min(level, ALKAHEST_PURIFIER_MATERIALS.length - 1))];
    }

    public static TextureAtlasSprite getMoaFeatherSprite(ResourceLocation texture) {
        return moaFeatherHolder.get(texture);
    }

    public static TextureAtlasSprite getMoaEyesSprite(ResourceLocation texture) {
        return moaEyesHolder.get(texture);
    }

    public static TextureAtlasSprite getMoaKeratinSprite(ResourceLocation texture) {
        return moaKeratinHolder.get(texture);
    }

    private static Material getAlkahestPurifierMaterial(String name) {
        return new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(AetherII.MODID, "entity/alkahest_purifier/" + name));
    }

    private static class AetherIIAtlasHolder extends TextureAtlasHolder {
        private AetherIIAtlasHolder(TextureManager textureManager, ResourceLocation atlasTexture, ResourceLocation atlasInfoLocation) {
            super(textureManager, atlasTexture, atlasInfoLocation);
        }

        private TextureAtlasSprite get(ResourceLocation texture) {
            return this.getSprite(texture);
        }
    }
}
