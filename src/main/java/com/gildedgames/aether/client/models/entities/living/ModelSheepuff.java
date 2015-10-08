package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.living.EntitySheepuff;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelSheepuff extends ModelQuadruped
{
	private float headRotationAngleX;

	public ModelSheepuff()
	{
		super(12, 0.0F);

		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-3.0F, -4.0F, -6.0F, 6, 6, 8, 0.0F);
		this.head.setRotationPoint(0.0F, 6.0F, -8.0F);

		this.body = new ModelRenderer(this, 28, 8);
		this.body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 0.0F);
		this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
	}

	public void setLivingAnimations(EntityLivingBase entity, float p_78086_2_, float p_78086_3_, float p_78086_4_)
	{
		super.setLivingAnimations(entity, p_78086_2_, p_78086_3_, p_78086_4_);

		EntitySheepuff sheepuff = (EntitySheepuff) entity;

		this.head.rotationPointY = 6.0F + sheepuff.getHeadRotationPointY(p_78086_4_) * 9.0F;
		this.headRotationAngleX = sheepuff.getHeadRotationAngleX(p_78086_4_);
	}

	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
	{
		super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);

		this.head.rotateAngleX = this.headRotationAngleX;
	}
}
