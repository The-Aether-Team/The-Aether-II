package com.gildedgames.aether.client.models.entities.companions;

import com.gildedgames.aether.common.entities.companions.EntityFrostpineTotem;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;

public class ModelFrostpineTotem extends EntityModel<EntityFrostpineTotem>
{
	final RendererModel Moa_Body;

	final RendererModel Moa_Head;

	final RendererModel Moa_Neck;

	final RendererModel Moa_Left_Wing_Main_1;

	final RendererModel Moa_Right_Wing_Main_1;

	final RendererModel Moa_Left_Wing_Main_2;

	final RendererModel Moa_Right_Wing_Main_2;

	final RendererModel Moa_Left_Wing_Sub_Top;

	final RendererModel Moa_Right_Wing_Sub_Top;

	final RendererModel Moa_Left_Wing_Sub_Mid;

	final RendererModel Moa_Right_Wing_Sub_Mid;

	final RendererModel Moa_Left_Wing_Sub_Bottom;

	final RendererModel Moa_Right_Wing_Sub_Bottom;

	final RendererModel Moa_Tail_Main;

	final RendererModel Moa_Tail_Feather_Left_1;

	final RendererModel Moa_Tail_Feather_Right_1;

	final RendererModel Moa_Tail_Feather_Left_2;

	final RendererModel Moa_Tail_Feather_Right_2;

	final RendererModel Moa_Leg_Left;

	final RendererModel Moa_Leg_Right;

	final RendererModel Moa_Lower_Leg_Left;

	final RendererModel Moa_Lower_Leg_Right;

	final RendererModel Gruegar_Head;

	final RendererModel Gruegar_Ear_Left;

	final RendererModel Gruegar_Ear_Right;

	final RendererModel Gruegar_Muzzle;

	final RendererModel Gruegar_Neck;

	final RendererModel Gruegar_Shell_Top;

	final RendererModel Gruegar_Shell_Middle;

	final RendererModel Gruegar_Shell_Lower;

	final RendererModel Gruegar_Body;

	final RendererModel Gruegar_Shoulder_Left;

	final RendererModel Gruegar_Shoulder_Right;

	final RendererModel Gruegar_Upper_Arm_Left;

	final RendererModel Gruegar_Upper_Arm_Right;

	final RendererModel Gruegar_Forearm_Left;

	final RendererModel Gruegar_Forearm_Right;

	final RendererModel Gruegar_Paw_Left;

	final RendererModel Gruegar_Paw_Right;

	final RendererModel Gruegar_Leg_Left;

	final RendererModel Gruegar_Leg_Right;

	final RendererModel Gruegar_Tail;

	final RendererModel Zephyr_Body_1;

	final RendererModel Zephyr_Body_2;

	final RendererModel Zephyr_Rib_Left_1;

	final RendererModel Zephyr_Rib_Left_2;

	final RendererModel Zephyr_Rib_Right_1;

	final RendererModel Zephyr_Rib_Right_2;

	final RendererModel Zeohyr_Back_Plate_Left;

	final RendererModel Zephyr_Back_Plate_Right;

	final RendererModel Zephyr_Tail_1;

	final RendererModel Zephyr_Tail_2;

	final RendererModel Zephyr_Jaw_Right;

	final RendererModel Zephyr_Jaw_Left;

	final RendererModel Zephyr_Mouth;

	public ModelFrostpineTotem()
	{
		this.textureWidth = 64;
		this.textureHeight = 256;

		this.Moa_Body = new RendererModel(this, 16, 19);
		this.Moa_Body.addBox(-2F, -9F, -1F, 4, 7, 6);
		this.Moa_Body.setRotationPoint(0F, 0F, 0F);
		this.Moa_Body.setTextureSize(64, 32);
		this.Moa_Body.mirror = true;
		this.setRotation(this.Moa_Body, 0.4363323F, 0F, 0F);
		this.Moa_Head = new RendererModel(this, 13, 0);
		this.Moa_Head.addBox(-2.5F, -11F, 0F, 5, 4, 8);
		this.Moa_Head.setRotationPoint(0F, 0F, 0F);
		this.Moa_Head.setTextureSize(64, 32);
		this.Moa_Head.mirror = true;
		this.setRotation(this.Moa_Head, 1.047198F, 0F, 0F);
		this.Moa_Neck = new RendererModel(this, 19, 12);
		this.Moa_Neck.addBox(-1.5F, -9.5F, -6.5F, 3, 3, 4);
		this.Moa_Neck.setRotationPoint(0F, 0F, 0F);
		this.Moa_Neck.setTextureSize(64, 32);
		this.Moa_Neck.mirror = true;
		this.setRotation(this.Moa_Neck, -0.2792527F, 0F, 0F);
		this.Moa_Left_Wing_Main_1 = new RendererModel(this, 26, 37);
		this.Moa_Left_Wing_Main_1.addBox(4.5F, -4F, -2F, 11, 4, 1);
		this.Moa_Left_Wing_Main_1.setRotationPoint(0F, 0F, 0F);
		this.Moa_Left_Wing_Main_1.setTextureSize(64, 32);
		this.Moa_Left_Wing_Main_1.mirror = true;
		this.setRotation(this.Moa_Left_Wing_Main_1, 0F, 0F, -1.047198F);
		this.Moa_Right_Wing_Main_1 = new RendererModel(this, 2, 37);
		this.Moa_Right_Wing_Main_1.addBox(-15F, -4F, -2F, 11, 4, 1);
		this.Moa_Right_Wing_Main_1.setRotationPoint(0F, 0F, 0F);
		this.Moa_Right_Wing_Main_1.setTextureSize(64, 32);
		this.Moa_Right_Wing_Main_1.mirror = true;
		this.setRotation(this.Moa_Right_Wing_Main_1, 0F, 0F, 1.047198F);
		this.Moa_Left_Wing_Main_2 = new RendererModel(this, 26, 32);
		this.Moa_Left_Wing_Main_2.addBox(8F, 0F, -2F, 10, 4, 1);
		this.Moa_Left_Wing_Main_2.setRotationPoint(0F, 0F, 0F);
		this.Moa_Left_Wing_Main_2.setTextureSize(64, 32);
		this.Moa_Left_Wing_Main_2.mirror = true;
		this.setRotation(this.Moa_Left_Wing_Main_2, 0F, 0F, -1.047198F);
		this.Moa_Right_Wing_Main_2 = new RendererModel(this, 4, 32);
		this.Moa_Right_Wing_Main_2.addBox(-18F, 0F, -2F, 10, 4, 1);
		this.Moa_Right_Wing_Main_2.setRotationPoint(0F, 0F, 0F);
		this.Moa_Right_Wing_Main_2.setTextureSize(64, 32);
		this.Moa_Right_Wing_Main_2.mirror = true;
		this.setRotation(this.Moa_Right_Wing_Main_2, 0F, 0F, 1.047198F);
		this.Moa_Left_Wing_Sub_Top = new RendererModel(this, 26, 42);
		this.Moa_Left_Wing_Sub_Top.addBox(5F, -15F, -2.5F, 11, 3, 2);
		this.Moa_Left_Wing_Sub_Top.setRotationPoint(0F, 0F, 0F);
		this.Moa_Left_Wing_Sub_Top.setTextureSize(64, 32);
		this.Moa_Left_Wing_Sub_Top.mirror = true;
		this.setRotation(this.Moa_Left_Wing_Sub_Top, 0F, 0F, -0.0872665F);
		this.Moa_Right_Wing_Sub_Top = new RendererModel(this, 0, 42);
		this.Moa_Right_Wing_Sub_Top.addBox(-16F, -15F, -2.5F, 11, 3, 2);
		this.Moa_Right_Wing_Sub_Top.setRotationPoint(0F, 0F, 0F);
		this.Moa_Right_Wing_Sub_Top.setTextureSize(64, 32);
		this.Moa_Right_Wing_Sub_Top.mirror = true;
		this.setRotation(this.Moa_Right_Wing_Sub_Top, 0F, 0F, 0.0872665F);
		this.Moa_Left_Wing_Sub_Mid = new RendererModel(this, 26, 47);
		this.Moa_Left_Wing_Sub_Mid.addBox(4F, -11F, -2.5F, 9, 3, 2);
		this.Moa_Left_Wing_Sub_Mid.setRotationPoint(0F, 0F, 0F);
		this.Moa_Left_Wing_Sub_Mid.setTextureSize(64, 32);
		this.Moa_Left_Wing_Sub_Mid.mirror = true;
		this.setRotation(this.Moa_Left_Wing_Sub_Mid, 0F, 0F, -0.0872665F);
		this.Moa_Right_Wing_Sub_Mid = new RendererModel(this, 4, 47);
		this.Moa_Right_Wing_Sub_Mid.addBox(-13F, -11F, -2.5F, 9, 3, 2);
		this.Moa_Right_Wing_Sub_Mid.setRotationPoint(0F, 0F, 0F);
		this.Moa_Right_Wing_Sub_Mid.setTextureSize(64, 32);
		this.Moa_Right_Wing_Sub_Mid.mirror = true;
		this.setRotation(this.Moa_Right_Wing_Sub_Mid, 0F, 0F, 0.0872665F);
		this.Moa_Left_Wing_Sub_Bottom = new RendererModel(this, 26, 52);
		this.Moa_Left_Wing_Sub_Bottom.addBox(3F, -7F, -2.5F, 7, 3, 2);
		this.Moa_Left_Wing_Sub_Bottom.setRotationPoint(0F, 0F, 0F);
		this.Moa_Left_Wing_Sub_Bottom.setTextureSize(64, 32);
		this.Moa_Left_Wing_Sub_Bottom.mirror = true;
		this.setRotation(this.Moa_Left_Wing_Sub_Bottom, 0F, 0F, -0.0872665F);
		this.Moa_Right_Wing_Sub_Bottom = new RendererModel(this, 8, 52);
		this.Moa_Right_Wing_Sub_Bottom.addBox(-10F, -7F, -2.5F, 7, 3, 2);
		this.Moa_Right_Wing_Sub_Bottom.setRotationPoint(0F, 0F, 0F);
		this.Moa_Right_Wing_Sub_Bottom.setTextureSize(64, 32);
		this.Moa_Right_Wing_Sub_Bottom.mirror = true;
		this.setRotation(this.Moa_Right_Wing_Sub_Bottom, 0F, 0F, 0.0872665F);
		this.Moa_Tail_Main = new RendererModel(this, 21, 57);
		this.Moa_Tail_Main.addBox(-1.5F, -2F, 4F, 3, 5, 2);
		this.Moa_Tail_Main.setRotationPoint(0F, 0F, 0F);
		this.Moa_Tail_Main.setTextureSize(64, 32);
		this.Moa_Tail_Main.mirror = true;
		this.setRotation(this.Moa_Tail_Main, 0.7853982F, 0F, 0F);
		this.Moa_Tail_Feather_Left_1 = new RendererModel(this, 31, 58);
		this.Moa_Tail_Feather_Left_1.addBox(1F, -2F, 4.5F, 6, 2, 1);
		this.Moa_Tail_Feather_Left_1.setRotationPoint(0F, 0F, 0F);
		this.Moa_Tail_Feather_Left_1.setTextureSize(64, 32);
		this.Moa_Tail_Feather_Left_1.mirror = true;
		this.setRotation(this.Moa_Tail_Feather_Left_1, 0.7853982F, -0.2617994F, 0.1745329F);
		this.Moa_Tail_Feather_Right_1 = new RendererModel(this, 7, 58);
		this.Moa_Tail_Feather_Right_1.addBox(-7F, -2F, 4.5F, 6, 2, 1);
		this.Moa_Tail_Feather_Right_1.setRotationPoint(0F, 0F, 0F);
		this.Moa_Tail_Feather_Right_1.setTextureSize(64, 32);
		this.Moa_Tail_Feather_Right_1.mirror = true;
		this.setRotation(this.Moa_Tail_Feather_Right_1, 0.7853982F, 0.2617994F, -0.1745329F);
		this.Moa_Tail_Feather_Left_2 = new RendererModel(this, 31, 61);
		this.Moa_Tail_Feather_Left_2.addBox(2F, -1F, 4.5F, 5, 2, 1);
		this.Moa_Tail_Feather_Left_2.setRotationPoint(0F, 0F, 0F);
		this.Moa_Tail_Feather_Left_2.setTextureSize(64, 32);
		this.Moa_Tail_Feather_Left_2.mirror = true;
		this.setRotation(this.Moa_Tail_Feather_Left_2, 0.7853982F, -0.6108652F, 0.5235988F);
		this.Moa_Tail_Feather_Right_2 = new RendererModel(this, 9, 61);
		this.Moa_Tail_Feather_Right_2.addBox(-7F, -1F, 4.5F, 5, 2, 1);
		this.Moa_Tail_Feather_Right_2.setRotationPoint(0F, 0F, 0F);
		this.Moa_Tail_Feather_Right_2.setTextureSize(64, 32);
		this.Moa_Tail_Feather_Right_2.mirror = true;
		this.setRotation(this.Moa_Tail_Feather_Right_2, 0.7853982F, 0.6108652F, -0.5235988F);
		this.Moa_Leg_Left = new RendererModel(this, 18, 64);
		this.Moa_Leg_Left.addBox(-3.5F, -4F, -5F, 2, 5, 2);
		this.Moa_Leg_Left.setRotationPoint(0F, 0F, 0F);
		this.Moa_Leg_Left.setTextureSize(64, 32);
		this.Moa_Leg_Left.mirror = true;
		this.setRotation(this.Moa_Leg_Left, -0.4363323F, 0F, 0F);
		this.Moa_Leg_Right = new RendererModel(this, 26, 64);
		this.Moa_Leg_Right.addBox(1.5F, -4F, -5F, 2, 5, 2);
		this.Moa_Leg_Right.setRotationPoint(0F, 0F, 0F);
		this.Moa_Leg_Right.setTextureSize(64, 32);
		this.Moa_Leg_Right.mirror = true;
		this.setRotation(this.Moa_Leg_Right, -0.4363323F, 0F, 0F);
		this.Moa_Lower_Leg_Left = new RendererModel(this, 34, 65);
		this.Moa_Lower_Leg_Left.addBox(2F, -3F, -7.5F, 1, 2, 4);
		this.Moa_Lower_Leg_Left.setRotationPoint(0F, 0F, 0F);
		this.Moa_Lower_Leg_Left.setTextureSize(64, 32);
		this.Moa_Lower_Leg_Left.mirror = true;
		this.setRotation(this.Moa_Lower_Leg_Left, 0F, 0F, 0F);
		this.Moa_Lower_Leg_Right = new RendererModel(this, 8, 65);
		this.Moa_Lower_Leg_Right.addBox(-3F, -3F, -7.5F, 1, 2, 4);
		this.Moa_Lower_Leg_Right.setRotationPoint(0F, 0F, 0F);
		this.Moa_Lower_Leg_Right.setTextureSize(64, 32);
		this.Moa_Lower_Leg_Right.mirror = true;
		this.setRotation(this.Moa_Lower_Leg_Right, 0F, 0F, 0F);
		this.Gruegar_Head = new RendererModel(this, 8, 76);
		this.Gruegar_Head.addBox(-4.5F, -2F, -7F, 9, 6, 5);
		this.Gruegar_Head.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Head.setTextureSize(64, 32);
		this.Gruegar_Head.mirror = true;
		this.setRotation(this.Gruegar_Head, 0F, 0F, 0F);
		this.Gruegar_Ear_Left = new RendererModel(this, 36, 84);
		this.Gruegar_Ear_Left.addBox(3F, -3F, -5F, 2, 1, 2);
		this.Gruegar_Ear_Left.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Ear_Left.setTextureSize(64, 32);
		this.Gruegar_Ear_Left.mirror = true;
		this.setRotation(this.Gruegar_Ear_Left, 0F, 0F, 0.4363323F);
		this.Gruegar_Ear_Right = new RendererModel(this, 0, 84);
		this.Gruegar_Ear_Right.addBox(-5F, -3F, -5F, 2, 1, 2);
		this.Gruegar_Ear_Right.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Ear_Right.setTextureSize(64, 32);
		this.Gruegar_Ear_Right.mirror = true;
		this.setRotation(this.Gruegar_Ear_Right, 0F, 0F, -0.4363323F);
		this.Gruegar_Muzzle = new RendererModel(this, 16, 87);
		this.Gruegar_Muzzle.addBox(-1.5F, 5F, -4F, 3, 3, 3);
		this.Gruegar_Muzzle.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Muzzle.setTextureSize(64, 32);
		this.Gruegar_Muzzle.mirror = true;
		this.setRotation(this.Gruegar_Muzzle, -0.6981317F, 0F, 0F);
		this.Gruegar_Neck = new RendererModel(this, 14, 93);
		this.Gruegar_Neck.addBox(-1.5F, -1F, -3F, 3, 3, 5);
		this.Gruegar_Neck.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Neck.setTextureSize(64, 32);
		this.Gruegar_Neck.mirror = true;
		this.setRotation(this.Gruegar_Neck, -0.4363323F, 0F, 0F);
		this.Gruegar_Shell_Top = new RendererModel(this, 13, 101);
		this.Gruegar_Shell_Top.addBox(-3F, -3F, 1F, 6, 6, 3);
		this.Gruegar_Shell_Top.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Shell_Top.setTextureSize(64, 32);
		this.Gruegar_Shell_Top.mirror = true;
		this.setRotation(this.Gruegar_Shell_Top, 0.3490659F, -0.2443461F, 0.7853982F);
		this.Gruegar_Shell_Top.mirror = false;
		this.Gruegar_Shell_Middle = new RendererModel(this, 8, 110);
		this.Gruegar_Shell_Middle.addBox(-5F, -1F, 1F, 10, 8, 4);
		this.Gruegar_Shell_Middle.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Shell_Middle.setTextureSize(64, 32);
		this.Gruegar_Shell_Middle.mirror = true;
		this.setRotation(this.Gruegar_Shell_Middle, 0F, 0F, 0F);
		this.Gruegar_Shell_Lower = new RendererModel(this, 12, 122);
		this.Gruegar_Shell_Lower.addBox(1F, 1F, 2F, 7, 7, 3);
		this.Gruegar_Shell_Lower.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Shell_Lower.setTextureSize(64, 32);
		this.Gruegar_Shell_Lower.mirror = true;
		this.setRotation(this.Gruegar_Shell_Lower, -0.0698132F, 0.0523599F, 0.7853982F);
		this.Gruegar_Body = new RendererModel(this, 8, 132);
		this.Gruegar_Body.addBox(-4F, 0F, -5F, 8, 11, 6);
		this.Gruegar_Body.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Body.setTextureSize(64, 32);
		this.Gruegar_Body.mirror = true;
		this.setRotation(this.Gruegar_Body, 0F, 0F, 0F);
		this.Gruegar_Shoulder_Left = new RendererModel(this, 22, 149);
		this.Gruegar_Shoulder_Left.addBox(2F, -3F, -5F, 5, 6, 6);
		this.Gruegar_Shoulder_Left.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Shoulder_Left.setTextureSize(64, 32);
		this.Gruegar_Shoulder_Left.mirror = true;
		this.setRotation(this.Gruegar_Shoulder_Left, 0.7853982F, 0F, 0.1745329F);
		this.Gruegar_Shoulder_Right = new RendererModel(this, 0, 149);
		this.Gruegar_Shoulder_Right.addBox(-7F, -1F, -3F, 5, 6, 6);
		this.Gruegar_Shoulder_Right.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Shoulder_Right.setTextureSize(64, 32);
		this.Gruegar_Shoulder_Right.mirror = true;
		this.setRotation(this.Gruegar_Shoulder_Right, -0.7853982F, 0F, -0.1745329F);
		this.Gruegar_Upper_Arm_Left = new RendererModel(this, 22, 161);
		this.Gruegar_Upper_Arm_Left.addBox(4F, 1F, -3F, 3, 5, 3);
		this.Gruegar_Upper_Arm_Left.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Upper_Arm_Left.setTextureSize(64, 32);
		this.Gruegar_Upper_Arm_Left.mirror = true;
		this.setRotation(this.Gruegar_Upper_Arm_Left, 0F, 0F, 0F);
		this.Gruegar_Upper_Arm_Right = new RendererModel(this, 10, 161);
		this.Gruegar_Upper_Arm_Right.addBox(-7F, 1F, -3F, 3, 5, 3);
		this.Gruegar_Upper_Arm_Right.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Upper_Arm_Right.setTextureSize(64, 32);
		this.Gruegar_Upper_Arm_Right.mirror = true;
		this.setRotation(this.Gruegar_Upper_Arm_Right, 0F, 0F, 0F);
		this.Gruegar_Forearm_Left = new RendererModel(this, 22, 169);
		this.Gruegar_Forearm_Left.addBox(5F, 5F, -6F, 3, 4, 7);
		this.Gruegar_Forearm_Left.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Forearm_Left.setTextureSize(64, 32);
		this.Gruegar_Forearm_Left.mirror = true;
		this.setRotation(this.Gruegar_Forearm_Left, 0F, 0.0872665F, 0.0872665F);
		this.Gruegar_Forearm_Right = new RendererModel(this, 2, 169);
		this.Gruegar_Forearm_Right.addBox(-8F, 5F, -6F, 3, 4, 7);
		this.Gruegar_Forearm_Right.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Forearm_Right.setTextureSize(64, 32);
		this.Gruegar_Forearm_Right.mirror = true;
		this.setRotation(this.Gruegar_Forearm_Right, 0F, -0.0872665F, -0.0872665F);
		this.Gruegar_Paw_Left = new RendererModel(this, 22, 180);
		this.Gruegar_Paw_Left.addBox(2F, 5F, -8F, 4, 5, 2);
		this.Gruegar_Paw_Left.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Paw_Left.setTextureSize(64, 32);
		this.Gruegar_Paw_Left.mirror = true;
		this.setRotation(this.Gruegar_Paw_Left, 0F, -0.0872665F, 0F);
		this.Gruegar_Paw_Right = new RendererModel(this, 10, 180);
		this.Gruegar_Paw_Right.addBox(-6F, 5F, -8F, 4, 5, 2);
		this.Gruegar_Paw_Right.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Paw_Right.setTextureSize(64, 32);
		this.Gruegar_Paw_Right.mirror = true;
		this.setRotation(this.Gruegar_Paw_Right, 0F, 0.0872665F, 0F);
		this.Gruegar_Leg_Left = new RendererModel(this, 22, 187);
		this.Gruegar_Leg_Left.addBox(0.5F, 7.5F, -7.5F, 3, 4, 3);
		this.Gruegar_Leg_Left.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Leg_Left.setTextureSize(64, 32);
		this.Gruegar_Leg_Left.mirror = true;
		this.setRotation(this.Gruegar_Leg_Left, 0F, 0F, 0F);
		this.Gruegar_Leg_Right = new RendererModel(this, 10, 187);
		this.Gruegar_Leg_Right.addBox(-3.5F, 7.5F, -7.5F, 3, 4, 3);
		this.Gruegar_Leg_Right.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Leg_Right.setTextureSize(64, 32);
		this.Gruegar_Leg_Right.mirror = true;
		this.setRotation(this.Gruegar_Leg_Right, 0F, 0F, 0F);
		this.Gruegar_Tail = new RendererModel(this, 16, 194);
		this.Gruegar_Tail.addBox(-1F, 9F, 4F, 2, 1, 4);
		this.Gruegar_Tail.setRotationPoint(0F, 0F, 0F);
		this.Gruegar_Tail.setTextureSize(64, 32);
		this.Gruegar_Tail.mirror = true;
		this.setRotation(this.Gruegar_Tail, -0.3490659F, 0F, 0F);
		this.Zephyr_Body_1 = new RendererModel(this, 0, 204);
		this.Zephyr_Body_1.addBox(-5.5F, 11F, -7F, 11, 4, 8);
		this.Zephyr_Body_1.setRotationPoint(0F, 0F, 0F);
		this.Zephyr_Body_1.setTextureSize(64, 32);
		this.Zephyr_Body_1.mirror = true;
		this.setRotation(this.Zephyr_Body_1, 0F, 0F, 0F);
		this.Zephyr_Body_2 = new RendererModel(this, 11, 216);
		this.Zephyr_Body_2.addBox(-1.5F, 14F, -7F, 3, 3, 5);
		this.Zephyr_Body_2.setRotationPoint(0F, 0F, 0F);
		this.Zephyr_Body_2.setTextureSize(64, 32);
		this.Zephyr_Body_2.mirror = true;
		this.setRotation(this.Zephyr_Body_2, 0.1745329F, 0F, 0F);
		this.Zephyr_Rib_Left_1 = new RendererModel(this, 27, 216);
		this.Zephyr_Rib_Left_1.addBox(6.5F, 13.5F, -3F, 1, 3, 1);
		this.Zephyr_Rib_Left_1.setRotationPoint(0F, 0F, 0F);
		this.Zephyr_Rib_Left_1.setTextureSize(64, 32);
		this.Zephyr_Rib_Left_1.mirror = true;
		this.setRotation(this.Zephyr_Rib_Left_1, 0F, 0F, 0.1745329F);
		this.Zephyr_Rib_Left_2 = new RendererModel(this, 32, 216);
		this.Zephyr_Rib_Left_2.addBox(6.5F, 13.5F, -1F, 1, 3, 1);
		this.Zephyr_Rib_Left_2.setRotationPoint(0F, 0F, 0F);
		this.Zephyr_Rib_Left_2.setTextureSize(64, 32);
		this.Zephyr_Rib_Left_2.mirror = true;
		this.setRotation(this.Zephyr_Rib_Left_2, 0F, 0F, 0.1745329F);
		this.Zephyr_Rib_Right_1 = new RendererModel(this, 7, 216);
		this.Zephyr_Rib_Right_1.addBox(-7.5F, 13.5F, -3F, 1, 3, 1);
		this.Zephyr_Rib_Right_1.setRotationPoint(0F, 0F, 0F);
		this.Zephyr_Rib_Right_1.setTextureSize(64, 32);
		this.Zephyr_Rib_Right_1.mirror = true;
		this.setRotation(this.Zephyr_Rib_Right_1, 0F, 0F, -0.1745329F);
		this.Zephyr_Rib_Right_2 = new RendererModel(this, 2, 216);
		this.Zephyr_Rib_Right_2.addBox(-7.5F, 13.5F, -1F, 1, 3, 1);
		this.Zephyr_Rib_Right_2.setRotationPoint(0F, 0F, 0F);
		this.Zephyr_Rib_Right_2.setTextureSize(64, 32);
		this.Zephyr_Rib_Right_2.mirror = true;
		this.setRotation(this.Zephyr_Rib_Right_2, 0F, 0F, -0.1745329F);
		this.Zeohyr_Back_Plate_Left = new RendererModel(this, 19, 224);
		this.Zeohyr_Back_Plate_Left.addBox(3F, 11F, 2F, 4, 2, 4);
		this.Zeohyr_Back_Plate_Left.setRotationPoint(0F, 0F, 0F);
		this.Zeohyr_Back_Plate_Left.setTextureSize(64, 32);
		this.Zeohyr_Back_Plate_Left.mirror = true;
		this.setRotation(this.Zeohyr_Back_Plate_Left, -0.1745329F, -0.1745329F, 0.1745329F);
		this.Zephyr_Back_Plate_Right = new RendererModel(this, 3, 224);
		this.Zephyr_Back_Plate_Right.addBox(-7F, 11F, 2F, 4, 2, 4);
		this.Zephyr_Back_Plate_Right.setRotationPoint(0F, 0F, 0F);
		this.Zephyr_Back_Plate_Right.setTextureSize(64, 32);
		this.Zephyr_Back_Plate_Right.mirror = true;
		this.setRotation(this.Zephyr_Back_Plate_Right, -0.1745329F, 0.1745329F, -0.1745329F);
		this.Zephyr_Tail_1 = new RendererModel(this, 28, 239);
		this.Zephyr_Tail_1.addBox(-1F, 11F, 2F, 2, 4, 8);
		this.Zephyr_Tail_1.setRotationPoint(0F, 0F, 0F);
		this.Zephyr_Tail_1.setTextureSize(64, 32);
		this.Zephyr_Tail_1.mirror = true;
		this.setRotation(this.Zephyr_Tail_1, -0.3490659F, 0F, 0F);
		this.Zephyr_Tail_2 = new RendererModel(this, 33, 251);
		this.Zephyr_Tail_2.addBox(-1F, 11F, 10F, 2, 2, 3);
		this.Zephyr_Tail_2.setRotationPoint(0F, 0F, 0F);
		this.Zephyr_Tail_2.setTextureSize(64, 32);
		this.Zephyr_Tail_2.mirror = true;
		this.setRotation(this.Zephyr_Tail_2, -0.3490659F, 0F, 0F);
		this.Zephyr_Jaw_Right = new RendererModel(this, 9, 230);
		this.Zephyr_Jaw_Right.addBox(-5F, 15F, -7F, 2, 3, 3);
		this.Zephyr_Jaw_Right.setRotationPoint(0F, 0F, 0F);
		this.Zephyr_Jaw_Right.setTextureSize(64, 32);
		this.Zephyr_Jaw_Right.mirror = true;
		this.setRotation(this.Zephyr_Jaw_Right, 0F, 0F, 0F);
		this.Zephyr_Jaw_Left = new RendererModel(this, 19, 230);
		this.Zephyr_Jaw_Left.addBox(3F, 15F, -7F, 2, 3, 3);
		this.Zephyr_Jaw_Left.setRotationPoint(0F, 0F, 0F);
		this.Zephyr_Jaw_Left.setTextureSize(64, 32);
		this.Zephyr_Jaw_Left.mirror = true;
		this.setRotation(this.Zephyr_Jaw_Left, 0F, 0F, 0F);
		this.Zephyr_Mouth = new RendererModel(this, 10, 236);
		this.Zephyr_Mouth.addBox(8F, 8F, -6F, 6, 6, 3);
		this.Zephyr_Mouth.setRotationPoint(0F, 0F, 0F);
		this.Zephyr_Mouth.setTextureSize(64, 32);
		this.Zephyr_Mouth.mirror = true;
		this.setRotation(this.Zephyr_Mouth, 0F, 0F, 0.7853982F);
	}

	@Override
	public void render(EntityFrostpineTotem entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		this.Moa_Body.render(scale);
		this.Moa_Head.render(scale);
		this.Moa_Neck.render(scale);
		this.Moa_Left_Wing_Main_1.render(scale);
		this.Moa_Right_Wing_Main_1.render(scale);
		this.Moa_Left_Wing_Main_2.render(scale);
		this.Moa_Right_Wing_Main_2.render(scale);
		this.Moa_Left_Wing_Sub_Top.render(scale);
		this.Moa_Right_Wing_Sub_Top.render(scale);
		this.Moa_Left_Wing_Sub_Mid.render(scale);
		this.Moa_Right_Wing_Sub_Mid.render(scale);
		this.Moa_Left_Wing_Sub_Bottom.render(scale);
		this.Moa_Right_Wing_Sub_Bottom.render(scale);
		this.Moa_Tail_Main.render(scale);
		this.Moa_Tail_Feather_Left_1.render(scale);
		this.Moa_Tail_Feather_Right_1.render(scale);
		this.Moa_Tail_Feather_Left_2.render(scale);
		this.Moa_Tail_Feather_Right_2.render(scale);
		this.Moa_Leg_Left.render(scale);
		this.Moa_Leg_Right.render(scale);
		this.Moa_Lower_Leg_Left.render(scale);
		this.Moa_Lower_Leg_Right.render(scale);
		this.Gruegar_Head.render(scale);
		this.Gruegar_Ear_Left.render(scale);
		this.Gruegar_Ear_Right.render(scale);
		this.Gruegar_Muzzle.render(scale);
		this.Gruegar_Neck.render(scale);
		this.Gruegar_Shell_Top.render(scale);
		this.Gruegar_Shell_Middle.render(scale);
		this.Gruegar_Shell_Lower.render(scale);
		this.Gruegar_Body.render(scale);
		this.Gruegar_Shoulder_Left.render(scale);
		this.Gruegar_Shoulder_Right.render(scale);
		this.Gruegar_Upper_Arm_Left.render(scale);
		this.Gruegar_Upper_Arm_Right.render(scale);
		this.Gruegar_Forearm_Left.render(scale);
		this.Gruegar_Forearm_Right.render(scale);
		this.Gruegar_Paw_Left.render(scale);
		this.Gruegar_Paw_Right.render(scale);
		this.Gruegar_Leg_Left.render(scale);
		this.Gruegar_Leg_Right.render(scale);
		this.Gruegar_Tail.render(scale);
		this.Zephyr_Body_1.render(scale);
		this.Zephyr_Body_2.render(scale);
		this.Zephyr_Rib_Left_1.render(scale);
		this.Zephyr_Rib_Left_2.render(scale);
		this.Zephyr_Rib_Right_1.render(scale);
		this.Zephyr_Rib_Right_2.render(scale);
		this.Zeohyr_Back_Plate_Left.render(scale);
		this.Zephyr_Back_Plate_Right.render(scale);
		this.Zephyr_Tail_1.render(scale);
		this.Zephyr_Tail_2.render(scale);
		this.Zephyr_Jaw_Right.render(scale);
		this.Zephyr_Jaw_Left.render(scale);
		this.Zephyr_Mouth.render(scale);
	}

	private void setRotation(RendererModel model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
