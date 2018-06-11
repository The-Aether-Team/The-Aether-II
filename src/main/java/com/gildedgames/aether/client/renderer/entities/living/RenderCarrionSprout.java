package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelCarrionSprout;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.passive.EntityCarrionSprout;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderCarrionSprout extends RenderLiving<EntityCarrionSprout>
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/carrion_sprout/carrion_sprout.png");

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

		float sinage = sprout.prevSinage + ((sprout.sinage - sprout.prevSinage) * partialTicks);

		float f1 = (float) Math.sin(sinage - 1);

		final float f2;

		if (sprout.hurtTime > 0)
		{
			f1 *= 0.45F;
			f1 -= 0.125F;

			f2 = 1.75F + ((float) Math.sin(sinage + 2.0F) * 1.5F);
		}
		else
		{
			f1 *= 0.25F;
			f2 = 1.75F + ((float) Math.sin(sinage + 2.0F) * 1.5F);
		}

		this.plantModel.sinage = f1;
		this.plantModel.sinage2 = f2;

		this.shadowSize = 0.10f * sprout.getSproutSize();
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityCarrionSprout entity)
	{
		return TEXTURE;
	}
}
