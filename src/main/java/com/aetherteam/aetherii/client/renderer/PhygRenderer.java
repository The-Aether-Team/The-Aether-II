package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.layer.QuadrupedWingsLayer;
import com.aetherteam.aetherii.client.renderer.model.QuadrupedWingsModel;
import com.aetherteam.aetherii.entity.passive.Phyg;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class PhygRenderer extends MobRenderer<Phyg, PigModel<Phyg>> {
    private static final ResourceLocation PHYG_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/phyg/phyg.png");

    public PhygRenderer(EntityRendererProvider.Context context) {
        super(context, new PigModel<>(context.bakeLayer(AetherModelLayers.PHYG)), 0.7F);
        this.addLayer(new QuadrupedWingsLayer<>(this, new QuadrupedWingsModel<>(context.bakeLayer(AetherModelLayers.PHYG_WINGS)), new ResourceLocation(AetherII.MODID, "textures/entity/mobs/phyg/phyg_wings.png")));
        this.addLayer(new SaddleLayer<>(this, new PigModel<>(context.bakeLayer(AetherModelLayers.PHYG_SADDLE)), new ResourceLocation("textures/entity/pig/pig_saddle.png")));
    }

    @Override
    public ResourceLocation getTextureLocation(Phyg phyg) {
        return PHYG_TEXTURE;
    }
}
