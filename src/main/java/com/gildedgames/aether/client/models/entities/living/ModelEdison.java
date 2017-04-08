package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEdison extends ModelBiped
{
	ModelRenderer arms;

	ModelRenderer leftshoulder;

	ModelRenderer vial1;

	ModelRenderer rightshoulder;

	ModelRenderer bigbag;

	ModelRenderer belt;

	ModelRenderer coinbag;

	ModelRenderer vial2;

	public ModelEdison()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;

		this.bipedHead = new ModelRenderer(this, 45, 40);
		this.bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8);
		this.bipedHead.setRotationPoint(0F, 0F, 0F);
		this.bipedHead.setTextureSize(128, 64);
		this.bipedHead.mirror = true;
		this.setRotation(this.bipedHead, 0F, 0F, 0F);
		this.arms = new ModelRenderer(this, 65, 27);
		this.arms.addBox(-3F, 3F, -2F, 16, 4, 4);
		this.arms.setRotationPoint(-5F, 2F, 0F);
		this.arms.setTextureSize(128, 64);
		this.arms.mirror = true;
		this.setRotation(this.arms, -0.7330383F, 0F, 0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.addBox(-2F, 0F, -2F, 4, 12, 4);
		this.bipedRightLeg.setRotationPoint(-2F, 12F, 0F);
		this.bipedRightLeg.setTextureSize(128, 64);
		this.bipedRightLeg.mirror = true;
		this.setRotation(this.bipedRightLeg, 0F, 0F, 0F);
		this.bipedRightLeg.mirror = false;
		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.addBox(-2F, 0F, -2F, 4, 12, 4);
		this.bipedLeftLeg.setRotationPoint(2F, 12F, 0F);
		this.bipedLeftLeg.setTextureSize(128, 64);
		this.bipedLeftLeg.mirror = true;
		this.setRotation(this.bipedLeftLeg, 0F, 0F, 0F);
		this.leftshoulder = new ModelRenderer(this, 43, 27);
		this.leftshoulder.addBox(-1F, -2F, -2F, 4, 5, 4);
		this.leftshoulder.setRotationPoint(5F, 2F, 0F);
		this.leftshoulder.setTextureSize(128, 64);
		this.leftshoulder.mirror = true;
		this.setRotation(this.leftshoulder, -0.7330383F, 0F, 0F);
		this.vial1 = new ModelRenderer(this, 60, 16);
		this.vial1.addBox(4.5F, 9F, 0.5F, 1, 4, 1);
		this.vial1.setRotationPoint(0F, 0F, 0F);
		this.vial1.setTextureSize(128, 64);
		this.vial1.mirror = true;
		this.setRotation(this.vial1, 0F, 0F, 0F);
		this.rightshoulder = new ModelRenderer(this, 43, 27);
		this.rightshoulder.addBox(-3F, -2F, -2F, 4, 5, 4);
		this.rightshoulder.setRotationPoint(-5F, 2F, 0F);
		this.rightshoulder.setTextureSize(128, 64);
		this.rightshoulder.mirror = true;
		this.setRotation(this.rightshoulder, -0.7330383F, 0F, 0F);
		this.rightshoulder.mirror = false;
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.addBox(-4F, 0F, -2F, 8, 12, 4);
		this.bipedBody.setRotationPoint(0F, 0F, 0F);
		this.bipedBody.setTextureSize(128, 64);
		this.bipedBody.mirror = true;
		this.setRotation(this.bipedBody, 0F, 0F, 0F);
		this.bigbag = new ModelRenderer(this, 1, 34);
		this.bigbag.addBox(-6F, -9F, 2F, 12, 17, 8);
		this.bigbag.setRotationPoint(0F, 0F, 0F);
		this.bigbag.setTextureSize(128, 64);
		this.bigbag.mirror = true;
		this.setRotation(this.bigbag, 0F, 0F, 0F);
		this.belt = new ModelRenderer(this, 66, 16);
		this.belt.addBox(-4.5F, 10F, -2.5F, 9, 2, 5);
		this.belt.setRotationPoint(0F, 0F, 0F);
		this.belt.setTextureSize(128, 64);
		this.belt.mirror = true;
		this.setRotation(this.belt, 0F, 0F, 0F);
		this.coinbag = new ModelRenderer(this, 43, 15);
		this.coinbag.addBox(-6F, 10F, -2F, 3, 6, 4);
		this.coinbag.setRotationPoint(0F, 0F, 0F);
		this.coinbag.setTextureSize(128, 64);
		this.coinbag.mirror = true;
		this.setRotation(this.coinbag, 0F, 0F, 0.0872665F);
		this.vial2 = new ModelRenderer(this, 60, 16);
		this.vial2.addBox(4.5F, 9F, -1.5F, 1, 4, 1);
		this.vial2.setRotationPoint(0F, 0F, 0F);
		this.vial2.setTextureSize(128, 64);
		this.vial2.mirror = true;
		this.setRotation(this.vial2, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		//super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.bipedHead.render(f5);
		this.arms.render(f5);
		this.bipedRightLeg.render(f5);
		this.bipedLeftLeg.render(f5);
		this.leftshoulder.render(f5);
		this.vial1.render(f5);
		this.rightshoulder.render(f5);
		this.bipedBody.render(f5);
		this.bigbag.render(f5);
		this.belt.render(f5);
		this.coinbag.render(f5);
		this.vial2.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
