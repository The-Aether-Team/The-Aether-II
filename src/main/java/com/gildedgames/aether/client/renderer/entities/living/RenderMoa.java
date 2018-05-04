package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelMoa;
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
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderMoa extends RenderLiving<EntityMoa>
{

	public static final ResourceLocation AECHOR_PETAL_TEXTURE = new ResourceLocation("aether", "textures/items/consumables/aechor_petal.png");

	public static final SpriteGeneric SPRITE = new SpriteGeneric("aechor_petal.png", 16, 16);

	public static ResourceLocation BODY = AetherCore.getResource("textures/entities/moa/curved_main.png");

	public static ResourceLocation BODY_HIGHLIGHT = AetherCore.getResource("textures/entities/moa/curved_highlight.png");

	public static ResourceLocation BEAK = AetherCore.getResource("textures/entities/moa/beaklegs.png");

	public static ResourceLocation EYES = AetherCore.getResource("textures/entities/moa/eyes.png");

	public static ResourceLocation TONGUE = AetherCore.getResource("textures/entities/moa/tongue.png");

	public static ResourceLocation SADDLE = AetherCore.getResource("textures/entities/moa/saddle.png");

	public RenderMoa(final RenderManager manager)
	{
		super(manager, new ModelMoa(), 0.5F);

		SPRITE.initSprite(16, 16, 0, 0, false);
	}

	protected float getWingRotation(final EntityMoa moa, final float f)
	{
		final float f1 = moa.prevWingRotation + (moa.wingRotation - moa.prevWingRotation) * f;
		final float f2 = moa.prevDestPos + (moa.destPos - moa.prevDestPos) * f;

		return (MathHelper.sin(f1) + 1.0F) * f2;
	}

	@Override
	protected float handleRotationFloat(final EntityMoa entityliving, final float f)
	{
		return this.getWingRotation(entityliving, f);
	}

	protected void scaleMoa(final EntityMoa entityMoa)
	{
		float moaScale = entityMoa.isChild() ? 0.5f : 0.85f;

		moaScale += entityMoa.isGroupLeader() ? 0.15F : 0.0F;

		GL11.glScalef(moaScale, moaScale, moaScale);
	}

	@Override
	protected void preRenderCallback(final EntityMoa entityliving, final float f)
	{
		this.scaleMoa(entityliving);
	}

	public void renderMoa(final int color, final ResourceLocation texture, final EntityLivingBase entity, final float par7, final ModelRenderer... models)
	{
		final float red = ((color >> 16) & 0xff) / 255F;
		final float green = ((color >> 8) & 0xff) / 255F;
		final float blue = (color & 0xff) / 255F;

		GL11.glColor3f(red, green, blue);

		this.renderManager.renderEngine.bindTexture(texture);

		for (final ModelRenderer model : models)
		{
			model.render(par7);
		}
	}

	public void renderMoa(final int color, final ResourceLocation texture, final EntityLivingBase entity, final float par2, final float par3, final float par4,
			final float par5,
			final float par6, final float par7)
	{
		final ModelMoa model = ((ModelMoa) this.mainModel);

		final float red = ((color >> 16) & 0xff) / 255F;
		final float green = ((color >> 8) & 0xff) / 255F;
		final float blue = (color & 0xff) / 255F;
		GL11.glColor3f(red, green, blue);

		this.renderManager.renderEngine.bindTexture(texture);

//		model.LeftLeg2.isHidden = true;
//		model.LeftLeg2.isHidden = true;
//		model.RightLeg1.isHidden = true;
//		model.RightLeg2.isHidden = true;
		this.mainModel.render(entity, par2, par3, par4, par5, par6, par7);
//		model.LeftLeg2.isHidden = false;
//		model.LeftLeg2.isHidden = false;
//		model.RightLeg1.isHidden = false;
//		model.RightLeg2.isHidden = false;
	}

	public Color darker(final Color c, final float factor)
	{
		return new Color(Math.max((int) (c.getRed() * factor), 0), Math.max((int) (c.getGreen() * factor), 0), Math.max((int) (c.getBlue()
				* factor), 0), c.getAlpha());
	}

	private Color blend(final Color c1, final Color c2, float ratio)
	{
		if (ratio > 1f)
		{
			ratio = 1f;
		}
		else if (ratio < 0f)
		{
			ratio = 0f;
		}
		final float iRatio = 1.0f - ratio;

		final int i1 = c1.getRGB();
		final int i2 = c2.getRGB();

		final int a1 = (i1 >> 24 & 0xff);
		final int r1 = ((i1 & 0xff0000) >> 16);
		final int g1 = ((i1 & 0xff00) >> 8);
		final int b1 = (i1 & 0xff);

		final int a2 = (i2 >> 24 & 0xff);
		final int r2 = ((i2 & 0xff0000) >> 16);
		final int g2 = ((i2 & 0xff00) >> 8);
		final int b2 = (i2 & 0xff);

		final int a = (int) ((a1 * iRatio) + (a2 * ratio));
		final int r = (int) ((r1 * iRatio) + (r2 * ratio));
		final int g = (int) ((g1 * iRatio) + (g2 * ratio));
		final int b = (int) ((b1 * iRatio) + (b2 * ratio));

		return new Color(a << 24 | r << 16 | g << 8 | b);
	}

	protected void renderMoa(
			final EntityLivingBase entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7)
	{
		final EntityMoa moa = ((EntityMoa) entity);
		final MoaGenePool genePool = moa.getGenePool();
		final ModelMoa model = ((ModelMoa) this.mainModel);

		if (genePool == null || genePool.getFeathers() == null)
		{
			return;
		}

		final Color base = genePool.getFeathers().gene().data();

		float[] hsb = new float[3];

		Color.RGBtoHSB(base.getRed(), base.getGreen(), base.getBlue(), hsb);
		Color highlight = Color.getHSBColor(hsb[0] + 13f / 255f, hsb[1] + 26f / 255f, hsb[2] + .001f);

		GL11.glColor3f(highlight.getRed() / 255f, highlight.getGreen()/ 255f, highlight.getBlue() / 255f);
		this.renderManager.renderEngine.bindTexture(BODY);
		model.render(entity, par2, par3, par4, par5, par6, par7);


		GlStateManager.color(base.getRed() / 255f, base.getGreen()/ 255f, base.getBlue() / 255f);

		this.renderManager.renderEngine.bindTexture(BODY_HIGHLIGHT);
		model.render(entity, par2, par3, par4, par5, par6, par7);

		GlStateManager.color(1.0f, 1.0f, 1.0f);

		this.renderManager.renderEngine.bindTexture(TONGUE);
		model.render(entity, par2, par3, par4, par5, par6, par7);


		final Color eyesC = genePool.getEyes().gene().data();

		GlStateManager.color(eyesC.getRed() / 255f, eyesC.getGreen()/ 255f, eyesC.getBlue() / 255f);

		this.renderManager.renderEngine.bindTexture(EYES);
		model.render(entity, par2, par3, par4, par5, par6, par7);


		final Color beakColor = genePool.getKeratin().gene().data();

		GlStateManager.color(beakColor.getRed() / 255f, beakColor.getGreen()/ 255f, beakColor.getBlue() / 255f);

		this.renderManager.renderEngine.bindTexture(BEAK);
		model.render(entity, par2, par3, par4, par5, par6, par7);

		if (true)
			return;


		GL11.glPushMatrix();

		GL11.glDepthMask(true);

		this.renderMoa(genePool.getFeathers().gene().data().getRGB(), BODY, entity, par2, par3, par4, par5, par6, par7);

//		this.renderMoa(genePool.getKeratin().gene().data().getRGB(), LEGS, entity, par7, model.LeftLeg1, model.RightLeg1, model.LeftLeg2, model.RightLeg2,
//				model.FootLeft, model.FootRight, model.Toe1Left, model.Toe1Right, model.Toe2Left, model.Toe2Right);

//		this.renderMoa(genePool.getKeratin().gene().data().getRGB(), BEAK, entity, par7, model.Jaw, model.Head);
//		this.renderMoa(genePool.getEyes().gene().data().getRGB(), EYES, entity, par7, model.Head);
//
		GL11.glScalef(1.001f, 1.001f, 1.001f);
		GL11.glTranslatef(0.0f, -0.001f, 0.001f);

//		this.renderMoa(patternColor.getRGB(), genePool.getMarks().gene().getHead(), entity, par7, model.Head);

		GL11.glTranslatef(0.0f, 0.0f, -0.001f);

//		this.renderMoa(patternColor.getRGB(), genePool.getMarks().gene().getBack(), entity, par7, model.Body, model.Chest);
//		this.renderMoa(patternColor.getRGB(), genePool.getMarks().gene().getWing(), entity, par7, model.LeftWing, model.RightWing);

		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		this.renderManager.renderEngine.bindTexture(TONGUE);
//		model.Jaw.render(par7);

		GL11.glColor3f(1.0f, 1.0f, 1.0f);
//		this.renderManager.renderEngine.bindTexture(TEETH);
//		model.Teeth.render(par7);

		GL11.glColor3f(1.0f, 1.0f, 1.0f);
//		this.renderManager.renderEngine.bindTexture(LEGS);
//		model.Claw1Left.render(par7);
//		model.Claw2Left.render(par7);
//		model.Claw3Left.render(par7);
//		model.Claw1Right.render(par7);
//		model.Claw2Right.render(par7);
//		model.Claw3Right.render(par7);

		if (moa.isSaddled())
		{
			GL11.glColor3f(1.0f, 1.0f, 1.0f);

			this.renderManager.renderEngine.bindTexture(SADDLE);

			GL11.glScalef(1.0001F, 1.0001F, 1.0001F);
			GL11.glTranslatef(0.0F, -0.001F, 0.0F);

//			model.Body.render(par7);
//			model.Chest.render(par7);
		}

		if (moa.getGender() == AnimalGender.MALE)
		{
//			this.renderMoa(genePool.getFeathers().gene().data().getRGB(), FEATHERS, entity, par7, model.feather1, model.feather2, model.feather3,
//					model.feather4);
		}

		GL11.glColor3f(1.0f, 1.0f, 1.0f);

		//GL11.glTranslatef(0.0f, -0.1f, 0.0f);

		//this.renderMoa(patternColor.getRGB(), genePool.getMarks().gene().getTail(), entity, par7, model.TailLeft, model.TailRight);

		GL11.glPopMatrix();
	}

	@Override
	protected void renderModel(final EntityMoa entity, final float par2, final float par3, final float par4, final float par5, final float par6,
			final float par7)
	{
		final boolean flag = !entity.isInvisible() || this.renderOutlines;
		final boolean flag1 = !flag && !entity.isInvisibleToPlayer(Minecraft.getMinecraft().player);

		if (flag || flag1)
		{
			this.renderMoa(entity, par2, par3, par4, par5, par6, par7);
		}
	}

	@Override
	public void doRender(final EntityMoa entity, final double d, final double d1, final double d2, final float f, final float f1)
	{
		if (entity.isHungry())
		{
			this.renderLivingLabel(entity, "Hungry", d, d1 + 0.15D + (entity.getGender() == AnimalGender.MALE ? 0.0D : -0.35D), d2, 64);
			this.drawAechorPetal(d, d1 + entity.height + 0.8D + (entity.getGender() == AnimalGender.MALE ? 0.0D : -0.35D), d2, 0.5D);
		}

		super.doRender(entity, d, d1, d2, f, f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityMoa entity)
	{
		return null;
	}

	public void drawAechorPetal(final double x, final double y, final double z, final double scale)
	{
		GL11.glPushMatrix();

		final Tessellator tesselator = Tessellator.getInstance();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glScaled(scale, scale, scale);

		this.bindTexture(AECHOR_PETAL_TEXTURE);
		this.renderEntity(tesselator.getBuffer(), SPRITE);

		tesselator.draw();

		GL11.glPopMatrix();
	}

	private void renderEntity(final BufferBuilder renderer, final TextureAtlasSprite icon)
	{
		final float f = icon.getMinU();
		final float f1 = icon.getMaxU();
		final float f2 = icon.getMinV();
		final float f3 = icon.getMaxV();

		GL11.glRotatef(180f + Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, -1.0F, 0.0F);

		renderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);

		renderer.pos(-0.5D, -0.25D, 0.0D).tex((double) f, (double) f3).normal(0.0F, 1.0F, 0.0F).endVertex();
		renderer.pos(0.5D, -0.25D, 0.0D).tex((double) f1, (double) f3).normal(0.0F, 1.0F, 0.0F).endVertex();
		renderer.pos(0.5D, 0.75D, 0.0D).tex((double) f1, (double) f2).normal(0.0F, 1.0F, 0.0F).endVertex();
		renderer.pos(-0.5D, 0.75D, 0.0D).tex((double) f, (double) f2).normal(0.0F, 1.0F, 0.0F).endVertex();
	}

}
