package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelCarrionSproutBase;
import com.gildedgames.aether.client.models.entities.living.ModelCarrionSproutLodHigh;
import com.gildedgames.aether.client.models.entities.living.ModelCarrionSproutLodLow;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityCarrionSprout;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderCarrionSprout extends RenderLivingLOD<EntityCarrionSprout, ModelCarrionSproutBase>
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/carrion_sprout/carrion_sprout.png");

	public RenderCarrionSprout(final EntityRendererManager manager)
	{
		super(manager, new ModelCarrionSproutLodHigh(), new ModelCarrionSproutLodLow(), 0.75f);
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

		ModelCarrionSproutBase model = this.getEntityModel();

		model.sinage = f1;
		model.sinage2 = f2;

		this.shadowSize = 0.10f * sprout.getSproutSize();
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityCarrionSprout entity)
	{
		return TEXTURE;
	}

	@Override
	protected double getHighLODMinDistanceSq()
	{
		return 8.0f * 8.0f;
	}
}
