package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelTempest extends ModelBase
{

	ModelRenderer main_body;

	ModelRenderer RB_cloud;

	ModelRenderer LB_cloud;

	ModelRenderer tail_2;

	ModelRenderer FR_cloud;

	ModelRenderer FL_cloud;

	ModelRenderer tail_1;

	public ModelTempest()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.main_body = new ModelRenderer(this, 0, 0);
		this.main_body.addBox(-6F, -4F, -7F, 12, 8, 14);
		this.main_body.setRotationPoint(0F, 10F, 0F);
		this.main_body.setTextureSize(64, 64);
		this.main_body.mirror = true;
		this.setRotation(this.main_body, 0F, 0F, 0F);
		this.RB_cloud = new ModelRenderer(this, 16, 22);
		this.RB_cloud.addBox(-8F, -2F, 0F, 2, 6, 6);
		this.RB_cloud.setRotationPoint(0F, 10F, 0F);
		this.RB_cloud.setTextureSize(64, 64);
		this.RB_cloud.mirror = true;
		this.setRotation(this.RB_cloud, 0F, 0F, 0F);
		this.LB_cloud = new ModelRenderer(this, 16, 22);
		this.LB_cloud.addBox(6F, -2F, 0F, 2, 6, 6);
		this.LB_cloud.setRotationPoint(0F, 10F, 0F);
		this.LB_cloud.setTextureSize(64, 64);
		this.LB_cloud.mirror = true;
		this.setRotation(this.LB_cloud, 0F, 0F, 0F);
		this.LB_cloud.mirror = false;
		this.tail_2 = new ModelRenderer(this, 32, 22);
		this.tail_2.addBox(-2F, -2F, -2F, 4, 4, 4);
		this.tail_2.setRotationPoint(0F, 10F, 19F);
		this.tail_2.setTextureSize(64, 64);
		this.tail_2.mirror = true;
		this.setRotation(this.tail_2, 0F, 0F, 0F);
		this.FR_cloud = new ModelRenderer(this, 0, 22);
		this.FR_cloud.addBox(-8F, -3F, -7F, 2, 6, 6);
		this.FR_cloud.setRotationPoint(0F, 10F, 0F);
		this.FR_cloud.setTextureSize(64, 64);
		this.FR_cloud.mirror = true;
		this.setRotation(this.FR_cloud, 0F, 0F, 0F);
		this.FL_cloud = new ModelRenderer(this, 0, 22);
		this.FL_cloud.addBox(6F, -3F, -7F, 2, 6, 6);
		this.FL_cloud.setRotationPoint(0F, 10F, 0F);
		this.FL_cloud.setTextureSize(64, 64);
		this.FL_cloud.mirror = true;
		this.setRotation(this.FL_cloud, 0F, 0F, 0F);
		this.FL_cloud.mirror = false;
		this.tail_1 = new ModelRenderer(this, 0, 34);
		this.tail_1.addBox(-3F, -3F, -3F, 6, 6, 6);
		this.tail_1.setRotationPoint(0F, 10F, 12F);
		this.tail_1.setTextureSize(64, 64);
		this.tail_1.mirror = true;
		this.setRotation(this.tail_1, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.main_body.render(f5);
		this.RB_cloud.render(f5);
		this.LB_cloud.render(f5);
		this.FR_cloud.render(f5);
		this.FL_cloud.render(f5);

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);

		this.tail_1.render(f5);
		this.tail_2.render(f5);

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public void renderTransparentTail(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.tail_1.render(f5);
		this.tail_2.render(f5);
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

		float boff = this.sinage2;
		float yOffset = 7.5F;

		float vertMotion = (float) (Math.sin(f * 20 / 57.2957795) * this.sinage * .5F);
		float PI = (float) Math.PI;
		float initialOffset = PI / 2;

		this.FR_cloud.rotationPointY = vertMotion + 10;
		this.FL_cloud.rotationPointX = vertMotion * .5F;
		this.LB_cloud.rotationPointY = 8 - vertMotion * .5F;
		this.RB_cloud.rotationPointY = 9 + vertMotion * .5F;

		this.tail_1.rotationPointX = (float) (Math.sin(f * 20 / 57.2957795) * f1 * .75F);

		this.tail_1.rotateAngleY = ((float) Math.pow(0.99f, -4)) * 1 * PI / 4 * (MathHelper.cos(-0.055f * f + initialOffset));
		this.tail_1.rotationPointY = 10 - vertMotion;

		this.tail_2.rotationPointX = ((float) Math.pow(0.99f, 1)) * 1 * PI / 4 * (MathHelper.cos(-0.055f * f + initialOffset));
		this.tail_2.rotationPointY = 10 - vertMotion * 1.25F;
		this.tail_2.rotateAngleY = this.tail_1.rotateAngleY + .25F;

		this.main_body.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
		this.RB_cloud.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
		this.LB_cloud.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
		this.tail_2.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
		this.FR_cloud.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
		this.FL_cloud.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
		this.tail_1.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
	}

	public float sinage;

	public float sinage2;
}
