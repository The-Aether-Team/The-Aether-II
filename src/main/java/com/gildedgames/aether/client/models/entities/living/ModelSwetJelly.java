package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.monsters.EntitySwet;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelSwetJelly extends EntityModel<EntitySwet>
{
	public final RendererModel Base;

	public final RendererModel Front;

	public final RendererModel Top;

	public final RendererModel Right;

	public final RendererModel Left;

	public final RendererModel Back;

	public final RendererModel Bottom;

	public ModelSwetJelly()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.Right = new RendererModel(this, 0, 47);
		this.Right.setRotationPoint(-10.0F, 0.0F, 0.0F);
		this.Right.addBox(-6.5F, -6.5F, 0.0F, 13, 13, 2, 0.0F);
		this.setRotateAngle(this.Right, 0.0F, 1.5707963267948966F, 0.0F);
		this.Front = new RendererModel(this, 0, 47);
		this.Front.setRotationPoint(0.0F, 0.0F, -10.0F);
		this.Front.addBox(-6.5F, -6.5F, 0.0F, 13, 13, 2, 0.0F);
		this.Bottom = new RendererModel(this, 0, 32);
		this.Bottom.setRotationPoint(0.0F, 8.0F, 0.0F);
		this.Bottom.addBox(-6.5F, 0.0F, -6.5F, 13, 2, 13, 0.0F);
		this.Base = new RendererModel(this, 0, 0);
		this.Base.setRotationPoint(0.0F, 14.0F, 0.0F);
		this.Base.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
		this.Back = new RendererModel(this, 0, 47);
		this.Back.setRotationPoint(0.0F, 0.0F, 10.0F);
		this.Back.addBox(-6.5F, -6.5F, 0.0F, 13, 13, 2, 0.0F);
		this.setRotateAngle(this.Back, 0.0F, 3.141592653589793F, 0.0F);
		this.Left = new RendererModel(this, 0, 47);
		this.Left.mirror = true;
		this.Left.setRotationPoint(10.0F, 0.0F, 0.0F);
		this.Left.addBox(-6.5F, -6.5F, 0.0F, 13, 13, 2, 0.0F);
		this.setRotateAngle(this.Left, 0.0F, -1.5707963267948966F, 0.0F);
		this.Top = new RendererModel(this, 0, 47);
		this.Top.mirror = true;
		this.Top.setRotationPoint(0.0F, -10.0F, 0.0F);
		this.Top.addBox(-6.5F, -6.5F, 0.0F, 13, 13, 2, 0.0F);
		this.setRotateAngle(this.Top, -1.5707963267948966F, 0.0F, 0.0F);
		this.Base.addChild(this.Right);
		this.Base.addChild(this.Front);
		this.Base.addChild(this.Back);
		this.Base.addChild(this.Left);
		this.Base.addChild(this.Top);
	}

	@Override
	public void render(final EntitySwet entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5)
	{
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.7F);

		final float wiggle = (entity.prevSquishFactor + (entity.squishFactor - entity.prevSquishFactor) * f5) / (f1 * 0.5F + 1.0F);

		this.Base.rotateAngleX = wiggle;
		this.Base.render(f5);

		GlStateManager.pushMatrix();

		float width = entity.getSquishPool();
		float height = width * 1.14f;

		GlStateManager.translatef(0, -height / 2 + 1.2f, 0);
		GlStateManager.scalef(width, height, width);

		this.Bottom.render(f5);

		GlStateManager.popMatrix();

		GlStateManager.disableBlend();
	}

	public void renderRaw(final EntitySwet entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5)
	{
		this.Base.render(f5);
		GlStateManager.translatef(0, .85f, 0);
		this.Bottom.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(final RendererModel modelRenderer, final float x, final float y, final float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
