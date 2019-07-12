package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelVaranys;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.monsters.EntityVaranys;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderVaranys extends LivingRenderer<EntityVaranys, ModelVaranys>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/varanys/varanys.png");

	public RenderVaranys(final EntityRendererManager renderManager)
	{
		super(renderManager, new ModelVaranys(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityVaranys entity)
	{
		return TEXTURE;
	}

}
