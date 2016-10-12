package com.gildedgames.aether.client.models.entities.attachments;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBomb3 extends ModelBase
{
	//fields
	ModelRenderer bomb_3_main;

	ModelRenderer bomb_3_sub;

	public ModelBomb3()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;

		this.bomb_3_main = new ModelRenderer(this, 0, 0);
		this.bomb_3_main.addBox(-8.5F, -24F, -8F, 7, 5, 5);
		this.bomb_3_main.setRotationPoint(0F, 0F, 0F);
		this.bomb_3_main.setTextureSize(64, 32);
		this.bomb_3_main.mirror = true;
		this.setRotation(this.bomb_3_main, 0F, 0F, -0.5235988F);
		this.bomb_3_sub = new ModelRenderer(this, 0, 0);
		this.bomb_3_sub.addBox(-10.5F, -23.5F, -7.5F, 11, 4, 4);
		this.bomb_3_sub.setRotationPoint(0F, 0F, 0F);
		this.bomb_3_sub.setTextureSize(64, 32);
		this.bomb_3_sub.mirror = true;
		this.setRotation(this.bomb_3_sub, 0F, 0F, -0.5235988F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.bomb_3_main.render(f5);
		this.bomb_3_sub.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}

