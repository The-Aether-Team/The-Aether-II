package com.aetherteam.aetherii.data.resources.builders.models;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AetherIITexturedModels extends TexturedModel {
    public static final Provider LEAVES = createDefault(TextureMapping::cube, AetherIIModelTemplates.TEMPLATE_CUTOUT_MIPPED_CUBE_ALL);
    public static final Provider TINTED_LEAVES = createDefault(TextureMapping::cube, AetherIIModelTemplates.LEAVES);
    public static final Provider ASYMMETRICAL_CROSS_EVEN = createDefault(AetherIITextureMappings::asymmetricalCross, AetherIIModelTemplates.ASYMMETRICAL_CROSS_EVEN);
    public static final Provider ASYMMETRICAL_CROSS_EVEN_MIRRORED = createDefault(AetherIITextureMappings::asymmetricalCross, AetherIIModelTemplates.ASYMMETRICAL_CROSS_EVEN_MIRRORED);
    public static final Provider ASYMMETRICAL_CROSS_ODD = createDefault(AetherIITextureMappings::asymmetricalCross, AetherIIModelTemplates.ASYMMETRICAL_CROSS_ODD);
    public static final Provider ASYMMETRICAL_CROSS_ODD_MIRRORED = createDefault(AetherIITextureMappings::asymmetricalCross, AetherIIModelTemplates.ASYMMETRICAL_CROSS_ODD_MIRRORED);
    public static final Provider LILICHIME = createDefault(AetherIITextureMappings::itemPlant, AetherIIModelTemplates.LILICHIME);
    public static final Provider PLURACIAN = createDefault(AetherIITextureMappings::itemPlant, AetherIIModelTemplates.PLURACIAN);
    public static final Provider BRYALINN_MOSS_FLOWERS_1 = createDefault(AetherIITextureMappings::flowerbed, AetherIIModelTemplates.BRYALINN_MOSS_FLOWERS_1);
    public static final Provider BRYALINN_MOSS_FLOWERS_2 = createDefault(AetherIITextureMappings::flowerbed, AetherIIModelTemplates.BRYALINN_MOSS_FLOWERS_2);
    public static final Provider BRYALINN_MOSS_FLOWERS_3 = createDefault(AetherIITextureMappings::flowerbed, AetherIIModelTemplates.BRYALINN_MOSS_FLOWERS_3);
    public static final Provider BRYALINN_MOSS_FLOWERS_4 = createDefault(AetherIITextureMappings::flowerbed, AetherIIModelTemplates.BRYALINN_MOSS_FLOWERS_4);
    public static final Provider HOLPUPEA_1 = createDefault(AetherIITextureMappings::flowerbed, AetherIIModelTemplates.HOLPUPEA_1);
    public static final Provider HOLPUPEA_2 = createDefault(AetherIITextureMappings::flowerbed, AetherIIModelTemplates.HOLPUPEA_2);
    public static final Provider HOLPUPEA_3 = createDefault(AetherIITextureMappings::flowerbed, AetherIIModelTemplates.HOLPUPEA_3);
    public static final Provider HOLPUPEA_4 = createDefault(AetherIITextureMappings::flowerbed, AetherIIModelTemplates.HOLPUPEA_4);
    public static final Provider TARAHESP_FLOWERS_1 = createDefault(AetherIITextureMappings::tarahespFlowerbed, AetherIIModelTemplates.TARAHESP_FLOWERS_1);
    public static final Provider TARAHESP_FLOWERS_2 = createDefault(AetherIITextureMappings::tarahespFlowerbed, AetherIIModelTemplates.TARAHESP_FLOWERS_2);
    public static final Provider TARAHESP_FLOWERS_3 = createDefault(AetherIITextureMappings::tarahespFlowerbed, AetherIIModelTemplates.TARAHESP_FLOWERS_3);
    public static final Provider TARAHESP_FLOWERS_4 = createDefault(AetherIITextureMappings::tarahespFlowerbed, AetherIIModelTemplates.TARAHESP_FLOWERS_4);
    public static final Provider ALTAR = createDefault(TextureMapping::cube, AetherIIModelTemplates.ALTAR);
    public static final Provider ARKENIUM_FORGE = createDefault(TextureMapping::cube, AetherIIModelTemplates.ARKENIUM_FORGE);

    public AetherIITexturedModels(TextureMapping mapping, ModelTemplate template) {
        super(mapping, template);
    }
}