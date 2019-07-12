package com.gildedgames.aether.client.models.entities.tile;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;

public class ModelMoaEgg extends EntityModel
{

	private final RendererModel Shape1;

	private final RendererModel Shape2;

	private final RendererModel Shape3;

	public ModelMoaEgg()
	{
		this.textureWidth = 32;
		this.textureHeight = 32;

		this.Shape1 = new RendererModel(this, 0, 18);
		this.Shape1.addBox(-3F, -6F, -3F, 6, 1, 6);
		this.Shape1.setRotationPoint(0F, 18F, 0F);
		this.Shape1.setTextureSize(64, 32);
		this.Shape1.mirror = true;
		this.setRotation(this.Shape1, 0F, 0F, 0F);
		this.Shape2 = new RendererModel(this, 0, 25);
		this.Shape2.addBox(-3F, 5F, -3F, 6, 1, 6);
		this.Shape2.setRotationPoint(0F, 18F, 0F);
		this.Shape2.setTextureSize(64, 32);
		this.Shape2.mirror = true;
		this.setRotation(this.Shape2, 0F, 0F, 0F);
		this.Shape3 = new RendererModel(this, 0, 0);
		this.Shape3.addBox(-4F, -5F, -4F, 8, 10, 8);
		this.Shape3.setRotationPoint(0F, 18F, 0F);
		this.Shape3.setTextureSize(64, 32);
		this.Shape3.mirror = true;
		this.setRotation(this.Shape3, 0F, 0F, 0F);
	}

	public void renderAll(float f5)
	{
		this.Shape1.render(f5);
		this.Shape2.render(f5);
		this.Shape3.render(f5);
	}

	private void setRotation(RendererModel model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
