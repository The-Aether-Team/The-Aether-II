package com.aetherteam.aetherii.client.renderer.entity;


import com.aetherteam.aetherii.client.renderer.BiomeVariantPresets;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.AbstractBurrukaiBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.AbstractBurrukaiModel;
import com.aetherteam.aetherii.client.renderer.entity.state.BurrukaiRenderState;
import com.aetherteam.aetherii.entity.passive.Burrukai;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class BurrukaiRenderer extends AgeableMobRenderer<Burrukai, BurrukaiRenderState, EntityModel<BurrukaiRenderState>> {
    private final BiomeVariantPresets preset;

    public BurrukaiRenderer(EntityRendererProvider.Context context, BiomeVariantPresets preset) {
        super(context, (AbstractBurrukaiModel) preset.getDefaultModel(context), (AbstractBurrukaiBabyModel) preset.getBabyModel(context), 0.75F);
        this.preset = preset;
    }

    @Override
    public BurrukaiRenderState createRenderState() {
        return new BurrukaiRenderState();
    }

    @Override
    public void extractRenderState(Burrukai burrukai, BurrukaiRenderState renderState, float partialTick) {
        super.extractRenderState(burrukai, renderState, partialTick);
        renderState.ramAnimationState.copyFrom(burrukai.ramAnimationState);
    }

    @Override
    public Identifier getTextureLocation(BurrukaiRenderState renderState) {
        return renderState.isBaby ? this.preset.getBabyTexture() : this.preset.getDefaultTexture();
    }
}