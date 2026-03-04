package com.aetherteam.aetherii.client.renderer.entity;


import com.aetherteam.aetherii.client.renderer.BiomeVariantPresets;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.AbstractBurrukaiBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.AbstractBurrukaiModel;
import com.aetherteam.aetherii.client.renderer.entity.state.BurrukaiRenderState;
import com.aetherteam.aetherii.entity.passive.Burrukai;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class BurrukaiRenderer extends MultiBabyModelRenderer<Burrukai, BurrukaiRenderState, EntityModel<BurrukaiRenderState>, AbstractBurrukaiModel, AbstractBurrukaiBabyModel> {
    private final Identifier defaultTexture;
    private final Identifier babyTexture;
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
    public AbstractBurrukaiModel getDefaultModel(BurrukaiRenderState burrukai) {
        return this.defaultModel;
    }

    @Override
    public AbstractBurrukaiBabyModel getBabyModel(BurrukaiRenderState burrukai) {
        return this.babyModel;
    }

    @Override
    public Identifier getDefaultTexture(BurrukaiRenderState burrukai) {
        return this.defaultTexture;
    }

    @Override
    public Identifier getBabyTexture(BurrukaiRenderState burrukai) {
        return this.babyTexture;
    }

    @Override
    public void extractRenderState(Burrukai burrukai, BurrukaiRenderState renderState, float partialTick) {
        super.extractRenderState(burrukai, renderState, partialTick);
        renderState.ramAnimationState.copyFrom(burrukai.ramAnimationState);
    }

    @Override
    public BurrukaiRenderState createRenderState() {
        return new BurrukaiRenderState();
    }
}