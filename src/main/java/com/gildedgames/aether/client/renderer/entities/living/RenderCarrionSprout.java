package com.gildedgames.aether.client.renderer.entities.living;

import org.lwjgl.opengl.GL11;

import com.gildedgames.aether.client.models.entities.living.ModelCarrionSprout;
import com.gildedgames.aether.common.entities.living.passive.EntityCarrionSprout;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderCarrionSprout extends RenderLiving<EntityCarrionSprout>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation("aether", "textures/entities/carrion_sprout/carrion_sprout.png");

	private final ModelCarrionSprout plantModel;

	public RenderCarrionSprout(final RenderManager manager)
	{
		super(manager, new ModelCarrionSprout(), 0.75f);

		this.plantModel = (ModelCarrionSprout) this.mainModel;
	}

	@Override
	protected void preRenderCallback(final EntityCarrionSprout sprout, final float partialTicks)
	{
		final float scale = 0.5f + (sprout.getSproutSize() * 0.1f);

		GL11.glScalef(scale, scale, scale);

		float sinage = (float) Math.sin(sprout.sinage - 1);
		final float sinage2;

		if (sprout.hurtTime > 0)
		{
			sinage *= 0.45F;
			sinage -= 0.125F;

			sinage2 = 1.75F + ((float) Math.sin(sprout.sinage + 2.0F) * 1.5F);
		}
		else
		{
			sinage *= 0.25F;
			sinage2 = 1.75F + ((float) Math.sin(sprout.sinage + 2.0F) * 1.5F);
		}

		this.plantModel.sinage = sinage;
		this.plantModel.sinage2 = sinage2;

		this.shadowSize = 0.10f * sprout.getSproutSize();
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityCarrionSprout entity)
	{
		return TEXTURE;
	}
}
