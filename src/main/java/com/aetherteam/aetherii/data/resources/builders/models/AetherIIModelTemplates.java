package com.aetherteam.aetherii.data.resources.builders.models;

import com.aetherteam.aetherii.AetherII;
import com.mojang.math.Quadrant;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public class AetherIIModelTemplates {
    public static final ModelTemplate EMPTY = ModelTemplates.create("block", TextureSlot.PARTICLE);
    public static final ModelTemplate TINTED_GRASS = ModelTemplates.create("block", TextureSlot.BOTTOM, TextureSlot.PARTICLE, TextureSlot.TOP, AetherIITextureSlots.TOP_1, AetherIITextureSlots.TOP_2, AetherIITextureSlots.TOP_3, TextureSlot.SIDE, AetherIITextureSlots.SIDE_OVERLAY_1, AetherIITextureSlots.SIDE_OVERLAY_2, AetherIITextureSlots.SIDE_OVERLAY_3).extend()
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
            .element((builder) -> builder
                    .from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).singleAxis(Direction.Axis.Y, 45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_1).tintindex(0))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_1).tintindex(0))
            ).element((builder) -> builder
                    .from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).singleAxis(Direction.Axis.Y, 45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_1).tintindex(0))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_1).tintindex(0))
            ).element((builder) -> builder
                    .from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).singleAxis(Direction.Axis.Y, 45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_2).tintindex(1))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_2).tintindex(1))
            ).element((builder) -> builder
                    .from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).singleAxis(Direction.Axis.Y, 45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_2).tintindex(1))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_2).tintindex(1))
            ).element((builder) -> builder
                    .from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).singleAxis(Direction.Axis.Y, 45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_3).tintindex(2))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_3).tintindex(2))
            ).element((builder) -> builder
                    .from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).singleAxis(Direction.Axis.Y, 45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_3).tintindex(2))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(AetherIITextureSlots.OVERLAY_3).tintindex(2))
            ).build();
    public static final ModelTemplate PORTAL_NS = ModelTemplates.create("nether_portal_ns", "_ns", AetherIITextureSlots.PORTAL, TextureSlot.PARTICLE);
    public static final ModelTemplate PORTAL_EW = ModelTemplates.create("nether_portal_ew", "_ew", AetherIITextureSlots.PORTAL, TextureSlot.PARTICLE);
    public static final ModelTemplate THIN = ModelTemplates.create("thin_block", TextureSlot.ALL);
    public static final ModelTemplate DIRT_PATH = ModelTemplates.create("dirt_path", TextureSlot.BOTTOM, TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.SIDE);
    public static final ModelTemplate LEAVES = ModelTemplates.create("leaves", TextureSlot.ALL);
    public static final ModelTemplate TRANSLUCENT_INNER_FACES = ModelTemplates.create("cube", TextureSlot.PARTICLE, TextureSlot.NORTH, TextureSlot.SOUTH, TextureSlot.EAST, TextureSlot.WEST, TextureSlot.UP, TextureSlot.DOWN).extend()
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
                    .rotation((rotation) -> rotation.origin(8, 8,8).singleAxis(Direction.Axis.Y, 45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.CROSS))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.CROSS))
            ).element((builder) -> builder
                    .from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F)
                    .rotation((rotation) -> rotation.origin(8, 8,8).singleAxis(Direction.Axis.Y, 45.0F).rescale(true))
                    .shade(false)
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.CROSS))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.CROSS))
            ).build();
    public static final ModelTemplate LADDER = ModelTemplates.create("ladder", TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    public static final ModelTemplate TWIG_1 = ModelTemplates.create("block", "_1", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.PARTICLE).extend()
            .element((builder) -> builder
                    .from(7.0F, 0.0F, 2.0F).to(9.0F, 2.0F, 13.0F)
                    .rotation((rotationBuilder) -> rotationBuilder.singleAxis(Direction.Axis.X, 0).origin(9.0F, 0.0F, 7.0F))
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(7, 7, 9, 9).rotation(Quadrant.R180).texture(TextureSlot.TOP))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(2, 2, 4, 13).rotation(Quadrant.R90).texture(TextureSlot.SIDE))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(7, 7, 9, 9).texture(TextureSlot.TOP))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(6, 2, 8, 13).rotation(Quadrant.R270).texture(TextureSlot.SIDE))
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(4, 2, 6, 13).texture(TextureSlot.SIDE))
                    .face(Direction.DOWN, (faceBuilder) -> faceBuilder.uvs(0, 2, 2, 13).rotation(Quadrant.R180).texture(TextureSlot.SIDE))
            ).build();
    public static final ModelTemplate TWIG_2 = ModelTemplates.create("block", "_2", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.PARTICLE).extend()
            .element((builder) -> builder
                    .from(11.0F, 0.0F, 2.0F).to(13.0F, 2.0F, 13.0F)
                    .rotation((rotationBuilder) -> rotationBuilder.singleAxis(Direction.Axis.X, 0).origin(9.0F, 0.0F, 7.0F))
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(7, 7, 9, 9).rotation(Quadrant.R180).texture(TextureSlot.TOP))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(2, 2, 4, 13).rotation(Quadrant.R90).texture(TextureSlot.SIDE))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(7, 7, 9, 9).texture(TextureSlot.TOP))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(6, 2, 8, 13).rotation(Quadrant.R270).texture(TextureSlot.SIDE))
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(4, 2, 6, 13).texture(TextureSlot.SIDE))
                    .face(Direction.DOWN, (faceBuilder) -> faceBuilder.uvs(0, 2, 2, 13).rotation(Quadrant.R180).texture(TextureSlot.SIDE))
            ).element((builder) -> builder
                    .from(2.0F, -1.0F, 9.0F).to(8.0F, 1.0F, 11.0F)
                    .rotation((rotationBuilder) -> rotationBuilder.singleAxis(Direction.Axis.Y, 0).origin(8.0F, 0.0F, 8.0F))
                    .face(Direction.NORTH, (faceBuilder) -> faceBuilder.uvs(2.0F, 4.0F, 4.0F, 8.0F).rotation(Quadrant.R90).texture(TextureSlot.SIDE))
                    .face(Direction.EAST, (faceBuilder) -> faceBuilder.uvs(7.0F, 7.0F, 9.0F, 9.0F).texture(TextureSlot.TOP))
                    .face(Direction.SOUTH, (faceBuilder) -> faceBuilder.uvs(6.0F, 2.0F, 8.0F, 8.0F).rotation(Quadrant.R270).texture(TextureSlot.SIDE))
                    .face(Direction.WEST, (faceBuilder) -> faceBuilder.uvs(7.0F, 7.0F, 9.0F, 9.0F).rotation(Quadrant.R180).texture(TextureSlot.TOP))
                    .face(Direction.UP, (faceBuilder) -> faceBuilder.uvs(4.0F, 2.0F, 6.0F, 8.0F).rotation(Quadrant.R270).texture(TextureSlot.SIDE))
                    .face(Direction.DOWN, (faceBuilder) -> faceBuilder.uvs(0.0F, 2.0F, 2.0F, 8.0F).rotation(Quadrant.R270).texture(TextureSlot.SIDE))
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
    public static final ModelTemplate POINTED_STONE_BLOCK = ModelTemplates.create("pointed_dripstone", TextureSlot.CROSS);

    public static final ModelTemplate TEMPLATE_EMISSIVE_SINGLE_FACE = AetherIIModelTemplates.create("emissive_single_face", TextureSlot.TEXTURE, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_CROSS = AetherIIModelTemplates.create("emissive_cross", TextureSlot.CROSS, TextureSlot.CROSS_EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_FLOWER_POT_CROSS = AetherIIModelTemplates.create("emissive_flower_pot_cross", TextureSlot.PLANT, TextureSlot.CROSS_EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_CUBE_ALL =  AetherIIModelTemplates.create("emissive_cube_all", TextureSlot.ALL, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_CUBE_COLUMN =  AetherIIModelTemplates.create("emissive_cube_column", TextureSlot.SIDE, TextureSlot.END, AetherIITextureSlots.EMISSIVE_SIDE, AetherIITextureSlots.EMISSIVE_END);
    public static final ModelTemplate TEMPLATE_EMISSIVE_CUBE_COLUMN_HORIZONTAL =  AetherIIModelTemplates.create("emissive_cube_column_horizontal", "_horizontal", TextureSlot.SIDE, TextureSlot.END, AetherIITextureSlots.EMISSIVE_SIDE, AetherIITextureSlots.EMISSIVE_END);
    public static final ModelTemplate TEMPLATE_EMISSIVE_STAIRS_STRAIGHT = create("emissive_stairs", TextureSlot.ALL, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_STAIRS_INNER = create("emissive_inner_stairs", "_inner", TextureSlot.ALL, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_STAIRS_OUTER = create("emissive_outer_stairs", "_outer", TextureSlot.ALL, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_SLAB_BOTTOM = create("emissive_slab", TextureSlot.ALL, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_SLAB_TOP = create("emissive_slab_top", "_top", TextureSlot.ALL, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate COLUMN_WALL_POST = create("template_column_wall_post", "_post", TextureSlot.END, TextureSlot.SIDE);
    public static final ModelTemplate COLUMN_WALL_LOW_SIDE = create("template_column_wall_side", "_side", TextureSlot.END, TextureSlot.SIDE);
    public static final ModelTemplate COLUMN_WALL_TALL_SIDE = create("template_column_wall_side_tall", "_side_tall", TextureSlot.END, TextureSlot.SIDE);
    public static final ModelTemplate EMISSIVE_COLUMN_WALL_POST = create("template_emissive_column_wall_post", "_post", TextureSlot.END, TextureSlot.SIDE, AetherIITextureSlots.EMISSIVE_END, AetherIITextureSlots.EMISSIVE_SIDE);
    public static final ModelTemplate EMISSIVE_COLUMN_WALL_LOW_SIDE = create("template_emissive_column_wall_side", "_side", TextureSlot.END, TextureSlot.SIDE, AetherIITextureSlots.EMISSIVE_END, AetherIITextureSlots.EMISSIVE_SIDE);
    public static final ModelTemplate EMISSIVE_COLUMN_WALL_TALL_SIDE = create("template_emissive_column_wall_side_tall", "_side_tall", TextureSlot.END, TextureSlot.SIDE, AetherIITextureSlots.EMISSIVE_END, AetherIITextureSlots.EMISSIVE_SIDE);
    public static final ModelTemplate EMISSIVE_COLUMN_WALL_INVENTORY = create("emissive_column_wall_inventory", "_inventory", TextureSlot.END, TextureSlot.SIDE, TextureSlot.WALL, AetherIITextureSlots.EMISSIVE_END, AetherIITextureSlots.EMISSIVE_SIDE, AetherIITextureSlots.EMISSIVE_WALL);
    public static final ModelTemplate TEMPLATE_EMISSIVE_BUTTON =  AetherIIModelTemplates.create("template_emissive_button", TextureSlot.TEXTURE, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_BUTTON_PRESSED =  AetherIIModelTemplates.create("template_emissive_button_pressed", "_pressed", TextureSlot.TEXTURE, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate TEMPLATE_EMISSIVE_BUTTON_INVENTORY =  AetherIIModelTemplates.create("template_emissive_button_inventory", "_inventory", TextureSlot.TEXTURE, AetherIITextureSlots.EMISSIVE);

    public static final ModelTemplate TRUNK_CENTER = AetherIIModelTemplates.create("template_trunk_center", "_center", TextureSlot.ALL);
    public static final ModelTemplate TRUNK_SIDE = AetherIIModelTemplates.create("template_trunk_side", "_side", TextureSlot.ALL);
    public static final ModelTemplate TRUNK_CORNER = AetherIIModelTemplates.create("template_trunk_corner", "_corner", TextureSlot.ALL);
    public static final ModelTemplate TRUNK_CENTER_TALL = AetherIIModelTemplates.create("template_trunk_center_tall", "_center_tall", TextureSlot.ALL);
    public static final ModelTemplate TRUNK_SIDE_TALL = AetherIIModelTemplates.create("template_trunk_side_tall", "_side_tall", TextureSlot.ALL);
    public static final ModelTemplate TRUNK_CORNER_TALL = AetherIIModelTemplates.create("template_trunk_corner_tall", "_corner_tall", TextureSlot.ALL);
    public static final ModelTemplate TRUNK_INVENTORY = AetherIIModelTemplates.create("template_trunk_inventory", "_inventory", TextureSlot.ALL);
    public static final ModelTemplate OVERLAID_LEAVES = create("template_overlaid_leaves", TextureSlot.BOTTOM, TextureSlot.SIDE);
    public static final ModelTemplate TINTED_OVERLAID_LEAVES = create("template_tinted_overlaid_leaves", TextureSlot.BOTTOM, TextureSlot.SIDE);
    public static final ModelTemplate OVERLAY = create("template_overlay", TextureSlot.TOP, TextureSlot.SIDE);
    public static final ModelTemplate MOSS_VINE = create("moss_vine", AetherIITextureSlots.VINE, TextureSlot.PARTICLE);
    public static final ModelTemplate ASYMMETRICAL_CROSS_EVEN = create("asymmetrical_cross_even", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER, TextureSlot.PARTICLE);
    public static final ModelTemplate ASYMMETRICAL_CROSS_EVEN_MIRRORED = create("asymmetrical_cross_even_mirrored", "_mirrored", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER, TextureSlot.PARTICLE);
    public static final ModelTemplate ASYMMETRICAL_CROSS_ODD = create("asymmetrical_cross_odd", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER, TextureSlot.PARTICLE);
    public static final ModelTemplate ASYMMETRICAL_CROSS_ODD_MIRRORED = create("asymmetrical_cross_odd_mirrored", "_mirrored", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER, TextureSlot.PARTICLE);
    public static final ModelTemplate POTTED_ASYMMETRICAL_CROSS_EVEN = create("flower_pot_asymmetrical_cross_even", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER);
    public static final ModelTemplate POTTED_ASYMMETRICAL_CROSS_ODD = create("flower_pot_asymmetrical_cross_odd", TextureSlot.CROSS, AetherIITextureSlots.CROSS_OTHER);
    public static final ModelTemplate LILICHIME = create("template_lilichime", TextureSlot.STEM, AetherIITextureSlots.PETALS, TextureSlot.PARTICLE);
    public static final ModelTemplate PLURACIAN = create("template_pluracian", TextureSlot.STEM, AetherIITextureSlots.LEAVES1, AetherIITextureSlots.LEAVES2, AetherIITextureSlots.PETAL_TOP, AetherIITextureSlots.PETAL_BOTTOM, TextureSlot.PARTICLE);
    public static final ModelTemplate POTTED_LILICHIME = create("flower_pot_lilichime", TextureSlot.STEM, AetherIITextureSlots.PETALS);
    public static final ModelTemplate POTTED_PLURACIAN = create("flower_pot_pluracian", TextureSlot.STEM, AetherIITextureSlots.LEAVES1, AetherIITextureSlots.LEAVES2, AetherIITextureSlots.PETAL_TOP, AetherIITextureSlots.PETAL_BOTTOM);
    public static final ModelTemplate POTTED_BUSH_BLOCK = create("flower_pot_bush_block", TextureSlot.STEM, AetherIITextureSlots.BUSH);
    public static final ModelTemplate BRYALINN_MOSS_FLOWERS_1 = create("template_bryalinn_moss_flowers_1", "_1", TextureSlot.FLOWERBED, TextureSlot.PARTICLE);
    public static final ModelTemplate BRYALINN_MOSS_FLOWERS_2 = create("template_bryalinn_moss_flowers_2", "_2", TextureSlot.FLOWERBED, TextureSlot.PARTICLE);
    public static final ModelTemplate BRYALINN_MOSS_FLOWERS_3 = create("template_bryalinn_moss_flowers_3", "_3", TextureSlot.FLOWERBED, TextureSlot.PARTICLE);
    public static final ModelTemplate BRYALINN_MOSS_FLOWERS_4 = create("template_bryalinn_moss_flowers_4", "_4", TextureSlot.FLOWERBED, TextureSlot.PARTICLE);
    public static final ModelTemplate HOLPUPEA_1 = create("template_holpupea_1", "_1", TextureSlot.FLOWERBED, TextureSlot.STEM, TextureSlot.PARTICLE);
    public static final ModelTemplate HOLPUPEA_2 = create("template_holpupea_2", "_2", TextureSlot.FLOWERBED, TextureSlot.STEM, TextureSlot.PARTICLE);
    public static final ModelTemplate HOLPUPEA_3 = create("template_holpupea_3", "_3", TextureSlot.FLOWERBED, TextureSlot.STEM, TextureSlot.PARTICLE);
    public static final ModelTemplate HOLPUPEA_4 = create("template_holpupea_4", "_4", TextureSlot.FLOWERBED, TextureSlot.STEM, TextureSlot.PARTICLE);
    public static final ModelTemplate TARAHESP_FLOWERS_1 = create("template_tarahesp_flowers_1", "_1", AetherIITextureSlots.TARAHESP_FLOWERS_PURPLE, AetherIITextureSlots.TARAHESP_FLOWERS_WHITE, TextureSlot.PARTICLE);
    public static final ModelTemplate TARAHESP_FLOWERS_2 = create("template_tarahesp_flowers_2", "_2", AetherIITextureSlots.TARAHESP_FLOWERS_PURPLE, AetherIITextureSlots.TARAHESP_FLOWERS_WHITE, TextureSlot.PARTICLE);
    public static final ModelTemplate TARAHESP_FLOWERS_3 = create("template_tarahesp_flowers_3", "_3", AetherIITextureSlots.TARAHESP_FLOWERS_PURPLE, AetherIITextureSlots.TARAHESP_FLOWERS_WHITE, TextureSlot.PARTICLE);
    public static final ModelTemplate TARAHESP_FLOWERS_4 = create("template_tarahesp_flowers_4", "_4", AetherIITextureSlots.TARAHESP_FLOWERS_PURPLE, AetherIITextureSlots.TARAHESP_FLOWERS_WHITE, TextureSlot.PARTICLE);
    public static final ModelTemplate AMBRELINN_MOSS_VINE = create("template_ambrelinn_moss_vine", AetherIITextureSlots.VINE, TextureSlot.PARTICLE);
    public static final ModelTemplate HANGING_UNDERGROWTH = create("template_hanging_undergrowth", AetherIITextureSlots.VINE, TextureSlot.PARTICLE);
    public static final ModelTemplate ROTSHROOM_CLUSTER = create("template_rotshroom_cluster", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate CARPET_CUTOUT = ModelTemplates.create("carpet", TextureSlot.WOOL);
    public static final ModelTemplate MOSSY_CARPET_SIDE_CUTOUT = ModelTemplates.create("mossy_carpet_side", TextureSlot.SIDE);
    public static final ModelTemplate DOOR_BOTTOM_LEFT = create("door_bottom_left", "_bottom_left", TextureSlot.FRONT, TextureSlot.SIDE, TextureSlot.END);
    public static final ModelTemplate DOOR_BOTTOM_LEFT_OPEN = create("door_bottom_left_open", "_bottom_left_open", TextureSlot.FRONT, TextureSlot.SIDE, TextureSlot.END);
    public static final ModelTemplate DOOR_BOTTOM_RIGHT = create("door_bottom_right", "_bottom_right", TextureSlot.FRONT, TextureSlot.SIDE, TextureSlot.END);
    public static final ModelTemplate DOOR_BOTTOM_RIGHT_OPEN = create("door_bottom_right_open", "_bottom_right_open", TextureSlot.FRONT, TextureSlot.SIDE, TextureSlot.END);
    public static final ModelTemplate DOOR_TOP_LEFT = create("door_top_left", "_top_left", TextureSlot.FRONT, TextureSlot.SIDE, TextureSlot.END);
    public static final ModelTemplate DOOR_TOP_LEFT_OPEN = create("door_top_left_open", "_top_left_open", TextureSlot.FRONT, TextureSlot.SIDE, TextureSlot.END);
    public static final ModelTemplate DOOR_TOP_RIGHT = create("door_top_right", "_top_right", TextureSlot.FRONT, TextureSlot.SIDE, TextureSlot.END);
    public static final ModelTemplate DOOR_TOP_RIGHT_OPEN = create("door_top_right_open", "_top_right_open", TextureSlot.FRONT, TextureSlot.SIDE, TextureSlot.END);
    public static final ModelTemplate ORIENTABLE_SECRET_TRAPDOOR_TOP = create("template_orientable_secret_trapdoor_top", "_top", TextureSlot.TEXTURE);
    public static final ModelTemplate ORIENTABLE_SECRET_TRAPDOOR_BOTTOM = create("template_orientable_secret_trapdoor_bottom", "_bottom", TextureSlot.TEXTURE);
    public static final ModelTemplate ORIENTABLE_SECRET_TRAPDOOR_OPEN = create("template_orientable_secret_trapdoor_open", "_open", TextureSlot.TEXTURE);
    public static final ModelTemplate ARKENIUM_LANTERN = create("template_arkenium_lantern", TextureSlot.LANTERN);
    public static final ModelTemplate HANGING_ARKENIUM_LANTERN = create("template_hanging_arkenium_lantern", "_hanging", TextureSlot.LANTERN);
    public static final ModelTemplate RUSTIC_ARKENIUM_LANTERN = create("template_rustic_arkenium_lantern", TextureSlot.LANTERN);
    public static final ModelTemplate HANGING_RUSTIC_ARKENIUM_LANTERN = create("template_hanging_rustic_arkenium_lantern", "_hanging", TextureSlot.LANTERN);
    public static final ModelTemplate TALL_TORCH = create("template_tall_torch", TextureSlot.TORCH);
    public static final ModelTemplate TALL_WALL_TORCH = create("template_tall_wall_torch", TextureSlot.TORCH);
    public static final ModelTemplate AMBER_HOURGLASS = create("template_amber_hourglass", TextureSlot.CROSS, TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM, TextureSlot.INNER_TOP, AetherIITextureSlots.INNER_BOTTOM);
    public static final ModelTemplate ALTAR = create("template_altar", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM, AetherIITextureSlots.BASE_TOP, AetherIITextureSlots.BASE_BOTTOM);
    public static final ModelTemplate ARTISANS_BENCH = create("template_artisans_bench", TextureSlot.NORTH, TextureSlot.SOUTH, TextureSlot.EAST, TextureSlot.WEST, TextureSlot.UP, TextureSlot.DOWN, TextureSlot.INSIDE, AetherIITextureSlots.SAW, TextureSlot.PARTICLE);
    public static final ModelTemplate ARKENIUM_FORGE = create("template_arkenium_forge", TextureSlot.SIDE, AetherIITextureSlots.BASE_TOP, AetherIITextureSlots.ANVIL_FRONT, AetherIITextureSlots.ANVIL_SIDE, AetherIITextureSlots.ANVIL_BOTTOM, TextureSlot.PARTICLE);
    public static final ModelTemplate ARILUM_LANTERN = create("template_arilum_lantern", TextureSlot.TEXTURE, TextureSlot.INSIDE);
    public static final ModelTemplate AMBROSIUM_CAMPFIRE_OFF = create("template_ambrosium_campfire_off", AetherIITextureSlots.STONE, AetherIITextureSlots.LOG, TextureSlot.PARTICLE);
    public static final ModelTemplate AMBROSIUM_CAMPFIRE = create("template_ambrosium_campfire", AetherIITextureSlots.STONE, AetherIITextureSlots.LOG, AetherIITextureSlots.LIT, TextureSlot.FIRE, TextureSlot.PARTICLE);
    public static final ModelTemplate VASE_INVENTORY = createItem("template_vase", TextureSlot.PARTICLE);
    public static final ModelTemplate SENTRY_TRAP =  AetherIIModelTemplates.create("template_sentry_trap", TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE, AetherIITextureSlots.EMISSIVE_TOP);
    public static final ModelTemplate PRAYER_CANDLE = create("template_prayer_candle", TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    public static final ModelTemplate GUARDIAN_PEW = create("template_guardian_pew", TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    public static final ModelTemplate GUARDIAN_DONATION_BOX = create("template_guardian_donation_box", TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    public static final ModelTemplate ANIMAL_STASH = create("template_animal_stash", TextureSlot.TEXTURE, TextureSlot.PARTICLE).extend().build();
    public static final ModelTemplate ANIMAL_STASH_OPEN = create("template_animal_stash_open", TextureSlot.TEXTURE, TextureSlot.PARTICLE).extend().build();

    public static final ModelTemplate DART_SHOOTER = createItem("handheld_dart_shooter", TextureSlot.LAYER0);
    public static final ModelTemplate DART_SHOOTER_TWO_LAYER = createItem("handheld_dart_shooter", TextureSlot.LAYER0, TextureSlot.LAYER1);
    public static final ModelTemplate USING_DART_SHOOTER_TWO_LAYER = createItem("using_dart_shooter", TextureSlot.LAYER0, TextureSlot.LAYER1);
    public static final ModelTemplate HAMMER_OF_DEMOLITION_HANDLE = createItem("template_hammer_of_demolition_handle", "_handle", TextureSlot.LAYER0, TextureSlot.LAYER1);
    public static final ModelTemplate HAMMER_OF_DEMOLITION_HEAD = createItem("template_hammer_of_demolition_head", "_head", TextureSlot.TEXTURE, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate HAMMER_OF_DEMOLITION_HEAD_READY = createItem("template_hammer_of_demolition_head_ready", "_head_ready", TextureSlot.TEXTURE, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate HAMMER_OF_DEMOLITION_HEAD_DEPLOYED = createItem("template_hammer_of_demolition_head_deployed", "_head_deployed", TextureSlot.TEXTURE, AetherIITextureSlots.EMISSIVE);
    public static final ModelTemplate ALKAHEST_PURIFIER_INVENTORY = createItem("template_alkahest_purifier", TextureSlot.PARTICLE);
    public static final ModelTemplate SENTRY_SPAWNER_INVENTORY = createItem("template_sentry_spawner", TextureSlot.PARTICLE);
    public static final ModelTemplate ABANDONED_BAG_INVENTORY = createItem("template_abandoned_bag", TextureSlot.PARTICLE);
    public static final ModelTemplate FUNGAL_CACHE_INVENTORY = createItem("template_fungal_cache", TextureSlot.PARTICLE);
    public static final ModelTemplate LOCKED_BLOCK_INVENTORY = createItem("locked_block_inventory", AetherIITextureSlots.FACE, AetherIITextureSlots.OVERLAY);

    public static final ModelTemplate TRANSLUCENT_FLAT_ITEM = ModelTemplates.FLAT_ITEM;
    public static final ModelTemplate MEDIUM_CRYSTAL = ModelTemplates.createItem("medium_amethyst_bud", TextureSlot.LAYER0);
    public static final ModelTemplate LARGE_CRYSTAL = ModelTemplates.createItem("large_amethyst_bud", TextureSlot.LAYER0);
    public static final ModelTemplate FULL_CRYSTAL = ModelTemplates.createItem("amethyst_cluster", TextureSlot.LAYER0);
    public static final ModelTemplate POINTED_STONE = ModelTemplates.createItem("pointed_dripstone", TextureSlot.LAYER0);

    public static final ModelTemplate AERCLOUD_GLIDER_CLOSED = createItem("aercloud_glider_closed", "_closed", AetherIITextureSlots.MAIN, AetherIITextureSlots.SIDE1, AetherIITextureSlots.SIDE2);
    public static final ModelTemplate AERCLOUD_GLIDER_OPEN = createItem("aercloud_glider_open", "_open", AetherIITextureSlots.MAIN, AetherIITextureSlots.SIDE1, AetherIITextureSlots.SIDE2);

    public static ModelTemplate create(TextureSlot... textureSlot) {
        return new ModelTemplate(Optional.empty(), Optional.empty(), textureSlot);
    }

    public static ModelTemplate create(String path, TextureSlot... textureSlot) {
        return new ModelTemplate(Optional.of(decorateBlockModelLocation(path)), Optional.empty(), textureSlot);
    }

    public static ModelTemplate create(String path, String suffix, TextureSlot... textureSlot) {
        return new ModelTemplate(Optional.of(decorateBlockModelLocation(path)), Optional.of(suffix), textureSlot);
    }

    public static ModelTemplate createItem(String path, TextureSlot... textureSlot) {
        return new ModelTemplate(Optional.of(decorateItemModelLocation(path)), Optional.empty(), textureSlot);
    }

    public static ModelTemplate createItem(String path, String suffix, TextureSlot... textureSlot) {
        return new ModelTemplate(Optional.of(decorateItemModelLocation(path)), Optional.of(suffix), textureSlot);
    }

    /**
     * Based on {@link ModelLocationUtils#decorateBlockModelLocation(String)}
     */
    public static Identifier decorateBlockModelLocation(String path) {
        return Identifier.fromNamespaceAndPath(AetherII.MODID, "block/" + path);
    }

    /**
     * Based on {@link ModelLocationUtils#decorateItemModelLocation(String)}
     */
    public static Identifier decorateItemModelLocation(String path) {
        return Identifier.fromNamespaceAndPath(AetherII.MODID, "item/" + path);
    }
}