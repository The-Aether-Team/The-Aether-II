package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.CockatriceEmissiveLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.CockatriceModel;
import com.aetherteam.aetherii.client.renderer.entity.state.CockatriceRenderState;
import com.aetherteam.aetherii.entity.monster.Cockatrice;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CockatriceRenderer extends MobRenderer<Cockatrice, CockatriceRenderState, CockatriceModel> {
    private static final ResourceLocation COCKATRICE_PLANT_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/cockatrice/cockatrice.png");

    public CockatriceRenderer(EntityRendererProvider.Context context) {
        super(context, new CockatriceModel(context.bakeLayer(AetherIIModelLayers.COCKATRICE)), 0.3F);
        this.addLayer(new CockatriceEmissiveLayer(this));
    }

    @Override
    public CockatriceRenderState createRenderState() {
        return new CockatriceRenderState();
    }

    @Override
    public void extractRenderState(Cockatrice p_362733_, CockatriceRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.attackAnimationState.copyFrom(p_362733_.attackAnimationState);
        p_360515_.shootAnimationState.copyFrom(p_362733_.shootAnimationState);
    }

    @Override
    public ResourceLocation getTextureLocation(CockatriceRenderState p_368654_) {
        return COCKATRICE_PLANT_TEXTURE;
    }
}
