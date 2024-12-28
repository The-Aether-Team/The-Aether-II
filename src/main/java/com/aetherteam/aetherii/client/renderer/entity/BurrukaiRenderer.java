package com.aetherteam.aetherii.client.renderer.entity;


import com.aetherteam.aetherii.client.renderer.BiomeVariantPresets;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.AbstractBurrukaiBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.AbstractBurrukaiModel;
import com.aetherteam.aetherii.client.renderer.entity.state.BurrukaiRenderState;
import com.aetherteam.aetherii.entity.passive.Burrukai;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BurrukaiRenderer extends MultiBabyModelRenderer<Burrukai, BurrukaiRenderState, EntityModel<BurrukaiRenderState>, AbstractBurrukaiModel, AbstractBurrukaiBabyModel> {
    private final ResourceLocation defaultTexture;
    private final ResourceLocation babyTexture;
    private final AbstractBurrukaiModel defaultModel;
    private final AbstractBurrukaiBabyModel babyModel;

    public BurrukaiRenderer(EntityRendererProvider.Context context, BiomeVariantPresets preset) {
        super(context, (AbstractBurrukaiModel) preset.getDefaultModel(context), 0.75F);
        this.defaultTexture = preset.getDefaultTexture();
        this.babyTexture = preset.getBabyTexture();
        this.defaultModel = (AbstractBurrukaiModel) preset.getDefaultModel(context);
        this.babyModel = (AbstractBurrukaiBabyModel) preset.getBabyModel(context);
    }

    @Override
    public AbstractBurrukaiModel getDefaultModel() {
        return this.defaultModel;
    }

    @Override
    public AbstractBurrukaiBabyModel getBabyModel() {
        return this.babyModel;
    }

    @Override
    public ResourceLocation getDefaultTexture() {
        return this.defaultTexture;
    }

    @Override
    public ResourceLocation getBabyTexture() {
        return this.babyTexture;
    }

    @Override
    public void extractRenderState(Burrukai p_362733_, BurrukaiRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.ramAnimationState.copyFrom(p_362733_.ramAnimationState);
        p_360515_.rushAnimationState.copyFrom(p_362733_.rushAnimationState);
    }

    @Override
    public BurrukaiRenderState createRenderState() {
        return new BurrukaiRenderState();
    }
}