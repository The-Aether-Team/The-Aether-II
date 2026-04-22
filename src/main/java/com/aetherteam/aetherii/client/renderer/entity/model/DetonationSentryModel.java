package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.client.renderer.entity.animation.DetonationSentryAnimations;
import com.aetherteam.aetherii.client.renderer.entity.state.DetonationSentryRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class DetonationSentryModel extends EntityModel<DetonationSentryRenderState> {
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation explosionAnimation;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart legs_pair_1;
    private final ModelPart leg_back_left;
    private final ModelPart leg_front_right;
    private final ModelPart legs_pair_2;
    private final ModelPart leg_back_right;
    private final ModelPart leg_front_left;

    public DetonationSentryModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
        this.legs_pair_1 = this.body.getChild("legs_pair_1");
        this.leg_back_left = this.legs_pair_1.getChild("leg_back_left");
        this.leg_front_right = this.legs_pair_1.getChild("leg_front_right");
        this.legs_pair_2 = this.body.getChild("legs_pair_2");
        this.leg_back_right = this.legs_pair_2.getChild("leg_back_right");
        this.leg_front_left = this.legs_pair_2.getChild("leg_front_left");
        this.walkAnimation = DetonationSentryAnimations.walk.bake(root);
        this.explosionAnimation = DetonationSentryAnimations.explode.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -7.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(57, 0).addBox(-7.0F, -7.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, -7.0F, 0.0F));

        PartDefinition legs_pair_1 = body.addOrReplaceChild("legs_pair_1", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition leg_back_left = legs_pair_1.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(40, 43).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(57, 43).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(7.0F, 0.0F, 7.0F, 0.2618F, 0.7854F, 0.0F));

        PartDefinition leg_front_right = legs_pair_1.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(0, 30).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(17, 30).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-7.0F, 0.0F, -7.0F, -0.2618F, 0.7854F, 0.0F));

        PartDefinition legs_pair_2 = body.addOrReplaceChild("legs_pair_2", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition leg_back_right = legs_pair_2.addOrReplaceChild("leg_back_right", CubeListBuilder.create().texOffs(0, 43).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(17, 43).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-7.0F, 0.0F, 7.0F, 0.2618F, -0.7854F, 0.0F));

        PartDefinition leg_front_left = legs_pair_2.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(40, 30).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(57, 30).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(7.0F, 0.0F, -7.0F, -0.2618F, -0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(DetonationSentryRenderState renderState) {
        super.setupAnim(renderState);
        float f = renderState.walkAnimationSpeed;
        float f1 = renderState.walkAnimationPos;
        this.walkAnimation.applyWalk(f1, f, 1.0F, 1.0F);
        this.explosionAnimation.apply(renderState.explosionAnimationState, renderState.ageInTicks, 1.0F);
    }
}
