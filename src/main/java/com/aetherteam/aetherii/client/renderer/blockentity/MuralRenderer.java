package com.aetherteam.aetherii.client.renderer.blockentity;

import java.util.EnumSet;
import java.util.Set;

import javax.annotation.Nullable;

import org.joml.Vector3f;

import com.aetherteam.aetherii.api.Mural;
import com.aetherteam.aetherii.block.dungeon.MuralBlock;
import com.aetherteam.aetherii.blockentity.MuralBlockEntity;
import com.aetherteam.aetherii.blockentity.MuralSection;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.data.resources.registries.AetherIIMurals;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class MuralRenderer implements BlockEntityRenderer<MuralBlockEntity> {
    private final ModelPart[][][][] frontSides;
    private final BlockRenderDispatcher blockRenderer;

    public MuralRenderer(BlockEntityRendererProvider.Context context) {
        this(context.getModelSet(), context.getBlockRenderDispatcher());
    }

    public MuralRenderer(EntityModelSet modelSet) {
        this(modelSet, null);
    }

    private MuralRenderer(EntityModelSet modelSet, @Nullable BlockRenderDispatcher blockRenderer) {
        this.frontSides = new ModelPart[Mural.MAX_SIZE][Mural.MAX_SIZE][][];
        this.blockRenderer = blockRenderer;
        for (int width = 1; width <= Mural.MAX_SIZE; width++) {
            for (int height = 1; height <= Mural.MAX_SIZE; height++) {
                var faces = this.frontSides[width - 1][height - 1] = new ModelPart[width][height];
                for (int offsetX = 0; offsetX < width; offsetX++) {
                    for (int offsetY = 0; offsetY < height; offsetY++) {
                        var faceModelPart = modelSet.bakeLayer(AetherIIModelLayers.getMuralFace(width, height, offsetX, offsetY));
                        faces[offsetX][offsetY] = faceModelPart.getChild("front");
                    }
                }
            }
        }
    }

    public static LayerDefinition createFaceLayer(int width, int height, int offsetX, int offsetY) {
        Mural.checkSize(width, height);
        Mural.checkOffset(width, height, offsetX, offsetY);
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();
        var cubeListBuilder = CubeListBuilder.create().texOffs(16 * offsetX, 16 * offsetY).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.0F, EnumSet.of(Direction.NORTH));
        partDefinition.addOrReplaceChild("front", cubeListBuilder, PartPose.offsetAndRotation(16.0F, 16.0F, 0.0F, 0.0F, 0.0F, (float) Math.PI));
        return LayerDefinition.create(meshDefinition, 16 * width, 16 * height);
    }
    
    @Override
    public void render(MuralBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 cameraPos) {
        poseStack.pushPose();
        var direction = blockEntity.getDirection();
        poseStack.translate(0.5, 0.0, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - direction.toYRot()));
        poseStack.translate(-0.5, 0.0, -0.5);
        var blockState = blockEntity.getBlockState();
        // System.out.println("after : packedLight = " + packedLight + ", packedOverlay = " + Integer.toHexString(packedOverlay));
        packedLight = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().relative(blockState.getValue(BlockStateProperties.HORIZONTAL_FACING)));
        // System.out.println("after : packedLight = " + packedLight + ", packedOverlay = " + Integer.toHexString(packedOverlay));
        this.render(poseStack, bufferSource, packedLight, packedOverlay, blockEntity.getMural().orElse(null), blockState.getValue(MuralBlock.X_OFFSET), blockState.getValue(MuralBlock.Y_OFFSET));
        poseStack.popPose();
    }

    public void renderInHand(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, @Nullable MuralSection muralSection) {
        Holder<Mural> mural;
        int offsetX, offsetY;
        if (muralSection == null) {
            mural = null;
            offsetX = offsetY = 0;
        } else {
            mural = muralSection.mural();
            offsetX = muralSection.offsetX();
            offsetY = muralSection.offsetY();
        }
        this.render(poseStack, bufferSource, packedLight, packedOverlay, mural, offsetX, offsetY);
    }

    private void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, @Nullable Holder<Mural> mural, int offsetX, int offsetY) {
        VertexConsumer buffer;
        if (mural != null) {
            var material = AetherIIAtlases.getMuralMaterial(mural.getKey());
            buffer = material.buffer(bufferSource, RenderType::entitySolid);
            
            this.getFacePart(mural.value(), offsetX, offsetY).render(poseStack, buffer, packedLight, packedOverlay);
        }
    }

    protected final ModelPart getFacePart(Mural mural, int offsetX, int offsetY) {
        return this.getFacePart(mural.width(), mural.height(), offsetX, offsetY);
    }

    protected ModelPart getFacePart(int muralWidth, int muralHeight, int offsetX, int offsetY) {
        return this.frontSides[muralWidth - 1][muralHeight - 1][offsetX][offsetY];
    }

    public void getExtents(Set<Vector3f> output) {
        var poseStack = new PoseStack();
        this.frontSides[0][0][0][0].getExtentsForGui(poseStack, output);
    }
}
