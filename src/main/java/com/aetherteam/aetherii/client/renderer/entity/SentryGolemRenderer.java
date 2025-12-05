package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.SentryGolemLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SentryGolemModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SentryGolemRenderState;
import com.aetherteam.aetherii.entity.monster.dungeon.SentryGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class SentryGolemRenderer extends MobRenderer<SentryGolem, SentryGolemRenderState, SentryGolemModel> {
    private static final ResourceLocation SENTRY_GOLEM_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sentry_golem/sentry_golem.png");
    private static final ResourceLocation SENTRY_GOLEM_RANGED_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sentry_golem/sentry_golem_ranged.png");

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
        ArmedEntityRenderState.extractArmedEntityRenderState(sentryGolem, renderState, itemModelResolver);
        renderState.ranged = sentryGolem.isRanged();
        renderState.idleAnimationState.copyFrom(sentryGolem.idleAnimationState);
        renderState.checkSelfAnimationState.copyFrom(sentryGolem.checkSelfAnimationState);
        renderState.lookAroundAnimationState.copyFrom(sentryGolem.lookAroundAnimationState);
        renderState.attackAnimationState.copyFrom(sentryGolem.attackAnimationState);
        renderState.attackRangeAnimationState.copyFrom(sentryGolem.attackRangeAnimationState);
        renderState.attackReadyAnimationState.copyFrom(sentryGolem.attackReadyAnimationState);
        renderState.attackRangeReadyAnimationState.copyFrom(sentryGolem.attackRangeReadyAnimationState);
    }

    @Override
    public void render(SentryGolemRenderState renderState, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
        super.render(renderState, p_115311_, p_115312_, p_115313_);
    }
    @Override
    public ResourceLocation getTextureLocation(SentryGolemRenderState golem) {
        return golem.ranged ? SENTRY_GOLEM_RANGED_TEXTURE : SENTRY_GOLEM_TEXTURE;
    }
}
