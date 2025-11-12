package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.SliderGlowLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SliderModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SliderRenderState;
import com.aetherteam.aetherii.entity.monster.dungeon.boss.Slider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public class SliderRenderer extends MobRenderer<Slider, SliderRenderState, SliderModel> {
    private static final ResourceLocation SLIDER_ASLEEP_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/slider/slider_asleep.png");
    private static final ResourceLocation SLIDER_ASLEEP_CRITICAL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/slider/slider_asleep_critical.png");
    private static final ResourceLocation SLIDER_AWAKE_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/slider/slider_awake.png");
    private static final ResourceLocation SLIDER_AWAKE_CRITICAL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/slider/slider_awake_critical.png");

    public SliderRenderer(EntityRendererProvider.Context context) {
        super(context, new SliderModel(context.bakeLayer(AetherIIModelLayers.SLIDER)), 0.7F);
        this.addLayer(new SliderGlowLayer(this));
    }

    @Override
    public SliderRenderState createRenderState() {
        return new SliderRenderState();
    }

    @Override
    public void extractRenderState(Slider slider, SliderRenderState sliderRenderState, float partialTick) {
        super.extractRenderState(slider, sliderRenderState, partialTick);
        sliderRenderState.awake = slider.isAwake();
        sliderRenderState.critical = slider.isCritical();
        sliderRenderState.hurtAngle = slider.getHurtAngle();
        if (slider.getHurtAngle() > 0.0) {
            slider.setHurtAngle(Mth.lerp(sliderRenderState.partialTick, slider.getHurtAngle(), slider.getHurtAngle() * 0.78F));
        }
        sliderRenderState.hurtAngleX = slider.getHurtAngleX();
        sliderRenderState.hurtAngleZ = slider.getHurtAngleZ();
    }

    @Override
    public void render(SliderRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int partialTick) {
        if (renderState.deathTime == 0) {
            super.render(renderState, poseStack, bufferSource, partialTick);
        }
    }

    @Override
    protected void setupRotations(SliderRenderState renderState, PoseStack poseStack, float bodyRot, float scale) {
        if (!Minecraft.getInstance().isPaused()) {
            if (renderState.hurtAngle != 0) {
                poseStack.mulPose(Axis.of(new Vector3f(renderState.hurtAngleX, 0.0F, -renderState.hurtAngleZ)).rotationDegrees(renderState.hurtAngle * -15.0F));
            }
            if (renderState.isUpsideDown) {
                poseStack.translate(0.0, renderState.boundingBoxHeight + 0.1F, 0.0);
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(SliderRenderState slider) {
        if (!slider.awake) {
            return !slider.critical ? SLIDER_ASLEEP_TEXTURE : SLIDER_ASLEEP_CRITICAL_TEXTURE;
        } else {
            return !slider.critical ? SLIDER_AWAKE_TEXTURE : SLIDER_AWAKE_CRITICAL_TEXTURE;
        }
    }
}
