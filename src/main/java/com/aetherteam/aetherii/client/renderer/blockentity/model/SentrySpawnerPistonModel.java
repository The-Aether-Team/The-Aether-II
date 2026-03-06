package com.aetherteam.aetherii.client.renderer.blockentity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Unit;

public class SentrySpawnerPistonModel extends Model<Unit> {
    private final ModelPart piston;

    public SentrySpawnerPistonModel(ModelPart root) {
        super(root, RenderTypes::entityCutout);
        this.piston = root.getChild("piston");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("piston", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -18.0F, -5.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 32);
    }
}