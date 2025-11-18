package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.DetonationSentryEmissiveLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SentryModel;
import com.aetherteam.aetherii.client.renderer.entity.state.DetonationSentryRenderState;
import com.aetherteam.aetherii.entity.monster.dungeon.DetonationSentry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DetonationSentryRenderer extends MobRenderer<DetonationSentry, DetonationSentryRenderState, SentryModel> {
    private static final ResourceLocation SENTRY_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/detonation_sentry.png");

    public DetonationSentryRenderer(EntityRendererProvider.Context context) {
        super(context, new SentryModel(context.bakeLayer(AetherIIModelLayers.DETONATION_SENTRY)), 0.3F);
        this.addLayer(new DetonationSentryEmissiveLayer(this));
    }

    @Override
    public void extractRenderState(DetonationSentry detonationSentry, DetonationSentryRenderState renderState, float p_361157_) {
        super.extractRenderState(detonationSentry, renderState, p_361157_);
        renderState.awake = detonationSentry.isAwake();
        renderState.swelling = detonationSentry.getSwelling(p_361157_);
    }

    @Override
    protected float getWhiteOverlayProgress(DetonationSentryRenderState renderState) {
        float f = renderState.swelling;
        boolean swellIncoming = f > 0.5F;
        return (int) (swellIncoming ? f * 20.0F : f * 5.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
    }

    @Override
    public DetonationSentryRenderState createRenderState() {
        return new DetonationSentryRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(DetonationSentryRenderState sentry) {
        return SENTRY_TEXTURE;
    }
}
