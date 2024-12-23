package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.PhygModel;
import com.aetherteam.aetherii.client.renderer.entity.state.WingEntityRenderState;
import com.aetherteam.aetherii.entity.passive.Phyg;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

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
    public void extractRenderState(Phyg phyg, WingEntityRenderState wingEntityRenderState, float p_361157_) {
        super.extractRenderState(phyg, wingEntityRenderState, p_361157_);
        wingEntityRenderState.wingAngle = (phyg.getWingFold() * Mth.sin(wingEntityRenderState.ageInTicks / 15.9F));
        wingEntityRenderState.wingHold = phyg.getWingFold();
    }

    @Override
    public ResourceLocation getTextureLocation(WingEntityRenderState renderState) {
        return PHYG_TEXTURE;
    }
}
