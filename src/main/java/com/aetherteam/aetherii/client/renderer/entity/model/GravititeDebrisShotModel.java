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

        PartDefinition debris_2_r1 = debris_shot.addOrReplaceChild("debris_2_r1", CubeListBuilder.create().texOffs(0, 30).addBox(-7.0F, -7.0F, -8.0F, 14.0F, 14.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public void setupAnim(GravititeDebrisShotRenderState renderState) {
        super.setupAnim(renderState);
//        this.debrisShot.xRot = renderState.ageInTicks * 45 * Mth.DEG_TO_RAD;
//        this.debrisShot.yRot = renderState.yRot * Mth.DEG_TO_RAD;
        float f = renderState.ageInTicks * (float)(-(45 + (1) * 5));
        this.debrisShot.yRot = Mth.wrapDegrees(f) * ((float)Math.PI / 180F);
    }
}
