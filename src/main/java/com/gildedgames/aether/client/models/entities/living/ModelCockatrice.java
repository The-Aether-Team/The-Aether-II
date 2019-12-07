package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.client.renderer.entities.living.layers.ILayeredModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCockatrice extends ModelBase implements ILayeredModel
{

	public ModelRenderer body_base;
	public ModelRenderer thigh_left;
	public ModelRenderer thigh_right;
	public ModelRenderer body_top;
	public ModelRenderer body_back;
	public ModelRenderer dart_left_bottom;
	public ModelRenderer dart_left_middle;
	public ModelRenderer dart_left_top;
	public ModelRenderer dart_right_top;
	public ModelRenderer dart_right_middle;
	public ModelRenderer dart_right_bottom;
	public ModelRenderer leg_left;
	public ModelRenderer ankle_left;
	public ModelRenderer foot_left;
	public ModelRenderer toe_left_1;
	public ModelRenderer toe_left_2;
	public ModelRenderer toe_left_3;
	public ModelRenderer toe_left_4;
	public ModelRenderer leg_right;
	public ModelRenderer ankle_right;
	public ModelRenderer foot_right;
	public ModelRenderer toe_right_1;
	public ModelRenderer toe_right_2;
	public ModelRenderer toe_right_3;
	public ModelRenderer toe_right_4;
	public ModelRenderer shoulder_left;
	public ModelRenderer shoulder_right;
	public ModelRenderer neck;
	public ModelRenderer arm_left;
	public ModelRenderer wing_left;
	public ModelRenderer wing_left_feathers;
	public ModelRenderer thumb_left;
	public ModelRenderer claw_left_1;
	public ModelRenderer claw_left_2;
	public ModelRenderer arm_right;
	public ModelRenderer wing_right;
	public ModelRenderer wing_right_feathers;
	public ModelRenderer thumb_right;
	public ModelRenderer claw_right_1;
	public ModelRenderer claw_right_2;
	public ModelRenderer head;
	public ModelRenderer beak_bottom;
	public ModelRenderer beak_top;
	public ModelRenderer crest_left;
	public ModelRenderer crest_right;
	public ModelRenderer tongue;
	public ModelRenderer feather_right_1;
	public ModelRenderer feather_left_1;
	public ModelRenderer feather_right_2;
	public ModelRenderer feather_left_2;

	public ModelCockatrice() {
		this.textureWidth = 64;
		this.textureHeight = 128;
		this.toe_left_2 = new ModelRenderer(this, 4, 124);
		this.toe_left_2.setRotationPoint(0.0F, -1.0F, -2.0F);
		this.toe_left_2.addBox(-0.5F, -0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(toe_left_2, -0.6981317007977318F, 0.0F, 0.0F);
		this.dart_right_bottom = new ModelRenderer(this, 10, 119);
		this.dart_right_bottom.mirror = true;
		this.dart_right_bottom.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.dart_right_bottom.addBox(-6.5F, -2.0F, -6.5F, 1, 1, 8, 0.0F);
		this.crest_left = new ModelRenderer(this, 22, 24);
		this.crest_left.setRotationPoint(2.0F, -2.0F, 0.0F);
		this.crest_left.addBox(-1.0F, -13.0F, 0.0F, 8, 15, 1, 0.0F);
		this.setRotateAngle(crest_left, 0.0F, 0.0F, 0.08726646259971647F);
		this.beak_bottom = new ModelRenderer(this, 0, 10);
		this.beak_bottom.setRotationPoint(0.0F, -1.7F, -4.0F);
		this.beak_bottom.addBox(-3.5F, -1.0F, -7.0F, 7, 3, 8, 0.0F);
		this.crest_right = new ModelRenderer(this, 22, 24);
		this.crest_right.mirror = true;
		this.crest_right.setRotationPoint(-2.0F, -2.0F, 0.0F);
		this.crest_right.addBox(-7.0F, -13.0F, 0.0F, 8, 15, 1, 0.0F);
		this.setRotateAngle(crest_right, 0.0F, 0.0F, -0.08726646259971647F);
		this.wing_right = new ModelRenderer(this, 28, 46);
		this.wing_right.mirror = true;
		this.wing_right.setRotationPoint(0.0F, 5.5F, 0.5F);
		this.wing_right.addBox(-1.5F, -0.5F, -13.5F, 3, 4, 15, 0.0F);
		this.setRotateAngle(wing_right, 0.5235987755982988F, 0.0F, 0.0F);
		this.foot_left = new ModelRenderer(this, 0, 118);
		this.foot_left.setRotationPoint(0.0F, 3.6F, 0.0F);
		this.foot_left.addBox(-2.0F, 0.0F, -3.0F, 4, 1, 5, 0.0F);
		this.setRotateAngle(foot_left, 0.2617993877991494F, 0.22689280275926282F, 0.22689280275926282F);
		this.shoulder_right = new ModelRenderer(this, 27, 65);
		this.shoulder_right.mirror = true;
		this.shoulder_right.setRotationPoint(-5.0F, -3.0F, 3.0F);
		this.shoulder_right.addBox(-3.0F, -2.0F, -2.0F, 4, 6, 4, 0.0F);
		this.setRotateAngle(shoulder_right, 0.17453292519943295F, 0.0F, 0.6981317007977318F);
		this.beak_top = new ModelRenderer(this, 0, 0);
		this.beak_top.setRotationPoint(0.0F, -2.7F, -4.0F);
		this.beak_top.addBox(-3.5F, -1.0F, -7.0F, 7, 2, 8, 0.0F);
		this.body_base = new ModelRenderer(this, 0, 80);
		this.body_base.setRotationPoint(0.0F, 9.0F, 2.0F);
		this.body_base.addBox(-6.0F, -8.0F, -4.0F, 12, 14, 8, 0.0F);
		this.setRotateAngle(body_base, 0.3490658503988659F, 0.0F, 0.0F);
		this.wing_left = new ModelRenderer(this, 28, 46);
		this.wing_left.setRotationPoint(0.0F, 5.5F, 0.5F);
		this.wing_left.addBox(-1.5F, -0.5F, -13.5F, 3, 4, 15, 0.0F);
		this.setRotateAngle(wing_left, 0.5235987755982988F, 0.0F, 0.0F);
		this.thumb_left = new ModelRenderer(this, 53, 70);
		this.thumb_left.setRotationPoint(-1.0F, 1.5F, -10.0F);
		this.thumb_left.addBox(-2.5F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(thumb_left, 0.0F, -1.0471975511965976F, 0.0F);
		this.claw_left_2 = new ModelRenderer(this, 53, 68);
		this.claw_left_2.setRotationPoint(0.5F, 2.5F, -13.0F);
		this.claw_left_2.addBox(-2.5F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(claw_left_2, 0.0F, -0.2617993877991494F, 0.0F);
		this.feather_left_1 = new ModelRenderer(this, 36, 0);
		this.feather_left_1.setRotationPoint(0.0F, 5.0F, 4.0F);
		this.feather_left_1.addBox(0.0F, -15.0F, 0.0F, 4, 15, 1, 0.0F);
		this.setRotateAngle(feather_left_1, -0.8726646259971648F, 0.08726646259971647F, 0.0F);
		this.shoulder_left = new ModelRenderer(this, 27, 65);
		this.shoulder_left.setRotationPoint(5.0F, -3.0F, 3.0F);
		this.shoulder_left.addBox(-1.0F, -2.0F, -2.0F, 4, 6, 4, 0.0F);
		this.setRotateAngle(shoulder_left, 0.17453292519943295F, 0.0F, -0.6981317007977318F);
		this.ankle_right = new ModelRenderer(this, 0, 104);
		this.ankle_right.mirror = true;
		this.ankle_right.setRotationPoint(0.0F, 5.0F, 0.0F);
		this.ankle_right.addBox(-1.5F, -2.0F, -0.5F, 3, 6, 2, 0.0F);
		this.setRotateAngle(ankle_right, -1.3089969389957472F, 0.0F, 0.0F);
		this.claw_left_1 = new ModelRenderer(this, 53, 68);
		this.claw_left_1.setRotationPoint(0.5F, 0.5F, -13.0F);
		this.claw_left_1.addBox(-2.5F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(claw_left_1, 0.0F, -0.2617993877991494F, 0.0F);
		this.claw_right_1 = new ModelRenderer(this, 53, 68);
		this.claw_right_1.mirror = true;
		this.claw_right_1.setRotationPoint(-0.5F, 0.5F, -13.0F);
		this.claw_right_1.addBox(-0.5F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(claw_right_1, 0.0F, 0.2617993877991494F, 0.0F);
		this.dart_left_middle = new ModelRenderer(this, 28, 119);
		this.dart_left_middle.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.dart_left_middle.addBox(5.5F, -4.0F, -7.5F, 1, 1, 8, 0.0F);
		this.toe_left_1 = new ModelRenderer(this, 4, 124);
		this.toe_left_1.setRotationPoint(-1.5F, -1.0F, -2.0F);
		this.toe_left_1.addBox(-0.5F, -0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(toe_left_1, -0.6981317007977318F, 0.3490658503988659F, 0.0F);
		this.thigh_left = new ModelRenderer(this, 0, 102);
		this.thigh_left.setRotationPoint(4.0F, 2.5F, 0.0F);
		this.thigh_left.addBox(-2.5F, -3.0F, -9.0F, 5, 6, 10, 0.0F);
		this.setRotateAngle(thigh_left, 0.17453292519943295F, -0.5235987755982988F, 0.0F);
		this.body_back = new ModelRenderer(this, 38, 82);
		this.body_back.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body_back.addBox(-6.0F, 1.0F, 4.0F, 12, 5, 1, 0.0F);
		this.arm_left = new ModelRenderer(this, 43, 66);
		this.arm_left.setRotationPoint(1.0F, 4.0F, 0.0F);
		this.arm_left.addBox(-1.0F, 0.0F, -1.5F, 2, 6, 3, 0.0F);
		this.wing_left_feathers = new ModelRenderer(this, 34, 90);
		this.wing_left_feathers.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.wing_left_feathers.addBox(0.5F, 3.5F, -13.5F, 1, 8, 14, 0.0F);
		this.toe_right_1 = new ModelRenderer(this, 4, 124);
		this.toe_right_1.mirror = true;
		this.toe_right_1.setRotationPoint(-1.5F, -1.0F, -2.0F);
		this.toe_right_1.addBox(-0.5F, -0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(toe_right_1, -0.6981317007977318F, 0.3490658503988659F, 0.0F);
		this.arm_right = new ModelRenderer(this, 43, 66);
		this.arm_right.mirror = true;
		this.arm_right.setRotationPoint(-1.0F, 4.0F, 0.0F);
		this.arm_right.addBox(-1.0F, 0.0F, -1.5F, 2, 6, 3, 0.0F);
		this.toe_left_3 = new ModelRenderer(this, 4, 124);
		this.toe_left_3.setRotationPoint(1.5F, -1.0F, -2.0F);
		this.toe_left_3.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(toe_left_3, -0.6981317007977318F, -0.3490658503988659F, 0.0F);
		this.toe_right_2 = new ModelRenderer(this, 4, 124);
		this.toe_right_2.mirror = true;
		this.toe_right_2.setRotationPoint(-0.0F, -1.0F, -2.0F);
		this.toe_right_2.addBox(-0.5F, -0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(toe_right_2, -0.6981317007977318F, 0.0F, 0.0F);
		this.toe_right_4 = new ModelRenderer(this, 0, 124);
		this.toe_right_4.mirror = true;
		this.toe_right_4.setRotationPoint(0.0F, -1.0F, 1.5F);
		this.toe_right_4.addBox(-0.5F, -0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(toe_right_4, 0.6981317007977318F, 0.0F, 0.0F);
		this.ankle_left = new ModelRenderer(this, 0, 104);
		this.ankle_left.setRotationPoint(0.0F, 5.0F, 0.0F);
		this.ankle_left.addBox(-1.5F, -2.0F, -0.5F, 3, 6, 2, 0.0F);
		this.setRotateAngle(ankle_left, -1.3089969389957472F, 0.0F, 0.0F);
		this.dart_right_top = new ModelRenderer(this, 46, 119);
		this.dart_right_top.mirror = true;
		this.dart_right_top.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.dart_right_top.addBox(-6.5F, -6.0F, -8.0F, 1, 1, 8, 0.0F);
		this.thumb_right = new ModelRenderer(this, 53, 70);
		this.thumb_right.setRotationPoint(1.0F, 1.5F, -10.0F);
		this.thumb_right.addBox(-2.5F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(thumb_right, 0.0F, -2.0943951023931953F, 0.0F);
		this.wing_right_feathers = new ModelRenderer(this, 34, 90);
		this.wing_right_feathers.mirror = true;
		this.wing_right_feathers.setRotationPoint(-1.0F, 0.0F, 0.0F);
		this.wing_right_feathers.addBox(-0.5F, 3.5F, -13.5F, 1, 8, 14, 0.0F);
		this.tongue = new ModelRenderer(this, 14, 10);
		this.tongue.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.tongue.addBox(-3.5F, 1.0F, -7.0F, 7, 0, 8, 0.0F);
		this.dart_left_bottom = new ModelRenderer(this, 10, 119);
		this.dart_left_bottom.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.dart_left_bottom.addBox(5.5F, -2.0F, -6.5F, 1, 1, 8, 0.0F);
		this.toe_right_3 = new ModelRenderer(this, 4, 124);
		this.toe_right_3.mirror = true;
		this.toe_right_3.setRotationPoint(1.5F, -1.0F, -2.0F);
		this.toe_right_3.addBox(-0.5F, -0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(toe_right_3, -0.6981317007977318F, -0.3490658503988659F, 0.0F);
		this.leg_left = new ModelRenderer(this, 20, 104);
		this.leg_left.setRotationPoint(0.0F, 2.5F, -7.0F);
		this.leg_left.addBox(-1.0F, -1.0F, -1.0F, 2, 6, 2, 0.0F);
		this.setRotateAngle(leg_left, 0.5235987755982988F, 0.0F, 0.0F);
		this.neck = new ModelRenderer(this, 0, 45);
		this.neck.setRotationPoint(0.0F, -5.0F, 1.0F);
		this.neck.addBox(-2.0F, -9.0F, 0.0F, 4, 10, 3, 0.0F);
		this.setRotateAngle(neck, -0.2617993877991494F, 0.0F, 0.0F);
		this.feather_right_1 = new ModelRenderer(this, 36, 0);
		this.feather_right_1.mirror = true;
		this.feather_right_1.setRotationPoint(0.0F, 5.0F, 4.0F);
		this.feather_right_1.addBox(-4.0F, -15.0F, 0.0F, 4, 15, 1, 0.0F);
		this.setRotateAngle(feather_right_1, -0.8726646259971648F, -0.08726646259971647F, 0.0F);
		this.body_top = new ModelRenderer(this, 0, 68);
		this.body_top.setRotationPoint(0.0F, -8.0F, -4.0F);
		this.body_top.addBox(-5.0F, -5.0F, 0.0F, 10, 5, 7, 0.0F);
		this.setRotateAngle(body_top, -0.2617993877991494F, 0.0F, 0.0F);
		this.claw_right_2 = new ModelRenderer(this, 53, 68);
		this.claw_right_2.mirror = true;
		this.claw_right_2.setRotationPoint(-0.5F, 2.5F, -13.0F);
		this.claw_right_2.addBox(-0.5F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
		this.setRotateAngle(claw_right_2, 0.0F, 0.2617993877991494F, 0.0F);
		this.leg_right = new ModelRenderer(this, 20, 104);
		this.leg_right.mirror = true;
		this.leg_right.setRotationPoint(0.0F, 2.5F, -7.0F);
		this.leg_right.addBox(-1.0F, -1.0F, -1.0F, 2, 6, 2, 0.0F);
		this.setRotateAngle(leg_right, 0.5235987755982988F, 0.0F, 0.0F);
		this.thigh_right = new ModelRenderer(this, 0, 102);
		this.thigh_right.mirror = true;
		this.thigh_right.setRotationPoint(-4.0F, 2.5F, 0.0F);
		this.thigh_right.addBox(-2.5F, -3.0F, -9.0F, 5, 6, 10, 0.0F);
		this.setRotateAngle(thigh_right, 0.17453292519943295F, 0.5235987755982988F, 0.0F);
		this.toe_left_4 = new ModelRenderer(this, 0, 124);
		this.toe_left_4.setRotationPoint(0.0F, -1.0F, 1.5F);
		this.toe_left_4.addBox(-0.5F, -0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(toe_left_4, 0.6981317007977318F, 0.0F, 0.0F);
		this.dart_right_middle = new ModelRenderer(this, 28, 119);
		this.dart_right_middle.mirror = true;
		this.dart_right_middle.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.dart_right_middle.addBox(-6.5F, -4.0F, -7.5F, 1, 1, 8, 0.0F);
		this.foot_right = new ModelRenderer(this, 0, 118);
		this.foot_right.mirror = true;
		this.foot_right.setRotationPoint(0.0F, 3.6F, 0.0F);
		this.foot_right.addBox(-2.0F, 0.0F, -3.0F, 4, 1, 5, 0.0F);
		this.setRotateAngle(foot_right, 0.2617993877991494F, -0.22689280275926282F, -0.22689280275926282F);
		this.head = new ModelRenderer(this, 0, 34);
		this.head.setRotationPoint(0.0F, -8.5F, 1.5F);
		this.head.addBox(-4.0F, -4.5F, -4.0F, 8, 5, 6, 0.0F);
		this.setRotateAngle(head, 0.17453292519943295F, 0.0F, 0.0F);
		this.feather_right_2 = new ModelRenderer(this, 46, 0);
		this.feather_right_2.mirror = true;
		this.feather_right_2.setRotationPoint(-2.5F, 5.5F, 4.0F);
		this.feather_right_2.addBox(-4.0F, -12.0F, 0.0F, 4, 12, 1, 0.0F);
		this.setRotateAngle(feather_right_2, -0.6981317007977318F, -0.5235987755982988F, 0.0F);
		this.dart_left_top = new ModelRenderer(this, 46, 119);
		this.dart_left_top.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.dart_left_top.addBox(5.5F, -6.0F, -8.0F, 1, 1, 8, 0.0F);
		this.feather_left_2 = new ModelRenderer(this, 46, 0);
		this.feather_left_2.setRotationPoint(2.5F, 5.5F, 4.0F);
		this.feather_left_2.addBox(0.0F, -12.0F, 0.0F, 4, 12, 1, 0.0F);
		this.setRotateAngle(feather_left_2, -0.6981317007977318F, 0.5235987755982988F, 0.0F);
		this.foot_left.addChild(this.toe_left_2);
		this.body_base.addChild(this.dart_right_bottom);
		this.head.addChild(this.crest_left);
		this.head.addChild(this.beak_bottom);
		this.head.addChild(this.crest_right);
		this.arm_right.addChild(this.wing_right);
		this.ankle_left.addChild(this.foot_left);
		this.body_top.addChild(this.shoulder_right);
		this.head.addChild(this.beak_top);
		this.arm_left.addChild(this.wing_left);
		this.wing_left.addChild(this.thumb_left);
		this.wing_left.addChild(this.claw_left_2);
		this.body_back.addChild(this.feather_left_1);
		this.body_top.addChild(this.shoulder_left);
		this.leg_right.addChild(this.ankle_right);
		this.wing_left.addChild(this.claw_left_1);
		this.wing_right.addChild(this.claw_right_1);
		this.body_base.addChild(this.dart_left_middle);
		this.foot_left.addChild(this.toe_left_1);
		this.body_base.addChild(this.thigh_left);
		this.body_base.addChild(this.body_back);
		this.shoulder_left.addChild(this.arm_left);
		this.wing_left.addChild(this.wing_left_feathers);
		this.foot_right.addChild(this.toe_right_1);
		this.shoulder_right.addChild(this.arm_right);
		this.foot_left.addChild(this.toe_left_3);
		this.foot_right.addChild(this.toe_right_2);
		this.foot_right.addChild(this.toe_right_4);
		this.leg_left.addChild(this.ankle_left);
		this.body_base.addChild(this.dart_right_top);
		this.wing_right.addChild(this.thumb_right);
		this.wing_right.addChild(this.wing_right_feathers);
		this.beak_bottom.addChild(this.tongue);
		this.body_base.addChild(this.dart_left_bottom);
		this.foot_right.addChild(this.toe_right_3);
		this.thigh_left.addChild(this.leg_left);
		this.body_top.addChild(this.neck);
		this.body_back.addChild(this.feather_right_1);
		this.body_base.addChild(this.body_top);
		this.wing_right.addChild(this.claw_right_2);
		this.thigh_right.addChild(this.leg_right);
		this.body_base.addChild(this.thigh_right);
		this.foot_left.addChild(this.toe_left_4);
		this.body_base.addChild(this.dart_right_middle);
		this.ankle_right.addChild(this.foot_right);
		this.neck.addChild(this.head);
		this.body_back.addChild(this.feather_right_2);
		this.body_base.addChild(this.dart_left_top);
		this.body_back.addChild(this.feather_left_2);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.body_base.render(f5);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scaleFactor, Entity entity) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, scaleFactor, entity);

		float pitch = headPitch * 0.017453292F;
		float yaw = headYaw * 0.017453292F;

		this.head.rotateAngleX = 0.17453292519943295F + pitch;
		this.head.rotateAngleY = yaw;
		this.neck.rotateAngleY = yaw / 2f;

		float leftSwingX = MathHelper.cos(limbSwing * 1.2F) * 0.75F * limbSwingAmount;
		float rightSwingX = MathHelper.cos(limbSwing * 1.2F + (float) Math.PI) * 0.75F * limbSwingAmount;

		float leftSwingXLower = Math.max(-0.5009094953223726F, -rightSwingX * 2f) - 0.1F;
		float rightSwingXLower = Math.max(-0.5009094953223726F, -leftSwingX * 2f) - 0.1F;

		float leftToeCurlAngle = -rightSwingXLower;
		float rightToeCurlAngle = -leftSwingXLower;

		this.neck.rotateAngleX = leftSwingX * .5f - 0.261799f;
		this.head.rotateAngleX = -leftSwingX * .5f + 0.174533f;

		this.thigh_left.offsetY = leftSwingX * 0.05f;
		this.thigh_right.offsetY = rightSwingX * 0.05f;

		float leftOffsetY = leftSwingX * 0.25f - .05f;
		float rightOffsetY = rightSwingX * 0.25f - .05f;

		// The left and right are intentionally flipped to make it look like the darts are reacting to legs moving.

		this.dart_left_top.offsetY = rightOffsetY;
		this.dart_left_middle.offsetY = rightOffsetY;
		this.dart_left_bottom.offsetY = rightOffsetY;
		this.dart_right_top.offsetY = leftOffsetY;
		this.dart_right_middle.offsetY = leftOffsetY;
		this.dart_right_bottom.offsetY = leftOffsetY;

		float dartRotateRange = 0.08f;

		this.dart_left_top.rotateAngleX = rightSwingX * dartRotateRange;
		this.dart_left_middle.rotateAngleX =  rightSwingX * dartRotateRange;
		this.dart_left_bottom.rotateAngleX =  rightSwingX * dartRotateRange;
		this.dart_right_top.rotateAngleX = leftSwingX * dartRotateRange;
		this.dart_right_middle.rotateAngleX = leftSwingX * dartRotateRange;
		this.dart_right_bottom.rotateAngleX = leftSwingX * dartRotateRange;

		this.thigh_left.rotateAngleX = (leftSwingX * 1.2f) + 0.174533f;
		this.thigh_right.rotateAngleX = (rightSwingX * 1.2f) + 0.174533f;

		this.leg_left.rotateAngleX = (rightSwingXLower * 1.55F) + 0.523599F;
		this.leg_right.rotateAngleX = (leftSwingXLower * 1.55F) + 0.523599F;

		this.ankle_left.rotateAngleX = (rightSwingXLower * 0.75F) - 1.32645f;
		this.ankle_right.rotateAngleX = (leftSwingXLower * 0.75F) - 1.32645f;

		this.foot_left.rotateAngleX = -(rightSwingXLower * 3F) + 0.261799f;
		this.foot_right.rotateAngleX = -(leftSwingXLower * 3F) + 0.261799f;

		this.toe_left_1.rotateAngleX = leftToeCurlAngle - 0.698132F;
		this.toe_left_2.rotateAngleX = leftToeCurlAngle - 0.698132F;
		this.toe_left_3.rotateAngleX = leftToeCurlAngle - 0.698132F;

		this.toe_right_1.rotateAngleX = rightToeCurlAngle - 0.698132F;
		this.toe_right_2.rotateAngleX = rightToeCurlAngle - 0.698132F;
		this.toe_right_3.rotateAngleX = rightToeCurlAngle - 0.698132F;

		this.beak_bottom.rotateAngleX = Math.abs(MathHelper.cos( ageInTicks * 0.05662F)) * 0.1f;


		this.shoulder_left.rotateAngleX = leftSwingX * .1f;
		this.shoulder_right.rotateAngleX = rightSwingX * .1f;

		this.wing_left.rotateAngleX = leftSwingXLower * .1f;
		this.wing_right.rotateAngleX = rightSwingXLower * .1f;


		float wingSwayRange = 0.05f;

		float crestRotate = MathHelper.cos(ageInTicks * 0.15662F) * wingSwayRange;

		this.crest_left.rotateAngleX = crestRotate;
		this.crest_right.rotateAngleX = crestRotate;
		this.crest_left.rotateAngleY = -crestRotate;
		this.crest_right.rotateAngleY = -crestRotate;

		this.feather_left_1.rotateAngleZ = MathHelper.cos(ageInTicks * 0.15662F) * wingSwayRange;
		this.feather_left_2.rotateAngleZ = MathHelper.cos((10.0F + ageInTicks) * 0.15662F) * wingSwayRange;
		this.feather_right_1.rotateAngleZ = MathHelper.cos((20.0F + ageInTicks) * 0.15662F) * wingSwayRange;
		this.feather_right_2.rotateAngleZ = MathHelper.cos((30.0F + ageInTicks) * 0.15662F) * wingSwayRange;

		this.wing_left_feathers.rotateAngleZ = MathHelper.cos(ageInTicks * 0.15662F) * wingSwayRange;
		this.wing_right_feathers.rotateAngleZ = MathHelper.cos(ageInTicks * 0.15662F) * wingSwayRange;
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void preLayerRender() {
		this.body_back.isHidden = true;
		this.shoulder_left.isHidden = true;
		this.shoulder_right.isHidden = true;
		this.thigh_right.isHidden = true;
		this.thigh_left.isHidden = true;
		this.crest_right.isHidden = true;
		this.crest_left.isHidden = true;
		this.beak_bottom.isHidden = true;
		this.beak_top.isHidden = true;
	}

	@Override
	public void postLayerRender() {
		this.shoulder_left.isHidden = false;
		this.shoulder_right.isHidden = false;
		this.body_back.isHidden = false;
		this.body_top.isHidden = false;
		this.thigh_right.isHidden = false;
		this.thigh_left.isHidden = false;
		this.crest_right.isHidden = false;
		this.crest_left.isHidden = false;
		this.beak_bottom.isHidden = false;
		this.beak_top.isHidden = false;
	}
}
