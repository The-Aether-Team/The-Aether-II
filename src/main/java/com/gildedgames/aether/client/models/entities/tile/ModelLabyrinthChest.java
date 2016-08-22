package com.gildedgames.aether.client.models.entities.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLabyrinthChest extends ModelBase
{
  //fields
    public ModelRenderer Main_Top;
    public ModelRenderer Main_Bottom;
    public ModelRenderer Hinge_Left;
    public ModelRenderer Hinge_Right;
    public ModelRenderer Corner_Bottom_Front_Right;
    public ModelRenderer Corner_Bottom_Front_Left;
    public ModelRenderer Corner_Bottom_Back_Left;
    public ModelRenderer Corner_Bottom_Back_Right;
    public ModelRenderer Corner_Top_Back_Left;
    public ModelRenderer Corner_Top_Back_Right;
    public ModelRenderer Corner_Top_Front_Left;
    public ModelRenderer Corner_Top_Front_Right;

  public ModelLabyrinthChest()
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
	  this.Corner_Bottom_Front_Right = new ModelRenderer(this, 4, 44);
	  this.Corner_Bottom_Front_Right.addBox(-1F, 1F, -10F, 2, 6, 5);
	  this.Corner_Bottom_Front_Right.setRotationPoint(0F, 0F, 0F);
	  this.Corner_Bottom_Front_Right.setTextureSize(64, 128);
	  this.Corner_Bottom_Front_Right.mirror = true;
	  this.setRotation(this.Corner_Bottom_Front_Right, 0F, 0.7853982F, 0F);
	  this.Corner_Bottom_Front_Left = new ModelRenderer(this, 46, 44);
	  this.Corner_Bottom_Front_Left.addBox(-1F, 1F, -10F, 2, 6, 5);
	  this.Corner_Bottom_Front_Left.setRotationPoint(0F, 0F, 0F);
	  this.Corner_Bottom_Front_Left.setTextureSize(64, 128);
	  this.Corner_Bottom_Front_Left.mirror = true;
	  this.setRotation(this.Corner_Bottom_Front_Left, 0F, -0.7853982F, 0F);
	  this.Corner_Bottom_Back_Left = new ModelRenderer(this, 46, 44);
	  this.Corner_Bottom_Back_Left.addBox(-1F, 1F, 5F, 2, 6, 5);
	  this.Corner_Bottom_Back_Left.setRotationPoint(0F, 0F, 0F);
	  this.Corner_Bottom_Back_Left.setTextureSize(64, 128);
	  this.Corner_Bottom_Back_Left.mirror = true;
	  this.setRotation(this.Corner_Bottom_Back_Left, 0F, 0.7853982F, 0F);
	  this.Corner_Bottom_Back_Right = new ModelRenderer(this, 4, 44);
	  this.Corner_Bottom_Back_Right.addBox(-1F, 1F, 5F, 2, 6, 5);
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
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
	  this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	  this.Main_Top.render(f5);
	  this.Main_Bottom.render(f5);
	  this.Hinge_Left.render(f5);
	  this.Hinge_Right.render(f5);
	  this.Corner_Bottom_Front_Right.render(f5);
	  this.Corner_Bottom_Front_Left.render(f5);
	  this.Corner_Bottom_Back_Left.render(f5);
	  this.Corner_Bottom_Back_Right.render(f5);
	  this.Corner_Top_Back_Left.render(f5);
	  this.Corner_Top_Back_Right.render(f5);
	  this.Corner_Top_Front_Left.render(f5);
	  this.Corner_Top_Front_Right.render(f5);
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

	/*
	    ModelRenderer Main_Top;
    ModelRenderer Main_Bottom;
    ModelRenderer Hinge_Left;
    ModelRenderer Hinge_Right;
    ModelRenderer Corner_Bottom_Front_Right;
    ModelRenderer Corner_Bottom_Front_Left;
    ModelRenderer Corner_Bottom_Back_Left;
    ModelRenderer Corner_Bottom_Back_Right;
    ModelRenderer Corner_Top_Back_Left;
    ModelRenderer Corner_Top_Back_Right;
    ModelRenderer Corner_Top_Front_Left;
    ModelRenderer Corner_Top_Front_Right;

	 */
	public void renderAll()
	{
		float x = 0.0625F;

		this.Main_Top.render(x);
		this.Main_Bottom.render(x);
		this.Hinge_Left.render(x);
		this.Hinge_Right.render(x);
		this.Corner_Bottom_Front_Right.render(x);
		this.Corner_Bottom_Front_Left.render(x);
		this.Corner_Bottom_Back_Left.render(x);
		this.Corner_Bottom_Back_Right.render(x);
		this.Corner_Top_Back_Left.render(x);
		this.Corner_Top_Back_Right.render(x);
		this.Corner_Top_Front_Right.render(x);
		this.Corner_Top_Front_Left.render(x);
	}
}
