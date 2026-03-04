package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.dungeon.SentrySpawnerBlock;
import com.aetherteam.aetherii.blockentity.SentrySpawnerBlockEntity;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentrySpawnerModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentrySpawnerPistonModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class SentrySpawnerRenderer implements BlockEntityRenderer<SentrySpawnerBlockEntity> {
    public static int SENTRY_SPAWNER_FRAMES = 11;
    public static final Identifier PISTON_OFF = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/sentry_spawner/piston_off.png");
    public static final Identifier PISTON_ON = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/sentry_spawner/piston_on.png");
    public static final Identifier PISTON_ON_EMISSIVE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/sentry_spawner/piston_on_emissive.png");
    public static final Identifier PISTON_SPAWNING = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/sentry_spawner/piston_spawning.png");
    public static final Identifier PISTON_SPAWNING_EMISSIVE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/sentry_spawner/piston_spawning_emissive.png");

    private final SentrySpawnerModel sentrySpawnerModel;
    private final SentrySpawnerPistonModel sentrySpawnerPistonModel;

    public SentrySpawnerRenderer(BlockEntityRendererProvider.Context context) {
        this.sentrySpawnerModel = new SentrySpawnerModel(context.getModelSet().bakeLayer(AetherIIModelLayers.SENTRY_SPAWNER));
        this.sentrySpawnerPistonModel = new SentrySpawnerPistonModel(context.getModelSet().bakeLayer(AetherIIModelLayers.SENTRY_SPAWNER_PISTON));
    }

    public static Map<Integer, Material> getFrames() {
        Map<Integer, Material> pieces = new HashMap<>();
        for (int i = 0; i <= SENTRY_SPAWNER_FRAMES - 1; i++) {
            pieces.put(i, AetherIIAtlases.SENTRY_SPAWNER_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, "sentry_spawner_base_" + (i % SENTRY_SPAWNER_FRAMES))));
        }
        return pieces;
    }

    @Override
    public void render(SentrySpawnerBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, Vec3 cameraPos) {
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XN.rotationDegrees(180F));

        BlockState state = blockEntity.getBlockState();
        AetherIIBlockStateProperties.SentrySpawnerState spawnerState = state.getValue(SentrySpawnerBlock.SENTRY_SPAWNER_STATE);

        int frame = Math.max(0, (int) Math.ceil(blockEntity.getPistonAnimationScale(partialTick) * 10));

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

        VertexConsumer baseConsumer = baseMaterial.buffer(buffer, RenderType::entityCutout);
        this.sentrySpawnerModel.renderToBuffer(poseStack, baseConsumer, packedLight, packedOverlay);

        this.sentrySpawnerPistonModel.root().resetPose();
        this.sentrySpawnerPistonModel.root().offsetPos(new Vector3f(0, -(frame), 0));

        VertexConsumer pistonConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(pistonLocation));
        this.sentrySpawnerPistonModel.renderToBuffer(poseStack, pistonConsumer, packedLight, packedOverlay);

        if (emissiveLocation != null) {
            VertexConsumer emissiveConsumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(emissiveLocation));
            this.sentrySpawnerPistonModel.renderToBuffer(poseStack, emissiveConsumer, packedLight, packedOverlay);
        }
    }
}
