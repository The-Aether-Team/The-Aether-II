package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.util.flying.EntityFlying;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelAerwhale - Either Mojang or a mod author
 * Created using Tabula 7.0.0
 */
public class ModelAerwhale extends ModelBase
{
	public ModelRenderer Head;

	public ModelRenderer Middlebody;

	public ModelRenderer LeftFin;

	public ModelRenderer RightFin;

	public ModelRenderer BottomPartHead;

	public ModelRenderer MiddleFin;

	public ModelRenderer BackBody;

	public ModelRenderer BottomPartMiddlebody;

	public ModelRenderer FrontBody;

	public ModelRenderer BackfinLeft;

	public ModelRenderer BackfinRight;

	public ModelAerwhale()
	{
		this.textureWidth = 512;
		this.textureHeight = 64;
		this.BottomPartHead = new ModelRenderer(this, 116, 28);
		this.BottomPartHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.BottomPartHead.addBox(-13.0F, 4.0F, -15.0F, 26, 6, 30, 0.0F);
		this.BackBody = new ModelRenderer(this, 228, 32);
		this.BackBody.setRotationPoint(2.0F, 5.0F, 25.0F);
		this.BackBody.addBox(-10.5F, -9.0F, -2.0F, 17, 10, 22, 0.0F);
		this.RightFin = new ModelRenderer(this, 446, 1);
		this.RightFin.setRotationPoint(-10.0F, 4.0F, 10.0F);
		this.RightFin.addBox(-20.0F, -2.0F, -6.0F, 19, 3, 14, 0.0F);
		this.setRotateAngle(this.RightFin, -0.148352986419518F, 0.20943951023931953F, -0.19530234329816545F);
		this.Head = new ModelRenderer(this, 408, 18);
		this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Head.addBox(-12.0F, -9.0F, -14.0F, 24, 18, 28, 0.0F);
		this.BackfinLeft = new ModelRenderer(this, 261, 5);
		this.BackfinLeft.setRotationPoint(5.0F, 0.0F, 25.0F);
		this.BackfinLeft.addBox(-4.0F, -2.0F, -8.0F, 13, 3, 24, 0.0F);
		this.setRotateAngle(this.BackfinLeft, -0.10471975511965977F, 0.6866125277345694F, -0.19530234329816545F);
		this.BottomPartMiddlebody = new ModelRenderer(this, 16, 32);
		this.BottomPartMiddlebody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.BottomPartMiddlebody.addBox(-12.0F, 5.0F, -1.0F, 24, 6, 26, 0.0F);
		this.BackfinRight = new ModelRenderer(this, 261, 5);
		this.BackfinRight.setRotationPoint(-4.0F, 0.0F, 25.0F);
		this.BackfinRight.addBox(-11.0F, -2.0F, -8.0F, 15, 3, 24, 0.0F);
		this.setRotateAngle(this.BackfinRight, -0.10471975511965977F, -0.7794640439406675F, 0.19530234329816545F);
		this.FrontBody = new ModelRenderer(this, 0, 0);
		this.FrontBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.FrontBody.addBox(-11.0F, 0.0F, -1.0F, 19, 5, 21, 0.0F);
		this.LeftFin = new ModelRenderer(this, 446, 1);
		this.LeftFin.setRotationPoint(10.0F, 4.0F, 10.0F);
		this.LeftFin.addBox(1.0F, -2.0F, -6.0F, 19, 3, 14, 0.0F);
		this.setRotateAngle(this.LeftFin, -0.1483529955148697F, -0.2094395011663437F, 0.19533973932266235F);
		this.MiddleFin = new ModelRenderer(this, 318, 35);
		this.MiddleFin.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.MiddleFin.addBox(-1.0F, -11.0F, 7.0F, 2, 7, 8, 0.0F);
		this.setRotateAngle(this.MiddleFin, -0.14416419621473162F, -0.012217304763960306F, 0.0F);
		this.Middlebody = new ModelRenderer(this, 314, 25);
		this.Middlebody.setRotationPoint(0.0F, -1.0F, 14.0F);
		this.Middlebody.addBox(-11.0F, -5.0F, -1.0F, 22, 14, 25, 0.0F);
		this.Head.addChild(this.BottomPartHead);
		this.Middlebody.addChild(this.BackBody);
		this.Head.addChild(this.RightFin);
		this.BackBody.addChild(this.BackfinLeft);
		this.Middlebody.addChild(this.BottomPartMiddlebody);
		this.BackBody.addChild(this.BackfinRight);
		this.BackBody.addChild(this.FrontBody);
		this.Head.addChild(this.LeftFin);
		this.Middlebody.addChild(this.MiddleFin);
		this.Head.addChild(this.Middlebody);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.Head.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
			float scaleFactor, Entity entity)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

		EntityFlying flying = (EntityFlying) entity;
		float deltaTime = ageInTicks - (float) flying.ticksExisted;

		float tailAnimation = flying.getTailAnimation(deltaTime);

		float time = -0.05F;

		this.RightFin.rotateAngleZ = (MathHelper.sin(ageInTicks * (time + 0.01F)) * 0.18F * (float) Math.PI);
		this.LeftFin.rotateAngleZ = -(MathHelper.sin(ageInTicks * (time + 0.01F)) * 0.18F * (float) Math.PI);

		this.BackBody.rotateAngleX = MathHelper.cos(ageInTicks * time) * 0.15F * (float) Math.PI;
		this.Head.rotateAngleX = MathHelper.cos(ageInTicks * time) * -0.15F * (float) Math.PI;
		this.Middlebody.rotateAngleX = MathHelper.sin(ageInTicks * time) * 0.15F * (float) Math.PI;

		this.BackfinRight.rotateAngleX = MathHelper.sin(ageInTicks * time) * 0.18F * (float) Math.PI;
		this.BackfinLeft.rotateAngleX = MathHelper.sin(ageInTicks * time) * 0.18F * (float) Math.PI;

		/*float angle = 0.1047198F + (float) (MathHelper.sin(ageInTicks * time) * ((flying.rotationYaw - flying.prevRotationYaw) / 360F) * Math.PI);

		this.Head.rotateAngleY = angle;
		this.BackBody.rotateAngleY = -this.Head.rotateAngleY;*/
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
