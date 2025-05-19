package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.client.renderer.entity.state.GravititeDebrisShotRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class GravititeDebrisShotModel extends EntityModel<GravititeDebrisShotRenderState> {
    private final ModelPart debrisShot;

    public GravititeDebrisShotModel(ModelPart root) {
        super(root);
        this.debrisShot = root.getChild("debris_shot");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition debris_shot = partDefinition.addOrReplaceChild("debris_shot", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -7.0F, -8.0F, 14.0F, 14.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition debris_2_r1 = debris_shot.addOrReplaceChild("debris_2_r1", CubeListBuilder.create().texOffs(0, 30).addBox(-7.0F, -7.0F, -8.0F, 14.0F, 14.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public void setupAnim(GravititeDebrisShotRenderState renderState) {
        super.setupAnim(renderState);
        this.debrisShot.zRot = Mth.wrapDegrees(renderState.ageInTicks * -30.0F) * Mth.DEG_TO_RAD;
    }
}
