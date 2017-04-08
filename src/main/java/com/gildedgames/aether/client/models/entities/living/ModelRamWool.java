package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelRamWool extends ModelBase
{
	//fields
	ModelRenderer BodyMain;

	ModelRenderer BodyBack;

	ModelRenderer BodyBottom;

	ModelRenderer LegFrontLeft1;

	ModelRenderer LegFrontRight1;

	ModelRenderer LegBackLeft1;

	ModelRenderer LegBackLeft2;

	ModelRenderer LegBackRight1;

	ModelRenderer LegBackRight2;

	ModelRenderer Tail;

	public ModelRamWool()
	{
		this.textureWidth = 128;
		this.textureHeight = 256;

		this.BodyMain = new ModelRenderer(this, 28, 74);
		this.BodyMain.addBox(-8F, -1F, -10F, 16, 16, 18);
		this.BodyMain.setRotationPoint(0F, 0F, 0F);
		this.BodyMain.setTextureSize(128, 256);
		this.BodyMain.mirror = true;
		this.setRotation(this.BodyMain, 0F, 0F, 0F);
		this.BodyBack = new ModelRenderer(this, 35, 108);
		this.BodyBack.addBox(-7F, -3F, 7F, 14, 11, 13);
		this.BodyBack.setRotationPoint(0F, 0F, 0F);
		this.BodyBack.setTextureSize(128, 256);
		this.BodyBack.mirror = true;
		this.setRotation(this.BodyBack, -0.3490659F, 0F, 0F);
		this.BodyBottom = new ModelRenderer(this, 44, 132);
		this.BodyBottom.addBox(-5F, 10F, 8F, 10, 5, 8);
		this.BodyBottom.setRotationPoint(0F, 0F, 0F);
		this.BodyBottom.setTextureSize(128, 256);
		this.BodyBottom.mirror = true;
		this.setRotation(this.BodyBottom, 0F, 0F, 0F);
		this.LegFrontLeft1 = new ModelRenderer(this, 96, 61);
		this.LegFrontLeft1.addBox(-3F, 0F, -3F, 6, 7, 6);
		this.LegFrontLeft1.setRotationPoint(4F, 13F, -5F);
		this.LegFrontLeft1.setTextureSize(128, 256);
		this.LegFrontLeft1.mirror = true;
		this.setRotation(this.LegFrontLeft1, 0.0872665F, 0F, 0F);
		this.LegFrontRight1 = new ModelRenderer(this, 4, 61);
		this.LegFrontRight1.addBox(-3F, 0F, -3F, 6, 7, 6);
		this.LegFrontRight1.setRotationPoint(-4F, 13F, -5F);
		this.LegFrontRight1.setTextureSize(128, 256);
		this.LegFrontRight1.mirror = true;
		this.setRotation(this.LegFrontRight1, 0.0872665F, 0F, 0F);
		this.LegBackLeft1 = new ModelRenderer(this, 96, 108);
		this.LegBackLeft1.addBox(-3F, -1F, -3F, 6, 7, 8);
		this.LegBackLeft1.setRotationPoint(3.5F, 12F, 7F);
		this.LegBackLeft1.setTextureSize(128, 256);
		this.LegBackLeft1.mirror = true;
		this.setRotation(this.LegBackLeft1, 0.6108652F, 0F, 0F);
		this.LegBackLeft2 = new ModelRenderer(this, 99, 123);
		this.LegBackLeft2.addBox(-3F, 6F, -3F, 6, 2, 5);
		this.LegBackLeft2.setRotationPoint(3.5F, 12F, 7F);
		this.LegBackLeft2.setTextureSize(128, 256);
		this.LegBackLeft2.mirror = true;
		this.setRotation(this.LegBackLeft2, 0.6108652F, 0F, 0F);
		this.LegBackRight1 = new ModelRenderer(this, 0, 108);
		this.LegBackRight1.addBox(-3F, -1F, -3F, 6, 7, 8);
		this.LegBackRight1.setRotationPoint(-3.5F, 12F, 7F);
		this.LegBackRight1.setTextureSize(128, 256);
		this.LegBackRight1.mirror = true;
		this.setRotation(this.LegBackRight1, 0.6108652F, 0F, 0F);
		this.LegBackRight2 = new ModelRenderer(this, 3, 123);
		this.LegBackRight2.addBox(-3F, 6F, -3F, 6, 2, 5);
		this.LegBackRight2.setRotationPoint(-3.5F, 12F, 7F);
		this.LegBackRight2.setTextureSize(128, 256);
		this.LegBackRight2.mirror = true;
		this.setRotation(this.LegBackRight2, 0.6108652F, 0F, 0F);
		this.Tail = new ModelRenderer(this, 56, 145);
		this.Tail.addBox(-2F, 0F, -1F, 4, 9, 2);
		this.Tail.setRotationPoint(0F, 5F, 18F);
		this.Tail.setTextureSize(128, 256);
		this.Tail.mirror = true;
		this.setRotation(this.Tail, 0.1745329F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
			float scale)
	{
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);

		if (this.isChild)
		{
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.5F, 0.5F, 0.5F);
			GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
			this.BodyMain.render(scale);
			this.BodyBack.render(scale);
			this.BodyBottom.render(scale);
			this.LegFrontLeft1.render(scale);
			this.LegFrontRight1.render(scale);
			this.LegBackLeft1.render(scale);
			this.LegBackLeft2.render(scale);
			this.LegBackRight1.render(scale);
			this.LegBackRight2.render(scale);
			this.Tail.render(scale);
			GlStateManager.popMatrix();
		}
		else
		{
			this.BodyMain.render(scale);
			this.BodyBack.render(scale);
			this.BodyBottom.render(scale);
			this.LegFrontLeft1.render(scale);
			this.LegFrontRight1.render(scale);
			this.LegBackLeft1.render(scale);
			this.LegBackLeft2.render(scale);
			this.LegBackRight1.render(scale);
			this.LegBackRight2.render(scale);
			this.Tail.render(scale);
		}
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
			float scaleFactor, Entity entity)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

		this.Tail.rotateAngleZ = (MathHelper.cos(ageInTicks * 0.1662F) * 0.2F);

		this.LegFrontLeft1.rotateAngleX = 0.0872665F + (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);

		this.LegFrontRight1.rotateAngleX = 0.0872665F + (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount);

		this.LegBackLeft1.rotateAngleX = 0.6108652F + (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount);
		this.LegBackLeft2.rotateAngleX = 0.6108652F + (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount);

		this.LegBackRight1.rotateAngleX = 0.6108652F + (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
		this.LegBackRight2.rotateAngleX = 0.6108652F + (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
	}

}
