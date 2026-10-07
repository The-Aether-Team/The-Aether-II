package com.aetherteam.aetherii.client.renderer.entity.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class RotSentinelModel<T extends LivingEntityRenderState> extends EntityModel<T> {
    private final ModelPart RotSentinel;
    private final ModelPart Hip;
    private final ModelPart Leg;
    private final ModelPart Chestplate;
    private final ModelPart Head;
    private final ModelPart Chest;
    private final ModelPart Roots;
    private final ModelPart Arm_R;
    private final ModelPart Arm_L;

    public RotSentinelModel(ModelPart root) {
        super(root);
        this.RotSentinel = root.getChild("RotSentinel");
        this.Hip = this.RotSentinel.getChild("Hip");
        this.Leg = this.RotSentinel.getChild("Leg");
        this.Chestplate = this.RotSentinel.getChild("Chestplate");
        this.Head = this.RotSentinel.getChild("Head");
        this.Chest = this.RotSentinel.getChild("Chest");
        this.Roots = this.RotSentinel.getChild("Roots");
        this.Arm_R = this.RotSentinel.getChild("Arm_R");
        this.Arm_L = this.RotSentinel.getChild("Arm_L");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition RotSentinel = partdefinition.addOrReplaceChild("RotSentinel", CubeListBuilder.create(), PartPose.offset(0.0F, 2.3505F, -11.1543F));

        PartDefinition Hip = RotSentinel.addOrReplaceChild("Hip", CubeListBuilder.create(), PartPose.offset(0.0F, 3.091F, 9.3829F));

        PartDefinition Hip_r1 = Hip.addOrReplaceChild("Hip_r1", CubeListBuilder.create().texOffs(88, 51).addBox(-4.0F, -5.0F, -3.0F, 8.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition Leg = RotSentinel.addOrReplaceChild("Leg", CubeListBuilder.create(), PartPose.offset(-0.5F, 19.1495F, 10.9043F));

        PartDefinition Leg_r1 = Leg.addOrReplaceChild("Leg_r1", CubeListBuilder.create().texOffs(4, 64).addBox(-1.0F, -12.0F, -2.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition Chestplate = RotSentinel.addOrReplaceChild("Chestplate", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 1.0F));

        PartDefinition Left_r1 = Chestplate.addOrReplaceChild("Left_r1", CubeListBuilder.create().texOffs(105, 70).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, -0.3505F, -0.0957F, 0.1173F, -0.6133F, 1.1432F));

        PartDefinition Right_r1 = Chestplate.addOrReplaceChild("Right_r1", CubeListBuilder.create().texOffs(83, 70).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, -0.3505F, -0.0957F, 0.1173F, 0.6133F, -1.1432F));

        PartDefinition Head = RotSentinel.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(0.0095F, -8.6854F, 1.4619F));

        PartDefinition Head_r1 = Head.addOrReplaceChild("Head_r1", CubeListBuilder.create().texOffs(86, 2).addBox(-5.0F, -3.0F, -2.5F, 10.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.3044F, 0.3967F, 0.0F, 0.6545F, 1.5708F));

        PartDefinition Chest = RotSentinel.addOrReplaceChild("Chest", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.4842F, 4.0888F, 0.2618F, 0.0F, 0.0F));

        PartDefinition Front_r1 = Chest.addOrReplaceChild("Front_r1", CubeListBuilder.create().texOffs(86, 17).addBox(-7.0F, 2.0F, 2.0F, 14.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.1257F, -7.6245F, 0.3491F, 0.0F, 0.0F));

        PartDefinition Main_r1 = Chest.addOrReplaceChild("Main_r1", CubeListBuilder.create().texOffs(79, 28).addBox(-14.0F, -15.0F, 2.0F, 14.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 11.1336F, 0.0655F, 0.3491F, 0.0F, 0.0F));

        PartDefinition Roots = RotSentinel.addOrReplaceChild("Roots", CubeListBuilder.create().texOffs(0, -14).addBox(0.0F, -6.0F, -18.375F, 0.0F, 12.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(39, -14).addBox(0.0F, -6.0F, 0.625F, 0.0F, 12.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1315F, 15.6395F, 9.3202F, 0.0F, -0.7854F, 0.0F));

        PartDefinition Root_R_r1 = Roots.addOrReplaceChild("Root_R_r1", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -6.0F, -10.0F, 0.0F, 12.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 0.0F, -0.125F, 0.0F, 1.5708F, 0.0F));

        PartDefinition Root_L_r1 = Roots.addOrReplaceChild("Root_L_r1", CubeListBuilder.create().texOffs(39, 1).addBox(0.0F, -6.0F, -9.0F, 0.0F, 12.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, 0.0F, -0.125F, 0.0F, 1.5708F, 0.0F));

        PartDefinition Arm_R = RotSentinel.addOrReplaceChild("Arm_R", CubeListBuilder.create(), PartPose.offsetAndRotation(-11.4167F, -6.1131F, 7.9239F, -0.1745F, 0.0F, 0.0F));

        PartDefinition Joint_r1 = Arm_R.addOrReplaceChild("Joint_r1", CubeListBuilder.create().texOffs(41, 106).addBox(-10.0F, -1.0F, 1.0F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(97, 101).addBox(-17.0F, -5.0F, -1.0F, 7.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.4167F, -2.3863F, -3.5193F, -0.1745F, 0.0F, 0.0F));

        PartDefinition Shoulder_Blade_r1 = Arm_R.addOrReplaceChild("Shoulder_Blade_r1", CubeListBuilder.create().texOffs(57, 36).addBox(-2.0F, -12.0F, -3.0F, 0.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9167F, -8.6244F, 2.4392F, -0.5236F, 0.0F, 0.0F));

        PartDefinition Shoulder_Bump_r1 = Arm_R.addOrReplaceChild("Shoulder_Bump_r1", CubeListBuilder.create().texOffs(19, 121).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0833F, -7.6244F, 1.9392F, -0.1745F, 0.0F, 0.0F));

        PartDefinition Arm_r1 = Arm_R.addOrReplaceChild("Arm_r1", CubeListBuilder.create().texOffs(2, 117).addBox(-1.5F, -3.0F, -2.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5833F, 7.5712F, -4.2389F, -0.7854F, 0.0F, 0.0F));

        PartDefinition BladeCover_r1 = Arm_R.addOrReplaceChild("BladeCover_r1", CubeListBuilder.create().texOffs(1, 85).addBox(0.5F, -1.6073F, -2.7544F, 3.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(34, 85).addBox(25.5F, -1.6073F, -2.7544F, 3.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0833F, 11.3212F, -6.9889F, -0.7854F, 0.0F, 0.0F));

        PartDefinition Blade_r1 = Arm_R.addOrReplaceChild("Blade_r1", CubeListBuilder.create().texOffs(0, 29).addBox(0.5F, -1.6073F, -2.7544F, 0.0F, 8.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5833F, 7.5712F, -4.2389F, -0.6981F, 0.0F, 0.0F));

        PartDefinition Arm_L = RotSentinel.addOrReplaceChild("Arm_L", CubeListBuilder.create(), PartPose.offsetAndRotation(11.4167F, -6.1131F, 7.9239F, -0.1745F, 0.0F, 0.0F));

        PartDefinition Joint_r2 = Arm_L.addOrReplaceChild("Joint_r2", CubeListBuilder.create().texOffs(21, 106).addBox(-10.0F, -1.0F, 1.0F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(65, 101).addBox(-8.0F, -5.0F, -1.0F, 7.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5833F, -2.3863F, -3.5193F, -0.1745F, 0.0F, 0.0F));

        PartDefinition Shoulder_Blade_r2 = Arm_L.addOrReplaceChild("Shoulder_Blade_r2", CubeListBuilder.create().texOffs(44, 36).addBox(-2.0F, -12.0F, -3.0F, 0.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0833F, -8.6244F, 2.4392F, -0.5236F, 0.0F, 0.0F));

        PartDefinition Shoulder_Bump_r2 = Arm_L.addOrReplaceChild("Shoulder_Bump_r2", CubeListBuilder.create().texOffs(38, 121).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0833F, -7.6244F, 1.9392F, -0.1745F, 0.0F, 0.0F));

        PartDefinition Arm_r2 = Arm_L.addOrReplaceChild("Arm_r2", CubeListBuilder.create().texOffs(2, 104).addBox(-1.5F, -3.0F, -2.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5833F, 7.5712F, -4.2389F, -0.7854F, 0.0F, 0.0F));

        PartDefinition Blade_r2 = Arm_L.addOrReplaceChild("Blade_r2", CubeListBuilder.create().texOffs(0, 19).addBox(0.5F, -1.6073F, -2.7544F, 0.0F, 8.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5833F, 7.5712F, -4.2389F, -0.6981F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(LivingEntityRenderState entity) {

    }

}