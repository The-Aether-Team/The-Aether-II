package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.animals.EntitySheepuff;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelSheepuffWool extends ModelBase
{
	public ModelRenderer woolMain;
	public ModelRenderer woolMainTop;
	public ModelRenderer woolBack;
	public ModelRenderer woolTail;
	public ModelRenderer woolFrontLeftThigh;
	public ModelRenderer woolBackLeftThigh;
	public ModelRenderer woolFrontRightThigh;
	public ModelRenderer woolBackRightThigh;
	public ModelRenderer planeTopBend;
	public ModelRenderer planeTopBack;
	public ModelRenderer planeLeftBend;
	public ModelRenderer planeRightBend;
	public ModelRenderer planeTop;
	public ModelRenderer planeLeft;
	public ModelRenderer planeRight;

	public ModelSheepuffWool()
	{
		this.textureWidth = 100;
		this.textureHeight = 100;
		this.woolFrontRightThigh = new ModelRenderer(this, 52, 7);
		this.woolFrontRightThigh.setRotationPoint(-4.0F, 8.3F, -4.0F);
		this.woolFrontRightThigh.addBox(-2.5F, 0.0F, -3.0F, 5, 5, 6, 0.0F);
		this.woolBackRightThigh = new ModelRenderer(this, 53, 57);
		this.woolBackRightThigh.setRotationPoint(-4.0F, 8.3F, 7.0F);
		this.woolBackRightThigh.addBox(-2.5F, 0.0F, -3.0F, 5, 5, 6, 0.0F);
		this.planeTop = new ModelRenderer(this, 18, 74);
		this.planeTop.setRotationPoint(0.0F, 0.0F, 3.0F);
		this.planeTop.addBox(-6.0F, 0.0F, 0.0F, 12, 0, 12, 0.0F);
		this.setRotateAngle(planeTop, -0.8203047484373349F, 0.0F, 0.0F);
		this.planeTopBack = new ModelRenderer(this, 26, 86);
		this.planeTopBack.setRotationPoint(0.0F, -1.0F, 7.0F);
		this.planeTopBack.addBox(-4.7F, 0.0F, 0.0F, 10, 0, 6, 0.0F);
		this.setRotateAngle(planeTopBack, -0.27314402793711257F, 0.0F, 0.0F);
		this.woolBack = new ModelRenderer(this, 25, 47);
		this.woolBack.setRotationPoint(0.0F, 1.1F, 6.8F);
		this.woolBack.addBox(-5.5F, 0.0F, 0.0F, 11, 10, 6, 0.0F);
		this.setRotateAngle(woolBack, -0.18203784098300857F, 0.0F, 0.0F);
		this.woolBackLeftThigh = new ModelRenderer(this, 9, 57);
		this.woolBackLeftThigh.setRotationPoint(4.0F, 8.3F, 7.0F);
		this.woolBackLeftThigh.addBox(-2.5F, 0.0F, -3.0F, 5, 5, 6, 0.0F);
		this.planeRightBend = new ModelRenderer(this, 63, 67);
		this.planeRightBend.setRotationPoint(-6.0F, 4.0F, -6.4F);
		this.planeRightBend.addBox(0.0F, -4.0F, 0.0F, 0, 8, 4, 0.0F);
		this.setRotateAngle(planeRightBend, 0.0F, 0.40980330836826856F, 3.141592653589793F);
		this.woolMain = new ModelRenderer(this, 15, 20);
		this.woolMain.setRotationPoint(0.0F, 3.4F, 0.0F);
		this.woolMain.addBox(-6.0F, 0.0F, -6.5F, 12, 12, 15, 0.0F);
		this.setRotateAngle(woolMain, 0.0F, 0.01300062177754636F, 0.0F);
		this.planeTopBend = new ModelRenderer(this, 27, 71);
		this.planeTopBend.setRotationPoint(0.0F, 0.0F, -6.3F);
		this.planeTopBend.addBox(-6.0F, 0.0F, 0.0F, 12, 0, 3, 0.0F);
		this.setRotateAngle(planeTopBend, 0.8196066167365371F, 0.0F, 0.0F);
		this.woolMainTop = new ModelRenderer(this, 20, 6);
		this.woolMainTop.setRotationPoint(0.0F, -1.0F, 1.0F);
		this.woolMainTop.addBox(-5.0F, 0.0F, -6.0F, 10, 2, 12, 0.0F);
		this.woolFrontLeftThigh = new ModelRenderer(this, 10, 7);
		this.woolFrontLeftThigh.setRotationPoint(4.0F, 8.3F, -4.0F);
		this.woolFrontLeftThigh.addBox(-2.5F, 0.0F, -3.0F, 5, 5, 6, 0.0F);
		this.planeLeftBend = new ModelRenderer(this, 10, 67);
		this.planeLeftBend.setRotationPoint(6.0F, 4.2F, -6.4F);
		this.planeLeftBend.addBox(0.0F, -4.0F, 0.0F, 0, 8, 4, 0.0F);
		this.setRotateAngle(planeLeftBend, 0.0F, 0.40980330836826856F, 0.0F);
		this.woolTail = new ModelRenderer(this, 38, 63);
		this.woolTail.setRotationPoint(0.0F, 4.2F, 10.1F);
		this.woolTail.addBox(-1.0F, 0.0F, 0.0F, 2, 5, 2, 0.0F);
		this.setRotateAngle(woolTail, 0.4553564018453205F, 0.0F, 0.0F);
		this.planeRight = new ModelRenderer(this, 57, 69);
		this.planeRight.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.planeRight.addBox(0.0F, -4.0F, 0.0F, 0, 8, 10, 0.0F);
		this.setRotateAngle(planeRight, 0.0F, -0.3839724354387525F, 0.0F);
		this.planeLeft = new ModelRenderer(this, 4, 69);
		this.planeLeft.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.planeLeft.addBox(0.0F, -4.0F, 0.0F, 0, 8, 10, 0.0F);
		this.setRotateAngle(planeLeft, 0.0F, -0.3839724354387525F, 0.009599310885968812F);
		this.woolMain.addChild(this.woolFrontRightThigh);
		this.woolMain.addChild(this.woolBackRightThigh);
		this.planeTopBend.addChild(this.planeTop);
		this.woolMain.addChild(this.planeTopBack);
		this.woolMain.addChild(this.woolBack);
		this.woolMain.addChild(this.woolBackLeftThigh);
		this.woolMain.addChild(this.planeRightBend);
		this.woolMain.addChild(this.planeTopBend);
		this.woolMain.addChild(this.woolMainTop);
		this.woolMain.addChild(this.woolFrontLeftThigh);
		this.woolMain.addChild(this.planeLeftBend);
		this.woolMain.addChild(this.woolTail);
		this.planeRightBend.addChild(this.planeRight);
		this.planeLeftBend.addChild(this.planeLeft);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		GlStateManager.pushMatrix();

		float puffiness = ((EntitySheepuff) entity).getPuffiness();
		float scale = 1 + puffiness * 0.3f;

		GlStateManager.scale(scale, scale, scale);
		GlStateManager.translate(0, -puffiness / 4f, 0);

		this.woolMain.render(f5);

		GlStateManager.popMatrix();
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
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
	{
		super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);

		float perc = ((EntitySheepuff) entitylivingbaseIn).getHeadRotationPointY(partialTickTime);

		this.woolMain.rotationPointY = 3.4F + perc * 1.9F;
		this.woolTail.rotateAngleZ = (MathHelper.cos(entitylivingbaseIn.ticksExisted * 0.1662F) * 0.2F);
	}
}
