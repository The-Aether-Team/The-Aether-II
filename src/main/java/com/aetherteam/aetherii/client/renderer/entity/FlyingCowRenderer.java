package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherModelLayers;
import com.aetherteam.aetherii.client.renderer.model.FlyingCowModel;
import com.aetherteam.aetherii.entity.passive.FlyingCow;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FlyingCowRenderer extends MobRenderer<FlyingCow, FlyingCowModel<FlyingCow>> {
    private static final ResourceLocation FLYING_COW_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/flying_cow/flying_cow.png");

    public FlyingCowRenderer(EntityRendererProvider.Context context) {
        super(context, new FlyingCowModel<>(context.bakeLayer(AetherModelLayers.FLYING_COW)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(FlyingCow flyingCow) {
        return FLYING_COW_TEXTURE;
    }
}
