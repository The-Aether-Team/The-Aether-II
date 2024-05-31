package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.layer.QuadrupedWingsLayer;
import com.aetherteam.aetherii.client.renderer.model.QuadrupedWingsModel;
import com.aetherteam.aetherii.entity.passive.FlyingCow;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FlyingCowRenderer extends MobRenderer<FlyingCow, CowModel<FlyingCow>> {
    private static final ResourceLocation FLYING_COW_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/flying_cow/flying_cow.png");

    public FlyingCowRenderer(EntityRendererProvider.Context context) {
        super(context, new CowModel<>(context.bakeLayer(AetherModelLayers.FLYING_COW)), 0.7F);
        this.addLayer(new QuadrupedWingsLayer<>(this, new QuadrupedWingsModel<>(context.bakeLayer(AetherModelLayers.FLYING_COW_WINGS)), new ResourceLocation(AetherII.MODID, "textures/entity/mobs/flying_cow/flying_cow_wings.png")));
    }

    @Override
    public ResourceLocation getTextureLocation(FlyingCow flyingCow) {
        return FLYING_COW_TEXTURE;
    }
}
