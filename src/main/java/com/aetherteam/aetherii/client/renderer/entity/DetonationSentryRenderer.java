package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.DetonationSentryEmissiveLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SentryModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SentryRenderState;
import com.aetherteam.aetherii.entity.monster.dungeon.DetonationSentry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DetonationSentryRenderer extends MobRenderer<DetonationSentry, SentryRenderState, SentryModel> {
    private static final ResourceLocation SENTRY_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/detonation_sentry.png");
    private static final ResourceLocation SENTRY_LIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/detonation_sentry_lit.png");

    public DetonationSentryRenderer(EntityRendererProvider.Context context) {
        super(context, new SentryModel(context.bakeLayer(AetherIIModelLayers.DETONATION_SENTRY)), 0.3F);
        this.addLayer(new DetonationSentryEmissiveLayer(this));
    }

    @Override
    public void extractRenderState(DetonationSentry detonationSentry, SentryRenderState renderState, float p_361157_) {
        super.extractRenderState(detonationSentry, renderState, p_361157_);
        renderState.squish = Mth.lerp(p_361157_, detonationSentry.oSquish, detonationSentry.squish);
        renderState.size = detonationSentry.getSize();
        renderState.awake = detonationSentry.isAwake();
    }

    @Override
    public SentryRenderState createRenderState() {
        return new SentryRenderState();
    }

    /**
     * Scales the Sentry according to its size.
     *
     * @param sentry    The {@link SentryRenderState} entity.
     * @param poseStack The rendering {@link PoseStack}.
     */
    @Override
    protected void scale(SentryRenderState sentry, PoseStack poseStack) {
        float f = 0.879F;
        poseStack.scale(f, f, f);
        float f1 = sentry.size + 1.0F;
        float f2 = 0.0F;
        float f3 = 1.0F / (f2 + 1.0F);
        poseStack.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    @Override
    public ResourceLocation getTextureLocation(SentryRenderState sentry) {
        return sentry.awake ? SENTRY_LIT_TEXTURE : SENTRY_TEXTURE;
    }
}
