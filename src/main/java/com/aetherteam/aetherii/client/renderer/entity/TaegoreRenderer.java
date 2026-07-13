package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.client.renderer.BiomeVariantPresets;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.TaegoreBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.TaegoreModel;
import com.aetherteam.aetherii.client.renderer.entity.state.TaegoreRenderState;
import com.aetherteam.aetherii.entity.passive.Taegore;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class TaegoreRenderer extends AgeableMobRenderer<Taegore, TaegoreRenderState, EntityModel<TaegoreRenderState>> {
    private final BiomeVariantPresets preset;

    public TaegoreRenderer(EntityRendererProvider.Context context, BiomeVariantPresets preset) {
        super(context, (TaegoreModel) preset.getDefaultModel(context), (TaegoreBabyModel) preset.getBabyModel(context), 0.55F);
        this.preset = preset;
    }

    @Override
    public TaegoreRenderState createRenderState() {
        return new TaegoreRenderState();
    }

    @Override
    public void extractRenderState(Taegore taegore, TaegoreRenderState renderState, float partialTick) {
        super.extractRenderState(taegore, renderState, partialTick);
        renderState.digAnimationState.copyFrom(taegore.digAnimationState);
        renderState.digStartAnimationState.copyFrom(taegore.digStartAnimationState);
        renderState.digEndAnimationState.copyFrom(taegore.digEndAnimationState);
    }

    @Override
    public Identifier getTextureLocation(TaegoreRenderState renderState) {
        return renderState.isBaby ? this.preset.getBabyTexture() : this.preset.getDefaultTexture();
    }
}