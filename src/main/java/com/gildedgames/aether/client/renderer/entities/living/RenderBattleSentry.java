package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelBattleSentry;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderBattleSentry extends RenderLiving<EntityLiving>
{

	private static final ResourceLocation TEXTURE = new ResourceLocation(AetherCore.MOD_ID, "textures/entities/detonation_sentry/detonation_sentry.png");

	public RenderBattleSentry(RenderManager renderManager)
	{
		super(renderManager, new ModelBattleSentry(), 1f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity)
	{
		return TEXTURE;
	}

	@Override
	protected void preRenderCallback(EntityLiving entityliving, float f)
	{
		GL11.glScalef(0.75F, 0.75F, 0.75F);
	}

}
