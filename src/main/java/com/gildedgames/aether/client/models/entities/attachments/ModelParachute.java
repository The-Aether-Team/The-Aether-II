package com.gildedgames.aether.client.models.entities.attachments;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelParachute extends ModelBase
{
	public final ModelRenderer Canopy_Main;
	public final ModelRenderer Canopy_2nd_Right;
	public final ModelRenderer Canopy_2nd_Left;
	public final ModelRenderer Canopy_3rd_Right;
	public final ModelRenderer Canopy_3rd_Left;
	public final ModelRenderer Cord_Front_Left;
	public final ModelRenderer Cord_Front_Right;
	public final ModelRenderer Cord_Back_Left;
	public final ModelRenderer Cord_Back_Right;
	public final ModelRenderer Cord_Middle_Left;
	public final ModelRenderer Cord_Middle_Right;
	public final ModelRenderer Chest_Strap;
	public final ModelRenderer Shoulder_Strap_Right;
	public final ModelRenderer Shoulder_Strap_Left;

	public ModelParachute()
	{
		textureWidth = 64;
		textureHeight = 128;

		Canopy_Main = new ModelRenderer(this, 0, 0);
		Canopy_Main.addBox(-7F, -15F, -8.5F, 14, 2, 17);
		Canopy_Main.setRotationPoint(0F, 0F, 0F);
		Canopy_Main.setTextureSize(64, 128);
		Canopy_Main.mirror = true;
		setRotation(Canopy_Main, 0F, 0F, 0F);
		Canopy_2nd_Right = new ModelRenderer(this, 0, 19);
		Canopy_2nd_Right.addBox(-8.5F, -16F, -7.5F, 8, 2, 15);
		Canopy_2nd_Right.setRotationPoint(0F, 0F, 0F);
		Canopy_2nd_Right.setTextureSize(64, 128);
		Canopy_2nd_Right.mirror = true;
		setRotation(Canopy_2nd_Right, 0F, 0F, -0.3490659F);
		Canopy_2nd_Right.mirror = false;
		Canopy_2nd_Left = new ModelRenderer(this, 0, 19);
		Canopy_2nd_Left.addBox(0.5F, -16F, -7.5F, 8, 2, 15);
		Canopy_2nd_Left.setRotationPoint(0F, 0F, 0F);
		Canopy_2nd_Left.setTextureSize(64, 128);
		Canopy_2nd_Left.mirror = true;
		setRotation(Canopy_2nd_Left, 0F, 0F, 0.3490659F);
		Canopy_3rd_Right = new ModelRenderer(this, 0, 36);
		Canopy_3rd_Right.addBox(-13F, -16.5F, -6.5F, 8, 1, 13);
		Canopy_3rd_Right.setRotationPoint(0F, 0F, 0F);
		Canopy_3rd_Right.setTextureSize(64, 128);
		Canopy_3rd_Right.mirror = true;
		setRotation(Canopy_3rd_Right, 0F, 0F, -0.5235988F);
		Canopy_3rd_Right.mirror = false;
		Canopy_3rd_Left = new ModelRenderer(this, 0, 36);
		Canopy_3rd_Left.addBox(5F, -16.5F, -6.5F, 8, 1, 13);
		Canopy_3rd_Left.setRotationPoint(0F, 0F, 0F);
		Canopy_3rd_Left.setTextureSize(64, 128);
		Canopy_3rd_Left.mirror = true;
		setRotation(Canopy_3rd_Left, 0F, 0F, 0.5235988F);
		Cord_Front_Left = new ModelRenderer(this, 0, 50);
		Cord_Front_Left.addBox(4F, -17F, -1F, 1, 15, 1);
		Cord_Front_Left.setRotationPoint(0F, 0F, 0F);
		Cord_Front_Left.setTextureSize(64, 128);
		Cord_Front_Left.mirror = true;
		setRotation(Cord_Front_Left, 0.3490659F, 0F, 0.5235988F);
		Cord_Front_Right = new ModelRenderer(this, 0, 50);
		Cord_Front_Right.addBox(-5F, -17F, -1F, 1, 15, 1);
		Cord_Front_Right.setRotationPoint(0F, 0F, 0F);
		Cord_Front_Right.setTextureSize(64, 128);
		Cord_Front_Right.mirror = true;
		setRotation(Cord_Front_Right, 0.3490659F, 0F, -0.5235988F);
		Cord_Back_Left = new ModelRenderer(this, 0, 50);
		Cord_Back_Left.addBox(4F, -17F, 0F, 1, 15, 1);
		Cord_Back_Left.setRotationPoint(0F, 0F, 0F);
		Cord_Back_Left.setTextureSize(64, 128);
		Cord_Back_Left.mirror = true;
		setRotation(Cord_Back_Left, -0.3490659F, 0F, 0.5235988F);
		Cord_Back_Right = new ModelRenderer(this, 0, 50);
		Cord_Back_Right.addBox(-5F, -17F, 0F, 1, 15, 1);
		Cord_Back_Right.setRotationPoint(0F, 0F, 0F);
		Cord_Back_Right.setTextureSize(64, 128);
		Cord_Back_Right.mirror = true;
		setRotation(Cord_Back_Right, -0.3490659F, 0F, -0.5235988F);
		Cord_Middle_Left = new ModelRenderer(this, 0, 50);
		Cord_Middle_Left.addBox(2F, -20F, -0.5F, 1, 15, 1);
		Cord_Middle_Left.setRotationPoint(0F, 0F, 0F);
		Cord_Middle_Left.setTextureSize(64, 128);
		Cord_Middle_Left.mirror = true;
		setRotation(Cord_Middle_Left, 0F, 0F, 1.047198F);
		Cord_Middle_Right = new ModelRenderer(this, 0, 50);
		Cord_Middle_Right.addBox(-3F, -20F, -0.5F, 1, 15, 1);
		Cord_Middle_Right.setRotationPoint(0F, 0F, 0F);
		Cord_Middle_Right.setTextureSize(64, 128);
		Cord_Middle_Right.mirror = true;
		setRotation(Cord_Middle_Right, 0F, 0F, -1.047198F);
		Chest_Strap = new ModelRenderer(this, 0, 66);
		Chest_Strap.addBox(-5F, 2F, -2.5F, 10, 1, 5);
		Chest_Strap.setRotationPoint(0F, 0F, 0F);
		Chest_Strap.setTextureSize(64, 128);
		Chest_Strap.mirror = true;
		setRotation(Chest_Strap, 0F, 0F, 0F);
		Shoulder_Strap_Right = new ModelRenderer(this, 0, 72);
		Shoulder_Strap_Right.addBox(-6F, -1F, -3F, 2, 5, 6);
		Shoulder_Strap_Right.setRotationPoint(0F, 0F, 0F);
		Shoulder_Strap_Right.setTextureSize(64, 128);
		Shoulder_Strap_Right.mirror = true;
		setRotation(Shoulder_Strap_Right, 0F, 0F, 0F);
		Shoulder_Strap_Left = new ModelRenderer(this, 0, 72);
		Shoulder_Strap_Left.addBox(4F, -1F, -3F, 2, 5, 6);
		Shoulder_Strap_Left.setRotationPoint(0F, 0F, 0F);
		Shoulder_Strap_Left.setTextureSize(64, 128);
		Shoulder_Strap_Left.mirror = true;
		setRotation(Shoulder_Strap_Left, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Canopy_Main.render(f5);
		Canopy_2nd_Right.render(f5);
		Canopy_2nd_Left.render(f5);
		Canopy_3rd_Right.render(f5);
		Canopy_3rd_Left.render(f5);
		Cord_Front_Left.render(f5);
		Cord_Front_Right.render(f5);
		Cord_Back_Left.render(f5);
		Cord_Back_Right.render(f5);
		Cord_Middle_Left.render(f5);
		Cord_Middle_Right.render(f5);
		Chest_Strap.render(f5);
		Shoulder_Strap_Right.render(f5);
		Shoulder_Strap_Left.render(f5);
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
