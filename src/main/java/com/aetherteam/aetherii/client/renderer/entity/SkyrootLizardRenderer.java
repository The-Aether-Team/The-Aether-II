package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.SkyrootLizardModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SkyrootLizardRenderState;
import com.aetherteam.aetherii.entity.passive.SkyrootLizard;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class SkyrootLizardRenderer extends MobRenderer<SkyrootLizard, SkyrootLizardRenderState, SkyrootLizardModel<SkyrootLizardRenderState>> {
    public SkyrootLizardRenderer(EntityRendererProvider.Context context) {
        super(context, new SkyrootLizardModel<>(context.bakeLayer(AetherIIModelLayers.SKYROOT_LIZARD)), 0.3F);
    }

    @Override
    public SkyrootLizardRenderState createRenderState() {
        return new SkyrootLizardRenderState();
    }

    @Override
    public void extractRenderState(SkyrootLizard entity, SkyrootLizardRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.texture = entity.getVariant().value().texture();
    }

    @Override
    public Identifier getTextureLocation(SkyrootLizardRenderState renderState) {
        return renderState.texture;
    }
}
