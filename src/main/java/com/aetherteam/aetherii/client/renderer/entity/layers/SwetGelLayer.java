package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.renderer.entity.model.SwetModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SwetRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;

public abstract class SwetGelLayer extends RenderLayer<SwetRenderState, SwetModel> {
    private final SwetModel model;

    public SwetGelLayer(RenderLayerParent<SwetRenderState, SwetModel> renderer, EntityModelSet modelSet, SwetModel model) {
        super(renderer);
        this.model = model;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, SwetRenderState livingEntity, float netHeadYaw, float headPitch) {
        boolean flag = livingEntity.appearsGlowing && livingEntity.isInvisible;
        if (!livingEntity.isInvisible || flag) {
            VertexConsumer vertexconsumer = flag
                    ? bufferSource.getBuffer(RenderType.outline(this.getTextureLocation(livingEntity)))
                    : bufferSource.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(livingEntity)));
            this.model.setupAnim(livingEntity);
            this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F));
        }
    }

    public abstract Identifier getTextureLocation(SwetRenderState swetRenderState);
}
