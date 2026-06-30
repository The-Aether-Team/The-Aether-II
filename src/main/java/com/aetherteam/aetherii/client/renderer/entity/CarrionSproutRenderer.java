package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.CarrionSproutModel;
import com.aetherteam.aetherii.entity.monster.CarrionSprout;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CarrionSproutRenderer extends MobRenderer<CarrionSprout, CarrionSproutModel> {
    private static final ResourceLocation CARRION_SPROUT_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/carrion_sprout/carrion_sprout.png");

    public CarrionSproutRenderer(EntityRendererProvider.Context context) {
        super(context, new CarrionSproutModel(context.bakeLayer(AetherIIModelLayers.CARRION_SPROUT)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(CarrionSprout carrionSprout) {
        return CARRION_SPROUT_TEXTURE;
    }
}
