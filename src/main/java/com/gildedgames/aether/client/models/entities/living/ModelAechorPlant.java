package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelAechorPlant extends ModelBase
{
	private static int petals = 10, thorns = 4, stamens = 3;

	private ModelRenderer petal[], leaf[], thorn[], stamen[], stamen2[];

	private ModelRenderer stem, head;

	public float sinage, sinage2;

	public float size;

	public ModelAechorPlant()
	{
		this.size = 1.0F;

		this.petal = new ModelRenderer[petals];
		this.leaf = new ModelRenderer[petals];

		for (int i = 0; i < petals; i++)
		{
			this.petal[i] = new ModelRenderer(this, 0, 0);

			if (i % 2 == 0)
			{
				this.petal[i] = new ModelRenderer(this, 29, 3);
				this.petal[i].addBox(-4F, -1F, -12F, 8, 1, 9, 0.25F);
				this.petal[i].setRotationPoint(0.0F, 1.0F, 0.0F);
			}
			else
			{
				this.petal[i].addBox(-4F, -1F, -13F, 8, 1, 10, 0.125F);
				this.petal[i].setRotationPoint(0.0F, 1.0F, 0.0F);
			}

			this.leaf[i] = new ModelRenderer(this, 38, 13);
			this.leaf[i].addBox(-2F, -1F, -9.5F, 4, 1, 8, 0.15F);
			this.leaf[i].setRotationPoint(0.0F, 1.0F, 0.0F);
		}

		this.stamen = new ModelRenderer[stamens];
		this.stamen2 = new ModelRenderer[stamens];

		for (int i = 0; i < stamens; i++)
		{
			this.stamen[i] = new ModelRenderer(this, 36, 13);
			this.stamen[i].addBox(0F, -9F, -1.5F, 1, 6, 1, 0.25F);
			this.stamen[i].setRotationPoint(0.0F, 1.0F, 0.0F);
		}

		for (int i = 0; i < stamens; i++)
		{
			this.stamen2[i] = new ModelRenderer(this, 32, 15);
			this.stamen2[i].addBox(0F, -10F, -1.5F, 1, 1, 1, 0.125F);
			this.stamen2[i].setRotationPoint(0.0F, 1.0F, 0.0F);
		}

		this.head = new ModelRenderer(this, 0, 12);
		this.head.addBox(-3F, -3F, -3F, 6, 2, 6, 0.75F);
		this.head.setRotationPoint(0.0F, 1.0F, 0.0F);

		this.stem = new ModelRenderer(this, 24, 13);
		this.stem.addBox(-1F, 0F, -1F, 2, 6, 2, 0);
		this.stem.setRotationPoint(0.0F, 1.0F, 0.0F);

		this.thorn = new ModelRenderer[thorns];

		for (int i = 0; i < thorns; i++)
		{
			this.thorn[i] = new ModelRenderer(this, 32, 13);
			this.thorn[i].setRotationPoint(0.0F, 1.0F, 0.0F);
		}

		this.thorn[0].addBox(-1.75F, 1.25F, -1F, 1, 1, 1, 0.25F);
		this.thorn[1].addBox(-1F, 2.25F, 0.75F, 1, 1, 1, 0.25F);
		this.thorn[2].addBox(0.75F, 1.25F, 0F, 1, 1, 1, 0.25F);
		this.thorn[3].addBox(0F, 2.25F, -1.75F, 1, 1, 1, 0.25F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.setRotationAngles(f4, f5);

		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 1.2F, 0.0F);
		GL11.glScalef(this.size, this.size, this.size);

		for (int i = 0; i < petals; i++)
		{
			this.petal[i].render(f5);
			this.leaf[i].render(f5);
		}

		for (int i = 0; i < stamens; i++)
		{
			this.stamen[i].render(f5);
			this.stamen2[i].render(f5);
		}

		this.head.render(f5);
		this.stem.render(f5);

		for (int i = 0; i < thorns; i++)
		{
			this.thorn[i].render(f5);
		}

		GL11.glPopMatrix();
	}

	public void setRotationAngles(float f4, float f5)
	{
		this.head.rotateAngleX = 0.0F;
		this.head.rotateAngleY = f4 / 57.29578F;

		float boff = this.sinage2;

		this.stem.rotateAngleY = this.head.rotateAngleY;
		this.stem.rotationPointY = boff * 0.5F;
		for (int i = 0; i < thorns; i++)
		{
			this.thorn[i].rotateAngleY = this.head.rotateAngleY;
			this.thorn[i].rotationPointY = boff * 0.5F;
		}

		float pie = 3.141593F * 2F;

		for (int i = 0; i < petals; i++)
		{
			this.petal[i].rotateAngleX = ((i % 2 == 0) ? -0.25F : -0.4125F);
			this.petal[i].rotateAngleX += this.sinage;
			this.petal[i].rotateAngleY = this.head.rotateAngleY;
			this.petal[i].rotateAngleY += (pie / (float) petals) * (float) i;

			this.leaf[i].rotateAngleX = ((i % 2 == 0) ? 0.1F : 0.2F);
			this.leaf[i].rotateAngleX += this.sinage * 0.75F;
			this.leaf[i].rotateAngleY = this.head.rotateAngleY + (pie / (float) petals / 2F);
			this.leaf[i].rotateAngleY += (pie / (float) petals) * (float) i;

			this.petal[i].rotationPointY = boff;
			this.leaf[i].rotationPointY = boff;
		}

		for (int i = 0; i < stamens; i++)
		{
			this.stamen[i].rotateAngleX = 0.2F + ((float) i / 15F);
			this.stamen[i].rotateAngleY = this.head.rotateAngleY + 0.1F;
			this.stamen[i].rotateAngleY += (pie / (float) stamens) * (float) i;
			this.stamen[i].rotateAngleX += this.sinage * 0.4F;

			this.stamen2[i].rotateAngleX = 0.2F + ((float) i / 15F);
			this.stamen2[i].rotateAngleY = this.head.rotateAngleY + 0.1F;
			this.stamen2[i].rotateAngleY += (pie / (float) stamens) * (float) i;
			this.stamen2[i].rotateAngleX += this.sinage * 0.4F;

			this.stamen[i].rotationPointY = boff + ((this.sinage) * 2F);
			this.stamen2[i].rotationPointY = boff + ((this.sinage) * 2F);
		}

		this.head.rotationPointY = boff + ((this.sinage) * 2F);
	}
}
