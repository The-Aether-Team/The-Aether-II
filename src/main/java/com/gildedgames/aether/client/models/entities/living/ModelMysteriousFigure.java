package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * Mystery Man - Undefined
 * Created using Tabula 7.0.0
 */
public class ModelMysteriousFigure extends ModelBase
{
	public final ModelRenderer chest1;

	public final ModelRenderer chest2;

	public final ModelRenderer head1;

	public final ModelRenderer shoulder_right;

	public final ModelRenderer plumproot;

	public final ModelRenderer shoulder_left;

	public final ModelRenderer waist;

	public final ModelRenderer leg_right;

	public final ModelRenderer leg_left;

	public final ModelRenderer boot_right1;

	public final ModelRenderer boot_right2;

	public final ModelRenderer boot_right3;

	public final ModelRenderer boot_left1;

	public final ModelRenderer boot_left2;

	public final ModelRenderer boot_left3;

	public final ModelRenderer head2;

	public final ModelRenderer arm_right1;

	public final ModelRenderer arm_right2;

	public final ModelRenderer arm_right3;

	public final ModelRenderer sack;

	public final ModelRenderer sack_handle;

	public final ModelRenderer arm_left1;

	public final ModelRenderer arm_left2;

	public final ModelRenderer arm_left3;

	public ModelMysteriousFigure()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.shoulder_left = new ModelRenderer(this, 27, 11);
		this.shoulder_left.mirror = true;
		this.shoulder_left.setRotationPoint(4.0F, -1.5F, 0.0F);
		this.shoulder_left.addBox(-0.5F, -2.0F, -2.5F, 6, 4, 5, 0.0F);
		this.setRotateAngle(this.shoulder_left, 0.0F, 0.0F, 0.17453292519943295F);
		this.boot_left2 = new ModelRenderer(this, 44, 14);
		this.boot_left2.mirror = true;
		this.boot_left2.setRotationPoint(0.0F, 2.0F, 0.5F);
		this.boot_left2.addBox(-1.5F, 0.0F, -6.0F, 3, 2, 7, 0.0F);
		this.leg_left = new ModelRenderer(this, 0, 42);
		this.leg_left.mirror = true;
		this.leg_left.setRotationPoint(3.0F, 6.0F, 1.0F);
		this.leg_left.addBox(-1.5F, -1.5F, -1.5F, 3, 12, 3, 0.0F);
		this.boot_left1 = new ModelRenderer(this, 24, 0);
		this.boot_left1.mirror = true;
		this.boot_left1.setRotationPoint(0.0F, 10.0F, 0.0F);
		this.boot_left1.addBox(-2.0F, 0.0F, -2.0F, 4, 2, 4, 0.0F);
		this.head2 = new ModelRenderer(this, 0, 6);
		this.head2.setRotationPoint(0.0F, -8.0F, 4.0F);
		this.head2.addBox(-1.5F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
		this.setRotateAngle(this.head2, -0.6981317007977318F, 0.0F, 0.0F);
		this.arm_left1 = new ModelRenderer(this, 34, 23);
		this.arm_left1.mirror = true;
		this.arm_left1.setRotationPoint(2.5F, 2.5F, 0.0F);
		this.arm_left1.addBox(-0.5F, -1.5F, -1.5F, 3, 6, 3, 0.0F);
		this.setRotateAngle(this.arm_left1, -0.31869712141416456F, 0.17453292519943295F, -0.5235987755982988F);
		this.chest2 = new ModelRenderer(this, 36, 0);
		this.chest2.setRotationPoint(0.0F, 1.5F, 3.0F);
		this.chest2.addBox(-4.0F, 0.0F, -6.0F, 8, 5, 6, 0.0F);
		this.setRotateAngle(this.chest2, -0.5235987755982988F, 0.0F, 0.0F);
		this.boot_left3 = new ModelRenderer(this, 57, 18);
		this.boot_left3.mirror = true;
		this.boot_left3.setRotationPoint(0.0F, 0.0F, -6.0F);
		this.boot_left3.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(this.boot_left3, -0.4363323129985824F, 0.0F, 0.0F);
		this.plumproot = new ModelRenderer(this, 12, 44);
		this.plumproot.setRotationPoint(2.0F, 0.5F, 2.0F);
		this.plumproot.addBox(-5.0F, -3.0F, 0.0F, 10, 10, 10, 0.0F);
		this.setRotateAngle(this.plumproot, 0.08726646259971647F, 0.4363323129985824F, 0.0F);
		this.boot_right1 = new ModelRenderer(this, 24, 0);
		this.boot_right1.setRotationPoint(0.0F, 10.0F, 0.0F);
		this.boot_right1.addBox(-2.0F, 0.0F, -2.0F, 4, 2, 4, 0.0F);
		this.sack_handle = new ModelRenderer(this, 0, 0);
		this.sack_handle.setRotationPoint(0.0F, 3.0F, -1.0F);
		this.sack_handle.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(this.sack_handle, 1.3089969389957472F, 0.8726646259971648F, 0.0F);
		this.chest1 = new ModelRenderer(this, 0, 16);
		this.chest1.setRotationPoint(0.0F, -0.8F, 0.0F);
		this.chest1.addBox(-5.0F, -4.0F, -4.0F, 10, 6, 7, 0.0F);
		this.setRotateAngle(this.chest1, 0.5235987755982988F, 0.0F, 0.0F);
		this.waist = new ModelRenderer(this, 0, 29);
		this.waist.setRotationPoint(0.0F, 5.0F, -3.0F);
		this.waist.addBox(-6.0F, 0.0F, -3.5F, 12, 6, 7, 0.0F);
		this.arm_right3 = new ModelRenderer(this, 38, 35);
		this.arm_right3.setRotationPoint(0.0F, 5.5F, -1.5F);
		this.arm_right3.addBox(-2.0F, 0.0F, -2.0F, 4, 2, 4, 0.0F);
		this.arm_left2 = new ModelRenderer(this, 46, 23);
		this.arm_left2.mirror = true;
		this.arm_left2.setRotationPoint(1.0F, 4.5F, 1.5F);
		this.arm_left2.addBox(-1.49F, 0.0F, -3.0F, 3, 9, 3, 0.0F);
		this.setRotateAngle(this.arm_left2, -1.5707963267948966F, 0.0F, 0.0F);
		this.boot_right2 = new ModelRenderer(this, 44, 14);
		this.boot_right2.setRotationPoint(0.0F, 2.0F, 0.5F);
		this.boot_right2.addBox(-1.5F, 0.0F, -6.0F, 3, 2, 7, 0.0F);
		this.sack = new ModelRenderer(this, 44, 43);
		this.sack.setRotationPoint(0.0F, 8.4F, -1.4F);
		this.sack.addBox(-2.5F, 0.0F, -2.5F, 5, 6, 5, 0.0F);
		this.setRotateAngle(this.sack, 0.3490658503988659F, 0.0F, -0.17453292519943295F);
		this.boot_right3 = new ModelRenderer(this, 57, 18);
		this.boot_right3.setRotationPoint(0.0F, 0.0F, -6.0F);
		this.boot_right3.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(this.boot_right3, -0.4363323129985824F, 0.0F, 0.0F);
		this.arm_left3 = new ModelRenderer(this, 38, 35);
		this.arm_left3.mirror = true;
		this.arm_left3.setRotationPoint(0.0F, 5.5F, -1.5F);
		this.arm_left3.addBox(-2.0F, 0.0F, -2.0F, 4, 2, 4, 0.0F);
		this.leg_right = new ModelRenderer(this, 0, 42);
		this.leg_right.setRotationPoint(-3.0F, 6.0F, 1.0F);
		this.leg_right.addBox(-1.5F, -1.5F, -1.5F, 3, 12, 3, 0.0F);
		this.shoulder_right = new ModelRenderer(this, 27, 11);
		this.shoulder_right.setRotationPoint(-4.0F, -1.5F, 0.0F);
		this.shoulder_right.addBox(-5.5F, -2.0F, -2.5F, 6, 4, 5, 0.0F);
		this.setRotateAngle(this.shoulder_right, 0.0F, 0.0F, -0.17453292519943295F);
		this.arm_right1 = new ModelRenderer(this, 34, 23);
		this.arm_right1.setRotationPoint(-2.5F, 2.5F, 0.0F);
		this.arm_right1.addBox(-2.5F, -1.5F, -1.5F, 3, 6, 3, 0.0F);
		this.setRotateAngle(this.arm_right1, -0.5235987755982988F, 0.0F, 0.3490658503988659F);
		this.head1 = new ModelRenderer(this, 0, 0);
		this.head1.setRotationPoint(0.0F, -4.0F, -3.2F);
		this.head1.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.setRotateAngle(this.head1, -0.5235987755982988F, 0.08726646259971647F, 0.17453292519943295F);
		this.arm_right2 = new ModelRenderer(this, 46, 23);
		this.arm_right2.setRotationPoint(-1.0F, 4.5F, 1.5F);
		this.arm_right2.addBox(-1.51F, 0.0F, -3.0F, 3, 9, 3, 0.0F);
		this.setRotateAngle(this.arm_right2, -0.3490658503988659F, 0.0F, 0.0F);
		this.chest1.addChild(this.shoulder_left);
		this.boot_left1.addChild(this.boot_left2);
		this.waist.addChild(this.leg_left);
		this.leg_left.addChild(this.boot_left1);
		this.head1.addChild(this.head2);
		this.shoulder_left.addChild(this.arm_left1);
		this.chest1.addChild(this.chest2);
		this.boot_left2.addChild(this.boot_left3);
		this.chest1.addChild(this.plumproot);
		this.leg_right.addChild(this.boot_right1);
		this.arm_right3.addChild(this.sack_handle);
		this.chest2.addChild(this.waist);
		this.arm_right2.addChild(this.arm_right3);
		this.arm_left1.addChild(this.arm_left2);
		this.boot_right1.addChild(this.boot_right2);
		this.arm_right2.addChild(this.sack);
		this.boot_right2.addChild(this.boot_right3);
		this.arm_left2.addChild(this.arm_left3);
		this.waist.addChild(this.leg_right);
		this.chest1.addChild(this.shoulder_right);
		this.shoulder_right.addChild(this.arm_right1);
		this.chest1.addChild(this.head1);
		this.arm_right1.addChild(this.arm_right2);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.chest1.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
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

		this.shoulder_right.rotationPointZ = 0.0F;
		this.shoulder_right.rotationPointX = -4.0F;
		this.shoulder_left.rotationPointZ = 0.0F;
		this.shoulder_left.rotationPointX = 4.0F;

		this.shoulder_right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		this.shoulder_left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.shoulder_right.rotateAngleZ = 0.0F;
		this.shoulder_left.rotateAngleZ = 0.0F;

		this.shoulder_right.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.shoulder_left.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.shoulder_right.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.shoulder_left.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
	}
}
