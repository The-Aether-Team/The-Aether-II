package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.SentryCrateMimicModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SentryCrateMimicRenderState;
import com.aetherteam.aetherii.entity.monster.dungeon.Mimic;
import com.aetherteam.aetherii.entity.monster.dungeon.SentryCrateMimic;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SentryCrateMimicRenderer extends MobRenderer<SentryCrateMimic, SentryCrateMimicRenderState, SentryCrateMimicModel<SentryCrateMimicRenderState>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sentry_crate_mimic/sentry_crate_mimic.png");

    public SentryCrateMimicRenderer(EntityRendererProvider.Context renderer) {
        super(renderer, new SentryCrateMimicModel<>(renderer.bakeLayer(AetherIIModelLayers.SENTRY_CRATE_MIMIC)), 1.0F);

    }

    @Override
    public SentryCrateMimicRenderState createRenderState() {
        return new SentryCrateMimicRenderState();
    }

    /**
     * If the Lootr mod is installed or if it is Christmas, Mimics will have a custom texture.
     *
     * @param Mimic The {@link Mimic} entity.
     * @return The texture {@link ResourceLocation}.
     */
    @Override
    public ResourceLocation getTextureLocation(SentryCrateMimicRenderState Mimic) {
        return TEXTURE;
    }
}
