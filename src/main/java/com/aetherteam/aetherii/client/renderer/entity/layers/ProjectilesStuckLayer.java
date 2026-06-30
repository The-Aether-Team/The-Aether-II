package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.List;

public class ProjectilesStuckLayer<T extends Player, M extends PlayerModel<T>> extends RenderLayer<T, M> {
    public ProjectilesStuckLayer(LivingEntityRenderer<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        List<EntityType<?>> projectiles = AetherIIDataAttachments.get(player, AetherIIDataAttachments.PLAYER).getStuckProjectiles();
        if (!projectiles.isEmpty()) {
            RandomSource random = RandomSource.create(player.getId());
            for (EntityType<?> type : projectiles) {
                poseStack.pushPose();
                ModelPart part = this.getParentModel().getRandomModelPart(random);
                ModelPart.Cube cube = part.getRandomCube(random);
                part.translateAndRotate(poseStack);
                float x = random.nextFloat();
                float y = random.nextFloat();
                float z = random.nextFloat();

                int face = random.nextInt(3);
                if (face == 0) {
                    x = snapToFace(x);
                } else if (face == 1) {
                    y = snapToFace(y);
                } else {
                    z = snapToFace(z);
                }

                poseStack.translate(Mth.lerp(x, cube.minX, cube.maxX) / 16.0F, Mth.lerp(y, cube.minY, cube.maxY) / 16.0F, Mth.lerp(z, cube.minZ, cube.maxZ) / 16.0F);
                this.renderStuckProjectile(poseStack, bufferSource, packedLight, type, -(x * 2.0F - 1.0F), -(y * 2.0F - 1.0F), -(z * 2.0F - 1.0F));
                poseStack.popPose();
            }
        }
    }

    private void renderStuckProjectile(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, EntityType<?> type, float x, float y, float z) {
        ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(type);
        ResourceLocation texture = new ResourceLocation(key.getNamespace(), "textures/entity/projectile/" + key.getPath() + ".png");
        ResourceLocation emissive = type.builtInRegistryHolder().is(AetherIITags.EntityTypes.STICKABLE_PROJECTILES_EMISSIVE)
                ? new ResourceLocation(key.getNamespace(), "textures/entity/projectile/" + key.getPath() + "_emissive.png")
                : null;

        float horizontal = Mth.sqrt(x * x + z * z);
        float yRot = (float) (Math.atan2(x, z) * 180.0F / Math.PI);
        float zRot = (float) (Math.atan2(y, horizontal) * 180.0F / Math.PI);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(zRot));
        this.renderArrowGeometry(poseStack, bufferSource.getBuffer(RenderType.entityCutout(texture)), packedLight);
        if (emissive != null) {
            this.renderArrowGeometry(poseStack, bufferSource.getBuffer(RenderType.eyes(emissive)), packedLight);
        }
    }

    private void renderArrowGeometry(PoseStack poseStack, VertexConsumer consumer, int packedLight) {
        poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
        poseStack.scale(0.05625F, 0.05625F, 0.05625F);
        poseStack.translate(-4.0F, 0.0F, 0.0F);
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        this.vertex(matrix4f, matrix3f, consumer, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, packedLight);
        this.vertex(matrix4f, matrix3f, consumer, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, packedLight);
        this.vertex(matrix4f, matrix3f, consumer, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, packedLight);
        this.vertex(matrix4f, matrix3f, consumer, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, packedLight);
        this.vertex(matrix4f, matrix3f, consumer, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, packedLight);
        this.vertex(matrix4f, matrix3f, consumer, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, packedLight);
        this.vertex(matrix4f, matrix3f, consumer, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, packedLight);
        this.vertex(matrix4f, matrix3f, consumer, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, packedLight);

        for (int i = 0; i < 4; i++) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            this.vertex(matrix4f, matrix3f, consumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, packedLight);
            this.vertex(matrix4f, matrix3f, consumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, packedLight);
            this.vertex(matrix4f, matrix3f, consumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, packedLight);
            this.vertex(matrix4f, matrix3f, consumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, packedLight);
        }
    }

    private void vertex(Matrix4f pose, Matrix3f normal, VertexConsumer consumer, int x, int y, int z, float u, float v, int normalX, int normalY, int normalZ, int packedLight) {
        consumer.vertex(pose, x, y, z)
                .color(255, 255, 255, 255)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normal, normalX, normalZ, normalY)
                .endVertex();
    }

    private static float snapToFace(float value) {
        return value > 0.5F ? 1.0F : 0.5F;
    }
}
