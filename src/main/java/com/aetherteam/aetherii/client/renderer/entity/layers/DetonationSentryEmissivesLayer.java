package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.DetonationSentryModel;
import com.aetherteam.aetherii.entity.monster.dungeon.DetonationSentry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DetonationSentryEmissivesLayer extends RenderLayer<DetonationSentry, DetonationSentryModel> {
    private static final RenderType EYE = eyes("detonation_sentry_eye");
    private static final RenderType EYE_RED = eyes("detonation_sentry_eye_red");
    private static final RenderType RUNE = eyes("detonation_sentry_rune");
    private static final RenderType RUNE_RED = eyes("detonation_sentry_rune_red");
    private static final RenderType TIMER_0 = eyes("detonation_sentry_timer_0");
    private static final RenderType TIMER_1 = eyes("detonation_sentry_timer_1");
    private static final RenderType TIMER_2 = eyes("detonation_sentry_timer_2");
    private static final RenderType TIMER_3 = eyes("detonation_sentry_timer_3");

    public DetonationSentryEmissivesLayer(RenderLayerParent<DetonationSentry, DetonationSentryModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, DetonationSentry sentry, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!sentry.isInvisible()) {
            float sentryTimer = sentry.getTimer(partialTick);
            RenderType eye = Mth.sin(Mth.square(sentryTimer) / 50.0F) >= 0 ? EYE : EYE_RED;
            RenderType rune = RUNE;
            RenderType timer;

            float timerIncreaseInterval = DetonationSentry.MAX_TIMER / 4.0F;
            if (sentryTimer < timerIncreaseInterval) {
                timer = TIMER_0;
            } else if (sentryTimer < timerIncreaseInterval * 2) {
                timer = TIMER_1;
            } else if (sentryTimer < timerIncreaseInterval * 3) {
                timer = TIMER_2;
            } else {
                timer = TIMER_3;
                rune = RUNE_RED;
            }

            renderLayer(poseStack, buffer, sentry, eye);
            renderLayer(poseStack, buffer, sentry, rune);
            renderLayer(poseStack, buffer, sentry, timer);
        }
    }

    private void renderLayer(PoseStack poseStack, MultiBufferSource buffer, DetonationSentry sentry, RenderType renderType) {
        VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
        this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 15728640, LivingEntityRenderer.getOverlayCoords(sentry, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static RenderType eyes(String texture) {
        return RenderType.entityTranslucentEmissive(new ResourceLocation(AetherII.MODID, "textures/entity/mobs/detonation_sentry/" + texture + ".png"));
    }
}
