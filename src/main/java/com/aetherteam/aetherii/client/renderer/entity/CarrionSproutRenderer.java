package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.CarrionSproutModel;
import com.aetherteam.aetherii.client.renderer.entity.state.CarrionSproutRenderState;
import com.aetherteam.aetherii.entity.monster.CarrionSprout;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class CarrionSproutRenderer extends MobRenderer<CarrionSprout, CarrionSproutRenderState, CarrionSproutModel<CarrionSproutRenderState>> {
    private static final Identifier CARRION_SPROUT_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/carrion_sprout/carrion_sprout.png");

    public CarrionSproutRenderer(EntityRendererProvider.Context context) {
        super(context, new CarrionSproutModel(context.bakeLayer(AetherIIModelLayers.CARRION_SPROUT)), 0.3F);
    }

    @Override
    public CarrionSproutRenderState createRenderState() {
        return new CarrionSproutRenderState();
    }

    @Override
    public void extractRenderState(CarrionSprout aechorPlant, CarrionSproutRenderState aechorPlantRenderState, float p_361157_) {
        super.extractRenderState(aechorPlant, aechorPlantRenderState, p_361157_);
        aechorPlantRenderState.trapActivateAnimationState.copyFrom(aechorPlant.trapActiveAnimationState);
        aechorPlantRenderState.trapDeactivateAnimationState.copyFrom(aechorPlant.trapDeactiveAnimationState);
    }

    @Override
    protected void scale(CarrionSproutRenderState aechorPlant, PoseStack poseStack) {
        //poseStack.translate(0.0, 1.2, 0.0);
    }

    @Override
    public Identifier getTextureLocation(CarrionSproutRenderState renderState) {
        return CARRION_SPROUT_TEXTURE;
    }
}
