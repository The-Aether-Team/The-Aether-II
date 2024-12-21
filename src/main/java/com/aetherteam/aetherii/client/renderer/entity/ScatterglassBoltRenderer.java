package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.projectile.ScatterglassBolt;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class ScatterglassBoltRenderer extends ArrowRenderer<ScatterglassBolt, ArrowRenderState> {
    public static final ResourceLocation SCATTERGLASS_BOLT_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/scatterglass_bolt.png");

    public ScatterglassBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(ArrowRenderState renderState) {
        return SCATTERGLASS_BOLT_TEXTURE;
    }
}
