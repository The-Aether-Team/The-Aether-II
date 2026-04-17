package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.AechorPlantModel;
import com.aetherteam.aetherii.client.renderer.entity.state.AechorPlantRenderState;
import com.aetherteam.aetherii.entity.monster.AechorPlant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
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
    public void submit(AechorPlantRenderState renderState, PoseStack p_434615_, SubmitNodeCollector p_433768_, CameraRenderState p_450931_) {
        if (renderState.deathTime <= 0) {
            super.submit(renderState, p_434615_, p_433768_, p_450931_);
        }
    }

    @Override
    public Identifier getTextureLocation(AechorPlantRenderState renderState) {
        return AECHOR_PLANT_TEXTURE;
    }
}
