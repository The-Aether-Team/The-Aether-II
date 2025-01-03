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
import net.neoforged.neoforge.client.model.generators.template.FaceRotation;
import org.w3c.dom.Text;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class AetherIIModelTemplates {
    public static final ModelTemplate TEMPLATE_CUTOUT_MIPPED_CUBE_ALL = ModelTemplates.CUBE_ALL.extend().renderType("cutout_mipped").build();
    public static final ModelTemplate TEMPLATE_TRANSLUCENT_CUBE = ModelTemplates.CUBE.extend().renderType("translucent").build();
    public static final ModelTemplate TEMPLATE_TRANSLUCENT_CUBE_ALL = ModelTemplates.CUBE_ALL.extend().renderType("translucent").build();
    public static final ModelTemplate TEMPLATE_CUTOUT_CROSS = ModelTemplates.CROSS.extend().renderType("cutout").build();
    public static final ModelTemplate TEMPLATE_CUTOUT_TINTED_CROSS = ModelTemplates.TINTED_CROSS.extend().renderType("cutout").build();
    public static final ModelTemplate TEMPLATE_CUTOUT_TINTED_FLOWERPOT_CROSS = ModelTemplates.TINTED_FLOWER_POT_CROSS.extend().renderType("cutout").build();
    public static final ModelTemplate EMPTY = ModelTemplates.create("block", TextureSlot.PARTICLE);
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
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.TOP_1).tintindex(0).cullface(Direction.UP))
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.SIDE_OVERLAY_1).tintindex(0).cullface(Direction.NORTH))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.SIDE_OVERLAY_1).tintindex(0).cullface(Direction.SOUTH))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.SIDE_OVERLAY_1).tintindex(0).cullface(Direction.WEST))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.SIDE_OVERLAY_1).tintindex(0).cullface(Direction.EAST))
            ).element((builder) -> builder
                    .from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.TOP_2).tintindex(1).cullface(Direction.UP))
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.SIDE_OVERLAY_2).tintindex(1).cullface(Direction.NORTH))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.SIDE_OVERLAY_2).tintindex(1).cullface(Direction.SOUTH))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.SIDE_OVERLAY_2).tintindex(1).cullface(Direction.WEST))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.SIDE_OVERLAY_2).tintindex(1).cullface(Direction.EAST))
            ).element((builder) -> builder
                    .from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.TOP_3).tintindex(2).cullface(Direction.UP))
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.SIDE_OVERLAY_3).tintindex(2).cullface(Direction.NORTH))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.SIDE_OVERLAY_3).tintindex(2).cullface(Direction.SOUTH))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.SIDE_OVERLAY_3).tintindex(2).cullface(Direction.WEST))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.SIDE_OVERLAY_3).tintindex(2).cullface(Direction.EAST))
            ).build();
    public static final ModelTemplate TINTED_TALL_GRASS = ModelTemplates.create("cross", TextureSlot.CROSS, TextureSlot.PARTICLE, AetherIITextureSlots.OVERLAY_1, AetherIITextureSlots.OVERLAY_2, AetherIITextureSlots.OVERLAY_3).extend()
            .renderType(ResourceLocation.withDefaultNamespace("cutout"))
            .element((builder) -> builder
                    .from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).axis(Direction.Axis.Y).angle(45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.CROSS).cullface(Direction.NORTH))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.CROSS).cullface(Direction.SOUTH))
            ).element((builder) -> builder
                    .from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).axis(Direction.Axis.Y).angle(45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.CROSS).cullface(Direction.WEST))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.CROSS).cullface(Direction.EAST))
            ).element((builder) -> builder
                    .from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).axis(Direction.Axis.Y).angle(45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_1).tintindex(0).cullface(Direction.NORTH))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_1).tintindex(0).cullface(Direction.SOUTH))
            ).element((builder) -> builder
                    .from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).axis(Direction.Axis.Y).angle(45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_1).tintindex(0).cullface(Direction.WEST))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_1).tintindex(0).cullface(Direction.EAST))
            ).element((builder) -> builder
                    .from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).axis(Direction.Axis.Y).angle(45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_2).tintindex(1).cullface(Direction.NORTH))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_2).tintindex(1).cullface(Direction.SOUTH))
            ).element((builder) -> builder
                    .from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).axis(Direction.Axis.Y).angle(45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_2).tintindex(1).cullface(Direction.WEST))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_2).tintindex(1).cullface(Direction.EAST))
            ).element((builder) -> builder
                    .from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).axis(Direction.Axis.Y).angle(45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_3).tintindex(2).cullface(Direction.NORTH))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_3).tintindex(2).cullface(Direction.SOUTH))
            ).element((builder) -> builder
                    .from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).axis(Direction.Axis.Y).angle(45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_3).tintindex(2).cullface(Direction.WEST))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_3).tintindex(2).cullface(Direction.EAST))
            ).build();
    public static final ModelTemplate PORTAL_NS = ModelTemplates.create("nether_portal_ns", "_ns", AetherIITextureSlots.PORTAL, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("translucent")).build();
    public static final ModelTemplate PORTAL_EW = ModelTemplates.create("nether_portal_ew", "_ew", AetherIITextureSlots.PORTAL, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("translucent")).build();
    public static final ModelTemplate THIN = ModelTemplates.create("thin_block", TextureSlot.ALL);
    public static final ModelTemplate DIRT_PATH = ModelTemplates.create("dirt_path", TextureSlot.BOTTOM, TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.SIDE);
    public static final ModelTemplate LEAVES = ModelTemplates.create("leaves", TextureSlot.ALL).extend().renderType(ResourceLocation.withDefaultNamespace("cutout_mipped")).build();
    public static final ModelTemplate VINE = ModelTemplates.create("vine", AetherIITextureSlots.VINE, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate TRANSLUCENT_INNER_FACES = ModelTemplates.create("cube", TextureSlot.PARTICLE, TextureSlot.NORTH, TextureSlot.SOUTH, TextureSlot.EAST, TextureSlot.WEST, TextureSlot.UP, TextureSlot.DOWN).extend()
            .renderType(ResourceLocation.withDefaultNamespace("translucent"))
            .element((builder) -> builder
                    .from(0.0F, 15.998F, 0.0F).to(16.0F, 16.0F, 16.0F)
                    .face(Direction.DOWN, (faceBuilder) -> faceBuilder.texture(TextureSlot.UP).uvs(0, 16, 16, 0).cullface(Direction.UP))
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.texture(TextureSlot.UP).uvs(0, 0, 16, 16).cullface(Direction.UP))
            ).element((builder) -> builder
                    .from(0.0F, 0.0F, 0.0F).to(16.0F, 0.002F, 16.0F)
                    .face(Direction.DOWN, (faceBuilder) -> faceBuilder.texture(TextureSlot.DOWN).uvs(0, 0, 16, 16).cullface(Direction.DOWN))
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.texture(TextureSlot.DOWN).uvs(0, 16, 16, 0).cullface(Direction.DOWN))
            ).element((builder) -> builder
                    .from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 0.002F)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.texture(TextureSlot.NORTH).uvs(0, 0, 16, 16).cullface(Direction.NORTH))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.texture(TextureSlot.NORTH).uvs(16, 0, 0, 16).cullface(Direction.NORTH))
            ).element((builder) -> builder
                    .from(0.0F, 0.0F, 15.998F).to(16.0F, 16.0F, 16.0F)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.texture(TextureSlot.SOUTH).uvs(16, 0, 0, 16).cullface(Direction.SOUTH))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.texture(TextureSlot.SOUTH).uvs(0, 0, 16, 16).cullface(Direction.SOUTH))
            ).element((builder) -> builder
                    .from(0.0F, 0.0F, 0.0F).to(0.002F, 16.0F, 16.0F)
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.texture(TextureSlot.WEST).uvs(0, 0, 16, 16).cullface(Direction.WEST))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.texture(TextureSlot.WEST).uvs(16, 0, 0, 16).cullface(Direction.WEST))
            ).element((builder) -> builder
                    .from(15.998F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.texture(TextureSlot.EAST).uvs(16, 0, 0, 16).cullface(Direction.EAST))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.texture(TextureSlot.EAST).uvs(0, 0, 16, 16).cullface(Direction.EAST))
            ).build();
    public static final ModelTemplate BUSH_BLOCK = ModelTemplates.create("cube", TextureSlot.TEXTURE, TextureSlot.PARTICLE, TextureSlot.CROSS).extend()
            .renderType(ResourceLocation.withDefaultNamespace("cutout_mipped"))
            .element((builder) -> builder
                    .from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                    .face(Direction.DOWN, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.TEXTURE))
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.TEXTURE))
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.TEXTURE))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.TEXTURE))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.TEXTURE))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.TEXTURE))
            ).element((builder) -> builder
                    .from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).axis(Direction.Axis.Y).angle(45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.CROSS))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.CROSS))
            ).element((builder) -> builder
                    .from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).axis(Direction.Axis.Y).angle(45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.CROSS))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.CROSS))
            ).build();
    public static final ModelTemplate LADDER = ModelTemplates.create("ladder", TextureSlot.TEXTURE, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate TWIG_1 = ModelTemplates.create("block", "_1", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.PARTICLE).extend()
            .element((builder) -> builder
                    .from(7.0F, 0.0F, 2.0F).to(9.0F, 2.0F, 13.0F)
                    .rotation((rotationBuilder) -> rotationBuilder.angle(0).axis(Direction.Axis.X).origin(9.0F, 0.0F, 7.0F))
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(7, 7, 9, 9).rotation(FaceRotation.UPSIDE_DOWN).texture(TextureSlot.TOP))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(2, 2, 4, 13).rotation(FaceRotation.CLOCKWISE_90).texture(TextureSlot.SIDE))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(7, 7, 9, 9).texture(TextureSlot.TOP))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(6, 2, 8, 13).rotation(FaceRotation.COUNTERCLOCKWISE_90).texture(TextureSlot.SIDE))
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(4, 2, 6, 13).texture(TextureSlot.SIDE))
                    .face(Direction.DOWN, (faceBuilder) -> faceBuilder.uvs(0, 2, 2, 13).rotation(FaceRotation.UPSIDE_DOWN).texture(TextureSlot.SIDE))
            ).build();
    public static final ModelTemplate TWIG_2 = ModelTemplates.create("block", "_2", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.PARTICLE).extend()
            .element((builder) -> builder
                    .from(11.0F, 0.0F, 2.0F).to(13.0F, 2.0F, 13.0F)
                    .rotation((rotationBuilder) -> rotationBuilder.angle(0).axis(Direction.Axis.X).origin(9.0F, 0.0F, 7.0F))
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(7, 7, 9, 9).rotation(FaceRotation.UPSIDE_DOWN).texture(TextureSlot.TOP))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(2, 2, 4, 13).rotation(FaceRotation.CLOCKWISE_90).texture(TextureSlot.SIDE))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(7, 7, 9, 9).texture(TextureSlot.TOP))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(6, 2, 8, 13).rotation(FaceRotation.COUNTERCLOCKWISE_90).texture(TextureSlot.SIDE))
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(4, 2, 6, 13).texture(TextureSlot.SIDE))
                    .face(Direction.DOWN, (faceBuilder) -> faceBuilder.uvs(0, 2, 2, 13).rotation(FaceRotation.UPSIDE_DOWN).texture(TextureSlot.SIDE))
            ).element((builder) -> builder
                    .from(2.0F, -1.0F, 9.0F).to(8.0F, 1.0F, 11.0F)
                    .rotation((rotationBuilder) -> rotationBuilder.angle(0).axis(Direction.Axis.Y).origin(8.0F, 0.0F, 8.0F))
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(2.0F, 4.0F, 4.0F, 8.0F).rotation(FaceRotation.CLOCKWISE_90).texture(TextureSlot.SIDE))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(7.0F, 7.0F, 9.0F, 9.0F).texture(TextureSlot.TOP))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(6.0F, 2.0F, 8.0F, 8.0F).rotation(FaceRotation.COUNTERCLOCKWISE_90).texture(TextureSlot.SIDE))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(7.0F, 7.0F, 9.0F, 9.0F).rotation(FaceRotation.UPSIDE_DOWN).texture(TextureSlot.TOP))
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(4.0F, 2.0F, 6.0F, 8.0F).rotation(FaceRotation.COUNTERCLOCKWISE_90).texture(TextureSlot.SIDE))
                    .face(Direction.DOWN, (faceBuilder) -> faceBuilder.uvs(0.0F, 2.0F, 2.0F, 8.0F).rotation(FaceRotation.COUNTERCLOCKWISE_90).texture(TextureSlot.SIDE))
            ).build();
    public static final ModelTemplate ROCK_1 = ModelTemplates.create("block", "_1", TextureSlot.TEXTURE, TextureSlot.PARTICLE).extend()
            .element((builder) -> builder
                    .from(5.0F, 0.0F, 5.0F).to(11.0F, 3.0F, 11.0F)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0.0F, 6.0F, 6.0F, 9.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(6.0F, 6.0F, 12.0F, 9.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0.0F, 6.0F, 6.0F, 9.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(6.0F, 6.0F, 12.0F, 9.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(6.0F, 0.0F, 12.0F, 6.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.DOWN, (faceBuilder) -> faceBuilder.uvs(6.0F, 9.0F, 12.0F, 15.0F).texture(TextureSlot.TEXTURE))
            ).build();
    public static final ModelTemplate ROCK_2 = ModelTemplates.create("block", "_2", TextureSlot.TEXTURE, TextureSlot.PARTICLE).extend()
            .element((builder) -> builder
                    .from(2.0F, 0.0F, 2.0F).to(8.0F, 3.0F, 8.0F)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0.0F, 6.0F, 6.0F, 9.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(6.0F, 6.0F, 12.0F, 9.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0.0F, 6.0F, 6.0F, 9.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(6.0F, 6.0F, 12.0F, 9.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(6.0F, 0.0F, 12.0F, 6.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.DOWN, (faceBuilder) -> faceBuilder.uvs(6.0F, 9.0F, 12.0F, 15.0F).texture(TextureSlot.TEXTURE))
            ).element((builder) -> builder
                    .from(10.0F, 0.0F, 9.0F).to(14.0F, 2.0F, 13.0F)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0.0F, 4.0F, 4.0F, 6.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(4.0F, 4.0F, 8.0F, 6.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0.0F, 4.0F, 4.0F, 6.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(4.0F, 4.0F, 8.0F, 6.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(4.0F, 0.0F, 8.0F, 4.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.DOWN, (faceBuilder) -> faceBuilder.uvs(4.0F, 8.0F, 8.0F, 12.0F).texture(TextureSlot.TEXTURE))
            ).build();
    public static final ModelTemplate ROCK_3 = ModelTemplates.create("block", "_3", TextureSlot.TEXTURE, TextureSlot.PARTICLE).extend()
            .element((builder) -> builder
                    .from(2.0F, 0.0F, 2.0F).to(8.0F, 3.0F, 8.0F)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0.0F, 6.0F, 6.0F, 9.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(6.0F, 6.0F, 12.0F, 9.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0.0F, 6.0F, 6.0F, 9.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(6.0F, 6.0F, 12.0F, 9.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(6.0F, 0.0F, 12.0F, 6.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.DOWN, (faceBuilder) -> faceBuilder.uvs(6.0F, 9.0F, 12.0F, 15.0F).texture(TextureSlot.TEXTURE))
            ).element((builder) -> builder
                    .from(10.0F, 0.0F, 6.0F).to(14.0F, 2.0F, 10.0F)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0.0F, 4.0F, 4.0F, 6.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(4.0F, 4.0F, 8.0F, 6.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0.0F, 4.0F, 4.0F, 6.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(4.0F, 4.0F, 8.0F, 6.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(4.0F, 0.0F, 8.0F, 4.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.DOWN, (faceBuilder) -> faceBuilder.uvs(4.0F, 8.0F, 8.0F, 12.0F).texture(TextureSlot.TEXTURE))
            ).element((builder) -> builder
                    .from(7.0F, 0.0F, 12.0F).to(9.0F, 1.0F, 14.0F)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0.0F, 2.0F, 2.0F, 3.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(2.0F, 2.0F, 4.0F, 3.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0.0F, 2.0F, 2.0F, 3.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(2.0F, 2.0F, 4.0F, 3.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(2.0F, 0.0F, 4.0F, 2.0F).texture(TextureSlot.TEXTURE))
                    .face(Direction.DOWN, (faceBuilder) -> faceBuilder.uvs(2.0F, 3.0F, 4.0F, 5.0F).texture(TextureSlot.TEXTURE))
            ).build();

    public static final ModelTemplate ASYMMETRICAL_CROSS_EVEN = create("asymmetrical_cross_even", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate ASYMMETRICAL_CROSS_EVEN_MIRRORED = create("asymmetrical_cross_even_mirrored", "_mirrored", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate ASYMMETRICAL_CROSS_ODD = create("asymmetrical_cross_odd", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate ASYMMETRICAL_CROSS_ODD_MIRRORED = create("asymmetrical_cross_odd_mirrored", "_mirrored", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate POTTED_ASYMMETRICAL_CROSS_EVEN = create("flower_pot_asymmetrical_cross_even", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate POTTED_ASYMMETRICAL_CROSS_ODD = create("flower_pot_asymmetrical_cross_odd", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate LILICHIME = create("template_lilichime", TextureSlot.PLANT).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate PLURACIAN = create("template_pluracian", TextureSlot.PLANT).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate POTTED_LILICHIME = create("flower_pot_lilichime", TextureSlot.PLANT).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate POTTED_PLURACIAN = create("flower_pot_pluracian", TextureSlot.PLANT).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate BRYALINN_MOSS_FLOWERS_1 = create("template_bryalinn_moss_flowers_1", "_1", TextureSlot.FLOWERBED, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate BRYALINN_MOSS_FLOWERS_2 = create("template_bryalinn_moss_flowers_2", "_2", TextureSlot.FLOWERBED, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate BRYALINN_MOSS_FLOWERS_3 = create("template_bryalinn_moss_flowers_3", "_3", TextureSlot.FLOWERBED, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate BRYALINN_MOSS_FLOWERS_4 = create("template_bryalinn_moss_flowers_4", "_4", TextureSlot.FLOWERBED, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate HOLPUPEA_1 = create("template_holpupea_1", "_1", TextureSlot.FLOWERBED, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate HOLPUPEA_2 = create("template_holpupea_2", "_2", TextureSlot.FLOWERBED, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate HOLPUPEA_3 = create("template_holpupea_3", "_3", TextureSlot.FLOWERBED, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate HOLPUPEA_4 = create("template_holpupea_4", "_4", TextureSlot.FLOWERBED, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate TARAHESP_FLOWERS_1 = create("template_tarahesp_flowers_1", "_1", AetherIITextureSlots.TARAHESP_FLOWERS_PURPLE, AetherIITextureSlots.TARAHESP_FLOWERS_WHITE, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate TARAHESP_FLOWERS_2 = create("template_tarahesp_flowers_2", "_2", AetherIITextureSlots.TARAHESP_FLOWERS_PURPLE, AetherIITextureSlots.TARAHESP_FLOWERS_WHITE, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate TARAHESP_FLOWERS_3 = create("template_tarahesp_flowers_3", "_3", AetherIITextureSlots.TARAHESP_FLOWERS_PURPLE, AetherIITextureSlots.TARAHESP_FLOWERS_WHITE, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate TARAHESP_FLOWERS_4 = create("template_tarahesp_flowers_4", "_4", AetherIITextureSlots.TARAHESP_FLOWERS_PURPLE, AetherIITextureSlots.TARAHESP_FLOWERS_WHITE, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate DOOR_BOTTOM_LEFT = create("door_bottom_left", "_bottom_left", TextureSlot.ALL, TextureSlot.BOTTOM, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate DOOR_BOTTOM_LEFT_OPEN = create("door_bottom_left_open", "_bottom_left_open", TextureSlot.ALL, TextureSlot.BOTTOM, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate DOOR_BOTTOM_RIGHT = create("door_bottom_right", "_bottom_right", TextureSlot.ALL, TextureSlot.BOTTOM, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate DOOR_BOTTOM_RIGHT_OPEN = create("door_bottom_right_open", "_bottom_right_open", TextureSlot.ALL, TextureSlot.BOTTOM, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate DOOR_TOP_LEFT = create("door_top_left", "_top_left", TextureSlot.TOP, TextureSlot.ALL, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate DOOR_TOP_LEFT_OPEN = create("door_top_left_open", "_top_left_open", TextureSlot.TOP, TextureSlot.ALL, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate DOOR_TOP_RIGHT = create("door_top_right", "_top_right", TextureSlot.TOP, TextureSlot.ALL, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate DOOR_TOP_RIGHT_OPEN = create("door_top_right_open", "_top_right_open", TextureSlot.TOP, TextureSlot.ALL, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate ORIENTABLE_SECRET_TRAPDOOR_TOP = create("template_orientable_secret_trapdoor_top", "_top", TextureSlot.TEXTURE);
    public static final ModelTemplate ORIENTABLE_SECRET_TRAPDOOR_BOTTOM = create("template_orientable_secret_trapdoor_bottom", "_bottom", TextureSlot.TEXTURE);
    public static final ModelTemplate ORIENTABLE_SECRET_TRAPDOOR_OPEN = create("template_orientable_secret_trapdoor_open", "_open", TextureSlot.TEXTURE);
    public static final ModelTemplate TALL_TORCH = create("template_tall_torch", TextureSlot.TORCH);
    public static final ModelTemplate TALL_WALL_TORCH = create("template_tall_wall_torch", TextureSlot.TORCH);
    public static final ModelTemplate ALTAR = create("template_altar", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate ARTISANS_BENCH = create("template_artisans_bench", TextureSlot.ALL, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();
    public static final ModelTemplate ARKENIUM_FORGE = create("template_arkenium_forge", TextureSlot.ALL, TextureSlot.PARTICLE).extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build();

    public static final ModelTemplate TRANSLUCENT_FLAT_ITEM = ModelTemplates.FLAT_ITEM.extend().renderType(ResourceLocation.withDefaultNamespace("translucent")).build();
    public static final ModelTemplate MEDIUM_CRYSTAL = ModelTemplates.createItem("medium_amethyst_bud", TextureSlot.LAYER0);
    public static final ModelTemplate LARGE_CRYSTAL = ModelTemplates.createItem("large_amethyst_bud", TextureSlot.LAYER0);
    public static final ModelTemplate FULL_CRYSTAL = ModelTemplates.createItem("amethyst_cluster", TextureSlot.LAYER0);
    public static final ModelTemplate POINTED_STONE = ModelTemplates.createItem("pointed_dripstone", TextureSlot.LAYER0);

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