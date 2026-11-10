package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.projectile.VenomousDart;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;

public class VenomousDartRenderer extends EmissiveArrowRenderer<VenomousDart, ArrowRenderState> {
    private static final Identifier VENOMOUS_DART_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/venomous_dart.png");
    private static final Identifier VENOMOUS_DART_EMISSIVE_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/venomous_dart_emissive.png");

    private final ArrowModel model;

    public VenomousDartRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ArrowModel(context.bakeLayer(ModelLayers.ARROW));
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    public Identifier getTextureLocation(ArrowRenderState renderState) {
        return VENOMOUS_DART_TEXTURE;
    }

    public Identifier getEmissiveTextureLocation(ArrowRenderState renderState) {
        return VENOMOUS_DART_EMISSIVE_TEXTURE;
    }

    @Override
    public ArrowModel getModel(ArrowRenderState renderState) {
        return this.model;
    }
}
