package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.PrismallardModel;
import com.aetherteam.aetherii.entity.passive.Prismallard;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PrismallardRenderer extends MobRenderer<Prismallard, PrismallardModel<Prismallard>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/prismallard/prismallard.png");

    public PrismallardRenderer(EntityRendererProvider.Context context) {
        super(context, new PrismallardModel<>(context.bakeLayer(AetherIIModelLayers.PRISMALLARD)), 0.35F);
    }

    @Override
    public ResourceLocation getTextureLocation(Prismallard prismallard) {
        return LOCATION;
    }
}
