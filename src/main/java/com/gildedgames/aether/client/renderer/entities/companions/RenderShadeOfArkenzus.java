package com.gildedgames.aether.client.renderer.entities.companions;

import com.gildedgames.aether.client.models.entities.companions.ModelShadeOfArkenzus;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.companions.EntityShadeOfArkenzus;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderShadeOfArkenzus extends RenderCompanion<EntityShadeOfArkenzus>
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/companions/shade_of_arkenzus.png");

	public RenderShadeOfArkenzus(RenderManager renderManager)
	{
		super(renderManager, new ModelShadeOfArkenzus(), 0.3f, 1.5D);
	}

	@Override
	protected void preRenderCallback(EntityShadeOfArkenzus entity, float partialTicks)
	{
		GL11.glScalef(0.5F, 0.5F, 0.5F);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		super.preRenderCallback(entity, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityShadeOfArkenzus entity)
	{
		return TEXTURE;
	}
}
