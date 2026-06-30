package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.entity.monster.GravititeTaluton;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class GravititeTalutonModel extends HierarchicalModel<GravititeTaluton> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart legsNode;
    private final ModelPart legLeftPosition;
    private final ModelPart legLeft;
    private final ModelPart legRightPosition;
    private final ModelPart legRight;
    private final ModelPart legRearPosition;
    private final ModelPart legRear;
    private final ModelPart debrisNode;
    private final ModelPart debris1;
    private final ModelPart debris2;

    public GravititeTalutonModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.legsNode = this.body.getChild("legs_node");
        this.legLeftPosition = this.legsNode.getChild("leg_left_position");
        this.legLeft = this.legLeftPosition.getChild("leg_left");
        this.legRightPosition = this.legsNode.getChild("leg_right_position");
        this.legRight = this.legRightPosition.getChild("leg_right");
        this.legRearPosition = this.legsNode.getChild("leg_rear_position");
        this.legRear = this.legRearPosition.getChild("leg_rear");
        this.debrisNode = this.body.getChild("debris_node");
        this.debris1 = this.debrisNode.getChild("debris_1");
        this.debris2 = this.debrisNode.getChild("debris_2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));

        PartDefinition rock_b_r1 = body.addOrReplaceChild("rock_b_r1", CubeListBuilder.create().texOffs(0, 3).addBox(-1.5F, -5.1F, -0.9F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.7F, 5.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition rock_b_l_r1 = body.addOrReplaceChild("rock_b_l_r1", CubeListBuilder.create().texOffs(71, 3).addBox(-0.5F, -4.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -1.7F, 3.7F, 0.2182F, 0.7854F, 0.0F));

        PartDefinition rock_f_r_r1 = body.addOrReplaceChild("rock_f_r_r1", CubeListBuilder.create().texOffs(33, 3).addBox(-1.5F, -4.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -2.7F, -2.1F, -0.2182F, 0.7854F, 0.0F));

        PartDefinition rock_b_r_r1 = body.addOrReplaceChild("rock_b_r_r1", CubeListBuilder.create().texOffs(11, 5).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -2.7F, 2.8F, 0.4363F, -0.7854F, 0.0F));

        PartDefinition rock_f_l_r1 = body.addOrReplaceChild("rock_f_l_r1", CubeListBuilder.create().texOffs(53, 8).addBox(-0.5F, -1.0F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.8F, -2.4F, -3.5F, -0.1745F, -0.7854F, 0.0F));

        PartDefinition rock_f_r1 = body.addOrReplaceChild("rock_f_r1", CubeListBuilder.create().texOffs(42, 4).addBox(-1.5F, -4.0F, -1.0F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.7F, -5.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition rock_r_r1 = body.addOrReplaceChild("rock_r_r1", CubeListBuilder.create().texOffs(22, 3).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.8F, -5.0F, -0.5F, 0.0F, 0.0F, 0.1745F));

        PartDefinition rock_l_r1 = body.addOrReplaceChild("rock_l_r1", CubeListBuilder.create().texOffs(60, 0).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.4F, -4.7F, -0.5F, 0.0F, 0.0F, -0.1745F));

        PartDefinition body_3_r1 = body.addOrReplaceChild("body_3_r1", CubeListBuilder.create().texOffs(96, 0).addBox(-2.5F, -6.0F, -2.5F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(92, 14).addBox(-3.5F, 3.0F, -3.5F, 7.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(84, 27).addBox(-5.5F, 9.0F, -5.5F, 11.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition legs_node = body.addOrReplaceChild("legs_node", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_left_position = legs_node.addOrReplaceChild("leg_left_position", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition leg_left = leg_left_position.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(68, 21).addBox(-1.5F, -4.2F, -3.8F, 3.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(65, 13).addBox(-2.5F, -2.2F, -2.8F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -6.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition leg_right_position = legs_node.addOrReplaceChild("leg_right_position", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition leg_right = leg_right_position.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(15, 21).addBox(-1.5F, -4.2F, -3.8F, 3.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 13).addBox(-2.5F, -2.2F, -2.8F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -6.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition leg_rear_position = legs_node.addOrReplaceChild("leg_rear_position", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition leg_rear = leg_rear_position.addOrReplaceChild("leg_rear", CubeListBuilder.create().texOffs(42, 21).addBox(-1.5F, -4.2F, -3.8F, 3.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(39, 13).addBox(-2.5F, -2.2F, -2.8F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -6.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition debris_node = body.addOrReplaceChild("debris_node", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition debris_1 = debris_node.addOrReplaceChild("debris_1", CubeListBuilder.create().texOffs(0, 36).addBox(-14.0F, -9.0F, -14.0F, 28.0F, 18.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition debris_2 = debris_node.addOrReplaceChild("debris_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition debris_2_r1 = debris_2.addOrReplaceChild("debris_2_r1", CubeListBuilder.create().texOffs(0, 82).addBox(-14.0F, -9.0F, -14.0F, 28.0F, 18.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    public void setupAnim(GravititeTaluton entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.body.yRot = (-180.0F + Mth.rotLerp(ageInTicks - entity.tickCount, entity.yBodyRotO, entity.yBodyRot)) * Mth.DEG_TO_RAD;
        if (ageInTicks > 0) {
            this.legsNode.yRot = -this.body.yRot;
            this.debris1.yRot = -this.body.yRot + ageInTicks * 0.08F;
            this.debris2.yRot = -this.body.yRot - ageInTicks * 0.08F;
        }
        this.legLeft.xRot = 0.5236F + Mth.cos(limbSwing * 0.6662F) * 0.3F * limbSwingAmount;
        this.legRight.xRot = 0.5236F + Mth.cos(limbSwing * 0.6662F + Mth.HALF_PI) * 0.3F * limbSwingAmount;
        this.legRear.xRot = 0.5236F + Mth.cos(limbSwing * 0.6662F + Mth.PI) * 0.3F * limbSwingAmount;
        this.debrisNode.visible = entity.debrisVisible;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
