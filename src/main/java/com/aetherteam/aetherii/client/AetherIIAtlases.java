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
import net.minecraft.client.resources.model.AtlasManager;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.neoforged.neoforge.client.event.RegisterTextureAtlasesEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AetherIIAtlases {
    public static final Identifier MOA_FEATHER_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "moa_feather");
    public static final Identifier MOA_EYES_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "moa_eyes");
    public static final Identifier MOA_KERATIN_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "moa_keratin");
    public static final Identifier ARMOR_STYLES_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "armor_styles");
    public static final Identifier ALKAHEST_PURIFIER_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "alkahest_purifier");
    public static final Identifier SENTRY_CRATE_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "sentry_crate");
    public static final Identifier SENTRY_SPAWNER_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "sentry_spawner");

    public static final Identifier MOA_FEATHER_SHEET = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/moa_feather.png");
    public static final Identifier MOA_EYES_SHEET = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/moa_eyes.png");
    public static final Identifier MOA_KERATIN_SHEET = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/moa_keratin.png");
    public static final Identifier ARMOR_STYLES_SHEET = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/armor_styles.png");
    public static final Identifier ALKAHEST_PURIFIER_SHEET = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/alkahest_purifier.png");
    public static final Identifier SENTRY_CRATE_SHEET = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/sentry_crate.png");
    public static final Identifier SENTRY_SPAWNER_SHEET = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/atlas/sentry_spawner.png");

    public static final MaterialMapper ALKAHEST_PURIFIER_MAPPER = new MaterialMapper(ALKAHEST_PURIFIER_SHEET, "entity/alkahest_purifier");
    public static final MaterialMapper SENTRY_CRATE_MAPPER = new MaterialMapper(SENTRY_CRATE_SHEET, "entity/sentry_crate");
    public static final MaterialMapper SENTRY_SPAWNER_MAPPER = new MaterialMapper(SENTRY_SPAWNER_SHEET, "entity/sentry_spawner");
    public static final MaterialMapper MURAL_MAPPER = new MaterialMapper(TextureAtlas.LOCATION_BLOCKS, "entity/mural");

    public static final Material SKYROOT_CHEST_MATERIAL = Sheets.CHEST_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, "skyroot_chest"));
    public static final Material SKYROOT_CHEST_LEFT_MATERIAL = Sheets.CHEST_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, "skyroot_chest_left"));
    public static final Material SKYROOT_CHEST_RIGHT_MATERIAL = Sheets.CHEST_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, "skyroot_chest_right"));

    public static int SENTRY_CRATE_FRAMES = 4;
    public static final Multimap<ChestType, Material> SENTRY_CRATE_MATERIALS = IntStream.rangeClosed(0, (ChestType.values().length * SENTRY_CRATE_FRAMES) - 1)
            .boxed()
            .collect(Multimaps.toMultimap(
                    i -> ChestType.values()[i / SENTRY_CRATE_FRAMES],
                    i -> SENTRY_CRATE_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, ChestType.values()[i / SENTRY_CRATE_FRAMES].getSerializedName() + "/sentry_crate_" + (i % SENTRY_CRATE_FRAMES))),
                    () -> Multimaps.newMultimap(new HashMap<>(), ArrayList::new)
            ));
    public static final Material SENTRY_CRATE_SINGLE_EMISSIVE_LOCATION = AetherIIAtlases.SENTRY_CRATE_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, "single/sentry_crate_emissive"));
    public static final Material SENTRY_CRATE_LEFT_EMISSIVE_LOCATION = AetherIIAtlases.SENTRY_CRATE_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, "left/sentry_crate_emissive"));
    public static final Material SENTRY_CRATE_RIGHT_EMISSIVE_LOCATION = AetherIIAtlases.SENTRY_CRATE_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, "right/sentry_crate_emissive"));
    public static final Map<MuralSection, Material> MURAL_MATERIALS = Mural.getPieces().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> MURAL_MAPPER.apply(entry.getValue())));
    public static final Map<Integer, Material> SENTRY_SPAWNER_MATERIALS = SentrySpawnerRenderer.getFrames();

    public static void registerAtlases(RegisterTextureAtlasesEvent event) {
        event.register(new AtlasManager.AtlasConfig(MOA_FEATHER_SHEET, MOA_FEATHER_ID, false));
        event.register(new AtlasManager.AtlasConfig(MOA_EYES_SHEET, MOA_EYES_ID, false));
        event.register(new AtlasManager.AtlasConfig(MOA_KERATIN_SHEET, MOA_KERATIN_ID, false));
        event.register(new AtlasManager.AtlasConfig(ARMOR_STYLES_SHEET, ARMOR_STYLES_ID, false));
        event.register(new AtlasManager.AtlasConfig(ALKAHEST_PURIFIER_SHEET, ALKAHEST_PURIFIER_ID, false));
        event.register(new AtlasManager.AtlasConfig(SENTRY_CRATE_SHEET, SENTRY_CRATE_ID, false));
        event.register(new AtlasManager.AtlasConfig(SENTRY_SPAWNER_SHEET, SENTRY_SPAWNER_ID, false));
    }
}