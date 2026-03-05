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
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;

public class SkephidRenderer<T extends Skephid> extends MobRenderer<T, SkephidRenderState, SkephidModel<SkephidRenderState>> {
    private static final Identifier LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/skephid/skephid.png");

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
        renderState.rotations = skephid.getCellRotation();
        renderState.prevRotations = skephid.prevRotation;
        renderState.attachDir = skephid.getAttachFacing();
        renderState.attachChangeProgress = skephid.getAttachAmount(p_361157_);
    }

    @Override
    protected void setupRotations(SkephidRenderState entity, PoseStack poseStack, float rotationYaw, float p_320045_) {
        float trans = 6.5F / 16F;
        if (entity.pose != Pose.SLEEPING) {
            if (entity.attachDir == Direction.DOWN) {
                super.setupRotations(entity, poseStack, rotationYaw, p_320045_);
            } else {

                float yaw = (float) Math.toDegrees(Mth.atan2(entity.rotations.x(), entity.rotations.z()));
                float pitch = (float) -Math.toDegrees(Mth.atan2(entity.rotations.y(), Math.sqrt(entity.rotations.x() * entity.rotations.x() + entity.rotations.z() * entity.rotations.z())));
                float prevYaw = (float) Math.toDegrees(Mth.atan2(entity.prevRotations.x(), entity.prevRotations.z()));
                float prevPitch = (float) -Math.toDegrees(Mth.atan2(entity.prevRotations.y(), Math.sqrt(entity.prevRotations.x() * entity.prevRotations.x() + entity.prevRotations.z() * entity.prevRotations.z())));
                float realYaw = prevYaw * (1 - entity.attachChangeProgress) - yaw * entity.attachChangeProgress;
                float realPitch = prevPitch * (1 - entity.attachChangeProgress) - pitch * entity.attachChangeProgress;
                poseStack.translate(0.0F, trans, 0.0F);

                poseStack.mulPose(Axis.YP.rotationDegrees(realYaw));
                poseStack.mulPose(Axis.XP.rotationDegrees(-90 + realPitch));
                //poseStack.mulPose(Axis.YP.rotationDegrees(realDiff * realYaw));

                poseStack.translate(0.0F, -trans, 0.0F);
                super.setupRotations(entity, poseStack, 0.0F, p_320045_);
            }
        } else {
            super.setupRotations(entity, poseStack, rotationYaw, p_320045_);
        }
    }


    @Override
    public Identifier getTextureLocation(SkephidRenderState renderState) {
        return LOCATION;
    }
}
