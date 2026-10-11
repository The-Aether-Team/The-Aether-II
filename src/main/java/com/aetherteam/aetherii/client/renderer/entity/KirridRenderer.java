package com.aetherteam.aetherii.client.renderer.entity;


import com.aetherteam.aetherii.client.renderer.BiomeVariantPresets;
import com.aetherteam.aetherii.client.renderer.entity.layers.KirridWoolLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.AbstractKirridBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.AbstractKirridModel;
import com.aetherteam.aetherii.client.renderer.entity.state.KirridRenderState;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class KirridRenderer extends AgeableMobRenderer<Kirrid, KirridRenderState, EntityModel<KirridRenderState>> {
    private final BiomeVariantPresets preset;

    public KirridRenderer(EntityRendererProvider.Context context, BiomeVariantPresets preset) {
        super(context, (AbstractKirridModel) preset.getDefaultModel(context), (AbstractKirridBabyModel) preset.getBabyModel(context), 0.5F);
        this.preset = preset;
        this.addLayer(new KirridWoolLayer(this));
    }

    @Override
    public KirridRenderState createRenderState() {
        return new KirridRenderState();
    }

    @Override
    public void extractRenderState(Kirrid kirrid, KirridRenderState renderState, float partialTick) {
        super.extractRenderState(kirrid, renderState, partialTick);
        renderState.eatAnimationState.copyFrom(kirrid.eatAnimationState);
        renderState.jumpAnimationState.copyFrom(kirrid.jumpAnimationState);
        renderState.ramAnimationState.copyFrom(kirrid.ramAnimationState);
        renderState.plate = kirrid.hasPlate();
        renderState.wool = !kirrid.isSheared();
        renderState.entityType = kirrid.getType();
        renderState.id = kirrid.getId();
        renderState.woolColor = kirrid.getColor().map(Kirrid::getDecimalColor);
    }

    @Override
    public Identifier getTextureLocation(KirridRenderState renderState) {
        return renderState.isBaby ? this.preset.getBabyTexture() : this.preset.getDefaultTexture();
    }
}