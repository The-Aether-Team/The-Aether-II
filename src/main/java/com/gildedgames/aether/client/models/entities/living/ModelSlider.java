package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelSlider extends ModelBase
{

	public ModelRenderer head;

	public ModelSlider()
	{
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-8F, -4F, -8F, 16, 16, 16, 0.0F);
		this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	@Override
	public void render(Entity e, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.setRotationAngles(f, f1, f2, f3, f4, f5);
		GlStateManager.pushMatrix();
		GlStateManager.scale(2.0F, 2.0F, 2.0F);
		this.head.render(f5);
		GlStateManager.popMatrix();
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.head.rotateAngleY = 0F;
		this.head.rotateAngleX = 0F;
	}

}
