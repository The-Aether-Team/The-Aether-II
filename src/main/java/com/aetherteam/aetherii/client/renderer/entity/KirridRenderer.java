package com.aetherteam.aetherii.client.renderer.entity;


import com.aetherteam.aetherii.client.renderer.BiomeVariantPresets;
import com.aetherteam.aetherii.client.renderer.entity.layers.KirridWoolLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.AbstractKirridBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.AbstractKirridModel;
import com.aetherteam.aetherii.client.renderer.entity.state.KirridRenderState;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class KirridRenderer extends MultiBabyModelRenderer<Kirrid, KirridRenderState, EntityModel<KirridRenderState>, AbstractKirridModel, AbstractKirridBabyModel> {
    private final ResourceLocation defaultTexture;
    private final ResourceLocation babyTexture;
    private final AbstractKirridModel defaultModel;
    private final AbstractKirridBabyModel babyModel;

    public KirridRenderer(EntityRendererProvider.Context context, BiomeVariantPresets preset) {
        super(context, (AbstractKirridModel) preset.getDefaultModel(context), 0.5F);
        this.defaultTexture = preset.getDefaultTexture();
        this.babyTexture = preset.getBabyTexture();
        this.defaultModel = (AbstractKirridModel) preset.getDefaultModel(context);
        this.babyModel = (AbstractKirridBabyModel) preset.getBabyModel(context);
        this.addLayer(new KirridWoolLayer(this));
    }

    @Override
    public AbstractKirridModel getDefaultModel() {
        return this.defaultModel;
    }

    @Override
    public AbstractKirridBabyModel getBabyModel() {
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
    public KirridRenderState createRenderState() {
        return new KirridRenderState();
    }

    @Override
    public void extractRenderState(Kirrid kirrid, KirridRenderState renderState, float p_361157_) {
        super.extractRenderState(kirrid, renderState, p_361157_);
        renderState.eatAnimationState.copyFrom(kirrid.eatAnimationState);
        renderState.jumpAnimationState.copyFrom(kirrid.jumpAnimationState);
        renderState.ramAnimationState.copyFrom(kirrid.ramAnimationState);
        renderState.plate = kirrid.hasPlate();
        renderState.wool = !kirrid.isSheared();
        renderState.entityType = kirrid.getType();
        renderState.id = kirrid.getId();
        kirrid.getColor().ifPresent(kirridColor -> renderState.woolColor = kirridColor);
    }
}