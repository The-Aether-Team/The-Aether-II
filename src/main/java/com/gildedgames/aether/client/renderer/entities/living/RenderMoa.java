package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelMoa;
import com.gildedgames.aether.client.util.SpriteGeneric;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.entities.util.AnimalGender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.Color;

public class RenderMoa extends RenderLiving<EntityMoa>
{

	public static ResourceLocation FEATHERS = AetherCore.getResource("textures/entities/moa/feathers.png");

	public static ResourceLocation BODY = AetherCore.getResource("textures/entities/moa/body.png");

	public static ResourceLocation LEGS = AetherCore.getResource("textures/entities/moa/legs.png");

	public static ResourceLocation BEAK = AetherCore.getResource("textures/entities/moa/beak.png");

	public static ResourceLocation EYES = AetherCore.getResource("textures/entities/moa/eyes.png");

	public static ResourceLocation TEETH = AetherCore.getResource("textures/entities/moa/teeth.png");

	public static ResourceLocation TONGUE = AetherCore.getResource("textures/entities/moa/tongue.png");

	public static ResourceLocation SADDLE = AetherCore.getResource("textures/entities/moa/saddle.png");

	public static final ResourceLocation AECHOR_PETAL_TEXTURE = new ResourceLocation("aether", "textures/items/consumables/aechor_petal.png");

	public static final SpriteGeneric SPRITE = new SpriteGeneric("aechor_petal.png", 16, 16);

	public RenderMoa(RenderManager manager)
	{
		super(manager, new ModelMoa(), 1.0F);

		SPRITE.initSprite(16, 16, 0, 0, false);
	}

	protected float getWingRotation(EntityMoa moa, float f)
	{
		float f1 = moa.prevWingRotation + (moa.wingRotation - moa.prevWingRotation) * f;
		float f2 = moa.prevDestPos + (moa.destPos - moa.prevDestPos) * f;

		return (MathHelper.sin(f1) + 1.0F) * f2;
	}

	@Override
	protected float handleRotationFloat(EntityMoa entityliving, float f)
	{
		return this.getWingRotation(entityliving, f);
	}

	protected void scaleMoa(EntityMoa entityMoa)
	{
		float moaScale = entityMoa.isChild() ? 0.5f : 0.85f;

		moaScale += entityMoa.isGroupLeader() ? 0.15F : 0.0F;

		GL11.glScalef(moaScale, moaScale, moaScale);
	}

	@Override
	protected void preRenderCallback(EntityMoa entityliving, float f)
	{
		this.scaleMoa(entityliving);
	}

	public void renderMoa(int color, ResourceLocation texture, EntityLivingBase entity, float par7, ModelRenderer... models)
	{
		float red = ((color >> 16) & 0xff) / 255F;
		float green = ((color >> 8) & 0xff) / 255F;
		float blue = (color & 0xff) / 255F;

		GL11.glColor3f(red, green, blue);

		this.renderManager.renderEngine.bindTexture(texture);

		for (ModelRenderer model : models)
		{
			model.render(par7);
		}
	}

	public void renderMoa(int color, ResourceLocation texture, EntityLivingBase entity, float par2, float par3, float par4, float par5,
			float par6, float par7)
	{
		ModelMoa model = ((ModelMoa) this.mainModel);

		float red = ((color >> 16) & 0xff) / 255F;
		float green = ((color >> 8) & 0xff) / 255F;
		float blue = (color & 0xff) / 255F;
		GL11.glColor3f(red, green, blue);

		this.renderManager.renderEngine.bindTexture(texture);

		model.LeftLeg2.isHidden = true;
		model.LeftLeg2.isHidden = true;
		model.RightLeg1.isHidden = true;
		model.RightLeg2.isHidden = true;
		this.mainModel.render(entity, par2, par3, par4, par5, par6, par7);
		model.LeftLeg2.isHidden = false;
		model.LeftLeg2.isHidden = false;
		model.RightLeg1.isHidden = false;
		model.RightLeg2.isHidden = false;
	}

	public Color darker(Color c, float factor)
	{
		return new Color(Math.max((int) (c.getRed() * factor), 0), Math.max((int) (c.getGreen() * factor), 0), Math.max((int) (c.getBlue()
				* factor), 0), c.getAlpha());
	}

	private Color blend(Color c1, Color c2, float ratio)
	{
		if (ratio > 1f)
		{
			ratio = 1f;
		}
		else if (ratio < 0f)
		{
			ratio = 0f;
		}
		float iRatio = 1.0f - ratio;

		int i1 = c1.getRGB();
		int i2 = c2.getRGB();

		int a1 = (i1 >> 24 & 0xff);
		int r1 = ((i1 & 0xff0000) >> 16);
		int g1 = ((i1 & 0xff00) >> 8);
		int b1 = (i1 & 0xff);

		int a2 = (i2 >> 24 & 0xff);
		int r2 = ((i2 & 0xff0000) >> 16);
		int g2 = ((i2 & 0xff00) >> 8);
		int b2 = (i2 & 0xff);

		int a = (int) ((a1 * iRatio) + (a2 * ratio));
		int r = (int) ((r1 * iRatio) + (r2 * ratio));
		int g = (int) ((g1 * iRatio) + (g2 * ratio));
		int b = (int) ((b1 * iRatio) + (b2 * ratio));

		return new Color(a << 24 | r << 16 | g << 8 | b);
	}

	protected void renderMoa(EntityLivingBase entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		EntityMoa moa = ((EntityMoa) entity);
		MoaGenePool genePool = moa.getGenePool();
		ModelMoa model = ((ModelMoa) this.mainModel);

		if (genePool == null || genePool.getFeathers() == null)
		{
			return;
		}

		GL11.glPushMatrix();

		GL11.glDepthMask(true);

		this.renderMoa(genePool.getFeathers().gene().data().getRGB(), BODY, entity, par2, par3, par4, par5, par6, par7);

		this.renderMoa(genePool.getKeratin().gene().data().getRGB(), LEGS, entity, par7, model.LeftLeg1, model.RightLeg1, model.LeftLeg2, model.RightLeg2, model.FootLeft, model.FootRight, model.Toe1Left, model.Toe1Right, model.Toe2Left, model.Toe2Right);

		this.renderMoa(genePool.getKeratin().gene().data().getRGB(), BEAK, entity, par7, model.Jaw, model.Head);
		this.renderMoa(genePool.getEyes().gene().data().getRGB(), EYES, entity, par7, model.Head);

		GL11.glScalef(1.001f, 1.001f, 1.001f);
		GL11.glTranslatef(0.0f, -0.001f, 0.001f);

		Color patternColor = genePool.getFeathers().gene().data().darker();

		this.renderMoa(patternColor.getRGB(), genePool.getMarks().gene().getHead(), entity, par7, model.Head);

		GL11.glTranslatef(0.0f, 0.0f, -0.001f);

		this.renderMoa(patternColor.getRGB(), genePool.getMarks().gene().getBack(), entity, par7, model.Body, model.Chest);
		this.renderMoa(patternColor.getRGB(), genePool.getMarks().gene().getWing(), entity, par7, model.LeftWing, model.RightWing);

		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		this.renderManager.renderEngine.bindTexture(TONGUE);
		model.Jaw.render(par7);

		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		this.renderManager.renderEngine.bindTexture(TEETH);
		model.Teeth.render(par7);

		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		this.renderManager.renderEngine.bindTexture(LEGS);
		model.Claw1Left.render(par7);
		model.Claw2Left.render(par7);
		model.Claw3Left.render(par7);
		model.Claw1Right.render(par7);
		model.Claw2Right.render(par7);
		model.Claw3Right.render(par7);

		if (moa.isSaddled())
		{
			GL11.glColor3f(1.0f, 1.0f, 1.0f);

			this.renderManager.renderEngine.bindTexture(SADDLE);

			GL11.glScalef(1.0001F, 1.0001F, 1.0001F);
			GL11.glTranslatef(0.0F, -0.001F, 0.0F);

			model.Body.render(par7);
			model.Chest.render(par7);
		}

		if (moa.getGender() == AnimalGender.MALE)
		{
			this.renderMoa(genePool.getFeathers().gene().data().getRGB(), FEATHERS, entity, par7, model.feather1, model.feather2, model.feather3, model.feather4);
		}

		GL11.glColor3f(1.0f, 1.0f, 1.0f);

		//GL11.glTranslatef(0.0f, -0.1f, 0.0f);

		//this.renderMoa(patternColor.getRGB(), genePool.getMarks().gene().getTail(), entity, par7, model.TailLeft, model.TailRight);

		GL11.glPopMatrix();
	}

	@Override
	protected void renderModel(EntityMoa entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.renderMoa(entity, par2, par3, par4, par5, par6, par7);
	}

	@Override
	public void doRender(EntityMoa entity, double d, double d1, double d2, float f, float f1)
	{
		if (entity.isHungry())
		{
			this.renderLivingLabel(entity, "Hungry", d, d1 + 0.15D + (entity.getGender() == AnimalGender.MALE ? 0.0D : -0.35D), d2, 64);
			this.drawAechorPetal(d, d1 + entity.height + 0.8D + (entity.getGender() == AnimalGender.MALE ? 0.0D : -0.35D), d2, 0.5D);
		}

		super.doRender(entity, d, d1, d2, f, f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMoa entity)
	{
		return null;
	}

	public void drawAechorPetal(double x, double y, double z, double scale)
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

	private void renderEntity(VertexBuffer renderer, TextureAtlasSprite icon)
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
