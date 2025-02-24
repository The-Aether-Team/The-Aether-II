package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.SkephidEmissiveLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SkephidModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SkephidRenderState;
import com.aetherteam.aetherii.entity.monster.Skephid;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;

public class SkephidRenderer<T extends Skephid> extends MobRenderer<T, SkephidRenderState, SkephidModel<SkephidRenderState>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/skephid/skephid.png");

    public SkephidRenderer(EntityRendererProvider.Context context) {
        super(context, new SkephidModel<>(context.bakeLayer(AetherIIModelLayers.SKEPHID)), 0.5F);
        this.addLayer(new SkephidEmissiveLayer(this));
    }

    @Override
    public SkephidRenderState createRenderState() {
        return new SkephidRenderState();
    }

    @Override
    public void extractRenderState(T skephid, SkephidRenderState renderState, float p_361157_) {
        super.extractRenderState(skephid, renderState, p_361157_);
        renderState.deltaMovement = skephid.getDeltaMovement();
        renderState.yO = skephid.yo;
        renderState.attachDir = skephid.getAttachFacing();
        renderState.attachChangeProgress = skephid.attachChangeProgress;
        renderState.prevAttachChangeProgress = skephid.prevAttachChangeProgress;
    }

    @Override
    protected void setupRotations(SkephidRenderState entity, PoseStack poseStack, float rotationYaw, float p_320045_) {
        float trans = 6.5F / 16F;
        if (entity.pose != Pose.SLEEPING) {
            float progresso = 1F - (entity.prevAttachChangeProgress + (entity.attachChangeProgress - entity.prevAttachChangeProgress) * entity.partialTick);

            if (entity.attachDir == Direction.DOWN) {
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
                poseStack.translate(0.0D, trans, 0.0D);
                if (entity.yO < entity.y) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(90 * (1 - progresso)));
                } else {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90 * (1 - progresso)));
                }
                poseStack.translate(0.0D, -trans, 0.0D);

            } else if (entity.attachDir == Direction.UP) {
                poseStack.translate(0.0D, trans, 0.0D);
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
                poseStack.mulPose(Axis.XP.rotationDegrees(180));
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                poseStack.translate(0.0D, -trans, 0.0D);

            } else {
                poseStack.translate(0.0D, trans, 0.0D);
                switch (entity.attachDir) {
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
                if (entity.deltaMovement.y <= -0.001F) {
                    poseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
                }
                poseStack.translate(0.0D, -trans, 0.0D);
            }
        } else {
            super.setupRotations(entity, poseStack, rotationYaw, p_320045_);
        }
    }


    @Override
    public ResourceLocation getTextureLocation(SkephidRenderState renderState) {
        return LOCATION;
    }
}
