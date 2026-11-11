package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.client.renderer.entity.state.AcidSpitRenderState;
import com.aetherteam.aetherii.entity.projectile.AcidSpit;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.animal.llama.LlamaSpitModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class AcidSpitRenderer extends EntityRenderer<AcidSpit, AcidSpitRenderState> {
    //TODO Acid Spit Texture
    private static final Identifier ACID_SPIT_LOCATION = Identifier.withDefaultNamespace("textures/entity/llama/llama_spit.png");
    private final LlamaSpitModel model;

    public AcidSpitRenderer(EntityRendererProvider.Context context) {
        super(context);
        //TODO NEED Acid Spit Model
        this.model = new LlamaSpitModel(context.bakeLayer(ModelLayers.LLAMA_SPIT));
    }

    @Override
    public void submit(AcidSpitRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.15F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(state.xRot));
        submitNodeCollector.submitModel(this.model, state, poseStack, ACID_SPIT_LOCATION, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, (ModelFeatureRenderer.CrumblingOverlay) null);
        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public AcidSpitRenderState createRenderState() {
        return new AcidSpitRenderState();
    }

    @Override
    public void extractRenderState(AcidSpit entity, AcidSpitRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.xRot = entity.getXRot(partialTicks);
        state.yRot = entity.getYRot(partialTicks);
    }
}
