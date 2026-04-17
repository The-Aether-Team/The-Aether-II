package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.projectile.VenomousDart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class VenomousDartRenderer extends ArrowRenderer<VenomousDart, ArrowRenderState> {
    private static final Identifier VENOMOUS_DART_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/venomous_dart.png");
    private static final Identifier VENOMOUS_DART_EMISSIVE_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/venomous_dart_emissive.png");

    private final ArrowModel model;

    public VenomousDartRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ArrowModel(context.bakeLayer(ModelLayers.ARROW));
    }

    @Override
    public void submit(ArrowRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot));
        submitNodeCollector.submitModel(this.model, renderState, poseStack, RenderTypes.eyes(this.getEmissiveTextureLocation(renderState)), renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.outlineColor, null);
        poseStack.popPose();
        super.submit(renderState, poseStack, submitNodeCollector, cameraRenderState);
    }


    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    public Identifier getTextureLocation(ArrowRenderState renderState) {
        return VENOMOUS_DART_TEXTURE;
    }

    public Identifier getEmissiveTextureLocation(ArrowRenderState renderState) {
        return VENOMOUS_DART_EMISSIVE_TEXTURE;
    }
}
