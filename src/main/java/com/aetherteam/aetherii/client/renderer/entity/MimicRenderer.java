package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.MimicModel;
import com.aetherteam.aetherii.entity.monster.dungeon.Mimic;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class MimicRenderer extends MobRenderer<Mimic, LivingEntityRenderState, MimicModel<LivingEntityRenderState>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sentry_crate_mimic/sentry_crate_mimic.png");

    public MimicRenderer(EntityRendererProvider.Context renderer) {
        super(renderer, new MimicModel<>(renderer.bakeLayer(AetherIIModelLayers.MIMIC)), 1.0F);

    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    /**
     * If the Lootr mod is installed or if it is Christmas, Mimics will have a custom texture.
     *
     * @param Mimic The {@link Mimic} entity.
     * @return The texture {@link ResourceLocation}.
     */
    @Override
    public ResourceLocation getTextureLocation(LivingEntityRenderState Mimic) {
        return TEXTURE;
    }
}

