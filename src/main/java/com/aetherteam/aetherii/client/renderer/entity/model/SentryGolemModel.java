package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.client.renderer.entity.state.SentryGolemRenderState;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public class SentryGolemModel extends AbstractGolemModel<SentryGolemRenderState> {

    public SentryGolemModel(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(SentryGolemRenderState golem) {
        this.head.yRot = golem.yRot * Mth.DEG_TO_RAD;
        this.rightLeg.xRot = Mth.cos(golem.walkAnimationPos * 0.6662F) * 1.4F * golem.walkAnimationSpeed;
        this.leftLeg.xRot = Mth.cos(golem.walkAnimationPos * 0.6662F + Mth.PI) * 1.4F * golem.walkAnimationSpeed;

        this.rightArm.resetPose();
        this.leftArm.resetPose();

        this.rightArm.xRot = -3.0F * golem.progress;
        this.leftArm.xRot = -3.0F * golem.progress;
        ModelPart part = this.rightArm;
        part.yRot -= 0.3F * golem.progress;
        part = this.leftArm;
        part.yRot += 0.3F * golem.progress;
        part = this.rightArm;
        part.zRot += 0.3F * golem.progress;
        part = this.leftArm;
        part.zRot -= 0.3F * golem.progress;

        AnimationUtils.bobModelPart(this.rightArm, golem.ageInTicks, 1.0F);
        AnimationUtils.bobModelPart(this.leftArm, golem.ageInTicks, -1.0F);
    }
}
