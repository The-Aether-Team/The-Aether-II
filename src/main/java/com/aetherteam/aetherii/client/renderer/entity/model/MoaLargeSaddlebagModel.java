package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MoaLargeSaddlebagModel extends HierarchicalModel<Moa> {
    private final ModelPart root;
    private final ModelPart saddlebag;

    public MoaLargeSaddlebagModel(ModelPart root) {
        this.root = root;
        this.saddlebag = root.getChild("saddlebag");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition saddlebag = partDefinition.addOrReplaceChild("saddlebag", CubeListBuilder.create().texOffs(0, 8).addBox(-5.0F, -1.8F, 1.0F, 10.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(3, 0).addBox(-4.0F, -5.8F, 1.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, 0.1745F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
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
