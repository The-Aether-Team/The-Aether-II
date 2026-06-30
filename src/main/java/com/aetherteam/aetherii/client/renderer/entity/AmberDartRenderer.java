package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.projectile.AmberDart;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class AmberDartRenderer extends ArrowRenderer<AmberDart> {
    private static final ResourceLocation AMBER_DART_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/projectile/amber_dart.png");

    public AmberDartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(AmberDart dart) {
        return AMBER_DART_TEXTURE;
    }
}
