package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.state.ButterflyRenderState;
import com.aetherteam.aetherii.client.renderer.entity.state.InsectRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class ButterflyModel extends EntityModel<ButterflyRenderState> {
    private final ModelPart butterfly;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public ButterflyModel(ModelPart root) {
        super(root);
        this.butterfly = root.getChild("butterfly");
        this.rightWing = this.butterfly.getChild("right_wing");
        this.leftWing = this.butterfly.getChild("left_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition butterfly = partdefinition.addOrReplaceChild("butterfly", CubeListBuilder.create().texOffs(-1, 56).addBox(-4.0F, -2.0F, -12.5F, 8.0F, 0.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 0.0F));
        butterfly.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(-3, 0).addBox(-13.0F, 0.0F, -12.5F, 13.0F, 0.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));
        butterfly.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 27).addBox(0.0F, 0.0F, -12.5F, 13.0F, 0.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(ButterflyRenderState renderState) {
        super.setupAnim(renderState);
        this.rightWing.x -= renderState.wingXOffset;
        this.leftWing.x += renderState.wingXOffset;
        this.rightWing.zRot += renderState.wingZRotation;
        this.leftWing.zRot -= renderState.wingZRotation;
    }
}
