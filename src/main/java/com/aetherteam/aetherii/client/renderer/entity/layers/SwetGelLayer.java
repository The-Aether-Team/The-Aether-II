package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.SwetRenderer;
import com.aetherteam.aetherii.client.renderer.entity.model.SwetModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SwetRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class SwetGelLayer extends RenderLayer<SwetRenderState, SwetModel<SwetRenderState>> {
    public static ResourceLocation EYES_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/swet/blue_swet.png");

    private final SwetModel<SwetRenderState> model;

    public SwetGelLayer(RenderLayerParent<SwetRenderState, SwetModel<SwetRenderState>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new SwetModel<>(modelSet.bakeLayer(AetherIIModelLayers.SWET));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, SwetRenderState livingEntity, float netHeadYaw, float headPitch) {
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag = livingEntity.appearsGlowing && livingEntity.isInvisible;
        if (!livingEntity.isInvisible || flag) {
            VertexConsumer vertexconsumer;
            if (flag) {
                vertexconsumer = bufferSource.getBuffer(RenderType.outline(SwetRenderer.SWET_LOCATION));
            } else {
                vertexconsumer = bufferSource.getBuffer(RenderType.entityTranslucent(SwetRenderer.SWET_LOCATION));
            }
            this.model.setupAnim(livingEntity);
            this.model.head.visible = false;
            this.model.gel.visible = true;
            this.model.squish.visible = true;
            this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F));
        }
    }
}
