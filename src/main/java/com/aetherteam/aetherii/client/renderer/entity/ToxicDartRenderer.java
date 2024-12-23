package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.projectile.ToxicDart;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.ResourceLocation;

public class ToxicDartRenderer extends ArrowRenderer<ToxicDart, ArrowRenderState> {
    private static final ResourceLocation TOXIC_DART_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/toxic_dart.png");

    public ToxicDartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(ArrowRenderState renderState) {
        return TOXIC_DART_TEXTURE;
    }
}
