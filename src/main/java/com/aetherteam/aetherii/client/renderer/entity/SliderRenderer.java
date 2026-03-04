package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.SliderGlowLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SliderModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SliderRenderState;
import com.aetherteam.aetherii.entity.monster.dungeon.boss.Slider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class SliderRenderer extends MobRenderer<Slider, SliderRenderState, SliderModel> {
    private static final Identifier SLIDER_ASLEEP_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/slider/slider_asleep.png");
    private static final Identifier SLIDER_AWAKE_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/slider/slider_awake.png");
    private static final Identifier SLIDER_AWAKE_CRITICAL_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/slider/slider_awake_critical.png");

    private static final float HALF_SQRT_3 = (float) (Math.sqrt(3.0) / 2.0);

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
        sliderRenderState.deathTime = slider.sliderDeathTime > 0 ? slider.sliderDeathTime + partialTick : 0.0F;
    }

    @Override
    public void submit(SliderRenderState sliderRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (sliderRenderState.deathTime > 0) {
            float f2 = sliderRenderState.deathTime / 300.0F;
            poseStack.pushPose();
            poseStack.translate(0.0F, 1.0F, 0.0F);
            submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.dragonRays(), (pose, vertexConsumer) -> {
                renderRays(poseStack, f2, vertexConsumer);

            });
            submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.dragonRaysDepth(), (pose, vertexConsumer) -> {
                renderRays(poseStack, f2, vertexConsumer);

            });
            poseStack.popPose();
        }

        super.submit(sliderRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    private static void renderRays(PoseStack poseStack, float deathCompletion, VertexConsumer buffer) {
        poseStack.pushPose();
        float f = Math.min(deathCompletion > 0.8F ? (deathCompletion - 0.8F) / 0.2F : 0.0F, 1.0F);
        int i = ARGB.colorFromFloat(1.0F - f, 1.0F, 0.5F, 0.5F);
        int j = 16711680;
        RandomSource randomsource = RandomSource.create(432L);
        Vector3f vector3f = new Vector3f();
        Vector3f vector3f1 = new Vector3f();
        Vector3f vector3f2 = new Vector3f();
        Vector3f vector3f3 = new Vector3f();
        Quaternionf quaternionf = new Quaternionf();
        int k = Mth.floor((deathCompletion + deathCompletion * deathCompletion) / 2.0F * 60.0F);

        for (int l = 0; l < k; l++) {
            quaternionf.rotationXYZ(
                            randomsource.nextFloat() * (float) (Math.PI * 2),
                            randomsource.nextFloat() * (float) (Math.PI * 2),
                            randomsource.nextFloat() * (float) (Math.PI * 2)
                    )
                    .rotateXYZ(
                            randomsource.nextFloat() * (float) (Math.PI * 2),
                            randomsource.nextFloat() * (float) (Math.PI * 2),
                            randomsource.nextFloat() * (float) (Math.PI * 2) + deathCompletion * (float) (Math.PI / 2)
                    );
            poseStack.mulPose(quaternionf);
            float f1 = randomsource.nextFloat() * 20.0F + 5.0F + f * 10.0F;
            float f2 = randomsource.nextFloat() * 2.0F + 1.0F + f * 2.0F;
            vector3f1.set(-HALF_SQRT_3 * f2, f1, -0.5F * f2);
            vector3f2.set(HALF_SQRT_3 * f2, f1, -0.5F * f2);
            vector3f3.set(0.0F, f1, f2);
            PoseStack.Pose posestack$pose = poseStack.last();
            buffer.addVertex(posestack$pose, vector3f).setColor(i);
            buffer.addVertex(posestack$pose, vector3f1).setColor(j);
            buffer.addVertex(posestack$pose, vector3f2).setColor(j);
            buffer.addVertex(posestack$pose, vector3f).setColor(i);
            buffer.addVertex(posestack$pose, vector3f2).setColor(j);
            buffer.addVertex(posestack$pose, vector3f3).setColor(j);
            buffer.addVertex(posestack$pose, vector3f).setColor(i);
            buffer.addVertex(posestack$pose, vector3f3).setColor(j);
            buffer.addVertex(posestack$pose, vector3f1).setColor(j);
        }

        poseStack.popPose();
    }

    @Override
    protected void setupRotations(SliderRenderState renderState, PoseStack poseStack, float bodyRot, float scale) {
        if (!Minecraft.getInstance().isPaused()) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - bodyRot));

            if (renderState.hurtAngle != 0) {
                poseStack.mulPose(Axis.of(new Vector3f(renderState.hurtAngleX, 0.0F, -renderState.hurtAngleZ)).rotationDegrees(renderState.hurtAngle * -15.0F));
            }

            float f2 = renderState.deathTime / 150.0F;

            if (renderState.deathTime > 0) {
                poseStack.mulPose(Axis.YP.rotationDegrees((float) (Math.cos(Mth.floor(renderState.ageInTicks) * 3.25F) * Math.PI * 0.4F) * f2));
            }
            if (renderState.isUpsideDown) {
                poseStack.translate(0.0, renderState.boundingBoxHeight + 0.1F, 0.0);
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            }
        }
    }

    @Override
    public Identifier getTextureLocation(SliderRenderState slider) {
        if (!slider.awake) {
            return SLIDER_ASLEEP_TEXTURE;
        } else {
            return !slider.critical ? SLIDER_AWAKE_TEXTURE : SLIDER_AWAKE_CRITICAL_TEXTURE;
        }
    }
}
