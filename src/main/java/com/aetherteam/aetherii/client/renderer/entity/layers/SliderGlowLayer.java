package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.SliderModel;
import com.aetherteam.aetherii.entity.monster.dungeon.boss.Slider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class SliderGlowLayer extends RenderLayer<Slider, SliderModel> {
    private static final RenderType SLIDER_AWAKE_EMISSIVE = RenderType.eyes(new ResourceLocation(AetherII.MODID, "textures/entity/mobs/slider/slider_awake_emissive.png"));
    private static final RenderType SLIDER_AWAKE_CRITICAL_EMISSIVE = RenderType.eyes(new ResourceLocation(AetherII.MODID, "textures/entity/mobs/slider/slider_awake_critical_emissive.png"));

    public SliderGlowLayer(RenderLayerParent<Slider, SliderModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Slider slider, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (slider.isAwake() && !slider.isInvisible()) {
            VertexConsumer vertexConsumer = buffer.getBuffer(slider.isCritical() ? SLIDER_AWAKE_CRITICAL_EMISSIVE : SLIDER_AWAKE_EMISSIVE);
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 15728640, LivingEntityRenderer.getOverlayCoords(slider, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
