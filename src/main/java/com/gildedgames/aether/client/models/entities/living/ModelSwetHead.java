package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

/**
 * Swet - Undefined
 * Created using Tabula 5.1.0
 */
public class ModelSwetHead extends ModelBase
{
	public ModelRenderer InnerBody;

	public ModelRenderer OuterBody;

	public ModelRenderer TopLeftTail;

	public ModelRenderer TopRightTail;

	public ModelRenderer BottomRightTail;

	public ModelRenderer BottomLeftTail;

	public ModelRenderer BackLeftTail;

	public ModelRenderer BackRightTail;

	public ModelSwetHead()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.InnerBody = new ModelRenderer(this, 0, 20);
		this.InnerBody.setRotationPoint(0.0F, 15.0F, -2.0F);
		this.InnerBody.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.25F);
		this.TopRightTail = new ModelRenderer(this, 40, 0);
		this.TopRightTail.mirror = true;
		this.TopRightTail.setRotationPoint(-3.5F, -1.0F, -3.0F);
		this.TopRightTail.addBox(-5.0F, 0.0F, 0.0F, 5, 11, 0, 0.0F);
		this.setRotateAngle(this.TopRightTail, 1.3962634015954636F, -0.3490658503988659F, 1.2217304763960306F);
		this.BottomLeftTail = new ModelRenderer(this, 50, 0);
		this.BottomLeftTail.setRotationPoint(3.5F, 1.0F, -3.0F);
		this.BottomLeftTail.addBox(0.0F, 0.0F, 0.0F, 5, 11, 0, 0.0F);
		this.setRotateAngle(this.BottomLeftTail, 1.7453292519943295F, 0.3490658503988659F, 1.0471975511965976F);
		this.OuterBody = new ModelRenderer(this, 0, 0);
		this.OuterBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.OuterBody.addBox(-5.0F, -5.0F, -5.0F, 10, 10, 10, 0.0F);
		this.TopLeftTail = new ModelRenderer(this, 40, 0);
		this.TopLeftTail.setRotationPoint(3.5F, -1.0F, -3.0F);
		this.TopLeftTail.addBox(0.0F, 0.0F, 0.0F, 5, 11, 0, 0.0F);
		this.setRotateAngle(this.TopLeftTail, 1.3962634015954636F, 0.3490658503988659F, -1.2217304763960306F);
		this.BottomRightTail = new ModelRenderer(this, 50, 0);
		this.BottomRightTail.mirror = true;
		this.BottomRightTail.setRotationPoint(-3.5F, 1.0F, -3.0F);
		this.BottomRightTail.addBox(-5.0F, 0.0F, 0.0F, 5, 11, 0, 0.0F);
		this.setRotateAngle(this.BottomRightTail, 1.7453292519943295F, -0.3490658503988659F, -1.0471975511965976F);
		this.BackRightTail = new ModelRenderer(this, 50, 0);
		this.BackRightTail.setRotationPoint(-1.5F, 0.0F, 2.0F);
		this.BackRightTail.addBox(-5.0F, 0.0F, 0.0F, 5, 11, 0, 0.0F);
		this.setRotateAngle(this.BackRightTail, 1.6580627893946132F, -0.3490658503988659F, -1.3962634015954636F);
		this.BackLeftTail = new ModelRenderer(this, 50, 0);
		this.BackLeftTail.mirror = true;
		this.BackLeftTail.setRotationPoint(1.5F, 0.0F, 2.0F);
		this.BackLeftTail.addBox(0.0F, 0.0F, 0.0F, 5, 11, 0, 0.0F);
		this.setRotateAngle(this.BackLeftTail, 1.6580627893946132F, 0.3490658503988659F, 1.3962634015954636F);
		this.OuterBody.addChild(this.TopRightTail);
		this.OuterBody.addChild(this.BottomLeftTail);
		this.InnerBody.addChild(this.OuterBody);
		this.OuterBody.addChild(this.TopLeftTail);
		this.OuterBody.addChild(this.BottomRightTail);
		this.OuterBody.addChild(this.BackRightTail);
		this.OuterBody.addChild(this.BackLeftTail);
	}

	@Override
	public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.InnerBody.offsetX, this.InnerBody.offsetY, this.InnerBody.offsetZ);
		GlStateManager.translate(this.InnerBody.rotationPointX * f5, this.InnerBody.rotationPointY * f5, this.InnerBody.rotationPointZ * f5);
		GlStateManager.scale(0.8D, 0.8D, 0.8D);
		GlStateManager.translate(-this.InnerBody.offsetX, -this.InnerBody.offsetY, -this.InnerBody.offsetZ);
		GlStateManager.translate(-this.InnerBody.rotationPointX * f5, -this.InnerBody.rotationPointY * f5, -this.InnerBody.rotationPointZ * f5);

		EntitySwet swet = (EntitySwet) entity;

		final float sc = ((swet.getFoodSaturation() - 1));
		final float s = sc / 1.2F;
		final float wiggle = (swet.prevSquishFactor + (swet.squishFactor - swet.prevSquishFactor) * f5) / (f1 * 0.5F + 1.0F);

		GlStateManager.translate(0, -s * wiggle, (-sc / Math.cos(f5 * 10) * wiggle) / 2);

		this.InnerBody.render(f5);
		GlStateManager.popMatrix();
	}

	public void renderRaw(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5)
	{
		this.InnerBody.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(final ModelRenderer modelRenderer, final float x, final float y, final float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
