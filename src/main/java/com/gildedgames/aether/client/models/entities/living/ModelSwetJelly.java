package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelSwetJelly extends ModelBase
{
	public ModelRenderer Base;

	public ModelRenderer Front;

	public ModelRenderer Top;

	public ModelRenderer Right;

	public ModelRenderer Left;

	public ModelRenderer Back;

	public ModelRenderer Bottom;

	public ModelSwetJelly()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.Right = new ModelRenderer(this, 0, 47);
		this.Right.setRotationPoint(-10.0F, 0.0F, 0.0F);
		this.Right.addBox(-6.5F, -6.5F, 0.0F, 13, 13, 2, 0.0F);
		this.setRotateAngle(Right, 0.0F, 1.5707963267948966F, 0.0F);
		this.Front = new ModelRenderer(this, 0, 47);
		this.Front.setRotationPoint(0.0F, 0.0F, -10.0F);
		this.Front.addBox(-6.5F, -6.5F, 0.0F, 13, 13, 2, 0.0F);
		this.Bottom = new ModelRenderer(this, 0, 32);
		this.Bottom.setRotationPoint(0.0F, 8.0F, 0.0F);
		this.Bottom.addBox(-6.5F, 0.0F, -6.5F, 13, 2, 13, 0.0F);
		this.Base = new ModelRenderer(this, 0, 0);
		this.Base.setRotationPoint(0.0F, 14.0F, 0.0F);
		this.Base.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
		this.Back = new ModelRenderer(this, 0, 47);
		this.Back.setRotationPoint(0.0F, 0.0F, 10.0F);
		this.Back.addBox(-6.5F, -6.5F, 0.0F, 13, 13, 2, 0.0F);
		this.setRotateAngle(Back, 0.0F, 3.141592653589793F, 0.0F);
		this.Left = new ModelRenderer(this, 0, 47);
		this.Left.mirror = true;
		this.Left.setRotationPoint(10.0F, 0.0F, 0.0F);
		this.Left.addBox(-6.5F, -6.5F, 0.0F, 13, 13, 2, 0.0F);
		this.setRotateAngle(Left, 0.0F, -1.5707963267948966F, 0.0F);
		this.Top = new ModelRenderer(this, 0, 47);
		this.Top.mirror = true;
		this.Top.setRotationPoint(0.0F, -10.0F, 0.0F);
		this.Top.addBox(-6.5F, -6.5F, 0.0F, 13, 13, 2, 0.0F);
		this.setRotateAngle(Top, -1.5707963267948966F, 0.0F, 0.0F);
		this.Base.addChild(this.Right);
		this.Base.addChild(this.Front);
		this.Base.addChild(this.Back);
		this.Base.addChild(this.Left);
		this.Base.addChild(this.Top);
	}

	@Override
	public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5)
	{
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 0.7F);

		EntitySwet swet = (EntitySwet) entity;
		final float wiggle = (swet.prevSquishFactor + (swet.squishFactor - swet.prevSquishFactor) * f5) / (f1 * 0.5F + 1.0F);

		this.Base.rotateAngleX = wiggle;
		this.Base.render(f5);

		GlStateManager.pushMatrix();

		float width = swet.getSquishPool();
		float height = width * 1.14f;

		GlStateManager.translate(0, -height / 2 + 1.2f,0);
		GlStateManager.scale(width, height, width);

		this.Bottom.render(f5);

		GlStateManager.popMatrix();

		GlStateManager.disableBlend();
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
