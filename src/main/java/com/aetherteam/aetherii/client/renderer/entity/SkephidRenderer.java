package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.SkephidModel;
import com.aetherteam.aetherii.entity.monster.Skephid;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;

public class SkephidRenderer<T extends Skephid> extends MobRenderer<T, SkephidModel<T>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/skephid/skephid.png");

    public SkephidRenderer(EntityRendererProvider.Context context) {
        super(context, new SkephidModel<>(context.bakeLayer(AetherIIModelLayers.SKEPHID)), 0.5F);
        //this.addLayer(new SkephidEmissiveLayer<>(this));
    }

    @Override
    protected void setupRotations(T entity, PoseStack poseStack, float ageInTick, float rotationYaw, float partialTicks, float p_320045_) {
        float trans = 9F / 16F;
        if (entity.getPose() != Pose.SLEEPING) {
            float progresso = 1F - (entity.prevAttachChangeProgress + (entity.attachChangeProgress - entity.prevAttachChangeProgress) * partialTicks);

            if (entity.getAttachFacing() == Direction.DOWN) {
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
                poseStack.translate(0.0D, trans, 0.0D);
                if (entity.yo < entity.getY()) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(90 * (1 - progresso)));
                } else {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90 * (1 - progresso)));
                }
                poseStack.translate(0.0D, -trans, 0.0D);

            } else if (entity.getAttachFacing() == Direction.UP) {
                poseStack.translate(0.0D, trans, 0.0D);
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
                poseStack.mulPose(Axis.XP.rotationDegrees(180));
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                poseStack.translate(0.0D, -trans, 0.0D);

            } else {
                poseStack.translate(0.0D, trans, 0.0D);
                switch (entity.getAttachFacing()) {
                    case NORTH:
                        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F * progresso));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                        break;
                    case SOUTH:
                        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F * progresso));
                        break;
                    case WEST:
                        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                        poseStack.mulPose(Axis.YP.rotationDegrees(90F - 90.0F * progresso));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                        break;
                    case EAST:
                        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F * progresso - 90F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                        break;
                }
                if (entity.getDeltaMovement().y <= -0.001F) {
                    poseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
                }
                poseStack.translate(0.0D, -trans, 0.0D);
            }
        } else {
            super.setupRotations(entity, poseStack, ageInTick, rotationYaw, partialTicks, p_320045_);
        }
    }


    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return LOCATION;
    }
}
