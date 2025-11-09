package com.aetherteam.aetherii.client;

import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.Mural;
import com.aetherteam.aetherii.data.resources.registries.AetherIIMurals;

import net.minecraft.client.renderer.MaterialMapper;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterMaterialAtlasesEvent;

public class AetherIIAtlases {
    public static final ResourceLocation MOA_FEATHER_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/moa_feather.png");
    public static final ResourceLocation MOA_EYES_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/moa_eyes.png");
    public static final ResourceLocation MOA_KERATIN_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/moa_keratin.png");
    public static final ResourceLocation ARMOR_STYLES_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/armor_styles.png");
    public static final ResourceLocation ALKAHEST_PURIFIER_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/alkahest_purifier.png");
    public static final ResourceLocation MURAL_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/mural.png");

    public static final MaterialMapper ALKAHEST_PURIFIER_MAPPER = new MaterialMapper(ALKAHEST_PURIFIER_SHEET, "entity/alkahest_purifier");
    public static final MaterialMapper MURAL_MAPPER = new MaterialMapper(MURAL_SHEET, "entity/mural");

    public static final Material SKYROOT_CHEST_MATERIAL = Sheets.CHEST_MAPPER.apply(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "skyroot_chest"));
    public static final Material SKYROOT_CHEST_LEFT_MATERIAL = Sheets.CHEST_MAPPER.apply(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "skyroot_chest_left"));
    public static final Material SKYROOT_CHEST_RIGHT_MATERIAL = Sheets.CHEST_MAPPER.apply(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "skyroot_chest_right"));
    public static final Map<ResourceKey<Mural>, Material> MURAL_MATERIALS = AetherIIMurals.MURALS_REGISTRY
        .listElements()
        .collect(Collectors.toMap(Holder.Reference::key, entry -> MURAL_MAPPER.apply(entry.value().assetId())));
    public static final Material MURAL_BASE = MURAL_MAPPER.apply(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "mural_base"));
    public static final Material MURAL_SIDE = MURAL_MAPPER.apply(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "mural_side"));

    @Nullable
    public static Material getMuralMaterial(@Nullable ResourceKey<Mural> key) {
        return key == null ? null : MURAL_MATERIALS.get(key);
    }

    public static void registerAtlases(RegisterMaterialAtlasesEvent event) {
        event.register(MOA_FEATHER_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_feather"));
        event.register(MOA_EYES_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_eyes"));
        event.register(MOA_KERATIN_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_keratin"));
        event.register(ARMOR_STYLES_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_styles"));
        event.register(ALKAHEST_PURIFIER_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "alkahest_purifier"));
        event.register(MURAL_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "mural"));
    }
}