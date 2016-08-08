package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelRamWool extends ModelBase
{
  //fields
    ModelRenderer BodyMain;
    ModelRenderer BodyBack;
    ModelRenderer BodyBottom;
    ModelRenderer LegFrontLeft1;
    ModelRenderer LegFrontRight1;
    ModelRenderer LegBackLeft1;
    ModelRenderer LegBackLeft2;
    ModelRenderer LegBackRight1;
    ModelRenderer LegBackRight2;
    ModelRenderer Tail;
  
  public ModelRamWool()
  {
    textureWidth = 128;
    textureHeight = 256;
    
      BodyMain = new ModelRenderer(this, 28, 74);
      BodyMain.addBox(-8F, -1F, -10F, 16, 16, 18);
      BodyMain.setRotationPoint(0F, 0F, 0F);
      BodyMain.setTextureSize(128, 256);
      BodyMain.mirror = true;
      setRotation(BodyMain, 0F, 0F, 0F);
      BodyBack = new ModelRenderer(this, 35, 108);
      BodyBack.addBox(-7F, -3F, 7F, 14, 11, 13);
      BodyBack.setRotationPoint(0F, 0F, 0F);
      BodyBack.setTextureSize(128, 256);
      BodyBack.mirror = true;
      setRotation(BodyBack, -0.3490659F, 0F, 0F);
      BodyBottom = new ModelRenderer(this, 44, 132);
      BodyBottom.addBox(-5F, 10F, 8F, 10, 5, 8);
      BodyBottom.setRotationPoint(0F, 0F, 0F);
      BodyBottom.setTextureSize(128, 256);
      BodyBottom.mirror = true;
      setRotation(BodyBottom, 0F, 0F, 0F);
      LegFrontLeft1 = new ModelRenderer(this, 96, 61);
      LegFrontLeft1.addBox(-3F, 0F, -3F, 6, 7, 6);
      LegFrontLeft1.setRotationPoint(4F, 13F, -5F);
      LegFrontLeft1.setTextureSize(128, 256);
      LegFrontLeft1.mirror = true;
      setRotation(LegFrontLeft1, 0.0872665F, 0F, 0F);
      LegFrontRight1 = new ModelRenderer(this, 4, 61);
      LegFrontRight1.addBox(-3F, 0F, -3F, 6, 7, 6);
      LegFrontRight1.setRotationPoint(-4F, 13F, -5F);
      LegFrontRight1.setTextureSize(128, 256);
      LegFrontRight1.mirror = true;
      setRotation(LegFrontRight1, 0.0872665F, 0F, 0F);
      LegBackLeft1 = new ModelRenderer(this, 96, 108);
      LegBackLeft1.addBox(-3F, -1F, -3F, 6, 7, 8);
      LegBackLeft1.setRotationPoint(3.5F, 12F, 7F);
      LegBackLeft1.setTextureSize(128, 256);
      LegBackLeft1.mirror = true;
      setRotation(LegBackLeft1, 0.6108652F, 0F, 0F);
      LegBackLeft2 = new ModelRenderer(this, 99, 123);
      LegBackLeft2.addBox(-3F, 6F, -3F, 6, 2, 5);
      LegBackLeft2.setRotationPoint(3.5F, 12F, 7F);
      LegBackLeft2.setTextureSize(128, 256);
      LegBackLeft2.mirror = true;
      setRotation(LegBackLeft2, 0.6108652F, 0F, 0F);
      LegBackRight1 = new ModelRenderer(this, 0, 108);
      LegBackRight1.addBox(-3F, -1F, -3F, 6, 7, 8);
      LegBackRight1.setRotationPoint(-3.5F, 12F, 7F);
      LegBackRight1.setTextureSize(128, 256);
      LegBackRight1.mirror = true;
      setRotation(LegBackRight1, 0.6108652F, 0F, 0F);
      LegBackRight2 = new ModelRenderer(this, 3, 123);
      LegBackRight2.addBox(-3F, 6F, -3F, 6, 2, 5);
      LegBackRight2.setRotationPoint(-3.5F, 12F, 7F);
      LegBackRight2.setTextureSize(128, 256);
      LegBackRight2.mirror = true;
      setRotation(LegBackRight2, 0.6108652F, 0F, 0F);
      Tail = new ModelRenderer(this, 56, 145);
      Tail.addBox(-2F, 0F, -1F, 4, 9, 2);
      Tail.setRotationPoint(0F, 5F, 18F);
      Tail.setTextureSize(128, 256);
      Tail.mirror = true;
      setRotation(Tail, 0.1745329F, 0F, 0F);
  }
  
  public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
  {
    super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
    BodyMain.render(scaleFactor);
    BodyBack.render(scaleFactor);
    BodyBottom.render(scaleFactor);
    LegFrontLeft1.render(scaleFactor);
    LegFrontRight1.render(scaleFactor);
    LegBackLeft1.render(scaleFactor);
    LegBackLeft2.render(scaleFactor);
    LegBackRight1.render(scaleFactor);
    LegBackRight2.render(scaleFactor);
    Tail.render(scaleFactor);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
  {
    super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

    //this.BodyBack.rotateAngleX = ((float)Math.PI / 2F);
    //this.BodyBottom.rotateAngleX = ((float)Math.PI / 2F);
    //this.BodyMain.rotateAngleX = ((float)Math.PI / 2F);

    this.BodyMain.offsetX = (float)(Math.sin(ageInTicks * 10 / 87.2957795) * 1 * 0.5F) / 40;
    this.BodyMain.offsetZ = (float)(Math.sin(ageInTicks * 10 / 37.2957795) * 1 * 0.5F) / 40;

    this.BodyBottom.offsetX = (float)(Math.sin(ageInTicks * 10 / 87.2957795) * 1 * 0.25F) / 40;
    this.BodyBottom.offsetZ = (float)(Math.sin(ageInTicks * 10 / 37.2957795) * 1 * 0.25F) / 40;

    this.BodyBack.offsetX = (float)(Math.sin(ageInTicks * 10 / 87.2957795) * 1 * 0.25F) / 40;
    this.BodyBack.offsetZ = (float)(Math.sin(ageInTicks * 10 / 37.2957795) * 1 * 0.25F) / 40;

    this.Tail.rotateAngleZ = (MathHelper.cos(ageInTicks * 0.1662F) * 0.2F);

    this.LegFrontLeft1.rotateAngleX = 0.0872665F + (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);

    this.LegFrontRight1.rotateAngleX = 0.0872665F + (MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount);

    this.LegBackLeft1.rotateAngleX = 0.6108652F + (MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount);
    this.LegBackLeft2.rotateAngleX = 0.6108652F + (MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount);

    this.LegBackRight1.rotateAngleX = 0.6108652F + (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
    this.LegBackRight2.rotateAngleX = 0.6108652F + (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
  }

}
