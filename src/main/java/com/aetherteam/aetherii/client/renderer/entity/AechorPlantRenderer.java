package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.AechorPlantModel;
import com.aetherteam.aetherii.entity.monster.AechorPlant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AechorPlantRenderer extends MobRenderer<AechorPlant, AechorPlantModel> {
    private static final ResourceLocation AECHOR_PLANT_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/aechor_plant/aechor_plant.png");

    public AechorPlantRenderer(EntityRendererProvider.Context context) {
        super(context, new AechorPlantModel(context.bakeLayer(AetherIIModelLayers.AECHOR_PLANT)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(AechorPlant aechorPlant) {
        return AECHOR_PLANT_TEXTURE;
    }

    @Override
    public void render(AechorPlant entity, float entityYaw, float partialTicks, com.mojang.blaze3d.vertex.PoseStack poseStack, net.minecraft.client.renderer.MultiBufferSource buffer, int packedLight) {
        if (entity.deathTime <= 0) {
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        }
    }
}
