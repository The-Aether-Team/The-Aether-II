package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelAechorPlant;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.mobs.EntityAechorPlant;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderAechorPlant extends RenderLiving<EntityAechorPlant>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/aechor_plant/aechor_plant.png");

	public RenderAechorPlant(RenderManager manager)
	{
		super(manager, new ModelAechorPlant(), 0.75f);
	}

	@Override
	protected void preRenderCallback(EntityAechorPlant plant, float partialTicks)
	{
		float signage = plant.prevSinage + ((plant.sinage - plant.prevSinage) * partialTicks);

		float f1 = (float) Math.sin(signage);
		float f3;

		if (plant.hurtTime > 0)
		{
			f1 *= 0.45F;
			f1 -= 0.125F;
			f3 = 1.75F + ((float) Math.sin(signage + 2.0F) * 1.5F);
		}
		else
		{
			if (plant.canSeePrey())
			{
				f1 *= 0.25F;
				f3 = 1.75F + ((float) Math.sin(signage + 2.0F) * 1.5F);
			}
			else
			{
				f1 *= 0.125F;
				f3 = 1.75F;
			}
		}

		ModelAechorPlant model = (ModelAechorPlant) this.mainModel;

		model.sinage = f1;
		model.sinage2 = f3;

		float f2 = 0.625F + ((float) plant.getPlantSize() / 6F);

		model.size = f2;
		this.shadowSize = f2 - 0.25F;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAechorPlant entity)
	{
		return texture;
	}
}
