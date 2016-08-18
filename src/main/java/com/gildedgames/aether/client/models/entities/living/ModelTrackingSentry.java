package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelTrackingSentry extends ModelBase
{
  //fields
  ModelRenderer Shape1;
  ModelRenderer Shape2;
  ModelRenderer Shape3;
  ModelRenderer Shape4;
  ModelRenderer Shape5;
  ModelRenderer Shape6;
  ModelRenderer Shape7;
  ModelRenderer Shape8;
  ModelRenderer Shape9;
  ModelRenderer Shape10;
  ModelRenderer Shape11;
  ModelRenderer body_eye;
  ModelRenderer body_crest_top;
  ModelRenderer body_crest_left_top;
  ModelRenderer body_crest_left_bottom;
  ModelRenderer body_crest_bottom;
  ModelRenderer body_crest_right_top;
  ModelRenderer body_crest_right_bottom;
  ModelRenderer body_crest_right_top2;
  ModelRenderer body_crest_right_top3;
  ModelRenderer Shape12;

  public ModelTrackingSentry()
  {
    textureWidth = 64;
    textureHeight = 32;

    Shape1 = new ModelRenderer(this, 0, 0);
    Shape1.addBox(-4F, 1F, -4F, 8, 3, 9);
    Shape1.setRotationPoint(0F, 0F, 0F);
    Shape1.setTextureSize(64, 32);
    Shape1.mirror = true;
    setRotation(Shape1, -0.1745329F, 0F, 0F);
    Shape2 = new ModelRenderer(this, 0, 0);
    Shape2.addBox(-4F, 9.5F, -6.5F, 8, 3, 9);
    Shape2.setRotationPoint(0F, 0F, 0F);
    Shape2.setTextureSize(64, 32);
    Shape2.mirror = true;
    setRotation(Shape2, 0.1745329F, 0F, 0F);
    Shape3 = new ModelRenderer(this, 0, 0);
    Shape3.addBox(-5.5F, 3F, -5F, 3, 8, 9);
    Shape3.setRotationPoint(0F, 0F, 0F);
    Shape3.setTextureSize(64, 32);
    Shape3.mirror = true;
    setRotation(Shape3, 0F, 0.1745329F, 0F);
    Shape4 = new ModelRenderer(this, 0, 0);
    Shape4.addBox(2.5F, 3F, -5F, 3, 8, 9);
    Shape4.setRotationPoint(0F, 0F, 0F);
    Shape4.setTextureSize(64, 32);
    Shape4.mirror = true;
    setRotation(Shape4, 0F, -0.1745329F, 0F);
    Shape5 = new ModelRenderer(this, 0, 0);
    Shape5.addBox(-3F, 4F, 3F, 6, 6, 1);
    Shape5.setRotationPoint(0F, 0F, 0F);
    Shape5.setTextureSize(64, 32);
    Shape5.mirror = true;
    setRotation(Shape5, 0F, 0F, 0F);
    Shape6 = new ModelRenderer(this, 0, 0);
    Shape6.addBox(-21F, -3F, -1.5F, 3, 6, 3);
    Shape6.setRotationPoint(0F, 7F, -2F);
    Shape6.setTextureSize(64, 32);
    Shape6.mirror = true;
    setRotation(Shape6, 0F, 0F, 0F);
    Shape7 = new ModelRenderer(this, 0, 0);
    Shape7.addBox(-19.5F, -6F, -1F, 2, 14, 2);
    Shape7.setRotationPoint(0F, 7F, -2F);
    Shape7.setTextureSize(64, 32);
    Shape7.mirror = true;
    setRotation(Shape7, 0F, 0F, 0.5235988F);
    Shape8 = new ModelRenderer(this, 0, 0);
    Shape8.addBox(-19.5F, -8F, -1F, 2, 14, 2);
    Shape8.setRotationPoint(0F, 7F, -2F);
    Shape8.setTextureSize(64, 32);
    Shape8.mirror = true;
    setRotation(Shape8, 0F, 0F, -0.5235988F);
    Shape9 = new ModelRenderer(this, 0, 0);
    Shape9.addBox(18F, -3F, -1.5F, 3, 6, 3);
    Shape9.setRotationPoint(0F, 7F, -2F);
    Shape9.setTextureSize(64, 32);
    Shape9.mirror = true;
    setRotation(Shape9, 0F, 0F, 0F);
    Shape10 = new ModelRenderer(this, 0, 0);
    Shape10.addBox(17.5F, -8F, -1F, 2, 14, 2);
    Shape10.setRotationPoint(0F, 7F, -2F);
    Shape10.setTextureSize(64, 32);
    Shape10.mirror = true;
    setRotation(Shape10, 0F, 0F, 0.5235988F);
    Shape11 = new ModelRenderer(this, 0, 0);
    Shape11.addBox(17.5F, -6F, -1F, 2, 14, 2);
    Shape11.setRotationPoint(0F, 7F, -2F);
    Shape11.setTextureSize(64, 32);
    Shape11.mirror = true;
    setRotation(Shape11, 0F, 0F, -0.5235988F);
    body_eye = new ModelRenderer(this, 0, 0);
    body_eye.addBox(-5F, 3F, -8F, 10, 8, 2);
    body_eye.setRotationPoint(0F, 0F, 0F);
    body_eye.setTextureSize(64, 32);
    body_eye.mirror = true;
    setRotation(body_eye, 0F, 0F, 0F);
    body_crest_top = new ModelRenderer(this, 0, 0);
    body_crest_top.addBox(-4F, -2F, -1F, 8, 3, 12);
    body_crest_top.setRotationPoint(0F, 2F, -8F);
    body_crest_top.setTextureSize(64, 32);
    body_crest_top.mirror = true;
    setRotation(body_crest_top, 0.9599311F, 0F, 0F);
    body_crest_left_top = new ModelRenderer(this, 0, 0);
    body_crest_left_top.addBox(-1F, -3F, -1F, 8, 5, 2);
    body_crest_left_top.setRotationPoint(5F, 4F, -8F);
    body_crest_left_top.setTextureSize(64, 32);
    body_crest_left_top.mirror = true;
    setRotation(body_crest_left_top, -0.2094395F, -0.5235988F, -0.6108652F);
    body_crest_left_bottom = new ModelRenderer(this, 0, 0);
    body_crest_left_bottom.addBox(-1.5F, -2.5F, -1.5F, 9, 3, 3);
    body_crest_left_bottom.setRotationPoint(6F, 8F, -8F);
    body_crest_left_bottom.setTextureSize(64, 32);
    body_crest_left_bottom.mirror = true;
    setRotation(body_crest_left_bottom, 0F, -0.4363323F, 0F);
    body_crest_bottom = new ModelRenderer(this, 0, 0);
    body_crest_bottom.addBox(-4F, -1F, -1F, 8, 3, 12);
    body_crest_bottom.setRotationPoint(0F, 12F, -8F);
    body_crest_bottom.setTextureSize(64, 32);
    body_crest_bottom.mirror = true;
    setRotation(body_crest_bottom, -0.9599311F, 0F, 0F);
    body_crest_right_top = new ModelRenderer(this, 0, 0);
    body_crest_right_top.addBox(-1F, -2F, -1F, 8, 5, 2);
    body_crest_right_top.setRotationPoint(5F, 10F, -8F);
    body_crest_right_top.setTextureSize(64, 32);
    body_crest_right_top.mirror = true;
    setRotation(body_crest_right_top, 0.2094395F, -0.5235988F, 0.6108652F);
    body_crest_right_bottom = new ModelRenderer(this, 0, 0);
    body_crest_right_bottom.addBox(-7.5F, -2.5F, -1.5F, 9, 3, 3);
    body_crest_right_bottom.setRotationPoint(-6F, 8F, -8F);
    body_crest_right_bottom.setTextureSize(64, 32);
    body_crest_right_bottom.mirror = true;
    setRotation(body_crest_right_bottom, 0F, 0.4363323F, 0F);
    body_crest_right_top2 = new ModelRenderer(this, 0, 0);
    body_crest_right_top2.addBox(-7F, -3F, -1F, 8, 5, 2);
    body_crest_right_top2.setRotationPoint(-5F, 4F, -8F);
    body_crest_right_top2.setTextureSize(64, 32);
    body_crest_right_top2.mirror = true;
    setRotation(body_crest_right_top2, -0.2094395F, 0.5235988F, 0.6108652F);
    body_crest_right_top3 = new ModelRenderer(this, 0, 0);
    body_crest_right_top3.addBox(-7F, -2F, -1F, 8, 5, 2);
    body_crest_right_top3.setRotationPoint(-5F, 10F, -8F);
    body_crest_right_top3.setTextureSize(64, 32);
    body_crest_right_top3.mirror = true;
    setRotation(body_crest_right_top3, 0.2094395F, 0.5235988F, -0.6108652F);
    Shape12 = new ModelRenderer(this, 0, 0);
    Shape12.addBox(-5F, 2F, -6F, 10, 10, 5);
    Shape12.setRotationPoint(0F, 0F, 0F);
    Shape12.setTextureSize(64, 32);
    Shape12.mirror = true;
    setRotation(Shape12, 0F, 0F, 0F);
  }

  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);

    float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();

    GL11.glPushMatrix();

    GL11.glTranslatef(0, MathHelper.sin((entity.ticksExisted + partialTicks) * 0.25F) / 2, 0);

    Shape1.render(f5);
    Shape2.render(f5);
    Shape3.render(f5);
    Shape4.render(f5);
    Shape5.render(f5);
    Shape6.render(f5);
    Shape7.render(f5);
    Shape8.render(f5);
    Shape9.render(f5);
    Shape10.render(f5);
    Shape11.render(f5);
    body_eye.render(f5);
    body_crest_top.render(f5);
    body_crest_left_top.render(f5);
    body_crest_left_bottom.render(f5);
    body_crest_bottom.render(f5);
    body_crest_right_top.render(f5);
    body_crest_right_bottom.render(f5);
    body_crest_right_top2.render(f5);
    body_crest_right_top3.render(f5);
    Shape12.render(f5);

    GL11.glPopMatrix();
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
