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
		textureWidth = 128;
		textureHeight = 64;

		bipedHead = new ModelRenderer(this, 45, 40);
		bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8);
		bipedHead.setRotationPoint(0F, 0F, 0F);
		bipedHead.setTextureSize(128, 64);
		bipedHead.mirror = true;
		setRotation(bipedHead, 0F, 0F, 0F);
		arms = new ModelRenderer(this, 65, 27);
		arms.addBox(-3F, 3F, -2F, 16, 4, 4);
		arms.setRotationPoint(-5F, 2F, 0F);
		arms.setTextureSize(128, 64);
		arms.mirror = true;
		setRotation(arms, -0.7330383F, 0F, 0F);
		bipedRightLeg = new ModelRenderer(this, 0, 16);
		bipedRightLeg.addBox(-2F, 0F, -2F, 4, 12, 4);
		bipedRightLeg.setRotationPoint(-2F, 12F, 0F);
		bipedRightLeg.setTextureSize(128, 64);
		bipedRightLeg.mirror = true;
		setRotation(bipedRightLeg, 0F, 0F, 0F);
		bipedRightLeg.mirror = false;
		bipedLeftLeg = new ModelRenderer(this, 0, 16);
		bipedLeftLeg.addBox(-2F, 0F, -2F, 4, 12, 4);
		bipedLeftLeg.setRotationPoint(2F, 12F, 0F);
		bipedLeftLeg.setTextureSize(128, 64);
		bipedLeftLeg.mirror = true;
		setRotation(bipedLeftLeg, 0F, 0F, 0F);
		leftshoulder = new ModelRenderer(this, 43, 27);
		leftshoulder.addBox(-1F, -2F, -2F, 4, 5, 4);
		leftshoulder.setRotationPoint(5F, 2F, 0F);
		leftshoulder.setTextureSize(128, 64);
		leftshoulder.mirror = true;
		setRotation(leftshoulder, -0.7330383F, 0F, 0F);
		vial1 = new ModelRenderer(this, 60, 16);
		vial1.addBox(4.5F, 9F, 0.5F, 1, 4, 1);
		vial1.setRotationPoint(0F, 0F, 0F);
		vial1.setTextureSize(128, 64);
		vial1.mirror = true;
		setRotation(vial1, 0F, 0F, 0F);
		rightshoulder = new ModelRenderer(this, 43, 27);
		rightshoulder.addBox(-3F, -2F, -2F, 4, 5, 4);
		rightshoulder.setRotationPoint(-5F, 2F, 0F);
		rightshoulder.setTextureSize(128, 64);
		rightshoulder.mirror = true;
		setRotation(rightshoulder, -0.7330383F, 0F, 0F);
		rightshoulder.mirror = false;
		bipedBody = new ModelRenderer(this, 16, 16);
		bipedBody.addBox(-4F, 0F, -2F, 8, 12, 4);
		bipedBody.setRotationPoint(0F, 0F, 0F);
		bipedBody.setTextureSize(128, 64);
		bipedBody.mirror = true;
		setRotation(bipedBody, 0F, 0F, 0F);
		bigbag = new ModelRenderer(this, 1, 34);
		bigbag.addBox(-6F, -9F, 2F, 12, 17, 8);
		bigbag.setRotationPoint(0F, 0F, 0F);
		bigbag.setTextureSize(128, 64);
		bigbag.mirror = true;
		setRotation(bigbag, 0F, 0F, 0F);
		belt = new ModelRenderer(this, 66, 16);
		belt.addBox(-4.5F, 10F, -2.5F, 9, 2, 5);
		belt.setRotationPoint(0F, 0F, 0F);
		belt.setTextureSize(128, 64);
		belt.mirror = true;
		setRotation(belt, 0F, 0F, 0F);
		coinbag = new ModelRenderer(this, 43, 15);
		coinbag.addBox(-6F, 10F, -2F, 3, 6, 4);
		coinbag.setRotationPoint(0F, 0F, 0F);
		coinbag.setTextureSize(128, 64);
		coinbag.mirror = true;
		setRotation(coinbag, 0F, 0F, 0.0872665F);
		vial2 = new ModelRenderer(this, 60, 16);
		vial2.addBox(4.5F, 9F, -1.5F, 1, 4, 1);
		vial2.setRotationPoint(0F, 0F, 0F);
		vial2.setTextureSize(128, 64);
		vial2.mirror = true;
		setRotation(vial2, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		//super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		bipedHead.render(f5);
		arms.render(f5);
		bipedRightLeg.render(f5);
		bipedLeftLeg.render(f5);
		leftshoulder.render(f5);
		vial1.render(f5);
		rightshoulder.render(f5);
		bipedBody.render(f5);
		bigbag.render(f5);
		belt.render(f5);
		coinbag.render(f5);
		vial2.render(f5);
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
