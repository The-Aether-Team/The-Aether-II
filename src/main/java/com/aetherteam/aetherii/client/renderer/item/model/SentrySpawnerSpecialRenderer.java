package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentrySpawnerModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentrySpawnerPistonModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class SentrySpawnerSpecialRenderer {
    private static final ResourceLocation BASE = texture("sentry_spawner_base_0");
    private static final ResourceLocation PISTON = texture("piston_off");
    private final SentrySpawnerModel model;
    private final SentrySpawnerPistonModel pistonModel;

    public SentrySpawnerSpecialRenderer(SentrySpawnerModel model, SentrySpawnerPistonModel pistonModel) {
        this.model = model;
        this.pistonModel = pistonModel;
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, boolean hasFoil) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        this.renderModel(this.model, poseStack, buffer, BASE, packedLight, packedOverlay, hasFoil);
        this.renderModel(this.pistonModel, poseStack, buffer, PISTON, packedLight, packedOverlay, hasFoil);
        poseStack.popPose();
    }

    private void renderModel(Model model, PoseStack poseStack, MultiBufferSource buffer, ResourceLocation texture, int packedLight, int packedOverlay, boolean hasFoil) {
        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(buffer, RenderType.entityCutout(texture), false, hasFoil);
        model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public record Unbaked() {
        public static final MapCodec<SentrySpawnerSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new SentrySpawnerSpecialRenderer.Unbaked());

        public MapCodec<SentrySpawnerSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        public SentrySpawnerSpecialRenderer bake(EntityModelSet modelSet) {
            return new SentrySpawnerSpecialRenderer(
                    new SentrySpawnerModel(modelSet.bakeLayer(AetherIIModelLayers.SENTRY_SPAWNER)),
                    new SentrySpawnerPistonModel(modelSet.bakeLayer(AetherIIModelLayers.SENTRY_SPAWNER_PISTON)));
        }
    }

    private static ResourceLocation texture(String name) {
        return new ResourceLocation(AetherII.MODID, "textures/entity/sentry_spawner/" + name + ".png");
    }
}
