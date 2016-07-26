package com.gildedgames.aether.client.models.entities.companions;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelFangrin extends ModelBase
{
	private final ModelRenderer Torso_Front;

	private final ModelRenderer Torso_Back;

	private final ModelRenderer Left_Leg;

	private final ModelRenderer Left_Foot;

	private final ModelRenderer Right_Leg;

	private final ModelRenderer Right_Foot;

	private final ModelRenderer Head;

	private final ModelRenderer Cheek_Right;

	private final ModelRenderer Cheek_Left;

	private final ModelRenderer Ear_Right;

	private final ModelRenderer Ear_Left;

	private final ModelRenderer Jaw;

	private final ModelRenderer Brow_Right;

	private final ModelRenderer Brow_Left;

	private final ModelRenderer Nose_Top;

	private final ModelRenderer Nose_Bottom;

	private final ModelRenderer Shell_Front;

	private final ModelRenderer Shell_Left;

	private final ModelRenderer Shell_Right;

	private final ModelRenderer Shell_Top;

	private final ModelRenderer Shell_Back;

	private final ModelRenderer Shell_Tail;

	private final ModelRenderer Back_Leg_Left;

	private final ModelRenderer Back_Foot_Left;

	private final ModelRenderer Back_Leg_Right;

	private final ModelRenderer Back_Foot_Right;

	private final ModelRenderer Tail_Base;

	private final ModelRenderer Tail_End;

	public ModelFangrin()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;

		this.Torso_Front = new ModelRenderer(this, 32, 13);
		this.Torso_Front.addBox(-7F, 0F, -10F, 14, 9, 10);
		this.Torso_Front.setRotationPoint(0F, 10F, 0F);
		this.Torso_Front.setTextureSize(128, 64);
		this.Torso_Front.mirror = true;
		this.setRotation(this.Torso_Front, 0.1570796F, 0F, 0F);

		this.Torso_Back = new ModelRenderer(this, 32, 32);
		this.Torso_Back.addBox(-6F, 0F, 0F, 12, 8, 6);
		this.Torso_Back.setRotationPoint(0F, 10F, 0F);
		this.Torso_Back.setTextureSize(128, 64);
		this.Torso_Back.mirror = true;
		this.setRotation(this.Torso_Back, -0.3490659F, 0F, 0F);

		this.Left_Leg = new ModelRenderer(this, 0, 13);
		this.Left_Leg.addBox(0F, -2F, -2F, 3, 5, 4);
		this.Left_Leg.setRotationPoint(5F, 18F, -6F);
		this.Left_Leg.setTextureSize(128, 64);
		this.Left_Leg.mirror = true;
		this.setRotation(this.Left_Leg, 0F, 0F, 0F);

		this.Left_Leg.mirror = false;
		this.Left_Foot = new ModelRenderer(this, 0, 22);
		this.Left_Foot.addBox(0F, 1F, -3F, 3, 5, 4);
		this.Left_Foot.setRotationPoint(5F, 18F, -6F);
		this.Left_Foot.setTextureSize(128, 64);
		this.Left_Foot.mirror = true;
		this.setRotation(this.Left_Foot, -0.1745329F, -0.6981317F, 0.1745329F);

		this.Left_Foot.mirror = false;
		this.Right_Leg = new ModelRenderer(this, 0, 13);
		this.Right_Leg.addBox(-3F, -2F, -2F, 3, 5, 4);
		this.Right_Leg.setRotationPoint(-5F, 18F, -6F);
		this.Right_Leg.setTextureSize(128, 64);
		this.Right_Leg.mirror = true;
		this.setRotation(this.Right_Leg, 0F, 0F, 0F);

		this.Right_Foot = new ModelRenderer(this, 0, 22);
		this.Right_Foot.addBox(-3F, 1F, -3F, 3, 5, 4);
		this.Right_Foot.setRotationPoint(-5F, 18F, -6F);
		this.Right_Foot.setTextureSize(128, 64);
		this.Right_Foot.mirror = true;
		this.setRotation(this.Right_Foot, -0.1745329F, 0.6981317F, -0.1745329F);

		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.addBox(-4F, -5F, -7F, 8, 5, 8);
		this.Head.setRotationPoint(0F, 16F, -9F);
		this.Head.setTextureSize(128, 64);
		this.Head.mirror = true;
		this.setRotation(this.Head, 0.5235988F, 0F, 0F);

		this.Cheek_Right = new ModelRenderer(this, 0, 31);
		this.Cheek_Right.addBox(-5F, -1F, -3F, 3, 2, 6);
		this.Cheek_Right.setRotationPoint(0F, 16F, -9F);
		this.Cheek_Right.setTextureSize(128, 64);
		this.Cheek_Right.mirror = true;
		this.setRotation(this.Cheek_Right, 0.4363323F, -0.3490659F, 0F);

		this.Cheek_Left = new ModelRenderer(this, 0, 31);
		this.Cheek_Left.addBox(2F, -1F, -3F, 3, 2, 6);
		this.Cheek_Left.setRotationPoint(0F, 16F, -9F);
		this.Cheek_Left.setTextureSize(128, 64);
		this.Cheek_Left.mirror = true;
		this.setRotation(this.Cheek_Left, 0.4363323F, 0.3490659F, 0F);

		this.Cheek_Left.mirror = false;
		this.Ear_Right = new ModelRenderer(this, 58, 3);
		this.Ear_Right.addBox(-4F, -4F, 2F, 1, 3, 4);
		this.Ear_Right.setRotationPoint(0F, 16F, -9F);
		this.Ear_Right.setTextureSize(128, 64);
		this.Ear_Right.mirror = true;
		this.setRotation(this.Ear_Right, 0.6981317F, -0.5235988F, -0.3490659F);

		this.Ear_Left = new ModelRenderer(this, 58, 3);
		this.Ear_Left.addBox(3F, -4F, 2F, 1, 3, 4);
		this.Ear_Left.setRotationPoint(0F, 16F, -9F);
		this.Ear_Left.setTextureSize(128, 64);
		this.Ear_Left.mirror = true;
		this.setRotation(this.Ear_Left, 0.6981317F, 0.5235988F, 0.3490659F);

		this.Ear_Left.mirror = false;
		this.Jaw = new ModelRenderer(this, 32, 2);
		this.Jaw.addBox(-4.5F, -2F, -7.5F, 9, 3, 8);
		this.Jaw.setRotationPoint(0F, 16F, -9F);
		this.Jaw.setTextureSize(128, 64);
		this.Jaw.mirror = true;
		this.setRotation(this.Jaw, 0.5235988F, 0F, 0F);

		this.Brow_Right = new ModelRenderer(this, 30, 2);
		this.Brow_Right.addBox(-7F, -1F, -4F, 2, 1, 3);
		this.Brow_Right.setRotationPoint(0F, 16F, -9F);
		this.Brow_Right.setTextureSize(128, 64);
		this.Brow_Right.mirror = true;

		this.Brow_Left = new ModelRenderer(this, 30, 2);
		this.Brow_Left.addBox(5F, -1F, -4F, 2, 1, 3);
		this.Brow_Left.setRotationPoint(0F, 16F, -9F);
		this.Brow_Left.setTextureSize(128, 64);
		this.Brow_Left.mirror = true;

		this.Brow_Left.mirror = false;
		this.Nose_Top = new ModelRenderer(this, 32, 6);
		this.Nose_Top.addBox(-1.5F, -4.5F, -7.2F, 3, 1, 1);
		this.Nose_Top.setRotationPoint(0F, 16F, -9F);
		this.Nose_Top.setTextureSize(128, 64);
		this.Nose_Top.mirror = true;
		this.setRotation(this.Nose_Top, 0.5235988F, 0F, 0F);

		this.Nose_Bottom = new ModelRenderer(this, 34, 8);
		this.Nose_Bottom.addBox(-1F, -3.5F, -7.2F, 2, 1, 1);
		this.Nose_Bottom.setRotationPoint(0F, 16F, -9F);
		this.Nose_Bottom.setTextureSize(128, 64);
		this.Nose_Bottom.mirror = true;
		this.setRotation(this.Nose_Bottom, 0.5235988F, 0F, 0F);

		this.Shell_Front = new ModelRenderer(this, 68, 1);
		this.Shell_Front.addBox(1F, 0F, -9F, 8, 2, 8);
		this.Shell_Front.setRotationPoint(0F, 10F, 0F);
		this.Shell_Front.setTextureSize(128, 64);
		this.Shell_Front.mirror = true;
		this.setRotation(this.Shell_Front, -0.0872665F, 0.7853982F, -0.0872665F);

		this.Shell_Left = new ModelRenderer(this, 68, 32);
		this.Shell_Left.addBox(1F, -7F, -5F, 6, 7, 7);
		this.Shell_Left.setRotationPoint(0F, 10F, 0F);
		this.Shell_Left.setTextureSize(128, 64);
		this.Shell_Left.mirror = true;
		this.setRotation(this.Shell_Left, -0.5235988F, 0.5235988F, 1.047198F);

		this.Shell_Left.mirror = false;
		this.Shell_Right = new ModelRenderer(this, 68, 32);
		this.Shell_Right.addBox(-7F, -7F, -5F, 6, 7, 7);
		this.Shell_Right.setRotationPoint(0F, 10F, 0F);
		this.Shell_Right.setTextureSize(128, 64);
		this.Shell_Right.mirror = true;
		this.setRotation(this.Shell_Right, -0.5235988F, -0.5235988F, -1.047198F);

		this.Shell_Top = new ModelRenderer(this, 70, 11);
		this.Shell_Top.addBox(-4F, -2F, -4F, 8, 4, 8);
		this.Shell_Top.setRotationPoint(0F, 10F, 0F);
		this.Shell_Top.setTextureSize(128, 64);
		this.Shell_Top.mirror = true;
		this.setRotation(this.Shell_Top, -0.2617994F, 0.7853982F, -0.2617994F);

		this.Shell_Back = new ModelRenderer(this, 102, 0);
		this.Shell_Back.addBox(-4F, -2F, 1.5F, 8, 12, 5);
		this.Shell_Back.setRotationPoint(0F, 10F, 0F);
		this.Shell_Back.setTextureSize(128, 64);
		this.Shell_Back.mirror = true;
		this.setRotation(this.Shell_Back, 0.2230717F, 0F, 0F);

		this.Shell_Tail = new ModelRenderer(this, 80, 25);
		this.Shell_Tail.addBox(-2F, 5F, 1F, 4, 5, 2);
		this.Shell_Tail.setRotationPoint(0F, 10F, 0F);
		this.Shell_Tail.setTextureSize(128, 64);
		this.Shell_Tail.mirror = true;
		this.setRotation(this.Shell_Tail, 0.8551081F, 0F, 0F);

		this.Back_Leg_Left = new ModelRenderer(this, 14, 13);
		this.Back_Leg_Left.addBox(0F, -2F, -2F, 3, 5, 4);
		this.Back_Leg_Left.setRotationPoint(5F, 17F, 3F);
		this.Back_Leg_Left.setTextureSize(128, 64);
		this.Back_Leg_Left.mirror = true;
		this.setRotation(this.Back_Leg_Left, -0.296706F, 0F, 0F);

		this.Back_Leg_Left.mirror = false;
		this.Back_Foot_Left = new ModelRenderer(this, 14, 22);
		this.Back_Foot_Left.addBox(-1F, 2F, -1F, 4, 5, 4);
		this.Back_Foot_Left.setRotationPoint(5F, 17F, 3F);
		this.Back_Foot_Left.setTextureSize(128, 64);
		this.Back_Foot_Left.mirror = true;
		this.setRotation(this.Back_Foot_Left, 0F, -0.2617994F, 0F);

		this.Back_Foot_Left.mirror = false;
		this.Back_Leg_Right = new ModelRenderer(this, 14, 13);
		this.Back_Leg_Right.addBox(-2F, -2F, -2F, 3, 5, 4);
		this.Back_Leg_Right.setRotationPoint(-6F, 17F, 3F);
		this.Back_Leg_Right.setTextureSize(128, 64);
		this.Back_Leg_Right.mirror = true;
		this.setRotation(this.Back_Leg_Right, -0.296706F, 0F, 0F);

		this.Back_Foot_Right = new ModelRenderer(this, 14, 22);
		this.Back_Foot_Right.addBox(-3F, 2F, 0F, 4, 5, 4);
		this.Back_Foot_Right.setRotationPoint(-6F, 17F, 3F);
		this.Back_Foot_Right.setTextureSize(128, 64);
		this.Back_Foot_Right.mirror = true;
		this.setRotation(this.Back_Foot_Right, 0F, 0.2617994F, 0F);

		this.Tail_Base = new ModelRenderer(this, 92, 23);
		this.Tail_Base.addBox(-1F, 0F, 0F, 2, 2, 7);
		this.Tail_Base.setRotationPoint(0F, 13F, 7F);
		this.Tail_Base.setTextureSize(128, 64);
		this.Tail_Base.mirror = true;
		this.setRotation(this.Tail_Base, -0.8179294F, 0F, 0F);

		this.Tail_End = new ModelRenderer(this, 94, 32);
		this.Tail_End.addBox(-1F, 3.6F, 4F, 2, 2, 6);
		this.Tail_End.setRotationPoint(0F, 13F, 7F);
		this.Tail_End.setTextureSize(128, 64);
		this.Tail_End.mirror = true;
		this.setRotation(this.Tail_End, -0.1858931F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		this.Torso_Front.render(scale);
		this.Torso_Back.render(scale);
		this.Left_Leg.render(scale);
		this.Left_Foot.render(scale);
		this.Right_Leg.render(scale);
		this.Right_Foot.render(scale);
		this.Head.render(scale);
		this.Cheek_Right.render(scale);
		this.Cheek_Left.render(scale);
		this.Ear_Right.render(scale);
		this.Ear_Left.render(scale);
		this.Jaw.render(scale);
		this.Brow_Right.render(scale);
		this.Brow_Left.render(scale);
		this.Nose_Top.render(scale);
		this.Nose_Bottom.render(scale);
		this.Shell_Front.render(scale);
		this.Shell_Left.render(scale);
		this.Shell_Right.render(scale);
		this.Shell_Top.render(scale);
		this.Shell_Back.render(scale);
		this.Shell_Tail.render(scale);
		this.Back_Leg_Left.render(scale);
		this.Back_Foot_Left.render(scale);
		this.Back_Leg_Right.render(scale);
		this.Back_Foot_Right.render(scale);
		this.Tail_Base.render(scale);
		this.Tail_End.render(scale);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(Entity entity, float par1, float par2, float par3, float par4, float par5, float par6)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);

		this.Head.rotateAngleX = par5 / (180F / (float) Math.PI) + 0.4f;
		this.Head.rotateAngleY = par4 / (180F / (float) Math.PI);

		this.rotateWith(this.Head, this.Nose_Top);
		this.rotateWith(this.Head, this.Nose_Bottom);
		this.rotateWith(this.Head, this.Jaw);

		this.Jaw.rotateAngleX += 0.2f + (float) Math.cos(par3 / 10) / 10;

		this.rotateWith(this.Jaw, this.Cheek_Left);
		this.rotateWith(this.Jaw, this.Cheek_Right);

		this.Cheek_Left.rotateAngleX += 0.43f;
		this.Cheek_Left.rotateAngleY += 0.35f;
		this.Cheek_Right.rotateAngleX += 0.43f;
		this.Cheek_Right.rotateAngleY -= 0.35f;

		this.Tail_Base.rotateAngleX = (float) Math.sin(par3 / 10) / 20 - 0.7f;
		this.Tail_End.rotateAngleX = (float) Math.sin(par3 / 10) / 10 - 0.15f;
		this.Tail_End.offsetZ = 0.06f - ((float) Math.sin(par3 / 10)) / 210;

		this.Left_Leg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		this.Right_Leg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
		this.Back_Leg_Left.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
		this.Back_Leg_Right.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		this.Back_Leg_Left.rotateAngleY = 0;
		this.Back_Leg_Right.rotateAngleY = 0;

		this.rotateWith(this.Back_Leg_Left, this.Back_Foot_Left);
		this.rotateWith(this.Back_Leg_Right, this.Back_Foot_Right);
		this.rotateWith(this.Left_Leg, this.Left_Foot);
		this.rotateWith(this.Right_Leg, this.Right_Foot);

		this.Back_Leg_Left.rotateAngleX += 0.3f;
		this.Back_Leg_Left.rotateAngleY = 0.2f;
		this.Back_Leg_Right.rotateAngleX += 0.4f;
		this.Back_Leg_Right.rotateAngleY = -0.2f;
	}

	public void rotateWith(ModelRenderer base, ModelRenderer rotating)
	{
		rotating.setRotationPoint(base.rotationPointX, base.rotationPointY, base.rotationPointZ);
		rotating.rotateAngleX = base.rotateAngleX;
		rotating.rotateAngleY = base.rotateAngleY;
		rotating.rotateAngleZ = base.rotateAngleZ;
	}
}
