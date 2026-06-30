package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.entity.passive.Shroudwing;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class ShroudwingModel extends HierarchicalModel<Shroudwing> {
    private final ModelPart root;
    private final ModelPart shroudwing;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public ShroudwingModel(ModelPart root) {
        this.root = root;
        this.shroudwing = root.getChild("shroudwing");
        this.rightWing = this.shroudwing.getChild("right_wing");
        this.leftWing = this.shroudwing.getChild("left_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition shroudwing = partDefinition.addOrReplaceChild("shroudwing", CubeListBuilder.create().texOffs(28, 0).addBox(-1.0F, -3.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 23).addBox(-2.0F, -1.0F, -3.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(-3, 0).addBox(-3.0F, 0.0F, -5.25F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 23.0F, 0.0F));

        PartDefinition leftWingBase = shroudwing.addOrReplaceChild("left_wing_base", CubeListBuilder.create(), PartPose.offset(0.5F, -1.1F, -1.0F));
        leftWingBase.addOrReplaceChild("left_wing_case_r1", CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.1745F, 0.0F));

        PartDefinition rightWingbase = shroudwing.addOrReplaceChild("right_wing_base", CubeListBuilder.create(), PartPose.offset(-1.5F, -1.1F, -1.0F));
        rightWingbase.addOrReplaceChild("right_wing_case_r1", CubeListBuilder.create().texOffs(0, 26).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, -0.1745F, 0.0F));

        PartDefinition rightWing = shroudwing.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-1.25F, -1.1F, 0.25F));
        rightWing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create().texOffs(1, 11).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, -0.1745F, 0.0F));

        PartDefinition leftWing = shroudwing.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(0.25F, -1.1F, 0.25F));
        leftWing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create().texOffs(-4, 11).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.1745F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(Shroudwing shroudwing, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        if (shroudwing.isRest()) {
            this.shroudwing.xRot = Mth.cos(limbSwing * 0.6662F) * 0.08F * limbSwingAmount;
        } else {
            float rotation = Mth.sin(ageInTicks * 2) * 10 * Mth.DEG_TO_RAD;
            this.rightWing.zRot = rotation;
            this.leftWing.zRot = -rotation;
        }
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
