package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.aetherteam.aetherii.client.renderer.entity.AerbunnyRenderer;
import com.aetherteam.aetherii.client.renderer.entity.model.AerbunnyModel;
import com.aetherteam.aetherii.client.renderer.entity.state.AerbunnyRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class AerbunnyCollarLayer extends RenderLayer<AerbunnyRenderState, AerbunnyModel> {
    private static final Identifier COLLAR_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/aerbunny/aerbunny_collar.png");
    private final AerbunnyModel model;

    public AerbunnyCollarLayer(RenderLayerParent<AerbunnyRenderState, AerbunnyModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new AerbunnyModel(modelSet.bakeLayer(AetherIIModelLayers.AERBUNNY_COLLAR));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AerbunnyRenderState renderState, float netHeadYaw, float headPitch) {
        if (renderState.tame) {
            int color = this.getColor(renderState);
            if (!renderState.isInvisible) {
                this.model.setupAnim(renderState);
                VertexConsumer vertexconsumer = buffer.getBuffer(AetherIIRenderTypes.entityDitherNoCull(COLLAR_LOCATION));
                this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(renderState, 0.0F), color);
            }
        }
    }

    protected int getColor(AerbunnyRenderState renderState) {
        int color = renderState.collarColor.getTextureDiffuseColor();
        float opacity = AerbunnyRenderer.calculateOpacity(renderState);
        if (opacity < 1.0F) {
            return ARGB.color(opacity, color);
        }
        return color;
    }
}
