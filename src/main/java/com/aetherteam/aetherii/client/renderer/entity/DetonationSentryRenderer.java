package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.DetonationSentryEmissivesLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.DetonationSentryModel;
import com.aetherteam.aetherii.client.renderer.entity.state.DetonationSentryRenderState;
import com.aetherteam.aetherii.entity.monster.dungeon.DetonationSentry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class DetonationSentryRenderer extends MobRenderer<DetonationSentry, DetonationSentryRenderState, DetonationSentryModel> {
    private static final Identifier SENTRY_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/detonation_sentry.png");

    public DetonationSentryRenderer(EntityRendererProvider.Context context) {
        super(context, new DetonationSentryModel(context.bakeLayer(AetherIIModelLayers.DETONATION_SENTRY)), 0.3F);
        this.addLayer(new DetonationSentryEmissivesLayer(this));
    }

    @Override
    public void extractRenderState(DetonationSentry detonationSentry, DetonationSentryRenderState renderState, float partialTick) {
        super.extractRenderState(detonationSentry, renderState, partialTick);
        renderState.awake = detonationSentry.isAwake();
        renderState.timer = detonationSentry.getTimer(partialTick);
        renderState.explosionAnimationState.copyFrom(detonationSentry.explosionAnimationState);
    }

    @Override
    public DetonationSentryRenderState createRenderState() {
        return new DetonationSentryRenderState();
    }

    @Override
    public Identifier getTextureLocation(DetonationSentryRenderState sentry) {
        return SENTRY_TEXTURE;
    }
}
