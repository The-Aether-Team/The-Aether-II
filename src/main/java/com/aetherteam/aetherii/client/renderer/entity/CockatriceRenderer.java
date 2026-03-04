package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.CockatriceEmissiveLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.CockatriceModel;
import com.aetherteam.aetherii.client.renderer.entity.state.CockatriceRenderState;
import com.aetherteam.aetherii.entity.monster.Cockatrice;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class CockatriceRenderer extends MobRenderer<Cockatrice, CockatriceRenderState, CockatriceModel> {
    private static final Identifier COCKATRICE_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/cockatrice/cockatrice.png");

    public CockatriceRenderer(EntityRendererProvider.Context context) {
        super(context, new CockatriceModel(context.bakeLayer(AetherIIModelLayers.COCKATRICE)), 0.3F);
        this.addLayer(new CockatriceEmissiveLayer(this));
    }

    @Override
    public CockatriceRenderState createRenderState() {
        return new CockatriceRenderState();
    }

    @Override
    public void extractRenderState(Cockatrice cockatrice, CockatriceRenderState renderState, float partialTick) {
        super.extractRenderState(cockatrice, renderState, partialTick);
        renderState.clawAttackAnimationState.copyFrom(cockatrice.clawAttackAnimationState);
        renderState.dartAttackAnimationState.copyFrom(cockatrice.dartAttackAnimationState);
        renderState.digAnimationState.copyFrom(cockatrice.digAnimationState);
    }

    @Override
    public Identifier getTextureLocation(CockatriceRenderState p_368654_) {
        return COCKATRICE_TEXTURE;
    }
}
