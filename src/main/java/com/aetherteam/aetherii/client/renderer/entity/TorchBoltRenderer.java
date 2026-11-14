package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.projectile.TorchBolt;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;

public class TorchBoltRenderer extends EmissiveArrowRenderer<TorchBolt, ArrowRenderState> {
    private static final Identifier AMBROSIUM_TORCH_BOLT_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/ambrosium_torch_bolt.png");
    private static final Identifier AMBROSIUM_TORCH_BOLT_EMISSIVE_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/ambrosium_torch_bolt_emissive.png");

    private final ArrowModel model;

    public TorchBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ArrowModel(context.bakeLayer(ModelLayers.ARROW));
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    public Identifier getTextureLocation(ArrowRenderState renderState) {
        return AMBROSIUM_TORCH_BOLT_TEXTURE;
    }

    public Identifier getEmissiveTextureLocation(ArrowRenderState renderState) {
        return AMBROSIUM_TORCH_BOLT_EMISSIVE_TEXTURE;
    }

    @Override
    public ArrowModel getModel(ArrowRenderState renderState) {
        return this.model;
    }
}
