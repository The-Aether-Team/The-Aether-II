package com.aetherteam.aetherii.client.renderer.entity.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public abstract class AbstractGolemModel<T extends LivingEntityRenderState> extends EntityModel<T> {
    public final ModelPart body;
    public final ModelPart head;
    public final ModelPart leftArm;
    public final ModelPart rightArm;
    public final ModelPart leftLeg;
    public final ModelPart rightLeg;

    public AbstractGolemModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
        this.leftArm = this.body.getChild("left_arm");
        this.rightArm = this.body.getChild("right_arm");
        this.leftLeg = this.body.getChild("left_leg");
        this.rightLeg = this.body.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 50).addBox(-6.0F, 1.25F, -4.5F, 12.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(-8.0F, -8.75F, -5.5F, 16.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.75F, 0.0F));

        body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -6.0F, -4.5F, 11.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.75F, -0.5F));

        body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(54, 17).addBox(-5.0F, 5.0F, -3.5F, 5.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(40, 3).addBox(-4.0F, -3.0F, -3.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -4.75F, 0.0F));

        body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(54, 17).mirror().addBox(0.0F, 5.0F, -3.5F, 5.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(40, 3).mirror().addBox(0.0F, -3.0F, -3.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, -4.75F, 0.0F));

        body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(42, 47).addBox(-2.5F, 0.0F, -3.0F, 5.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 6.25F, 0.0F));

        body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(42, 47).mirror().addBox(-2.5F, 0.0F, -3.0F, 5.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.5F, 6.25F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

}
