package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.PhygModel;
import com.aetherteam.aetherii.client.renderer.entity.state.WingEntityRenderState;
import com.aetherteam.aetherii.entity.passive.Phyg;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PhygRenderer extends MobRenderer<Phyg, WingEntityRenderState, PhygModel<WingEntityRenderState>> {
    private static final ResourceLocation PHYG_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/phyg/phyg.png");

    public PhygRenderer(EntityRendererProvider.Context context) {
        super(context, new PhygModel<>(context.bakeLayer(AetherIIModelLayers.PHYG)), 0.7F);
    }


    @Override
    public WingEntityRenderState createRenderState() {
        return new WingEntityRenderState();
    }

    @Override
    public void extractRenderState(Phyg p_362733_, WingEntityRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.wingHold = p_362733_.getWingFold();
    }

    @Override
    public ResourceLocation getTextureLocation(WingEntityRenderState renderState) {
        return PHYG_TEXTURE;
    }
}
