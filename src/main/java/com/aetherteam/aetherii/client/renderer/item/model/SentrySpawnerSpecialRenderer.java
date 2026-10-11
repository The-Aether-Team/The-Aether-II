package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.SentrySpawnerRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentrySpawnerModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentrySpawnerPistonModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.util.Unit;
import org.joml.Vector3fc;

import java.util.function.Consumer;

public class SentrySpawnerSpecialRenderer implements NoDataSpecialModelRenderer {
    private final SentrySpawnerModel model;
    private final SentrySpawnerPistonModel pistonModel;
    private final SpriteGetter sprites;

    public SentrySpawnerSpecialRenderer(SpriteGetter sprites, SentrySpawnerModel model, SentrySpawnerPistonModel pistonModel) {
        this.sprites = sprites;
        this.model = model;
        this.pistonModel = pistonModel;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int i1, boolean b, int i2) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        submitNodeCollector.submitModel(this.model, Unit.INSTANCE, poseStack, AetherIIAtlases.SENTRY_SPAWNER_MATERIALS.get(0).renderType(RenderTypes::entitySolid), i, i1, -1, this.sprites.get(AetherIIAtlases.SENTRY_SPAWNER_MATERIALS.get(0)), i2, null);

        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        submitNodeCollector.submitModel(this.pistonModel, Unit.INSTANCE, poseStack, RenderTypes.entitySolid(SentrySpawnerRenderer.PISTON_OFF), i, i1, i2, null);
        poseStack.popPose();

    }

    @Override
    public void getExtents(Consumer<Vector3fc> consumer) {
        PoseStack poseStack = new PoseStack();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        this.model.root().getExtentsForGui(poseStack, consumer);
        this.pistonModel.root().getExtentsForGui(poseStack, consumer);
    }

    public record Unbaked() implements NoDataSpecialModelRenderer.Unbaked {
        public static final MapCodec<SentrySpawnerSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new SentrySpawnerSpecialRenderer.Unbaked());

        public Unbaked() {
        }

        @Override
        public MapCodec<SentrySpawnerSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public NoDataSpecialModelRenderer bake(BakingContext context) {
            SentrySpawnerModel model = new SentrySpawnerModel(context.entityModelSet().bakeLayer(AetherIIModelLayers.SENTRY_SPAWNER));
            SentrySpawnerPistonModel pistonModel = new SentrySpawnerPistonModel(context.entityModelSet().bakeLayer(AetherIIModelLayers.SENTRY_SPAWNER_PISTON));

            return new SentrySpawnerSpecialRenderer(context.sprites(), model, pistonModel);
        }
    }
}
