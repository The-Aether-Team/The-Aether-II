package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.client.renderer.entity.animation.GlitterwingAnimations;
import com.aetherteam.aetherii.client.renderer.entity.state.GlitterwingRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class GlitterwingModel extends EntityModel<GlitterwingRenderState> {
    private final KeyframeAnimation flyingAnimation;
    private final KeyframeAnimation landAnimation;
    private final KeyframeAnimation takeOffAnimation;

    private final ModelPart glitterwing;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public GlitterwingModel(ModelPart root) {
        super(root);
        this.flyingAnimation = GlitterwingAnimations.FLYING.bake(root);
        this.landAnimation = GlitterwingAnimations.LAND.bake(root);
        this.takeOffAnimation = GlitterwingAnimations.TAKEOFF.bake(root);
        this.glitterwing = root.getChild("glitterwing");
        this.rightWing = this.glitterwing.getChild("right_wing");
        this.leftWing = this.glitterwing.getChild("left_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition glitterwing = partdefinition.addOrReplaceChild("glitterwing", CubeListBuilder.create().texOffs(-26, 26).addBox(-4.0F, -2.0F, -17.0F, 8.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 26.0F, 4.0F));
        glitterwing.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(-26, 0).addBox(-13.0F, 0.0F, -17.0F, 13.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));
        glitterwing.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -17.0F, 13.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(GlitterwingRenderState renderState) {
        super.setupAnim(renderState);
        this.rightWing.x -= renderState.wingXOffset;
        this.leftWing.x += renderState.wingXOffset;
        this.rightWing.zRot += renderState.wingZRotation;
        this.leftWing.zRot -= renderState.wingZRotation;
        this.flyingAnimation.applyWalk(renderState.ageInTicks, 1.0F, 1.0F, 1.0F);
        this.landAnimation.apply(renderState.landAnimationState, renderState.ageInTicks);
        this.takeOffAnimation.apply(renderState.takeOffAnimationState, renderState.ageInTicks);
    }
}
