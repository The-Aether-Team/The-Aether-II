package com.gildedgames.aether.client.models.entities.attachments;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelParachute extends ModelBase
{

	public final ModelRenderer Cloud1;

	public final ModelRenderer Cloud2;

	public final ModelRenderer Cloud3;

	public final ModelRenderer Cloud4;

	public final ModelRenderer Cloud5;

	public final ModelRenderer Shape2;

	public final ModelRenderer Shape3;

	public final ModelRenderer Shape4;

	public final ModelRenderer Shape1;

	public ModelParachute()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.Cloud1 = new ModelRenderer(this, 0, 0);
		this.Cloud1.addBox(0F, 0F, 0F, 8, 8, 16);
		this.Cloud1.setRotationPoint(-29.5F, 2F, 0F);
		this.Cloud1.setTextureSize(64, 64);
		this.Cloud1.mirror = true;
		this.setRotation(this.Cloud1, 0F, 0F, -0.6981317F);
		this.Cloud2 = new ModelRenderer(this, 0, 0);
		this.Cloud2.addBox(0F, 0F, 0F, 8, 8, 16);
		this.Cloud2.setRotationPoint(23.65F, -3F, 0F);
		this.Cloud2.setTextureSize(64, 64);
		this.Cloud2.mirror = true;
		this.setRotation(this.Cloud2, 0F, 0F, 0.6981317F);
		this.Cloud3 = new ModelRenderer(this, 0, 0);
		this.Cloud3.addBox(0F, 0F, 0F, 16, 8, 16);
		this.Cloud3.setRotationPoint(-8F, -6F, 0F);
		this.Cloud3.setTextureSize(64, 64);
		this.Cloud3.mirror = true;
		this.setRotation(this.Cloud3, 0F, 0F, 0F);
		this.Cloud4 = new ModelRenderer(this, 0, 0);
		this.Cloud4.addBox(0F, 0F, 0F, 16, 8, 16);
		this.Cloud4.setRotationPoint(8F, -6F, 0F);
		this.Cloud4.setTextureSize(64, 64);
		this.Cloud4.mirror = true;
		this.setRotation(this.Cloud4, 0F, 0F, 0.1919862F);
		this.Cloud5 = new ModelRenderer(this, 0, 0);
		this.Cloud5.addBox(0F, 0F, 0F, 16, 8, 16);
		this.Cloud5.setRotationPoint(-23.5F, -3F, 0F);
		this.Cloud5.setTextureSize(64, 64);
		this.Cloud5.mirror = true;
		this.setRotation(this.Cloud5, 0F, 0F, -0.1919862F);
		this.Shape2 = new ModelRenderer(this, 6, 0);
		this.Shape2.addBox(0F, 0F, -3F, 2, 14, 2);
		this.Shape2.setRotationPoint(5F, 0F, 13F);
		this.Shape2.setTextureSize(64, 64);
		this.Shape2.mirror = true;
		this.setRotation(this.Shape2, -0.1919862F, 0F, 0.1047198F);
		this.Shape3 = new ModelRenderer(this, 0, 0);
		this.Shape3.addBox(0F, 0F, -3F, 2, 14, 2);
		this.Shape3.setRotationPoint(-8F, 0F, 12F);
		this.Shape3.setTextureSize(64, 64);
		this.Shape3.mirror = true;
		this.setRotation(this.Shape3, -0.1919862F, 0F, -0.1047198F);
		this.Shape4 = new ModelRenderer(this, 6, 0);
		this.Shape4.addBox(0F, 0F, 0F, 2, 14, 2);
		this.Shape4.setRotationPoint(5F, 0F, 1F);
		this.Shape4.setTextureSize(64, 64);
		this.Shape4.mirror = true;
		this.setRotation(this.Shape4, 0.1919862F, 0F, 0.1047198F);
		this.Shape1 = new ModelRenderer(this, 0, 0);
		this.Shape1.addBox(0F, 0F, 0F, 2, 14, 2);
		this.Shape1.setRotationPoint(-8F, 0F, 1F);
		this.Shape1.setTextureSize(64, 64);
		this.Shape1.mirror = true;
		this.setRotation(this.Shape1, 0.1919862F, 0F, -0.1047198F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.Cloud1.render(f5);
		this.Cloud2.render(f5);
		this.Cloud3.render(f5);
		this.Cloud4.render(f5);
		this.Cloud5.render(f5);
		this.Shape2.render(f5);
		this.Shape3.render(f5);
		this.Shape4.render(f5);
		this.Shape1.render(f5);
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
		//		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}
