package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.SliderGlowLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SliderModel;
import com.aetherteam.aetherii.entity.monster.dungeon.boss.Slider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class SliderRenderer extends MobRenderer<Slider, SliderModel> {
    private static final ResourceLocation SLIDER_ASLEEP_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/slider/slider_asleep.png");
    private static final ResourceLocation SLIDER_AWAKE_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/slider/slider_awake.png");
    private static final ResourceLocation SLIDER_AWAKE_CRITICAL_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/slider/slider_awake_critical.png");
    private static final float HALF_SQRT_3 = (float) (Math.sqrt(3.0) / 2.0);

    public SliderRenderer(EntityRendererProvider.Context context) {
        super(context, new SliderModel(context.bakeLayer(AetherIIModelLayers.SLIDER)), 0.7F);
        this.addLayer(new SliderGlowLayer(this));
    }

    @Override
    public void render(Slider slider, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        float deathTime = slider.sliderDeathTime > 0 ? slider.sliderDeathTime + partialTick : 0.0F;
        if (slider.getHurtAngle() > 0.0) {
            slider.setHurtAngle(Mth.lerp(partialTick, slider.getHurtAngle(), slider.getHurtAngle() * 0.78F));
        }
        super.render(slider, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        if (deathTime > 0.0F) {
            poseStack.pushPose();
            poseStack.translate(0.0F, 1.0F, 0.0F);
            renderRays(poseStack, bufferSource, deathTime / 300.0F);
            poseStack.popPose();
        }
    }

    private static void renderRays(PoseStack poseStack, MultiBufferSource bufferSource, float deathProgress) {
        float fade = Math.min(deathProgress > 0.8F ? (deathProgress - 0.8F) / 0.2F : 0.0F, 1.0F);
        RandomSource random = RandomSource.create(432L);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.lightning());
        int rays = Mth.floor((deathProgress + deathProgress * deathProgress) / 2.0F * 60.0F);
        for (int i = 0; i < rays; ++i) {
            poseStack.mulPose(Axis.XP.rotationDegrees(random.nextFloat() * 360.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(random.nextFloat() * 360.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(random.nextFloat() * 360.0F));
            poseStack.mulPose(Axis.XP.rotationDegrees(random.nextFloat() * 360.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(random.nextFloat() * 360.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(random.nextFloat() * 360.0F + deathProgress * 90.0F));
            float length = random.nextFloat() * 20.0F + 5.0F + fade * 10.0F;
            float width = random.nextFloat() * 2.0F + 1.0F + fade * 2.0F;
            Matrix4f matrix4f = poseStack.last().pose();
            int alpha = (int) (255.0F * (1.0F - fade));
            vertex01(vertexConsumer, matrix4f, alpha);
            vertex2(vertexConsumer, matrix4f, length, width);
            vertex3(vertexConsumer, matrix4f, length, width);
            vertex01(vertexConsumer, matrix4f, alpha);
            vertex3(vertexConsumer, matrix4f, length, width);
            vertex4(vertexConsumer, matrix4f, length, width);
            vertex01(vertexConsumer, matrix4f, alpha);
            vertex4(vertexConsumer, matrix4f, length, width);
            vertex2(vertexConsumer, matrix4f, length, width);
        }
    }

    private static void vertex01(VertexConsumer vertexConsumer, Matrix4f matrix4f, int alpha) {
        vertexConsumer.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color(255, 255, 255, alpha).endVertex();
    }

    private static void vertex2(VertexConsumer vertexConsumer, Matrix4f matrix4f, float length, float width) {
        vertexConsumer.vertex(matrix4f, -HALF_SQRT_3 * width, length, -0.5F * width).color(255, 0, 255, 0).endVertex();
    }

    private static void vertex3(VertexConsumer vertexConsumer, Matrix4f matrix4f, float length, float width) {
        vertexConsumer.vertex(matrix4f, HALF_SQRT_3 * width, length, -0.5F * width).color(255, 0, 255, 0).endVertex();
    }

    private static void vertex4(VertexConsumer vertexConsumer, Matrix4f matrix4f, float length, float width) {
        vertexConsumer.vertex(matrix4f, 0.0F, length, width).color(255, 0, 255, 0).endVertex();
    }

    @Override
    protected void setupRotations(Slider slider, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        if (!Minecraft.getInstance().isPaused()) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));

            if (slider.getHurtAngle() != 0) {
                poseStack.mulPose(Axis.of(new Vector3f(slider.getHurtAngleX(), 0.0F, -slider.getHurtAngleZ())).rotationDegrees(slider.getHurtAngle() * -15.0F));
            }

            float deathTime = slider.sliderDeathTime > 0 ? slider.sliderDeathTime + partialTick : 0.0F;
            float f2 = deathTime / 150.0F;

            if (deathTime > 0) {
                poseStack.mulPose(Axis.YP.rotationDegrees((float) (Math.cos(Mth.floor(ageInTicks) * 3.25F) * Math.PI * 0.4F) * f2));
            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Slider slider) {
        if (!slider.isAwake()) {
            return SLIDER_ASLEEP_TEXTURE;
        } else {
            return !slider.isCritical() ? SLIDER_AWAKE_TEXTURE : SLIDER_AWAKE_CRITICAL_TEXTURE;
        }
    }
}
