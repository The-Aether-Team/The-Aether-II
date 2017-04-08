package com.gildedgames.aether.client.models.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBlock extends ModelBase
{

	private ModelRenderer box;

	public ModelBlock()
	{
		this.textureWidth = 32;
		this.textureHeight = 32;

		this.box = new ModelRenderer(this, 0, 0);
		this.box.addBox(-8F, -4F, -8F, 16, 16, 16, 0.0F);
		this.box.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	public void renderAll(float f5)
	{
		this.box.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
