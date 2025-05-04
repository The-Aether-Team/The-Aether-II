package com.aetherteam.aetherii.client.renderer.entity.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class ArkeniumTalotonModel extends EntityModel<LivingEntityRenderState> {
    private final ModelPart Body;
    private final ModelPart bone;
    private final ModelPart bone2;
    private final ModelPart bone3;
    private final ModelPart bone4;
    private final ModelPart bone5;
    private final ModelPart bone6;
    private final ModelPart bone7;
    private final ModelPart bone8;
    private final ModelPart bone9;
    private final ModelPart bone10;
    private final ModelPart bone11;

    public ArkeniumTalotonModel(ModelPart root) {
        super(root);
        this.Body = root.getChild("Body");
        this.bone = this.Body.getChild("bone");
        this.bone2 = this.bone.getChild("bone2");
        this.bone3 = this.Body.getChild("bone3");
        this.bone4 = this.bone3.getChild("bone4");
        this.bone5 = this.Body.getChild("bone5");
        this.bone6 = this.bone5.getChild("bone6");
        this.bone7 = this.Body.getChild("bone7");
        this.bone8 = this.bone7.getChild("bone8");
        this.bone9 = this.Body.getChild("bone9");
        this.bone10 = this.bone9.getChild("bone10");
        this.bone11 = this.bone10.getChild("bone11");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition Body = partDefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-17.0F, -8.0F, -1.0F, 18.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-14.5F, 0.0F, 0.0F, 13.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 34).addBox(-12.5F, 4.0F, 0.0F, 9.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 6.0F, -5.0F));

        PartDefinition cube_r1 = Body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(72, 21).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3F, -8.5F, 10.4F, -0.2618F, -0.0436F, 0.5236F));

        PartDefinition cube_r2 = Body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(66, 42).addBox(-2.0F, -3.5F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -7.5F, 10.0F, -0.3491F, -0.1309F, 0.1745F));

        PartDefinition bone = Body.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(32, 69).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 10.5F, 8.0F));

        PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition cube_r3 = bone2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(66, 28).addBox(-2.0F, -2.0F, -1.5F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone3 = Body.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(16, 68).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.8F, 10.5F, 1.3F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r4 = bone3.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(74, 53).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -2.5F, 4.0F, -0.1745F, -0.1309F, -0.2182F));

        PartDefinition bone4 = bone3.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.0F));

        PartDefinition cube_r5 = bone4.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(58, 54).addBox(-2.0F, -2.0F, -0.5F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone5 = Body.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(58, 68).addBox(4.9F, -1.5F, 0.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.2F, 10.5F, 1.3F, 0.0F, -2.3562F, 0.0F));

        PartDefinition bone6 = bone5.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.0F));

        PartDefinition cube_r6 = bone6.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(60, 0).addBox(-2.0F, -2.0F, -0.5F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.4F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone7 = Body.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 5.0F));

        PartDefinition cube_r7 = bone7.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(72, 14).addBox(-2.0F, -3.0F, -1.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.1F, -0.3927F, 0.1745F, 0.829F));

        PartDefinition cube_r8 = bone7.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(46, 20).addBox(-2.0F, -3.0F, -2.0F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition bone8 = bone7.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(38, 34).addBox(-3.5F, -0.5F, -3.5F, 7.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 4.5F, 0.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition bone9 = Body.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offset(-16.0F, -4.0F, 5.0F));

        PartDefinition cube_r9 = bone9.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 53).addBox(-6.0F, -6.0F, -2.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 3.0F, -2.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition bone10 = bone9.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(0, 68).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 3.5F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition cube_r10 = bone10.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(48, 69).addBox(-3.0F, -4.0F, -2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 3.5F, 1.0F, 0.0F, 0.0F, -0.829F));

        PartDefinition bone11 = bone10.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(32, 54).addBox(-3.0F, 3.0F, -1.0F, 6.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3F, 2.0F, -2.5F, 0.0F, 0.0F, -0.0873F));

        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    public void setupAnim(LivingEntityRenderState renderState) {
        super.setupAnim(renderState);
    }
}
