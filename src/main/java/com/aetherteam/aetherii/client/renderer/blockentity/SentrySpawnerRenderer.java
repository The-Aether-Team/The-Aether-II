package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.dungeon.SentrySpawnerBlock;
import com.aetherteam.aetherii.blockentity.SentrySpawnerBlockEntity;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentrySpawnerModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentrySpawnerPistonModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class SentrySpawnerRenderer implements BlockEntityRenderer<SentrySpawnerBlockEntity> {
    private static final int FRAME_COUNT = 11;
    private static final ResourceLocation[] BASE_TEXTURES = new ResourceLocation[FRAME_COUNT];
    private static final ResourceLocation PISTON_OFF = texture("piston_off");
    private static final ResourceLocation PISTON_ON = texture("piston_on");
    private static final ResourceLocation PISTON_ON_EMISSIVE = texture("piston_on_emissive");
    private static final ResourceLocation PISTON_SPAWNING = texture("piston_spawning");
    private static final ResourceLocation PISTON_SPAWNING_EMISSIVE = texture("piston_spawning_emissive");

    static {
        for (int i = 0; i < BASE_TEXTURES.length; i++) {
            BASE_TEXTURES[i] = texture("sentry_spawner_base_" + i);
        }
    }

    private final SentrySpawnerModel spawnerModel;
    private final SentrySpawnerPistonModel pistonModel;

    public SentrySpawnerRenderer(BlockEntityRendererProvider.Context context) {
        this.spawnerModel = new SentrySpawnerModel(context.getModelSet().bakeLayer(AetherIIModelLayers.SENTRY_SPAWNER));
        this.pistonModel = new SentrySpawnerPistonModel(context.getModelSet().bakeLayer(AetherIIModelLayers.SENTRY_SPAWNER_PISTON));
    }

    @Override
    public void render(SentrySpawnerBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState state = blockEntity.getBlockState();
        AetherIIBlockStateProperties.SentrySpawnerState spawnerState = state.hasProperty(SentrySpawnerBlock.SENTRY_SPAWNER_STATE) ? state.getValue(SentrySpawnerBlock.SENTRY_SPAWNER_STATE) : AetherIIBlockStateProperties.SentrySpawnerState.INACTIVE;
        int frame = Math.max(0, Math.min(FRAME_COUNT - 1, (int) Math.ceil(blockEntity.getPistonAnimationScale(partialTick) * 10.0F)));

        ResourceLocation pistonTexture = PISTON_OFF;
        ResourceLocation emissiveTexture = null;
        switch (spawnerState) {
            case TRIGGERED, CLOSING -> {
                pistonTexture = PISTON_ON;
                emissiveTexture = PISTON_ON_EMISSIVE;
            }
            case OPENING -> {
                pistonTexture = PISTON_SPAWNING;
                emissiveTexture = PISTON_SPAWNING_EMISSIVE;
            }
            default -> {
            }
        }

        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XN.rotationDegrees(180.0F));

        this.renderModel(this.spawnerModel, poseStack, buffer, BASE_TEXTURES[frame], packedLight, RenderType.entityCutout(BASE_TEXTURES[frame]));

        poseStack.pushPose();
        poseStack.translate(0.0F, -frame / 16.0F, 0.0F);
        this.renderModel(this.pistonModel, poseStack, buffer, pistonTexture, packedLight, RenderType.entityCutout(pistonTexture));
        if (emissiveTexture != null) {
            this.renderModel(this.pistonModel, poseStack, buffer, emissiveTexture, LightTexture.FULL_BRIGHT, RenderType.entityTranslucentEmissive(emissiveTexture));
        }
        poseStack.popPose();

        poseStack.popPose();
    }

    private void renderModel(net.minecraft.client.model.Model model, PoseStack poseStack, MultiBufferSource buffer, ResourceLocation texture, int packedLight, RenderType renderType) {
        VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static ResourceLocation texture(String name) {
        return new ResourceLocation(AetherII.MODID, "textures/entity/sentry_spawner/" + name + ".png");
    }
}
