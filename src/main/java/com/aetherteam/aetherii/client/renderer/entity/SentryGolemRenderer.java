package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.SentryGolemLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SentryGolemModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SentryGolemRenderState;
import com.aetherteam.aetherii.entity.monster.dungeon.SentryGolem;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.resources.Identifier;

public class SentryGolemRenderer extends MobRenderer<SentryGolem, SentryGolemRenderState, SentryGolemModel> {
    private static final Identifier SENTRY_GOLEM_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sentry_golem/sentry_golem.png");
    private static final Identifier SENTRY_GOLEM_RANGED_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sentry_golem/sentry_golem_ranged.png");

    public SentryGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new SentryGolemModel(context.bakeLayer(AetherIIModelLayers.SENTRY_GOLEM)), 0.7F);
        this.addLayer(new SentryGolemLayer(this));
        this.addLayer(new ItemInHandLayer<>(this));
    }

    @Override
    public SentryGolemRenderState createRenderState() {
        return new SentryGolemRenderState();
    }

    @Override
    public void extractRenderState(SentryGolem sentryGolem, SentryGolemRenderState renderState, float p_361157_) {
        super.extractRenderState(sentryGolem, renderState, p_361157_);
        ArmedEntityRenderState.extractArmedEntityRenderState(sentryGolem, renderState, itemModelResolver, p_361157_);
        renderState.ranged = sentryGolem.isRanged();
        renderState.checkSelfAnimationState.copyFrom(sentryGolem.checkSelfAnimationState);
        renderState.lookAroundAnimationState.copyFrom(sentryGolem.lookAroundAnimationState);
        renderState.attackAnimationState.copyFrom(sentryGolem.attackAnimationState);
        renderState.attackRangeAnimationState.copyFrom(sentryGolem.attackRangeAnimationState);
        renderState.attackReadyAnimationState.copyFrom(sentryGolem.attackReadyAnimationState);
        renderState.attackRangeReadyAnimationState.copyFrom(sentryGolem.attackRangeReadyAnimationState);
    }

    @Override
    public Identifier getTextureLocation(SentryGolemRenderState golem) {
        return golem.ranged ? SENTRY_GOLEM_RANGED_TEXTURE : SENTRY_GOLEM_TEXTURE;
    }
}
