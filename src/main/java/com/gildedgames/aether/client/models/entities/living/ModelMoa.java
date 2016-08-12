package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.moa.EntityMoa;
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
		textureWidth = 128;
		textureHeight = 128;

		Claw1Left = new ModelRenderer(this, 13, 41);
		Claw1Left.addBox(0.6F, 15F, -4.9F, 0, 1, 1);
		Claw1Left.setRotationPoint(2.1F, 8F, 1F);
		Claw1Left.setTextureSize(128, 128);
		Claw1Left.mirror = true;
		setRotation(Claw1Left, 0F, 0.2094395F, 0F);
		Claw2Left = new ModelRenderer(this, 13, 41);
		Claw2Left.addBox(1.7F, 15F, -5F, 0, 1, 1);
		Claw2Left.setRotationPoint(2.1F, 8F, 1F);
		Claw2Left.setTextureSize(128, 128);
		Claw2Left.mirror = true;
		setRotation(Claw2Left, 0F, -0.2094395F, 0F);
		Claw3Right = new ModelRenderer(this, 13, 41);
		Claw3Right.addBox(-0.9F, 14F, -6.5F, 0, 2, 2);
		Claw3Right.setRotationPoint(-2.1F, 8F, 1F);
		Claw3Right.setTextureSize(128, 128);
		Claw3Right.mirror = true;
		setRotation(Claw3Right, 0F, 0F, 0F);
		Neck2 = new ModelRenderer(this, 62, 16);
		Neck2.addBox(-1F, 0F, -1F, 2, 4, 2);
		Neck2.setRotationPoint(0F, 5F, -8F);
		Neck2.setTextureSize(128, 128);
		Neck2.mirror = true;
		setRotation(Neck2, 0.5934119F, 0F, 0F);
		Neck1 = new ModelRenderer(this, 62, 1);
		Neck1.addBox(-2F, -9F, -2F, 4, 10, 4);
		Neck1.setRotationPoint(0F, 5F, -7F);
		Neck1.setTextureSize(128, 128);
		Neck1.mirror = true;
		setRotation(Neck1, -0.0174533F, 0F, 0F);
		Claw1Right = new ModelRenderer(this, 13, 41);
		Claw1Right.addBox(-0.2F, 15F, -5F, 0, 1, 1);
		Claw1Right.setRotationPoint(-2.1F, 8F, 1F);
		Claw1Right.setTextureSize(128, 128);
		Claw1Right.mirror = true;
		setRotation(Claw1Right, 0F, -0.2094395F, 0F);
		Claw1Right.mirror = false;
		RightWing = new ModelRenderer(this, 50, 24);
		RightWing.addBox(-1F, -2F, -1F, 2, 8, 12);
		RightWing.setRotationPoint(-5F, 4F, -4F);
		RightWing.setTextureSize(128, 128);
		RightWing.mirror = true;
		setRotation(RightWing, 0F, 0F, 0F);
		RightWing.mirror = false;
		Claw2Right = new ModelRenderer(this, 13, 41);
		Claw2Right.addBox(-1.5F, 15F, -5F, 0, 1, 1);
		Claw2Right.setRotationPoint(-2.1F, 8F, 1F);
		Claw2Right.setTextureSize(128, 128);
		Claw2Right.mirror = true;
		setRotation(Claw2Right, 0F, 0.2094395F, 0F);
		Claw2Right.mirror = false;
		LeftLeg1 = new ModelRenderer(this, 34, 37);
		LeftLeg1.addBox(0F, 7F, 5F, 2, 7, 2);
		LeftLeg1.setRotationPoint(2.1F, 8F, 1F);
		LeftLeg1.setTextureSize(128, 128);
		LeftLeg1.mirror = true;
		setRotation(LeftLeg1, -0.4886922F, 0F, 0F);
		Claw3Left = new ModelRenderer(this, 13, 41);
		Claw3Left.addBox(1.1F, 14F, -6.5F, 0, 2, 2);
		Claw3Left.setRotationPoint(2.1F, 8F, 1F);
		Claw3Left.setTextureSize(128, 128);
		Claw3Left.mirror = true;
		setRotation(Claw3Left, 0F, 0F, 0F);
		RightThigh = new ModelRenderer(this, 35, 49);
		RightThigh.addBox(-3F, 0F, -1F, 4, 7, 5);
		RightThigh.setRotationPoint(-2.1F, 8F, 1F);
		RightThigh.setTextureSize(128, 128);
		RightThigh.mirror = true;
		setRotation(RightThigh, -0.4537856F, 0F, 0F);
		RightThigh.mirror = false;
		Jaw = new ModelRenderer(this, 33, 1);
		Jaw.addBox(-3F, -1F, -11F, 6, 1, 8);
		Jaw.setRotationPoint(0F, -3F, -4F);
		Jaw.setTextureSize(128, 128);
		Jaw.mirror = true;
		setRotation(Jaw, 0.1047198F, 0F, 0F);
		Toe2Right = new ModelRenderer(this, 8, 50);
		Toe2Right.addBox(-1.9F, 15F, -4F, 1, 1, 4);
		Toe2Right.setRotationPoint(-2.1F, 8F, 1F);
		Toe2Right.setTextureSize(128, 128);
		Toe2Right.mirror = true;
		setRotation(Toe2Right, 0F, 0.2094395F, 0F);
		Toe2Right.mirror = false;
		TailLeft = new ModelRenderer(this, 0, 59);
		TailLeft.addBox(1F, 0F, 0F, 3, 0, 9);
		TailLeft.setRotationPoint(0F, 2F, 10F);
		TailLeft.setTextureSize(128, 128);
		TailLeft.mirror = true;
		setRotation(TailLeft, -0.418879F, 0.2617994F, 0F);
		Chest = new ModelRenderer(this, 80, 1);
		Chest.addBox(-4F, 0F, 0F, 8, 10, 8);
		Chest.setRotationPoint(0F, 0F, -5F);
		Chest.setTextureSize(128, 128);
		Chest.mirror = true;
		setRotation(Chest, -0.2792527F, 0F, 0F);
		Teeth = new ModelRenderer(this, 33, 13);
		Teeth.addBox(-3F, -2F, -11F, 6, 1, 8);
		Teeth.setRotationPoint(0F, -3F, -4F);
		Teeth.setTextureSize(128, 128);
		Teeth.mirror = true;
		setRotation(Teeth, 0.1047198F, 0F, 0F);
		RightLeg1 = new ModelRenderer(this, 34, 27);
		RightLeg1.addBox(-2.5F, 5F, -5F, 3, 5, 3);
		RightLeg1.setRotationPoint(-2.1F, 8F, 1F);
		RightLeg1.setTextureSize(128, 128);
		RightLeg1.mirror = true;
		setRotation(RightLeg1, 0.4886922F, 0F, 0F);
		RightLeg1.mirror = false;
		FootRight = new ModelRenderer(this, 1, 29);
		FootRight.addBox(-1.9F, 14F, -5F, 2, 2, 7);
		FootRight.setRotationPoint(-2.1F, 8F, 1F);
		FootRight.setTextureSize(128, 128);
		FootRight.mirror = true;
		setRotation(FootRight, 0F, 0F, 0F);
		FootRight.mirror = false;
		Toe1Right = new ModelRenderer(this, 8, 50);
		Toe1Right.addBox(-0.9F, 15F, -4F, 1, 1, 4);
		Toe1Right.setRotationPoint(-2.1F, 8F, 1F);
		Toe1Right.setTextureSize(128, 128);
		Toe1Right.mirror = true;
		setRotation(Toe1Right, 0F, -0.2094395F, 0F);
		Toe1Right.mirror = false;
		LeftThigh = new ModelRenderer(this, 35, 49);
		LeftThigh.addBox(-1F, 0F, -1F, 4, 7, 5);
		LeftThigh.setRotationPoint(2.1F, 8F, 1F);
		LeftThigh.setTextureSize(128, 128);
		LeftThigh.mirror = true;
		setRotation(LeftThigh, -0.4537856F, 0F, 0F);
		LeftLeg2 = new ModelRenderer(this, 34, 27);
		LeftLeg2.addBox(-0.5F, 5F, -5F, 3, 5, 3);
		LeftLeg2.setRotationPoint(2.1F, 8F, 1F);
		LeftLeg2.setTextureSize(128, 128);
		LeftLeg2.mirror = true;
		setRotation(LeftLeg2, 0.4886922F, 0F, 0F);
		Toe1Left = new ModelRenderer(this, 8, 50);
		Toe1Left.addBox(0.1F, 15F, -3.9F, 1, 1, 4);
		Toe1Left.setRotationPoint(2.1F, 8F, 1F);
		Toe1Left.setTextureSize(128, 128);
		Toe1Left.mirror = true;
		setRotation(Toe1Left, 0F, 0.2094395F, 0F);
		FootLeft = new ModelRenderer(this, 1, 29);
		FootLeft.addBox(0.1F, 14F, -5F, 2, 2, 7);
		FootLeft.setRotationPoint(2.1F, 8F, 1F);
		FootLeft.setTextureSize(128, 128);
		FootLeft.mirror = true;
		setRotation(FootLeft, 0F, 0F, 0F);
		Toe2Left = new ModelRenderer(this, 8, 50);
		Toe2Left.addBox(1.1F, 15F, -4F, 1, 1, 4);
		Toe2Left.setRotationPoint(2.1F, 8F, 1F);
		Toe2Left.setTextureSize(128, 128);
		Toe2Left.mirror = true;
		setRotation(Toe2Left, 0F, -0.2094395F, 0F);
		LeftWing = new ModelRenderer(this, 50, 24);
		LeftWing.addBox(-1F, -2F, -1F, 2, 8, 12);
		LeftWing.setRotationPoint(5F, 4F, -4F);
		LeftWing.setTextureSize(128, 128);
		LeftWing.mirror = true;
		setRotation(LeftWing, 0F, 0F, 0F);
		RightLeg2 = new ModelRenderer(this, 34, 37);
		RightLeg2.addBox(-2F, 7F, 5F, 2, 7, 2);
		RightLeg2.setRotationPoint(-2.1F, 8F, 1F);
		RightLeg2.setTextureSize(128, 128);
		RightLeg2.mirror = true;
		setRotation(RightLeg2, -0.4886922F, 0F, 0F);
		RightLeg2.mirror = false;
		Body = new ModelRenderer(this, 80, 21);
		Body.addBox(-4F, -6F, 0F, 8, 10, 8);
		Body.setRotationPoint(0F, 8F, 2F);
		Body.setTextureSize(128, 128);
		Body.mirror = true;
		setRotation(Body, 0F, 0F, 0F);
		TailMiddle = new ModelRenderer(this, 0, 72);
		TailMiddle.addBox(-1.5F, 0F, 0F, 3, 0, 10);
		TailMiddle.setRotationPoint(0F, 2F, 10F);
		TailMiddle.setTextureSize(128, 128);
		TailMiddle.mirror = true;
		setRotation(TailMiddle, -0.418879F, 0F, 0F);
		TailRight = new ModelRenderer(this, 0, 59);
		TailRight.addBox(-4F, 0F, 0F, 3, 0, 9);
		TailRight.setRotationPoint(0F, 2F, 10F);
		TailRight.setTextureSize(128, 128);
		TailRight.mirror = true;
		setRotation(TailRight, -0.418879F, -0.2617994F, 0F);
		TailRight.mirror = false;
		Head = new ModelRenderer(this, 0, 0);
		Head.addBox(-4F, -8F, -12F, 8, 7, 13);
		Head.setRotationPoint(0F, -3F, -4F);
		Head.setTextureSize(128, 128);
		Head.mirror = true;
		setRotation(Head, 0F, 0F, 0F);
		feather4 = new ModelRenderer(this, 20, 29);
		feather4.addBox(3F, -5F, 0F, 1, 2, 5);
		feather4.setRotationPoint(0F, -3F, -4F);
		feather4.setTextureSize(128, 128);
		feather4.mirror = true;
		setRotation(feather4, -0.1745329F, 0.2268928F, 0F);
		feather3 = new ModelRenderer(this, 20, 29);
		feather3.addBox(-4F, -8F, 1F, 1, 2, 5);
		feather3.setRotationPoint(0F, -3F, -4F);
		feather3.setTextureSize(128, 128);
		feather3.mirror = true;
		setRotation(feather3, 0.1745329F, -0.2268928F, 0F);
		feather2 = new ModelRenderer(this, 20, 29);
		feather2.addBox(-4F, -5F, 0F, 1, 2, 5);
		feather2.setRotationPoint(0F, -3F, -4F);
		feather2.setTextureSize(128, 128);
		feather2.mirror = true;
		setRotation(feather2, -0.1745329F, -0.2268928F, 0F);
		feather1 = new ModelRenderer(this, 20, 29);
		feather1.addBox(3F, -8F, 1F, 1, 2, 5);
		feather1.setRotationPoint(0F, -3F, -4F);
		feather1.setTextureSize(128, 128);
		feather1.mirror = true;
		setRotation(feather1, 0.1745329F, 0.2268928F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Claw1Left.render(f5);
		Claw2Left.render(f5);
		Claw3Right.render(f5);
		Neck2.render(f5);
		Neck1.render(f5);
		Claw1Right.render(f5);
		RightWing.render(f5);
		Claw2Right.render(f5);
		LeftLeg1.render(f5);
		Claw3Left.render(f5);
		RightThigh.render(f5);
		Jaw.render(f5);
		Toe2Right.render(f5);
		TailLeft.render(f5);
		Chest.render(f5);
		Teeth.render(f5);
		RightLeg1.render(f5);
		FootRight.render(f5);
		Toe1Right.render(f5);
		LeftThigh.render(f5);
		LeftLeg2.render(f5);
		Toe1Left.render(f5);
		FootLeft.render(f5);
		Toe2Left.render(f5);
		LeftWing.render(f5);
		RightLeg2.render(f5);
		Body.render(f5);
		TailMiddle.render(f5);
		TailRight.render(f5);
		Head.render(f5);
		feather4.render(f5);
		feather3.render(f5);
		feather2.render(f5);
		feather1.render(f5);
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
		} else {
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
