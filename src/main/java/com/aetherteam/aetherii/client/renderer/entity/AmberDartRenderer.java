package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.state.AmberDartRenderState;
import com.aetherteam.aetherii.entity.projectile.AmberDart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArrowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
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

    public void render(AmberDartRenderState renderState, PoseStack poseStack, MultiBufferSource buffer, int partialTick) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot));
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutout(AMBER_DART_OVERLAY_TEXTURE));
        this.model.setupAnim(renderState);
        this.model.renderToBuffer(poseStack, consumer, partialTick, OverlayTexture.NO_OVERLAY, renderState.color);
        poseStack.popPose();
        super.render(renderState, poseStack, buffer, partialTick);
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
