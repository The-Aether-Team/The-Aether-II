package com.aetherteam.aetherii.client.renderer.blockentity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

public class SentryTrapModel extends Model {
    private final ModelPart trap;

    public SentryTrapModel(ModelPart root) {
        super(root, RenderType::entityCutout);
        this.trap = root.getChild("trap");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("trap", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F, CubeDeformation.NONE), PartPose.ZERO);

        return LayerDefinition.create(meshDefinition, 64, 32);
    }
}
