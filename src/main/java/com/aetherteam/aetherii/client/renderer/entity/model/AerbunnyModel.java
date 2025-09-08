package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.client.renderer.entity.animation.AerbunnyAnimation;
import com.aetherteam.aetherii.client.renderer.entity.state.AerbunnyRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class AerbunnyModel extends EntityModel<AerbunnyRenderState> {
    private final ModelPart body;
    private final ModelPart cloudpuff;
    private final ModelPart head;
    private final ModelPart ear_left;
    private final ModelPart ear_right;
    private final ModelPart whisker_left;
    private final ModelPart whisker_right;
    private final ModelPart leg_front_left;
    private final ModelPart leg_front_right;
    private final ModelPart leg_back_left;
    private final ModelPart foot_back_left;
    private final ModelPart leg_back_right;
    private final ModelPart foot_back_right;
    private final ModelPart tail;

    public AerbunnyModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.cloudpuff = this.body.getChild("cloudpuff");
        this.head = this.body.getChild("head");
        this.ear_left = this.head.getChild("ear_left");
        this.ear_right = this.head.getChild("ear_right");
        this.whisker_left = this.head.getChild("whisker_left");
        this.whisker_right = this.head.getChild("whisker_right");
        this.leg_front_left = this.body.getChild("leg_front_left");
        this.leg_front_right = this.body.getChild("leg_front_right");
        this.leg_back_left = this.body.getChild("leg_back_left");
        this.foot_back_left = this.leg_back_left.getChild("foot_back_left");
        this.leg_back_right = this.body.getChild("leg_back_right");
        this.foot_back_right = this.leg_back_right.getChild("foot_back_right");
        this.tail = this.body.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 15).addBox(-2.0F, -5.0F, 0.0F, 4.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.75F, -2.0F));

        PartDefinition cloudpuff = body.addOrReplaceChild("cloudpuff", CubeListBuilder.create().texOffs(16, 0).addBox(-3.0F, -3.5F, -5.0F, 6.0F, 7.0F, 10.0F, new CubeDeformation(-0.75F)), PartPose.offset(0.0F, -2.5F, 3.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 6).addBox(-2.0F, -2.0F, -2.25F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(12, 5).addBox(-1.0F, 0.0F, -2.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -1.75F));

        PartDefinition ear_left = head.addOrReplaceChild("ear_left", CubeListBuilder.create(), PartPose.offset(0.5F, -2.0F, -2.0F));

        PartDefinition ear_left_r1 = ear_left.addOrReplaceChild("ear_left_r1", CubeListBuilder.create().texOffs(6, 0).addBox(0.0F, -4.0F, 0.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.1309F));

        PartDefinition ear_right = head.addOrReplaceChild("ear_right", CubeListBuilder.create(), PartPose.offset(-0.5F, -2.0F, -2.0F));

        PartDefinition ear_right_r1 = ear_right.addOrReplaceChild("ear_right_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3054F, 0.0F, -0.1309F));

        PartDefinition whisker_left = head.addOrReplaceChild("whisker_left", CubeListBuilder.create(), PartPose.offset(2.0F, 0.0F, -1.25F));

        PartDefinition whisker_left_r1 = whisker_left.addOrReplaceChild("whisker_left_r1", CubeListBuilder.create().texOffs(12, 7).mirror().addBox(0.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5672F, 0.0F));

        PartDefinition whisker_right = head.addOrReplaceChild("whisker_right", CubeListBuilder.create(), PartPose.offset(-2.0F, 0.0F, -1.25F));

        PartDefinition whisker_right_r1 = whisker_right.addOrReplaceChild("whisker_right_r1", CubeListBuilder.create().texOffs(12, 7).addBox(-2.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5672F, 0.0F));

        PartDefinition leg_front_left = body.addOrReplaceChild("leg_front_left", CubeListBuilder.create(), PartPose.offset(2.0F, -0.25F, -0.5F));

        PartDefinition leg_front_left_r1 = leg_front_left.addOrReplaceChild("leg_front_left_r1", CubeListBuilder.create().texOffs(23, 17).addBox(-0.1281F, -0.8276F, -2.5225F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, -0.1222F, 0.0F));

        PartDefinition leg_front_right = body.addOrReplaceChild("leg_front_right", CubeListBuilder.create(), PartPose.offset(-2.0F, -0.25F, -0.5F));

        PartDefinition leg_front_right_r1 = leg_front_right.addOrReplaceChild("leg_front_right_r1", CubeListBuilder.create().texOffs(15, 17).addBox(-0.8719F, -0.8276F, -2.5225F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.1222F, 0.0F));

        PartDefinition leg_back_left = body.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(48, 10).addBox(-0.75F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.25F, -0.8F, 4.75F));

        PartDefinition foot_back_left = leg_back_left.addOrReplaceChild("foot_back_left", CubeListBuilder.create().texOffs(48, 16).addBox(-0.5F, 0.0F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 1.0F, 0.75F));

        PartDefinition leg_back_right = body.addOrReplaceChild("leg_back_right", CubeListBuilder.create().texOffs(48, 0).addBox(-1.25F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.25F, -0.8F, 4.75F));

        PartDefinition foot_back_right = leg_back_right.addOrReplaceChild("foot_back_right", CubeListBuilder.create().texOffs(48, 6).addBox(-0.5F, 0.0F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 1.0F, 0.75F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(22, 22).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.75F, 7.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(AerbunnyRenderState aerbunny) {
        super.setupAnim(aerbunny);
        this.head.xRot = aerbunny.xRot * Mth.DEG_TO_RAD;
        this.head.yRot = aerbunny.yRot * Mth.DEG_TO_RAD;
        if (!aerbunny.isSitting) {
            this.leg_front_right.xRot = (Mth.cos(aerbunny.walkAnimationPos * 0.6662F) * 1.0F * aerbunny.walkAnimationSpeed) - this.body.xRot;
            this.leg_front_left.xRot = (Mth.cos(aerbunny.walkAnimationPos * 0.6662F) * 1.0F * aerbunny.walkAnimationSpeed) - this.body.xRot;
            this.leg_back_right.xRot = (Mth.cos(aerbunny.walkAnimationPos * 0.6662F + Mth.PI) * 1.2F * aerbunny.walkAnimationSpeed) - this.body.xRot;
            this.leg_back_left.xRot = (Mth.cos(aerbunny.walkAnimationPos * 0.6662F + Mth.PI) * 1.2F * aerbunny.walkAnimationSpeed) - this.body.xRot;
        } else {
            this.applyStatic(AerbunnyAnimation.SITTING);
        }
        float a = 1.0F + aerbunny.puffiness * 0.5F;

        this.cloudpuff.xScale = a;
        this.cloudpuff.yScale = a;
        this.cloudpuff.zScale = a;
    }
}
