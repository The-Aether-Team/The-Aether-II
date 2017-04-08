package com.gildedgames.aether.client.models.entities.attachments;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBombLeftHand extends ModelBase
{
	//fields
	ModelRenderer bomb_hand_left_main;

	ModelRenderer bomb_hand_left_sub;

	public ModelBombLeftHand()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;

		this.bomb_hand_left_main = new ModelRenderer(this, 0, 0);
		this.bomb_hand_left_main.addBox(10.5F, 8F, -23F, 7, 5, 5);
		this.bomb_hand_left_main.setRotationPoint(14F, -10F, 2F);
		this.bomb_hand_left_main.setTextureSize(64, 32);
		this.bomb_hand_left_main.mirror = true;
		this.setRotation(this.bomb_hand_left_main, 0F, -0.1745329F, 0F);
		this.bomb_hand_left_sub = new ModelRenderer(this, 0, 0);
		this.bomb_hand_left_sub.addBox(8.5F, 8.5F, -22.5F, 11, 4, 4);
		this.bomb_hand_left_sub.setRotationPoint(14F, -10F, 2F);
		this.bomb_hand_left_sub.setTextureSize(64, 32);
		this.bomb_hand_left_sub.mirror = true;
		this.setRotation(this.bomb_hand_left_sub, 0F, -0.1745329F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.bomb_hand_left_main.render(f5);
		this.bomb_hand_left_sub.render(f5);
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
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}

