package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.entity.projectile.GravititeDebrisShot;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class GravititeDebrisShotModel extends HierarchicalModel<GravititeDebrisShot> {
    private final ModelPart root;
    private final ModelPart debrisShot;

    public GravititeDebrisShotModel(ModelPart root) {
        this.root = root;
        this.debrisShot = root.getChild("debris_shot");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition debrisShot = partDefinition.addOrReplaceChild("debris_shot", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -7.0F, -8.0F, 14.0F, 14.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        debrisShot.addOrReplaceChild("debris_2_r1", CubeListBuilder.create().texOffs(0, 30).addBox(-7.0F, -7.0F, -8.0F, 14.0F, 14.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(GravititeDebrisShot entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.debrisShot.zRot = Mth.wrapDegrees(ageInTicks * -30.0F) * Mth.DEG_TO_RAD;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
