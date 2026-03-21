package com.aetherteam.aetherii.client.renderer.blockentity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Unit;

public class VaseModel extends Model<Unit> {
    private final ModelPart vase;

    public VaseModel(ModelPart root) {
        super(root, RenderTypes::entityCutout);
        this.vase = root.getChild("vase");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("vase", CubeListBuilder.create().texOffs(0, 20).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-5.0F, -11.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(32, 27).addBox(5.0F, -13.0F, 0.0F, 5.0F, 13.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-10.0F, -13.0F, 0.0F, 5.0F, 13.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).addBox(-4.0F, -13.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 20).addBox(-3.0F, -12.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
}