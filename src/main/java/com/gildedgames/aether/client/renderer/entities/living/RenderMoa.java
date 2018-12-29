package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelMoa;
import com.gildedgames.aether.client.renderer.EyeUtil;
import com.gildedgames.aether.client.util.SpriteGeneric;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.entities.util.AnimalGender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderMoa extends RenderLiving<EntityMoa>
{

	private static final ResourceLocation AECHOR_PETAL_TEXTURE = AetherCore.getResource("textures/items/consumables/aechor_petal.png");

	private static final SpriteGeneric SPRITE = new SpriteGeneric("aechor_petal.png", 16, 16);

	private static final ResourceLocation BODY = AetherCore.getResource("textures/entities/moa/curved_main.png");

	private static final ResourceLocation BODY_HIGHLIGHT = AetherCore.getResource("textures/entities/moa/curved_highlight.png");

	private static final ResourceLocation BEAK = AetherCore.getResource("textures/entities/moa/keratin.png");

	private static final ResourceLocation EYES = AetherCore.getResource("textures/entities/moa/eyes.png");

	private static final ResourceLocation TONGUE = AetherCore.getResource("textures/entities/moa/tongue.png");

	private static final ResourceLocation SADDLE = AetherCore.getResource("textures/entities/moa/saddle.png");

	private static final ResourceLocation EYES_CLOSED = AetherCore.getResource("textures/entities/moa/eyes_closed.png");

	private static final ResourceLocation PUPIL_LEFT = AetherCore.getResource("textures/entities/moa/pupil_left.png");

	private static final ResourceLocation PUPIL_RIGHT = AetherCore.getResource("textures/entities/moa/pupil_right.png");

	public RenderMoa(RenderManager manager)
	{
		super(manager, new ModelMoa(), 0.5F);

		SPRITE.initSprite(16, 16, 0, 0, false);
	}

	private void scaleMoa(EntityMoa entityMoa)
	{
		float moaScale = entityMoa.isChild() ? 0.5f : 0.85f;
		moaScale += entityMoa.isGroupLeader() ? 0.15F : 0.0F;

		GL11.glScalef(moaScale, moaScale, moaScale);
	}

	@Override
	protected void preRenderCallback(EntityMoa entityliving, float partialTicks)
	{
		this.scaleMoa(entityliving);
	}

	private void renderMoa(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		ModelMoa model = (ModelMoa) this.mainModel;

		for (ModelRenderer renderer : model.boxList)
		{
			renderer.showModel = true;
		}

		EntityMoa moa = ((EntityMoa) entity);

		MoaGenePool genePool = moa.getGenePool();

		if (genePool == null || genePool.getFeathers() == null)
		{
			return;
		}

		Color base = genePool.getFeathers().gene().data();

		float[] hsb = new float[3];

		Color.RGBtoHSB(base.getRed(), base.getGreen(), base.getBlue(), hsb);
		Color highlight = Color.getHSBColor(hsb[0] + 13f / 255f, hsb[1] + 26f / 255f, hsb[2] + .001f);

		model.JawMain.showModel = false;
		model.HeadBeakMain.showModel = false;

		GL11.glColor3f(highlight.getRed() / 255f, highlight.getGreen() / 255f, highlight.getBlue() / 255f);

		this.renderManager.renderEngine.bindTexture(BODY);
		model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		EyeUtil.renderEyes(this.renderManager, model, model.HeadFront, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale,
				PUPIL_LEFT,
				PUPIL_RIGHT, EYES_CLOSED, false);

		GlStateManager.color(base.getRed() / 255f, base.getGreen() / 255f, base.getBlue() / 255f);

		this.renderManager.renderEngine.bindTexture(BODY_HIGHLIGHT);
		model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		model.JawMain.showModel = true;
		model.HeadBeakMain.showModel = true;

		double distance = Minecraft.getMinecraft().player.getDistance(entity);

		if (distance < 32.0D)
		{
			model.BodyBack.showModel = false;
			model.TailBase.showModel = false;
			model.LegL1.showModel = false;
			model.LegR1.showModel = false;
			model.ShoulderL.showModel = false;
			model.ShoulderR.showModel = false;
			model.HeadBack.showModel = false;
			model.HeadBeakMain.showModel = false;
			model.HeadBrow.showModel = false;
			model.HeadFeatherL1.showModel = false;
			model.HeadFeatherR1.showModel = false;
			model.HeadFeatherL2.showModel = false;
			model.HeadFeatherR2.showModel = false;
			model.HeadBeakIntL.showModel = false;
			model.HeadBeakIntR.showModel = false;

			GlStateManager.color(1.0f, 1.0f, 1.0f);

			this.renderManager.renderEngine.bindTexture(TONGUE);

			model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

			model.JawMain.showModel = false;

			Color eyesC = genePool.getEyes().gene().data();

			GlStateManager.color(eyesC.getRed() / 255f, eyesC.getGreen() / 255f, eyesC.getBlue() / 255f);

			this.renderManager.renderEngine.bindTexture(EYES);

			model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

			model.BodyBack.showModel = true;
			model.TailBase.showModel = true;
			model.LegL1.showModel = true;
			model.LegR1.showModel = true;
			model.ShoulderL.showModel = true;
			model.ShoulderR.showModel = true;
			model.HeadBack.showModel = true;
			model.HeadBeakMain.showModel = true;
			model.JawMain.showModel = true;
			model.HeadBrow.showModel = true;
			model.HeadFeatherL1.showModel = true;
			model.HeadFeatherR1.showModel = true;
			model.HeadFeatherL2.showModel = true;
			model.HeadFeatherR2.showModel = true;
			model.HeadBeakIntL.showModel = true;
			model.HeadBeakIntR.showModel = true;
		}

		model.BodyBack.showModel = false;
		model.TailBase.showModel = false;
		model.ShoulderL.showModel = false;
		model.ShoulderR.showModel = false;
		model.HeadBack.showModel = false;
		model.HeadFeatherL1.showModel = false;
		model.HeadFeatherR1.showModel = false;
		model.HeadFeatherL2.showModel = false;
		model.HeadFeatherR2.showModel = false;
		model.HeadBeakIntL.showModel = false;
		model.HeadBeakIntR.showModel = false;

		Color beakColor = genePool.getKeratin().gene().data();

		GlStateManager.color(beakColor.getRed() / 255f, beakColor.getGreen() / 255f, beakColor.getBlue() / 255f);

		this.renderManager.renderEngine.bindTexture(BEAK);
		model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		model.BodyBack.showModel = true;
		model.TailBase.showModel = true;
		model.ShoulderL.showModel = true;
		model.ShoulderR.showModel = true;
		model.HeadBack.showModel = true;
		model.HeadFeatherL1.showModel = true;
		model.HeadFeatherR1.showModel = true;
		model.HeadFeatherL2.showModel = true;
		model.HeadFeatherR2.showModel = true;
		model.HeadBeakIntL.showModel = true;
		model.HeadBeakIntR.showModel = true;

		if (moa.isSaddled())
		{
			GL11.glColor3f(1.0f, 1.0f, 1.0f);

			this.renderManager.renderEngine.bindTexture(SADDLE);
			model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

			GL11.glScalef(1.001F, 1.001F, 1.001F);
			GL11.glTranslatef(0.0F, 0.001F, 0.0F);
		}
	}

	@Override
	protected void renderModel(EntityMoa entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		boolean globalInvisible = !entity.isInvisible() || this.renderOutlines;
		boolean playerInvisible = !globalInvisible && !entity.isInvisibleToPlayer(Minecraft.getMinecraft().player);

		if (globalInvisible || playerInvisible)
		{
			this.renderMoa(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}

	@Override
	public void doRender(EntityMoa entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		if (entity.isHungry())
		{
			this.renderLivingLabel(entity, "Hungry", x, y + 0.15D + (entity.getGender() == AnimalGender.MALE ? 0.0D : -0.35D), z, 64);

			this.drawAechorPetal(x, y + entity.height + 0.8D + (entity.getGender() == AnimalGender.MALE ? 0.0D : -0.35D), z, 0.5D);
		}

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMoa entity)
	{
		return null;
	}

	private void drawAechorPetal(double x, double y, double z, double scale)
	{
		GL11.glPushMatrix();

		Tessellator tesselator = Tessellator.getInstance();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glScaled(scale, scale, scale);

		this.bindTexture(AECHOR_PETAL_TEXTURE);
		this.renderEntity(tesselator.getBuffer(), SPRITE);

		tesselator.draw();

		GL11.glPopMatrix();
	}

	private void renderEntity(BufferBuilder renderer, TextureAtlasSprite icon)
	{
		float f = icon.getMinU();
		float f1 = icon.getMaxU();
		float f2 = icon.getMinV();
		float f3 = icon.getMaxV();

		GL11.glRotatef(180f + Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, -1.0F, 0.0F);

		renderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);

		renderer.pos(-0.5D, -0.25D, 0.0D).tex((double) f, (double) f3).normal(0.0F, 1.0F, 0.0F).endVertex();
		renderer.pos(0.5D, -0.25D, 0.0D).tex((double) f1, (double) f3).normal(0.0F, 1.0F, 0.0F).endVertex();
		renderer.pos(0.5D, 0.75D, 0.0D).tex((double) f1, (double) f2).normal(0.0F, 1.0F, 0.0F).endVertex();
		renderer.pos(-0.5D, 0.75D, 0.0D).tex((double) f, (double) f2).normal(0.0F, 1.0F, 0.0F).endVertex();
	}

}
