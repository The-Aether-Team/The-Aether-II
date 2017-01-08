package com.gildedgames.aether.client.models.entities.companions;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelKraisith extends ModelBase
{
	private final ModelRenderer Body_Main;

	private final ModelRenderer Body_Base;

	private final ModelRenderer Front_Leg_Left;

	private final ModelRenderer Front_Leg_Right;

	private final ModelRenderer Back_Leg_Right;

	private final ModelRenderer Back_Leg_Left;

	private final ModelRenderer Tail_1;

	private final ModelRenderer Tail_2;

	private final ModelRenderer Tail_Spines_1;

	private final ModelRenderer Tail_Spines_2;

	private final ModelRenderer Neck;

	private final ModelRenderer Head_Main;

	private final ModelRenderer Head_Top_Left;

	private final ModelRenderer Head_Top_Right;

	private final ModelRenderer Head_Bottom_Left;

	private final ModelRenderer Head_Bottom_Right;

	private final ModelRenderer Body_Stem;

	private final ModelRenderer Left_Shell_Main;

	private final ModelRenderer Right_Shell_Main;

	private final ModelRenderer Left_Shell_Front;

	private final ModelRenderer Right_Shell_Front;

	private final ModelRenderer Left_Shell_Back;

	private final ModelRenderer Right_Shell_Back;

	private final ModelRenderer Left_Shell_Top;

	private final ModelRenderer Right_Shell_Top;

	private final ModelRenderer Left_Shell_Bottom;

	private final ModelRenderer Right_Shell_Bottom;

	public ModelKraisith()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;

		this.Body_Main = new ModelRenderer(this, 50, 28);
		this.Body_Main.addBox(0F, 0F, 0F, 7, 8, 7);
		this.Body_Main.setRotationPoint(-3F, 8F, -4F);
		this.Body_Main.setTextureSize(128, 64);
		this.Body_Main.mirror = true;
		this.setRotation(this.Body_Main, 0F, 0F, 0F);

		this.Body_Base = new ModelRenderer(this, 44, 43);
		this.Body_Base.addBox(0F, 0F, 0F, 9, 3, 11);
		this.Body_Base.setRotationPoint(-4F, 16F, -6F);
		this.Body_Base.setTextureSize(128, 64);
		this.Body_Base.mirror = true;
		this.setRotation(this.Body_Base, 0F, 0F, 0F);

		this.Front_Leg_Left = new ModelRenderer(this, 65, 57);
		this.Front_Leg_Left.addBox(-1F, -2F, -1F, 2, 5, 2);
		this.Front_Leg_Left.setRotationPoint(5F, 19F, -6F);
		this.Front_Leg_Left.setTextureSize(128, 64);
		this.Front_Leg_Left.mirror = true;
		this.setRotation(this.Front_Leg_Left, -0.3490659F, 0F, -0.3490659F);

		this.Front_Leg_Right = new ModelRenderer(this, 55, 57);
		this.Front_Leg_Right.addBox(-1F, -2F, -1F, 2, 5, 2);
		this.Front_Leg_Right.setRotationPoint(-4F, 19F, -6F);
		this.Front_Leg_Right.setTextureSize(128, 64);
		this.Front_Leg_Right.mirror = true;
		this.setRotation(this.Front_Leg_Right, -0.3490659F, 0F, 0.3490659F);

		this.Back_Leg_Right = new ModelRenderer(this, 45, 57);
		this.Back_Leg_Right.addBox(-1F, -2F, -1F, 2, 5, 2);
		this.Back_Leg_Right.setRotationPoint(-4F, 19F, 5F);
		this.Back_Leg_Right.setTextureSize(128, 64);
		this.Back_Leg_Right.mirror = true;
		this.setRotation(this.Back_Leg_Right, 0.3490659F, 0F, 0.3490659F);

		this.Back_Leg_Left = new ModelRenderer(this, 75, 57);
		this.Back_Leg_Left.addBox(-1F, -2F, -1F, 2, 5, 2);
		this.Back_Leg_Left.setRotationPoint(5F, 19F, 5F);
		this.Back_Leg_Left.setTextureSize(128, 64);
		this.Back_Leg_Left.mirror = true;
		this.setRotation(this.Back_Leg_Left, 0.3490659F, 0F, -0.3490659F);

		this.Tail_1 = new ModelRenderer(this, 84, 45);
		this.Tail_1.addBox(-1F, -1.5F, -1F, 2, 3, 7);
		this.Tail_1.setRotationPoint(0.5F, 11F, 2F);
		this.Tail_1.setTextureSize(128, 64);
		this.Tail_1.mirror = true;
		this.setRotation(this.Tail_1, -0.3490659F, 0F, 0F);

		this.Tail_2 = new ModelRenderer(this, 102, 48);
		this.Tail_2.addBox(-0.5F, 1F, 5F, 1, 2, 5);
		this.Tail_2.setRotationPoint(0.5F, 11F, 2F);
		this.Tail_2.setTextureSize(128, 64);
		this.Tail_2.mirror = true;
		this.setRotation(this.Tail_2, 0F, 0F, 0F);

		this.Tail_Spines_1 = new ModelRenderer(this, 84, 37);
		this.Tail_Spines_1.addBox(0F, -2.5F, -1F, 0, 1, 7);
		this.Tail_Spines_1.setRotationPoint(0.5F, 11F, 2F);
		this.Tail_Spines_1.setTextureSize(128, 64);
		this.Tail_Spines_1.mirror = true;
		this.setRotation(this.Tail_Spines_1, -0.3490659F, 0F, 0F);

		this.Tail_Spines_2 = new ModelRenderer(this, 102, 43);
		this.Tail_Spines_2.addBox(0F, 0F, 6F, 0, 1, 4);
		this.Tail_Spines_2.setRotationPoint(0.5F, 11F, 2F);
		this.Tail_Spines_2.setTextureSize(128, 64);
		this.Tail_Spines_2.mirror = true;
		this.setRotation(this.Tail_Spines_2, 0F, 0F, 0F);

		this.Neck = new ModelRenderer(this, 58, 15);
		this.Neck.addBox(0F, 0F, -3F, 3, 4, 3);
		this.Neck.setRotationPoint(-1F, 8.5F, -3F);
		this.Neck.setTextureSize(128, 64);
		this.Neck.mirror = true;
		this.setRotation(this.Neck, 0.1745329F, 0F, 0F);

		this.Head_Main = new ModelRenderer(this, 58, 3);
		this.Head_Main.addBox(0.5F, -2F, -7F, 2, 8, 4);
		this.Head_Main.setRotationPoint(-1F, 8.5F, -3F);
		this.Head_Main.setTextureSize(128, 64);
		this.Head_Main.mirror = true;
		this.setRotation(this.Head_Main, 0.1745329F, 0F, 0F);

		this.Head_Top_Left = new ModelRenderer(this, 70, 0);
		this.Head_Top_Left.addBox(2F, -3F, -7.5F, 1, 5, 4);
		this.Head_Top_Left.setRotationPoint(-1F, 8.5F, -3F);
		this.Head_Top_Left.setTextureSize(128, 64);
		this.Head_Top_Left.mirror = true;
		this.setRotation(this.Head_Top_Left, 0.1745329F, 0F, -0.1047198F);

		this.Head_Top_Right = new ModelRenderer(this, 48, 0);
		this.Head_Top_Right.addBox(0F, -3.3F, -7.5F, 1, 5, 4);
		this.Head_Top_Right.setRotationPoint(-1F, 8.5F, -3F);
		this.Head_Top_Right.setTextureSize(128, 64);
		this.Head_Top_Right.mirror = true;
		this.setRotation(this.Head_Top_Right, 0.1745329F, 0F, 0.1047198F);

		this.Head_Bottom_Left = new ModelRenderer(this, 70, 9);
		this.Head_Bottom_Left.addBox(2.4F, 1.5F, -7.5F, 1, 5, 4);
		this.Head_Bottom_Left.setRotationPoint(-1F, 8.5F, -3F);
		this.Head_Bottom_Left.setTextureSize(128, 64);
		this.Head_Bottom_Left.mirror = true;
		this.setRotation(this.Head_Bottom_Left, 0.1745329F, 0F, 0.1047198F);

		this.Head_Bottom_Right = new ModelRenderer(this, 48, 9);
		this.Head_Bottom_Right.addBox(-0.4F, 1.8F, -7.5F, 1, 5, 4);
		this.Head_Bottom_Right.setRotationPoint(-1F, 8.5F, -3F);
		this.Head_Bottom_Right.setTextureSize(128, 64);
		this.Head_Bottom_Right.mirror = true;
		this.setRotation(this.Head_Bottom_Right, 0.1745329F, 0F, -0.1047198F);

		this.Body_Stem = new ModelRenderer(this, 50, 22);
		this.Body_Stem.addBox(0F, 0F, 0F, 11, 3, 3);
		this.Body_Stem.setRotationPoint(-5F, 9.5F, -2F);
		this.Body_Stem.setTextureSize(128, 64);
		this.Body_Stem.mirror = true;
		this.setRotation(this.Body_Stem, 0F, 0F, 0F);

		this.Left_Shell_Main = new ModelRenderer(this, 12, 13);
		this.Left_Shell_Main.addBox(-3F, 0F, 0F, 3, 10, 10);
		this.Left_Shell_Main.setRotationPoint(9F, 6F, -5.5F);
		this.Left_Shell_Main.setTextureSize(128, 64);
		this.setRotation(this.Left_Shell_Main, 0F, 0F, 0F);

		this.Left_Shell_Main.mirror = false;
		this.Right_Shell_Main = new ModelRenderer(this, 12, 13);
		this.Right_Shell_Main.addBox(0F, 0F, 0F, 3, 10, 10);
		this.Right_Shell_Main.setRotationPoint(-8F, 6F, -5.5F);
		this.Right_Shell_Main.setTextureSize(128, 64);
		this.Right_Shell_Main.mirror = true;
		this.setRotation(this.Right_Shell_Main, 0F, 0F, 0F);

		this.Left_Shell_Front = new ModelRenderer(this, 38, 18);
		this.Left_Shell_Front.mirror = true;
		this.Left_Shell_Front.addBox(-2F, -0.5F, -4F, 2, 11, 4);
		this.Left_Shell_Front.setRotationPoint(9F, 6F, -5.5F);
		this.Left_Shell_Front.setTextureSize(128, 64);
		this.setRotation(this.Left_Shell_Front, 0F, 0.6108652F, 0F);

		this.Right_Shell_Front = new ModelRenderer(this, 38, 18);
		this.Right_Shell_Front.addBox(0F, -0.5F, -4F, 2, 11, 4);
		this.Right_Shell_Front.setRotationPoint(-8F, 6F, -5.5F);
		this.Right_Shell_Front.setTextureSize(128, 64);
		this.Right_Shell_Front.mirror = true;
		this.setRotation(this.Right_Shell_Front, 0F, -0.6108652F, 0F);

		this.Left_Shell_Back = new ModelRenderer(this, 0, 18);
		this.Left_Shell_Back.addBox(3.5F, -0.5F, 8F, 2, 11, 4);
		this.Left_Shell_Back.setRotationPoint(9F, 6F, -5.5F);
		this.Left_Shell_Back.setTextureSize(128, 64);
		this.setRotation(this.Left_Shell_Back, 0F, -0.6108652F, 0F);

		this.Left_Shell_Back.mirror = true;
		this.Right_Shell_Back = new ModelRenderer(this, 0, 18);
		this.Right_Shell_Back.addBox(-5.5F, -0.5F, 8F, 2, 11, 4);
		this.Right_Shell_Back.setRotationPoint(-8F, 6F, -5.5F);
		this.Right_Shell_Back.setTextureSize(128, 64);
		this.Right_Shell_Back.mirror = true;
		this.setRotation(this.Right_Shell_Back, 0F, 0.6108652F, 0F);

		this.Left_Shell_Top = new ModelRenderer(this, 12, 0);
		this.Left_Shell_Top.mirror = true;
		this.Left_Shell_Top.addBox(-2F, -4F, 0.5F, 2, 4, 9);
		this.Left_Shell_Top.setRotationPoint(9F, 6F, -5.5F);
		this.Left_Shell_Top.setTextureSize(128, 64);
		this.setRotation(this.Left_Shell_Top, 0F, 0F, -0.6108652F);

		this.Right_Shell_Top = new ModelRenderer(this, 12, 0);
		this.Right_Shell_Top.addBox(0F, -4F, 0.5F, 2, 4, 9);
		this.Right_Shell_Top.setRotationPoint(-8F, 6F, -5.5F);
		this.Right_Shell_Top.setTextureSize(128, 64);
		this.Right_Shell_Top.mirror = true;
		this.setRotation(this.Right_Shell_Top, 0F, 0F, 0.6108652F);

		this.Left_Shell_Bottom = new ModelRenderer(this, 12, 33);
		this.Left_Shell_Bottom.mirror = true;
		this.Left_Shell_Bottom.addBox(3.5F, 8F, 0.5F, 2, 4, 9);
		this.Left_Shell_Bottom.setRotationPoint(9F, 6F, -5.5F);
		this.Left_Shell_Bottom.setTextureSize(128, 64);
		this.setRotation(this.Left_Shell_Bottom, 0F, 0F, 0.6108652F);

		this.Right_Shell_Bottom = new ModelRenderer(this, 12, 33);
		this.Right_Shell_Bottom.addBox(-5.5F, 8F, 0.5F, 2, 4, 9);
		this.Right_Shell_Bottom.setRotationPoint(-8F, 6F, -5.5F);
		this.Right_Shell_Bottom.setTextureSize(128, 64);
		this.Right_Shell_Bottom.mirror = true;
		this.setRotation(this.Right_Shell_Bottom, 0F, 0F, -0.6108652F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
			float scale)
	{
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		this.Body_Main.render(scale);
		this.Body_Base.render(scale);
		this.Front_Leg_Left.render(scale);
		this.Front_Leg_Right.render(scale);
		this.Back_Leg_Right.render(scale);
		this.Back_Leg_Left.render(scale);
		this.Tail_1.render(scale);
		this.Tail_2.render(scale);
		this.Tail_Spines_1.render(scale);
		this.Tail_Spines_2.render(scale);
		this.Neck.render(scale);
		this.Head_Main.render(scale);
		this.Head_Top_Left.render(scale);
		this.Head_Top_Right.render(scale);
		this.Head_Bottom_Left.render(scale);
		this.Head_Bottom_Right.render(scale);
		this.Body_Stem.render(scale);
		this.Left_Shell_Main.render(scale);
		this.Right_Shell_Main.render(scale);
		this.Left_Shell_Front.render(scale);
		this.Right_Shell_Front.render(scale);
		this.Left_Shell_Back.render(scale);
		this.Right_Shell_Back.render(scale);
		this.Left_Shell_Top.render(scale);
		this.Right_Shell_Top.render(scale);
		this.Left_Shell_Bottom.render(scale);
		this.Right_Shell_Bottom.render(scale);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		this.Front_Leg_Left.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
		this.Front_Leg_Right.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;

		this.Back_Leg_Left.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		this.Back_Leg_Right.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;

		this.Head_Main.rotateAngleX = (f4 / 107.29578F);
		this.Head_Main.rotateAngleY = (f3 / 107.29578F);

		float flapAnim = (float) (Math.sin(f2 * 20 / 87.2957795) * 1.0F) / 20;
		float flapAnimOpposite = (float) (Math.cos(f2 * 20 / 87.2957795) * 1.0F) / 20;

		this.Tail_1.rotateAngleX = -0.3490659F + flapAnimOpposite;
		this.Tail_2.rotateAngleX = flapAnim;

		this.Tail_Spines_1.rotateAngleX = this.Tail_1.rotateAngleX;
		this.Tail_Spines_2.rotateAngleX = this.Tail_2.rotateAngleX;

		this.Neck.rotateAngleX = this.Head_Main.rotateAngleX;
		this.Neck.rotateAngleY = this.Head_Main.rotateAngleY;
		this.Head_Bottom_Left.rotateAngleX = this.Head_Main.rotateAngleX;
		this.Head_Bottom_Left.rotateAngleY = this.Head_Main.rotateAngleY + flapAnim;
		this.Head_Bottom_Right.rotateAngleX = this.Head_Main.rotateAngleX;
		this.Head_Bottom_Right.rotateAngleY = this.Head_Main.rotateAngleY + flapAnimOpposite;
		this.Head_Top_Left.rotateAngleX = this.Head_Main.rotateAngleX;
		this.Head_Top_Left.rotateAngleY = this.Head_Main.rotateAngleY + flapAnim;
		this.Head_Top_Right.rotateAngleX = this.Head_Main.rotateAngleX;
		this.Head_Top_Right.rotateAngleY = this.Head_Main.rotateAngleY + flapAnimOpposite;

		this.Left_Shell_Front.rotateAngleY = this.Left_Shell_Main.rotateAngleY + 0.6108652F;
		this.Left_Shell_Back.rotateAngleY = this.Left_Shell_Main.rotateAngleY - 0.6108652F;
		this.Left_Shell_Top.rotateAngleY = this.Left_Shell_Main.rotateAngleY;
		this.Left_Shell_Bottom.rotateAngleY = this.Left_Shell_Main.rotateAngleY;

		this.Right_Shell_Front.rotateAngleY = this.Right_Shell_Main.rotateAngleY - 0.6108652F;
		this.Right_Shell_Back.rotateAngleY = this.Right_Shell_Main.rotateAngleY + 0.6108652F;
		this.Right_Shell_Top.rotateAngleY = this.Right_Shell_Main.rotateAngleY;
		this.Right_Shell_Bottom.rotateAngleY = this.Right_Shell_Main.rotateAngleY;

		this.Left_Shell_Main.rotateAngleY = (float) (Math.sin(f2 * 20 / 87.2957795) * 4.0F) / 20;
		this.Right_Shell_Main.rotateAngleY = (float) (Math.cos(f2 * 20 / 87.2957795) * 4.0F) / 20;
	}
}
