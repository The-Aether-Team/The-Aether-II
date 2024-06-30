package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.furniture.OutpostCampfireBlock;
import com.aetherteam.aetherii.blockentity.OutpostCampfireBlockEntity;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class OutpostCampfireRenderer implements BlockEntityRenderer<OutpostCampfireBlockEntity> {
    public static final Material OUTPOST_CAMPFIRE_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "entity/tiles/furniture/outpost_campfire"));
    private final ModelPart campfire;

    public OutpostCampfireRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart part = context.bakeLayer(AetherIIModelLayers.OUTPOST_CAMPFIRE);
        this.campfire = part.getChild("campfire");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition campfire = partDefinition.addOrReplaceChild("campfire", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -0.25F, -14.0F, 28.0F, 1.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(24, 29).addBox(-7.0F, -3.0F, 14.0F, 14.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        campfire.addOrReplaceChild("rim_left_r1", CubeListBuilder.create().texOffs(56, 29).addBox(-5.0F, -3.0F, 14.75F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

        campfire.addOrReplaceChild("rim_left_r2", CubeListBuilder.create().texOffs(56, 29).addBox(-5.0F, -3.0F, 14.75F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

        campfire.addOrReplaceChild("rim_left_r3", CubeListBuilder.create().texOffs(56, 29).addBox(-5.0F, -3.0F, 14.75F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 44).addBox(1.5F, -3.2F, -7.0F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        campfire.addOrReplaceChild("rim_left_r4", CubeListBuilder.create().texOffs(56, 29).addBox(-5.0F, -3.0F, 14.75F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        campfire.addOrReplaceChild("rim_right_r1", CubeListBuilder.create().texOffs(24, 29).addBox(-7.0F, -3.0F, 14.0F, 14.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        campfire.addOrReplaceChild("rim_left_r5", CubeListBuilder.create().texOffs(24, 29).mirror().addBox(-7.0F, -3.0F, 14.0F, 14.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        campfire.addOrReplaceChild("log_6_r1", CubeListBuilder.create().texOffs(78, 44).addBox(3.0F, -3.2F, -5.0F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(30, 44).addBox(-11.5F, -3.2F, -2.0F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

        campfire.addOrReplaceChild("log_2_r1", CubeListBuilder.create().texOffs(56, 36).addBox(-8.0F, -4.0F, -1.0F, 11.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        campfire.addOrReplaceChild("log_1_r1", CubeListBuilder.create().texOffs(22, 34).addBox(-5.0F, -0.2F, -5.0F, 12.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, -1.2654F, 0.0F));

        campfire.addOrReplaceChild("rim_right_r2", CubeListBuilder.create().texOffs(24, 29).mirror().addBox(-7.0F, -3.0F, 14.0F, 14.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        campfire.addOrReplaceChild("log_5_r1", CubeListBuilder.create().texOffs(6, 44).addBox(-10.0F, -5.5F, -2.0F, 9.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 1.2654F, -1.5708F));

        return LayerDefinition.create(meshDefinition, 128, 64);
    }

    @Override
    public void render(OutpostCampfireBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState blockState = blockEntity.getBlockState();
        poseStack.translate(0, 1.5, 0);
        poseStack.mulPose(Axis.XN.rotationDegrees(180));
        Direction xDirection = blockState.getValue(OutpostCampfireBlock.X_DIRECTION_FROM_ORIGIN);
        Direction zDirection = blockState.getValue(OutpostCampfireBlock.Z_DIRECTION_FROM_ORIGIN);
        if (xDirection != Direction.EAST) {
            poseStack.translate(-xDirection.getStepX(), 0, 0);
        }
        if (zDirection != Direction.SOUTH) {
            poseStack.translate(0, 0, zDirection.getStepZ());
        }
        poseStack.mulPose(Axis.YP.rotationDegrees(blockState.getValue(OutpostCampfireBlock.HORIZONTAL_FACING).toYRot()));
        VertexConsumer vertexConsumer = OUTPOST_CAMPFIRE_TEXTURE.buffer(bufferSource, RenderType::entityCutout);
        this.campfire.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    @Override
    public AABB getRenderBoundingBox(OutpostCampfireBlockEntity blockEntity) {
        BlockState state = blockEntity.getBlockState();
        OutpostCampfireBlock block = (OutpostCampfireBlock) state.getBlock();
        BlockPos origin = block.locateOriginFrom(state, blockEntity.getBlockPos());
        BlockPos corner = origin.relative(state.getValue(OutpostCampfireBlock.X_DIRECTION_FROM_ORIGIN), block.getScale().getX() - 1).relative(Direction.UP, block.getScale().getY() - 1).relative(state.getValue(OutpostCampfireBlock.Z_DIRECTION_FROM_ORIGIN), block.getScale().getZ() - 1);
        return new AABB(origin.getX(), origin.getY(), origin.getZ(), corner.getX(), corner.getY(), corner.getZ()).inflate(1, 1, 1);
    }
}
