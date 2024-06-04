package com.aetherteam.aetherii.client.renderer.entity.model;// Made with Blockbench 4.10.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.aetherteam.aetherii.client.renderer.entity.animation.KirridAnimations;
import com.aetherteam.aetherii.client.renderer.entity.animation.KirridBabyAnimations;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class KirridBabyModel<T extends Kirrid> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart wool;
    private final ModelPart head;
    private final ModelPart plate;
    private final ModelPart ears;
    private final ModelPart leg_b_l;
    private final ModelPart leg_b_r;
    private final ModelPart tail;
    private final ModelPart leg_f_l;
    private final ModelPart leg_f_r;

    public KirridBabyModel(ModelPart root) {
        this.root = root;
        this.body_main = root.getChild("body_main");
        this.wool = this.body_main.getChild("wool");
        this.head = this.body_main.getChild("head");
        this.plate = this.head.getChild("plate");
        this.ears = this.head.getChild("ears");
        this.leg_b_l = this.body_main.getChild("leg_b_l");
        this.leg_b_r = this.body_main.getChild("leg_b_r");
        this.tail = this.body_main.getChild("tail");
        this.leg_f_l = this.body_main.getChild("leg_f_l");
        this.leg_f_r = this.body_main.getChild("leg_f_r");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body_main = partdefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(17, 10).addBox(-2.5F, -2.5F, -3.0F, 5.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, -0.5F));

        PartDefinition wool = body_main.addOrReplaceChild("wool", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.5F, -1.5F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body_main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 10).addBox(-2.0F, -5.1F, -4.7F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.2F, -2.0F, 0.6374F, 0.0F, 0.0F));

        PartDefinition plate = head.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(1, 0).addBox(-2.5F, -6.0F, -5.3F, 5.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.3203F, 0.0F, 0.0F));

        PartDefinition ears = head.addOrReplaceChild("ears", CubeListBuilder.create().texOffs(0, 8).addBox(-3.5F, -2.8F, 1.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition leg_b_l = body_main.addOrReplaceChild("leg_b_l", CubeListBuilder.create().texOffs(54, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.7F, 1.0F, 5.5F));

        PartDefinition leg_b_r = body_main.addOrReplaceChild("leg_b_r", CubeListBuilder.create().texOffs(46, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.7F, 1.0F, 5.5F));

        PartDefinition tail = body_main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(29, 25).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 7.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition leg_f_l = body_main.addOrReplaceChild("leg_f_l", CubeListBuilder.create().texOffs(54, 9).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.7F, 1.0F, -0.9F));

        PartDefinition leg_f_r = body_main.addOrReplaceChild("leg_f_r", CubeListBuilder.create().texOffs(46, 9).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.7F, 1.0F, -0.9F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
        this.head.xRot = headPitch * (float) (Math.PI / 180.0);
        this.animate(entity.jumpAnimationState, KirridBabyAnimations.JUMP, ageInTicks, 1.0F);
        this.animate(entity.eatAnimationState, KirridBabyAnimations.EAT, ageInTicks, 1.0F);
        if (!entity.jumpAnimationState.isStarted()) {
            this.animateWalk(KirridAnimations.WALK, limbSwing, limbSwingAmount, 2.0F, 2.0F);

        }
        this.plate.visible = entity.hasPlate();
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}