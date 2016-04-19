package com.gildedgames.aether.client.models.entities.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelLabyrinthTotem extends ModelBase
{
	public ModelRenderer Shape1;

	public ModelRenderer Shape2;

	public ModelRenderer Shape3;

	public ModelRenderer Shape4;

	public ModelRenderer Shape5;

	public ModelRenderer Shape6;

	public ModelRenderer Shape7;

	public ModelRenderer Shape8;

	public ModelRenderer Shape9;

	public ModelRenderer Shape10;

	public ModelRenderer Shape11;

	public ModelLabyrinthTotem()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;

		this.Shape1 = new ModelRenderer(this, 0, 0);
		this.Shape1.addBox(-2F, -6F, -2F, 4, 6, 4);
		this.Shape1.setRotationPoint(-6F, 24F, -6F);
		this.Shape1.setTextureSize(128, 64);
		this.Shape1.mirror = true;
		this.setRotation(this.Shape1, 0F, 0F, 0F);
		this.Shape1.mirror = true;
		this.Shape2 = new ModelRenderer(this, 0, 0);
		this.Shape2.addBox(-2F, -6F, -2F, 4, 6, 4);
		this.Shape2.setRotationPoint(6F, 24F, -6F);
		this.Shape2.setTextureSize(128, 64);
		this.Shape2.mirror = true;
		this.setRotation(this.Shape2, 0F, 0F, 0F);
		this.Shape2.mirror = false;
		this.Shape3 = new ModelRenderer(this, 0, 0);
		this.Shape3.addBox(-2F, -6F, -2F, 4, 6, 4);
		this.Shape3.setRotationPoint(6F, 24F, 6F);
		this.Shape3.setTextureSize(128, 64);
		this.Shape3.mirror = true;
		this.setRotation(this.Shape3, 0F, 0F, 0F);
		this.Shape4 = new ModelRenderer(this, 0, 0);
		this.Shape4.addBox(-2F, -6F, -2F, 4, 6, 4);
		this.Shape4.setRotationPoint(-6F, 24F, 6F);
		this.Shape4.setTextureSize(128, 64);
		this.Shape4.mirror = true;
		this.setRotation(this.Shape4, 0F, 0F, 0F);
		this.Shape4.mirror = false;
		this.Shape5 = new ModelRenderer(this, 2, 0);
		this.Shape5.addBox(-7F, -14F, -7F, 14, 14, 14);
		this.Shape5.setRotationPoint(0F, 23F, 0F);
		this.Shape5.setTextureSize(128, 64);
		this.Shape5.mirror = true;
		this.setRotation(this.Shape5, 0F, 0F, 0F);
		this.Shape6 = new ModelRenderer(this, 2, 28);
		this.Shape6.addBox(-7F, 0F, -7F, 14, 14, 14);
		this.Shape6.setRotationPoint(0F, -7F, 0F);
		this.Shape6.setTextureSize(128, 64);
		this.Shape6.mirror = true;
		this.setRotation(this.Shape6, 0F, 0F, 0F);
		this.Shape7 = new ModelRenderer(this, 44, 0);
		this.Shape7.addBox(-6F, 0F, -6F, 12, 2, 12);
		this.Shape7.setRotationPoint(0F, 7F, 0F);
		this.Shape7.setTextureSize(128, 64);
		this.Shape7.mirror = true;
		this.setRotation(this.Shape7, 0F, 0F, 0F);
		this.Shape8 = new ModelRenderer(this, 0, 28);
		this.Shape8.addBox(-8F, -2F, -8F, 4, 6, 4);
		this.Shape8.setRotationPoint(0F, -7F, 0F);
		this.Shape8.setTextureSize(128, 64);
		this.Shape8.mirror = true;
		this.setRotation(this.Shape8, 0F, 0F, 0F);
		this.Shape8.mirror = true;
		this.Shape9 = new ModelRenderer(this, 0, 28);
		this.Shape9.addBox(-8F, -2F, 4F, 4, 6, 4);
		this.Shape9.setRotationPoint(0F, -7F, 0F);
		this.Shape9.setTextureSize(128, 64);
		this.Shape9.mirror = true;
		this.setRotation(this.Shape9, 0F, 0F, 0F);
		this.Shape9.mirror = false;
		this.Shape10 = new ModelRenderer(this, 0, 28);
		this.Shape10.addBox(4F, -2F, 4F, 4, 6, 4);
		this.Shape10.setRotationPoint(0F, -7F, 0F);
		this.Shape10.setTextureSize(128, 64);
		this.Shape10.mirror = true;
		this.setRotation(this.Shape10, 0F, 0F, 0F);
		this.Shape10.mirror = true;
		this.Shape11 = new ModelRenderer(this, 0, 28);
		this.Shape11.addBox(4F, -2F, -8F, 4, 6, 4);
		this.Shape11.setRotationPoint(0F, -7F, 0F);
		this.Shape11.setTextureSize(128, 64);
		this.Shape11.mirror = true;
		this.setRotation(this.Shape11, 0F, 0F, 0F);
		this.Shape11.mirror = false;
	}

	public void renderAll(float f5)
	{
		this.Shape1.render(f5);
		this.Shape2.render(f5);
		this.Shape3.render(f5);
		this.Shape4.render(f5);
		this.Shape5.render(f5);
		this.Shape6.render(f5);
		this.Shape7.render(f5);
		this.Shape8.render(f5);
		this.Shape9.render(f5);
		this.Shape10.render(f5);
		this.Shape11.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
