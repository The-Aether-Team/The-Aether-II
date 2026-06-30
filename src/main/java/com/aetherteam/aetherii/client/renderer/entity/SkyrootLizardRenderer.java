package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.SkyrootLizardModel;
import com.aetherteam.aetherii.entity.passive.SkyrootLizard;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SkyrootLizardRenderer extends MobRenderer<SkyrootLizard, SkyrootLizardModel<SkyrootLizard>> {
    public SkyrootLizardRenderer(EntityRendererProvider.Context context) {
        super(context, new SkyrootLizardModel<>(context.bakeLayer(AetherIIModelLayers.SKYROOT_LIZARD)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(SkyrootLizard lizard) {
        return lizard.getVariant().value().texture();
    }
}
