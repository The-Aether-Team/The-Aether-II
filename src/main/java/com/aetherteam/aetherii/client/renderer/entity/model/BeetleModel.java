package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.client.renderer.entity.state.BeetleRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class BeetleModel extends EntityModel<BeetleRenderState> {
    private final ModelPart beetle;
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public BeetleModel(ModelPart root) {
        super(root);
        this.beetle = root.getChild("beetle");
        this.leftWing = this.beetle.getChild("left_wing");
        this.rightWing = this.beetle.getChild("right_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition beetle = partdefinition.addOrReplaceChild("beetle", CubeListBuilder.create().texOffs(28, 0).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 23).addBox(-2.0F, 0.0F, 0.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(-3, 0).addBox(-3.0F, 1.0F, -2.25F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 22.0F, -3.0F));

        PartDefinition leftWing = beetle.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(0.5F, -0.1F, 2.0F));
        leftWing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.1745F, 0.0F));

        PartDefinition rightWing = beetle.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-1.5F, -0.1F, 2.0F));
        rightWing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create().texOffs(0, 26).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, -0.1745F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(BeetleRenderState renderState) {
        super.setupAnim(renderState);
    }
}
