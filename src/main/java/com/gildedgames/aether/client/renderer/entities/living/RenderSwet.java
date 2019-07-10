package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelSwetHead;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerSwetJelly;
import com.gildedgames.aether.common.entities.monsters.EntitySwet;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSwet extends RenderLiving<EntitySwet>
{

	public RenderSwet(final RenderManager renderManagerIn)
	{
		super(renderManagerIn, new ModelSwetHead(), 0.78F);

		this.addLayer(new LayerSwetJelly(this));
	}

	@Override
	protected void preRenderCallback(final EntitySwet entity, final float partialTickTime)
	{
		final float sc = (entity.getFoodSaturation() / 5.0F);

		this.shadowSize = 0.3F + (sc * 0.48F);

		if (entity.getFoodSaturation() == 0)
		{
			GlStateManager.translate(0.0, 0.18, 0.0);
		}
		else
		{
			GlStateManager.translate(0.0, 0.1, 0.0);
		}

		GlStateManager.scale(0.35F, 0.35F, 0.35F);

		final float f1 = 2.0F;
		final float f2 = (entity.prevSquishFactor + (entity.squishFactor - entity.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
		final float f3 = 1.0F / (f2 + 1.0F);

		GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
	}

	@Override
	public void doRender(final EntitySwet entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks)
	{
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntitySwet entity)
	{
		return entity.getType().texture_head;
	}
}
