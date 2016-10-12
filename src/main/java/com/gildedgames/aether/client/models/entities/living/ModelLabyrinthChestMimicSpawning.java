package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLabyrinthChestMimicSpawning extends ModelBase
{

	ModelRenderer Main_Top;

	ModelRenderer Main_Bottom;

	ModelRenderer Hinge_Left;

	ModelRenderer Hinge_Right;

	ModelRenderer Mouth_Back;

	ModelRenderer Mouth_Side_Right;

	ModelRenderer Mouth_Side_Left;

	ModelRenderer Corner_Bottom_Front_Right;

	ModelRenderer Corner_Bottom_Front_Left;

	ModelRenderer Corner_Bottom_Back_Left;

	ModelRenderer Corner_Bottom_Back_Right;

	ModelRenderer Corner_Top_Back_Left;

	ModelRenderer Corner_Top_Back_Right;

	ModelRenderer Corner_Top_Front_Left;

	ModelRenderer Corner_Top_Front_Right;

	ModelRenderer Leg_Joint_Front_Left;

	ModelRenderer Leg_Joint_Front_Right;

	ModelRenderer Leg_Joint_Back_Left;

	ModelRenderer Leg_Joint_Back_Right;

	ModelRenderer Leg_Front_Right;

	ModelRenderer Leg_Front_Left;

	ModelRenderer Leg_Back_Right;

	ModelRenderer Leg_Back_Left;

	ModelRenderer Leg_2_Front_Left;

	ModelRenderer Leg_2_Front_Right;

	ModelRenderer Leg_2_Back_Left;

	ModelRenderer Leg_2_Back_Right;

	ModelRenderer Foot_Front_Left;

	ModelRenderer Foot_Front_Right;

	ModelRenderer Foot_Back_Right;

	ModelRenderer Foot_Back_Left;

	public ModelLabyrinthChestMimicSpawning()
	{
		this.textureWidth = 64;
		this.textureHeight = 128;

		this.Main_Top = new ModelRenderer(this, 4, 0);
		this.Main_Top.addBox(-7F, -7F, -14F, 14, 7, 14);
		this.Main_Top.setRotationPoint(0F, 0F, 7F);
		this.Main_Top.setTextureSize(64, 128);
		this.Main_Top.mirror = true;
		this.setRotation(this.Main_Top, 0F, 0F, 0F);
		this.Main_Bottom = new ModelRenderer(this, 4, 56);
		this.Main_Bottom.addBox(-7F, 0F, -7F, 14, 7, 14);
		this.Main_Bottom.setRotationPoint(0F, 0F, 0F);
		this.Main_Bottom.setTextureSize(64, 128);
		this.Main_Bottom.mirror = true;
		this.setRotation(this.Main_Bottom, 0F, 0F, 0F);
		this.Hinge_Left = new ModelRenderer(this, 26, 30);
		this.Hinge_Left.addBox(1.5F, -1F, -1.5F, 4, 2, 2);
		this.Hinge_Left.setRotationPoint(0F, 0F, 7F);
		this.Hinge_Left.setTextureSize(64, 128);
		this.Hinge_Left.mirror = true;
		this.setRotation(this.Hinge_Left, 0F, 0F, 0F);
		this.Hinge_Right = new ModelRenderer(this, 26, 30);
		this.Hinge_Right.addBox(-5.5F, -1F, -1.5F, 4, 2, 2);
		this.Hinge_Right.setRotationPoint(0F, 0F, 7F);
		this.Hinge_Right.setTextureSize(64, 128);
		this.Hinge_Right.mirror = true;
		this.setRotation(this.Hinge_Right, 0F, 0F, 0F);
		this.Mouth_Back = new ModelRenderer(this, 21, 34);
		this.Mouth_Back.addBox(-5F, -2F, 5F, 10, 3, 1);
		this.Mouth_Back.setRotationPoint(0F, 0F, 0F);
		this.Mouth_Back.setTextureSize(64, 128);
		this.Mouth_Back.mirror = true;
		this.setRotation(this.Mouth_Back, 0F, 0F, 0F);
		this.Mouth_Side_Right = new ModelRenderer(this, 12, 28);
		this.Mouth_Side_Right.addBox(-5F, -6F, -5F, 0, 6, 10);
		this.Mouth_Side_Right.setRotationPoint(0F, 0F, 0F);
		this.Mouth_Side_Right.setTextureSize(64, 128);
		this.Mouth_Side_Right.mirror = true;
		this.setRotation(this.Mouth_Side_Right, 0F, 0.1745329F, 0F);
		this.Mouth_Side_Left = new ModelRenderer(this, 32, 28);
		this.Mouth_Side_Left.addBox(5F, -6F, -5F, 0, 6, 10);
		this.Mouth_Side_Left.setRotationPoint(0F, 0F, 0F);
		this.Mouth_Side_Left.setTextureSize(64, 128);
		this.Mouth_Side_Left.mirror = true;
		this.setRotation(this.Mouth_Side_Left, 0F, -0.1745329F, 0F);
		this.Corner_Bottom_Front_Right = new ModelRenderer(this, 4, 44);
		this.Corner_Bottom_Front_Right.addBox(-1F, 1F, -10F, 2, 7, 5);
		this.Corner_Bottom_Front_Right.setRotationPoint(0F, 0F, 0F);
		this.Corner_Bottom_Front_Right.setTextureSize(64, 128);
		this.Corner_Bottom_Front_Right.mirror = true;
		this.setRotation(this.Corner_Bottom_Front_Right, 0F, 0.7853982F, 0F);
		this.Corner_Bottom_Front_Left = new ModelRenderer(this, 46, 44);
		this.Corner_Bottom_Front_Left.addBox(-1F, 1F, -10F, 2, 7, 5);
		this.Corner_Bottom_Front_Left.setRotationPoint(0F, 0F, 0F);
		this.Corner_Bottom_Front_Left.setTextureSize(64, 128);
		this.Corner_Bottom_Front_Left.mirror = true;
		this.setRotation(this.Corner_Bottom_Front_Left, 0F, -0.7853982F, 0F);
		this.Corner_Bottom_Back_Left = new ModelRenderer(this, 46, 44);
		this.Corner_Bottom_Back_Left.addBox(-1F, 1F, 5F, 2, 7, 5);
		this.Corner_Bottom_Back_Left.setRotationPoint(0F, 0F, 0F);
		this.Corner_Bottom_Back_Left.setTextureSize(64, 128);
		this.Corner_Bottom_Back_Left.mirror = true;
		this.setRotation(this.Corner_Bottom_Back_Left, 0F, 0.7853982F, 0F);
		this.Corner_Bottom_Back_Right = new ModelRenderer(this, 4, 44);
		this.Corner_Bottom_Back_Right.addBox(-1F, 1F, 5F, 2, 7, 5);
		this.Corner_Bottom_Back_Right.setRotationPoint(0F, 0F, 0F);
		this.Corner_Bottom_Back_Right.setTextureSize(64, 128);
		this.Corner_Bottom_Back_Right.mirror = true;
		this.setRotation(this.Corner_Bottom_Back_Right, 0F, -0.7853982F, 0F);
		this.Corner_Top_Back_Left = new ModelRenderer(this, 44, 21);
		this.Corner_Top_Back_Left.addBox(4F, -8F, -0.4F, 2, 7, 6);
		this.Corner_Top_Back_Left.setRotationPoint(0F, 0F, 7F);
		this.Corner_Top_Back_Left.setTextureSize(64, 128);
		this.Corner_Top_Back_Left.mirror = true;
		this.setRotation(this.Corner_Top_Back_Left, 0F, 0.7853982F, 0F);
		this.Corner_Top_Back_Right = new ModelRenderer(this, 4, 21);
		this.Corner_Top_Back_Right.addBox(-5.5F, -8F, -0.6F, 2, 7, 6);
		this.Corner_Top_Back_Right.setRotationPoint(0F, 0F, 7F);
		this.Corner_Top_Back_Right.setTextureSize(64, 128);
		this.Corner_Top_Back_Right.mirror = true;
		this.setRotation(this.Corner_Top_Back_Right, 0F, -0.7853982F, 0F);
		this.Corner_Top_Front_Left = new ModelRenderer(this, 44, 21);
		this.Corner_Top_Front_Left.addBox(-6F, -8F, -15.3F, 2, 7, 6);
		this.Corner_Top_Front_Left.setRotationPoint(0F, 0F, 7F);
		this.Corner_Top_Front_Left.setTextureSize(64, 128);
		this.Corner_Top_Front_Left.mirror = true;
		this.setRotation(this.Corner_Top_Front_Left, 0F, -0.7853982F, 0F);
		this.Corner_Top_Front_Right = new ModelRenderer(this, 4, 21);
		this.Corner_Top_Front_Right.addBox(4F, -8F, -15.3F, 2, 7, 6);
		this.Corner_Top_Front_Right.setRotationPoint(0F, 0F, 7F);
		this.Corner_Top_Front_Right.setTextureSize(64, 128);
		this.Corner_Top_Front_Right.mirror = true;
		this.setRotation(this.Corner_Top_Front_Right, 0F, 0.7853982F, 0F);
		this.Leg_Joint_Front_Left = new ModelRenderer(this, 38, 77);
		this.Leg_Joint_Front_Left.addBox(-1F, -3F, -1.5F, 8, 5, 3);
		this.Leg_Joint_Front_Left.setRotationPoint(3F, 7F, -3F);
		this.Leg_Joint_Front_Left.setTextureSize(64, 128);
		this.Leg_Joint_Front_Left.mirror = true;
		this.setRotation(this.Leg_Joint_Front_Left, 0F, 0.7853982F, 1.22173F);
		this.Leg_Joint_Front_Right = new ModelRenderer(this, 4, 77);
		this.Leg_Joint_Front_Right.addBox(-7F, -3F, -1.5F, 8, 5, 3);
		this.Leg_Joint_Front_Right.setRotationPoint(-3F, 7F, -3F);
		this.Leg_Joint_Front_Right.setTextureSize(64, 128);
		this.Leg_Joint_Front_Right.mirror = true;
		this.setRotation(this.Leg_Joint_Front_Right, 0F, -0.7853982F, -1.22173F);
		this.Leg_Joint_Back_Left = new ModelRenderer(this, 38, 77);
		this.Leg_Joint_Back_Left.addBox(-1F, -3F, -1.5F, 8, 5, 3);
		this.Leg_Joint_Back_Left.setRotationPoint(3F, 7F, 3F);
		this.Leg_Joint_Back_Left.setTextureSize(64, 128);
		this.Leg_Joint_Back_Left.mirror = true;
		this.setRotation(this.Leg_Joint_Back_Left, 0F, -0.7853982F, 1.22173F);
		this.Leg_Joint_Back_Right = new ModelRenderer(this, 4, 77);
		this.Leg_Joint_Back_Right.addBox(-7F, -3F, -1.5F, 8, 5, 3);
		this.Leg_Joint_Back_Right.setRotationPoint(-3F, 7F, 3F);
		this.Leg_Joint_Back_Right.setTextureSize(64, 128);
		this.Leg_Joint_Back_Right.mirror = true;
		this.setRotation(this.Leg_Joint_Back_Right, 0F, 0.7853982F, -1.22173F);
		this.Leg_Front_Right = new ModelRenderer(this, 5, 85);
		this.Leg_Front_Right.addBox(-1F, -5.5F, -12F, 2, 2, 8);
		this.Leg_Front_Right.setRotationPoint(-3F, 7F, -3F);
		this.Leg_Front_Right.setTextureSize(64, 128);
		this.Leg_Front_Right.mirror = true;
		this.setRotation(this.Leg_Front_Right, 1.919862F, 0.7853982F, 0F);
		this.Leg_Front_Left = new ModelRenderer(this, 39, 85);
		this.Leg_Front_Left.addBox(-1F, -5.5F, -12F, 2, 2, 8);
		this.Leg_Front_Left.setRotationPoint(3F, 7F, -3F);
		this.Leg_Front_Left.setTextureSize(64, 128);
		this.Leg_Front_Left.mirror = true;
		this.setRotation(this.Leg_Front_Left, 1.919862F, -0.7853982F, 0F);
		this.Leg_Back_Right = new ModelRenderer(this, 5, 85);
		this.Leg_Back_Right.addBox(-1F, -5.5F, 4F, 2, 2, 8);
		this.Leg_Back_Right.setRotationPoint(-3F, 7F, 3F);
		this.Leg_Back_Right.setTextureSize(64, 128);
		this.Leg_Back_Right.mirror = true;
		this.setRotation(this.Leg_Back_Right, -1.919862F, -0.7853982F, 0F);
		this.Leg_Back_Left = new ModelRenderer(this, 39, 85);
		this.Leg_Back_Left.addBox(-1F, -5.5F, 4F, 2, 2, 8);
		this.Leg_Back_Left.setRotationPoint(3F, 7F, 3F);
		this.Leg_Back_Left.setTextureSize(64, 128);
		this.Leg_Back_Left.mirror = true;
		this.setRotation(this.Leg_Back_Left, -1.919862F, 0.7853982F, 0F);
		this.Leg_2_Front_Left = new ModelRenderer(this, 39, 95);
		this.Leg_2_Front_Left.addBox(-11.5F, -9.5F, -1.5F, 7, 3, 3);
		this.Leg_2_Front_Left.setRotationPoint(3F, 7F, -3F);
		this.Leg_2_Front_Left.setTextureSize(64, 128);
		this.Leg_2_Front_Left.mirror = true;
		this.setRotation(this.Leg_2_Front_Left, 0F, 0.7853982F, -2.268928F);
		this.Leg_2_Front_Right = new ModelRenderer(this, 5, 95);
		this.Leg_2_Front_Right.addBox(4.5F, -9.5F, -1.5F, 7, 3, 3);
		this.Leg_2_Front_Right.setRotationPoint(-3F, 7F, -3F);
		this.Leg_2_Front_Right.setTextureSize(64, 128);
		this.Leg_2_Front_Right.mirror = true;
		this.setRotation(this.Leg_2_Front_Right, 0F, -0.7853982F, 2.268928F);
		this.Leg_2_Back_Left = new ModelRenderer(this, 39, 95);
		this.Leg_2_Back_Left.addBox(-11.5F, -9.5F, -1.5F, 7, 3, 3);
		this.Leg_2_Back_Left.setRotationPoint(3F, 7F, 3F);
		this.Leg_2_Back_Left.setTextureSize(64, 128);
		this.Leg_2_Back_Left.mirror = true;
		this.setRotation(this.Leg_2_Back_Left, 0F, -0.7853982F, -2.268928F);
		this.Leg_2_Back_Right = new ModelRenderer(this, 5, 95);
		this.Leg_2_Back_Right.addBox(4.5F, -9.5F, -1.5F, 7, 3, 3);
		this.Leg_2_Back_Right.setRotationPoint(-3F, 7F, 3F);
		this.Leg_2_Back_Right.setTextureSize(64, 128);
		this.Leg_2_Back_Right.mirror = true;
		this.setRotation(this.Leg_2_Back_Right, 0F, 0.7853982F, 2.268928F);
		this.Foot_Front_Left = new ModelRenderer(this, 46, 101);
		this.Foot_Front_Left.addBox(-6F, -8F, -0.5F, 2, 3, 1);
		this.Foot_Front_Left.setRotationPoint(3F, 7F, -3F);
		this.Foot_Front_Left.setTextureSize(64, 128);
		this.Foot_Front_Left.mirror = true;
		this.setRotation(this.Foot_Front_Left, 0F, 0.7853982F, -2.268928F);
		this.Foot_Front_Right = new ModelRenderer(this, 12, 101);
		this.Foot_Front_Right.addBox(4F, -8F, -0.5F, 2, 3, 1);
		this.Foot_Front_Right.setRotationPoint(-3F, 7F, -3F);
		this.Foot_Front_Right.setTextureSize(64, 128);
		this.Foot_Front_Right.mirror = true;
		this.setRotation(this.Foot_Front_Right, 0F, -0.7853982F, 2.268928F);
		this.Foot_Back_Right = new ModelRenderer(this, 12, 101);
		this.Foot_Back_Right.addBox(4F, -8F, -0.5F, 2, 3, 1);
		this.Foot_Back_Right.setRotationPoint(-3F, 7F, 3F);
		this.Foot_Back_Right.setTextureSize(64, 128);
		this.Foot_Back_Right.mirror = true;
		this.setRotation(this.Foot_Back_Right, 0F, 0.7853982F, 2.268928F);
		this.Foot_Back_Left = new ModelRenderer(this, 46, 101);
		this.Foot_Back_Left.addBox(-6F, -8F, -0.5F, 2, 3, 1);
		this.Foot_Back_Left.setRotationPoint(3F, 7F, 3F);
		this.Foot_Back_Left.setTextureSize(64, 128);
		this.Foot_Back_Left.mirror = true;
		this.setRotation(this.Foot_Back_Left, 0F, -0.7853982F, -2.268928F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.Main_Top.render(f5);
		this.Main_Bottom.render(f5);
		this.Hinge_Left.render(f5);
		this.Hinge_Right.render(f5);
		this.Mouth_Back.render(f5);
		this.Mouth_Side_Right.render(f5);
		this.Mouth_Side_Left.render(f5);
		this.Corner_Bottom_Front_Right.render(f5);
		this.Corner_Bottom_Front_Left.render(f5);
		this.Corner_Bottom_Back_Left.render(f5);
		this.Corner_Bottom_Back_Right.render(f5);
		this.Corner_Top_Back_Left.render(f5);
		this.Corner_Top_Back_Right.render(f5);
		this.Corner_Top_Front_Left.render(f5);
		this.Corner_Top_Front_Right.render(f5);
		this.Leg_Joint_Front_Left.render(f5);
		this.Leg_Joint_Front_Right.render(f5);
		this.Leg_Joint_Back_Left.render(f5);
		this.Leg_Joint_Back_Right.render(f5);
		this.Leg_Front_Right.render(f5);
		this.Leg_Front_Left.render(f5);
		this.Leg_Back_Right.render(f5);
		this.Leg_Back_Left.render(f5);
		this.Leg_2_Front_Left.render(f5);
		this.Leg_2_Front_Right.render(f5);
		this.Leg_2_Back_Left.render(f5);
		this.Leg_2_Back_Right.render(f5);
		this.Foot_Front_Left.render(f5);
		this.Foot_Front_Right.render(f5);
		this.Foot_Back_Right.render(f5);
		this.Foot_Back_Left.render(f5);
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
