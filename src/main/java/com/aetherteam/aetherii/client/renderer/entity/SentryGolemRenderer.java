package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.SentryGolemLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SentryGolemModel;
import com.aetherteam.aetherii.entity.monster.dungeon.SentryGolem;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class SentryGolemRenderer extends MobRenderer<SentryGolem, SentryGolemModel> {
    private static final ResourceLocation SENTRY_GOLEM_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/sentry_golem/sentry_golem.png");
    private static final ResourceLocation SENTRY_GOLEM_RANGED_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/sentry_golem/sentry_golem_ranged.png");

    public SentryGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new SentryGolemModel(context.bakeLayer(AetherIIModelLayers.SENTRY_GOLEM)), 0.7F);
        this.addLayer(new SentryGolemLayer(this));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(SentryGolem golem) {
        return golem.isRanged() ? SENTRY_GOLEM_RANGED_TEXTURE : SENTRY_GOLEM_TEXTURE;
    }
}
