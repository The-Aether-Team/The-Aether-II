package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelMoaBase;
import com.gildedgames.aether.client.models.entities.living.ModelMoaLodHigh;
import com.gildedgames.aether.client.models.entities.living.ModelMoaLodLow;
import com.gildedgames.aether.client.renderer.EyeUtil;
import com.gildedgames.aether.client.util.SpriteGeneric;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityMoa;
import com.gildedgames.aether.common.entities.genes.AnimalGender;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.items.other.ItemMoaEgg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderMoa extends RenderLivingLOD<EntityMoa>
{

	private static final ResourceLocation AECHOR_PETAL_TEXTURE = AetherCore.getResource("textures/items/consumables/aechor_petal.png");

	private static final SpriteGeneric SPRITE = new SpriteGeneric("aechor_petal.png", 16, 16);

	private static final ResourceLocation CURVED_BODY = AetherCore.getResource("textures/entities/moa/curved_main.png");

	private static final ResourceLocation CURVED_BODY_HIGHLIGHT = AetherCore.getResource("textures/entities/moa/curved_highlight.png");

	private static final ResourceLocation POINTED_BODY = AetherCore.getResource("textures/entities/moa/pointed_main.png");

	private static final ResourceLocation POINTED_BODY_HIGHLIGHT = AetherCore.getResource("textures/entities/moa/pointed_highlight.png");

	private static final ResourceLocation BEAK = AetherCore.getResource("textures/entities/moa/keratin.png");

	private static final ResourceLocation EYES = AetherCore.getResource("textures/entities/moa/eyes.png");

	private static final ResourceLocation TONGUE = AetherCore.getResource("textures/entities/moa/tongue.png");

	private static final ResourceLocation SADDLE = AetherCore.getResource("textures/entities/moa/saddle.png");

	private static final ResourceLocation EYES_CLOSED = AetherCore.getResource("textures/entities/moa/eyes_closed.png");

	private static final ResourceLocation PUPIL_LEFT = AetherCore.getResource("textures/entities/moa/pupil_left.png");

	private static final ResourceLocation PUPIL_RIGHT = AetherCore.getResource("textures/entities/moa/pupil_right.png");

	public RenderMoa(RenderManager manager)
	{
		super(manager, new ModelMoaLodHigh(), new ModelMoaLodLow(), 0.5F);

		SPRITE.initSprite(16, 16, 0, 0, false);
	}

	@Override
	protected void preRenderCallback(EntityMoa entityliving, float partialTicks)
	{
		float moaScale = entityliving.isChild() ? 0.5f : 0.85f;
		moaScale += entityliving.isGroupLeader() ? 0.15F : 0.0F;

		GL11.glScalef(moaScale, moaScale, moaScale);
	}

	private void renderMoa(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
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

		GlStateManager.color(base.getRed() / 255f, base.getGreen() / 255f, base.getBlue() / 255f);

		ModelMoaBase model = (ModelMoaBase) this.mainModel;
		model.LegL2.isHidden = true;
		model.LegR2.isHidden = true;
		model.JawMain.isHidden = true;
		model.HeadBeakMain.isHidden = true;

		if (!this.isLowDetail)
		{
			Color eyesC = genePool.getEyes().gene().data();

			// Re-render the head with our eyes texture and then draw the pupils
			model.HeadMain.callback = () -> {
				GlStateManager.color(eyesC.getRed() / 255f, eyesC.getGreen() / 255f, eyesC.getBlue() / 255f);

				this.renderManager.renderEngine.bindTexture(EYES);

				model.HeadFront.render(scale, true, false);

				GlStateManager.color(base.getRed() / 255f, base.getGreen() / 255f, base.getBlue() / 255f);

				EyeUtil.renderEyesFast(model, model.HeadFront, model.HeadFront, entity,
						scale, PUPIL_LEFT, PUPIL_RIGHT, EYES_CLOSED, EYES, false);

				if (genePool.getMarks() != null)
				{
					final String mark = genePool.getMarks().gene().getResourceName();

					if (mark.equals("curved"))
					{
						this.renderManager.renderEngine.bindTexture(CURVED_BODY);
					}
					else
					{
						this.renderManager.renderEngine.bindTexture(POINTED_BODY);
					}
				}
			};
		}

		if (genePool.getMarks() != null)
		{
			final String mark = genePool.getMarks().gene().getResourceName();

			if (mark.equals("curved"))
			{
				this.renderManager.renderEngine.bindTexture(CURVED_BODY);
			}
			else
			{
				this.renderManager.renderEngine.bindTexture(POINTED_BODY);
			}
		}
		model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		model.LegL2.isHidden = false;
		model.LegR2.isHidden = false;
		model.JawMain.isHidden = false;
		model.HeadBeakMain.isHidden = false;

		if (genePool.getMarks() != null)
		{
			final String mark = genePool.getMarks().gene().getResourceName();

			if (mark.equals("curved"))
			{
				this.renderManager.renderEngine.bindTexture(CURVED_BODY_HIGHLIGHT);
			}
			else
			{
				this.renderManager.renderEngine.bindTexture(POINTED_BODY_HIGHLIGHT);
			}
		}
		GL11.glColor3f(highlight.getRed() / 255f, highlight.getGreen() / 255f, highlight.getBlue() / 255f);

		model.setDefaultDisplayState(false);

		model.HeadFeatherL1.forceDisplayFlag = true;
		model.HeadFeatherL2.forceDisplayFlag = true;
		model.HeadFeatherR1.forceDisplayFlag = true;
		model.HeadFeatherR2.forceDisplayFlag = true;

		model.WingLFeatherInt1.forceDisplayFlag = true;
		model.WingLFeatherInt2.forceDisplayFlag = true;
		model.WingLFeatherExt1.forceDisplayFlag = true;
		model.WingLFeatherExt2.forceDisplayFlag = true;
		model.WingLFeatherExt3.forceDisplayFlag = true;

		model.WingRFeatherInt1.forceDisplayFlag = true;
		model.WingRFeatherInt2.forceDisplayFlag = true;
		model.WingRFeatherExt1.forceDisplayFlag = true;
		model.WingRFeatherExt2.forceDisplayFlag = true;
		model.WingRFeatherExt3.forceDisplayFlag = true;

		if (!this.isLowDetail)
		{
			model.LegLTalonL.forceDisplayFlag = true;
			model.LegLTalonR.forceDisplayFlag = true;
			model.LegLTalonM.forceDisplayFlag = true;

			model.LegRTalonL.forceDisplayFlag = true;
			model.LegRTalonR.forceDisplayFlag = true;
			model.LegRTalonM.forceDisplayFlag = true;
		}

		model.TailFeatherL.forceDisplayFlag = true;
		model.TailFeatherR.forceDisplayFlag = true;
		model.TailFeatherM.forceDisplayFlag = true;

		model.BodyMain.forceDisplayFlag = true;
		model.BodyFront.forceDisplayFlag = true;

		model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		model.setDefaultDisplayState(true);

		Color beakColor = genePool.getKeratin().gene().data();

		GlStateManager.color(beakColor.getRed() / 255f, beakColor.getGreen() / 255f, beakColor.getBlue() / 255f);

		this.renderManager.renderEngine.bindTexture(BEAK);

		model.setDefaultDisplayState(false);

		model.HeadBrow.forceDisplayFlag = true;
		model.JawMain.forceDisplayFlag = true;

		if (!this.isLowDetail)
		{
			model.JawFrontL.forceDisplayFlag = true;
			model.JawFrontR.forceDisplayFlag = true;
			model.JawBack.forceDisplayFlag = true;

			model.JawToothL1.forceDisplayFlag = true;
			model.JawToothL2.forceDisplayFlag = true;
			model.JawToothL3.forceDisplayFlag = true;
			model.JawToothL1_1.forceDisplayFlag = true;
			model.JawToothR2.forceDisplayFlag = true;
			model.JawToothR3.forceDisplayFlag = true;

			model.HeadBeakFrontL.forceDisplayFlag = true;
			model.HeadBeakFrontR.forceDisplayFlag = true;
		}

		model.HeadBeakMain.forceDisplayFlag = true;

		model.LegL2.forceDisplayFlag = true;
		model.LegL3.forceDisplayFlag = true;
		model.LegLAnkle.forceDisplayFlag = true;
		model.LegLToeL.forceDisplayFlag = true;
		model.LegLToeM.forceDisplayFlag = true;
		model.LegLToeR.forceDisplayFlag = true;

		model.LegR2.forceDisplayFlag = true;
		model.LegR3.forceDisplayFlag = true;
		model.LegRAnkle.forceDisplayFlag = true;
		model.LegRToeL.forceDisplayFlag = true;
		model.LegRToeM.forceDisplayFlag = true;
		model.LegRToeR.forceDisplayFlag = true;

		model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		model.setDefaultDisplayState(true);

		if (moa.isSaddled())
		{
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			GL11.glTranslatef(0.0F, -0.001F, 0.0F);
			GL11.glScalef(1.01F, 1.01F, 1.01F);

			this.renderManager.renderEngine.bindTexture(SADDLE);
			model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
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
