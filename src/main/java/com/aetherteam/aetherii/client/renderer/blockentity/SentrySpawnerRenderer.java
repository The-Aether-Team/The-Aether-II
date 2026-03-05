package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.dungeon.SentrySpawnerBlock;
import com.aetherteam.aetherii.blockentity.SentrySpawnerBlockEntity;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentrySpawnerModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentrySpawnerPistonModel;
import com.aetherteam.aetherii.client.renderer.blockentity.state.SentrySpawnerRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SentrySpawnerRenderer implements BlockEntityRenderer<SentrySpawnerBlockEntity, SentrySpawnerRenderState> {
    public static int SENTRY_SPAWNER_FRAMES = 11;
    public static final Identifier PISTON_OFF = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/sentry_spawner/piston_off.png");
    public static final Identifier PISTON_ON = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/sentry_spawner/piston_on.png");
    public static final Identifier PISTON_ON_EMISSIVE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/sentry_spawner/piston_on_emissive.png");
    public static final Identifier PISTON_SPAWNING = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/sentry_spawner/piston_spawning.png");
    public static final Identifier PISTON_SPAWNING_EMISSIVE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/sentry_spawner/piston_spawning_emissive.png");

    private final MaterialSet materials;

    private final SentrySpawnerModel sentrySpawnerModel;
    private final SentrySpawnerPistonModel sentrySpawnerPistonModel;

    public SentrySpawnerRenderer(BlockEntityRendererProvider.Context context) {
        this.materials = context.materials();

        this.sentrySpawnerModel = new SentrySpawnerModel(context.entityModelSet().bakeLayer(AetherIIModelLayers.SENTRY_SPAWNER));
        this.sentrySpawnerPistonModel = new SentrySpawnerPistonModel(context.entityModelSet().bakeLayer(AetherIIModelLayers.SENTRY_SPAWNER_PISTON));
    }

    public static Map<Integer, Material> getFrames() {
        Map<Integer, Material> pieces = new HashMap<>();
        for (int i = 0; i <= SENTRY_SPAWNER_FRAMES - 1; i++) {
            pieces.put(i, AetherIIAtlases.SENTRY_SPAWNER_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, "sentry_spawner_base_" + (i % SENTRY_SPAWNER_FRAMES))));
        }
        return pieces;
    }


    @Override
    public void submit(SentrySpawnerRenderState sentrySpawnerRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XN.rotationDegrees(180F));

        AetherIIBlockStateProperties.SentrySpawnerState spawnerState = sentrySpawnerRenderState.sentrySpawnerState;

        int frame = sentrySpawnerRenderState.open;

        Material baseMaterial = AetherIIAtlases.SENTRY_SPAWNER_MATERIALS.get(frame);
        Identifier pistonLocation = PISTON_OFF;
        Identifier emissiveLocation = null;

        switch (spawnerState) {
            case TRIGGERED, CLOSING -> {
                pistonLocation = PISTON_ON;
                emissiveLocation = PISTON_ON_EMISSIVE;
            }
            case OPENING -> {
                pistonLocation = PISTON_SPAWNING;
                emissiveLocation = PISTON_SPAWNING_EMISSIVE;
            }
        }

        RenderType renderType = baseMaterial.renderType(RenderTypes::entityCutout);
        submitNodeCollector.submitModel(this.sentrySpawnerModel, sentrySpawnerRenderState, poseStack, renderType, sentrySpawnerRenderState.lightCoords,
                OverlayTexture.NO_OVERLAY,
                -1,
                materials.get(baseMaterial),
                0,
                sentrySpawnerRenderState.breakProgress);



        this.sentrySpawnerPistonModel.root().resetPose();
        this.sentrySpawnerPistonModel.root().offsetPos(new Vector3f(0, -(frame), 0));

        RenderType pistonRenderType = RenderTypes.entityCutoutNoCull(pistonLocation);
        submitNodeCollector.submitModel(this.sentrySpawnerPistonModel, sentrySpawnerRenderState, poseStack, pistonRenderType, sentrySpawnerRenderState.lightCoords,
                OverlayTexture.NO_OVERLAY,
                -1,
                null);

        RenderType pistonEmissiveRenderType = RenderTypes.entityTranslucentEmissive(pistonLocation);

        if (emissiveLocation != null) {
            submitNodeCollector.submitModel(this.sentrySpawnerPistonModel, sentrySpawnerRenderState, poseStack, pistonEmissiveRenderType, sentrySpawnerRenderState.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    -1,
                    null);
        }
    }

    @Override
    public SentrySpawnerRenderState createRenderState() {
        return new SentrySpawnerRenderState();
    }

    @Override
    public void extractRenderState(SentrySpawnerBlockEntity sentrySpawnerBlockEntity, SentrySpawnerRenderState sentrySpawnerRenderState, float partialTick, Vec3 p_445788_, ModelFeatureRenderer.@Nullable CrumblingOverlay p_446944_) {
        BlockEntityRenderer.super.extractRenderState(sentrySpawnerBlockEntity, sentrySpawnerRenderState, partialTick, p_445788_, p_446944_);
        int frame = Math.max(0, (int) Math.ceil(sentrySpawnerBlockEntity.getPistonAnimationScale(partialTick) * 10));
        sentrySpawnerRenderState.open = frame;
        sentrySpawnerRenderState.sentrySpawnerState = sentrySpawnerBlockEntity.getBlockState().getValue(SentrySpawnerBlock.SENTRY_SPAWNER_STATE);
    }
}
