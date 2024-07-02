package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.projectile.ToxicDart;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ToxicDartRenderer extends ArrowRenderer<ToxicDart> {
    private static final ResourceLocation TOXIC_DART_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/toxic_dart.png");

    public ToxicDartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ToxicDart dart) {
        return TOXIC_DART_TEXTURE;
    }
}
