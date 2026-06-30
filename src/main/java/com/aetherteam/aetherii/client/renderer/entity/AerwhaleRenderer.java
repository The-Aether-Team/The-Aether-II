package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.AerwhaleModel;
import com.aetherteam.aetherii.entity.passive.Aerwhale;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AerwhaleRenderer extends MobRenderer<Aerwhale, AerwhaleModel> {
    private static final ResourceLocation AERWHALE_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/aerwhale/aerwhale.png");
    
    public AerwhaleRenderer(EntityRendererProvider.Context context) {
        super(context, new AerwhaleModel(context.bakeLayer(AetherIIModelLayers.AERWHALE)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Aerwhale aerwhale) {
        return AERWHALE_TEXTURE;
    }

    @Override
    protected void scale(Aerwhale aerwhale, PoseStack poseStack, float partialTick) {
        poseStack.translate(0.0, -0.5, 0.0);
        poseStack.scale(2.0F, 2.0F, 2.0F);
    }

    @Override
    protected void setupRotations(Aerwhale aerwhale, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        float yRot = Mth.lerp(partialTick, aerwhale.yRotO, aerwhale.getYRot()) + 90.0F;
        float xRot = Mth.lerp(partialTick, aerwhale.xRotO, aerwhale.getXRot());
        super.setupRotations(aerwhale, poseStack, ageInTicks, yRot, partialTick);
        poseStack.mulPose(Axis.XN.rotationDegrees(xRot));
    }
}
