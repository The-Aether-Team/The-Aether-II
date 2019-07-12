package com.gildedgames.aether.client.models.entities.companions;

import com.gildedgames.aether.common.entities.companions.EntityNexSpirit;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;

public class ModelNexSpirit extends EntityModel<EntityNexSpirit>
{
	private final RendererModel Seal_Outer_Bottom;

	private final RendererModel Seal_Outer_Left_Bottom;

	private final RendererModel Seal_Outer_Right_Bottom;

	private final RendererModel Seal_Outer_Left_Top;

	private final RendererModel Seal_Outer_Right_Top;

	private final RendererModel Seal_Ridge_Top;

	private final RendererModel Seal_Ridge_Right_1;

	private final RendererModel Seal_Ridge_Right_2;

	private final RendererModel Seal_Ridge_Right_3;

	private final RendererModel Seal_Ridge_Right_4;

	private final RendererModel Seal_Ridge_Left_1;

	private final RendererModel Seal_Ridge_Left_2;

	private final RendererModel Seal_Ridge_Left_3;

	private final RendererModel Seal_Ridge_Left_4;

	private final RendererModel Seal_Ridge_Bottom;

	private final RendererModel Seal_Base;

	private final RendererModel Skull;

	private final RendererModel Chest;

	private final RendererModel Neck;

	private final RendererModel Head;

	private final RendererModel Mask;

	private final RendererModel Back;

	private final RendererModel Stub;

	private final RendererModel Right_Arm;

	private final RendererModel Left_Arm;

	private final RendererModel Shoulder_Left;

	private final RendererModel Shoulder_Right;

	private final RendererModel Hand_Left;

	private final RendererModel Hand_Right;

	public ModelNexSpirit()
	{
		this.textureWidth = 128;
		this.textureHeight = 128;

		this.Seal_Outer_Bottom = new RendererModel(this, 43, 51);
		this.Seal_Outer_Bottom.addBox(-3F, 7F, 0F, 6, 2, 3);
		this.Seal_Outer_Bottom.setRotationPoint(0F, 0F, 0F);
		this.Seal_Outer_Bottom.setTextureSize(128, 128);
		this.Seal_Outer_Bottom.mirror = true;
		this.setRotation(this.Seal_Outer_Bottom, 0F, 0F, 0F);

		this.Seal_Outer_Left_Bottom = new RendererModel(this, 24, 51);
		this.Seal_Outer_Left_Bottom.addBox(-3F, 7F, 0F, 6, 2, 3);
		this.Seal_Outer_Left_Bottom.setRotationPoint(0F, 0F, 0F);
		this.Seal_Outer_Left_Bottom.setTextureSize(128, 128);
		this.Seal_Outer_Left_Bottom.mirror = true;
		this.setRotation(this.Seal_Outer_Left_Bottom, 0F, 0F, 1.22173F);

		this.Seal_Outer_Right_Bottom = new RendererModel(this, 62, 51);
		this.Seal_Outer_Right_Bottom.addBox(-3F, 7F, 0F, 6, 2, 3);
		this.Seal_Outer_Right_Bottom.setRotationPoint(0F, 0F, 0F);
		this.Seal_Outer_Right_Bottom.setTextureSize(128, 128);
		this.Seal_Outer_Right_Bottom.mirror = true;
		this.setRotation(this.Seal_Outer_Right_Bottom, 0F, 0F, -1.22173F);

		this.Seal_Outer_Left_Top = new RendererModel(this, 5, 51);
		this.Seal_Outer_Left_Top.addBox(-3F, 7F, 0F, 6, 2, 3);
		this.Seal_Outer_Left_Top.setRotationPoint(0F, 0F, 0F);
		this.Seal_Outer_Left_Top.setTextureSize(128, 128);
		this.Seal_Outer_Left_Top.mirror = true;
		this.setRotation(this.Seal_Outer_Left_Top, 0F, 0F, 2.443461F);

		this.Seal_Outer_Right_Top = new RendererModel(this, 81, 51);
		this.Seal_Outer_Right_Top.addBox(-3F, 7F, 0F, 6, 2, 3);
		this.Seal_Outer_Right_Top.setRotationPoint(0F, 0F, 0F);
		this.Seal_Outer_Right_Top.setTextureSize(128, 128);
		this.Seal_Outer_Right_Top.mirror = true;
		this.setRotation(this.Seal_Outer_Right_Top, 0F, 0F, -2.443461F);

		this.Seal_Ridge_Top = new RendererModel(this, 44, 17);
		this.Seal_Ridge_Top.addBox(-3F, -8F, 0.5F, 6, 2, 2);
		this.Seal_Ridge_Top.setRotationPoint(0F, 0F, 0F);
		this.Seal_Ridge_Top.setTextureSize(128, 128);
		this.Seal_Ridge_Top.mirror = true;
		this.setRotation(this.Seal_Ridge_Top, 0F, 0F, 0F);

		this.Seal_Ridge_Right_1 = new RendererModel(this, 67, 22);
		this.Seal_Ridge_Right_1.addBox(-3F, 6F, 0.5F, 6, 2, 2);
		this.Seal_Ridge_Right_1.setRotationPoint(0F, 0F, 0F);
		this.Seal_Ridge_Right_1.setTextureSize(128, 128);
		this.Seal_Ridge_Right_1.mirror = true;
		this.setRotation(this.Seal_Ridge_Right_1, 0F, 0F, -2.443461F);

		this.Seal_Ridge_Right_2 = new RendererModel(this, 67, 27);
		this.Seal_Ridge_Right_2.addBox(-4F, 6F, 0.5F, 8, 2, 2);
		this.Seal_Ridge_Right_2.setRotationPoint(0F, 0F, 0F);
		this.Seal_Ridge_Right_2.setTextureSize(128, 128);
		this.Seal_Ridge_Right_2.mirror = true;
		this.setRotation(this.Seal_Ridge_Right_2, 0F, 0F, -1.832596F);

		this.Seal_Ridge_Right_3 = new RendererModel(this, 67, 32);
		this.Seal_Ridge_Right_3.addBox(-3F, 6F, 0.5F, 6, 2, 2);
		this.Seal_Ridge_Right_3.setRotationPoint(0F, 0F, 0F);
		this.Seal_Ridge_Right_3.setTextureSize(128, 128);
		this.Seal_Ridge_Right_3.mirror = true;
		this.setRotation(this.Seal_Ridge_Right_3, 0F, 0F, -1.22173F);

		this.Seal_Ridge_Right_4 = new RendererModel(this, 67, 37);
		this.Seal_Ridge_Right_4.addBox(-4F, 6F, 0.5F, 8, 2, 2);
		this.Seal_Ridge_Right_4.setRotationPoint(0F, 0F, 0F);
		this.Seal_Ridge_Right_4.setTextureSize(128, 128);
		this.Seal_Ridge_Right_4.mirror = true;
		this.setRotation(this.Seal_Ridge_Right_4, 0F, 0F, -0.6108652F);

		this.Seal_Ridge_Left_1 = new RendererModel(this, 21, 22);
		this.Seal_Ridge_Left_1.addBox(-3F, 6F, 0.5F, 6, 2, 2);
		this.Seal_Ridge_Left_1.setRotationPoint(0F, 0F, 0F);
		this.Seal_Ridge_Left_1.setTextureSize(128, 128);
		this.Seal_Ridge_Left_1.mirror = true;
		this.setRotation(this.Seal_Ridge_Left_1, 0F, 0F, 2.443461F);

		this.Seal_Ridge_Left_2 = new RendererModel(this, 17, 27);
		this.Seal_Ridge_Left_2.addBox(-4F, 6F, 0.5F, 8, 2, 2);
		this.Seal_Ridge_Left_2.setRotationPoint(0F, 0F, 0F);
		this.Seal_Ridge_Left_2.setTextureSize(128, 128);
		this.Seal_Ridge_Left_2.mirror = true;
		this.setRotation(this.Seal_Ridge_Left_2, 0F, 0F, 1.832596F);

		this.Seal_Ridge_Left_3 = new RendererModel(this, 21, 32);
		this.Seal_Ridge_Left_3.addBox(-3F, 6F, 0.5F, 6, 2, 2);
		this.Seal_Ridge_Left_3.setRotationPoint(0F, 0F, 0F);
		this.Seal_Ridge_Left_3.setTextureSize(128, 128);
		this.Seal_Ridge_Left_3.mirror = true;
		this.setRotation(this.Seal_Ridge_Left_3, 0F, 0F, 1.22173F);

		this.Seal_Ridge_Left_4 = new RendererModel(this, 17, 37);
		this.Seal_Ridge_Left_4.addBox(-4F, 6F, 0.5F, 8, 2, 2);
		this.Seal_Ridge_Left_4.setRotationPoint(0F, 0F, 0F);
		this.Seal_Ridge_Left_4.setTextureSize(128, 128);
		this.Seal_Ridge_Left_4.mirror = true;
		this.setRotation(this.Seal_Ridge_Left_4, 0F, 0F, 0.6108652F);

		this.Seal_Ridge_Bottom = new RendererModel(this, 44, 46);
		this.Seal_Ridge_Bottom.addBox(-3F, 6F, 0.5F, 6, 2, 2);
		this.Seal_Ridge_Bottom.setRotationPoint(0F, 0F, 0F);
		this.Seal_Ridge_Bottom.setTextureSize(128, 128);
		this.Seal_Ridge_Bottom.mirror = true;
		this.setRotation(this.Seal_Ridge_Bottom, 0F, 0F, 0F);

		this.Seal_Base = new RendererModel(this, 38, 22);
		this.Seal_Base.addBox(-6.5F, -6F, 1F, 13, 12, 1);
		this.Seal_Base.setRotationPoint(0F, 0F, 0F);
		this.Seal_Base.setTextureSize(128, 128);
		this.Seal_Base.mirror = true;
		this.setRotation(this.Seal_Base, 0F, 0F, 0F);

		this.Skull = new RendererModel(this, 43, 36);
		this.Skull.addBox(-4F, -4F, 0.5F, 8, 8, 1);
		this.Skull.setRotationPoint(0F, 0F, 0F);
		this.Skull.setTextureSize(128, 128);
		this.Skull.mirror = true;
		this.setRotation(this.Skull, 0F, 0F, 0F);

		this.Chest = new RendererModel(this, 34, 90);
		this.Chest.addBox(-6F, -9F, 4F, 12, 10, 6);
		this.Chest.setRotationPoint(0F, 0F, 0F);
		this.Chest.setTextureSize(128, 128);
		this.Chest.mirror = true;
		this.setRotation(this.Chest, 0F, 0F, 0F);

		this.Neck = new RendererModel(this, 38, 74);
		this.Neck.addBox(-4F, -8F, 8F, 8, 9, 6);
		this.Neck.setRotationPoint(0F, 0F, 0F);
		this.Neck.setTextureSize(128, 128);
		this.Neck.mirror = true;
		this.setRotation(this.Neck, 0.6320364F, 0F, 0F);

		this.Head = new RendererModel(this, 24, 59);
		this.Head.addBox(-3.5F, -16.5F, -0.5F, 7, 7, 7);
		this.Head.setRotationPoint(0F, 0F, 0F);
		this.Head.setTextureSize(128, 128);
		this.Head.mirror = true;
		this.setRotation(this.Head, 0F, 0F, 0F);

		this.Mask = new RendererModel(this, 53, 66);
		this.Mask.addBox(-3F, -16F, -1F, 6, 6, 1);
		this.Mask.setRotationPoint(0F, 0F, 0F);
		this.Mask.setTextureSize(128, 128);
		this.Mask.mirror = true;
		this.setRotation(this.Mask, 0F, 0F, 0F);

		this.Back = new RendererModel(this, 27, 107);
		this.Back.addBox(-3.5F, -9F, 5.5F, 7, 8, 5);
		this.Back.setRotationPoint(0F, 0F, 0F);
		this.Back.setTextureSize(128, 128);
		this.Back.mirror = true;
		this.setRotation(this.Back, -0.1570796F, 0F, 0F);

		this.Stub = new RendererModel(this, 53, 107);
		this.Stub.addBox(-2.5F, -2.5F, 5F, 7, 7, 4);
		this.Stub.setRotationPoint(0F, 0F, 0F);
		this.Stub.setTextureSize(128, 128);
		this.Stub.mirror = true;
		this.setRotation(this.Stub, 0F, 0F, 0.7853982F);

		this.Right_Arm = new RendererModel(this, 77, 83);
		this.Right_Arm.addBox(1F, -8F, 6F, 6, 6, 5);
		this.Right_Arm.setRotationPoint(0F, 0F, 0F);
		this.Right_Arm.setTextureSize(128, 128);
		this.Right_Arm.mirror = true;
		this.setRotation(this.Right_Arm, 0F, 0.4363323F, 0F);

		this.Left_Arm = new RendererModel(this, 5, 83);
		this.Left_Arm.addBox(-7F, -8F, 6F, 6, 6, 5);
		this.Left_Arm.setRotationPoint(0F, 0F, 0F);
		this.Left_Arm.setTextureSize(128, 128);
		this.Left_Arm.mirror = true;
		this.setRotation(this.Left_Arm, 0F, -0.4363323F, 0F);

		this.Shoulder_Left = new RendererModel(this, 11, 74);
		this.Shoulder_Left.addBox(-9.5F, -10F, 5F, 5, 5, 3);
		this.Shoulder_Left.setRotationPoint(0F, 0F, 0F);
		this.Shoulder_Left.setTextureSize(128, 128);
		this.Shoulder_Left.mirror = true;
		this.setRotation(this.Shoulder_Left, 0F, 0F, 0.5235988F);

		this.Shoulder_Right = new RendererModel(this, 77, 74);
		this.Shoulder_Right.addBox(4.5F, -10F, 5F, 5, 5, 3);
		this.Shoulder_Right.setRotationPoint(0F, 0F, 0F);
		this.Shoulder_Right.setTextureSize(128, 128);
		this.Shoulder_Right.mirror = true;
		this.setRotation(this.Shoulder_Right, 0F, 0F, -0.5235988F);

		this.Hand_Left = new RendererModel(this, 5, 95);
		this.Hand_Left.addBox(-11F, -5F, 0F, 4, 4, 7);
		this.Hand_Left.setRotationPoint(0F, 0F, 0F);
		this.Hand_Left.setTextureSize(128, 128);
		this.Hand_Left.mirror = true;
		this.setRotation(this.Hand_Left, 0.1745329F, 0F, 0F);

		this.Hand_Right = new RendererModel(this, 77, 95);
		this.Hand_Right.addBox(7F, -5F, 0F, 4, 4, 7);
		this.Hand_Right.setRotationPoint(0F, 0F, 0F);
		this.Hand_Right.setTextureSize(128, 128);
		this.Hand_Right.mirror = true;
		this.setRotation(this.Hand_Right, 0.1745329F, 0F, 0F);
	}

	@Override
	public void render(EntityNexSpirit entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(entity, f, f1, f2, f3, f4, f5);
		this.Seal_Outer_Bottom.render(f5);
		this.Seal_Outer_Left_Bottom.render(f5);
		this.Seal_Outer_Right_Bottom.render(f5);
		this.Seal_Outer_Left_Top.render(f5);
		this.Seal_Outer_Right_Top.render(f5);
		this.Seal_Ridge_Top.render(f5);
		this.Seal_Ridge_Right_1.render(f5);
		this.Seal_Ridge_Right_2.render(f5);
		this.Seal_Ridge_Right_3.render(f5);
		this.Seal_Ridge_Right_4.render(f5);
		this.Seal_Ridge_Left_1.render(f5);
		this.Seal_Ridge_Left_2.render(f5);
		this.Seal_Ridge_Left_3.render(f5);
		this.Seal_Ridge_Left_4.render(f5);
		this.Seal_Ridge_Bottom.render(f5);
		this.Seal_Base.render(f5);
		this.Skull.render(f5);
		this.Chest.render(f5);
		this.Neck.render(f5);
		this.Head.render(f5);
		this.Mask.render(f5);
		this.Back.render(f5);
		//Stub.render(f5);
		this.Right_Arm.render(f5);
		this.Left_Arm.render(f5);
		this.Shoulder_Left.render(f5);
		this.Shoulder_Right.render(f5);
		this.Hand_Left.render(f5);
		this.Hand_Right.render(f5);
	}

	private void setRotation(RendererModel model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
