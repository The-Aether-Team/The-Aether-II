package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.entity.passive.Glitterwing;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class GlitterwingModel extends HierarchicalModel<Glitterwing> {
    private final ModelPart root;
    private final ModelPart glitterwing;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public GlitterwingModel(ModelPart root) {
        this.root = root;
        this.glitterwing = root.getChild("glitterwing");
        this.rightWing = this.glitterwing.getChild("right_wing");
        this.leftWing = this.glitterwing.getChild("left_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition glitterwing = partdefinition.addOrReplaceChild("glitterwing", CubeListBuilder.create().texOffs(-26, 26).addBox(-4.0F, -2.0F, -17.0F, 8.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 26.0F, 4.0F));
        glitterwing.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(-26, 0).addBox(-13.0F, 0.0F, -17.0F, 13.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));
        glitterwing.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -17.0F, 13.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Glitterwing glitterwing, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float wingXOffset = glitterwing.getVariant().value().wingXOffset();
        float wingZRotation = glitterwing.getVariant().value().wingZRotation();
        this.rightWing.x -= wingXOffset;
        this.leftWing.x += wingXOffset;
        this.rightWing.zRot += wingZRotation;
        this.leftWing.zRot -= wingZRotation;
        if (!glitterwing.isRest()) {
            float flap = Mth.sin(ageInTicks * 1.8F) * 0.45F;
            this.rightWing.zRot -= flap;
            this.leftWing.zRot += flap;
        }
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
