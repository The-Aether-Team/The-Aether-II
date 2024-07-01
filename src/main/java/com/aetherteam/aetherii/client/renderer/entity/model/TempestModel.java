package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.entity.monster.Tempest;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class TempestModel extends EntityModel<Tempest> {
    public ModelPart mouth;
    public ModelPart body;
    public ModelPart bodyRightSideFront;
    public ModelPart bodyRightSideBack;
    public ModelPart bodyLeftSideFront;
    public ModelPart bodyLeftSideBack;
    public ModelPart cloudButt;
    public ModelPart tailBase;
    public ModelPart tailMiddle;
    public ModelPart tailEnd;

    public TempestModel(ModelPart model) {
        this.mouth = model.getChild("mouth");
        this.body = model.getChild("body");
        this.bodyRightSideFront = model.getChild("body_right_side_front");
        this.bodyRightSideBack = model.getChild("body_right_side_back");
        this.bodyLeftSideFront = model.getChild("body_left_side_front");
        this.bodyLeftSideBack = model.getChild("body_left_side_back");
        this.cloudButt = model.getChild("cloud_butt");
        this.tailBase = model.getChild("tail_base");
        this.tailMiddle = this.tailBase.getChild("tail_middle");
        this.tailEnd = this.tailMiddle.getChild("tail_end");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(66, 19).mirror().addBox(-3.0F, 1.0F, -8.0F, 6.0F, 3.0F, 1.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(27, 9).addBox(-6.0F, -4.0F, -7.0F, 12.0F, 9.0F, 14.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
        partDefinition.addOrReplaceChild("body_right_side_front", CubeListBuilder.create().texOffs(0, 20).mirror().addBox(-2.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F), PartPose.offset(-6.0F, 8.0F, -4.0F));
        partDefinition.addOrReplaceChild("body_right_side_back", CubeListBuilder.create().texOffs(25, 11).mirror().addBox(-2.0F, -3.3333F, -2.5F, 2.0F, 6.0F, 6.0F), PartPose.offset(-5.5F, 9.0F, 2.0F));
        partDefinition.addOrReplaceChild("body_left_side_front", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F), PartPose.offset(6.0F, 8.0F, -4.0F));
        partDefinition.addOrReplaceChild("body_left_side_back", CubeListBuilder.create().texOffs(25, 11).addBox(0.0F, -3.3333F, -2.5F, 2.0F, 6.0F, 6.0F), PartPose.offset(5.5F, 9.0F, 2.0F));
        partDefinition.addOrReplaceChild("cloud_butt", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-6.0F, -3.0F, 0.0F, 8.0F, 6.0F, 2.0F), PartPose.offset(2.0F, 8.0F, 7.0F));
        PartDefinition tailBase = partDefinition.addOrReplaceChild("tail_base", CubeListBuilder.create().texOffs(96, 22).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F), PartPose.offset(0.0F, 0.0F, 12.4F));
        PartDefinition tailMiddle = tailBase.addOrReplaceChild("tail_middle", CubeListBuilder.create().texOffs(80, 24).addBox(-2.0F, -2.0F, -1.966667F, 4.0F, 4.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 6.0F));
        tailMiddle.addOrReplaceChild("tail_end", CubeListBuilder.create().texOffs(84, 18).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F), PartPose.offset(0.0F, 0.0F, 5.0F));
        return LayerDefinition.create(meshDefinition, 128, 32);
    }

    public void setupAnim(Tempest tempest, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float motion = Mth.sin(limbSwing * 20.0F / 57.295776F) * limbSwingAmount * 0.5F;
        this.bodyRightSideFront.y = 8.0F - motion * 0.5F;
        this.bodyRightSideBack.y = 9.0F + motion * 0.5F;
        this.bodyLeftSideFront.y = this.bodyRightSideFront.y;
        this.bodyLeftSideBack.y = this.bodyRightSideBack.y;
        this.tailBase.x = Mth.sin(limbSwing * 20.0F / 57.295776F) * limbSwingAmount * 0.75F;
        this.tailBase.y = 8.0F - motion;
        this.tailBase.yRot = Mth.sin(ageInTicks * 0.5F) * limbSwingAmount * 0.75F;
        this.tailMiddle.x = Mth.sin(limbSwing * 15.0F / 57.295776F) * limbSwingAmount * 0.85F;
        this.tailMiddle.y = motion * 1.25F;
        this.tailMiddle.yRot = this.tailBase.yRot + 0.25F;
        this.tailEnd.x = Mth.sin(limbSwing * 10.0F / 57.295776F) * limbSwingAmount * 0.95F;
        this.tailEnd.y = -motion;
        this.tailEnd.yRot = this.tailMiddle.yRot + 0.35F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        this.mouth.render(poseStack, buffer, packedLight, packedOverlay);
        this.body.render(poseStack, buffer, packedLight, packedOverlay);
        this.bodyRightSideFront.render(poseStack, buffer, packedLight, packedOverlay);
        this.bodyRightSideBack.render(poseStack, buffer, packedLight, packedOverlay);
        this.bodyLeftSideFront.render(poseStack, buffer, packedLight, packedOverlay);
        this.bodyLeftSideBack.render(poseStack, buffer, packedLight, packedOverlay);
        this.cloudButt.render(poseStack, buffer, packedLight, packedOverlay);
        this.tailBase.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
