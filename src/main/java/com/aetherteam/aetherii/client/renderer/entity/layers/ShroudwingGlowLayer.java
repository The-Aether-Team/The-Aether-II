package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.renderer.entity.model.ShroudwingModel;
import com.aetherteam.aetherii.entity.passive.Shroudwing;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class ShroudwingGlowLayer extends RenderLayer<Shroudwing, ShroudwingModel> {
    public ShroudwingGlowLayer(RenderLayerParent<Shroudwing, ShroudwingModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Shroudwing shroudwing, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!shroudwing.isInvisible()) {
            shroudwing.getVariant().value().emissiveTexture().ifPresent(texture -> {
                VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.eyes(texture));
                this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 15728640, LivingEntityRenderer.getOverlayCoords(shroudwing, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
            });
        }
    }
}
