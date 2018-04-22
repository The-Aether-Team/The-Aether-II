package com.gildedgames.aether.client.models.entities.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTeleporter extends ModelBase
{
	public ModelRenderer BlockBase;

	public ModelRenderer ShardFront1, ShardLeft1, ShardLeft2, ShardLeft3, ShardLeft4, ShardLeft5;

	public ModelRenderer ShardBack, ShardRight1, ShardFront2, ShardFront3;

	public ModelRenderer ShardRight2, ShardRight3, ShardRight4, ShardRight5;

	public ModelRenderer PedestalPlatform, PedestalBase, PortalFramebase, PedestalFrameHolder;

	public ModelRenderer Core1, Core2, Core3, Core4;

	public ModelRenderer WingRightBase, WingLeftBase;

	public ModelRenderer WingRightArm, WingRightFeather1, WingRightFeather2, WingRightFeather3;

	public ModelRenderer WingLeftArm, WingLeftFeather1, WingLeftFeather2, WingLeftFeather3;

	public ModelRenderer PortalFrameRight, PortalFrameLeft, PortalFrameTop;

	public ModelRenderer PortalCorner1, PortalCorner2, PortalCorner3, PortalCorner4, PortalVortex;

	public ModelTeleporter()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.BlockBase = new ModelRenderer(this, 0, 15);
		this.BlockBase.setRotationPoint(0.0F, 9.0F, 0.0F);
		this.BlockBase.addBox(-5.0F, 0.0F, -5.0F, 10, 4, 10, 0.0F);
		this.Core3 = new ModelRenderer(this, 20, 0);
		this.Core3.setRotationPoint(0.0F, -0.5F, -1.0F);
		this.Core3.addBox(-2.5F, 0.0F, -1.0F, 5, 10, 1, 0.0F);
		this.WingLeftArm = new ModelRenderer(this, 70, 54);
		this.WingLeftArm.mirror = true;
		this.WingLeftArm.setRotationPoint(-1.8F, -1.4F, -0.2F);
		this.WingLeftArm.addBox(-2.0F, -6.0F, 0.0F, 2, 9, 1, 0.0F);
		this.setRotateAngle(this.WingLeftArm, 0.0F, 0.0F, -0.17453292519943295F);
		this.ShardLeft5 = new ModelRenderer(this, 20, 34);
		this.ShardLeft5.setRotationPoint(-5.0F, 5.0F, 3.0F);
		this.ShardLeft5.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
		this.ShardFront1 = new ModelRenderer(this, 0, 30);
		this.ShardFront1.setRotationPoint(-4.0F, 4.0F, -5.0F);
		this.ShardFront1.addBox(0.0F, 0.0F, 0.0F, 2, 4, 1, 0.0F);
		this.ShardLeft2 = new ModelRenderer(this, 12, 30);
		this.ShardLeft2.setRotationPoint(-5.0F, 5.0F, -5.0F);
		this.ShardLeft2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
		this.PedestalBase = new ModelRenderer(this, 24, 44);
		this.PedestalBase.setRotationPoint(0.0F, -10.0F, 4.0F);
		this.PedestalBase.addBox(-5.0F, 0.0F, -9.0F, 10, 10, 10, 0.0F);
		this.WingRightFeather1 = new ModelRenderer(this, 64, 45);
		this.WingRightFeather1.setRotationPoint(1.6F, -3.5F, 0.0F);
		this.WingRightFeather1.addBox(0.0F, -2.0F, -0.5F, 7, 2, 1, 0.0F);
		this.setRotateAngle(this.WingRightFeather1, 0.0F, 0.0F, -0.436F);
		this.WingLeftFeather1 = new ModelRenderer(this, 64, 45);
		this.WingLeftFeather1.mirror = true;
		this.WingLeftFeather1.setRotationPoint(-1.6F, -3.5F, 0.0F);
		this.WingLeftFeather1.addBox(-7.0F, -2.0F, -0.5F, 7, 2, 1, 0.0F);
		this.setRotateAngle(this.WingLeftFeather1, 0.0F, 0.0F, 0.436F);
		this.ShardRight1 = new ModelRenderer(this, 22, 30);
		this.ShardRight1.setRotationPoint(4.0F, 4.0F, -5.0F);
		this.ShardRight1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 10, 0.0F);
		this.WingRightFeather3 = new ModelRenderer(this, 64, 51);
		this.WingRightFeather3.setRotationPoint(1.6F, 0.0F, 0.0F);
		this.WingRightFeather3.addBox(0.0F, 0.0F, -0.5F, 5, 2, 1, 0.0F);
		this.setRotateAngle(this.WingRightFeather3, 0.0F, 0.0F, 0.08726646259971647F);
		this.ShardLeft3 = new ModelRenderer(this, 12, 34);
		this.ShardLeft3.setRotationPoint(-5.0F, 5.0F, -1.0F);
		this.ShardLeft3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
		this.ShardRight4 = new ModelRenderer(this, 0, 51);
		this.ShardRight4.setRotationPoint(4.0F, 6.0F, -3.0F);
		this.ShardRight4.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
		this.ShardRight3 = new ModelRenderer(this, 0, 55);
		this.ShardRight3.setRotationPoint(4.0F, 5.0F, 2.0F);
		this.ShardRight3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
		this.WingRightArm = new ModelRenderer(this, 70, 54);
		this.WingRightArm.setRotationPoint(1.8F, -1.4F, -0.2F);
		this.WingRightArm.addBox(0.0F, -6.0F, 0.0F, 2, 9, 1, 0.0F);
		this.setRotateAngle(this.WingRightArm, 0.0F, 0.0F, 0.17453292519943295F);
		this.Core2 = new ModelRenderer(this, 0, 12);
		this.Core2.setRotationPoint(0.0F, 10.0F, 0.0F);
		this.Core2.addBox(-3.0F, 0.0F, -1.0F, 6, 1, 2, 0.0F);
		this.PortalFrameRight = new ModelRenderer(this, 83, 2);
		this.PortalFrameRight.setRotationPoint(2.0F, 0.0F, 1.0F);
		this.PortalFrameRight.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
		this.PortalCorner3 = new ModelRenderer(this, 77, 4);
		this.PortalCorner3.setRotationPoint(-4.0F, 0.0F, 6.0F);
		this.PortalCorner3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.PortalCorner2 = new ModelRenderer(this, 69, 4);
		this.PortalCorner2.setRotationPoint(1.0F, 0.0F, 1.0F);
		this.PortalCorner2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.PortalFrameTop = new ModelRenderer(this, 69, 0);
		this.PortalFrameTop.setRotationPoint(0.0F, 0.0F, 7.0F);
		this.PortalFrameTop.addBox(-4.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
		this.PortalCorner1 = new ModelRenderer(this, 69, 2);
		this.PortalCorner1.setRotationPoint(-2.0F, 0.0F, 1.0F);
		this.PortalCorner1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.Core1 = new ModelRenderer(this, 0, 0);
		this.Core1.setRotationPoint(0.0F, 3.0F, 0.0F);
		this.Core1.addBox(-4.0F, 0.0F, -1.0F, 8, 10, 2, 0.0F);
		this.PortalVortex = new ModelRenderer(this, 85, 0);
		this.PortalVortex.setRotationPoint(0.0F, 0.5F, 1.0F);
		this.PortalVortex.addBox(-2.0F, 0.0F, 0.0F, 4, 0, 6, 0.0F);
		this.ShardBack = new ModelRenderer(this, 0, 41);
		this.ShardBack.setRotationPoint(-4.0F, 4.0F, 4.0F);
		this.ShardBack.addBox(0.0F, 0.0F, 0.0F, 8, 4, 1, 0.0F);
		this.WingRightBase = new ModelRenderer(this, 64, 60);
		this.WingRightBase.setRotationPoint(5.0F, -3.5F, 2.0F);
		this.WingRightBase.addBox(0.0F, -1.5F, 0.0F, 2, 3, 1, 0.0F);
		this.PortalFrameLeft = new ModelRenderer(this, 69, 2);
		this.PortalFrameLeft.setRotationPoint(-3.0F, 0.0F, 1.0F);
		this.PortalFrameLeft.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
		this.PortalCorner4 = new ModelRenderer(this, 77, 2);
		this.PortalCorner4.setRotationPoint(3.0F, 0.0F, 6.0F);
		this.PortalCorner4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.ShardFront3 = new ModelRenderer(this, 0, 37);
		this.ShardFront3.setRotationPoint(0.0F, 5.0F, -5.0F);
		this.ShardFront3.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		this.ShardRight5 = new ModelRenderer(this, 0, 58);
		this.ShardRight5.setRotationPoint(4.0F, 6.0F, 2.0F);
		this.ShardRight5.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.WingLeftFeather3 = new ModelRenderer(this, 64, 51);
		this.WingLeftFeather3.mirror = true;
		this.WingLeftFeather3.setRotationPoint(-1.6F, 0.0F, 0.0F);
		this.WingLeftFeather3.addBox(-5.0F, 0.0F, -0.5F, 5, 2, 1, 0.0F);
		this.setRotateAngle(this.WingLeftFeather3, 0.0F, 0.0F, -0.08726646259971647F);
		this.Core4 = new ModelRenderer(this, 20, 0);
		this.Core4.setRotationPoint(0.0F, -0.5F, 1.0F);
		this.Core4.addBox(-2.5F, 0.0F, -1.0F, 5, 10, 1, 0.0F);
		this.setRotateAngle(this.Core4, 0.0F, 3.141592653589793F, 0.0F);
		this.ShardLeft4 = new ModelRenderer(this, 20, 30);
		this.ShardLeft4.setRotationPoint(-5.0F, 6.0F, 0.0F);
		this.ShardLeft4.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
		this.ShardRight2 = new ModelRenderer(this, 0, 46);
		this.ShardRight2.setRotationPoint(4.0F, 5.0F, -4.0F);
		this.ShardRight2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
		this.ShardLeft1 = new ModelRenderer(this, 0, 30);
		this.ShardLeft1.setRotationPoint(-5.0F, 4.0F, -5.0F);
		this.ShardLeft1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 10, 0.0F);
		this.ShardFront2 = new ModelRenderer(this, 0, 35);
		this.ShardFront2.setRotationPoint(0.0F, 4.0F, -5.0F);
		this.ShardFront2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
		this.PedestalPlatform = new ModelRenderer(this, 30, 0);
		this.PedestalPlatform.setRotationPoint(0.0F, 0.0F, -6.0F);
		this.PedestalPlatform.addBox(-6.0F, 0.0F, 0.4F, 12, 1, 15, 0.0F);
		this.setRotateAngle(this.PedestalPlatform, 0.7853981633974483F, 0.0F, 0.0F);
		this.PedestalFrameHolder = new ModelRenderer(this, 30, 16);
		this.PedestalFrameHolder.setRotationPoint(0.0F, 1000.0F, 0.0F);
		this.PedestalFrameHolder.addBox(-6.0F, -2.0F, 0.4F, 12, 2, 1, 0.0F);
		this.WingRightFeather2 = new ModelRenderer(this, 64, 48);
		this.WingRightFeather2.setRotationPoint(1.6F, -2.0F, 0.0F);
		this.WingRightFeather2.addBox(0.0F, -1.0F, -0.5F, 6, 2, 1, 0.0F);
		this.setRotateAngle(this.WingRightFeather2, 0.0F, 0.0F, -0.17453292519943295F);
		this.WingLeftFeather2 = new ModelRenderer(this, 64, 48);
		this.WingLeftFeather2.mirror = true;
		this.WingLeftFeather2.setRotationPoint(-1.6F, -2.0F, 0.0F);
		this.WingLeftFeather2.addBox(-6.0F, -1.0F, -0.5F, 6, 2, 1, 0.0F);
		this.setRotateAngle(this.WingLeftFeather2, 0.0F, 0.0F, 0.17453292519943295F);
		this.PortalFramebase = new ModelRenderer(this, 69, 9);
		this.PortalFramebase.setRotationPoint(0.0F, -3.6F, -4.1F);
		this.PortalFramebase.addBox(-2.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
		this.setRotateAngle(this.PortalFramebase, 0.7853981633974483F, 0.0F, 0.0F);
		this.WingLeftBase = new ModelRenderer(this, 64, 60);
		this.WingLeftBase.mirror = true;
		this.WingLeftBase.setRotationPoint(-5.0F, -3.5F, 2.0F);
		this.WingLeftBase.addBox(-2.0F, -1.5F, 0.0F, 2, 3, 1, 0.0F);
		this.Core1.addChild(this.Core3);
		this.WingLeftBase.addChild(this.WingLeftArm);
		this.BlockBase.addChild(this.ShardLeft5);
		this.BlockBase.addChild(this.ShardFront1);
		this.BlockBase.addChild(this.ShardLeft2);
		this.BlockBase.addChild(this.PedestalBase);
		this.WingRightArm.addChild(this.WingRightFeather1);
		this.WingLeftArm.addChild(this.WingLeftFeather1);
		this.BlockBase.addChild(this.ShardRight1);
		this.WingRightArm.addChild(this.WingRightFeather3);
		this.BlockBase.addChild(this.ShardLeft3);
		this.BlockBase.addChild(this.ShardRight4);
		this.BlockBase.addChild(this.ShardRight3);
		this.WingRightBase.addChild(this.WingRightArm);
		this.Core1.addChild(this.Core2);
		this.PortalFramebase.addChild(this.PortalFrameRight);
		this.PortalFramebase.addChild(this.PortalCorner3);
		this.PortalFramebase.addChild(this.PortalCorner2);
		this.PortalFramebase.addChild(this.PortalFrameTop);
		this.PortalFramebase.addChild(this.PortalCorner1);
		this.BlockBase.addChild(this.Core1);
		this.PortalFramebase.addChild(this.PortalVortex);
		this.BlockBase.addChild(this.ShardBack);
		this.BlockBase.addChild(this.WingRightBase);
		this.PortalFramebase.addChild(this.PortalFrameLeft);
		this.PortalFramebase.addChild(this.PortalCorner4);
		this.BlockBase.addChild(this.ShardFront3);
		this.BlockBase.addChild(this.ShardRight5);
		this.WingLeftArm.addChild(this.WingLeftFeather3);
		this.Core1.addChild(this.Core4);
		this.BlockBase.addChild(this.ShardLeft4);
		this.BlockBase.addChild(this.ShardRight2);
		this.BlockBase.addChild(this.ShardLeft1);
		this.BlockBase.addChild(this.ShardFront2);
		this.BlockBase.addChild(this.PedestalPlatform);
		this.PedestalPlatform.addChild(this.PedestalFrameHolder);
		this.WingRightArm.addChild(this.WingRightFeather2);
		this.WingLeftArm.addChild(this.WingLeftFeather2);
		this.BlockBase.addChild(this.PortalFramebase);
		this.BlockBase.addChild(this.WingLeftBase);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.render(f5, 0);
	}

	/*

	public ModelRenderer BlockBase;
	public ModelRenderer ShardFront1, ShardLeft1,  ShardLeft2, ShardLeft3, ShardLeft4, ShardLeft5;
	public ModelRenderer ShardBack,  ShardRight1,  ShardFront2, ShardFront3;
	public ModelRenderer ShardRight2,  ShardRight3,  ShardRight4, ShardRight5;
	public ModelRenderer PedestalPlatform, PedestalBase,  PortalFramebase,  PedestalFrameHolder;
	public ModelRenderer Core1, Core2, Core3, Core4;
	public ModelRenderer WingRightBase,  WingLeftBase;
	public ModelRenderer WingRightArm,  WingRightFeather1, WingRightFeather2, WingRightFeather3;
	public ModelRenderer WingLeftArm, WingLeftFeather1, WingLeftFeather2, WingLeftFeather3;
	public ModelRenderer PortalFrameRight, PortalFrameLeft, PortalFrameTop;
	public ModelRenderer PortalCorner1, PortalCorner2, PortalCorner3, PortalCorner4, PortalVortex;

	 */

	public void render(float scale, int progress)
	{
		final double degtorad = 180 / Math.PI;

		this.PortalFramebase.offsetZ = -.1f;
		this.PortalFramebase.offsetY = -.1f + (float) Math.cos(progress / 10f) / 10f;
		this.PortalFramebase.rotateAngleX = 1.5f;

		/*if (progress > 100)
		{
			WingRightBase.rotateAngleZ = (progress - 100) * (-3.14f / 100) - 3.14f;
			WingLeftBase.rotateAngleZ = (progress - 100) * (3.14f / 100) - 3.14f;

			if (progress < 150)
			{
				WingRightFeather1.rotateAngleZ = (progress % 50) * (-1.436f / 50f) + 1;
				WingLeftFeather1.rotateAngleZ = (progress % 50) * (1.436f / 50f) - 1;
			}
			else
			{
				WingRightFeather1.rotateAngleZ = -0.436F;
				WingLeftFeather1.rotateAngleZ = 0.436F;
			}
		}
		else
		{
			WingRightBase.rotateAngleZ = 3.14f;
			WingLeftBase.rotateAngleZ = 3.14f;
			WingRightFeather1.rotateAngleZ = 1f;
			WingLeftFeather1.rotateAngleZ = 1f;
		}*/

		this.BlockBase.render(scale);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
