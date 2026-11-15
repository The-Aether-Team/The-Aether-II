package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.cherub.*;
import com.aetherteam.aetherii.client.renderer.entity.state.CherubRenderState;
import com.aetherteam.aetherii.entity.passive.Cherub;
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

public class CherubRenderer extends MobRenderer<Cherub, CherubRenderState, EntityModel<CherubRenderState>> {
    private final Map<Cherub.CherubType, EntityModel<CherubRenderState>> cherubModels = Maps.newHashMap();

    public CherubRenderer(EntityRendererProvider.Context context) {
        super(context, new ChonkModel(context.bakeLayer(AetherIIModelLayers.CHERUB_CHONK)), 0.25F);
        this.cherubModels.put(Cherub.CherubType.CHONK, this.getModel());
        this.cherubModels.put(Cherub.CherubType.FINCH, new FinchModel(context.bakeLayer(AetherIIModelLayers.CHERUB_FINCH)));
        this.cherubModels.put(Cherub.CherubType.MACAW, new MacawModel(context.bakeLayer(AetherIIModelLayers.CHERUB_MACAW)));
        this.cherubModels.put(Cherub.CherubType.PHEASANT, new PheasantModel(context.bakeLayer(AetherIIModelLayers.CHERUB_PHEASANT)));
        this.cherubModels.put(Cherub.CherubType.WARBLER, new WarblerModel(context.bakeLayer(AetherIIModelLayers.CHERUB_WARBLER)));
    }

    @Override
    public void submit(CherubRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        this.model = this.cherubModels.get(state.type);
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public CherubRenderState createRenderState() {
        return new CherubRenderState();
    }

    @Override
    public void extractRenderState(Cherub cherub, CherubRenderState state, float partialTicks) {
        super.extractRenderState(cherub, state, partialTicks);
        state.texture = cherub.getVariant().value().texture();
        state.type = cherub.getVariant().value().type();
        state.flying = cherub.isFlying();
        float flap = Mth.lerp(partialTicks, cherub.oFlap, cherub.flap);
        float flapSpeed = Mth.lerp(partialTicks, cherub.oFlapSpeed, cherub.flapSpeed);
        state.flapAngle = (Mth.sin(flap) + 1.0F) * flapSpeed;
    }

    @Override
    public Identifier getTextureLocation(CherubRenderState renderState) {
        return renderState.texture;
    }
}
