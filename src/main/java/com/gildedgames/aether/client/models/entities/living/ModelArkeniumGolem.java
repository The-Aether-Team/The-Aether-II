package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.client.renderer.entities.living.layers.ILayeredModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelArkeniumGolem extends ModelBase implements ILayeredModel
{

	public ModelRenderer base_main;
	public ModelRenderer leg_right_1;
	public ModelRenderer leg_left_1;
	public ModelRenderer leg_back_1;
	public ModelRenderer torso_low;
	public ModelRenderer leg_right_2;
	public ModelRenderer leg_right_3;
	public ModelRenderer leg_left_2;
	public ModelRenderer leg_spike1;
	public ModelRenderer leg_spike2;
	public ModelRenderer leg_left_3;
	public ModelRenderer leg_back_2;
	public ModelRenderer leg_back_3;
	public ModelRenderer torso_mid;
	public ModelRenderer torso_top;
	public ModelRenderer shoulder_right;
	public ModelRenderer shoulder_left;
	public ModelRenderer torso_spike2;
	public ModelRenderer torso_spike1;
	public ModelRenderer torso_spike3;
	public ModelRenderer arm_right1;
	public ModelRenderer arm_right2;
	public ModelRenderer arm_right3;
	public ModelRenderer arm_right_spike1;
	public ModelRenderer arm_right_spike2;
	public ModelRenderer arm_left1;
	public ModelRenderer arm_left_spike1;
	public ModelRenderer arm_left_spike2;
	public ModelRenderer arm_left_spike3;

	public ModelArkeniumGolem() {
		this.textureWidth = 96;
		this.textureHeight = 128;
		this.leg_back_2 = new ModelRenderer(this, 40, 108);
		this.leg_back_2.setRotationPoint(6.0F, 0.0F, 0.0F);
		this.leg_back_2.addBox(-2.2F, -1.9F, -2.0F, 4, 7, 4, 0.0F);
		this.setRotateAngle(leg_back_2, 0.0F, 0.0F, -0.3490658503988659F);
		this.arm_left_spike2 = new ModelRenderer(this, 84, 41);
		this.arm_left_spike2.setRotationPoint(4.2F, -2.0F, 1.3F);
		this.arm_left_spike2.addBox(0.0F, -5.0F, -2.0F, 3, 5, 3, 0.0F);
		this.setRotateAngle(arm_left_spike2, -0.2617993877991494F, 0.0F, 0.0F);
		this.arm_right2 = new ModelRenderer(this, 6, 52);
		this.arm_right2.setRotationPoint(-7.0F, 5.0F, 0.0F);
		this.arm_right2.addBox(-0.4F, -0.5F, -3.5F, 5, 4, 6, 0.0F);
		this.setRotateAngle(arm_right2, 0.0F, 0.0F, 0.17453292519943295F);
		this.leg_left_2 = new ModelRenderer(this, 76, 108);
		this.leg_left_2.setRotationPoint(6.0F, 0.0F, 0.0F);
		this.leg_left_2.addBox(-2.2F, -1.9F, -2.0F, 4, 7, 4, 0.0F);
		this.setRotateAngle(leg_left_2, 0.0F, 0.0F, -0.3490658503988659F);
		this.arm_right3 = new ModelRenderer(this, 4, 75);
		this.arm_right3.setRotationPoint(-7.0F, 5.0F, 0.0F);
		this.arm_right3.addBox(-2.5F, 4.5F, -4.0F, 6, 10, 7, 0.0F);
		this.setRotateAngle(arm_right3, 0.0F, 0.0F, 0.08726646259971647F);
		this.leg_left_3 = new ModelRenderer(this, 78, 119);
		this.leg_left_3.setRotationPoint(0.0F, 4.5F, 0.0F);
		this.leg_left_3.addBox(-1.4F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
		this.setRotateAngle(leg_left_3, 0.0F, 0.0F, 0.3490658503988659F);
		this.torso_mid = new ModelRenderer(this, 24, 52);
		this.torso_mid.setRotationPoint(0.0F, 0.0F, 1.0F);
		this.torso_mid.addBox(-6.5F, -8.0F, -5.5F, 13, 4, 11, 0.0F);
		this.setRotateAngle(torso_mid, 0.17453292519943295F, 0.0F, 0.0F);
		this.leg_back_3 = new ModelRenderer(this, 42, 119);
		this.leg_back_3.setRotationPoint(0.0F, 4.5F, 0.0F);
		this.leg_back_3.addBox(-1.4F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
		this.setRotateAngle(leg_back_3, 0.0F, 0.0F, 0.3490658503988659F);
		this.torso_spike3 = new ModelRenderer(this, 54, 23);
		this.torso_spike3.setRotationPoint(7.0F, -14.0F, 5.0F);
		this.torso_spike3.addBox(0.0F, -4.0F, -2.0F, 3, 6, 3, 0.0F);
		this.setRotateAngle(torso_spike3, -0.5235987755982988F, 0.0F, 0.3490658503988659F);
		this.base_main = new ModelRenderer(this, 28, 82);
		this.base_main.setRotationPoint(0.0F, 2.5F, 0.0F);
		this.base_main.addBox(-5.0F, 8.0F, -5.0F, 10, 6, 10, 0.0F);
		this.shoulder_right = new ModelRenderer(this, 0, 29);
		this.shoulder_right.setRotationPoint(-8.0F, -12.5F, 0.0F);
		this.shoulder_right.addBox(-9.0F, -3.0F, -4.0F, 8, 7, 8, 0.0F);
		this.setRotateAngle(shoulder_right, 0.0F, 0.0F, -0.08726646259971647F);
		this.arm_right_spike1 = new ModelRenderer(this, 0, 44);
		this.arm_right_spike1.setRotationPoint(-7.4F, 6.0F, 0.0F);
		this.arm_right_spike1.addBox(-1.5F, -6.0F, -1.5F, 3, 6, 3, 0.0F);
		this.setRotateAngle(arm_right_spike1, -0.13962634015954636F, 0.0F, -0.6981317007977318F);
		this.arm_left1 = new ModelRenderer(this, 66, 63);
		this.arm_left1.setRotationPoint(6.0F, 0.0F, 0.0F);
		this.arm_left1.addBox(-3.0F, 0.0F, -3.5F, 7, 15, 7, 0.0F);
		this.setRotateAngle(arm_left1, 0.0F, 0.0F, -0.8726646259971648F);
		this.torso_spike2 = new ModelRenderer(this, 38, 21);
		this.torso_spike2.setRotationPoint(3.0F, -16.0F, 4.5F);
		this.torso_spike2.addBox(0.0F, -5.0F, -2.0F, 4, 7, 4, 0.0F);
		this.setRotateAngle(torso_spike2, -0.2617993877991494F, 0.0F, 0.3490658503988659F);
		this.leg_spike2 = new ModelRenderer(this, 64, 114);
		this.leg_spike2.setRotationPoint(5.0F, -2.0F, 1.0F);
		this.leg_spike2.addBox(0.0F, -5.0F, -2.0F, 3, 6, 3, 0.0F);
		this.setRotateAngle(leg_spike2, -0.17453292519943295F, 0.0F, 0.4363323129985824F);
		this.arm_left_spike1 = new ModelRenderer(this, 84, 32);
		this.arm_left_spike1.setRotationPoint(0.0F, -2.0F, -0.2F);
		this.arm_left_spike1.addBox(0.5F, -5.5F, -2.0F, 3, 6, 3, 0.0F);
		this.setRotateAngle(arm_left_spike1, 0.13962634015954636F, 0.0F, 0.0F);
		this.leg_right_3 = new ModelRenderer(this, 6, 119);
		this.leg_right_3.setRotationPoint(0.0F, 4.5F, 0.0F);
		this.leg_right_3.addBox(-1.4F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
		this.setRotateAngle(leg_right_3, 0.0F, 0.0F, 0.3490658503988659F);
		this.leg_right_1 = new ModelRenderer(this, 0, 98);
		this.leg_right_1.setRotationPoint(-2.0F, 11.5F, -2.0F);
		this.leg_right_1.addBox(0.0F, -3.0F, -2.5F, 7, 5, 5, 0.0F);
		this.setRotateAngle(leg_right_1, 0.0F, 2.6179938779914944F, 0.0F);
		this.shoulder_left = new ModelRenderer(this, 67, 55);
		this.shoulder_left.setRotationPoint(8.0F, -12.5F, 0.0F);
		this.shoulder_left.addBox(-1.0F, -2.5F, -2.0F, 9, 4, 4, 0.0F);
		this.setRotateAngle(shoulder_left, 0.0F, 0.0F, 0.7853981633974483F);
		this.torso_spike1 = new ModelRenderer(this, 30, 27);
		this.torso_spike1.setRotationPoint(0.2F, -16.0F, 2.3F);
		this.torso_spike1.addBox(0.0F, -2.0F, -1.0F, 2, 3, 2, 0.0F);
		this.setRotateAngle(torso_spike1, -0.17453292519943295F, 0.0F, 0.3490658503988659F);
		this.leg_right_2 = new ModelRenderer(this, 4, 108);
		this.leg_right_2.setRotationPoint(6.0F, 0.0F, 0.0F);
		this.leg_right_2.addBox(-2.2F, -1.9F, -2.0F, 4, 7, 4, 0.0F);
		this.setRotateAngle(leg_right_2, 0.0F, 0.0F, -0.3490658503988659F);
		this.torso_top = new ModelRenderer(this, 20, 32);
		this.torso_top.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.torso_top.addBox(-9.0F, -16.0F, -6.0F, 18, 8, 12, 0.0F);
		this.arm_right_spike2 = new ModelRenderer(this, 0, 53);
		this.arm_right_spike2.setRotationPoint(-7.4F, 7.4F, -1.0F);
		this.arm_right_spike2.addBox(-2.4F, -2.9F, -1.5F, 2, 3, 2, 0.0F);
		this.setRotateAngle(arm_right_spike2, 0.0F, 0.0F, -0.6981317007977318F);
		this.leg_spike1 = new ModelRenderer(this, 66, 108);
		this.leg_spike1.setRotationPoint(3.0F, -2.0F, -0.5F);
		this.leg_spike1.addBox(0.0F, -5.0F, -2.0F, 2, 4, 2, 0.0F);
		this.setRotateAngle(leg_spike1, 0.13962634015954636F, 0.0F, 0.4363323129985824F);
		this.arm_left_spike3 = new ModelRenderer(this, 88, 49);
		this.arm_left_spike3.setRotationPoint(3.5F, 1.0F, 1.0F);
		this.arm_left_spike3.addBox(0.0F, -2.4F, -2.0F, 2, 4, 2, 0.0F);
		this.setRotateAngle(arm_left_spike3, -0.08726646259971647F, 0.0F, 0.7853981633974483F);
		this.leg_left_1 = new ModelRenderer(this, 72, 98);
		this.leg_left_1.setRotationPoint(2.0F, 11.5F, -2.0F);
		this.leg_left_1.addBox(0.0F, -3.0F, -2.5F, 7, 5, 5, 0.0F);
		this.setRotateAngle(leg_left_1, 0.0F, 0.5235987755982988F, 0.0F);
		this.arm_right1 = new ModelRenderer(this, 7, 62);
		this.arm_right1.setRotationPoint(-5.0F, 3.0F, 0.0F);
		this.arm_right1.addBox(-2.5F, -0.5F, -3.0F, 5, 8, 5, 0.0F);
		this.setRotateAngle(arm_right1, 0.0F, 0.0F, 0.2617993877991494F);
		this.torso_low = new ModelRenderer(this, 30, 67);
		this.torso_low.setRotationPoint(0.0F, 8.0F, 0.0F);
		this.torso_low.addBox(-4.5F, -5.0F, -4.5F, 9, 6, 9, 0.0F);
		this.setRotateAngle(torso_low, -0.17453292519943295F, 0.0F, 0.0F);
		this.leg_back_1 = new ModelRenderer(this, 36, 98);
		this.leg_back_1.setRotationPoint(0.0F, 11.6F, 2.0F);
		this.leg_back_1.addBox(0.0F, -3.0F, -2.5F, 7, 5, 5, 0.0F);
		this.setRotateAngle(leg_back_1, 0.0F, -1.5707963267948966F, 0.0F);
		this.leg_back_1.addChild(this.leg_back_2);
		this.shoulder_left.addChild(this.arm_left_spike2);
		this.shoulder_right.addChild(this.arm_right2);
		this.leg_left_1.addChild(this.leg_left_2);
		this.shoulder_right.addChild(this.arm_right3);
		this.leg_left_2.addChild(this.leg_left_3);
		this.torso_low.addChild(this.torso_mid);
		this.leg_back_2.addChild(this.leg_back_3);
		this.torso_top.addChild(this.torso_spike3);
		this.torso_top.addChild(this.shoulder_right);
		this.shoulder_right.addChild(this.arm_right_spike1);
		this.shoulder_left.addChild(this.arm_left1);
		this.torso_top.addChild(this.torso_spike2);
		this.leg_left_1.addChild(this.leg_spike2);
		this.shoulder_left.addChild(this.arm_left_spike1);
		this.leg_right_2.addChild(this.leg_right_3);
		this.base_main.addChild(this.leg_right_1);
		this.torso_top.addChild(this.shoulder_left);
		this.torso_top.addChild(this.torso_spike1);
		this.leg_right_1.addChild(this.leg_right_2);
		this.torso_mid.addChild(this.torso_top);
		this.shoulder_right.addChild(this.arm_right_spike2);
		this.leg_left_1.addChild(this.leg_spike1);
		this.arm_left1.addChild(this.arm_left_spike3);
		this.base_main.addChild(this.leg_left_1);
		this.shoulder_right.addChild(this.arm_right1);
		this.base_main.addChild(this.torso_low);
		this.base_main.addChild(this.leg_back_1);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.base_main.render(f5);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scaleFactor, Entity entity) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, scaleFactor, entity);

		float leftSwingX = MathHelper.cos(limbSwing * 1.2F) * 0.75F * limbSwingAmount;
		float rightSwingX = MathHelper.cos(limbSwing * 1.2F) * 0.75F * limbSwingAmount;

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

	}

	@Override
	public void postLayerRender() {

	}
}
