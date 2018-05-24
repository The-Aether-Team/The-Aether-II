package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * Josediya - Undefined
 * Created using Tabula 6.0.0
 */
public class ModelJosediya extends ModelBase {
	public ModelRenderer torso1;
	public ModelRenderer torso4;
	public ModelRenderer leg_left_upper;
	public ModelRenderer leg_right_upper;
	public ModelRenderer arm_left_upper;
	public ModelRenderer arm_right_upper;
	public ModelRenderer wing_left1;
	public ModelRenderer wing_right1;
	public ModelRenderer torso2;
	public ModelRenderer neck;
	public ModelRenderer torso3;
	public ModelRenderer leg_left_lower1;
	public ModelRenderer leg_left_lower2;
	public ModelRenderer leg_left_lower3;
	public ModelRenderer leg_right_lower1;
	public ModelRenderer leg_right_lower2;
	public ModelRenderer leg_right_lower3;
	public ModelRenderer arm_left_lower1;
	public ModelRenderer arm_left_lower2;
	public ModelRenderer arm_right_lower1;
	public ModelRenderer arm_right_lower2;
	public ModelRenderer wing_left2;
	public ModelRenderer wing_right2;
	public ModelRenderer head1;
	public ModelRenderer head2;
	public ModelRenderer head3;
	public ModelRenderer head4;
	public ModelRenderer head5;
	public ModelRenderer head6;
	public ModelRenderer head7;
	public ModelRenderer head8;
	public ModelRenderer hair;
	public ModelRenderer head9;

	public ModelJosediya() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.leg_right_lower1 = new ModelRenderer(this, 32, 41);
		this.leg_right_lower1.setRotationPoint(0.0F, 7.0F, -2.0F);
		this.leg_right_lower1.addBox(-2.01F, 0.0F, 0.0F, 4, 6, 4, 0.0F);
		this.arm_left_upper = new ModelRenderer(this, 14, 44);
		this.arm_left_upper.setRotationPoint(4.0F, -3.5F, 0.0F);
		this.arm_left_upper.addBox(0.0F, -2.0F, -2.0F, 3, 6, 4, 0.0F);
		this.leg_right_upper = new ModelRenderer(this, 32, 31);
		this.leg_right_upper.setRotationPoint(-2.0F, 5.0F, 0.0F);
		this.leg_right_upper.addBox(-2.0F, 1.0F, -2.0F, 4, 6, 4, 0.0F);
		this.leg_right_lower2 = new ModelRenderer(this, 36, 18);
		this.leg_right_lower2.setRotationPoint(0.0F, 4.0F, 2.1F);
		this.leg_right_lower2.addBox(-1.5F, -4.0F, 0.0F, 3, 4, 2, 0.0F);
		this.setRotateAngle(leg_right_lower2, -0.3490658503988659F, 0.0F, 0.0F);
		this.arm_right_lower1 = new ModelRenderer(this, 0, 54);
		this.arm_right_lower1.setRotationPoint(-1.5F, 4.0F, 1.0F);
		this.arm_right_lower1.addBox(-1.51F, 0.0F, -3.0F, 3, 6, 4, 0.0F);
		this.leg_right_lower3 = new ModelRenderer(this, 64, 45);
		this.leg_right_lower3.setRotationPoint(0.0F, 5.0F, 0.0F);
		this.leg_right_lower3.addBox(-1.5F, -0.01F, -0.7F, 3, 1, 1, 0.0F);
		this.head4 = new ModelRenderer(this, 24, 0);
		this.head4.setRotationPoint(4.0F, -4.0F, -1.0F);
		this.head4.addBox(-1.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(head4, 0.0F, 0.3490658503988659F, 0.0F);
		this.arm_right_upper = new ModelRenderer(this, 0, 44);
		this.arm_right_upper.setRotationPoint(-4.0F, -3.5F, 0.0F);
		this.arm_right_upper.addBox(-3.0F, -2.0F, -2.0F, 3, 6, 4, 0.0F);
		this.head9 = new ModelRenderer(this, 0, 0);
		this.head9.setRotationPoint(0.0F, -0.99F, -4.02F);
		this.head9.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.head3 = new ModelRenderer(this, 0, 2);
		this.head3.setRotationPoint(0.0F, -4.3F, -3.9F);
		this.head3.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(head3, -0.17453292519943295F, 0.0F, 0.0F);
		this.head7 = new ModelRenderer(this, 12, 15);
		this.head7.setRotationPoint(3.0F, -1.0F, 3.0F);
		this.head7.addBox(-1.0F, -1.0F, -1.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(head7, 0.3490658503988659F, 0.7853981633974483F, 0.0F);
		this.neck = new ModelRenderer(this, 0, 21);
		this.neck.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.neck.addBox(-1.5F, -3.0F, -1.5F, 3, 4, 3, 0.0F);
		this.head8 = new ModelRenderer(this, 12, 15);
		this.head8.mirror = true;
		this.head8.setRotationPoint(-3.0F, -1.0F, 3.0F);
		this.head8.addBox(-1.0F, -1.0F, -1.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(head8, 0.3490658503988659F, -0.7853981633974483F, 0.0F);
		this.torso4 = new ModelRenderer(this, 36, 24);
		this.torso4.setRotationPoint(0.0F, 10.0F, 0.0F);
		this.torso4.addBox(-4.0F, 0.0F, -2.0F, 8, 3, 4, 0.1F);
		this.arm_left_lower2 = new ModelRenderer(this, 24, 38);
		this.arm_left_lower2.setRotationPoint(0.0F, 2.2F, 1.0F);
		this.arm_left_lower2.addBox(-1.0F, -3.0F, -2.0F, 2, 3, 2, 0.0F);
		this.setRotateAngle(arm_left_lower2, -0.3490658503988659F, 0.0F, 0.0F);
		this.head1 = new ModelRenderer(this, 0, 0);
		this.head1.setRotationPoint(0.0F, -0.8F, 0.0F);
		this.head1.addBox(-4.0F, -8.0F, -4.0F, 8, 7, 8, 0.0F);
		this.torso1 = new ModelRenderer(this, 0, 28);
		this.torso1.setRotationPoint(0.0F, 6.0F, 0.0F);
		this.torso1.addBox(-4.0F, -6.0F, -2.0F, 8, 12, 4, 0.0F);
		this.hair = new ModelRenderer(this, 42, 0);
		this.hair.setRotationPoint(0.0F, -0.01F, -0.01F);
		this.hair.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.2F);
		this.leg_left_lower2 = new ModelRenderer(this, 50, 18);
		this.leg_left_lower2.setRotationPoint(0.0F, 4.0F, 2.1F);
		this.leg_left_lower2.addBox(-1.5F, -4.0F, 0.0F, 3, 4, 2, 0.0F);
		this.setRotateAngle(leg_left_lower2, -0.3490658503988659F, 0.0F, 0.0F);
		this.arm_left_lower1 = new ModelRenderer(this, 14, 54);
		this.arm_left_lower1.setRotationPoint(1.5F, 4.0F, 1.0F);
		this.arm_left_lower1.addBox(-1.49F, 0.0F, -3.0F, 3, 6, 4, 0.0F);
		this.head6 = new ModelRenderer(this, 0, 15);
		this.head6.setRotationPoint(0.0F, -1.0F, 4.0F);
		this.head6.addBox(-2.5F, -1.0F, -1.0F, 5, 3, 1, 0.0F);
		this.setRotateAngle(head6, 0.2617993877991494F, 0.0F, 0.0F);
		this.wing_right2 = new ModelRenderer(this, 82, 41);
		this.wing_right2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.wing_right2.addBox(-1.0F, -1.0F, -1.0F, 1, 2, 8, 0.0F);
		this.torso3 = new ModelRenderer(this, 60, 17);
		this.torso3.setRotationPoint(0.0F, -4.0F, -2.0F);
		this.torso3.addBox(-4.0F, 0.0F, 0.0F, 8, 4, 2, 0.0F);
		this.setRotateAngle(torso3, -0.2617993877991494F, 0.0F, 0.0F);
		this.arm_right_lower2 = new ModelRenderer(this, 24, 43);
		this.arm_right_lower2.setRotationPoint(0.0F, 2.2F, 1.0F);
		this.arm_right_lower2.addBox(-1.0F, -3.0F, -2.0F, 2, 3, 2, 0.0F);
		this.setRotateAngle(arm_right_lower2, -0.3490658503988659F, 0.0F, 0.0F);
		this.wing_left2 = new ModelRenderer(this, 64, 41);
		this.wing_left2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.wing_left2.addBox(0.0F, -1.0F, -1.0F, 1, 2, 8, 0.0F);
		this.torso2 = new ModelRenderer(this, 12, 21);
		this.torso2.setRotationPoint(0.01F, -5.4F, 1.8F);
		this.torso2.addBox(-3.0F, -2.0F, -4.0F, 6, 2, 5, 0.0F);
		this.setRotateAngle(torso2, 0.3490658503988659F, 0.0F, 0.0F);
		this.wing_right1 = new ModelRenderer(this, 50, 51);
		this.wing_right1.setRotationPoint(-1.9F, 0.0F, 2.0F);
		this.wing_right1.addBox(-1.0F, -4.0F, 0.0F, 1, 3, 10, 0.0F);
		this.setRotateAngle(wing_right1, 0.2617993877991494F, -0.3490658503988659F, 0.0F);
		this.head5 = new ModelRenderer(this, 24, 0);
		this.head5.mirror = true;
		this.head5.setRotationPoint(-4.0F, -4.0F, -1.0F);
		this.head5.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
		this.setRotateAngle(head5, 0.0F, -0.3490658503988659F, 0.0F);
		this.leg_left_lower3 = new ModelRenderer(this, 64, 47);
		this.leg_left_lower3.mirror = true;
		this.leg_left_lower3.setRotationPoint(0.0F, 5.0F, 0.0F);
		this.leg_left_lower3.addBox(-1.5F, -0.01F, -0.7F, 3, 1, 1, 0.0F);
		this.leg_left_lower1 = new ModelRenderer(this, 48, 41);
		this.leg_left_lower1.setRotationPoint(0.0F, 7.0F, -2.0F);
		this.leg_left_lower1.addBox(-1.99F, 0.0F, 0.0F, 4, 6, 4, 0.0F);
		this.leg_left_upper = new ModelRenderer(this, 48, 31);
		this.leg_left_upper.setRotationPoint(2.0F, 5.0F, 0.0F);
		this.leg_left_upper.addBox(-2.0F, 1.0F, -2.0F, 4, 6, 4, 0.0F);
		this.head2 = new ModelRenderer(this, 24, 0);
		this.head2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.head2.addBox(-3.0F, -1.0F, -4.0F, 6, 1, 7, 0.0F);
		this.wing_left1 = new ModelRenderer(this, 28, 51);
		this.wing_left1.setRotationPoint(1.9F, 0.0F, 2.0F);
		this.wing_left1.addBox(0.0F, -4.0F, 0.0F, 1, 3, 10, 0.0F);
		this.setRotateAngle(wing_left1, 0.2617993877991494F, 0.3490658503988659F, 0.0F);
		this.leg_right_upper.addChild(this.leg_right_lower1);
		this.torso1.addChild(this.arm_left_upper);
		this.torso1.addChild(this.leg_right_upper);
		this.leg_right_lower1.addChild(this.leg_right_lower2);
		this.arm_right_upper.addChild(this.arm_right_lower1);
		this.leg_right_lower1.addChild(this.leg_right_lower3);
		this.head1.addChild(this.head4);
		this.torso1.addChild(this.arm_right_upper);
		this.head1.addChild(this.head9);
		this.head1.addChild(this.head3);
		this.head1.addChild(this.head7);
		this.torso1.addChild(this.neck);
		this.head1.addChild(this.head8);
		this.arm_left_lower1.addChild(this.arm_left_lower2);
		this.neck.addChild(this.head1);
		this.head1.addChild(this.hair);
		this.leg_left_lower1.addChild(this.leg_left_lower2);
		this.arm_left_upper.addChild(this.arm_left_lower1);
		this.head1.addChild(this.head6);
		this.wing_right1.addChild(this.wing_right2);
		this.torso1.addChild(this.torso3);
		this.arm_right_lower1.addChild(this.arm_right_lower2);
		this.wing_left1.addChild(this.wing_left2);
		this.torso1.addChild(this.torso2);
		this.torso1.addChild(this.wing_right1);
		this.head1.addChild(this.head5);
		this.leg_left_lower1.addChild(this.leg_left_lower3);
		this.leg_left_upper.addChild(this.leg_left_lower1);
		this.torso1.addChild(this.leg_left_upper);
		this.head1.addChild(this.head2);
		this.torso1.addChild(this.wing_left1);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.torso4.render(f5);
		this.torso1.render(f5);
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
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scaleFactor, Entity entity)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, scaleFactor, entity);

		this.head1.rotateAngleY = headYaw * 0.017453292F;
		this.head1.rotateAngleX = headPitch * 0.0057453292F;

		this.arm_right_upper.rotationPointZ = 0.0F;
		this.arm_right_upper.rotationPointX = -4.0F;
		this.arm_left_upper.rotationPointZ = 0.0F;
		this.arm_left_upper.rotationPointX = 4.0F;

		this.arm_right_upper.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		this.arm_left_upper.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.arm_right_upper.rotateAngleZ = 0.0F;
		this.arm_left_upper.rotateAngleZ = 0.0F;

		this.arm_right_upper.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.arm_left_upper.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.arm_right_upper.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.arm_left_upper.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
	}
}
