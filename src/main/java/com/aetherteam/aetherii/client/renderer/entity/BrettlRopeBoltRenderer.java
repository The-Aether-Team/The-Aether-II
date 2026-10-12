package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.projectile.BrettlRopeBolt;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;

public class BrettlRopeBoltRenderer extends ArrowRenderer<BrettlRopeBolt, ArrowRenderState> {
    public static final Identifier BRETTL_ROPE_BOLT_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/brettl_rope_bolt.png");

    public BrettlRopeBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    public Identifier getTextureLocation(ArrowRenderState renderState) {
        return BRETTL_ROPE_BOLT_TEXTURE;
    }
}
