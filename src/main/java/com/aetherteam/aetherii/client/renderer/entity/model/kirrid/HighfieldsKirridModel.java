package com.aetherteam.aetherii.client.renderer.entity.model.kirrid;

import com.aetherteam.aetherii.entity.passive.Kirrid;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class HighfieldsKirridModel extends AbstractKirridModel {
    protected final ModelPart headPlate;
    protected final ModelPart headPlateBroken;

    public HighfieldsKirridModel(ModelPart root) {
        super(root);
        this.headPlate = this.head.getChild("head_plate");
        this.headPlateBroken = this.head.getChild("head_plate_broken");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(4, 38).addBox(-5.0F, -4.0F, -9.0F, 10.0F, 8.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, -1.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 9.0F));

        PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(23, 64).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -8.0F));

        PartDefinition neck_r1 = neck.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(22, 28).addBox(-2.0F, -1.5F, -5.5F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.5F, -0.4363F, 0.0F, 0.0F));

        PartDefinition wool_neck_r1 = neck.addOrReplaceChild("wool_neck_r1", CubeListBuilder.create().texOffs(19, 73).addBox(-4.5F, -4.0F, -13.5F, 9.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 10.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -3.0F));

        PartDefinition head_main_r1 = head.addOrReplaceChild("head_main_r1", CubeListBuilder.create().texOffs(21, 13).addBox(-3.5F, -1.5F, -3.7F, 7.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.5F, -0.3316F, 0.0F, 0.0F));

        PartDefinition ear_left = head.addOrReplaceChild("ear_left", CubeListBuilder.create(), PartPose.offset(3.5F, -0.5F, -1.5F));

        PartDefinition ear_left_r1 = ear_left.addOrReplaceChild("ear_left_r1", CubeListBuilder.create().texOffs(43, 15).addBox(-1.0F, 0.0F, -1.5F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition ear_right = head.addOrReplaceChild("ear_right", CubeListBuilder.create(), PartPose.offset(-3.5F, -0.5F, -1.5F));

        PartDefinition ear_right_r1 = ear_right.addOrReplaceChild("ear_right_r1", CubeListBuilder.create().texOffs(15, 15).addBox(0.0F, 0.0F, -1.5F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition plate_broken = head.addOrReplaceChild("head_plate_broken", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head_plate_broken_3_r1 = plate_broken.addOrReplaceChild("head_plate_broken_3_r1", CubeListBuilder.create().texOffs(10, 73).addBox(-1.0F, -7.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(25, 5).addBox(-1.0F, -5.0F, -1.0F, 5.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 64).addBox(-4.0F, -10.0F, -1.0F, 3.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -2.5F, -0.5672F, 0.0F, 0.0F));

        PartDefinition plate = head.addOrReplaceChild("head_plate", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head_plate_r1 = plate.addOrReplaceChild("head_plate_r1", CubeListBuilder.create().texOffs(22, 0).addBox(-4.0F, -10.0F, -1.0F, 8.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -2.5F, -0.5672F, 0.0F, 0.0F));

        PartDefinition leg_front_left = body.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(50, 0).addBox(-1.0F, -1.5F, -2.0F, 3.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.5F, -6.0F, 0.0F, 0.0F, 0.0349F));

        PartDefinition leg_front_left_2 = leg_front_left.addOrReplaceChild("leg_front_left_2", CubeListBuilder.create().texOffs(52, 13).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.25F, 6.5F, 1.0F));

        PartDefinition leg_front_right = body.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.5F, -2.0F, 3.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.5F, -6.0F, 0.0F, 0.0F, -0.0349F));

        PartDefinition leg_front_right_2 = leg_front_right.addOrReplaceChild("leg_front_right_2", CubeListBuilder.create().texOffs(2, 13).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.25F, 6.5F, 1.0F));

        PartDefinition leg_rear_left = body.addOrReplaceChild("leg_rear_left", CubeListBuilder.create().texOffs(42, 27).addBox(-2.0F, -3.0F, -3.5F, 4.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 2.0F, 6.5F, 0.0F, 0.0F, 0.0349F));

        PartDefinition leg_rear_left_2 = leg_rear_left.addOrReplaceChild("leg_rear_left_2", CubeListBuilder.create().texOffs(48, 43).addBox(-1.0F, 0.0F, -1.25F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 2.5F, -0.1396F, 0.0F, 0.0F));

        PartDefinition leg_rear_right = body.addOrReplaceChild("leg_rear_right", CubeListBuilder.create().texOffs(0, 27).addBox(-2.0F, -3.0F, -3.5F, 4.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 2.0F, 6.5F, 0.0F, 0.0F, -0.0349F));

        PartDefinition leg_rear_right_2 = leg_rear_right.addOrReplaceChild("leg_rear_right_2", CubeListBuilder.create().texOffs(6, 43).addBox(-1.0F, 0.0F, -1.25F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 2.5F, -0.1396F, 0.0F, 0.0F));

        PartDefinition wool = body.addOrReplaceChild("wool", CubeListBuilder.create().texOffs(8, 86).addBox(-6.5F, -6.0F, -11.5F, 13.0F, 12.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition wool_rear_r1 = wool.addOrReplaceChild("wool_rear_r1", CubeListBuilder.create().texOffs(13, 109).addBox(-5.5F, -21.0F, -1.5F, 11.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, -1.0F, -0.0873F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 128);
    }

    @Override
    public void setupAnim(Kirrid kirrid, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(kirrid, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.headPlate.visible = kirrid.hasPlate();
        this.headPlateBroken.visible = !kirrid.hasPlate();
    }
}