package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.ArkeniumTalutonEyesLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.ArkeniumTalutonModel;
import com.aetherteam.aetherii.client.renderer.entity.state.ArkeniumTalutonRenderState;
import com.aetherteam.aetherii.entity.monster.ArkeniumTaluton;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class ArkeniumTalutonRenderer extends MobRenderer<ArkeniumTaluton, ArkeniumTalutonRenderState, ArkeniumTalutonModel> {
    private static final Identifier ARKENIUM_TALUTON_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/arkenium_taluton/arkenium_taluton.png");

    public ArkeniumTalutonRenderer(EntityRendererProvider.Context context) {
        super(context, new ArkeniumTalutonModel(context.bakeLayer(AetherIIModelLayers.ARKENIUM_TALUTON)), 0.5F);
        this.addLayer(new ArkeniumTalutonEyesLayer(this));
    }

    @Override
    public ArkeniumTalutonRenderState createRenderState() {
        return new ArkeniumTalutonRenderState();
    }

    @Override
    public void extractRenderState(ArkeniumTaluton entity, ArkeniumTalutonRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.attackTicksRemaining = (float) entity.getAttackAnimationTick() > 0.0F ? (float) entity.getAttackAnimationTick() - partialTick : 0.0F;
    }

    @Override
    public Identifier getTextureLocation(ArkeniumTalutonRenderState renderState) {
        return ARKENIUM_TALUTON_TEXTURE;
    }
}
