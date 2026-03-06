package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.DetonationSentryModel;
import com.aetherteam.aetherii.client.renderer.entity.state.DetonationSentryRenderState;
import com.aetherteam.aetherii.client.renderer.entity.state.SliderRenderState;
import com.aetherteam.aetherii.entity.monster.dungeon.DetonationSentry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class DetonationSentryEmissivesLayer extends EyesLayer<DetonationSentryRenderState, DetonationSentryModel> {
    private static final RenderType EYE = RenderTypes.eyes(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/detonation_sentry_eye.png"));
    private static final RenderType EYE_RED = RenderTypes.eyes(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/detonation_sentry_eye_red.png"));
    private static final RenderType RUNE = RenderTypes.eyes(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/detonation_sentry_rune.png"));
    private static final RenderType RUNE_RED = RenderTypes.eyes(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/detonation_sentry_rune_red.png"));
    private static final RenderType TIMER_0 = RenderTypes.eyes(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/detonation_sentry_timer_0.png"));
    private static final RenderType TIMER_1 = RenderTypes.eyes(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/detonation_sentry_timer_1.png"));
    private static final RenderType TIMER_2 = RenderTypes.eyes(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/detonation_sentry_timer_2.png"));
    private static final RenderType TIMER_3 = RenderTypes.eyes(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/detonation_sentry_timer_3.png"));

    public DetonationSentryEmissivesLayer(RenderLayerParent<DetonationSentryRenderState, DetonationSentryModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, DetonationSentryRenderState sentry, float netHeadYaw, float headPitch) {
        RenderType eye;
        RenderType rune = RUNE;
        RenderType timer;

        float sentryTimer = sentry.timer;
        float flickerInterval = Mth.sin(Mth.square(sentryTimer) / 50.0F);
        if (flickerInterval >= 0) {
            eye = EYE;
        } else {
            eye = EYE_RED;
        }
        float timerIncreaseInterval = DetonationSentry.MAX_TIMER / 4.0F;
        if (sentryTimer < timerIncreaseInterval) {
            timer = TIMER_0;
        } else if (sentryTimer < timerIncreaseInterval * 2) {
            timer = TIMER_1;
        } else if (sentryTimer <  timerIncreaseInterval * 3) {
            timer = TIMER_2;
        } else {
            timer = TIMER_3;
            rune = RUNE_RED;
        }
        this.getParentModel().renderToBuffer(poseStack, buffer.getBuffer(eye), packedLight, OverlayTexture.NO_OVERLAY);
        this.getParentModel().renderToBuffer(poseStack, buffer.getBuffer(rune), packedLight, OverlayTexture.NO_OVERLAY);
        this.getParentModel().renderToBuffer(poseStack, buffer.getBuffer(timer), packedLight, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public RenderType renderType() {
        return EYE;
    }
}
