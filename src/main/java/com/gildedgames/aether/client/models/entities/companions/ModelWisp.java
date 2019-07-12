package com.gildedgames.aether.client.models.entities.companions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelWisp<T extends Entity> extends EntityModel<T>
{
	private final RendererModel Pillar_Top_Left;

	private final RendererModel Pillar_Bottom_Left;

	private final RendererModel Pillar_Top_Right;

	private final RendererModel Pillar_Bottom_Right;

	private final RendererModel Head;

	private final RendererModel Base;

	public ModelWisp()
	{
		this.textureWidth = 32;
		this.textureHeight = 32;

		this.Pillar_Top_Left = new RendererModel(this, 0, 0);
		this.Pillar_Top_Left.addBox(-7F, -5.5F, -1F, 2, 8, 2);
		this.Pillar_Top_Left.setRotationPoint(0F, 0F, 0F);
		this.Pillar_Top_Left.setTextureSize(32, 32);
		this.Pillar_Top_Left.mirror = true;
		this.setRotation(this.Pillar_Top_Left, 0F, 0F, 0.3490659F);

		this.Pillar_Bottom_Left = new RendererModel(this, 8, 0);
		this.Pillar_Bottom_Left.addBox(-7F, -2.5F, -1F, 2, 8, 2);
		this.Pillar_Bottom_Left.setRotationPoint(0F, 0F, 0F);
		this.Pillar_Bottom_Left.setTextureSize(32, 32);
		this.Pillar_Bottom_Left.mirror = true;
		this.setRotation(this.Pillar_Bottom_Left, 0F, 0F, -0.3490659F);

		this.Pillar_Top_Right = new RendererModel(this, 0, 0);
		this.Pillar_Top_Right.addBox(5F, -5.5F, -1F, 2, 8, 2);
		this.Pillar_Top_Right.setRotationPoint(0F, 0F, 0F);
		this.Pillar_Top_Right.setTextureSize(32, 32);
		this.Pillar_Top_Right.mirror = true;
		this.setRotation(this.Pillar_Top_Right, 0F, 0F, -0.3490659F);

		this.Pillar_Bottom_Right = new RendererModel(this, 8, 0);
		this.Pillar_Bottom_Right.addBox(5F, -2.5F, -1F, 2, 8, 2);
		this.Pillar_Bottom_Right.setRotationPoint(0F, 0F, 0F);
		this.Pillar_Bottom_Right.setTextureSize(32, 32);
		this.Pillar_Bottom_Right.mirror = true;
		this.setRotation(this.Pillar_Bottom_Right, 0F, 0F, 0.3490659F);

		this.Head = new RendererModel(this, 0, 10);
		this.Head.addBox(-1.5F, -1.5F, -1F, 3, 3, 4);
		this.Head.setRotationPoint(0F, 0F, 0F);
		this.Head.setTextureSize(32, 32);
		this.Head.mirror = true;
		this.setRotation(this.Head, 0F, 0F, 0F);

		this.Base = new RendererModel(this, 16, 0);
		this.Base.addBox(-1F, 2F, 0F, 2, 3, 1);
		this.Base.setRotationPoint(0F, 0F, 0F);
		this.Base.setTextureSize(32, 32);
		this.Base.mirror = true;
		this.setRotation(this.Base, 0F, 0F, 0F);
	}

	@Override
	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
			float scale)
	{
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		float partialTicks = Minecraft.getInstance().getRenderPartialTicks();

		GL11.glPushMatrix();

		GL11.glTranslatef(0, MathHelper.sin(entity.ticksExisted + partialTicks) / 50, 0);

		this.Head.render(scale);
		this.Base.render(scale);

		GL11.glRotatef((entity.ticksExisted + partialTicks) * 20, 0, 1, 0);

		this.Pillar_Top_Left.render(scale);
		this.Pillar_Bottom_Left.render(scale);
		this.Pillar_Top_Right.render(scale);
		this.Pillar_Bottom_Right.render(scale);

		GL11.glPopMatrix();
	}

	private void setRotation(RendererModel model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
