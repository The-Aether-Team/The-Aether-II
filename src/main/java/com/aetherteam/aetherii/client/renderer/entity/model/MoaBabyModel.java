package com.aetherteam.aetherii.client.renderer.entity.model;// Made with Blockbench 4.10.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.aetherteam.aetherii.client.renderer.entity.animation.MoaBabyAnimation;
import com.aetherteam.aetherii.client.renderer.entity.state.MoaRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class MoaBabyModel<T extends MoaRenderState> extends EntityModel<T> {
    private final ModelPart body_main;
    private final ModelPart tail;
    private final ModelPart leg_l_1;
    private final ModelPart leg_l_2;
    private final ModelPart head;
    private final ModelPart feather_1;
    private final ModelPart feather_2;
    private final ModelPart feather_3;
    private final ModelPart feather_4;
    private final ModelPart wing_l;
    private final ModelPart wing_r;
    private final ModelPart leg_r_1;
    private final ModelPart leg_r_2;
    private final ModelPart neck;

    public MoaBabyModel(ModelPart root) {
        super(root);
        this.body_main = root.getChild("body_main");
        this.tail = this.body_main.getChild("tail");
        this.leg_l_1 = this.body_main.getChild("leg_l_1");
        this.leg_l_2 = this.leg_l_1.getChild("leg_l_2");
        this.head = this.body_main.getChild("head");
        this.feather_1 = this.head.getChild("feather_1");
        this.feather_2 = this.head.getChild("feather_2");
        this.feather_3 = this.head.getChild("feather_3");
        this.feather_4 = this.head.getChild("feather_4");
        this.wing_l = this.body_main.getChild("wing_l");
        this.wing_r = this.body_main.getChild("wing_r");
        this.leg_r_1 = this.body_main.getChild("leg_r_1");
        this.leg_r_2 = this.leg_r_1.getChild("leg_r_2");
        this.neck = this.body_main.getChild("neck");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body_main = partdefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-3.0F, -1.5F, -3.5F, 6.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 15.0F, 0.0F));

        PartDefinition tail = body_main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(26, 25).mirror().addBox(-2.5F, 0.0F, 0.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.0F, 5.5F, -1.0472F, 0.0F, 0.0F));

        PartDefinition leg_l_1 = body_main.addOrReplaceChild("leg_l_1", CubeListBuilder.create().texOffs(50, 14).mirror().addBox(-2.0F, -2.0F, -2.1F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 2.0F, 2.5F, -0.7285F, 0.0F, 0.0F));

        PartDefinition leg_l_2 = leg_l_1.addOrReplaceChild("leg_l_2", CubeListBuilder.create().texOffs(56, 24).mirror().addBox(-1.5F, 0.2F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.6981F, 0.0F, 0.0F));

        PartDefinition head = body_main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.5F, -3.7F, -5.4F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -4.0F, -2.5F));

        PartDefinition feather_1 = head.addOrReplaceChild("feather_1", CubeListBuilder.create().texOffs(21, 16).mirror().addBox(-2.3F, -0.5F, 1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.2F, -0.0873F, -0.2618F, 0.0F));

        PartDefinition feather_2 = head.addOrReplaceChild("feather_2", CubeListBuilder.create().texOffs(21, 12).mirror().addBox(-2.3F, -3.0F, 1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.2F, 0.0873F, -0.2618F, 0.0F));

        PartDefinition feather_3 = head.addOrReplaceChild("feather_3", CubeListBuilder.create().texOffs(1, 12).mirror().addBox(1.3F, -3.0F, 1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.2F, 0.0873F, 0.2618F, 0.0F));

        PartDefinition feather_4 = head.addOrReplaceChild("feather_4", CubeListBuilder.create().texOffs(1, 16).mirror().addBox(1.3F, -0.5F, 1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.2F, -0.0873F, 0.2618F, 0.0F));

        PartDefinition wing_l = body_main.addOrReplaceChild("wing_l", CubeListBuilder.create().texOffs(48, 3).mirror().addBox(-1.0F, -2.0F, -1.0F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 1.0F, -1.9F, 0.0F, -0.4363F, 0.0F));

        PartDefinition wing_r = body_main.addOrReplaceChild("wing_r", CubeListBuilder.create().texOffs(32, 3).mirror().addBox(0.0F, -2.0F, -1.0F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 1.0F, -1.9F, 0.0F, 0.4363F, 0.0F));

        PartDefinition leg_r_1 = body_main.addOrReplaceChild("leg_r_1", CubeListBuilder.create().texOffs(36, 14).mirror().addBox(-1.0F, -2.0F, -2.1F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, 2.0F, 2.5F, -0.6981F, 0.0F, 0.0F));

        PartDefinition leg_r_2 = leg_r_1.addOrReplaceChild("leg_r_2", CubeListBuilder.create().texOffs(48, 24).mirror().addBox(-0.5F, 0.2F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.6981F, 0.0F, 0.0F));

        PartDefinition neck = body_main.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(24, 1).mirror().addBox(-1.0F, -4.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.0F, -3.0F, -0.1396F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity) {
        super.setupAnim(entity);
        this.head.xRot = entity.xRot * Mth.DEG_TO_RAD;
        this.head.yRot = entity.yRot * Mth.DEG_TO_RAD;
        float flyAmount = entity.flyAmount;
        this.animateWalk(MoaBabyAnimation.walk, entity.walkAnimationPos, Mth.clamp(entity.walkAnimationSpeed - flyAmount, 0, 1F), 2.0F, 2.5F);
        if (entity.sitting) {
            this.applyStatic(MoaBabyAnimation.sit);
        }
        this.animateWalk(MoaBabyAnimation.flying, entity.ageInTicks, flyAmount, 1.0F, 1.0F);

    }
}