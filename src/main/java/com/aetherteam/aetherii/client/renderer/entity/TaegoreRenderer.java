package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.client.renderer.BiomeVariantPresets;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.TaegoreBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.TaegoreModel;
import com.aetherteam.aetherii.client.renderer.entity.state.TaegoreRenderState;
import com.aetherteam.aetherii.entity.passive.Taegore;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class TaegoreRenderer extends MultiBabyModelRenderer<Taegore, TaegoreRenderState, EntityModel<TaegoreRenderState>, TaegoreModel, TaegoreBabyModel> {
    private final Identifier defaultTexture;
    private final Identifier babyTexture;
    private final TaegoreModel defaultModel;
    private final TaegoreBabyModel babyModel;

    public TaegoreRenderer(EntityRendererProvider.Context context, BiomeVariantPresets preset) {
        super(context, (TaegoreModel) preset.getDefaultModel(context), 0.55F);
        this.defaultTexture = preset.getDefaultTexture();
        this.babyTexture = preset.getBabyTexture();
        this.defaultModel = (TaegoreModel) preset.getDefaultModel(context);
        this.babyModel = (TaegoreBabyModel) preset.getBabyModel(context);
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
    public TaegoreModel getDefaultModel(TaegoreRenderState taegore) {
        return this.defaultModel;
    }

    @Override
    public TaegoreBabyModel getBabyModel(TaegoreRenderState taegore) {
        return this.babyModel;
    }

    @Override
    public Identifier getDefaultTexture(TaegoreRenderState taegore) {
        return this.defaultTexture;
    }

    @Override
    public Identifier getBabyTexture(TaegoreRenderState taegore) {
        return this.babyTexture;
    }
}