package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.blockentity.SentryTrapBlockEntity;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentryTrapModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class SentryTrapRenderer implements BlockEntityRenderer<SentryTrapBlockEntity> {
    public static final ResourceLocation SENTRY_TRAP = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/sentry_trap/sentry_trap.png");
    public static final ResourceLocation SENTRY_TRAP_TRIGGERED = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/sentry_trap/sentry_trap_triggered.png");
    public static final ResourceLocation SENTRY_TRAP_EMISSIVE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/sentry_trap/sentry_trap_emissive.png");

    private final SentryTrapModel sentryTrapModel;

    public SentryTrapRenderer(BlockEntityRendererProvider.Context context) {
        this.sentryTrapModel = new SentryTrapModel(context.getModelSet().bakeLayer(AetherIIModelLayers.SENTRY_TRAP));
    }

    @Override
    public void render(SentryTrapBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, Vec3 cameraPos) {
//        poseStack.translate(0.5F, 1.0F, 0.5F);
//        poseStack.mulPose(Axis.XN.rotationDegrees(180F));

        if (blockEntity.getSpawner().hasSpawnedEntity()) {
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutout(SENTRY_TRAP_TRIGGERED));
            this.sentryTrapModel.renderToBuffer(poseStack, consumer, packedLight, packedOverlay);
            VertexConsumer emissiveConsumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(SENTRY_TRAP_EMISSIVE));
            this.sentryTrapModel.renderToBuffer(poseStack, emissiveConsumer, packedLight, packedOverlay);
        } else {
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutout(SENTRY_TRAP));
            this.sentryTrapModel.renderToBuffer(poseStack, consumer, packedLight, packedOverlay);
        }
    }
}
