package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.entity.projectile.ScatterglassBolt;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ScatterglassBoltRenderer extends ArrowRenderer<ScatterglassBolt> {
    public static final ResourceLocation SCATTERGLASS_BOLT_LOCATION = new ResourceLocation("textures/entity/projectiles/arrow.png"); //todo

    public ScatterglassBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ScatterglassBolt bolt) {
        return SCATTERGLASS_BOLT_LOCATION;
    }
}
