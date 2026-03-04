package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.AechorPlantModel;
import com.aetherteam.aetherii.client.renderer.entity.state.AechorPlantRenderState;
import com.aetherteam.aetherii.entity.monster.AechorPlant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class AechorPlantRenderer extends MobRenderer<AechorPlant, AechorPlantRenderState, AechorPlantModel> {
    private static final Identifier AECHOR_PLANT_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/aechor_plant/aechor_plant.png");

    public AechorPlantRenderer(EntityRendererProvider.Context context) {
        super(context, new AechorPlantModel(context.bakeLayer(AetherIIModelLayers.AECHOR_PLANT)), 0.3F);
    }

    @Override
    public AechorPlantRenderState createRenderState() {
        return new AechorPlantRenderState();
    }

    @Override
    public void extractRenderState(AechorPlant aechorPlant, AechorPlantRenderState aechorPlantRenderState, float partialTick) {
        super.extractRenderState(aechorPlant, aechorPlantRenderState, partialTick);
        aechorPlantRenderState.attackAnimationState.copyFrom(aechorPlant.attackAnimationState);
    }

    @Override
    public void render(AechorPlantRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int partialTick) {
        if (renderState.deathTime <= 0) {
            super.render(renderState, poseStack, bufferSource, partialTick);
        }
    }

    @Override
    public Identifier getTextureLocation(AechorPlantRenderState renderState) {
        return AECHOR_PLANT_TEXTURE;
    }
}
