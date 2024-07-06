package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.AechorPlantModel;
import com.aetherteam.aetherii.entity.monster.AechorPlant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AechorPlantRenderer extends MobRenderer<AechorPlant, AechorPlantModel> {
    private static final ResourceLocation AECHOR_PLANT_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/aechor_plant/aechor_plant.png");

    public AechorPlantRenderer(EntityRendererProvider.Context context) {
        super(context, new AechorPlantModel(context.bakeLayer(AetherIIModelLayers.AECHOR_PLANT)), 0.3F);
    }

    @Override
    protected void scale(AechorPlant aechorPlant, PoseStack poseStack, float partialTicks) {
        poseStack.translate(0.0, 1.2, 0.0);
    }

    @Override
    public ResourceLocation getTextureLocation(AechorPlant aechorPlant) {
        return AECHOR_PLANT_TEXTURE;
    }
}
