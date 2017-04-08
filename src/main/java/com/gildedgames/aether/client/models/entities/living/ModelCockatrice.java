package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.living.mobs.EntityCockatrice;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCockatrice extends ModelBase
{

	ModelRenderer LegTopRight;

	ModelRenderer LegTopLeft;

	ModelRenderer LegMidRight;

	ModelRenderer LegMidLeft;

	ModelRenderer LegBottomRight;

	ModelRenderer LegBottomLeft;

	ModelRenderer FootRight;

	ModelRenderer FootLeft;

	ModelRenderer LegFeatheredRight;

	ModelRenderer LegFeatheredLeft;

	ModelRenderer BodyFront;

	ModelRenderer BodyBack;

	ModelRenderer ShoulderRight;

	ModelRenderer ShoulderLeft;

	ModelRenderer WingBaseRight;

	ModelRenderer WingBaseLeft;

	ModelRenderer WingRight;

	ModelRenderer WingLeft;

	ModelRenderer TailMiddle;

	ModelRenderer TailLeft;

	ModelRenderer TailRight;

	ModelRenderer NeckBase;

	ModelRenderer NeckTop;

	ModelRenderer Head;

	ModelRenderer BeakJaw;

	ModelRenderer Teeth;

	ModelRenderer FeatherRight;

	ModelRenderer FeatherLeft;

	ModelRenderer FeatherTop;

	ModelRenderer ToeLeftRight;

	ModelRenderer ToeMidRight;

	ModelRenderer ToeRightRight;

	ModelRenderer ToeLeftLeft;

	ModelRenderer ToeMidLeft;

	ModelRenderer ToeRightLeft;

	ModelRenderer Dart1;

	ModelRenderer Dart2;

	ModelRenderer Dart3;

	ModelRenderer Dart4;

	ModelRenderer Dart5;

	ModelRenderer Dart6;

	public ModelCockatrice()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;

		this.LegTopRight = new ModelRenderer(this, 50, 0);
		this.LegTopRight.addBox(-1F, 2F, 0.7F, 2, 4, 2);
		this.LegTopRight.setRotationPoint(-3F, 7F, -2F);
		this.LegTopRight.setTextureSize(128, 64);
		this.LegTopRight.mirror = true;
		this.setRotation(this.LegTopRight, -0.2094395F, 0F, 0F);
		this.LegTopRight.mirror = false;
		this.LegTopLeft = new ModelRenderer(this, 50, 0);
		this.LegTopLeft.addBox(-1F, 2F, 0.7F, 2, 4, 2);
		this.LegTopLeft.setRotationPoint(3F, 7F, -2F);
		this.LegTopLeft.setTextureSize(128, 64);
		this.LegTopLeft.mirror = true;
		this.setRotation(this.LegTopLeft, -0.2094395F, 0F, 0F);
		this.LegMidRight = new ModelRenderer(this, 58, 0);
		this.LegMidRight.addBox(-1F, 3.5F, -5F, 2, 8, 2);
		this.LegMidRight.setRotationPoint(-3F, 7F, -2F);
		this.LegMidRight.setTextureSize(128, 64);
		this.LegMidRight.mirror = true;
		this.setRotation(this.LegMidRight, 0.8726646F, 0F, 0F);
		this.LegMidRight.mirror = false;
		this.LegMidLeft = new ModelRenderer(this, 58, 0);
		this.LegMidLeft.addBox(-1F, 3.5F, -5F, 2, 8, 2);
		this.LegMidLeft.setRotationPoint(3F, 7F, -2F);
		this.LegMidLeft.setTextureSize(128, 64);
		this.LegMidLeft.mirror = true;
		this.setRotation(this.LegMidLeft, 0.8726646F, 0F, 0F);
		this.LegBottomRight = new ModelRenderer(this, 58, 10);
		this.LegBottomRight.addBox(-1F, 3.1F, 9.5F, 2, 9, 2);
		this.LegBottomRight.setRotationPoint(-3F, 7F, -2F);
		this.LegBottomRight.setTextureSize(128, 64);
		this.LegBottomRight.mirror = true;
		this.setRotation(this.LegBottomRight, -0.6981317F, 0F, 0F);
		this.LegBottomRight.mirror = false;
		this.LegBottomLeft = new ModelRenderer(this, 58, 10);
		this.LegBottomLeft.addBox(-1F, 3.1F, 9.5F, 2, 9, 2);
		this.LegBottomLeft.setRotationPoint(3F, 7F, -2F);
		this.LegBottomLeft.setTextureSize(128, 64);
		this.LegBottomLeft.mirror = true;
		this.setRotation(this.LegBottomLeft, -0.6981317F, 0F, 0F);
		this.FootRight = new ModelRenderer(this, 38, 12);
		this.FootRight.addBox(-1F, 15F, -2F, 2, 2, 8);
		this.FootRight.setRotationPoint(-3F, 7F, -2F);
		this.FootRight.setTextureSize(128, 64);
		this.FootRight.mirror = true;
		this.setRotation(this.FootRight, 0F, 0F, 0F);
		this.FootRight.mirror = false;
		this.FootLeft = new ModelRenderer(this, 38, 12);
		this.FootLeft.addBox(-1F, 15F, -2F, 2, 2, 8);
		this.FootLeft.setRotationPoint(3F, 7F, -2F);
		this.FootLeft.setTextureSize(128, 64);
		this.FootLeft.mirror = true;
		this.setRotation(this.FootLeft, 0F, 0F, 0F);
		this.LegFeatheredRight = new ModelRenderer(this, 58, 21);
		this.LegFeatheredRight.addBox(-2F, 0F, -2F, 4, 4, 6);
		this.LegFeatheredRight.setRotationPoint(-3F, 7F, -2F);
		this.LegFeatheredRight.setTextureSize(128, 64);
		this.LegFeatheredRight.mirror = true;
		this.setRotation(this.LegFeatheredRight, -0.2094395F, 0F, 0.1745329F);
		this.LegFeatheredRight.mirror = false;
		this.LegFeatheredLeft = new ModelRenderer(this, 58, 21);
		this.LegFeatheredLeft.addBox(-2F, 0F, -2F, 4, 4, 6);
		this.LegFeatheredLeft.setRotationPoint(3F, 7F, -2F);
		this.LegFeatheredLeft.setTextureSize(128, 64);
		this.LegFeatheredLeft.mirror = true;
		this.setRotation(this.LegFeatheredLeft, -0.2094395F, 0F, -0.1745329F);
		this.BodyFront = new ModelRenderer(this, 74, 0);
		this.BodyFront.addBox(-3F, 0F, -8F, 6, 5, 8);
		this.BodyFront.setRotationPoint(0F, 3F, 0F);
		this.BodyFront.setTextureSize(128, 64);
		this.BodyFront.mirror = true;
		this.setRotation(this.BodyFront, 0.0872665F, 0F, 0F);
		this.BodyBack = new ModelRenderer(this, 94, 5);
		this.BodyBack.addBox(-4F, 0F, 0F, 8, 6, 8);
		this.BodyBack.setRotationPoint(0F, 3F, 0F);
		this.BodyBack.setTextureSize(128, 64);
		this.BodyBack.mirror = true;
		this.setRotation(this.BodyBack, -0.1396263F, 0F, 0F);
		this.ShoulderRight = new ModelRenderer(this, 29, 0);
		this.ShoulderRight.addBox(-5F, -0.8F, -2F, 6, 3, 3);
		this.ShoulderRight.setRotationPoint(-2F, 4.5F, -3F);
		this.ShoulderRight.setTextureSize(128, 64);
		this.ShoulderRight.mirror = true;
		this.setRotation(this.ShoulderRight, 0F, 0.1745329F, -0.1745329F);
		this.ShoulderRight.mirror = false;
		this.ShoulderLeft = new ModelRenderer(this, 29, 0);
		this.ShoulderLeft.addBox(-1F, -0.8F, -2F, 6, 3, 3);
		this.ShoulderLeft.setRotationPoint(2F, 4.5F, -3F);
		this.ShoulderLeft.setTextureSize(128, 64);
		this.ShoulderLeft.mirror = true;
		this.setRotation(this.ShoulderLeft, 0F, -0.1745329F, 0.1745329F);
		this.WingBaseRight = new ModelRenderer(this, 66, 0);
		this.WingBaseRight.addBox(-5F, 0.2F, -1F, 2, 6, 2);
		this.WingBaseRight.setRotationPoint(-2F, 4.5F, -3F);
		this.WingBaseRight.setTextureSize(128, 64);
		this.WingBaseRight.mirror = true;
		this.setRotation(this.WingBaseRight, 0F, 0F, 0F);
		this.WingBaseRight.mirror = false;
		this.WingBaseLeft = new ModelRenderer(this, 66, 0);
		this.WingBaseLeft.addBox(3F, 0.2F, -1F, 2, 6, 2);
		this.WingBaseLeft.setRotationPoint(2F, 4.5F, -3F);
		this.WingBaseLeft.setTextureSize(128, 64);
		this.WingBaseLeft.mirror = true;
		this.setRotation(this.WingBaseLeft, 0F, 0F, 0F);
		this.WingRight = new ModelRenderer(this, 0, 20);
		this.WingRight.addBox(-5F, 2F, 0F, 1, 8, 15);
		this.WingRight.setRotationPoint(-2F, 4.5F, -3F);
		this.WingRight.setTextureSize(128, 64);
		this.WingRight.mirror = true;
		this.setRotation(this.WingRight, -0.1745329F, 0F, 0F);
		this.WingLeft = new ModelRenderer(this, 0, 20);
		this.WingLeft.addBox(4F, 2F, 0F, 1, 8, 15);
		this.WingLeft.setRotationPoint(2F, 4.5F, -3F);
		this.WingLeft.setTextureSize(128, 64);
		this.WingLeft.mirror = true;
		this.setRotation(this.WingLeft, -0.1745329F, 0F, 0F);
		this.TailMiddle = new ModelRenderer(this, 17, 20);
		this.TailMiddle.addBox(-1.5F, 0F, 0F, 3, 1, 14);
		this.TailMiddle.setRotationPoint(0F, 5F, 7F);
		this.TailMiddle.setTextureSize(128, 64);
		this.TailMiddle.mirror = true;
		this.setRotation(this.TailMiddle, 0.3490659F, 0F, 0F);
		this.TailLeft = new ModelRenderer(this, 20, 35);
		this.TailLeft.addBox(0F, 0F, 0F, 3, 1, 12);
		this.TailLeft.setRotationPoint(0F, 5F, 7F);
		this.TailLeft.setTextureSize(128, 64);
		this.TailLeft.mirror = true;
		this.setRotation(this.TailLeft, 0F, 0.3490659F, 0.1745329F);
		this.TailRight = new ModelRenderer(this, 20, 35);
		this.TailRight.addBox(-3F, 0F, 0F, 3, 1, 12);
		this.TailRight.setRotationPoint(0F, 5F, 7F);
		this.TailRight.setTextureSize(128, 64);
		this.TailRight.mirror = true;
		this.setRotation(this.TailRight, 0F, -0.3490659F, -0.1745329F);
		this.NeckBase = new ModelRenderer(this, 66, 13);
		this.NeckBase.addBox(-3F, -4F, -4F, 6, 4, 4);
		this.NeckBase.setRotationPoint(0F, 8.7F, -7.5F);
		this.NeckBase.setTextureSize(128, 64);
		this.NeckBase.mirror = true;
		this.setRotation(this.NeckBase, -0.669215F, 0F, 0F);
		this.NeckTop = new ModelRenderer(this, 44, 6);
		this.NeckTop.addBox(-2F, -3F, -3F, 4, 3, 3);
		this.NeckTop.setRotationPoint(0F, 6F, -10F);
		this.NeckTop.setTextureSize(128, 64);
		this.NeckTop.mirror = true;
		this.setRotation(this.NeckTop, -0.9599311F, 0F, 0F);
		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.addBox(-4F, -6F, -8F, 8, 7, 13);
		this.Head.setRotationPoint(0F, 3.5F, -10F);
		this.Head.setTextureSize(128, 64);
		this.Head.mirror = true;
		this.setRotation(this.Head, 0.0872665F, 0F, 0F);
		this.BeakJaw = new ModelRenderer(this, 80, 15);
		this.BeakJaw.addBox(-2F, 1.2F, -5.2F, 4, 1, 6);
		this.BeakJaw.setRotationPoint(0F, 3.5F, -10F);
		this.BeakJaw.setTextureSize(128, 64);
		this.BeakJaw.mirror = true;
		this.setRotation(this.BeakJaw, 0.1745329F, 0F, 0F);
		this.Teeth = new ModelRenderer(this, 44, 25);
		this.Teeth.addBox(-2F, 0.2F, -5.2F, 4, 1, 6);
		this.Teeth.setRotationPoint(0F, 3.5F, -10F);
		this.Teeth.setTextureSize(128, 64);
		this.Teeth.mirror = true;
		this.setRotation(this.Teeth, 0.1745329F, 0F, 0F);
		this.FeatherRight = new ModelRenderer(this, 78, 22);
		this.FeatherRight.addBox(-3.5F, -4.6F, 1F, 1, 5, 5);
		this.FeatherRight.setRotationPoint(0F, 3.5F, -10F);
		this.FeatherRight.setTextureSize(128, 64);
		this.FeatherRight.mirror = true;
		this.setRotation(this.FeatherRight, 0F, -0.4363323F, 0.0523599F);
		this.FeatherLeft = new ModelRenderer(this, 78, 22);
		this.FeatherLeft.addBox(2.533333F, -4.6F, 1F, 1, 5, 5);
		this.FeatherLeft.setRotationPoint(0F, 3.5F, -10F);
		this.FeatherLeft.setTextureSize(128, 64);
		this.FeatherLeft.mirror = true;
		this.setRotation(this.FeatherLeft, 0F, 0.4363323F, -0.0523599F);
		this.FeatherLeft.mirror = false;
		this.FeatherTop = new ModelRenderer(this, 43, 32);
		this.FeatherTop.addBox(-3F, -6.5F, -2F, 6, 1, 8);
		this.FeatherTop.setRotationPoint(0F, 3.5F, -10F);
		this.FeatherTop.setTextureSize(128, 64);
		this.FeatherTop.mirror = true;
		this.setRotation(this.FeatherTop, 0.3490659F, 0F, 0F);
		this.ToeLeftRight = new ModelRenderer(this, 7, 0);
		this.ToeLeftRight.addBox(0F, 15F, -3F, 1, 2, 2);
		this.ToeLeftRight.setRotationPoint(-3F, 7F, -2F);
		this.ToeLeftRight.setTextureSize(128, 64);
		this.ToeLeftRight.mirror = true;
		this.setRotation(this.ToeLeftRight, 0F, -0.2617994F, 0F);
		this.ToeMidRight = new ModelRenderer(this, 7, 0);
		this.ToeMidRight.addBox(-0.5F, 15F, -4F, 1, 2, 2);
		this.ToeMidRight.setRotationPoint(-3F, 7F, -2F);
		this.ToeMidRight.setTextureSize(128, 64);
		this.ToeMidRight.mirror = true;
		this.setRotation(this.ToeMidRight, 0F, 0F, 0F);
		this.ToeRightRight = new ModelRenderer(this, 7, 0);
		this.ToeRightRight.addBox(-1F, 15F, -3F, 1, 2, 2);
		this.ToeRightRight.setRotationPoint(-3F, 7F, -2F);
		this.ToeRightRight.setTextureSize(128, 64);
		this.ToeRightRight.mirror = true;
		this.setRotation(this.ToeRightRight, 0F, 0.2617994F, 0F);
		this.ToeLeftLeft = new ModelRenderer(this, 7, 0);
		this.ToeLeftLeft.addBox(0F, 15F, -3F, 1, 2, 2);
		this.ToeLeftLeft.setRotationPoint(3F, 7F, -2F);
		this.ToeLeftLeft.setTextureSize(128, 64);
		this.ToeLeftLeft.mirror = true;
		this.setRotation(this.ToeLeftLeft, 0F, -0.2617994F, 0F);
		this.ToeMidLeft = new ModelRenderer(this, 7, 0);
		this.ToeMidLeft.addBox(-0.5F, 15F, -4F, 1, 2, 2);
		this.ToeMidLeft.setRotationPoint(3F, 7F, -2F);
		this.ToeMidLeft.setTextureSize(128, 64);
		this.ToeMidLeft.mirror = true;
		this.setRotation(this.ToeMidLeft, 0F, 0F, 0F);
		this.ToeRightLeft = new ModelRenderer(this, 7, 0);
		this.ToeRightLeft.addBox(-1F, 15F, -3F, 1, 2, 2);
		this.ToeRightLeft.setRotationPoint(3F, 7F, -2F);
		this.ToeRightLeft.setTextureSize(128, 64);
		this.ToeRightLeft.mirror = true;
		this.setRotation(this.ToeRightLeft, 0F, 0.2617994F, 0F);
		this.Dart1 = new ModelRenderer(this, 0, 0);
		this.Dart1.addBox(-0.5F, -6F, -0.5F, 1, 6, 1);
		this.Dart1.setRotationPoint(-2F, 4F, 1F);
		this.Dart1.setTextureSize(128, 64);
		this.Dart1.mirror = true;
		this.setRotation(this.Dart1, 0.5235988F, 0F, 0F);
		this.Dart2 = new ModelRenderer(this, 0, 0);
		this.Dart2.addBox(-0.5F, -6F, -0.5F, 1, 6, 1);
		this.Dart2.setRotationPoint(2F, 4F, 1F);
		this.Dart2.setTextureSize(128, 64);
		this.Dart2.mirror = true;
		this.setRotation(this.Dart2, 0.5235988F, 0F, 0F);
		this.Dart2.mirror = false;
		this.Dart3 = new ModelRenderer(this, 0, 0);
		this.Dart3.addBox(-0.5F, -6F, -0.5F, 1, 6, 1);
		this.Dart3.setRotationPoint(2F, 5F, 4F);
		this.Dart3.setTextureSize(128, 64);
		this.Dart3.mirror = true;
		this.setRotation(this.Dart3, 0.5235988F, -0.1745329F, 0F);
		this.Dart3.mirror = false;
		this.Dart4 = new ModelRenderer(this, 0, 0);
		this.Dart4.addBox(-0.5F, -6F, -0.5F, 1, 6, 1);
		this.Dart4.setRotationPoint(-2F, 5F, 4F);
		this.Dart4.setTextureSize(128, 64);
		this.Dart4.mirror = true;
		this.setRotation(this.Dart4, 0.5235988F, 0.1745329F, 0F);
		this.Dart5 = new ModelRenderer(this, 0, 0);
		this.Dart5.addBox(-0.5F, -6F, -0.5F, 1, 6, 1);
		this.Dart5.setRotationPoint(-2F, 6F, 7F);
		this.Dart5.setTextureSize(128, 64);
		this.Dart5.mirror = true;
		this.setRotation(this.Dart5, 0.5235988F, 0.3490659F, 0F);
		this.Dart6 = new ModelRenderer(this, 0, 0);
		this.Dart6.addBox(-0.5F, -6F, -0.5F, 1, 6, 1);
		this.Dart6.setRotationPoint(2F, 6F, 7F);
		this.Dart6.setTextureSize(128, 64);
		this.Dart6.mirror = true;
		this.setRotation(this.Dart6, 0.5235988F, -0.1745329F, 0F);
		this.Dart6.mirror = false;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);

		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.LegTopRight.render(f5);
		this.LegTopLeft.render(f5);
		this.LegMidRight.render(f5);
		this.LegMidLeft.render(f5);
		this.LegBottomRight.render(f5);
		this.LegBottomLeft.render(f5);
		this.FootRight.render(f5);
		this.FootLeft.render(f5);
		this.LegFeatheredRight.render(f5);
		this.LegFeatheredLeft.render(f5);
		this.BodyFront.render(f5);
		this.BodyBack.render(f5);
		this.ShoulderRight.render(f5);
		this.ShoulderLeft.render(f5);
		this.WingBaseRight.render(f5);
		this.WingBaseLeft.render(f5);
		this.WingRight.render(f5);
		this.WingLeft.render(f5);
		this.TailMiddle.render(f5);
		this.TailLeft.render(f5);
		this.TailRight.render(f5);
		this.NeckBase.render(f5);
		this.NeckTop.render(f5);
		this.Head.render(f5);
		this.BeakJaw.render(f5);
		this.Teeth.render(f5);
		this.FeatherRight.render(f5);
		this.FeatherLeft.render(f5);
		this.FeatherTop.render(f5);
		this.ToeLeftRight.render(f5);
		this.ToeMidRight.render(f5);
		this.ToeRightRight.render(f5);
		this.ToeLeftLeft.render(f5);
		this.ToeMidLeft.render(f5);
		this.ToeRightLeft.render(f5);
		this.Dart1.render(f5);
		this.Dart2.render(f5);
		this.Dart3.render(f5);
		this.Dart4.render(f5);
		this.Dart5.render(f5);
		this.Dart6.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
			float scaleFactor, Entity entity)
	{
		//super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		EntityCockatrice cockatrice = (EntityCockatrice) entity;

		float f6 = 3.141593F;

		this.Head.rotateAngleX = headPitch / 57.29578F;
		this.Head.rotateAngleY = netHeadYaw / 57.29578F;

		float rightLegRotation;
		float leftLegRotation;

		float legSwingSpeed = 0.7F;
		float legSwingLength = 0.8F;

		rightLegRotation = MathHelper.cos(limbSwing * legSwingSpeed) * legSwingLength * limbSwingAmount;
		leftLegRotation = MathHelper.cos(limbSwing * legSwingSpeed + 3.141593F) * legSwingLength * limbSwingAmount;

		this.LegFeatheredRight.rotateAngleX = -0.2094395F + rightLegRotation;
		this.LegTopRight.rotateAngleX = -0.2094395F + rightLegRotation;
		this.LegMidRight.rotateAngleX = 0.8726646F + rightLegRotation;
		this.LegBottomRight.rotateAngleX = -0.6981317F + rightLegRotation;
		this.ToeLeftRight.rotateAngleX = rightLegRotation;
		this.ToeMidRight.rotateAngleX = rightLegRotation;
		this.ToeRightRight.rotateAngleX = rightLegRotation;
		this.FootRight.rotateAngleX = rightLegRotation;

		this.LegFeatheredLeft.rotateAngleX = -0.2094395F + leftLegRotation;
		this.LegTopLeft.rotateAngleX = -0.2094395F + leftLegRotation;
		this.LegMidLeft.rotateAngleX = 0.8726646F + leftLegRotation;
		this.LegBottomLeft.rotateAngleX = -0.6981317F + leftLegRotation;
		this.ToeLeftLeft.rotateAngleX = leftLegRotation;
		this.ToeMidLeft.rotateAngleX = leftLegRotation;
		this.ToeRightLeft.rotateAngleX = leftLegRotation;
		this.FootLeft.rotateAngleX = leftLegRotation;

		/*this.Neck1.rotateAngleX = 0.0F;
		this.Neck2.rotateAngleX = 0.0F;
		this.Neck1.rotateAngleY = this.Head.rotateAngleY;
		this.Neck2.rotateAngleY = this.Head.rotateAngleY;*/

		this.FeatherTop.rotateAngleY = this.Head.rotateAngleY;
		this.FeatherTop.rotateAngleX = 0.3490659F + this.Head.rotateAngleX;
		this.FeatherRight.rotateAngleY = -0.4363323F + this.Head.rotateAngleY;
		this.FeatherRight.rotateAngleX = this.Head.rotateAngleX;
		this.FeatherLeft.rotateAngleY = 0.4363323F + this.Head.rotateAngleY;
		this.FeatherLeft.rotateAngleX = this.Head.rotateAngleX;

		this.BeakJaw.rotateAngleY = this.Head.rotateAngleY;
		this.BeakJaw.rotateAngleX = 0.1745329F + this.Head.rotateAngleX;
		this.Teeth.rotateAngleY = this.Head.rotateAngleY;
		this.Teeth.rotateAngleX = 0.1745329F + this.Head.rotateAngleX;
		
		/*this.feather1.rotateAngleX = 0.1745329F + this.Head.rotateAngleX;
		this.feather1.rotateAngleY = 0.2268928F + this.Head.rotateAngleY;
		
		this.feather2.rotateAngleX = -0.1745329F + this.Head.rotateAngleX;
		this.feather2.rotateAngleY = -0.2268928F + this.Head.rotateAngleY;

		this.feather3.rotateAngleX = 0.1745329F + this.Head.rotateAngleX;
		this.feather3.rotateAngleY = -0.2268928F + this.Head.rotateAngleY;
		
		this.feather4.rotateAngleX = -0.1745329F + this.Head.rotateAngleX;
		this.feather4.rotateAngleY = 0.2268928F + this.Head.rotateAngleY;*/
	}

}
