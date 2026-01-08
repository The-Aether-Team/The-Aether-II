package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.client.renderer.entity.state.ShroudwingRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ShroudwingModel extends EntityModel<ShroudwingRenderState> {
    private final ModelPart shroudwing;
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public ShroudwingModel(ModelPart root) {
        super(root);
        this.shroudwing = root.getChild("shroudwing");
        this.leftWing = this.shroudwing.getChild("left_wing");
        this.rightWing = this.shroudwing.getChild("right_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition shroudwing = partdefinition.addOrReplaceChild("shroudwing", CubeListBuilder.create().texOffs(28, 0).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 23).addBox(-2.0F, 0.0F, 0.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(-3, 0).addBox(-3.0F, 1.0F, -2.25F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 22.0F, -3.0F));

        PartDefinition leftWing = shroudwing.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(0.5F, -0.1F, 2.0F));
        leftWing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.1745F, 0.0F));

        PartDefinition rightWing = shroudwing.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-1.5F, -0.1F, 2.0F));
        rightWing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create().texOffs(0, 26).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, -0.1745F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(ShroudwingRenderState renderState) {
        super.setupAnim(renderState);
    }
}
