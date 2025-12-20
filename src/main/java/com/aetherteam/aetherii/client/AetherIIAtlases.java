package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.Mural;
import com.aetherteam.aetherii.blockentity.MuralSection;
import com.aetherteam.aetherii.client.renderer.blockentity.SentrySpawnerRenderer;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.minecraft.client.renderer.MaterialMapper;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.neoforged.neoforge.client.event.RegisterMaterialAtlasesEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AetherIIAtlases {
    public static final ResourceLocation MOA_FEATHER_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/moa_feather.png");
    public static final ResourceLocation MOA_EYES_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/moa_eyes.png");
    public static final ResourceLocation MOA_KERATIN_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/moa_keratin.png");
    public static final ResourceLocation ARMOR_STYLES_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/armor_styles.png");
    public static final ResourceLocation ALKAHEST_PURIFIER_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/alkahest_purifier.png");
    public static final ResourceLocation SENTRY_CRATE_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/sentry_crate.png");
    public static final ResourceLocation SENTRY_SPAWNER_SHEET = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/sentry_spawner.png");

    public static final MaterialMapper ALKAHEST_PURIFIER_MAPPER = new MaterialMapper(ALKAHEST_PURIFIER_SHEET, "entity/alkahest_purifier");
    public static final MaterialMapper SENTRY_CRATE_MAPPER = new MaterialMapper(SENTRY_CRATE_SHEET, "entity/sentry_crate");
    public static final MaterialMapper SENTRY_SPAWNER_MAPPER = new MaterialMapper(SENTRY_SPAWNER_SHEET, "entity/sentry_spawner");
    public static final MaterialMapper MURAL_MAPPER = new MaterialMapper(TextureAtlas.LOCATION_BLOCKS, "entity/mural");

    public static final Material SKYROOT_CHEST_MATERIAL = Sheets.CHEST_MAPPER.apply(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "skyroot_chest"));
    public static final Material SKYROOT_CHEST_LEFT_MATERIAL = Sheets.CHEST_MAPPER.apply(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "skyroot_chest_left"));
    public static final Material SKYROOT_CHEST_RIGHT_MATERIAL = Sheets.CHEST_MAPPER.apply(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "skyroot_chest_right"));

    public static int SENTRY_CRATE_FRAMES = 4;
    public static final Multimap<ChestType, Material> SENTRY_CRATE_MATERIALS = IntStream.rangeClosed(0, (ChestType.values().length * SENTRY_CRATE_FRAMES) - 1)
            .boxed()
            .collect(Multimaps.toMultimap(
                    i -> ChestType.values()[i / SENTRY_CRATE_FRAMES],
                    i -> SENTRY_CRATE_MAPPER.apply(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, ChestType.values()[i / SENTRY_CRATE_FRAMES].getSerializedName() + "/sentry_crate_" + (i % SENTRY_CRATE_FRAMES))),
                    () -> Multimaps.newMultimap(new HashMap<>(), ArrayList::new)
            ));
    public static final Material SENTRY_CRATE_SINGLE_EMISSIVE_LOCATION = AetherIIAtlases.SENTRY_CRATE_MAPPER.apply(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "single/sentry_crate_emissive"));
    public static final Material SENTRY_CRATE_LEFT_EMISSIVE_LOCATION = AetherIIAtlases.SENTRY_CRATE_MAPPER.apply(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "left/sentry_crate_emissive"));
    public static final Material SENTRY_CRATE_RIGHT_EMISSIVE_LOCATION = AetherIIAtlases.SENTRY_CRATE_MAPPER.apply(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "right/sentry_crate_emissive"));
    public static final Map<MuralSection, Material> MURAL_MATERIALS = Mural.getPieces().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> MURAL_MAPPER.apply(entry.getValue())));
    public static final Map<Integer, Material> SENTRY_SPAWNER_MATERIALS = SentrySpawnerRenderer.getPieces();

    public static void registerAtlases(RegisterMaterialAtlasesEvent event) {
        event.register(MOA_FEATHER_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_feather"));
        event.register(MOA_EYES_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_eyes"));
        event.register(MOA_KERATIN_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_keratin"));
        event.register(ARMOR_STYLES_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_styles"));
        event.register(ALKAHEST_PURIFIER_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "alkahest_purifier"));
        event.register(SENTRY_CRATE_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sentry_crate"));
        event.register(SENTRY_SPAWNER_SHEET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sentry_spawner"));
    }
}