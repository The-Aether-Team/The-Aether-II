package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.bird.*;
import com.aetherteam.aetherii.client.renderer.entity.state.BirdRenderState;
import com.aetherteam.aetherii.entity.passive.Bird;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

import java.util.Map;

public class BirdRenderer extends MobRenderer<Bird, BirdRenderState, EntityModel<BirdRenderState>> {
    private final Map<Bird.BirdType, EntityModel<BirdRenderState>> birdModels = Maps.newHashMap();

    public BirdRenderer(EntityRendererProvider.Context context) {
        super(context, new ChonkModel(context.bakeLayer(AetherIIModelLayers.BIRD_CHONK)), 0.25F);
        this.birdModels.put(Bird.BirdType.CHONK, this.getModel());
        this.birdModels.put(Bird.BirdType.FINCH, new FinchModel(context.bakeLayer(AetherIIModelLayers.BIRD_FINCH)));
        this.birdModels.put(Bird.BirdType.MACAW, new MacawModel(context.bakeLayer(AetherIIModelLayers.BIRD_MACAW)));
        this.birdModels.put(Bird.BirdType.PHEASANT, new PheasantModel(context.bakeLayer(AetherIIModelLayers.BIRD_PHEASANT)));
        this.birdModels.put(Bird.BirdType.WARBLER, new WarblerModel(context.bakeLayer(AetherIIModelLayers.BIRD_WARBLER)));
    }

    @Override
    public void submit(BirdRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        this.model = this.birdModels.get(state.type);
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public BirdRenderState createRenderState() {
        return new BirdRenderState();
    }

    @Override
    public void extractRenderState(Bird bird, BirdRenderState state, float partialTicks) {
        super.extractRenderState(bird, state, partialTicks);
        state.texture = bird.getVariant().value().texture();
        state.emissiveTexture = bird.getVariant().value().emissiveTexture().orElse(null);
        state.type = bird.getVariant().value().type();
        state.rest = bird.isRest();
        float flap = Mth.lerp(partialTicks, bird.oFlap, bird.flap);
        float flapSpeed = Mth.lerp(partialTicks, bird.oFlapSpeed, bird.flapSpeed);
        state.flapAngle = (Mth.sin(flap) + 1.0F) * flapSpeed;
    }

    @Override
    public Identifier getTextureLocation(BirdRenderState renderState) {
        return renderState.texture;
    }
}
