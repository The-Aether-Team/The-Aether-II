package com.aetherteam.aetherii.data.resources.builders.models;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;

public class AetherIITexturedModels extends TexturedModel {
    public static final Provider LEAVES = createDefault(TextureMapping::cube, ModelTemplates.CUBE_ALL);
    public static final Provider TINTED_LEAVES = createDefault(TextureMapping::cube, AetherIIModelTemplates.LEAVES);
    public static final Provider LEAF_LITTER_1 = createDefault(TextureMapping::defaultTexture, AetherIIModelTemplates.LEAF_LITTER_1);
    public static final Provider LEAF_LITTER_2 = createDefault(TextureMapping::defaultTexture, AetherIIModelTemplates.LEAF_LITTER_2);
    public static final Provider LEAF_LITTER_3 = createDefault(TextureMapping::defaultTexture, AetherIIModelTemplates.LEAF_LITTER_3);
    public static final Provider LEAF_LITTER_4 = createDefault(TextureMapping::defaultTexture, AetherIIModelTemplates.LEAF_LITTER_4);
    public static final Provider ASYMMETRICAL_CROSS_EVEN = createDefault(AetherIITextureMappings::asymmetricalCross, AetherIIModelTemplates.ASYMMETRICAL_CROSS_EVEN);
    public static final Provider ASYMMETRICAL_CROSS_EVEN_MIRRORED = createDefault(AetherIITextureMappings::asymmetricalCross, AetherIIModelTemplates.ASYMMETRICAL_CROSS_EVEN_MIRRORED);
    public static final Provider ASYMMETRICAL_CROSS_ODD = createDefault(AetherIITextureMappings::asymmetricalCross, AetherIIModelTemplates.ASYMMETRICAL_CROSS_ODD);
    public static final Provider ASYMMETRICAL_CROSS_ODD_MIRRORED = createDefault(AetherIITextureMappings::asymmetricalCross, AetherIIModelTemplates.ASYMMETRICAL_CROSS_ODD_MIRRORED);
    public static final Provider LILICHIME = createDefault(AetherIITextureMappings::lilichime, AetherIIModelTemplates.LILICHIME);
    public static final Provider PLURACIAN = createDefault(AetherIITextureMappings::pluracian, AetherIIModelTemplates.PLURACIAN);
    public static final Provider BRYALINN_MOSS_FLOWERS_1 = createDefault(AetherIITextureMappings::flowerbed, AetherIIModelTemplates.BRYALINN_MOSS_FLOWERS_1);
    public static final Provider BRYALINN_MOSS_FLOWERS_2 = createDefault(AetherIITextureMappings::flowerbed, AetherIIModelTemplates.BRYALINN_MOSS_FLOWERS_2);
    public static final Provider BRYALINN_MOSS_FLOWERS_3 = createDefault(AetherIITextureMappings::flowerbed, AetherIIModelTemplates.BRYALINN_MOSS_FLOWERS_3);
    public static final Provider BRYALINN_MOSS_FLOWERS_4 = createDefault(AetherIITextureMappings::flowerbed, AetherIIModelTemplates.BRYALINN_MOSS_FLOWERS_4);
    public static final Provider HOLPUPEA_1 = createDefault(AetherIITextureMappings::flowerbedAndStem, AetherIIModelTemplates.HOLPUPEA_1);
    public static final Provider HOLPUPEA_2 = createDefault(AetherIITextureMappings::flowerbedAndStem, AetherIIModelTemplates.HOLPUPEA_2);
    public static final Provider HOLPUPEA_3 = createDefault(AetherIITextureMappings::flowerbedAndStem, AetherIIModelTemplates.HOLPUPEA_3);
    public static final Provider HOLPUPEA_4 = createDefault(AetherIITextureMappings::flowerbedAndStem, AetherIIModelTemplates.HOLPUPEA_4);
    public static final Provider TARAHESP_FLOWERS_1 = createDefault(AetherIITextureMappings::tarahespFlowerbed, AetherIIModelTemplates.TARAHESP_FLOWERS_1);
    public static final Provider TARAHESP_FLOWERS_2 = createDefault(AetherIITextureMappings::tarahespFlowerbed, AetherIIModelTemplates.TARAHESP_FLOWERS_2);
    public static final Provider TARAHESP_FLOWERS_3 = createDefault(AetherIITextureMappings::tarahespFlowerbed, AetherIIModelTemplates.TARAHESP_FLOWERS_3);
    public static final Provider TARAHESP_FLOWERS_4 = createDefault(AetherIITextureMappings::tarahespFlowerbed, AetherIIModelTemplates.TARAHESP_FLOWERS_4);
    public static final Provider CARPET_CUTOUT = createDefault(TextureMapping::wool, AetherIIModelTemplates.CARPET_CUTOUT);
    public static final Provider MOSSY_CARPET_SIDE_CUTOUT = createDefault(TextureMapping::side, AetherIIModelTemplates.MOSSY_CARPET_SIDE_CUTOUT);
    public static final Provider ARKENIUM_LANTERN = createDefault(TextureMapping::lantern, AetherIIModelTemplates.ARKENIUM_LANTERN);
    public static final Provider ARKENIUM_LANTERN_LIT = createDefault(AetherIITextureMappings::lanternLit, AetherIIModelTemplates.ARKENIUM_LANTERN);
    public static final Provider HANGING_ARKENIUM_LANTERN = createDefault(TextureMapping::lantern, AetherIIModelTemplates.HANGING_ARKENIUM_LANTERN);
    public static final Provider HANGING_ARKENIUM_LANTERN_LIT = createDefault(AetherIITextureMappings::lanternLit, AetherIIModelTemplates.HANGING_ARKENIUM_LANTERN);
    public static final Provider RUSTIC_ARKENIUM_LANTERN = createDefault(TextureMapping::lantern, AetherIIModelTemplates.RUSTIC_ARKENIUM_LANTERN);
    public static final Provider RUSTIC_ARKENIUM_LANTERN_LIT = createDefault(AetherIITextureMappings::lanternLit, AetherIIModelTemplates.RUSTIC_ARKENIUM_LANTERN);
    public static final Provider HANGING_RUSTIC_ARKENIUM_LANTERN = createDefault(TextureMapping::lantern, AetherIIModelTemplates.HANGING_RUSTIC_ARKENIUM_LANTERN);
    public static final Provider HANGING_RUSTIC_ARKENIUM_LANTERN_LIT = createDefault(AetherIITextureMappings::lanternLit, AetherIIModelTemplates.HANGING_RUSTIC_ARKENIUM_LANTERN);
    public static final Provider ALTAR = createDefault(TextureMapping::cube, AetherIIModelTemplates.ALTAR);
    public static final Provider ARKENIUM_FORGE = createDefault(TextureMapping::cube, AetherIIModelTemplates.ARKENIUM_FORGE);

    public AetherIITexturedModels(TextureMapping mapping, ModelTemplate template) {
        super(mapping, template);
    }
}