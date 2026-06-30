package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.AetherIIArmPoseTransformers;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.entity.vehicle.CloudSkiff;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends LivingEntity> {
    @Shadow
    @Final
    public ModelPart head;
    @Shadow
    @Final
    public ModelPart body;
    @Shadow
    @Final
    public ModelPart rightArm;
    @Shadow
    @Final
    public ModelPart leftArm;
    @Shadow
    @Final
    public ModelPart rightLeg;
    @Shadow
    @Final
    public ModelPart leftLeg;

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    private void setupMoaRiderPose(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity.getVehicle() instanceof Moa) {
            this.positionMoaRider();
        }
        if (entity.getVehicle() instanceof CloudSkiff cloudSkiff) {
            this.positionCloudSkiffRider(entity, cloudSkiff);
        }
        if (entity.getUseItem().is(AetherIITags.Items.TOOLS_GLIDERS)) {
            AetherIIArmPoseTransformers.GLIDING_TRANSFORMER.applyTransform((HumanoidModel<?>) (Object) this, entity, entity.getMainArm());
        }
        if (entity.getUseItem().is(AetherIIItems.DART_SHOOTER.get())) {
            AetherIIArmPoseTransformers.DART_SHOOTER_TRANSFORMER.applyTransform((HumanoidModel<?>) (Object) this, entity, entity.getMainArm());
        }
    }

    private void positionMoaRider() {
        this.rightArm.xRot += -10.0F * Mth.DEG_TO_RAD;
        this.rightArm.zRot += -30.0F * Mth.DEG_TO_RAD;
        this.leftArm.xRot += -10.0F * Mth.DEG_TO_RAD;
        this.leftArm.zRot += 30.0F * Mth.DEG_TO_RAD;

        this.rightLeg.x -= 1.0F;
        this.rightLeg.y -= 1.0F;
        this.rightLeg.xRot += 40.0F * Mth.DEG_TO_RAD;
        this.rightLeg.yRot += 10.0F * Mth.DEG_TO_RAD;
        this.leftLeg.x += 1.0F;
        this.leftLeg.y -= 1.0F;
        this.leftLeg.xRot += 40.0F * Mth.DEG_TO_RAD;
        this.leftLeg.yRot -= 10.0F * Mth.DEG_TO_RAD;
    }

    private void positionCloudSkiffRider(T entity, CloudSkiff cloudSkiff) {
        if (cloudSkiff.getPassengers().indexOf(entity) == 0) {
            AetherIIArmPoseTransformers.SKIFF_SAILING_TRANSFORMER.applyTransform((HumanoidModel<?>) (Object) this, entity, entity.getMainArm());
            this.rightLeg.xRot = 0.0F;
            this.rightLeg.yRot = 5.0F * Mth.DEG_TO_RAD;
            this.rightLeg.zRot = 0.0F;
            this.leftLeg.xRot = 0.0F;
            this.leftLeg.yRot = -5.0F * Mth.DEG_TO_RAD;
            this.leftLeg.zRot = 0.0F;
        }
    }
}
