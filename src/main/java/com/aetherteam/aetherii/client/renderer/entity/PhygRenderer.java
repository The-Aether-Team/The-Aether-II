package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.PhygModel;
import com.aetherteam.aetherii.entity.passive.Phyg;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class PhygRenderer extends MobRenderer<Phyg, PhygModel<Phyg>> {
    private static final ResourceLocation PHYG_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/phyg/phyg.png");

    public PhygRenderer(EntityRendererProvider.Context context) {
        super(context, new PhygModel<>(context.bakeLayer(AetherIIModelLayers.PHYG)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(LivingEntityRenderState renderState) {
        return PHYG_TEXTURE;
    }
}
