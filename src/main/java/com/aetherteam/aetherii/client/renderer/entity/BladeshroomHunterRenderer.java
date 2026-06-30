package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.BladeshroomHunterModel;
import com.aetherteam.aetherii.entity.monster.BladeshroomHunter;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import org.joml.Quaternionfc;

public class BladeshroomHunterRenderer extends MobRenderer<BladeshroomHunter, BladeshroomHunterModel> {
    private static final ResourceLocation LOCATION = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/bladeshroom_hunter/bladeshroom_hunter.png");

    public BladeshroomHunterRenderer(EntityRendererProvider.Context context) {
        super(context, new BladeshroomHunterModel(context.bakeLayer(AetherIIModelLayers.BLADESHROOM_HUNTER)), 0.5F);
    }

    @Override
    protected void setupRotations(BladeshroomHunter entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        float trans = 8.0F / 16.0F;
        if (entity.getPose() != Pose.SLEEPING && entity.getAttachFacing() != Direction.DOWN) {
            Quaternionfc rotations = entity.getCellRotation();
            float yaw = (float) Math.toDegrees(Mth.atan2(rotations.x(), rotations.z()));
            float pitch = (float) -Math.toDegrees(Mth.atan2(rotations.y(), Math.sqrt(rotations.x() * rotations.x() + rotations.z() * rotations.z())));
            poseStack.translate(0.0F, trans, 0.0F);
            poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
            poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F + pitch));
            poseStack.translate(0.0F, -trans - 0.25F, 0.0F);
            super.setupRotations(entity, poseStack, ageInTicks, 0.0F, partialTick);
        } else {
            super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTick);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(BladeshroomHunter bladeshroomHunter) {
        return LOCATION;
    }
}
