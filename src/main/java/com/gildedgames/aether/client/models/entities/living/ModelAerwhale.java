package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.util.flying.EntityFlying;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelAerwhale extends ModelBase
{

	//fields
	ModelRenderer FrontBody;

	ModelRenderer RightFin;

	ModelRenderer BottomPartHead;

	ModelRenderer LeftFin;

	ModelRenderer BottomPartMiddlebody;

	ModelRenderer Head;

	ModelRenderer MiddleFin;

	ModelRenderer BackfinRight;

	ModelRenderer BackBody;

	ModelRenderer BackfinLeft;

	ModelRenderer Middlebody;

	public ModelAerwhale()
	{
		this.textureWidth = 512;
		this.textureHeight = 64;

		this.FrontBody = new ModelRenderer(this, 0, 0);
		this.FrontBody.addBox(-11.5F, -1F, -0.5F, 19, 5, 21);
		this.FrontBody.setRotationPoint(2F, 6F, 38F);
		this.FrontBody.setTextureSize(512, 64);
		this.FrontBody.mirror = true;
		this.setRotation(this.FrontBody, -0.1047198F, 0F, 0F);
		this.RightFin = new ModelRenderer(this, 446, 1);
		this.RightFin.addBox(-20F, -2F, -6F, 19, 3, 14);
		this.RightFin.setRotationPoint(-10F, 4F, 10F);
		this.RightFin.setTextureSize(512, 64);
		this.RightFin.mirror = true;
		this.setRotation(this.RightFin, -0.148353F, 0.2094395F, 0F);
		this.RightFin.mirror = false;
		this.BottomPartHead = new ModelRenderer(this, 116, 28);
		this.BottomPartHead.addBox(-13F, 4F, -15F, 26, 6, 30);
		this.BottomPartHead.setRotationPoint(0F, 0F, 0F);
		this.BottomPartHead.setTextureSize(512, 64);
		this.BottomPartHead.mirror = true;
		this.setRotation(this.BottomPartHead, 0F, 0F, 0F);
		this.LeftFin = new ModelRenderer(this, 446, 1);
		this.LeftFin.addBox(1F, -2F, -6F, 19, 3, 14);
		this.LeftFin.setRotationPoint(10F, 4F, 10F);
		this.LeftFin.setTextureSize(512, 64);
		this.LeftFin.mirror = true;
		this.setRotation(this.LeftFin, -0.148353F, -0.2094395F, 0F);
		this.BottomPartMiddlebody = new ModelRenderer(this, 16, 32);
		this.BottomPartMiddlebody.addBox(-12F, 5F, -1F, 24, 6, 26);
		this.BottomPartMiddlebody.setRotationPoint(0F, -1F, 14F);
		this.BottomPartMiddlebody.setTextureSize(512, 64);
		this.BottomPartMiddlebody.mirror = true;
		this.setRotation(this.BottomPartMiddlebody, 0F, 0F, 0F);
		this.Head = new ModelRenderer(this, 408, 18);
		this.Head.addBox(-12F, -9F, -14F, 24, 18, 28);
		this.Head.setRotationPoint(0F, 0F, 0F);
		this.Head.setTextureSize(512, 64);
		this.Head.mirror = true;
		this.setRotation(this.Head, 0F, 0F, 0F);
		this.MiddleFin = new ModelRenderer(this, 318, 35);
		this.MiddleFin.addBox(-1F, -11F, 7F, 2, 7, 8);
		this.MiddleFin.setRotationPoint(0F, -1F, 14F);
		this.MiddleFin.setTextureSize(512, 64);
		this.MiddleFin.mirror = true;
		this.setRotation(this.MiddleFin, -0.1441704F, 0F, 0F);
		this.BackfinRight = new ModelRenderer(this, 261, 5);
		this.BackfinRight.addBox(-11F, 0F, -6F, 15, 3, 24);
		this.BackfinRight.setRotationPoint(-4F, 5F, 59F);
		this.BackfinRight.setTextureSize(512, 64);
		this.BackfinRight.mirror = true;
		this.setRotation(this.BackfinRight, -0.1047198F, -0.7330383F, 0F);
		this.BackfinRight.mirror = false;
		this.BackBody = new ModelRenderer(this, 228, 32);
		this.BackBody.addBox(-10.5F, -9F, -2F, 17, 10, 22);
		this.BackBody.setRotationPoint(2F, 5F, 38F);
		this.BackBody.setTextureSize(512, 64);
		this.BackBody.mirror = true;
		this.setRotation(this.BackBody, -0.1047198F, 0F, 0F);
		this.BackfinLeft = new ModelRenderer(this, 261, 5);
		this.BackfinLeft.addBox(-4F, 0F, -6F, 13, 3, 24);
		this.BackfinLeft.setRotationPoint(5F, 5F, 59F);
		this.BackfinLeft.setTextureSize(512, 64);
		this.BackfinLeft.mirror = true;
		this.setRotation(this.BackfinLeft, -0.1047198F, 0.7330383F, 0F);
		this.Middlebody = new ModelRenderer(this, 314, 25);
		this.Middlebody.addBox(-11F, -5F, -1F, 22, 14, 25);
		this.Middlebody.setRotationPoint(0F, -1F, 14F);
		this.Middlebody.setTextureSize(512, 64);
		this.Middlebody.mirror = true;
		this.setRotation(this.Middlebody, -0.0698132F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.FrontBody.render(f5);
		this.RightFin.render(f5);
		this.BottomPartHead.render(f5);
		this.LeftFin.render(f5);
		this.BottomPartMiddlebody.render(f5);
		this.Head.render(f5);
		this.MiddleFin.render(f5);
		this.BackfinRight.render(f5);
		this.BackBody.render(f5);
		this.BackfinLeft.render(f5);
		this.Middlebody.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

		EntityFlying flying = (EntityFlying)entity;
		float deltaTime = ageInTicks - (float)flying.ticksExisted;

		float tailAnimation = flying.getTailAnimation(deltaTime);

/*		this.BackfinLeft.rotateAngleY = 0.7330383F + (MathHelper.sin(ageInTicks * 0.152F) * 0.02F * (float)Math.PI);
		this.BackfinRight.rotateAngleY = -0.7330383F + (MathHelper.sin(ageInTicks * 0.152F) * 0.02F * (float)Math.PI);

		this.BackfinLeft.rotateAngleZ = (MathHelper.sin(ageInTicks * 0.152F) * 0.02F * (float)Math.PI);
		this.BackfinRight.rotateAngleZ = (MathHelper.sin(ageInTicks * 0.152F) * 0.02F * (float)Math.PI);

		this.BackBody.rotateAngleY = MathHelper.sin(ageInTicks * 0.1662F) * 0.02F * (float)Math.PI;
		this.FrontBody.rotateAngleY = MathHelper.sin(ageInTicks * 0.1662F) * 0.02F * (float)Math.PI;*/

		/*this.Middlebody.rotateAngleY = MathHelper.sin(ageInTicks * 0.1762F) * 0.02F * (float)Math.PI;
		this.BottomPartMiddlebody.rotateAngleY = MathHelper.sin(ageInTicks * 0.1762F) * 0.02F * (float)Math.PI;
		this.MiddleFin.rotateAngleY = MathHelper.sin(ageInTicks * 0.1762F) * 0.02F * (float)Math.PI;*/

		/*float angle = -0.1047198F + (float) ((float) flying.motionX * Math.PI);

		this.BackBody.rotateAngleY = (float) Math.toRadians(this.limitAngle((float)Math.toDegrees(this.BackBody.rotateAngleY), (float)Math.toDegrees(angle), 2.0F));
		this.FrontBody.rotateAngleY = this.BackBody.rotateAngleY;

		this.BackfinLeft.rotateAngleY = 0.7330383F + this.BackBody.rotateAngleY;
		this.BackfinRight.rotateAngleY = -0.7330383F + this.BackBody.rotateAngleY;

		this.BackfinLeft.offsetX = this.BackBody.rotateAngleY;
		this.BackfinRight.offsetX = this.BackBody.rotateAngleY;*/
	}

	protected float limitAngle(float p_75639_1_, float p_75639_2_, float p_75639_3_)
	{
		float f = MathHelper.wrapDegrees(p_75639_2_ - p_75639_1_);

		if (f > p_75639_3_)
		{
			f = p_75639_3_;
		}

		if (f < -p_75639_3_)
		{
			f = -p_75639_3_;
		}

		float f1 = p_75639_1_ + f;

		if (f1 < 0.0F)
		{
			f1 += 360.0F;
		}
		else if (f1 > 360.0F)
		{
			f1 -= 360.0F;
		}

		return f1;
	}

}
