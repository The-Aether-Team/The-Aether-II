package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelMoa;
import com.gildedgames.aether.common.entities.moa.EntityMoa;
import com.gildedgames.aether.common.entities.moa.MoaGenetics;
import com.gildedgames.aether.common.entities.util.AnimalGender;
import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class RenderMoa extends RenderLiving<EntityMoa>
{

	public static ResourceLocation FEATHER_TEXTURE = new ResourceLocation("aether", "textures/entities/moa/feathers.png");
	
	public RenderMoa(RenderManager manager)
	{
		super(manager, new ModelMoa(), 1.0F);
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
		float moaScale = entityMoa.isChild() ? 0.5f : 1.0f;
		
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

		/*if (entity.riddenByEntity != null && entity.riddenByEntity instanceof EntityPlayer)
		{
			PlayerAether playerInfo = PlayerAether.get((EntityPlayer) entity.riddenByEntity);

			if (playerInfo.isDonator() && playerInfo.getDonatorChoice().getMoa() != DonatorMoa.OFF)
			{
				GL11.glColor3f(1.0F, 1.0F, 1.0F);
			}
		}*/

		this.renderManager.renderEngine.bindTexture(texture);

		for (ModelRenderer model : models)
		{
			model.render(par7);
		}
	}

	public void renderMoa(int color, ResourceLocation texture, EntityLivingBase entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		ModelMoa model = ((ModelMoa) this.mainModel);
		
		float red = ((color >> 16) & 0xff) / 255F;
		float green = ((color >> 8) & 0xff) / 255F;
		float blue = (color & 0xff) / 255F;
		GL11.glColor3f(red, green, blue);

		/*if (entity.riddenByEntity != null && entity.riddenByEntity instanceof EntityPlayer)
		{
			PlayerAether playerInfo = PlayerAether.get((EntityPlayer) entity.riddenByEntity);

			if (playerInfo.isDonator() && playerInfo.getDonatorChoice().getMoa() != DonatorMoa.OFF)
			{
				GL11.glColor3f(1.0F, 1.0F, 1.0F);
			}
		}*/

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

	protected void renderMoa(EntityLivingBase entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		EntityMoa moa = ((EntityMoa) entity);
		MoaGenetics genetics = moa.getGenetics();
		ModelMoa model = ((ModelMoa) this.mainModel);
		
		if (genetics == null)
		{
			MoaGenetics defaultGenetics = new MoaGenetics();

			defaultGenetics.setBaseGenetics(new MoaGenetics.BaseGenetics("Default", 0x93C5ED, 0, 0));
			
			int beakLegColor = 0x898A81;
			
			defaultGenetics.legColor = beakLegColor;
			defaultGenetics.beakColor = beakLegColor;
			defaultGenetics.eyeColor = 0xffffff;
			defaultGenetics.markColor = 0x3A4F5E;
			
			defaultGenetics.markBack = MoaGenetics.backMarks[0];
			defaultGenetics.markHead = MoaGenetics.headMarks[0];
			defaultGenetics.markTail = MoaGenetics.tailMarks[0];
			defaultGenetics.markWing = MoaGenetics.wingMarks[0];
			
			genetics = defaultGenetics;
		}
		
		GL11.glPushMatrix();
		
		//GL11.glRotatef((float)Math.toDegrees(moa.rotationYaw), 0, 0.5F, 0);

		GL11.glDepthMask(true);
		
		PlayerAether playerInfo = null;

		/*if (moa.riddenByEntity != null && moa.riddenByEntity instanceof EntityPlayer)
		{
			playerInfo = PlayerAether.get((EntityPlayer) moa.riddenByEntity);
			
			if (playerInfo.isDonator() && playerInfo.getDonatorChoice().getMoa() != DonatorMoa.OFF)
			{
				genetics.body = playerInfo.getDonatorChoice().getMoa().resourceLocation;
				genetics.legs = playerInfo.getDonatorChoice().getMoa().resourceLocation;
			}
		}*/

		this.renderMoa(genetics.bodyColor, genetics.body, entity, par2, par3, par4, par5, par6, par7);

		this.renderMoa(genetics.legColor, genetics.legs, entity, par7, model.LeftLeg1, model.RightLeg1, model.LeftLeg2, model.RightLeg2, model.FootLeft, model.FootRight, model.Toe1Left, model.Toe1Right, model.Toe2Left, model.Toe2Right);
		
		/*if (playerInfo != null && playerInfo.isDonator() && playerInfo.getDonatorChoice().getMoa() != DonatorMoa.OFF)
		{
			return;
		}*/

		this.renderMoa(genetics.beakColor, genetics.beak, entity, par7, model.Jaw, model.Head);
		this.renderMoa(genetics.eyeColor, genetics.eye, entity, par7, model.Head);

		GL11.glScalef(1.001f, 1.001f, 1.001f);
		GL11.glTranslatef(0.0f, -0.001f, 0.001f);
		
		this.renderMoa(genetics.markColor, genetics.markHead, entity, par7, model.Head);
		
		GL11.glTranslatef(0.0f, 0.0f, -0.001f);
		
		this.renderMoa(genetics.markColor, genetics.markBack, entity, par7, model.Body, model.Chest);
		this.renderMoa(genetics.markColor, genetics.markWing, entity, par7, model.LeftWing, model.RightWing);

		this.renderMoa(genetics.markColor, genetics.markTail, entity, par7, model.TailLeft, model.TailMiddle, model.TailRight);
		
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		this.renderManager.renderEngine.bindTexture(genetics.tongue);
		model.Jaw.render(par7);
		
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		this.renderManager.renderEngine.bindTexture(genetics.teeth);
		model.Teeth.render(par7);
		
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		this.renderManager.renderEngine.bindTexture(genetics.legs);
		model.Claw1Left.render(par7);
		model.Claw2Left.render(par7);
		model.Claw3Left.render(par7);
		model.Claw1Right.render(par7);
		model.Claw2Right.render(par7);
		model.Claw3Right.render(par7);
		
		/*if (moa.getSaddled())
		{
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			this.renderManager.renderEngine.bindTexture(genetics.saddle);
			model.Body.render(par7);
			model.Chest.render(par7);
		}*/
		
		if (moa.getGender() == AnimalGender.MALE)
		{
			this.renderMoa(genetics.bodyColor, RenderMoa.FEATHER_TEXTURE, entity, par7, model.feather1, model.feather2, model.feather3, model.feather4);
		}

		GL11.glColor3f(1.0f, 1.0f, 1.0f);

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
		super.doRender(entity, d, d1, d2, f, f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMoa entity)
	{
		return null;
	}
}
