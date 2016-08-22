package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLabyrinthChestMimicSpawning extends ModelBase
{

    ModelRenderer Main_Top;
    ModelRenderer Main_Bottom;
    ModelRenderer Hinge_Left;
    ModelRenderer Hinge_Right;
    ModelRenderer Mouth_Back;
    ModelRenderer Mouth_Side_Right;
    ModelRenderer Mouth_Side_Left;
    ModelRenderer Corner_Bottom_Front_Right;
    ModelRenderer Corner_Bottom_Front_Left;
    ModelRenderer Corner_Bottom_Back_Left;
    ModelRenderer Corner_Bottom_Back_Right;
    ModelRenderer Corner_Top_Back_Left;
    ModelRenderer Corner_Top_Back_Right;
    ModelRenderer Corner_Top_Front_Left;
    ModelRenderer Corner_Top_Front_Right;
    ModelRenderer Leg_Joint_Front_Left;
    ModelRenderer Leg_Joint_Front_Right;
    ModelRenderer Leg_Joint_Back_Left;
    ModelRenderer Leg_Joint_Back_Right;
    ModelRenderer Leg_Front_Right;
    ModelRenderer Leg_Front_Left;
    ModelRenderer Leg_Back_Right;
    ModelRenderer Leg_Back_Left;
    ModelRenderer Leg_2_Front_Left;
    ModelRenderer Leg_2_Front_Right;
    ModelRenderer Leg_2_Back_Left;
    ModelRenderer Leg_2_Back_Right;
    ModelRenderer Foot_Front_Left;
    ModelRenderer Foot_Front_Right;
    ModelRenderer Foot_Back_Right;
    ModelRenderer Foot_Back_Left;
  
  public ModelLabyrinthChestMimicSpawning()
  {
    textureWidth = 64;
    textureHeight = 128;
    
      Main_Top = new ModelRenderer(this, 4, 0);
      Main_Top.addBox(-7F, -7F, -14F, 14, 7, 14);
      Main_Top.setRotationPoint(0F, 0F, 7F);
      Main_Top.setTextureSize(64, 128);
      Main_Top.mirror = true;
      setRotation(Main_Top, 0F, 0F, 0F);
      Main_Bottom = new ModelRenderer(this, 4, 56);
      Main_Bottom.addBox(-7F, 0F, -7F, 14, 7, 14);
      Main_Bottom.setRotationPoint(0F, 0F, 0F);
      Main_Bottom.setTextureSize(64, 128);
      Main_Bottom.mirror = true;
      setRotation(Main_Bottom, 0F, 0F, 0F);
      Hinge_Left = new ModelRenderer(this, 26, 30);
      Hinge_Left.addBox(1.5F, -1F, -1.5F, 4, 2, 2);
      Hinge_Left.setRotationPoint(0F, 0F, 7F);
      Hinge_Left.setTextureSize(64, 128);
      Hinge_Left.mirror = true;
      setRotation(Hinge_Left, 0F, 0F, 0F);
      Hinge_Right = new ModelRenderer(this, 26, 30);
      Hinge_Right.addBox(-5.5F, -1F, -1.5F, 4, 2, 2);
      Hinge_Right.setRotationPoint(0F, 0F, 7F);
      Hinge_Right.setTextureSize(64, 128);
      Hinge_Right.mirror = true;
      setRotation(Hinge_Right, 0F, 0F, 0F);
      Mouth_Back = new ModelRenderer(this, 21, 34);
      Mouth_Back.addBox(-5F, -2F, 5F, 10, 3, 1);
      Mouth_Back.setRotationPoint(0F, 0F, 0F);
      Mouth_Back.setTextureSize(64, 128);
      Mouth_Back.mirror = true;
      setRotation(Mouth_Back, 0F, 0F, 0F);
      Mouth_Side_Right = new ModelRenderer(this, 12, 28);
      Mouth_Side_Right.addBox(-5F, -6F, -5F, 0, 6, 10);
      Mouth_Side_Right.setRotationPoint(0F, 0F, 0F);
      Mouth_Side_Right.setTextureSize(64, 128);
      Mouth_Side_Right.mirror = true;
      setRotation(Mouth_Side_Right, 0F, 0.1745329F, 0F);
      Mouth_Side_Left = new ModelRenderer(this, 32, 28);
      Mouth_Side_Left.addBox(5F, -6F, -5F, 0, 6, 10);
      Mouth_Side_Left.setRotationPoint(0F, 0F, 0F);
      Mouth_Side_Left.setTextureSize(64, 128);
      Mouth_Side_Left.mirror = true;
      setRotation(Mouth_Side_Left, 0F, -0.1745329F, 0F);
      Corner_Bottom_Front_Right = new ModelRenderer(this, 4, 44);
      Corner_Bottom_Front_Right.addBox(-1F, 1F, -10F, 2, 7, 5);
      Corner_Bottom_Front_Right.setRotationPoint(0F, 0F, 0F);
      Corner_Bottom_Front_Right.setTextureSize(64, 128);
      Corner_Bottom_Front_Right.mirror = true;
      setRotation(Corner_Bottom_Front_Right, 0F, 0.7853982F, 0F);
      Corner_Bottom_Front_Left = new ModelRenderer(this, 46, 44);
      Corner_Bottom_Front_Left.addBox(-1F, 1F, -10F, 2, 7, 5);
      Corner_Bottom_Front_Left.setRotationPoint(0F, 0F, 0F);
      Corner_Bottom_Front_Left.setTextureSize(64, 128);
      Corner_Bottom_Front_Left.mirror = true;
      setRotation(Corner_Bottom_Front_Left, 0F, -0.7853982F, 0F);
      Corner_Bottom_Back_Left = new ModelRenderer(this, 46, 44);
      Corner_Bottom_Back_Left.addBox(-1F, 1F, 5F, 2, 7, 5);
      Corner_Bottom_Back_Left.setRotationPoint(0F, 0F, 0F);
      Corner_Bottom_Back_Left.setTextureSize(64, 128);
      Corner_Bottom_Back_Left.mirror = true;
      setRotation(Corner_Bottom_Back_Left, 0F, 0.7853982F, 0F);
      Corner_Bottom_Back_Right = new ModelRenderer(this, 4, 44);
      Corner_Bottom_Back_Right.addBox(-1F, 1F, 5F, 2, 7, 5);
      Corner_Bottom_Back_Right.setRotationPoint(0F, 0F, 0F);
      Corner_Bottom_Back_Right.setTextureSize(64, 128);
      Corner_Bottom_Back_Right.mirror = true;
      setRotation(Corner_Bottom_Back_Right, 0F, -0.7853982F, 0F);
      Corner_Top_Back_Left = new ModelRenderer(this, 44, 21);
      Corner_Top_Back_Left.addBox(4F, -8F, -0.4F, 2, 7, 6);
      Corner_Top_Back_Left.setRotationPoint(0F, 0F, 7F);
      Corner_Top_Back_Left.setTextureSize(64, 128);
      Corner_Top_Back_Left.mirror = true;
      setRotation(Corner_Top_Back_Left, 0F, 0.7853982F, 0F);
      Corner_Top_Back_Right = new ModelRenderer(this, 4, 21);
      Corner_Top_Back_Right.addBox(-5.5F, -8F, -0.6F, 2, 7, 6);
      Corner_Top_Back_Right.setRotationPoint(0F, 0F, 7F);
      Corner_Top_Back_Right.setTextureSize(64, 128);
      Corner_Top_Back_Right.mirror = true;
      setRotation(Corner_Top_Back_Right, 0F, -0.7853982F, 0F);
      Corner_Top_Front_Left = new ModelRenderer(this, 44, 21);
      Corner_Top_Front_Left.addBox(-6F, -8F, -15.3F, 2, 7, 6);
      Corner_Top_Front_Left.setRotationPoint(0F, 0F, 7F);
      Corner_Top_Front_Left.setTextureSize(64, 128);
      Corner_Top_Front_Left.mirror = true;
      setRotation(Corner_Top_Front_Left, 0F, -0.7853982F, 0F);
      Corner_Top_Front_Right = new ModelRenderer(this, 4, 21);
      Corner_Top_Front_Right.addBox(4F, -8F, -15.3F, 2, 7, 6);
      Corner_Top_Front_Right.setRotationPoint(0F, 0F, 7F);
      Corner_Top_Front_Right.setTextureSize(64, 128);
      Corner_Top_Front_Right.mirror = true;
      setRotation(Corner_Top_Front_Right, 0F, 0.7853982F, 0F);
      Leg_Joint_Front_Left = new ModelRenderer(this, 38, 77);
      Leg_Joint_Front_Left.addBox(-1F, -3F, -1.5F, 8, 5, 3);
      Leg_Joint_Front_Left.setRotationPoint(3F, 7F, -3F);
      Leg_Joint_Front_Left.setTextureSize(64, 128);
      Leg_Joint_Front_Left.mirror = true;
      setRotation(Leg_Joint_Front_Left, 0F, 0.7853982F, 1.22173F);
      Leg_Joint_Front_Right = new ModelRenderer(this, 4, 77);
      Leg_Joint_Front_Right.addBox(-7F, -3F, -1.5F, 8, 5, 3);
      Leg_Joint_Front_Right.setRotationPoint(-3F, 7F, -3F);
      Leg_Joint_Front_Right.setTextureSize(64, 128);
      Leg_Joint_Front_Right.mirror = true;
      setRotation(Leg_Joint_Front_Right, 0F, -0.7853982F, -1.22173F);
      Leg_Joint_Back_Left = new ModelRenderer(this, 38, 77);
      Leg_Joint_Back_Left.addBox(-1F, -3F, -1.5F, 8, 5, 3);
      Leg_Joint_Back_Left.setRotationPoint(3F, 7F, 3F);
      Leg_Joint_Back_Left.setTextureSize(64, 128);
      Leg_Joint_Back_Left.mirror = true;
      setRotation(Leg_Joint_Back_Left, 0F, -0.7853982F, 1.22173F);
      Leg_Joint_Back_Right = new ModelRenderer(this, 4, 77);
      Leg_Joint_Back_Right.addBox(-7F, -3F, -1.5F, 8, 5, 3);
      Leg_Joint_Back_Right.setRotationPoint(-3F, 7F, 3F);
      Leg_Joint_Back_Right.setTextureSize(64, 128);
      Leg_Joint_Back_Right.mirror = true;
      setRotation(Leg_Joint_Back_Right, 0F, 0.7853982F, -1.22173F);
      Leg_Front_Right = new ModelRenderer(this, 5, 85);
      Leg_Front_Right.addBox(-1F, -5.5F, -12F, 2, 2, 8);
      Leg_Front_Right.setRotationPoint(-3F, 7F, -3F);
      Leg_Front_Right.setTextureSize(64, 128);
      Leg_Front_Right.mirror = true;
      setRotation(Leg_Front_Right, 1.919862F, 0.7853982F, 0F);
      Leg_Front_Left = new ModelRenderer(this, 39, 85);
      Leg_Front_Left.addBox(-1F, -5.5F, -12F, 2, 2, 8);
      Leg_Front_Left.setRotationPoint(3F, 7F, -3F);
      Leg_Front_Left.setTextureSize(64, 128);
      Leg_Front_Left.mirror = true;
      setRotation(Leg_Front_Left, 1.919862F, -0.7853982F, 0F);
      Leg_Back_Right = new ModelRenderer(this, 5, 85);
      Leg_Back_Right.addBox(-1F, -5.5F, 4F, 2, 2, 8);
      Leg_Back_Right.setRotationPoint(-3F, 7F, 3F);
      Leg_Back_Right.setTextureSize(64, 128);
      Leg_Back_Right.mirror = true;
      setRotation(Leg_Back_Right, -1.919862F, -0.7853982F, 0F);
      Leg_Back_Left = new ModelRenderer(this, 39, 85);
      Leg_Back_Left.addBox(-1F, -5.5F, 4F, 2, 2, 8);
      Leg_Back_Left.setRotationPoint(3F, 7F, 3F);
      Leg_Back_Left.setTextureSize(64, 128);
      Leg_Back_Left.mirror = true;
      setRotation(Leg_Back_Left, -1.919862F, 0.7853982F, 0F);
      Leg_2_Front_Left = new ModelRenderer(this, 39, 95);
      Leg_2_Front_Left.addBox(-11.5F, -9.5F, -1.5F, 7, 3, 3);
      Leg_2_Front_Left.setRotationPoint(3F, 7F, -3F);
      Leg_2_Front_Left.setTextureSize(64, 128);
      Leg_2_Front_Left.mirror = true;
      setRotation(Leg_2_Front_Left, 0F, 0.7853982F, -2.268928F);
      Leg_2_Front_Right = new ModelRenderer(this, 5, 95);
      Leg_2_Front_Right.addBox(4.5F, -9.5F, -1.5F, 7, 3, 3);
      Leg_2_Front_Right.setRotationPoint(-3F, 7F, -3F);
      Leg_2_Front_Right.setTextureSize(64, 128);
      Leg_2_Front_Right.mirror = true;
      setRotation(Leg_2_Front_Right, 0F, -0.7853982F, 2.268928F);
      Leg_2_Back_Left = new ModelRenderer(this, 39, 95);
      Leg_2_Back_Left.addBox(-11.5F, -9.5F, -1.5F, 7, 3, 3);
      Leg_2_Back_Left.setRotationPoint(3F, 7F, 3F);
      Leg_2_Back_Left.setTextureSize(64, 128);
      Leg_2_Back_Left.mirror = true;
      setRotation(Leg_2_Back_Left, 0F, -0.7853982F, -2.268928F);
      Leg_2_Back_Right = new ModelRenderer(this, 5, 95);
      Leg_2_Back_Right.addBox(4.5F, -9.5F, -1.5F, 7, 3, 3);
      Leg_2_Back_Right.setRotationPoint(-3F, 7F, 3F);
      Leg_2_Back_Right.setTextureSize(64, 128);
      Leg_2_Back_Right.mirror = true;
      setRotation(Leg_2_Back_Right, 0F, 0.7853982F, 2.268928F);
      Foot_Front_Left = new ModelRenderer(this, 46, 101);
      Foot_Front_Left.addBox(-6F, -8F, -0.5F, 2, 3, 1);
      Foot_Front_Left.setRotationPoint(3F, 7F, -3F);
      Foot_Front_Left.setTextureSize(64, 128);
      Foot_Front_Left.mirror = true;
      setRotation(Foot_Front_Left, 0F, 0.7853982F, -2.268928F);
      Foot_Front_Right = new ModelRenderer(this, 12, 101);
      Foot_Front_Right.addBox(4F, -8F, -0.5F, 2, 3, 1);
      Foot_Front_Right.setRotationPoint(-3F, 7F, -3F);
      Foot_Front_Right.setTextureSize(64, 128);
      Foot_Front_Right.mirror = true;
      setRotation(Foot_Front_Right, 0F, -0.7853982F, 2.268928F);
      Foot_Back_Right = new ModelRenderer(this, 12, 101);
      Foot_Back_Right.addBox(4F, -8F, -0.5F, 2, 3, 1);
      Foot_Back_Right.setRotationPoint(-3F, 7F, 3F);
      Foot_Back_Right.setTextureSize(64, 128);
      Foot_Back_Right.mirror = true;
      setRotation(Foot_Back_Right, 0F, 0.7853982F, 2.268928F);
      Foot_Back_Left = new ModelRenderer(this, 46, 101);
      Foot_Back_Left.addBox(-6F, -8F, -0.5F, 2, 3, 1);
      Foot_Back_Left.setRotationPoint(3F, 7F, 3F);
      Foot_Back_Left.setTextureSize(64, 128);
      Foot_Back_Left.mirror = true;
      setRotation(Foot_Back_Left, 0F, -0.7853982F, -2.268928F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Main_Top.render(f5);
    Main_Bottom.render(f5);
    Hinge_Left.render(f5);
    Hinge_Right.render(f5);
    Mouth_Back.render(f5);
    Mouth_Side_Right.render(f5);
    Mouth_Side_Left.render(f5);
    Corner_Bottom_Front_Right.render(f5);
    Corner_Bottom_Front_Left.render(f5);
    Corner_Bottom_Back_Left.render(f5);
    Corner_Bottom_Back_Right.render(f5);
    Corner_Top_Back_Left.render(f5);
    Corner_Top_Back_Right.render(f5);
    Corner_Top_Front_Left.render(f5);
    Corner_Top_Front_Right.render(f5);
    Leg_Joint_Front_Left.render(f5);
    Leg_Joint_Front_Right.render(f5);
    Leg_Joint_Back_Left.render(f5);
    Leg_Joint_Back_Right.render(f5);
    Leg_Front_Right.render(f5);
    Leg_Front_Left.render(f5);
    Leg_Back_Right.render(f5);
    Leg_Back_Left.render(f5);
    Leg_2_Front_Left.render(f5);
    Leg_2_Front_Right.render(f5);
    Leg_2_Back_Left.render(f5);
    Leg_2_Back_Right.render(f5);
    Foot_Front_Left.render(f5);
    Foot_Front_Right.render(f5);
    Foot_Back_Right.render(f5);
    Foot_Back_Left.render(f5);
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
