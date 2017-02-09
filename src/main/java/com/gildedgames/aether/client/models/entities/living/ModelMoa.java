package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelMoa extends ModelBase
{
	public ModelRenderer Claw1Left;

	public ModelRenderer Claw2Left;

	public ModelRenderer Claw3Right;

	public ModelRenderer Neck2;

	public ModelRenderer Neck1;

	public ModelRenderer Claw1Right;

	public ModelRenderer RightWing;

	public ModelRenderer Claw2Right;

	public ModelRenderer LeftLeg1;

	public ModelRenderer Claw3Left;

	public ModelRenderer RightThigh;

	public ModelRenderer Jaw;

	public ModelRenderer Toe2Right;

	public ModelRenderer TailLeft;

	public ModelRenderer Chest;

	public ModelRenderer Teeth;

	public ModelRenderer RightLeg1;

	public ModelRenderer FootRight;

	public ModelRenderer Toe1Right;

	public ModelRenderer LeftThigh;

	public ModelRenderer LeftLeg2;

	public ModelRenderer Toe1Left;

	public ModelRenderer FootLeft;

	public ModelRenderer Toe2Left;

	public ModelRenderer LeftWing;

	public ModelRenderer RightLeg2;

	public ModelRenderer Body;

	public ModelRenderer TailMiddle;

	public ModelRenderer TailRight;

	public ModelRenderer Head;

	public ModelRenderer feather4;

	public ModelRenderer feather3;

	public ModelRenderer feather2;

	public ModelRenderer feather1;

	public ModelMoa()
	{
		this.textureWidth = 128;
		this.textureHeight = 128;

		this.Claw1Left = new ModelRenderer(this, 13, 41);
		this.Claw1Left.addBox(0.6F, 15F, -4.9F, 0, 1, 1);
		this.Claw1Left.setRotationPoint(2.1F, 8F, 1F);
		this.Claw1Left.setTextureSize(128, 128);
		this.Claw1Left.mirror = true;
		this.setRotation(this.Claw1Left, 0F, 0.2094395F, 0F);
		this.Claw2Left = new ModelRenderer(this, 13, 41);
		this.Claw2Left.addBox(1.7F, 15F, -5F, 0, 1, 1);
		this.Claw2Left.setRotationPoint(2.1F, 8F, 1F);
		this.Claw2Left.setTextureSize(128, 128);
		this.Claw2Left.mirror = true;
		this.setRotation(this.Claw2Left, 0F, -0.2094395F, 0F);
		this.Claw3Right = new ModelRenderer(this, 13, 41);
		this.Claw3Right.addBox(-0.9F, 14F, -6.5F, 0, 2, 2);
		this.Claw3Right.setRotationPoint(-2.1F, 8F, 1F);
		this.Claw3Right.setTextureSize(128, 128);
		this.Claw3Right.mirror = true;
		this.setRotation(this.Claw3Right, 0F, 0F, 0F);
		this.Neck2 = new ModelRenderer(this, 62, 16);
		this.Neck2.addBox(-1F, 0F, -1F, 2, 4, 2);
		this.Neck2.setRotationPoint(0F, 5F, -8F);
		this.Neck2.setTextureSize(128, 128);
		this.Neck2.mirror = true;
		this.setRotation(this.Neck2, 0.5934119F, 0F, 0F);
		this.Neck1 = new ModelRenderer(this, 62, 1);
		this.Neck1.addBox(-2F, -9F, -2F, 4, 10, 4);
		this.Neck1.setRotationPoint(0F, 5F, -7F);
		this.Neck1.setTextureSize(128, 128);
		this.Neck1.mirror = true;
		this.setRotation(this.Neck1, 0F, 0F, 0F);
		this.Claw1Right = new ModelRenderer(this, 13, 41);
		this.Claw1Right.addBox(-0.2F, 15F, -5F, 0, 1, 1);
		this.Claw1Right.setRotationPoint(-2.1F, 8F, 1F);
		this.Claw1Right.setTextureSize(128, 128);
		this.Claw1Right.mirror = true;
		this.setRotation(this.Claw1Right, 0F, -0.2094395F, 0F);
		this.Claw1Right.mirror = false;
		this.RightWing = new ModelRenderer(this, 50, 24);
		this.RightWing.addBox(-1F, -2F, -1F, 2, 8, 12);
		this.RightWing.setRotationPoint(-5F, 4F, -4F);
		this.RightWing.setTextureSize(128, 128);
		this.RightWing.mirror = true;
		this.setRotation(this.RightWing, 0F, 0F, 0F);
		this.RightWing.mirror = false;
		this.Claw2Right = new ModelRenderer(this, 13, 41);
		this.Claw2Right.addBox(-1.5F, 15F, -5F, 0, 1, 1);
		this.Claw2Right.setRotationPoint(-2.1F, 8F, 1F);
		this.Claw2Right.setTextureSize(128, 128);
		this.Claw2Right.mirror = true;
		this.setRotation(this.Claw2Right, 0F, 0.2094395F, 0F);
		this.Claw2Right.mirror = false;
		this.LeftLeg1 = new ModelRenderer(this, 34, 37);
		this.LeftLeg1.addBox(0F, 7F, 5F, 2, 7, 2);
		this.LeftLeg1.setRotationPoint(2.1F, 8F, 1F);
		this.LeftLeg1.setTextureSize(128, 128);
		this.LeftLeg1.mirror = true;
		this.setRotation(this.LeftLeg1, -0.4886922F, 0F, 0F);
		this.Claw3Left = new ModelRenderer(this, 13, 41);
		this.Claw3Left.addBox(1.1F, 14F, -6.5F, 0, 2, 2);
		this.Claw3Left.setRotationPoint(2.1F, 8F, 1F);
		this.Claw3Left.setTextureSize(128, 128);
		this.Claw3Left.mirror = true;
		this.setRotation(this.Claw3Left, 0F, 0F, 0F);
		this.RightThigh = new ModelRenderer(this, 35, 49);
		this.RightThigh.addBox(-3F, 0F, -1F, 4, 7, 5);
		this.RightThigh.setRotationPoint(-2.1F, 8F, 1F);
		this.RightThigh.setTextureSize(128, 128);
		this.RightThigh.mirror = true;
		this.setRotation(this.RightThigh, -0.4537856F, 0F, 0F);
		this.RightThigh.mirror = false;
		this.Jaw = new ModelRenderer(this, 33, 1);
		this.Jaw.addBox(-3F, 0F, -9F, 6, 1, 8);
		this.Jaw.setRotationPoint(0F, -4F, -7F);
		this.Jaw.setTextureSize(128, 128);
		this.Jaw.mirror = true;
		this.setRotation(this.Jaw, 0.1047198F, 0F, 0F);
		this.Toe2Right = new ModelRenderer(this, 8, 50);
		this.Toe2Right.addBox(-1.9F, 15F, -4F, 1, 1, 4);
		this.Toe2Right.setRotationPoint(-2.1F, 8F, 1F);
		this.Toe2Right.setTextureSize(128, 128);
		this.Toe2Right.mirror = true;
		this.setRotation(this.Toe2Right, 0F, 0.2094395F, 0F);
		this.Toe2Right.mirror = false;
		this.TailLeft = new ModelRenderer(this, 0, 59);
		this.TailLeft.addBox(1F, 0F, 0F, 3, 0, 9);
		this.TailLeft.setRotationPoint(0F, 2F, 10F);
		this.TailLeft.setTextureSize(128, 128);
		this.TailLeft.mirror = true;
		this.setRotation(this.TailLeft, -0.418879F, 0.2617994F, 0F);
		this.Chest = new ModelRenderer(this, 80, 1);
		this.Chest.addBox(-4F, 0F, 0F, 8, 10, 8);
		this.Chest.setRotationPoint(0F, 0F, -5F);
		this.Chest.setTextureSize(128, 128);
		this.Chest.mirror = true;
		this.setRotation(this.Chest, -0.2792527F, 0F, 0F);
		this.Teeth = new ModelRenderer(this, 33, 13);
		this.Teeth.addBox(-3F, -1F, -9F, 6, 1, 8);
		this.Teeth.setRotationPoint(0F, -4F, -7F);
		this.Teeth.setTextureSize(128, 128);
		this.Teeth.mirror = true;
		this.setRotation(this.Teeth, 0.1047198F, 0F, 0F);
		this.RightLeg1 = new ModelRenderer(this, 34, 27);
		this.RightLeg1.addBox(-2.5F, 5F, -5F, 3, 5, 3);
		this.RightLeg1.setRotationPoint(-2.1F, 8F, 1F);
		this.RightLeg1.setTextureSize(128, 128);
		this.RightLeg1.mirror = true;
		this.setRotation(this.RightLeg1, 0.4886922F, 0F, 0F);
		this.RightLeg1.mirror = false;
		this.FootRight = new ModelRenderer(this, 1, 29);
		this.FootRight.addBox(-1.9F, 14F, -5F, 2, 2, 7);
		this.FootRight.setRotationPoint(-2.1F, 8F, 1F);
		this.FootRight.setTextureSize(128, 128);
		this.FootRight.mirror = true;
		this.setRotation(this.FootRight, 0F, 0F, 0F);
		this.FootRight.mirror = false;
		this.Toe1Right = new ModelRenderer(this, 8, 50);
		this.Toe1Right.addBox(-0.9F, 15F, -4F, 1, 1, 4);
		this.Toe1Right.setRotationPoint(-2.1F, 8F, 1F);
		this.Toe1Right.setTextureSize(128, 128);
		this.Toe1Right.mirror = true;
		this.setRotation(this.Toe1Right, 0F, -0.2094395F, 0F);
		this.Toe1Right.mirror = false;
		this.LeftThigh = new ModelRenderer(this, 35, 49);
		this.LeftThigh.addBox(-1F, 0F, -1F, 4, 7, 5);
		this.LeftThigh.setRotationPoint(2.1F, 8F, 1F);
		this.LeftThigh.setTextureSize(128, 128);
		this.LeftThigh.mirror = true;
		this.setRotation(this.LeftThigh, -0.4537856F, 0F, 0F);
		this.LeftLeg2 = new ModelRenderer(this, 34, 27);
		this.LeftLeg2.addBox(-0.5F, 5F, -5F, 3, 5, 3);
		this.LeftLeg2.setRotationPoint(2.1F, 8F, 1F);
		this.LeftLeg2.setTextureSize(128, 128);
		this.LeftLeg2.mirror = true;
		this.setRotation(this.LeftLeg2, 0.4886922F, 0F, 0F);
		this.Toe1Left = new ModelRenderer(this, 8, 50);
		this.Toe1Left.addBox(0.1F, 15F, -3.9F, 1, 1, 4);
		this.Toe1Left.setRotationPoint(2.1F, 8F, 1F);
		this.Toe1Left.setTextureSize(128, 128);
		this.Toe1Left.mirror = true;
		this.setRotation(this.Toe1Left, 0F, 0.2094395F, 0F);
		this.FootLeft = new ModelRenderer(this, 1, 29);
		this.FootLeft.addBox(0.1F, 14F, -5F, 2, 2, 7);
		this.FootLeft.setRotationPoint(2.1F, 8F, 1F);
		this.FootLeft.setTextureSize(128, 128);
		this.FootLeft.mirror = true;
		this.setRotation(this.FootLeft, 0F, 0F, 0F);
		this.Toe2Left = new ModelRenderer(this, 8, 50);
		this.Toe2Left.addBox(1.1F, 15F, -4F, 1, 1, 4);
		this.Toe2Left.setRotationPoint(2.1F, 8F, 1F);
		this.Toe2Left.setTextureSize(128, 128);
		this.Toe2Left.mirror = true;
		this.setRotation(this.Toe2Left, 0F, -0.2094395F, 0F);
		this.LeftWing = new ModelRenderer(this, 50, 24);
		this.LeftWing.addBox(-1F, -2F, -1F, 2, 8, 12);
		this.LeftWing.setRotationPoint(5F, 4F, -4F);
		this.LeftWing.setTextureSize(128, 128);
		this.LeftWing.mirror = true;
		this.setRotation(this.LeftWing, 0F, 0F, 0F);
		this.RightLeg2 = new ModelRenderer(this, 34, 37);
		this.RightLeg2.addBox(-2F, 7F, 5F, 2, 7, 2);
		this.RightLeg2.setRotationPoint(-2.1F, 8F, 1F);
		this.RightLeg2.setTextureSize(128, 128);
		this.RightLeg2.mirror = true;
		this.setRotation(this.RightLeg2, -0.4886922F, 0F, 0F);
		this.RightLeg2.mirror = false;
		this.Body = new ModelRenderer(this, 80, 21);
		this.Body.addBox(-4F, -6F, 0F, 8, 10, 8);
		this.Body.setRotationPoint(0F, 8F, 2F);
		this.Body.setTextureSize(128, 128);
		this.Body.mirror = true;
		this.setRotation(this.Body, 0F, 0F, 0F);
		this.TailMiddle = new ModelRenderer(this, 0, 72);
		this.TailMiddle.addBox(-1.5F, 0F, 0F, 3, 0, 10);
		this.TailMiddle.setRotationPoint(0F, 2F, 10F);
		this.TailMiddle.setTextureSize(128, 128);
		this.TailMiddle.mirror = true;
		this.setRotation(this.TailMiddle, -0.418879F, 0F, 0F);
		this.TailRight = new ModelRenderer(this, 0, 59);
		this.TailRight.addBox(-4F, 0F, 0F, 3, 0, 9);
		this.TailRight.setRotationPoint(0F, 2F, 10F);
		this.TailRight.setTextureSize(128, 128);
		this.TailRight.mirror = true;
		this.setRotation(this.TailRight, -0.418879F, -0.2617994F, 0F);
		this.TailRight.mirror = false;
		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.addBox(-4F, -7F, -10F, 8, 7, 13);
		this.Head.setRotationPoint(0F, -4F, -7F);
		this.Head.setTextureSize(128, 128);
		this.Head.mirror = true;
		this.setRotation(this.Head, 0F, 0F, 0F);
		this.feather4 = new ModelRenderer(this, 20, 29);
		this.feather4.addBox(3F, -3F, 3F, 1, 2, 5);
		this.feather4.setRotationPoint(0F, -4F, -7F);
		this.feather4.setTextureSize(128, 128);
		this.feather4.mirror = true;
		this.setRotation(this.feather4, -0.1745329F, 0.2268928F, 0F);
		this.feather3 = new ModelRenderer(this, 20, 29);
		this.feather3.addBox(-4F, -6F, 4F, 1, 2, 5);
		this.feather3.setRotationPoint(0F, -4F, -7F);
		this.feather3.setTextureSize(128, 128);
		this.feather3.mirror = true;
		this.setRotation(this.feather3, 0.1745329F, -0.2268928F, 0F);
		this.feather2 = new ModelRenderer(this, 20, 29);
		this.feather2.addBox(-4F, -3F, 3F, 1, 2, 5);
		this.feather2.setRotationPoint(0F, -4F, -7F);
		this.feather2.setTextureSize(128, 128);
		this.feather2.mirror = true;
		this.setRotation(this.feather2, -0.1745329F, -0.2268928F, 0F);
		this.feather1 = new ModelRenderer(this, 20, 29);
		this.feather1.addBox(3F, -6F, 4F, 1, 2, 5);
		this.feather1.setRotationPoint(0F, -4F, -7F);
		this.feather1.setTextureSize(128, 128);
		this.feather1.mirror = true;
		this.setRotation(this.feather1, 0.1745329F, 0.2268928F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.Claw1Left.render(f5);
		this.Claw2Left.render(f5);
		this.Claw3Right.render(f5);
		this.Neck2.render(f5);
		this.Neck1.render(f5);
		this.Claw1Right.render(f5);
		this.RightWing.render(f5);
		this.Claw2Right.render(f5);
		this.LeftLeg1.render(f5);
		this.Claw3Left.render(f5);
		this.RightThigh.render(f5);
		this.Jaw.render(f5);
		this.Toe2Right.render(f5);
		this.TailLeft.render(f5);
		this.Chest.render(f5);
		this.Teeth.render(f5);
		this.RightLeg1.render(f5);
		this.FootRight.render(f5);
		this.Toe1Right.render(f5);
		this.LeftThigh.render(f5);
		this.LeftLeg2.render(f5);
		this.Toe1Left.render(f5);
		this.FootLeft.render(f5);
		this.Toe2Left.render(f5);
		this.LeftWing.render(f5);
		this.RightLeg2.render(f5);
		this.Body.render(f5);
		this.TailMiddle.render(f5);
		this.TailRight.render(f5);
		this.Head.render(f5);
		this.feather4.render(f5);
		this.feather3.render(f5);
		this.feather2.render(f5);
		this.feather1.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		EntityMoa moa = (EntityMoa) entity;
		float f6 = 3.141593F;

		this.Head.rotateAngleX = f4 / 57.29578F;
		this.Head.rotateAngleY = f3 / 57.29578F;

		float rightLegRotation;
		float leftLegRotation;

		float legSwingSpeed = 0.7F;
		float legSwingLength = 0.8F;

		if (moa.onGround)
		{
			rightLegRotation = MathHelper.cos(f * legSwingSpeed) * legSwingLength * f1;
			leftLegRotation = MathHelper.cos(f * legSwingSpeed + 3.141593F) * legSwingLength * f1;
		}
		else
		{
			rightLegRotation = 0.0F;
			leftLegRotation = 0.0F;
		}

		this.RightThigh.rotateAngleX = -0.4537856F + rightLegRotation;
		this.RightLeg1.rotateAngleX = 0.4886922F + rightLegRotation;
		this.RightLeg2.rotateAngleX = -0.4886922F + rightLegRotation;
		this.Toe1Right.rotateAngleX = rightLegRotation;
		this.Toe2Right.rotateAngleX = rightLegRotation;
		this.Claw1Right.rotateAngleX = rightLegRotation;
		this.Claw2Right.rotateAngleX = rightLegRotation;
		this.Claw3Right.rotateAngleX = rightLegRotation;
		this.FootRight.rotateAngleX = rightLegRotation;

		this.LeftThigh.rotateAngleX = -0.4537856F + leftLegRotation;
		this.LeftLeg1.rotateAngleX = -0.4886922F + leftLegRotation;
		this.LeftLeg2.rotateAngleX = 0.4886922F + leftLegRotation;
		this.Toe1Left.rotateAngleX = leftLegRotation;
		this.Toe2Left.rotateAngleX = leftLegRotation;
		this.Claw1Left.rotateAngleX = leftLegRotation;
		this.Claw2Left.rotateAngleX = leftLegRotation;
		this.Claw3Left.rotateAngleX = leftLegRotation;
		this.FootLeft.rotateAngleX = leftLegRotation;

		if (f2 > 0F)
		{
			this.LeftWing.rotationPointZ = -1F;
			this.RightWing.rotationPointZ = -1F;
			this.LeftWing.rotationPointY = 2F;
			this.RightWing.rotationPointY = 2F;
			this.LeftWing.rotateAngleX = f6 / 2.0f;
			this.RightWing.rotateAngleX = f6 / 2.0f;
			this.LeftWing.rotateAngleZ = f2;
			this.RightWing.rotateAngleZ = -f2;

			this.RightThigh.rotateAngleX += 0.8F;
			this.RightLeg1.rotateAngleX += 0.8F;
			this.RightLeg2.rotateAngleX += 0.8F;
			this.Toe1Right.rotateAngleX += 0.8F;
			this.Toe2Right.rotateAngleX += 0.8F;
			this.Claw1Right.rotateAngleX += 0.8F;
			this.Claw2Right.rotateAngleX += 0.8F;
			this.Claw3Right.rotateAngleX += 0.8F;
			this.FootRight.rotateAngleX += 0.8F;

			this.LeftThigh.rotateAngleX += 0.8F;
			this.LeftLeg1.rotateAngleX += 0.8F;
			this.LeftLeg2.rotateAngleX += 0.8F;
			this.Toe1Left.rotateAngleX += 0.8F;
			this.Toe2Left.rotateAngleX += 0.8F;
			this.Claw1Left.rotateAngleX += 0.8F;
			this.Claw2Left.rotateAngleX += 0.8F;
			this.Claw3Left.rotateAngleX += 0.8F;
			this.FootLeft.rotateAngleX += 0.8F;
		}
		else
		{
			this.LeftWing.rotationPointZ = -3F;
			this.RightWing.rotationPointZ = -3F;
			this.LeftWing.rotationPointY = 4F;
			this.RightWing.rotationPointY = 4F;
			this.LeftWing.rotateAngleX = 0.0F;
			this.RightWing.rotateAngleX = 0.0F;
			this.LeftWing.rotateAngleZ = 0.0F;
			this.RightWing.rotateAngleZ = 0.0F;
		}

		this.Neck1.rotateAngleX = 0.0F;
		this.Neck2.rotateAngleX = 0.0F;
		this.Neck1.rotateAngleY = this.Head.rotateAngleY;
		this.Neck2.rotateAngleY = this.Head.rotateAngleY;

		this.Jaw.rotateAngleY = this.Head.rotateAngleY;
		this.Jaw.rotateAngleX = this.Head.rotateAngleX;
		this.Teeth.rotateAngleY = this.Head.rotateAngleY;
		this.Teeth.rotateAngleX = this.Head.rotateAngleX;

		this.feather1.rotateAngleX = 0.1745329F + this.Head.rotateAngleX;
		this.feather1.rotateAngleY = 0.2268928F + this.Head.rotateAngleY;

		this.feather2.rotateAngleX = -0.1745329F + this.Head.rotateAngleX;
		this.feather2.rotateAngleY = -0.2268928F + this.Head.rotateAngleY;

		this.feather3.rotateAngleX = 0.1745329F + this.Head.rotateAngleX;
		this.feather3.rotateAngleY = -0.2268928F + this.Head.rotateAngleY;

		this.feather4.rotateAngleX = -0.1745329F + this.Head.rotateAngleX;
		this.feather4.rotateAngleY = 0.2268928F + this.Head.rotateAngleY;
	}

}
