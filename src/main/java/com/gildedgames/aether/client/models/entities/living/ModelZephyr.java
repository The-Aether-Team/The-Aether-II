package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelZephyr extends ModelBase
{

	ModelRenderer mainbody;

	ModelRenderer RBcloud;

	ModelRenderer LBcloud;

	ModelRenderer tail2;

	ModelRenderer FRcloud;

	ModelRenderer FLcloud;

	ModelRenderer tail1;

	ModelRenderer Shape1;

	ModelRenderer Shape2;

	ModelRenderer Shape3;

	public ModelZephyr()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.mainbody = new ModelRenderer(this, 0, 0);
		this.mainbody.addBox(-6F, -4F, -7F, 12, 8, 14);
		this.mainbody.setRotationPoint(0F, 10F, 0F);
		this.mainbody.setTextureSize(64, 64);
		this.mainbody.mirror = true;
		this.setRotation(this.mainbody, 0F, 0F, 0F);
		this.RBcloud = new ModelRenderer(this, 16, 22);
		this.RBcloud.addBox(-7F, -2F, 0F, 2, 6, 6);
		this.RBcloud.setRotationPoint(0F, 10F, 0F);
		this.RBcloud.setTextureSize(64, 64);
		this.RBcloud.mirror = true;
		this.setRotation(this.RBcloud, 0F, 0F, 0F);
		this.LBcloud = new ModelRenderer(this, 16, 22);
		this.LBcloud.addBox(5F, -2F, 0F, 2, 6, 6);
		this.LBcloud.setRotationPoint(0F, 10F, 0F);
		this.LBcloud.setTextureSize(64, 64);
		this.LBcloud.mirror = true;
		this.setRotation(this.LBcloud, 0F, 0F, 0F);
		this.LBcloud.mirror = false;
		this.tail2 = new ModelRenderer(this, 32, 22);
		this.tail2.addBox(-2F, -2F, -2F, 4, 4, 4);
		this.tail2.setRotationPoint(0F, 10F, 19F);
		this.tail2.setTextureSize(64, 64);
		this.tail2.mirror = true;
		this.setRotation(this.tail2, 0F, 0F, 0F);
		this.FRcloud = new ModelRenderer(this, 0, 22);
		this.FRcloud.addBox(-8F, -3F, -7F, 2, 6, 6);
		this.FRcloud.setRotationPoint(0F, 10F, 0F);
		this.FRcloud.setTextureSize(64, 64);
		this.FRcloud.mirror = true;
		this.setRotation(this.FRcloud, 0F, 0F, 0F);
		this.FLcloud = new ModelRenderer(this, 0, 22);
		this.FLcloud.addBox(6F, -3F, -7F, 2, 6, 6);
		this.FLcloud.setRotationPoint(0F, 10F, 0F);
		this.FLcloud.setTextureSize(64, 64);
		this.FLcloud.mirror = true;
		this.setRotation(this.FLcloud, 0F, 0F, 0F);
		this.FLcloud.mirror = false;
		this.tail1 = new ModelRenderer(this, 0, 34);
		this.tail1.addBox(-3F, -3F, -3F, 6, 6, 6);
		this.tail1.setRotationPoint(0F, 10F, 12F);
		this.tail1.setTextureSize(64, 64);
		this.tail1.mirror = true;
		this.setRotation(this.tail1, 0F, 0F, 0F);
		this.Shape1 = new ModelRenderer(this, 0, 1);
		this.Shape1.addBox(-7F, 0F, -9F, 4, 4, 2);
		this.Shape1.setRotationPoint(0F, 10F, 0F);
		this.Shape1.setTextureSize(64, 64);
		this.Shape1.mirror = true;
		this.setRotation(this.Shape1, 0F, 0F, 0F);
		this.Shape2 = new ModelRenderer(this, 0, 1);
		this.Shape2.addBox(3F, 0F, -9F, 4, 4, 2);
		this.Shape2.setRotationPoint(0F, 10F, 0F);
		this.Shape2.setTextureSize(64, 64);
		this.Shape2.mirror = true;
		this.setRotation(this.Shape2, 0F, 0F, 0F);
		this.Shape3 = new ModelRenderer(this, 0, 7);
		this.Shape3.addBox(-3F, 3F, -8F, 6, 3, 1);
		this.Shape3.setRotationPoint(0F, 10F, 0F);
		this.Shape3.setTextureSize(64, 64);
		this.Shape3.mirror = true;
		this.setRotation(this.Shape3, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.mainbody.render(f5);
		this.RBcloud.render(f5);
		this.LBcloud.render(f5);
		this.tail2.render(f5);
		this.FRcloud.render(f5);
		this.FLcloud.render(f5);
		this.tail1.render(f5);
		this.Shape1.render(f5);
		this.Shape2.render(f5);
		this.Shape3.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		float boff = this.sinage2;
		float yOffset = 5.5F;

		float vertMotion = (float) (Math.sin(f * 20 / 57.2957795) * f1 * .5F);
		float PI = (float) Math.PI;
		float initialOffset = PI / 2;

		this.FRcloud.rotationPointY = vertMotion + 10;
		this.FLcloud.rotationPointX = vertMotion * .5F;
		this.LBcloud.rotationPointY = 8 - vertMotion * .5F;
		this.RBcloud.rotationPointY = 9 + vertMotion * .5F;

		this.Shape2.rotationPointY = 10 - vertMotion;
		this.Shape2.rotationPointX = -vertMotion * .5F;
		this.Shape1.rotationPointY = 10 + vertMotion;
		this.Shape1.rotationPointX = +vertMotion * .5F;

		this.tail1.rotationPointX = (float) (Math.sin(f * 20 / 57.2957795) * f1 * .75F);
		this.tail1.rotateAngleY = ((float) Math.pow(0.99f, -4)) * 1 * PI / 4 * (MathHelper.cos(-0.055f * f + initialOffset));
		this.tail1.rotationPointY = 10 - vertMotion;

		this.tail2.rotationPointX = ((float) Math.pow(0.99f, 1)) * 1 * PI / 4 * (MathHelper.cos(-0.055f * f + initialOffset));
		this.tail2.rotationPointY = 10 - vertMotion * 1.25F;
		this.tail2.rotateAngleY = this.tail1.rotateAngleY + .25F;

		//this.Shape3.rotationPointY = +8 + ((((EntityZephyr) entity).attackTime + 1));

		this.mainbody.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
		this.RBcloud.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
		this.LBcloud.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
		this.tail2.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
		this.FRcloud.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
		this.FLcloud.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
		this.tail1.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
		this.Shape1.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
		this.Shape2.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
		this.Shape3.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
	}

	public float sinage;

	public float sinage2;
}
