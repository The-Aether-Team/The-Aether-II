package com.aetherteam.aetherii.client.renderer.entity;

import java.util.UUID;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.AerwhaleModel;
import com.aetherteam.aetherii.entity.passive.Aerwhale;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;

public class AerwhaleRenderer extends MobRenderer<Aerwhale, LivingEntityRenderState, AerwhaleModel> {
    private static final ResourceLocation AERWHALE_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/aerwhale/aerwhale.png");
    
    public AerwhaleRenderer(EntityRendererProvider.Context context) {
        super(context, new AerwhaleModel(context.bakeLayer(AetherIIModelLayers.AERWHALE)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(LivingEntityRenderState renderState) {
        return AERWHALE_TEXTURE;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    protected void scale(LivingEntityRenderState renderState, PoseStack poseStack) {
        poseStack.translate(0.0, -0.5, 0.0);
        poseStack.scale(2.0F, 2.0F, 2.0F);
    }

    @Override
    protected AABB getBoundingBoxForCulling(Aerwhale p_360864_) {
        return super.getBoundingBoxForCulling(p_360864_).inflate(3.0);
    }

    @Override
    protected RenderType getRenderType(LivingEntityRenderState renderState, boolean isVisible, boolean renderTranslucent, boolean appearsGlowing) {
        return super.getRenderType(renderState, isVisible, renderTranslucent, appearsGlowing);
    }

    @Override
    public void extractRenderState(Aerwhale whale, LivingEntityRenderState state, float partialTicks) {
        super.extractRenderState(whale, state, partialTicks);
        state.yRot = state.bodyRot = whale.getYRot(partialTicks) + 90.0F;
        state.xRot = whale.getXRot(partialTicks);
    }

    @Override
    protected void setupRotations(LivingEntityRenderState state, PoseStack poseStack, float bodyRot, float scale) {
        super.setupRotations(state, poseStack, bodyRot, scale);
        poseStack.mulPose(Axis.XN.rotationDegrees(state.xRot));
    }
}
