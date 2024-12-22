package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.client.renderer.entity.animation.MoaAnimation;
import com.aetherteam.aetherii.client.renderer.entity.state.MoaRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class MoaModel<T extends MoaRenderState> extends EntityModel<T> {
    private final ModelPart body_main;
    private final ModelPart body_front;
    private final ModelPart neck;
    private final ModelPart head_main;
    private final ModelPart head_beak_main;
    private final ModelPart jaw_main;
    private final ModelPart jaw_back;
    private final ModelPart jaw_teeth;
    private final ModelPart head_front;
    private final ModelPart head_brow;
    private final ModelPart head_feather_l1;
    private final ModelPart head_feather_r1;
    private final ModelPart head_feather_l2;
    private final ModelPart head_feather_r2;
    private final ModelPart beak_int;
    private final ModelPart wing_left_1;
    private final ModelPart wing_left_feather_1;
    private final ModelPart wing_left_2;
    private final ModelPart wing_left_3;
    private final ModelPart wing_left_feather_5;
    private final ModelPart wing_left_feather_4;
    private final ModelPart wing_left_feather_3;
    private final ModelPart wing_left_feather_2;
    private final ModelPart wing_right_1;
    private final ModelPart wing_right_feather_1;
    private final ModelPart wing_right_2;
    private final ModelPart wing_right_3;
    private final ModelPart wing_right_feather_5;
    private final ModelPart wing_right_feather_4;
    private final ModelPart wing_right_feather_3;
    private final ModelPart wing_right_feather_2;
    private final ModelPart tail_base;
    private final ModelPart tail_feather_right;
    private final ModelPart tail_feather_mid;
    private final ModelPart tail_feather_left;
    private final ModelPart leg_left_1;
    private final ModelPart leg_left_2;
    private final ModelPart leg_left_3;
    private final ModelPart leg_left_foot;
    private final ModelPart leg_left_toe_mid;
    private final ModelPart leg_left_toe_right;
    private final ModelPart leg_left_toe_left;
    private final ModelPart leg_right_1;
    private final ModelPart leg_right_2;
    private final ModelPart leg_right_3;
    private final ModelPart leg_right_foot;
    private final ModelPart leg_right_toe_mid;
    private final ModelPart leg_right_toe_right;
    private final ModelPart leg_right_toe_left;

    public MoaModel(ModelPart root) {
        super(root);
        this.body_main = root.getChild("body_main");
        this.body_front = this.body_main.getChild("body_front");
        this.neck = this.body_front.getChild("neck");
        this.head_main = this.neck.getChild("head_main");
        this.head_beak_main = this.head_main.getChild("head_beak_main");
        this.jaw_main = this.head_main.getChild("jaw_main");
        this.jaw_back = this.jaw_main.getChild("jaw_back");
        this.jaw_teeth = this.jaw_main.getChild("jaw_teeth");
        this.head_front = this.head_main.getChild("head_front");
        this.head_brow = this.head_main.getChild("head_brow");
        this.head_feather_l1 = this.head_main.getChild("head_feather_l1");
        this.head_feather_r1 = this.head_main.getChild("head_feather_r1");
        this.head_feather_l2 = this.head_main.getChild("head_feather_l2");
        this.head_feather_r2 = this.head_main.getChild("head_feather_r2");
        this.beak_int = this.head_main.getChild("beak_int");
        this.wing_left_1 = this.body_front.getChild("wing_left_1");
        this.wing_left_feather_1 = this.wing_left_1.getChild("wing_left_feather_1");
        this.wing_left_2 = this.wing_left_1.getChild("wing_left_2");
        this.wing_left_3 = this.wing_left_2.getChild("wing_left_3");
        this.wing_left_feather_5 = this.wing_left_3.getChild("wing_left_feather_5");
        this.wing_left_feather_4 = this.wing_left_3.getChild("wing_left_feather_4");
        this.wing_left_feather_3 = this.wing_left_3.getChild("wing_left_feather_3");
        this.wing_left_feather_2 = this.wing_left_2.getChild("wing_left_feather_2");
        this.wing_right_1 = this.body_front.getChild("wing_right_1");
        this.wing_right_feather_1 = this.wing_right_1.getChild("wing_right_feather_1");
        this.wing_right_2 = this.wing_right_1.getChild("wing_right_2");
        this.wing_right_3 = this.wing_right_2.getChild("wing_right_3");
        this.wing_right_feather_5 = this.wing_right_3.getChild("wing_right_feather_5");
        this.wing_right_feather_4 = this.wing_right_3.getChild("wing_right_feather_4");
        this.wing_right_feather_3 = this.wing_right_3.getChild("wing_right_feather_3");
        this.wing_right_feather_2 = this.wing_right_2.getChild("wing_right_feather_2");
        this.tail_base = this.body_main.getChild("tail_base");
        this.tail_feather_right = this.tail_base.getChild("tail_feather_right");
        this.tail_feather_mid = this.tail_base.getChild("tail_feather_mid");
        this.tail_feather_left = this.tail_base.getChild("tail_feather_left");
        this.leg_left_1 = this.body_main.getChild("leg_left_1");
        this.leg_left_2 = this.leg_left_1.getChild("leg_left_2");
        this.leg_left_3 = this.leg_left_2.getChild("leg_left_3");
        this.leg_left_foot = this.leg_left_3.getChild("leg_left_foot");
        this.leg_left_toe_mid = this.leg_left_foot.getChild("leg_left_toe_mid");
        this.leg_left_toe_right = this.leg_left_foot.getChild("leg_left_toe_right");
        this.leg_left_toe_left = this.leg_left_foot.getChild("leg_left_toe_left");
        this.leg_right_1 = this.body_main.getChild("leg_right_1");
        this.leg_right_2 = this.leg_right_1.getChild("leg_right_2");
        this.leg_right_3 = this.leg_right_2.getChild("leg_right_3");
        this.leg_right_foot = this.leg_right_3.getChild("leg_right_foot");
        this.leg_right_toe_mid = this.leg_right_foot.getChild("leg_right_toe_mid");
        this.leg_right_toe_right = this.leg_right_foot.getChild("leg_right_toe_right");
        this.leg_right_toe_left = this.leg_right_foot.getChild("leg_right_toe_left");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body_main = partdefinition.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(58, 130).mirror().addBox(-4.5F, 0.0F, -5.0F, 9.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 1.1F, 0.0F));

        PartDefinition body_front = body_main.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(59, 108).mirror().addBox(-4.0F, 0.0F, -9.0F, 8.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -0.5F, -4.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition neck = body_front.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(68, 85).mirror().addBox(-2.5F, -13.0F, -3.0F, 5.0F, 17.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 4.0F, -7.0F));

        PartDefinition head_main = neck.addOrReplaceChild("head_main", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-4.5F, -6.0F, -4.5F, 9.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -12.0F, 0.0F, 0.1886F, 0.0F, 0.0F));

        PartDefinition head_beak_main = head_main.addOrReplaceChild("head_beak_main", CubeListBuilder.create().texOffs(63, 30).mirror().addBox(-2.5F, -3.8F, -10.9F, 5.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.0F, -4.7F, 0.1396F, 0.0F, 0.0F));

        PartDefinition jaw_main = head_main.addOrReplaceChild("jaw_main", CubeListBuilder.create().texOffs(60, 68).mirror().addBox(-3.0F, -1.7F, -14.0F, 6.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, -2.0F));

        PartDefinition jaw_back = jaw_main.addOrReplaceChild("jaw_back", CubeListBuilder.create().texOffs(65, 57).mirror().addBox(-3.5F, -1.6F, -3.5F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition jaw_teeth = jaw_main.addOrReplaceChild("jaw_teeth", CubeListBuilder.create().texOffs(43, 64).mirror().addBox(-2.5F, -3.6F, -13.5F, 5.0F, 2.0F, 10.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head_front = head_main.addOrReplaceChild("head_front", CubeListBuilder.create().texOffs(67, 47).mirror().addBox(-3.0F, -4.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.5F, -4.5F, 0.0F, -0.7854F, 0.0F));

        PartDefinition head_brow = head_main.addOrReplaceChild("head_brow", CubeListBuilder.create().texOffs(65, 20).mirror().addBox(-3.5F, 0.0F, -3.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -5.9F, -5.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition head_feather_l1 = head_main.addOrReplaceChild("head_feather_l1", CubeListBuilder.create().texOffs(98, 0).mirror().addBox(-0.5F, -2.0F, -1.0F, 1.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.5F, -4.0F, 2.0F, 0.1745F, -0.3491F, 0.0F));

        PartDefinition head_feather_r1 = head_main.addOrReplaceChild("head_feather_r1", CubeListBuilder.create().texOffs(38, 0).mirror().addBox(-0.5F, -2.0F, -1.0F, 1.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, -4.0F, 2.0F, 0.1745F, 0.3491F, 0.0F));

        PartDefinition head_feather_l2 = head_main.addOrReplaceChild("head_feather_l2", CubeListBuilder.create().texOffs(98, 14).mirror().addBox(-0.5F, -2.0F, -1.0F, 1.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.5F, 1.0F, 2.0F, -0.1745F, -0.3491F, 0.0F));

        PartDefinition head_feather_r2 = head_main.addOrReplaceChild("head_feather_r2", CubeListBuilder.create().texOffs(38, 14).mirror().addBox(-0.5F, -2.0F, -1.0F, 1.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, 1.0F, 1.9F, -0.1745F, 0.3491F, 0.0F));

        PartDefinition beak_int = head_main.addOrReplaceChild("beak_int", CubeListBuilder.create().texOffs(51, 57).mirror().addBox(-2.5F, 0.2F, -7.0F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wing_left_1 = body_front.addOrReplaceChild("wing_left_1", CubeListBuilder.create().texOffs(118, 0).mirror().addBox(-10.5F, -2.0F, -4.0F, 12.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 1.5F, -4.0F, 0.0F, -0.1745F, -0.9599F));

        PartDefinition wing_left_feather_1 = wing_left_1.addOrReplaceChild("wing_left_feather_1", CubeListBuilder.create().texOffs(120, 19).mirror().addBox(-10.5F, 0.5F, -2.5F, 11.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.0F, -1.5F, 0.5F, 0.0F, 1.4835F, 0.0F));

        PartDefinition wing_left_2 = wing_left_1.addOrReplaceChild("wing_left_2", CubeListBuilder.create().texOffs(116, 9).mirror().addBox(-10.0F, -1.5F, -3.5F, 13.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-10.0F, -1.0F, -1.5F, 0.0F, 1.8326F, -0.5236F));

        PartDefinition wing_left_3 = wing_left_2.addOrReplaceChild("wing_left_3", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, 0.2618F, -0.1745F));

        PartDefinition wing_left_feather_5 = wing_left_3.addOrReplaceChild("wing_left_feather_5", CubeListBuilder.create().texOffs(114, 43).mirror().addBox(-17.0F, -0.5F, -2.5F, 17.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, -0.1745F, 0.0F));

        PartDefinition wing_left_feather_4 = wing_left_3.addOrReplaceChild("wing_left_feather_4", CubeListBuilder.create().texOffs(115, 37).mirror().addBox(-14.0F, -0.5F, -2.5F, 16.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.5F, 2.0F, 0.0F, 0.1745F, 0.0F));

        PartDefinition wing_left_feather_3 = wing_left_3.addOrReplaceChild("wing_left_feather_3", CubeListBuilder.create().texOffs(117, 31).mirror().addBox(-13.5F, -0.5F, -2.5F, 14.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 1.0F, 3.0F, 0.0F, 0.6981F, 0.0F));

        PartDefinition wing_left_feather_2 = wing_left_2.addOrReplaceChild("wing_left_feather_2", CubeListBuilder.create().texOffs(119, 25).mirror().addBox(-11.5F, 0.0F, -2.5F, 12.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 0.0F, 3.0F, 0.0F, 1.3963F, 0.0F));

        PartDefinition wing_right_1 = body_front.addOrReplaceChild("wing_right_1", CubeListBuilder.create().texOffs(4, 0).mirror().addBox(-1.5F, -2.0F, -4.0F, 12.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 1.5F, -4.0F, 0.0F, 0.1745F, 0.9599F));

        PartDefinition wing_right_feather_1 = wing_right_1.addOrReplaceChild("wing_right_feather_1", CubeListBuilder.create().texOffs(6, 19).mirror().addBox(0.5F, 0.5F, -2.5F, 11.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, -1.5F, 0.5F, 0.0F, -1.4835F, 0.0F));

        PartDefinition wing_right_2 = wing_right_1.addOrReplaceChild("wing_right_2", CubeListBuilder.create().texOffs(2, 9).mirror().addBox(-3.0F, -1.5F, -3.5F, 13.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.0F, -1.0F, -1.5F, 0.0F, -1.8326F, 0.5236F));

        PartDefinition wing_right_3 = wing_right_2.addOrReplaceChild("wing_right_3", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-3.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.1745F));

        PartDefinition wing_right_feather_5 = wing_right_3.addOrReplaceChild("wing_right_feather_5", CubeListBuilder.create().texOffs(0, 43).mirror().addBox(0.0F, -0.5F, -2.5F, 17.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, 0.1745F, 0.0F));

        PartDefinition wing_right_feather_4 = wing_right_3.addOrReplaceChild("wing_right_feather_4", CubeListBuilder.create().texOffs(1, 37).mirror().addBox(-2.0F, -0.5F, -2.5F, 16.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.5F, 2.0F, 0.0F, -0.1745F, 0.0F));

        PartDefinition wing_right_feather_3 = wing_right_3.addOrReplaceChild("wing_right_feather_3", CubeListBuilder.create().texOffs(3, 31).mirror().addBox(-1.5F, -0.5F, -2.5F, 14.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 1.0F, 3.0F, 0.0F, -0.6981F, 0.0F));

        PartDefinition wing_right_feather_2 = wing_right_2.addOrReplaceChild("wing_right_feather_2", CubeListBuilder.create().texOffs(5, 25).mirror().addBox(-0.5F, 0.0F, -2.5F, 12.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 0.0F, 3.0F, 0.0F, -1.3963F, 0.0F));

        PartDefinition tail_base = body_main.addOrReplaceChild("tail_base", CubeListBuilder.create().texOffs(63, 152).mirror().addBox(-3.5F, -1.6F, -4.5F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.7F, 8.5F, 0.0F, 0.7854F, 0.0F));

        PartDefinition tail_feather_right = tail_base.addOrReplaceChild("tail_feather_right", CubeListBuilder.create().texOffs(-15, 118).mirror().addBox(-2.5F, -0.5F, 0.0F, 5.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 1.0F, 3.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition tail_feather_mid = tail_base.addOrReplaceChild("tail_feather_mid", CubeListBuilder.create().texOffs(-7, 114).mirror().addBox(-2.5F, -0.5F, 0.0F, 5.0F, 1.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, 1.0F, -0.5236F, -0.7854F, 0.0F));

        PartDefinition tail_feather_left = tail_base.addOrReplaceChild("tail_feather_left", CubeListBuilder.create().texOffs(9, 118).mirror().addBox(-2.5F, -0.5F, 0.0F, 5.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 1.0F, -1.0F, -0.5236F, -1.5708F, 0.0F));

        PartDefinition leg_left_1 = body_main.addOrReplaceChild("leg_left_1", CubeListBuilder.create().texOffs(124, 49).mirror().addBox(-4.0F, -2.5F, -3.5F, 5.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 6.0F, 3.0F, -0.6981F, 0.0F, 0.3491F));

        PartDefinition leg_left_2 = leg_left_1.addOrReplaceChild("leg_left_2", CubeListBuilder.create().texOffs(128, 68).mirror().addBox(-2.0F, -1.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 7.0F, 1.5F, 1.9199F, 0.0F, -0.0873F));

        PartDefinition leg_left_3 = leg_left_2.addOrReplaceChild("leg_left_3", CubeListBuilder.create().texOffs(130, 81).mirror().addBox(-1.5F, -0.5F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.5F, 0.0F, -1.0472F, 0.0F, -0.1222F));

        PartDefinition leg_left_foot = leg_left_3.addOrReplaceChild("leg_left_foot", CubeListBuilder.create().texOffs(127, 94).mirror().addBox(-2.0F, 0.0F, -3.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 7.5F, -1.5F, -0.1745F, 0.0F, -0.2443F));

        PartDefinition leg_left_toe_mid = leg_left_foot.addOrReplaceChild("leg_left_toe_mid", CubeListBuilder.create().texOffs(126, 101).mirror().addBox(-1.5F, -1.0F, -7.0F, 3.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, -2.5F, -0.4887F, 0.0F, 0.0F));

        PartDefinition leg_left_toe_right = leg_left_foot.addOrReplaceChild("leg_left_toe_right", CubeListBuilder.create().texOffs(114, 103).mirror().addBox(-0.5F, -1.0F, -5.0F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 1.0F, -2.5F, -0.1745F, -0.5236F, 0.0F));

        PartDefinition leg_left_toe_left = leg_left_foot.addOrReplaceChild("leg_left_toe_left", CubeListBuilder.create().texOffs(146, 103).mirror().addBox(-0.5F, -1.0F, -5.0F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 1.0F, -2.5F, -0.1745F, 0.5236F, 0.0F));

        PartDefinition leg_right_1 = body_main.addOrReplaceChild("leg_right_1", CubeListBuilder.create().texOffs(10, 49).mirror().addBox(-1.0F, -2.5F, -3.5F, 5.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, 6.0F, 3.0F, -0.6981F, 0.0F, -0.3491F));

        PartDefinition leg_right_2 = leg_right_1.addOrReplaceChild("leg_right_2", CubeListBuilder.create().texOffs(14, 68).mirror().addBox(-2.0F, -1.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, 7.0F, 1.5F, 1.9199F, 0.0F, 0.0873F));

        PartDefinition leg_right_3 = leg_right_2.addOrReplaceChild("leg_right_3", CubeListBuilder.create().texOffs(16, 81).mirror().addBox(-1.5F, -0.5F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.5F, 0.0F, -1.0472F, 0.0F, 0.1222F));

        PartDefinition leg_right_foot = leg_right_3.addOrReplaceChild("leg_right_foot", CubeListBuilder.create().texOffs(13, 94).mirror().addBox(-2.0F, 0.0F, -3.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 7.5F, -1.5F, -0.1745F, 0.0F, 0.2443F));

        PartDefinition leg_right_toe_mid = leg_right_foot.addOrReplaceChild("leg_right_toe_mid", CubeListBuilder.create().texOffs(12, 101).mirror().addBox(-1.5F, -1.0F, -7.0F, 3.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, -2.5F, -0.4887F, 0.0F, 0.0F));

        PartDefinition leg_right_toe_right = leg_right_foot.addOrReplaceChild("leg_right_toe_right", CubeListBuilder.create().texOffs(0, 103).mirror().addBox(-0.5F, -1.0F, -5.0F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 1.0F, -2.5F, -0.1745F, -0.5236F, 0.0F));

        PartDefinition leg_right_toe_left = leg_right_foot.addOrReplaceChild("leg_right_toe_left", CubeListBuilder.create().texOffs(32, 103).mirror().addBox(-0.5F, -1.0F, -5.0F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 1.0F, -2.5F, -0.1745F, 0.5236F, 0.0F));

        return LayerDefinition.create(meshdefinition, 168, 168);
    }

    @Override
    public void setupAnim(T entity) {
        super.setupAnim(entity);
        this.head_main.xRot = (entity.xRot * Mth.DEG_TO_RAD) + 0.1886F;
        this.head_main.yRot = entity.yRot * Mth.DEG_TO_RAD;
        float flyAmount = entity.flyAmount;
        this.animateWalk(MoaAnimation.walk, entity.walkAnimationPos, Mth.clamp(entity.walkAnimationSpeed - flyAmount, 0, 1F), 2.0F, 2.5F);
        if (entity.sitting) {
            this.applyStatic(MoaAnimation.sit);
        }
        this.animateWalk(MoaAnimation.fly, entity.ageInTicks, flyAmount, 0.5F, 1.0F);
    }
}