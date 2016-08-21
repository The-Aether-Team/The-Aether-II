package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDetonationSentry extends ModelBase
{

    ModelRenderer body_main;
    ModelRenderer body_main_top;
    ModelRenderer body_main_bottom;
    ModelRenderer body_main_back;
    ModelRenderer body_eye;
    ModelRenderer body_crest_top;
    ModelRenderer body_crest_left_top;
    ModelRenderer body_crest_right_top;
    ModelRenderer body_crest_left_bottom;
    ModelRenderer body_crest_right_bottom;
    ModelRenderer body_jaw_mid;
    ModelRenderer body_main_jaw_left;
    ModelRenderer body_main_jaw_right;
    ModelRenderer body_bomb_main;
    ModelRenderer body_bomb_left_corner_top;
    ModelRenderer body_bomb_right_corner_top;
    ModelRenderer body_bomb_left_corner_bottom;
    ModelRenderer body_bomb_right_corner_bottom;
    ModelRenderer body_bomb_left_corner_back;
    ModelRenderer body_bomb_left_corner_front;
    ModelRenderer body_bomb_right_corner_back;
    ModelRenderer body_bomb_right_corner_front;
    ModelRenderer body_bomb_left_cap;
    ModelRenderer body_bomb_right_cap;
  
  public ModelDetonationSentry()
  {
      textureWidth = 64;
      textureHeight = 32;
    
      body_main = new ModelRenderer(this, 0, 0);
      body_main.addBox(-6F, 13F, -6F, 12, 8, 15);
      body_main.setRotationPoint(0F, 0F, 0F);
      body_main.setTextureSize(64, 32);
      body_main.mirror = true;
      setRotation(body_main, 0F, 0F, 0F);
      body_main_top = new ModelRenderer(this, 0, 0);
      body_main_top.addBox(-6.5F, 10F, -7F, 13, 3, 17);
      body_main_top.setRotationPoint(0F, 0F, 0F);
      body_main_top.setTextureSize(64, 32);
      body_main_top.mirror = true;
      setRotation(body_main_top, 0F, 0F, 0F);
      body_main_bottom = new ModelRenderer(this, 0, 0);
      body_main_bottom.addBox(-6.5F, 21F, -8F, 13, 3, 18);
      body_main_bottom.setRotationPoint(0F, 0F, 0F);
      body_main_bottom.setTextureSize(64, 32);
      body_main_bottom.mirror = true;
      setRotation(body_main_bottom, 0F, 0F, 0F);
      body_main_back = new ModelRenderer(this, 0, 0);
      body_main_back.addBox(-4F, 7F, -0.5F, 8, 4, 12);
      body_main_back.setRotationPoint(0F, 0F, 0F);
      body_main_back.setTextureSize(64, 32);
      body_main_back.mirror = true;
      setRotation(body_main_back, -0.2617994F, 0F, 0F);
      body_eye = new ModelRenderer(this, 0, 0);
      body_eye.addBox(-5.5F, 12F, -8F, 11, 11, 2);
      body_eye.setRotationPoint(0F, 0F, 0F);
      body_eye.setTextureSize(64, 32);
      body_eye.mirror = true;
      setRotation(body_eye, 0F, 0F, 0F);
      body_crest_top = new ModelRenderer(this, 0, 0);
      body_crest_top.addBox(-4.5F, -2F, -1F, 9, 3, 8);
      body_crest_top.setRotationPoint(0F, 12F, -8F);
      body_crest_top.setTextureSize(64, 32);
      body_crest_top.mirror = true;
      setRotation(body_crest_top, 0.9599311F, 0F, 0F);
      body_crest_left_top = new ModelRenderer(this, 0, 0);
      body_crest_left_top.addBox(-1F, -4F, -1F, 10, 7, 2);
      body_crest_left_top.setRotationPoint(5F, 14F, -8F);
      body_crest_left_top.setTextureSize(64, 32);
      body_crest_left_top.mirror = true;
      setRotation(body_crest_left_top, -0.2094395F, -0.5235988F, -0.6108652F);
      body_crest_right_top = new ModelRenderer(this, 0, 0);
      body_crest_right_top.addBox(-9F, -4F, -1F, 10, 7, 2);
      body_crest_right_top.setRotationPoint(-5F, 14F, -8F);
      body_crest_right_top.setTextureSize(64, 32);
      body_crest_right_top.mirror = true;
      setRotation(body_crest_right_top, -0.2094395F, 0.5235988F, 0.6108652F);
      body_crest_left_bottom = new ModelRenderer(this, 0, 0);
      body_crest_left_bottom.addBox(-0.5F, -2.5F, -0.5F, 6, 8, 2);
      body_crest_left_bottom.setRotationPoint(6F, 18F, -8F);
      body_crest_left_bottom.setTextureSize(64, 32);
      body_crest_left_bottom.mirror = true;
      setRotation(body_crest_left_bottom, 0F, -0.3490659F, 0F);
      body_crest_right_bottom = new ModelRenderer(this, 0, 0);
      body_crest_right_bottom.addBox(-0.5F, -2.5F, -1.5F, 6, 8, 2);
      body_crest_right_bottom.setRotationPoint(-6F, 18F, -8F);
      body_crest_right_bottom.setTextureSize(64, 32);
      body_crest_right_bottom.mirror = true;
      setRotation(body_crest_right_bottom, 0F, -2.792527F, 0F);
      body_jaw_mid = new ModelRenderer(this, 0, 0);
      body_jaw_mid.addBox(-6F, 21F, -7F, 12, 4, 3);
      body_jaw_mid.setRotationPoint(0F, 0F, 0F);
      body_jaw_mid.setTextureSize(64, 32);
      body_jaw_mid.mirror = true;
      setRotation(body_jaw_mid, -0.1487144F, 0F, 0F);
      body_main_jaw_left = new ModelRenderer(this, 0, 0);
      body_main_jaw_left.addBox(-2F, -2F, -1F, 3, 6, 3);
      body_main_jaw_left.setRotationPoint(6F, 18F, -8F);
      body_main_jaw_left.setTextureSize(64, 32);
      body_main_jaw_left.mirror = true;
      setRotation(body_main_jaw_left, 0.0872665F, -0.3490659F, 0.5235988F);
      body_main_jaw_right = new ModelRenderer(this, 0, 0);
      body_main_jaw_right.addBox(-1F, -2F, -1F, 3, 6, 3);
      body_main_jaw_right.setRotationPoint(-6F, 18F, -8F);
      body_main_jaw_right.setTextureSize(64, 32);
      body_main_jaw_right.mirror = true;
      setRotation(body_main_jaw_right, 0.0872665F, 0.3490659F, -0.5235988F);
      body_bomb_main = new ModelRenderer(this, 0, 0);
      body_bomb_main.addBox(-10F, 8F, -14F, 20, 9, 9);
      body_bomb_main.setRotationPoint(0F, 0F, 0F);
      body_bomb_main.setTextureSize(64, 32);
      body_bomb_main.mirror = true;
      setRotation(body_bomb_main, 0.7853982F, 0F, 0F);
      body_bomb_left_corner_top = new ModelRenderer(this, 0, 0);
      body_bomb_left_corner_top.addBox(9.5F, 3F, 0.5F, 6, 4, 3);
      body_bomb_left_corner_top.setRotationPoint(0F, 0F, 0F);
      body_bomb_left_corner_top.setTextureSize(64, 32);
      body_bomb_left_corner_top.mirror = true;
      setRotation(body_bomb_left_corner_top, 0F, 0F, 0.5235988F);
      body_bomb_right_corner_top = new ModelRenderer(this, 0, 0);
      body_bomb_right_corner_top.addBox(-15.5F, 3F, 0.5F, 6, 4, 3);
      body_bomb_right_corner_top.setRotationPoint(0F, 0F, 0F);
      body_bomb_right_corner_top.setTextureSize(64, 32);
      body_bomb_right_corner_top.mirror = true;
      setRotation(body_bomb_right_corner_top, 0F, 0F, -0.5235988F);
      body_bomb_left_corner_bottom = new ModelRenderer(this, 0, 0);
      body_bomb_left_corner_bottom.addBox(-6F, 20F, 0.5F, 6, 4, 3);
      body_bomb_left_corner_bottom.setRotationPoint(0F, 0F, 0F);
      body_bomb_left_corner_bottom.setTextureSize(64, 32);
      body_bomb_left_corner_bottom.mirror = true;
      setRotation(body_bomb_left_corner_bottom, 0F, 0F, -0.5235988F);
      body_bomb_right_corner_bottom = new ModelRenderer(this, 0, 0);
      body_bomb_right_corner_bottom.addBox(0F, 20F, 0.5F, 6, 4, 3);
      body_bomb_right_corner_bottom.setRotationPoint(0F, 0F, 0F);
      body_bomb_right_corner_bottom.setTextureSize(64, 32);
      body_bomb_right_corner_bottom.mirror = true;
      setRotation(body_bomb_right_corner_bottom, 0F, 0F, 0.5235988F);
      body_bomb_left_corner_back = new ModelRenderer(this, 0, 0);
      body_bomb_left_corner_back.addBox(0.5F, 14F, 8.5F, 6, 3, 4);
      body_bomb_left_corner_back.setRotationPoint(0F, 0F, 0F);
      body_bomb_left_corner_back.setTextureSize(64, 32);
      body_bomb_left_corner_back.mirror = true;
      setRotation(body_bomb_left_corner_back, 0F, 0.5235988F, 0F);
      body_bomb_left_corner_front = new ModelRenderer(this, 0, 0);
      body_bomb_left_corner_front.addBox(2.5F, 14F, -9F, 6, 3, 4);
      body_bomb_left_corner_front.setRotationPoint(0F, 0F, 0F);
      body_bomb_left_corner_front.setTextureSize(64, 32);
      body_bomb_left_corner_front.mirror = true;
      setRotation(body_bomb_left_corner_front, 0F, -0.5235988F, 0F);
      body_bomb_right_corner_back = new ModelRenderer(this, 0, 0);
      body_bomb_right_corner_back.addBox(-6.5F, 14F, 8.5F, 6, 3, 4);
      body_bomb_right_corner_back.setRotationPoint(0F, 0F, 0F);
      body_bomb_right_corner_back.setTextureSize(64, 32);
      body_bomb_right_corner_back.mirror = true;
      setRotation(body_bomb_right_corner_back, 0F, -0.5235988F, 0F);
      body_bomb_right_corner_front = new ModelRenderer(this, 0, 0);
      body_bomb_right_corner_front.addBox(-8.5F, 14F, -9F, 6, 3, 4);
      body_bomb_right_corner_front.setRotationPoint(0F, 0F, 0F);
      body_bomb_right_corner_front.setTextureSize(64, 32);
      body_bomb_right_corner_front.mirror = true;
      setRotation(body_bomb_right_corner_front, 0F, 0.5235988F, 0F);
      body_bomb_left_cap = new ModelRenderer(this, 0, 0);
      body_bomb_left_cap.addBox(10F, 9.5F, -12.5F, 1, 6, 6);
      body_bomb_left_cap.setRotationPoint(0F, 0F, 0F);
      body_bomb_left_cap.setTextureSize(64, 32);
      body_bomb_left_cap.mirror = true;
      setRotation(body_bomb_left_cap, 0.7853982F, 0F, 0F);
      body_bomb_right_cap = new ModelRenderer(this, 0, 0);
      body_bomb_right_cap.addBox(-11F, 9.5F, -12.5F, 1, 6, 6);
      body_bomb_right_cap.setRotationPoint(0F, 0F, 0F);
      body_bomb_right_cap.setTextureSize(64, 32);
      body_bomb_right_cap.mirror = true;
      setRotation(body_bomb_right_cap, 0.7853982F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    body_main.render(f5);
    body_main_top.render(f5);
    body_main_bottom.render(f5);
    body_main_back.render(f5);
    body_eye.render(f5);
    body_crest_top.render(f5);
    body_crest_left_top.render(f5);
    body_crest_right_top.render(f5);
    body_crest_left_bottom.render(f5);
    body_crest_right_bottom.render(f5);
    body_jaw_mid.render(f5);
    body_main_jaw_left.render(f5);
    body_main_jaw_right.render(f5);
    body_bomb_main.render(f5);
    body_bomb_left_corner_top.render(f5);
    body_bomb_right_corner_top.render(f5);
    body_bomb_left_corner_bottom.render(f5);
    body_bomb_right_corner_bottom.render(f5);
    body_bomb_left_corner_back.render(f5);
    body_bomb_left_corner_front.render(f5);
    body_bomb_right_corner_back.render(f5);
    body_bomb_right_corner_front.render(f5);
    body_bomb_left_cap.render(f5);
    body_bomb_right_cap.render(f5);
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
