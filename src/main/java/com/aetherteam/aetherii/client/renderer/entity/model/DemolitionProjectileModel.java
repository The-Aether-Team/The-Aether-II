package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.client.renderer.entity.state.DemolitionProjectileenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class DemolitionProjectileModel extends EntityModel<DemolitionProjectileenderState> {
    public DemolitionProjectileModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("cube", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 21.5F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }
}
