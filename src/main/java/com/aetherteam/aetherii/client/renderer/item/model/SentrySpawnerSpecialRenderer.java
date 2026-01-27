package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.SentrySpawnerRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentrySpawnerModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentrySpawnerPistonModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Vector3f;

import java.util.Set;

public class SentrySpawnerSpecialRenderer implements NoDataSpecialModelRenderer {
    private final SentrySpawnerModel model;
    private final SentrySpawnerPistonModel pistonModel;

    public SentrySpawnerSpecialRenderer(SentrySpawnerModel model, SentrySpawnerPistonModel pistonModel) {
        this.model = model;
        this.pistonModel = pistonModel;
    }

    @Override
    public void render(ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, boolean partialTick) {
        VertexConsumer vertexconsumer = AetherIIAtlases.SENTRY_SPAWNER_MATERIALS.get(0).buffer(buffer, RenderType::entitySolid);
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, packedOverlay);
        poseStack.popPose();

        VertexConsumer vertexconsumer2 = buffer.getBuffer(RenderType.entitySolid(SentrySpawnerRenderer.PISTON_OFF));
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        this.pistonModel.renderToBuffer(poseStack, vertexconsumer2, packedLight, packedOverlay);
        poseStack.popPose();
    }

    @Override
    public void getExtents(Set<Vector3f> set) {
        PoseStack poseStack = new PoseStack();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        this.model.root().getExtentsForGui(poseStack, set);
        this.pistonModel.root().getExtentsForGui(poseStack, set);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<SentrySpawnerSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new SentrySpawnerSpecialRenderer.Unbaked());

        public Unbaked() {
        }

        @Override
        public MapCodec<SentrySpawnerSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(EntityModelSet entityModelSet) {
            SentrySpawnerModel model = new SentrySpawnerModel(entityModelSet.bakeLayer(AetherIIModelLayers.SENTRY_SPAWNER));
            SentrySpawnerPistonModel pistonModel = new SentrySpawnerPistonModel(entityModelSet.bakeLayer(AetherIIModelLayers.SENTRY_SPAWNER_PISTON));

            return new SentrySpawnerSpecialRenderer(model, pistonModel);
        }
    }
}
