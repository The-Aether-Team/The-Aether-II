package com.aetherteam.aetherii.data.resources.builders.models;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class AetherIIModelTemplates {
    public static final ModelTemplate TEMPLATE_CUTOUT_MIPPED_CUBE = ModelTemplates.CUBE_ALL.extend().renderType("cutout_mipped").build();
    public static final ModelTemplate TEMPLATE_TRANSLUCENT_CUBE = ModelTemplates.CUBE_ALL.extend().renderType("translucent").build();
    public static final ModelTemplate TEMPLATE_CUTOUT_CROSS = ModelTemplates.CROSS.extend().renderType("cutout").build();
    public static final ModelTemplate TINTED_GRASS = ModelTemplates.create("block", TextureSlot.BOTTOM, TextureSlot.PARTICLE, TextureSlot.TOP, AetherIITextureSlots.TOP_1, AetherIITextureSlots.TOP_2, AetherIITextureSlots.TOP_3, TextureSlot.SIDE, AetherIITextureSlots.SIDE_OVERLAY_1, AetherIITextureSlots.SIDE_OVERLAY_2, AetherIITextureSlots.SIDE_OVERLAY_3).extend()
            .renderType(ResourceLocation.withDefaultNamespace("cutout"))
            .element((builder) -> builder
                    .from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                    .face(Direction.DOWN, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.BOTTOM).cullface(Direction.DOWN))
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.TOP).tintindex(0).cullface(Direction.UP))
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.SIDE).cullface(Direction.NORTH))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.SIDE).cullface(Direction.SOUTH))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.SIDE).cullface(Direction.WEST))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.SIDE).cullface(Direction.EAST))
            ).element((builder) -> builder
                    .from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("top_1")).tintindex(0).cullface(Direction.UP))
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("side_overlay_1")).tintindex(0).cullface(Direction.NORTH))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("side_overlay_1")).tintindex(0).cullface(Direction.SOUTH))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("side_overlay_1")).tintindex(0).cullface(Direction.WEST))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("side_overlay_1")).tintindex(0).cullface(Direction.EAST))
            ).element((builder) -> builder
                    .from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("top_1")).tintindex(0).cullface(Direction.UP))
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("side_overlay_1")).tintindex(0).cullface(Direction.NORTH))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("side_overlay_1")).tintindex(0).cullface(Direction.SOUTH))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("side_overlay_1")).tintindex(0).cullface(Direction.WEST))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("side_overlay_1")).tintindex(0).cullface(Direction.EAST))
            ).element((builder) -> builder
                    .from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("top_2")).tintindex(1).cullface(Direction.UP))
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("side_overlay_2")).tintindex(1).cullface(Direction.NORTH))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("side_overlay_2")).tintindex(1).cullface(Direction.SOUTH))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("side_overlay_2")).tintindex(1).cullface(Direction.WEST))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("side_overlay_2")).tintindex(1).cullface(Direction.EAST))
            ).element((builder) -> builder
                    .from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("top_3")).tintindex(2).cullface(Direction.UP))
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("side_overlay_3")).tintindex(2).cullface(Direction.NORTH))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("side_overlay_3")).tintindex(2).cullface(Direction.SOUTH))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("side_overlay_3")).tintindex(2).cullface(Direction.WEST))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.create("side_overlay_3")).tintindex(2).cullface(Direction.EAST))
            ).build();
    public static final ModelTemplate THIN = ModelTemplates.create("thin_block", TextureSlot.ALL);
    public static final ModelTemplate DIRT_PATH = ModelTemplates.create("dirt_path", TextureSlot.BOTTOM, TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.SIDE);
    public static final ModelTemplate LEAVES = ModelTemplates.create("leaves", TextureSlot.ALL).extend().renderType(ResourceLocation.withDefaultNamespace("cutout_mipped")).build();
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
    public static final ModelTemplate ARKENIUM_FORGE = create("template_arkenium_forge", TextureSlot.ALL).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();;

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