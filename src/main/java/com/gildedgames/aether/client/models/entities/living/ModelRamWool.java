package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelRamWool extends ModelBase
{
	public ModelRenderer BodyMain;
	public ModelRenderer BodyBack;
	public ModelRenderer LegFrontLeft1;
	public ModelRenderer LegFrontRight1;

	public ModelRamWool()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.BodyBack = new ModelRenderer(this, 6, 31);
		this.BodyBack.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.BodyBack.addBox(-6.0F, -1.5F, 4.6F, 12, 5, 10, 0.0F);
		this.setRotateAngle(BodyBack, -0.17453292519943295F, -0.0F, 0.0F);
		this.LegFrontLeft1 = new ModelRenderer(this, 28, 46);
		this.LegFrontLeft1.mirror = true;
		this.LegFrontLeft1.setRotationPoint(4.0F, 13.0F, -5.0F);
		this.LegFrontLeft1.addBox(-3.0F, -5.0F, -2.5F, 6, 8, 6, 0.0F);
		this.setRotateAngle(LegFrontLeft1, 0.08726646259971647F, -0.0F, 0.0F);
		this.LegFrontRight1 = new ModelRenderer(this, 4, 46);
		this.LegFrontRight1.setRotationPoint(-4.0F, 13.0F, -5.0F);
		this.LegFrontRight1.addBox(-3.0F, -5.0F, -2.5F, 6, 8, 6, 0.0F);
		this.setRotateAngle(LegFrontRight1, 0.08726646259971647F, -0.0F, 0.0F);
		this.BodyMain = new ModelRenderer(this, 0, 0);
		this.BodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.BodyMain.addBox(-6.5F, -1.0F, -10.0F, 13, 16, 15, 0.0F);
		this.BodyMain.addChild(this.BodyBack);
		this.BodyMain.addChild(this.LegFrontLeft1);
		this.BodyMain.addChild(this.LegFrontRight1);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		if (this.isChild)
		{
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.5F, 0.5F, 0.5F);
			GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
			this.BodyMain.render(f5);
			GlStateManager.popMatrix();
		}
		else
		{
			this.BodyMain.render(f5);
		}
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
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
								  float scaleFactor, Entity entity)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

		this.LegFrontLeft1.rotateAngleX = 0.08726646259971647F + (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount);
		this.LegFrontRight1.rotateAngleX = 0.08726646259971647F + (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount);
	}
}