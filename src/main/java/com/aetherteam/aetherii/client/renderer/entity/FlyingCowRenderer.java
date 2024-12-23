package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.FlyingCowModel;
import com.aetherteam.aetherii.client.renderer.entity.state.WingEntityRenderState;
import com.aetherteam.aetherii.entity.passive.FlyingCow;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FlyingCowRenderer extends MobRenderer<FlyingCow, WingEntityRenderState, FlyingCowModel<WingEntityRenderState>> {
    private static final ResourceLocation FLYING_COW_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/flying_cow/flying_cow.png");

    public FlyingCowRenderer(EntityRendererProvider.Context context) {
        super(context, new FlyingCowModel<>(context.bakeLayer(AetherIIModelLayers.FLYING_COW)), 0.7F);
    }

    @Override
    public WingEntityRenderState createRenderState() {
        return new WingEntityRenderState();
    }

    @Override
    public void extractRenderState(FlyingCow flyingCow, WingEntityRenderState wingEntityRenderState, float p_361157_) {
        super.extractRenderState(flyingCow, wingEntityRenderState, p_361157_);
        wingEntityRenderState.wingAngle = (flyingCow.getWingFold() * Mth.sin(wingEntityRenderState.ageInTicks / 15.9F));
        wingEntityRenderState.wingHold = flyingCow.getWingFold();


    }

    @Override
    public ResourceLocation getTextureLocation(WingEntityRenderState renderState) {
        return FLYING_COW_TEXTURE;
    }
}