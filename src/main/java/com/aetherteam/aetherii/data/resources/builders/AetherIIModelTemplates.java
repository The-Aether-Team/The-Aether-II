package com.aetherteam.aetherii.data.resources.builders;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class AetherIIModelTemplates {

    public AetherIIModelTemplates() {
    }

    public static final ModelTemplate LILICHIME = create("template_lilichime", TextureSlot.ALL);
    public static final ModelTemplate PLURACIAN = create("template_pluracian", TextureSlot.ALL);
    public static final ModelTemplate POTTED_LILICHIME = create("template_potted_lilichime", TextureSlot.PLANT, TextureSlot.TOP, TextureSlot.SIDE);
    public static final ModelTemplate POTTED_PLURACIAN = create("template_potted_pluracian", TextureSlot.PLANT, TextureSlot.TOP, TextureSlot.SIDE);
    public static final ModelTemplate BRYALINN_MOSS_FLOWERS_1 = create("template_bryalinn_moss_flowers_1", "_1", TextureSlot.FLOWERBED, TextureSlot.STEM);
    public static final ModelTemplate BRYALINN_MOSS_FLOWERS_2 = create("template_bryalinn_moss_flowers_2", "_2", TextureSlot.FLOWERBED, TextureSlot.STEM);
    public static final ModelTemplate BRYALINN_MOSS_FLOWERS_3 = create("template_bryalinn_moss_flowers_3", "_3", TextureSlot.FLOWERBED, TextureSlot.STEM);
    public static final ModelTemplate BRYALINN_MOSS_FLOWERS_4 = create("template_bryalinn_moss_flowers_4", "_4", TextureSlot.FLOWERBED, TextureSlot.STEM);
    public static final ModelTemplate HOLPUPEA_1 = create("template_holpupea_1", "_1", TextureSlot.FLOWERBED, TextureSlot.STEM);
    public static final ModelTemplate HOLPUPEA_2 = create("template_holpupea_2", "_2", TextureSlot.FLOWERBED, TextureSlot.STEM);
    public static final ModelTemplate HOLPUPEA_3 = create("template_holpupea_3", "_3", TextureSlot.FLOWERBED, TextureSlot.STEM);
    public static final ModelTemplate HOLPUPEA_4 = create("template_holpupea_4", "_4", TextureSlot.FLOWERBED, TextureSlot.STEM);
    public static final ModelTemplate TARAHESP_FLOWERS_1 = create("template_tarahesp_flowers_1", "_1", TextureSlot.FLOWERBED, TextureSlot.STEM);
    public static final ModelTemplate TARAHESP_FLOWERS_2 = create("template_tarahesp_flowers_2", "_2", TextureSlot.FLOWERBED, TextureSlot.STEM);
    public static final ModelTemplate TARAHESP_FLOWERS_3 = create("template_tarahesp_flowers_3", "_3", TextureSlot.FLOWERBED, TextureSlot.STEM);
    public static final ModelTemplate TARAHESP_FLOWERS_4 = create("template_tarahesp_flowers_4", "_4", TextureSlot.FLOWERBED, TextureSlot.STEM);
    public static final ModelTemplate TALL_TORCH = create("template_tall_torch", TextureSlot.TORCH);
    public static final ModelTemplate TALL_WALL_TORCH = create("template_tall_torch_wall", TextureSlot.TORCH);
    public static final ModelTemplate ALTAR = create("template_altar", TextureSlot.ALL);
    public static final ModelTemplate ARKENIUM_FORGE = create("template_arkenium_forge", TextureSlot.ALL);

    public static ModelTemplate create(TextureSlot... textureSlot) {
        return new ModelTemplate(Optional.empty(), Optional.empty(), textureSlot);
    }

    public static ModelTemplate create(String path, TextureSlot... textureSlot) {
        return new ModelTemplate(Optional.of(decorateBlockModelLocation(path)), Optional.empty(), textureSlot);
    }

    public static ModelTemplate create(String path, String suffix, TextureSlot... textureSlot) {
        return new ModelTemplate(Optional.of(decorateBlockModelLocation(path)), Optional.of(suffix), textureSlot);
    }

    /**
     * Based on {@link ModelTemplate#getDefaultModelLocation(Block)}
     */
    public static ResourceLocation decorateBlockModelLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(AetherII.MODID, path).withPrefix("block/");
    }
}