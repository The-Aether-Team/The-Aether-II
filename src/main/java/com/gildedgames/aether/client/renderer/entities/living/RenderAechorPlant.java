package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelAechorPlant;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.monsters.EntityAechorPlant;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderAechorPlant extends LivingRenderer<EntityAechorPlant, ModelAechorPlant>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/aechor_plant/aechor_plant.png");

	public RenderAechorPlant(EntityRendererManager manager)
	{
		super(manager, new ModelAechorPlant(), 0.75f);
	}

	@Override
	protected void preRenderCallback(EntityAechorPlant plant, float partialTicks)
	{
		ModelAechorPlant model = this.getEntityModel();

		model.petal[0].isHidden = !plant.getPetalsPresent()[0];
		model.petal[1].isHidden = !plant.getPetalsPresent()[1];
		model.petal[2].isHidden = !plant.getPetalsPresent()[2];

		model.petal[3].isHidden = !plant.getPetalsPresent()[3];
		model.petal[4].isHidden = !plant.getPetalsPresent()[0];
		model.petal[5].isHidden = !plant.getPetalsPresent()[1];

		model.petal[6].isHidden = !plant.getPetalsPresent()[2];
		model.petal[7].isHidden = !plant.getPetalsPresent()[3];
		model.petal[8].isHidden = !plant.getPetalsPresent()[0];
		model.petal[9].isHidden = !plant.getPetalsPresent()[1];

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
