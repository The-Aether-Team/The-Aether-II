package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.living.passive.EntityAerbunny;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelAerbunny extends ModelBase
{
	public ModelRenderer a;

	public ModelRenderer body, body2;

	public ModelRenderer puff;

	public ModelRenderer ear1, ear2;

	public ModelRenderer feet1, feet2;

	public ModelRenderer g, g2;

	public ModelRenderer hand1, hand2;

	public ModelAerbunny()
	{
		this.a = new ModelRenderer(this, 0, 0);
		this.a.addBox(-2.0F, -1.0F, -4.0F, 4, 4, 6, 0.0F);
		this.a.setRotationPoint(0.0F, 15, -4F);

		this.g = new ModelRenderer(this, 14, 0);
		this.g.addBox(-2.0F, -5.0F, -3.0F, 1, 4, 2, 0.0F);
		this.g.setRotationPoint(0.0F, 15, -4F);

		this.g2 = new ModelRenderer(this, 14, 0);
		this.g2.addBox(1.0F, -5.0F, -3.0F, 1, 4, 2, 0.0F);
		this.g2.setRotationPoint(0.0F, 15, -4F);

		this.hand1 = new ModelRenderer(this, 20, 0);
		this.hand1.addBox(-4.0F, 0.0F, -3.0F, 2, 3, 2, 0.0F);
		this.hand1.setRotationPoint(0.0F, 15, -4F);

		this.hand2 = new ModelRenderer(this, 20, 0);
		this.hand2.addBox(2.0F, 0.0F, -3.0F, 2, 3, 2, 0.0F);
		this.hand2.setRotationPoint(0.0F, 15, -4F);

		this.body = new ModelRenderer(this, 0, 10);
		this.body.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
		this.body.setRotationPoint(0.0F, 16, 0.0F);

		this.body2 = new ModelRenderer(this, 0, 24);
		this.body2.addBox(-2.0F, 4.0F, -2.0F, 4, 3, 4, 0.0F);
		this.body2.setRotationPoint(0.0F, 16, 0.0F);

		this.puff = new ModelRenderer(this, 29, 0);
		this.puff.addBox(-3.5F, -3.5F, -3.5F, 7, 7, 7, 0.0F);
		this.puff.setRotationPoint(0.0F, 0, 0.0F);

		this.ear1 = new ModelRenderer(this, 24, 16);
		this.ear1.addBox(-2.0F, 0.0F, -1.0F, 2, 2, 2);
		this.ear1.setRotationPoint(3F, 19, -3F);

		this.ear2 = new ModelRenderer(this, 24, 16);
		this.ear2.addBox(0.0F, 0.0F, -1.0F, 2, 2, 2);
		this.ear2.setRotationPoint(-3F, 19, -3F);

		this.feet1 = new ModelRenderer(this, 16, 24);
		this.feet1.addBox(-2.0F, 0.0F, -4.0F, 2, 2, 4);
		this.feet1.setRotationPoint(3F, 19, 4F);

		this.feet2 = new ModelRenderer(this, 16, 24);
		this.feet2.addBox(0.0F, 0.0F, -4.0F, 2, 2, 4);
		this.feet2.setRotationPoint(-3F, 19, 4F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale)
	{
		EntityAerbunny bunny = (EntityAerbunny) entity;

		this.setRotationAngles(f, f1, f2, f3, f4, scale);

		GlStateManager.translate(0F, 0.2F, 0F);

		this.a.render(scale);
		this.g.render(scale);
		this.g2.render(scale);
		this.hand1.render(scale);
		this.hand2.render(scale);
		this.body.render(scale);
		this.body2.render(scale);

		GlStateManager.pushMatrix();

		float puffScale = 1.0F + ((bunny.getPuffiness() / 10F) * 0.5F);

		GlStateManager.translate(0F, 1F, 0F);
		GlStateManager.scale(puffScale, puffScale, puffScale);

		this.puff.render(scale);

		GlStateManager.popMatrix();

		this.ear1.render(scale);
		this.ear2.render(scale);
		this.feet1.render(scale);
		this.feet2.render(scale);
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.a.rotateAngleX = -(f4 / 57.29578F);
		this.a.rotateAngleY = f3 / 57.29578F;
		this.g.rotateAngleX = this.a.rotateAngleX;
		this.g.rotateAngleY = this.a.rotateAngleY;
		this.g2.rotateAngleX = this.a.rotateAngleX;
		this.g2.rotateAngleY = this.a.rotateAngleY;
		this.hand1.rotateAngleX = this.a.rotateAngleX;
		this.hand1.rotateAngleY = this.a.rotateAngleY;
		this.hand2.rotateAngleX = this.a.rotateAngleX;
		this.hand2.rotateAngleY = this.a.rotateAngleY;
		this.body.rotateAngleX = 1.570796F;
		this.body2.rotateAngleX = 1.570796F;
		this.puff.rotateAngleX = 1.570796F;

		this.ear1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.0F * f1;
		this.feet1.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.2F * f1;
		this.ear2.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.0F * f1;
		this.feet2.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.2F * f1;
	}
}
