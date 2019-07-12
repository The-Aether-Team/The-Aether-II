package com.gildedgames.aether.client.models.entities.tile;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;

public class ModelAltar extends EntityModel
{
	private final RendererModel Base1;

	private final RendererModel Base2;

	private final RendererModel Pillar;

	private final RendererModel Main1;

	private final RendererModel Main2;

	private final RendererModel CornerFrontRight;

	private final RendererModel CornerBackRight;

	private final RendererModel CornerFrontLeft;

	private final RendererModel CornerBackLeft;

	private final RendererModel ZaniteGem1;

	private final RendererModel ZaniteGem2;

	private final RendererModel AmbroGemFrontRight;

	private final RendererModel AmbroGemBackRight;

	private final RendererModel AmbroGemFrontLeft;

	private final RendererModel AmbroGemBackLeft;

	public ModelAltar()
	{
		this.textureWidth = 64;
		this.textureHeight = 128;

		this.Base1 = new RendererModel(this, 4, 112);
		this.Base1.addBox(-7F, 22F, -7F, 14, 2, 14);
		this.Base1.setRotationPoint(0F, 0F, 0F);
		this.Base1.setTextureSize(64, 128);
		this.Base1.mirror = true;
		this.setRotation(this.Base1, 0F, 0F, 0F);
		this.Base2 = new RendererModel(this, 16, 102);
		this.Base2.addBox(-4F, 20F, -4F, 8, 2, 8);
		this.Base2.setRotationPoint(0F, 0F, 0F);
		this.Base2.setTextureSize(64, 128);
		this.Base2.mirror = true;
		this.setRotation(this.Base2, 0F, 0F, 0F);
		this.Pillar = new RendererModel(this, 28, 95);
		this.Pillar.addBox(-1F, 15F, -1F, 2, 5, 2);
		this.Pillar.setRotationPoint(0F, 0F, 0F);
		this.Pillar.setTextureSize(64, 128);
		this.Pillar.mirror = true;
		this.setRotation(this.Pillar, 0F, 0F, 0F);
		this.Main1 = new RendererModel(this, 20, 86);
		this.Main1.addBox(-3F, 12F, -3F, 6, 3, 6);
		this.Main1.setRotationPoint(0F, 0F, 0F);
		this.Main1.setTextureSize(64, 128);
		this.Main1.mirror = true;
		this.setRotation(this.Main1, 0F, 0F, 0F);
		this.Main2 = new RendererModel(this, 4, 69);
		this.Main2.addBox(-7F, 9F, -7F, 14, 3, 14);
		this.Main2.setRotationPoint(0F, 0F, 0F);
		this.Main2.setTextureSize(64, 128);
		this.Main2.mirror = true;
		this.setRotation(this.Main2, 0F, 0F, 0F);
		this.CornerFrontRight = new RendererModel(this, 0, 74);
		this.CornerFrontRight.addBox(-8F, 8F, -8F, 4, 5, 4);
		this.CornerFrontRight.setRotationPoint(0F, 0F, 0F);
		this.CornerFrontRight.setTextureSize(64, 128);
		this.CornerFrontRight.mirror = true;
		this.setRotation(this.CornerFrontRight, 0F, 0F, 0F);
		this.CornerBackRight = new RendererModel(this, 0, 65);
		this.CornerBackRight.addBox(-8F, 8F, 4F, 4, 5, 4);
		this.CornerBackRight.setRotationPoint(0F, 0F, 0F);
		this.CornerBackRight.setTextureSize(64, 128);
		this.CornerBackRight.mirror = true;
		this.setRotation(this.CornerBackRight, 0F, 0F, 0F);
		this.CornerFrontLeft = new RendererModel(this, 48, 74);
		this.CornerFrontLeft.addBox(4F, 8F, -8F, 4, 5, 4);
		this.CornerFrontLeft.setRotationPoint(0F, 0F, 0F);
		this.CornerFrontLeft.setTextureSize(64, 128);
		this.CornerFrontLeft.mirror = true;
		this.setRotation(this.CornerFrontLeft, 0F, 0F, 0F);
		this.CornerBackLeft = new RendererModel(this, 48, 65);
		this.CornerBackLeft.addBox(4F, 8F, 4F, 4, 5, 4);
		this.CornerBackLeft.setRotationPoint(0F, 0F, 0F);
		this.CornerBackLeft.setTextureSize(64, 128);
		this.CornerBackLeft.mirror = true;
		this.setRotation(this.CornerBackLeft, 0F, 0F, 0F);
		this.ZaniteGem1 = new RendererModel(this, 20, 62);
		this.ZaniteGem1.addBox(-3F, 8F, -3F, 6, 1, 6);
		this.ZaniteGem1.setRotationPoint(0F, 0F, 0F);
		this.ZaniteGem1.setTextureSize(64, 128);
		this.ZaniteGem1.mirror = true;
		this.setRotation(this.ZaniteGem1, 0F, -0.7853982F, 0F);
		this.ZaniteGem2 = new RendererModel(this, 22, 56);
		this.ZaniteGem2.addBox(-2.5F, 7F, -2.5F, 5, 1, 5);
		this.ZaniteGem2.setRotationPoint(0F, 0F, 0F);
		this.ZaniteGem2.setTextureSize(64, 128);
		this.ZaniteGem2.mirror = true;
		this.setRotation(this.ZaniteGem2, 0F, 0.7853982F, 0F);
		this.AmbroGemFrontRight = new RendererModel(this, 4, 62);
		this.AmbroGemFrontRight.addBox(-1F, -1F, -1F, 2, 1, 2);
		this.AmbroGemFrontRight.setRotationPoint(-6F, 8F, -6F);
		this.AmbroGemFrontRight.setTextureSize(64, 128);
		this.AmbroGemFrontRight.mirror = true;
		this.setRotation(this.AmbroGemFrontRight, 0F, 0F, 0F);
		this.AmbroGemBackRight = new RendererModel(this, 4, 59);
		this.AmbroGemBackRight.addBox(-1F, -1F, -1F, 2, 1, 2);
		this.AmbroGemBackRight.setRotationPoint(-6F, 8F, 6F);
		this.AmbroGemBackRight.setTextureSize(64, 128);
		this.AmbroGemBackRight.mirror = true;
		this.setRotation(this.AmbroGemBackRight, 0F, 0F, 0F);
		this.AmbroGemFrontLeft = new RendererModel(this, 52, 62);
		this.AmbroGemFrontLeft.addBox(-1F, -1F, -1F, 2, 1, 2);
		this.AmbroGemFrontLeft.setRotationPoint(6F, 8F, -6F);
		this.AmbroGemFrontLeft.setTextureSize(64, 128);
		this.AmbroGemFrontLeft.mirror = true;
		this.setRotation(this.AmbroGemFrontLeft, 0F, 0F, 0F);
		this.AmbroGemBackLeft = new RendererModel(this, 52, 59);
		this.AmbroGemBackLeft.addBox(-1F, -1F, -1F, 2, 1, 2);
		this.AmbroGemBackLeft.setRotationPoint(6F, 8F, 6F);
		this.AmbroGemBackLeft.setTextureSize(64, 128);
		this.AmbroGemBackLeft.mirror = true;
		this.setRotation(this.AmbroGemBackLeft, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
			float scale)
	{
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		this.render(scale);
	}

	public void render(float scale)
	{
		this.Base1.render(scale);
		this.Base2.render(scale);
		this.Pillar.render(scale);
		this.Main1.render(scale);
		this.Main2.render(scale);
		this.CornerFrontRight.render(scale);
		this.CornerBackRight.render(scale);
		this.CornerFrontLeft.render(scale);
		this.CornerBackLeft.render(scale);
		this.ZaniteGem1.render(scale);
		this.ZaniteGem2.render(scale);
		this.AmbroGemFrontRight.render(scale);
		this.AmbroGemBackRight.render(scale);
		this.AmbroGemFrontLeft.render(scale);
		this.AmbroGemBackLeft.render(scale);
	}

	private void setRotation(RendererModel model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
