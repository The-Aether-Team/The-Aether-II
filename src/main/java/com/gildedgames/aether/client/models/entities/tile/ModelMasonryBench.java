package com.gildedgames.aether.client.models.entities.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMasonryBench extends ModelBase
{
	//fields
	ModelRenderer MainBottom;
	ModelRenderer Pedal;
	ModelRenderer MainLeft;
	ModelRenderer MainBack;
	ModelRenderer MainRight;
	ModelRenderer MainTop;
	ModelRenderer TopRight;
	ModelRenderer TopBack;
	ModelRenderer TopLeft;
	ModelRenderer Workslab;
	ModelRenderer SawRotor;
	ModelRenderer SawRight;
	ModelRenderer SawLeft;
	ModelRenderer RimRight;
	ModelRenderer RimBack;
	ModelRenderer RimLeft;

	public ModelMasonryBench()
	{
		textureWidth = 128;
		textureHeight = 256;

		MainBottom = new ModelRenderer(this, 33, 91);
		MainBottom.addBox(-8F, 22F, -8F, 16, 2, 16);
		MainBottom.setRotationPoint(0F, 0F, 0F);
		MainBottom.setTextureSize(128, 256);
		MainBottom.mirror = true;
		setRotation(MainBottom, 0F, 0F, 0F);
		Pedal = new ModelRenderer(this, 97, 91);
		Pedal.addBox(-2F, 0F, -5F, 4, 2, 5);
		Pedal.setRotationPoint(3.5F, 19F, -2F);
		Pedal.setTextureSize(128, 256);
		Pedal.mirror = true;
		setRotation(Pedal, 0.1745329F, 0F, 0F);
		MainLeft = new ModelRenderer(this, 0, 67);
		MainLeft.addBox(-8F, 14F, -8F, 9, 8, 16);
		MainLeft.setRotationPoint(0F, 0F, 0F);
		MainLeft.setTextureSize(128, 256);
		MainLeft.mirror = true;
		setRotation(MainLeft, 0F, 0F, 0F);
		MainBack = new ModelRenderer(this, 50, 73);
		MainBack.addBox(1F, 14F, -2F, 5, 8, 10);
		MainBack.setRotationPoint(0F, 0F, 0F);
		MainBack.setTextureSize(128, 256);
		MainBack.mirror = true;
		setRotation(MainBack, 0F, 0F, 0F);
		MainRight = new ModelRenderer(this, 80, 67);
		MainRight.addBox(6F, 14F, -8F, 2, 8, 16);
		MainRight.setRotationPoint(0F, 0F, 0F);
		MainRight.setTextureSize(128, 256);
		MainRight.mirror = true;
		setRotation(MainRight, 0F, 0F, 0F);
		MainTop = new ModelRenderer(this, 33, 49);
		MainTop.addBox(-8F, 12F, -8F, 16, 2, 16);
		MainTop.setRotationPoint(0F, 0F, 0F);
		MainTop.setTextureSize(128, 256);
		MainTop.mirror = true;
		setRotation(MainTop, 0F, 0F, 0F);
		TopRight = new ModelRenderer(this, 0, 31);
		TopRight.addBox(-8F, 10F, -8F, 10, 2, 16);
		TopRight.setRotationPoint(0F, 0F, 0F);
		TopRight.setTextureSize(128, 256);
		TopRight.mirror = true;
		setRotation(TopRight, 0F, 0F, 0F);
		TopBack = new ModelRenderer(this, 52, 44);
		TopBack.addBox(2F, 10F, 5F, 3, 2, 3);
		TopBack.setRotationPoint(0F, 0F, 0F);
		TopBack.setTextureSize(128, 256);
		TopBack.mirror = true;
		setRotation(TopBack, 0F, 0F, 0F);
		TopLeft = new ModelRenderer(this, 64, 31);
		TopLeft.addBox(5F, 10F, -8F, 3, 2, 16);
		TopLeft.setRotationPoint(0F, 0F, 0F);
		TopLeft.setTextureSize(128, 256);
		TopLeft.mirror = true;
		setRotation(TopLeft, 0F, 0F, 0F);
		Workslab = new ModelRenderer(this, 0, 17);
		Workslab.addBox(-6F, 9.5F, -7F, 7, 1, 13);
		Workslab.setRotationPoint(0F, 0F, 0F);
		Workslab.setTextureSize(128, 256);
		Workslab.mirror = true;
		setRotation(Workslab, 0F, 0F, 0F);
		SawRotor = new ModelRenderer(this, 40, 29);
		SawRotor.addBox(0F, -0.5F, -0.5F, 3, 1, 1);
		SawRotor.setRotationPoint(2F, 11F, 0F);
		SawRotor.setTextureSize(128, 256);
		SawRotor.mirror = true;
		setRotation(SawRotor, 0.7853982F, 0F, 0F);
		SawRight = new ModelRenderer(this, 48, 21);
		SawRight.addBox(0.9F, -2.5F, -2.5F, 1, 5, 5);
		SawRight.setRotationPoint(2F, 11F, 0F);
		SawRight.setTextureSize(128, 256);
		SawRight.mirror = true;
		setRotation(SawRight, 0F, 0F, 0F);
		SawLeft = new ModelRenderer(this, 60, 21);
		SawLeft.addBox(1.1F, -2.5F, -2.5F, 1, 5, 5);
		SawLeft.setRotationPoint(2F, 11F, 0F);
		SawLeft.setTextureSize(128, 256);
		SawLeft.mirror = true;
		setRotation(SawLeft, 0.7853982F, 0F, 0F);
		RimRight = new ModelRenderer(this, 0, 0);
		RimRight.addBox(-8F, 8F, -8F, 1, 2, 15);
		RimRight.setRotationPoint(0F, 0F, 0F);
		RimRight.setTextureSize(128, 256);
		RimRight.mirror = true;
		setRotation(RimRight, 0F, 0F, 0F);
		RimBack = new ModelRenderer(this, 32, 14);
		RimBack.addBox(-8F, 8F, 7F, 16, 2, 1);
		RimBack.setRotationPoint(0F, 0F, 0F);
		RimBack.setTextureSize(128, 256);
		RimBack.mirror = true;
		setRotation(RimBack, 0F, 0F, 0F);
		RimLeft = new ModelRenderer(this, 66, 0);
		RimLeft.addBox(7F, 8F, -8F, 1, 2, 15);
		RimLeft.setRotationPoint(0F, 0F, 0F);
		RimLeft.setTextureSize(128, 256);
		RimLeft.mirror = true;
		setRotation(RimLeft, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.render(f5);
	}

	public void render(float f5)
	{
		MainBottom.render(f5);
		Pedal.render(f5);
		MainLeft.render(f5);
		MainBack.render(f5);
		MainRight.render(f5);
		MainTop.render(f5);
		TopRight.render(f5);
		TopBack.render(f5);
		TopLeft.render(f5);
		Workslab.render(f5);
		SawRotor.render(f5);
		SawRight.render(f5);
		SawLeft.render(f5);
		RimRight.render(f5);
		RimBack.render(f5);
		RimLeft.render(f5);
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
