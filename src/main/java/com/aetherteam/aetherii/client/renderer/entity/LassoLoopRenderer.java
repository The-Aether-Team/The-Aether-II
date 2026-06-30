package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.projectile.LassoLoop;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class LassoLoopRenderer extends EntityRenderer<LassoLoop> {
    private static final int LEASH_RENDER_STEPS = 24;
    private static final ResourceLocation PROJECTILE_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/projectile/lasso_loop.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(PROJECTILE_TEXTURE);

    public LassoLoopRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(LassoLoop entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        PoseStack.Pose pose = poseStack.last();
        VertexConsumer consumer = buffer.getBuffer(RENDER_TYPE);
        vertex(consumer, pose, packedLight, 0.0F, 0, 0, 1);
        vertex(consumer, pose, packedLight, 1.0F, 0, 1, 1);
        vertex(consumer, pose, packedLight, 1.0F, 1, 1, 0);
        vertex(consumer, pose, packedLight, 0.0F, 1, 0, 0);
        poseStack.popPose();
        if (entity.getOwner() != null) {
            this.renderLeash(entity, partialTick, poseStack, buffer, entity.getOwner());
        }
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public boolean shouldRender(LassoLoop entity, Frustum camera, double camX, double camY, double camZ) {
        if (super.shouldRender(entity, camera, camX, camY, camZ)) {
            return true;
        }
        Entity owner = entity.getOwner();
        return owner != null && camera.isVisible(owner.getBoundingBoxForCulling());
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float x, int y, int u, int v) {
        consumer.vertex(pose.pose(), x - 0.5F, (float) y - 0.25F, 0.0F)
            .color(255, 255, 255, 255)
            .uv((float) u, (float) v)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(packedLight)
            .normal(pose.normal(), 0.0F, 1.0F, 0.0F)
            .endVertex();
    }

    private void renderLeash(LassoLoop entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, Entity owner) {
        poseStack.pushPose();
        Vec3 ownerPos = owner.getRopeHoldPosition(partialTick);
        Vec3 leashOffset = entity.getLeashOffset(partialTick);
        Vec3 entityPos = entity.getPosition(partialTick).add(leashOffset);
        poseStack.translate(leashOffset.x, leashOffset.y, leashOffset.z);
        float x = (float) (ownerPos.x - entityPos.x);
        float y = (float) (ownerPos.y - entityPos.y);
        float z = (float) (ownerPos.z - entityPos.z);
        VertexConsumer consumer = buffer.getBuffer(RenderType.leash());
        Matrix4f matrix = poseStack.last().pose();
        float width = Mth.invSqrt(x * x + z * z) * 0.025F / 2.0F;
        float zWidth = z * width;
        float xWidth = x * width;
        BlockPos entityLightPos = BlockPos.containing(entity.getEyePosition(partialTick));
        BlockPos ownerLightPos = BlockPos.containing(owner.getEyePosition(partialTick));
        int entityBlockLight = getBlockLight(entity, entityLightPos);
        int ownerBlockLight = getBlockLight(owner, ownerLightPos);
        int entitySkyLight = entity.level().getBrightness(LightLayer.SKY, entityLightPos);
        int ownerSkyLight = owner.level().getBrightness(LightLayer.SKY, ownerLightPos);

        for (int i = 0; i <= LEASH_RENDER_STEPS; ++i) {
            addVertexPair(consumer, matrix, x, y, z, entityBlockLight, ownerBlockLight, entitySkyLight, ownerSkyLight, 0.025F, 0.025F, zWidth, xWidth, i, false);
        }

        for (int i = LEASH_RENDER_STEPS; i >= 0; --i) {
            addVertexPair(consumer, matrix, x, y, z, entityBlockLight, ownerBlockLight, entitySkyLight, ownerSkyLight, 0.025F, 0.0F, zWidth, xWidth, i, true);
        }

        poseStack.popPose();
    }

    private static int getBlockLight(Entity entity, BlockPos pos) {
        return entity.isOnFire() ? 15 : entity.level().getBrightness(LightLayer.BLOCK, pos);
    }

    private static void addVertexPair(VertexConsumer consumer, Matrix4f matrix, float x, float y, float z, int entityBlockLight, int ownerBlockLight, int entitySkyLight, int ownerSkyLight, float yOffset1, float yOffset2, float zWidth, float xWidth, int index, boolean reverse) {
        float progress = (float) index / (float) LEASH_RENDER_STEPS;
        int blockLight = (int) Mth.lerp(progress, (float) entityBlockLight, (float) ownerBlockLight);
        int skyLight = (int) Mth.lerp(progress, (float) entitySkyLight, (float) ownerSkyLight);
        int light = LightTexture.pack(blockLight, skyLight);
        float brightness = index % 2 == (reverse ? 1 : 0) ? 0.7F : 1.0F;
        float red = 0.5F * brightness;
        float green = 0.4F * brightness;
        float blue = 0.3F * brightness;
        float segmentX = x * progress;
        float segmentY = y > 0.0F ? y * progress * progress : y - y * (1.0F - progress) * (1.0F - progress);
        float segmentZ = z * progress;
        consumer.vertex(matrix, segmentX - zWidth, segmentY + yOffset2, segmentZ + xWidth).color(red, green, blue, 1.0F).uv2(light).endVertex();
        consumer.vertex(matrix, segmentX + zWidth, segmentY + yOffset1 - yOffset2, segmentZ - xWidth).color(red, green, blue, 1.0F).uv2(light).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(LassoLoop entity) {
        return PROJECTILE_TEXTURE;
    }
}
