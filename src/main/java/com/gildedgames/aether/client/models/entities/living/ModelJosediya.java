package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * Josediya - Undefined
 * Created using Tabula 7.0.0
 */
public class ModelJosediya extends ModelBase
{
	public ModelRenderer shape2;

	public ModelRenderer shape6;

	public ModelRenderer shape3;

	public ModelRenderer shape3_1;

	public ModelRenderer rightArm;

	public ModelRenderer leftArm;

	public ModelRenderer shape17;

	public ModelRenderer shape17_1;

	public ModelRenderer shape19;

	public ModelRenderer shape20;

	public ModelRenderer shape4;

	public ModelRenderer shape9;

	public ModelRenderer shape4_1;

	public ModelRenderer shape9_1;

	public ModelRenderer shape12;

	public ModelRenderer shape13;

	public ModelRenderer shape12_1;

	public ModelRenderer shape13_1;

	public ModelRenderer head;

	public ModelRenderer shape22;

	public ModelJosediya()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.shape6 = new ModelRenderer(this, 36, 24);
		this.shape6.setRotationPoint(0.0F, 10.0F, 0.0F);
		this.shape6.addBox(-4.0F, 0.0F, -2.0F, 8, 3, 4, 0.3F);
		this.shape20 = new ModelRenderer(this, 0, 21);
		this.shape20.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.shape20.addBox(-1.5F, -3.0F, -1.5F, 3, 4, 3, 0.0F);
		this.shape4 = new ModelRenderer(this, 48, 41);
		this.shape4.setRotationPoint(0.0F, 7.0F, -2.0F);
		this.shape4.addBox(-1.9F, 0.0F, 0.0F, 4, 6, 4, 0.0F);
		this.shape4_1 = new ModelRenderer(this, 32, 41);
		this.shape4_1.setRotationPoint(0.0F, 7.0F, -2.0F);
		this.shape4_1.addBox(-2.1F, 0.0F, 0.0F, 4, 6, 4, 0.0F);
		this.shape12 = new ModelRenderer(this, 14, 54);
		this.shape12.setRotationPoint(1.5F, 4.0F, 1.0F);
		this.shape12.addBox(-1.4F, 0.0F, -3.0F, 3, 6, 4, 0.0F);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.setRotationPoint(0.0F, -0.8F, 0.0F);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.shape17 = new ModelRenderer(this, 28, 51);
		this.shape17.setRotationPoint(2.0F, 0.0F, 2.0F);
		this.shape17.addBox(0.0F, -5.0F, 0.0F, 1, 5, 8, 0.0F);
		this.setRotateAngle(this.shape17, 0.2617993877991494F, 0.3490658503988659F, 0.0F);
		this.leftArm = new ModelRenderer(this, 0, 44);
		this.leftArm.setRotationPoint(-4.0F, -3.5F, 0.0F);
		this.leftArm.addBox(-3.0F, -2.0F, -2.0F, 3, 6, 4, 0.0F);
		this.rightArm = new ModelRenderer(this, 14, 44);
		this.rightArm.setRotationPoint(4.0F, -3.5F, 0.0F);
		this.rightArm.addBox(0.0F, -2.0F, -2.0F, 3, 6, 4, 0.0F);
		this.shape13_1 = new ModelRenderer(this, 24, 43);
		this.shape13_1.setRotationPoint(0.0F, 2.2F, 1.0F);
		this.shape13_1.addBox(-1.0F, -3.0F, -2.0F, 2, 3, 2, 0.0F);
		this.setRotateAngle(this.shape13_1, -0.3490658503988659F, 0.0F, 0.0F);
		this.shape9_1 = new ModelRenderer(this, 36, 18);
		this.shape9_1.setRotationPoint(0.0F, 4.0F, 2.1F);
		this.shape9_1.addBox(-1.5F, -4.0F, 0.0F, 3, 4, 2, 0.0F);
		this.setRotateAngle(this.shape9_1, -0.3490658503988659F, 0.0F, 0.0F);
		this.shape19 = new ModelRenderer(this, 12, 21);
		this.shape19.setRotationPoint(0.0F, -5.4F, 1.8F);
		this.shape19.addBox(-3.0F, -2.0F, -4.0F, 6, 2, 5, 0.0F);
		this.setRotateAngle(this.shape19, 0.3490658503988659F, 0.0F, 0.0F);
		this.shape17_1 = new ModelRenderer(this, 46, 51);
		this.shape17_1.setRotationPoint(-2.0F, 0.5F, 2.0F);
		this.shape17_1.addBox(-1.0F, -5.0F, 0.0F, 1, 5, 8, 0.0F);
		this.setRotateAngle(this.shape17_1, 0.2617993877991494F, -0.3490658503988659F, 0.0F);
		this.shape3_1 = new ModelRenderer(this, 32, 31);
		this.shape3_1.setRotationPoint(-2.0F, 5.0F, 0.0F);
		this.shape3_1.addBox(-2.0F, 1.0F, -2.0F, 4, 6, 4, 0.0F);
		this.shape12_1 = new ModelRenderer(this, 0, 54);
		this.shape12_1.setRotationPoint(-1.5F, 4.0F, 1.0F);
		this.shape12_1.addBox(-1.6F, 0.0F, -3.0F, 3, 6, 4, 0.0F);
		this.shape3 = new ModelRenderer(this, 48, 31);
		this.shape3.setRotationPoint(2.0F, 5.0F, 0.0F);
		this.shape3.addBox(-2.0F, 1.0F, -2.0F, 4, 6, 4, 0.0F);
		this.shape22 = new ModelRenderer(this, 0, 16);
		this.shape22.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape22.addBox(-4.0F, 0.0F, 1.0F, 8, 2, 3, 0.0F);
		this.shape2 = new ModelRenderer(this, 0, 28);
		this.shape2.setRotationPoint(0.0F, 6.0F, 0.0F);
		this.shape2.addBox(-4.0F, -6.0F, -2.0F, 8, 12, 4, 0.0F);
		this.shape13 = new ModelRenderer(this, 24, 38);
		this.shape13.setRotationPoint(0.0F, 2.2F, 1.0F);
		this.shape13.addBox(-1.0F, -3.0F, -2.0F, 2, 3, 2, 0.0F);
		this.setRotateAngle(this.shape13, -0.3490658503988659F, 0.0F, 0.0F);
		this.shape9 = new ModelRenderer(this, 50, 18);
		this.shape9.setRotationPoint(0.0F, 4.0F, 2.1F);
		this.shape9.addBox(-1.5F, -4.0F, 0.0F, 3, 4, 2, 0.0F);
		this.setRotateAngle(this.shape9, -0.3490658503988659F, 0.0F, 0.0F);
		this.shape2.addChild(this.shape20);
		this.shape3.addChild(this.shape4);
		this.shape3_1.addChild(this.shape4_1);
		this.rightArm.addChild(this.shape12);
		this.shape20.addChild(this.head);
		this.shape2.addChild(this.shape17);
		this.shape2.addChild(this.leftArm);
		this.shape2.addChild(this.rightArm);
		this.shape12_1.addChild(this.shape13_1);
		this.shape4_1.addChild(this.shape9_1);
		this.shape2.addChild(this.shape19);
		this.shape2.addChild(this.shape17_1);
		this.shape2.addChild(this.shape3_1);
		this.leftArm.addChild(this.shape12_1);
		this.shape2.addChild(this.shape3);
		this.head.addChild(this.shape22);
		this.shape12.addChild(this.shape13);
		this.shape4.addChild(this.shape9);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.shape6.render(f5);
		this.shape2.render(f5);
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

		this.head.rotateAngleY = headYaw * 0.017453292F;
		this.head.rotateAngleX = headPitch * 0.0057453292F;

		this.rightArm.rotationPointZ = 0.0F;
		this.rightArm.rotationPointX = -7.0F;
		this.leftArm.rotationPointZ = 0.0F;
		this.leftArm.rotationPointX = 7.0F;

		this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.rightArm.rotateAngleZ = 0.0F;
		this.leftArm.rotateAngleZ = 0.0F;

		this.rightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.leftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.rightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.leftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
	}
}
