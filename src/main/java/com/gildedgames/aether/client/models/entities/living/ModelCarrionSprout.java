package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelCarrionSprout extends ModelBase
{

	ModelRenderer TopStem;

	ModelRenderer BottomStem;

	ModelRenderer HeadRoof;

	ModelRenderer Teeth;

	ModelRenderer Jaw;

	ModelRenderer Head;

	public ModelCarrionSprout()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;

		/*Pedal4 = newsystem ModelRenderer(this, 40, 58);
		Pedal4.addBox(0F, 0F, 0F, 9, 0, 6);
		Pedal4.setRotationPoint(1F, 24F, -3F);
		Pedal4.setTextureSize(64, 64);
		Pedal4.mirror = true;
		setRotation(Pedal4, 0F, 0F, -0.1396263F);
		Pedal3 = newsystem ModelRenderer(this, 0, 54);
		Pedal3.addBox(0F, 0F, 0F, 6, 0, 10);
		Pedal3.setRotationPoint(-3F, 24F, 1F);
		Pedal3.setTextureSize(64, 64);
		Pedal3.mirror = true;
		setRotation(Pedal3, 0.1396263F, 0F, 0F);
		Pedal2 = newsystem ModelRenderer(this, 0, 48);
		Pedal2.addBox(0F, 0F, 0F, 10, 0, 6);
		Pedal2.setRotationPoint(-11F, 22.5F, -3F);
		Pedal2.setTextureSize(64, 64);
		Pedal2.mirror = true;
		setRotation(Pedal2, 0F, 0F, 0.1396263F);
		Pedal1 = newsystem ModelRenderer(this, 43, 49);
		Pedal1.addBox(0F, 0F, 0F, 6, 0, 9);
		Pedal1.setRotationPoint(-3F, 0F, -10F);
		Pedal1.setTextureSize(64, 64);
		Pedal1.mirror = true;
		setRotation(Pedal1, -0.1396263F, 0F, 0F);*/

		this.petal = new ModelRenderer[petals];

		for (int i = 0; i < petals; i++)
		{
			this.petal[i] = new ModelRenderer(this, 43, 49);
			if (i % 2 == 0)
			{
				this.petal[i] = new ModelRenderer(this, 43, 49);
				this.petal[i].addBox(-2.8F, -1F, -10.8F, 6, 0, 9);
				this.petal[i].setRotationPoint(0.0F, 1.0F, 0.0F);
			}
			else
			{
				this.petal[i].addBox(-2.8F, -1F, -11.8F, 6, 0, 9);
				this.petal[i].setRotationPoint(0.0F, 1.0F, 0.0F);
			}
		}

		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.addBox(-3F, -3F, -3F, 6, 2, 6);
		this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);

		this.TopStem = new ModelRenderer(this, 8, 25);
		this.TopStem.addBox(0F, 0F, 0F, 2, 6, 2);
		this.TopStem.setRotationPoint(-1F, 14F, -3F);
		this.TopStem.setTextureSize(64, 64);
		this.TopStem.mirror = true;
		this.setRotation(this.TopStem, 0.3490659F, 0F, 0F);
		this.BottomStem = new ModelRenderer(this, 0, 25);
		this.BottomStem.addBox(0F, 0F, 0F, 2, 5, 2);
		this.BottomStem.setRotationPoint(-1F, 19F, -1F);
		this.BottomStem.setTextureSize(64, 64);
		this.BottomStem.mirror = true;
		this.setRotation(this.BottomStem, 0F, 0F, 0F);
		this.HeadRoof = new ModelRenderer(this, 20, 16);
		this.HeadRoof.addBox(0F, 0F, 0F, 11, 5, 11);
		this.HeadRoof.setRotationPoint(-5.5F, 4F, -7.5F);
		this.HeadRoof.setTextureSize(64, 64);
		this.HeadRoof.mirror = true;
		this.setRotation(this.HeadRoof, -0.3490659F, 0F, 0F);
		this.Teeth = new ModelRenderer(this, 0, 33);
		this.Teeth.addBox(0F, 0F, 0F, 9, 1, 9);
		this.Teeth.setRotationPoint(-4.5F, 8.5F, -8.5F);
		this.Teeth.setTextureSize(64, 64);
		this.Teeth.mirror = true;
		this.setRotation(this.Teeth, -0.3490659F, 0F, 0F);
		this.Jaw = new ModelRenderer(this, 24, 1);
		this.Jaw.addBox(0F, 0F, -9F, 10, 2, 10);
		this.Jaw.setRotationPoint(-5F, 12F, 0F);
		this.Jaw.setTextureSize(64, 64);
		this.Jaw.mirror = true;
		this.setRotation(this.Jaw, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		/*Pedal4.render(f5);
		Pedal3.render(f5);
		Pedal2.render(f5);
		Pedal1.render(f5);*/

		for (int i = 0; i < petals; i++)
		{
			this.petal[i].render(f5);
		}

		this.TopStem.render(f5);
		this.BottomStem.render(f5);
		this.HeadRoof.render(f5);
		this.Teeth.render(f5);
		this.Jaw.render(f5);
		this.Head.render(f5);

		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.3F, 0.0F);

		this.BottomStem.render(f5);

		GL11.glPopMatrix();
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
		float yOffset = 21.0F;

		for (int i = 0; i < petals; i++)
		{
			this.petal[i].rotateAngleX = ((i % 2 == 0) ? -0.25F : -0.4125F);
			this.petal[i].rotateAngleX += this.sinage;
			this.petal[i].rotateAngleY = 17.0F;
			this.petal[i].rotateAngleY += (this.pie / (float) petals) * (float) i;

			this.petal[i].rotationPointY = boff + yOffset;
		}

		this.Jaw.rotateAngleX = 0.08F;
		this.Jaw.rotateAngleX += this.sinage;

		this.Head.rotationPointY = boff + yOffset + ((this.sinage) * 2F);
		this.Jaw.rotationPointY = boff + 8F + ((this.sinage) * 2F);
		this.BottomStem.rotationPointY = boff + 15F + ((this.sinage) * 2F);
		this.TopStem.rotationPointY = boff + 10F + ((this.sinage) * 2F);
		this.HeadRoof.rotationPointY = boff + ((this.sinage) * 2F);
		this.Teeth.rotationPointY = boff + 4.5F + ((this.sinage) * 2F);
	}

	private ModelRenderer petal[];

	private static int petals = 8;

	public float sinage;

	public float sinage2;

	private float pie = 3.141593F * 2F;
}
