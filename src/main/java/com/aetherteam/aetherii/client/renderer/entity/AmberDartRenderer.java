package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.state.AmberDartRenderState;
import com.aetherteam.aetherii.entity.projectile.AmberDart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class AmberDartRenderer extends ArrowRenderer<AmberDart, AmberDartRenderState> {
    private static final Identifier AMBER_DART_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/amber_dart.png");
    private static final Identifier AMBER_DART_OVERLAY_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/amber_dart_overlay.png");

    private final ArrowModel model;

    public AmberDartRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ArrowModel(context.bakeLayer(ModelLayers.ARROW));
    }

    @Override
    public void submit(AmberDartRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot));
        this.model.setupAnim(renderState);
        submitNodeCollector.submitModel(this.model, renderState, poseStack, RenderTypes.entityCutout(AMBER_DART_OVERLAY_TEXTURE), renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.color, null);
        poseStack.popPose();

        super.submit(renderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    public AmberDartRenderState createRenderState() {
        return new AmberDartRenderState();
    }

    @Override
    public void extractRenderState(AmberDart dart, AmberDartRenderState renderState, float partialTick) {
        super.extractRenderState(dart, renderState, partialTick);
        renderState.color = dart.getColor();
    }

    @Override
    public Identifier getTextureLocation(AmberDartRenderState dart) {
        return AMBER_DART_TEXTURE;
    }
}
