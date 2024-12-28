package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.SkyrootLizardModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SkyrootLizardRenderState;
import com.aetherteam.aetherii.entity.passive.SkyrootLizard;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SkyrootLizardRenderer extends MobRenderer<SkyrootLizard, SkyrootLizardRenderState, SkyrootLizardModel<SkyrootLizardRenderState>> {
    private static final ResourceLocation SKYROOT_LIZARD_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/skyroot_lizard/skyroot_lizard.png");

    public SkyrootLizardRenderer(EntityRendererProvider.Context context) {
        super(context, new SkyrootLizardModel<>(context.bakeLayer(AetherIIModelLayers.SKYROOT_LIZARD)), 0.3F);
    }


    @Override
    public SkyrootLizardRenderState createRenderState() {
        return new SkyrootLizardRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(SkyrootLizardRenderState renderState) {
        return SKYROOT_LIZARD_TEXTURE;
    }
}
