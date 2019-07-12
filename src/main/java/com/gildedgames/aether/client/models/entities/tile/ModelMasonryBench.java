package com.gildedgames.aether.client.models.entities.tile;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;

public class ModelMasonryBench extends EntityModel
{
	//fields
	final RendererModel MainBottom;

	final RendererModel Pedal;

	final RendererModel MainLeft;

	final RendererModel MainBack;

	final RendererModel MainRight;

	final RendererModel MainTop;

	final RendererModel TopRight;

	final RendererModel TopBack;

	final RendererModel TopLeft;

	final RendererModel Workslab;

	final RendererModel SawRotor;

	final RendererModel SawRight;

	final RendererModel SawLeft;

	final RendererModel RimRight;

	final RendererModel RimBack;

	final RendererModel RimLeft;

	public ModelMasonryBench()
	{
		this.textureWidth = 128;
		this.textureHeight = 256;

		this.MainBottom = new RendererModel(this, 33, 91);
		this.MainBottom.addBox(-8F, 22F, -8F, 16, 2, 16);
		this.MainBottom.setRotationPoint(0F, 0F, 0F);
		this.MainBottom.setTextureSize(128, 256);
		this.MainBottom.mirror = true;
		this.setRotation(this.MainBottom, 0F, 0F, 0F);
		this.Pedal = new RendererModel(this, 97, 91);
		this.Pedal.addBox(-2F, 0F, -5F, 4, 2, 5);
		this.Pedal.setRotationPoint(3.5F, 19F, -2F);
		this.Pedal.setTextureSize(128, 256);
		this.Pedal.mirror = true;
		this.setRotation(this.Pedal, 0.1745329F, 0F, 0F);
		this.MainLeft = new RendererModel(this, 0, 67);
		this.MainLeft.addBox(-8F, 14F, -8F, 9, 8, 16);
		this.MainLeft.setRotationPoint(0F, 0F, 0F);
		this.MainLeft.setTextureSize(128, 256);
		this.MainLeft.mirror = true;
		this.setRotation(this.MainLeft, 0F, 0F, 0F);
		this.MainBack = new RendererModel(this, 50, 73);
		this.MainBack.addBox(1F, 14F, -2F, 5, 8, 10);
		this.MainBack.setRotationPoint(0F, 0F, 0F);
		this.MainBack.setTextureSize(128, 256);
		this.MainBack.mirror = true;
		this.setRotation(this.MainBack, 0F, 0F, 0F);
		this.MainRight = new RendererModel(this, 80, 67);
		this.MainRight.addBox(6F, 14F, -8F, 2, 8, 16);
		this.MainRight.setRotationPoint(0F, 0F, 0F);
		this.MainRight.setTextureSize(128, 256);
		this.MainRight.mirror = true;
		this.setRotation(this.MainRight, 0F, 0F, 0F);
		this.MainTop = new RendererModel(this, 33, 49);
		this.MainTop.addBox(-8F, 12F, -8F, 16, 2, 16);
		this.MainTop.setRotationPoint(0F, 0F, 0F);
		this.MainTop.setTextureSize(128, 256);
		this.MainTop.mirror = true;
		this.setRotation(this.MainTop, 0F, 0F, 0F);
		this.TopRight = new RendererModel(this, 0, 31);
		this.TopRight.addBox(-8F, 10F, -8F, 10, 2, 16);
		this.TopRight.setRotationPoint(0F, 0F, 0F);
		this.TopRight.setTextureSize(128, 256);
		this.TopRight.mirror = true;
		this.setRotation(this.TopRight, 0F, 0F, 0F);
		this.TopBack = new RendererModel(this, 52, 44);
		this.TopBack.addBox(2F, 10F, 5F, 3, 2, 3);
		this.TopBack.setRotationPoint(0F, 0F, 0F);
		this.TopBack.setTextureSize(128, 256);
		this.TopBack.mirror = true;
		this.setRotation(this.TopBack, 0F, 0F, 0F);
		this.TopLeft = new RendererModel(this, 64, 31);
		this.TopLeft.addBox(5F, 10F, -8F, 3, 2, 16);
		this.TopLeft.setRotationPoint(0F, 0F, 0F);
		this.TopLeft.setTextureSize(128, 256);
		this.TopLeft.mirror = true;
		this.setRotation(this.TopLeft, 0F, 0F, 0F);
		this.Workslab = new RendererModel(this, 0, 17);
		this.Workslab.addBox(-6F, 9.5F, -7F, 7, 1, 13);
		this.Workslab.setRotationPoint(0F, 0F, 0F);
		this.Workslab.setTextureSize(128, 256);
		this.Workslab.mirror = true;
		this.setRotation(this.Workslab, 0F, 0F, 0F);
		this.SawRotor = new RendererModel(this, 40, 29);
		this.SawRotor.addBox(0F, -0.5F, -0.5F, 3, 1, 1);
		this.SawRotor.setRotationPoint(2F, 11F, 0F);
		this.SawRotor.setTextureSize(128, 256);
		this.SawRotor.mirror = true;
		this.setRotation(this.SawRotor, 0.7853982F, 0F, 0F);
		this.SawRight = new RendererModel(this, 48, 21);
		this.SawRight.addBox(0.9F, -2.5F, -2.5F, 1, 5, 5);
		this.SawRight.setRotationPoint(2F, 11F, 0F);
		this.SawRight.setTextureSize(128, 256);
		this.SawRight.mirror = true;
		this.setRotation(this.SawRight, 0F, 0F, 0F);
		this.SawLeft = new RendererModel(this, 60, 21);
		this.SawLeft.addBox(1.1F, -2.5F, -2.5F, 1, 5, 5);
		this.SawLeft.setRotationPoint(2F, 11F, 0F);
		this.SawLeft.setTextureSize(128, 256);
		this.SawLeft.mirror = true;
		this.setRotation(this.SawLeft, 0.7853982F, 0F, 0F);
		this.RimRight = new RendererModel(this, 0, 0);
		this.RimRight.addBox(-8F, 8F, -8F, 1, 2, 15);
		this.RimRight.setRotationPoint(0F, 0F, 0F);
		this.RimRight.setTextureSize(128, 256);
		this.RimRight.mirror = true;
		this.setRotation(this.RimRight, 0F, 0F, 0F);
		this.RimBack = new RendererModel(this, 32, 14);
		this.RimBack.addBox(-8F, 8F, 7F, 16, 2, 1);
		this.RimBack.setRotationPoint(0F, 0F, 0F);
		this.RimBack.setTextureSize(128, 256);
		this.RimBack.mirror = true;
		this.setRotation(this.RimBack, 0F, 0F, 0F);
		this.RimLeft = new RendererModel(this, 66, 0);
		this.RimLeft.addBox(7F, 8F, -8F, 1, 2, 15);
		this.RimLeft.setRotationPoint(0F, 0F, 0F);
		this.RimLeft.setTextureSize(128, 256);
		this.RimLeft.mirror = true;
		this.setRotation(this.RimLeft, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.render(f5);
	}

	public void render(float f5)
	{
		this.MainBottom.render(f5);
		this.Pedal.render(f5);
		this.MainLeft.render(f5);
		this.MainBack.render(f5);
		this.MainRight.render(f5);
		this.MainTop.render(f5);
		this.TopRight.render(f5);
		this.TopBack.render(f5);
		this.TopLeft.render(f5);
		this.Workslab.render(f5);
		this.SawRotor.render(f5);
		this.SawRight.render(f5);
		this.SawLeft.render(f5);
		this.RimRight.render(f5);
		this.RimBack.render(f5);
		this.RimLeft.render(f5);
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
