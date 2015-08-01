package com.gildedgames.aether.client.models.player.attachments;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelParachute extends ModelBase
{
	private ModelRenderer canopyMain;

	private ModelRenderer canopyLeft2, canopyRight2;

	private ModelRenderer canopyLeft3, canopyRight3;

	private ModelRenderer cordFrontLeft, cordFrontRight;

	private ModelRenderer cordBackLeft, cordBackRight;

	private ModelRenderer cordMiddleLeft, cordMiddleRight;

	private ModelRenderer chestStrap;

	private ModelRenderer shoulderStrapLeft, shoulderStrapRight;

	public ModelParachute()
	{
		this.textureWidth = 64;
		this.textureHeight = 128;

		this.createModel();
	}

	private void createModel()
	{
		this.canopyMain = new ModelRenderer(this, 0, 0);
		this.canopyMain.addBox(-7F, -15F, -8.5F, 14, 2, 17);
		this.canopyMain.setRotationPoint(0F, 0F, 0F);
		this.canopyMain.setTextureSize(64, 128);
		this.canopyMain.mirror = true;
		this.setRotation(this.canopyMain, 0F, 0F, 0F);

		this.canopyRight2 = new ModelRenderer(this, 0, 19);
		this.canopyRight2.mirror = true;
		this.canopyRight2.addBox(-8.5F, -16F, -7.5F, 8, 2, 15);
		this.canopyRight2.setRotationPoint(0F, 0F, 0F);
		this.canopyRight2.setTextureSize(64, 128);
		this.setRotation(this.canopyRight2, 0F, 0F, -0.35F);

		this.canopyLeft2 = new ModelRenderer(this, 0, 19);
		this.canopyLeft2.addBox(0.5F, -16F, -7.5F, 8, 2, 15);
		this.canopyLeft2.setRotationPoint(0F, 0F, 0F);
		this.canopyLeft2.setTextureSize(64, 128);
		this.canopyLeft2.mirror = true;
		this.setRotation(this.canopyLeft2, 0F, 0F, 0.35F);

		this.canopyRight3 = new ModelRenderer(this, 0, 36);
		this.canopyRight3.mirror = true;
		this.canopyRight3.addBox(-13F, -16.5F, -6.5F, 8, 1, 13);
		this.canopyRight3.setRotationPoint(0F, 0F, 0F);
		this.canopyRight3.setTextureSize(64, 128);
		this.setRotation(this.canopyRight3, 0F, 0F, -0.5F);

		this.canopyLeft3 = new ModelRenderer(this, 0, 36);
		this.canopyLeft3.addBox(5F, -16.5F, -6.5F, 8, 1, 13);
		this.canopyLeft3.setRotationPoint(0F, 0F, 0F);
		this.canopyLeft3.setTextureSize(64, 128);
		this.canopyLeft3.mirror = true;
		this.setRotation(this.canopyLeft3, 0F, 0F, 0.5F);

		this.cordFrontLeft = new ModelRenderer(this, 0, 50);
		this.cordFrontLeft.addBox(4F, -17F, -1F, 1, 15, 1);
		this.cordFrontLeft.setRotationPoint(0F, 0F, 0F);
		this.cordFrontLeft.setTextureSize(64, 128);
		this.cordFrontLeft.mirror = true;
		this.setRotation(this.cordFrontLeft, 0.35F, 0F, 0.5F);

		this.cordFrontRight = new ModelRenderer(this, 0, 50);
		this.cordFrontRight.addBox(-5F, -17F, -1F, 1, 15, 1);
		this.cordFrontRight.setRotationPoint(0F, 0F, 0F);
		this.cordFrontRight.setTextureSize(64, 128);
		this.cordFrontRight.mirror = true;
		this.setRotation(this.cordFrontRight, 0.35F, 0F, -0.5F);

		this.cordBackLeft = new ModelRenderer(this, 0, 50);
		this.cordBackLeft.addBox(4F, -17F, 0F, 1, 15, 1);
		this.cordBackLeft.setRotationPoint(0F, 0F, 0F);
		this.cordBackLeft.setTextureSize(64, 128);
		this.cordBackLeft.mirror = true;
		this.setRotation(this.cordBackLeft, -0.35F, 0F, 0.5F);

		this.cordBackRight = new ModelRenderer(this, 0, 50);
		this.cordBackRight.addBox(-5F, -17F, 0F, 1, 15, 1);
		this.cordBackRight.setRotationPoint(0F, 0F, 0F);
		this.cordBackRight.setTextureSize(64, 128);
		this.cordBackRight.mirror = true;
		this.setRotation(this.cordBackRight, -0.35F, 0F, -0.5F);

		this.cordMiddleLeft = new ModelRenderer(this, 0, 50);
		this.cordMiddleLeft.addBox(2F, -20F, -0.5F, 1, 15, 1);
		this.cordMiddleLeft.setRotationPoint(0F, 0F, 0F);
		this.cordMiddleLeft.setTextureSize(64, 128);
		this.cordMiddleLeft.mirror = true;
		this.setRotation(this.cordMiddleLeft, 0F, 0F, 1F);

		this.cordMiddleRight = new ModelRenderer(this, 0, 50);
		this.cordMiddleRight.addBox(-3F, -20F, -0.5F, 1, 15, 1);
		this.cordMiddleRight.setRotationPoint(0F, 0F, 0F);
		this.cordMiddleRight.setTextureSize(64, 128);
		this.cordMiddleRight.mirror = true;
		this.setRotation(this.cordMiddleRight, 0F, 0F, -1F);

		this.chestStrap = new ModelRenderer(this, 0, 66);
		this.chestStrap.addBox(-5F, 2F, -2.5F, 10, 1, 5);
		this.chestStrap.setRotationPoint(0F, 0F, 0F);
		this.chestStrap.setTextureSize(64, 128);
		this.chestStrap.mirror = true;
		this.setRotation(this.chestStrap, 0F, 0F, 0F);

		this.shoulderStrapRight = new ModelRenderer(this, 0, 72);
		this.shoulderStrapRight.addBox(-6F, -1F, -3F, 2, 5, 6);
		this.shoulderStrapRight.setRotationPoint(0F, 0F, 0F);
		this.shoulderStrapRight.setTextureSize(64, 128);
		this.shoulderStrapRight.mirror = true;
		this.setRotation(this.shoulderStrapRight, 0F, 0F, 0F);

		this.shoulderStrapLeft = new ModelRenderer(this, 0, 72);
		this.shoulderStrapLeft.addBox(4F, -1F, -3F, 2, 5, 6);
		this.shoulderStrapLeft.setRotationPoint(0F, 0F, 0F);
		this.shoulderStrapLeft.setTextureSize(64, 128);
		this.shoulderStrapLeft.mirror = true;
		this.setRotation(this.shoulderStrapLeft, 0F, 0F, 0F);
	}

	public void draw(float scale)
	{
		this.canopyMain.render(scale);
		this.canopyRight2.render(scale);
		this.canopyLeft2.render(scale);
		this.canopyRight3.render(scale);
		this.canopyLeft3.render(scale);
		this.cordFrontLeft.render(scale);
		this.cordFrontRight.render(scale);
		this.cordBackLeft.render(scale);
		this.cordBackRight.render(scale);
		this.cordMiddleLeft.render(scale);
		this.cordMiddleRight.render(scale);
		this.chestStrap.render(scale);
		this.shoulderStrapRight.render(scale);
		this.shoulderStrapLeft.render(scale);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
