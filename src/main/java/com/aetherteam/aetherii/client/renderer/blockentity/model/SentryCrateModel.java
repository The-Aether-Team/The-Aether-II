package com.aetherteam.aetherii.client.renderer.blockentity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Unit;

public class SentryCrateModel extends Model<Unit> {
    public SentryCrateModel(ModelPart root) {
        super(root, RenderTypes::entitySolid);
    }

    public static LayerDefinition createSingleBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("crate", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 1.0F, 14.0F, 14.0F, 14.0F), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public static LayerDefinition createDoubleBodyRightLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("crate", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 1.0F, 15.0F, 14.0F, 14.0F), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public static LayerDefinition createDoubleBodyLeftLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("crate", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 1.0F, 15.0F, 14.0F, 14.0F), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 32);
    }
}
