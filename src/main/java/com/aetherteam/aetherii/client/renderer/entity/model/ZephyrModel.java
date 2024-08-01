package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.client.renderer.entity.animation.ZephyrAnimation;
import com.aetherteam.aetherii.entity.monster.Zephyr;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ZephyrModel extends HierarchicalModel<Zephyr> {
    protected final ModelPart root;
    public final ModelPart body;
    public final ModelPart mouth;
    public final ModelPart bottom;
    public final ModelPart cheekLeft;
    public final ModelPart cheekRight;
    public final ModelPart wispTopLeft;
    public final ModelPart wispTopRight;
    public final ModelPart wispBottomLeft;
    public final ModelPart wispBottomRight;
    public final ModelPart tail;
    public final ModelPart tailMiddle;
    public final ModelPart tailEnd;

    public ZephyrModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.mouth = this.body.getChild("mouth");
        this.bottom = this.body.getChild("bottom");
        this.cheekLeft = this.body.getChild("cheek_left");
        this.cheekRight = this.body.getChild("cheek_right");
        this.wispTopLeft = this.body.getChild("wisp_top_left");
        this.wispTopRight = this.body.getChild("wisp_top_right");
        this.wispBottomLeft = this.body.getChild("wisp_bottom_left");
        this.wispBottomRight = this.body.getChild("wisp_bottom_right");
        this.tail = this.body.getChild("tail");
        this.tailMiddle = this.tail.getChild("tail_middle");
        this.tailEnd = this.tailMiddle.getChild("tail_end");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 23).addBox(-7.4906F, -6.9085F, -8.0438F, 15.0F, 7.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0094F, 16.9085F, -2.9562F));
        body.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(20, 50).addBox(-4.5F, -7.0F, -10.5F, 9.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0094F, 7.0915F, 2.9562F));
        body.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -3.5F, -7.5F, 21.0F, 7.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0094F, -1.4085F, 1.4562F));
        body.addOrReplaceChild("cheek_left", CubeListBuilder.create().texOffs(79, 0).addBox(-1.505F, -3.5347F, -2.9802F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0144F, 0.6261F, -6.0636F));
        body.addOrReplaceChild("cheek_right", CubeListBuilder.create().texOffs(58, 0).addBox(-2.005F, -3.5347F, -2.9802F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.4856F, 0.6261F, -6.0636F));

        PartDefinition wispTopLeft = body.addOrReplaceChild("wisp_top_left", CubeListBuilder.create(), PartPose.offset(4.5094F, -6.9085F, 10.9562F));
        wispTopLeft.addOrReplaceChild("wisp_top_left_r1", CubeListBuilder.create().texOffs(0, 66).addBox(-17.0F, 0.0F, 0.0F, 17.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.9066F, 1.2114F, -2.8912F));

        PartDefinition wispTopRight = body.addOrReplaceChild("wisp_top_right", CubeListBuilder.create(), PartPose.offset(-4.4906F, -6.9085F, 10.9562F));
        wispTopRight.addOrReplaceChild("wisp_top_right_r1", CubeListBuilder.create().texOffs(0, 66).mirror().addBox(0.0F, 0.0F, 0.0F, 17.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.9066F, -1.2114F, 2.8912F));

        PartDefinition wispBottomLeft = body.addOrReplaceChild("wisp_bottom_left", CubeListBuilder.create(), PartPose.offset(-7.4906F, 2.0915F, 0.9562F));
        wispBottomLeft.addOrReplaceChild("wisp_bottom_left_r1", CubeListBuilder.create().texOffs(0, 93).addBox(-15.0F, 0.0F, 0.0F, 18.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.3963F, 0.0F));

        PartDefinition wispBottomRight = body.addOrReplaceChild("wisp_bottom_right", CubeListBuilder.create(), PartPose.offset(9.5094F, 2.0915F, 1.4562F));
        wispBottomRight.addOrReplaceChild("wisp_bottom_right_r1", CubeListBuilder.create().texOffs(0, 93).mirror().addBox(-3.5F, 0.0F, 2.0F, 18.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.3963F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(69, 35).addBox(-3.5F, -3.5F, 0.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0094F, -2.4085F, 10.9562F));
        PartDefinition tailMiddle = tail.addOrReplaceChild("tail_middle", CubeListBuilder.create().texOffs(69, 50).addBox(-2.5F, -2.5F, 0.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 7.5F));
        tailMiddle.addOrReplaceChild("tail_end", CubeListBuilder.create().texOffs(90, 54).addBox(-1.5F, -1.5F, 0.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.5F));

        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    public void setupAnim(Zephyr zephyr, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(ZephyrAnimation.FLY, limbSwing, limbSwingAmount, 2.0F, 2.0F);
        this.animate(zephyr.blowAnimationState, ZephyrAnimation.BLOW_ATTACK, ageInTicks, 1.0F);
        this.animate(zephyr.webAnimationState, ZephyrAnimation.WEB_ATTACK, ageInTicks, 1.0F);


//        float motion = Mth.sin((limbSwing * 20.0F) / Mth.RAD_TO_DEG) * limbSwingAmount * 0.5F;
//
//        this.rightFace.y = 8 - motion;
//        this.rightFace.x = -motion * 0.5F;
//
//        this.leftFace.y = motion + 8;
//        this.leftFace.x = motion * 0.5F;
//
//        this.bodyRightSideFront.y = 8 - motion * 0.5F;
//        this.bodyRightSideBack.y = 9 + motion * 0.5F;
//
//        this.bodyLeftSideFront.y = this.bodyRightSideFront.y;
//        this.bodyLeftSideBack.y = this.bodyRightSideBack.y;
//
//        this.tailBase.x = Mth.sin((limbSwing * 20.0F) / Mth.RAD_TO_DEG) * limbSwingAmount * 0.75F;
//        this.tailBase.y = 8 - motion;
//        this.tailBase.yRot = Mth.sin(ageInTicks * 0.5F) * limbSwingAmount * 0.75F;
//
//        this.tailMiddle.x = Mth.sin((limbSwing * 15.0F) / Mth.RAD_TO_DEG) * limbSwingAmount * 0.85F;
//        this.tailMiddle.y = motion * 1.25F;
//        this.tailMiddle.yRot = this.tailBase.yRot + 0.25F;
//
//        this.tailEnd.x = Mth.sin((limbSwing * 10.0F) / Mth.RAD_TO_DEG) * limbSwingAmount * 0.95F;
//        this.tailEnd.y = -motion;
//        this.tailEnd.yRot = this.tailMiddle.yRot + 0.35F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, int color) {
        this.body.render(poseStack, consumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
