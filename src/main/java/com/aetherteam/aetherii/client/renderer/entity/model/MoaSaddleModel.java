package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MoaSaddleModel extends HierarchicalModel<Moa> {
    private final ModelPart root;
    private final ModelPart moa_saddle;
    private final ModelPart handle;
    private final ModelPart stirrup_right;
    private final ModelPart stirrup_left;

    public MoaSaddleModel(ModelPart root) {
        this.root = root;
        this.moa_saddle = root.getChild("moa_saddle");
        this.handle = this.moa_saddle.getChild("handle");
        this.stirrup_right = this.moa_saddle.getChild("stirrup_right");
        this.stirrup_left = this.moa_saddle.getChild("stirrup_left");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition moa_saddle = partDefinition.addOrReplaceChild("moa_saddle", CubeListBuilder.create().texOffs(11, 13).addBox(-5.0F, -1.5F, -4.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(18, 5).addBox(-5.5F, -3.5F, -6.0F, 11.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 1.1F));

        PartDefinition rear_r1 = moa_saddle.addOrReplaceChild("rear_r1", CubeListBuilder.create().texOffs(17, 27).addBox(-3.5F, -2.0F, -3.5F, 7.0F, 4.0F, 7.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.5F, 6.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition handle = moa_saddle.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(21, 0).addBox(-4.5F, -3.8F, -0.5F, 9.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -4.5F, -0.5236F, 0.0F, 0.0F));

        PartDefinition stirrup_right = moa_saddle.addOrReplaceChild("stirrup_right", CubeListBuilder.create().texOffs(51, 13).addBox(0.0F, -2.0F, -2.0F, 0.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.5F, -2.0F, 0.0F, 0.0F, 0.48F));

        PartDefinition stirrup_left = moa_saddle.addOrReplaceChild("stirrup_left", CubeListBuilder.create().texOffs(3, 13).addBox(0.0F, -2.0F, -2.0F, 0.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.5F, -2.0F, 0.0F, 0.0F, -0.48F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(Moa moa, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if (moa.isSitting()) {
            this.root.y = 4.0F;
        }
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
