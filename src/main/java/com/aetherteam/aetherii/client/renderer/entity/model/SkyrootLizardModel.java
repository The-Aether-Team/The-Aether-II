package com.aetherteam.aetherii.client.renderer.entity.model;


import com.aetherteam.aetherii.entity.passive.SkyrootLizard;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class SkyrootLizardModel<T extends SkyrootLizard> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart leg_front_left;
    private final ModelPart leg_front_right;
    private final ModelPart leg_back_left;
    private final ModelPart leg_back_right;
    private final ModelPart neck_1;
    private final ModelPart neck_2;
    private final ModelPart neck_2_flaps;
    private final ModelPart head_1;
    private final ModelPart head_2;
    private final ModelPart earflaps;
    private final ModelPart tongue;
    private final ModelPart neck_1_flaps;
    private final ModelPart tail_1;
    private final ModelPart tail_2;
    private final ModelPart tail_3;
    private final ModelPart tail_3_flaps;
    private final ModelPart tail_2_flaps;
    private final ModelPart tail_1_flaps;

    public SkyrootLizardModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.leg_front_left = body.getChild("leg_front_left");
        this.leg_front_right = body.getChild("leg_front_right");
        this.leg_back_left = body.getChild("leg_back_left");
        this.leg_back_right = body.getChild("leg_back_right");
        this.neck_1 = body.getChild("neck_1");
        this.neck_2 = neck_1.getChild("neck_2");
        this.neck_2_flaps = neck_2.getChild("neck_2_flaps");
        this.head_1 = neck_2.getChild("head_1");
        this.head_2 = head_1.getChild("head_2");
        this.earflaps = head_1.getChild("earflaps");
        this.tongue = head_1.getChild("tongue");
        this.neck_1_flaps = neck_1.getChild("neck_1_flaps");
        this.tail_1 = body.getChild("tail_1");
        this.tail_2 = tail_1.getChild("tail_2");
        this.tail_3 = tail_2.getChild("tail_3");
        this.tail_3_flaps = tail_3.getChild("tail_3_flaps");
        this.tail_2_flaps = tail_2.getChild("tail_2_flaps");
        this.tail_1_flaps = tail_1.getChild("tail_1_flaps");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 20).addBox(-1.5F, 0.25F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.5F, 0.0F));

        PartDefinition leg_front_left = body.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(14, 34).addBox(-0.5F, 0.0F, -2.5F, 12.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, 0.5F, -3.25F, 0.1793F, 0.5872F, 0.3161F));

        PartDefinition leg_front_right = body.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(14, 34).mirror().addBox(-11.5F, 0.0F, -2.5F, 12.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.25F, 0.5F, -3.25F, 0.1793F, -0.5872F, -0.3161F));

        PartDefinition leg_back_left = body.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(14, 27).addBox(-0.777F, 0.0742F, -2.9096F, 12.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, 0.5F, 3.75F, -0.1793F, -0.5872F, 0.3161F));

        PartDefinition leg_back_right = body.addOrReplaceChild("leg_back_right", CubeListBuilder.create().texOffs(14, 27).mirror().addBox(-11.223F, 0.0742F, -2.9096F, 12.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.25F, 0.5F, 3.75F, -0.1793F, 0.5872F, -0.3161F));

        PartDefinition neck_1 = body.addOrReplaceChild("neck_1", CubeListBuilder.create().texOffs(0, 27).addBox(-1.0F, 0.0F, -6.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.25F, -1.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition neck_2 = neck_1.addOrReplaceChild("neck_2", CubeListBuilder.create().texOffs(0, 35).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, -6.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition neck_2_flaps = neck_2.addOrReplaceChild("neck_2_flaps", CubeListBuilder.create().texOffs(8, 14).addBox(-5.5F, -0.98F, -4.0F, 11.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition head_1 = neck_2.addOrReplaceChild("head_1", CubeListBuilder.create().texOffs(0, 41).addBox(-2.5F, -1.0F, -4.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.45F, -5.0F));

        PartDefinition head_2 = head_1.addOrReplaceChild("head_2", CubeListBuilder.create().texOffs(0, 47).addBox(-4.0F, -0.8F, -3.4F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition earflaps = head_1.addOrReplaceChild("earflaps", CubeListBuilder.create().texOffs(0, 53).addBox(-2.5F, -0.3F, -1.2F, 5.0F, 3.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition tongue = head_1.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(11, 53).addBox(-1.5F, 0.05F, -6.15F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition neck_1_flaps = neck_1.addOrReplaceChild("neck_1_flaps", CubeListBuilder.create().texOffs(9, 11).addBox(-5.5F, -0.99F, -5.0F, 11.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -1.0F));

        PartDefinition tail_1 = body.addOrReplaceChild("tail_1", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.25F, 1.25F, 0.3054F, 0.0F, 0.0F));

        PartDefinition tail_2 = tail_1.addOrReplaceChild("tail_2", CubeListBuilder.create().texOffs(0, 6).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition tail_3 = tail_2.addOrReplaceChild("tail_3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition tail_3_flaps = tail_3.addOrReplaceChild("tail_3_flaps", CubeListBuilder.create().texOffs(9, 0).addBox(-5.5F, -0.5F, 0.0F, 11.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

        PartDefinition tail_2_flaps = tail_2.addOrReplaceChild("tail_2_flaps", CubeListBuilder.create().texOffs(7, 3).addBox(-5.5F, -0.5F, 0.0F, 11.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

        PartDefinition tail_1_flaps = tail_1.addOrReplaceChild("tail_1_flaps", CubeListBuilder.create().texOffs(9, 8).addBox(-5.5F, -0.49F, 2.25F, 11.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.75F));

        return LayerDefinition.create(meshdefinition, 48, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(SkyrootLizard entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head_1.xRot = headPitch * Mth.DEG_TO_RAD;
        this.head_1.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.leg_back_right.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg_back_left.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg_front_right.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg_front_left.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}