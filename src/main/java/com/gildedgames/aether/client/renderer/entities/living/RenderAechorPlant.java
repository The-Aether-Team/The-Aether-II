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

	private final ModelAechorPlant plantModel;

	public RenderAechorPlant(RenderManager manager)
	{
		super(manager, new ModelAechorPlant(), 0.75f);

		this.plantModel = (ModelAechorPlant) this.mainModel;
	}

	@Override
	protected void preRenderCallback(EntityAechorPlant plant, float partialTicks)
	{
		this.plantModel.petal[0].isHidden = !plant.getPetalsPresent()[0];
		this.plantModel.petal[1].isHidden = !plant.getPetalsPresent()[1];
		this.plantModel.petal[2].isHidden = !plant.getPetalsPresent()[2];

		this.plantModel.petal[3].isHidden = !plant.getPetalsPresent()[3];
		this.plantModel.petal[4].isHidden = !plant.getPetalsPresent()[0];
		this.plantModel.petal[5].isHidden = !plant.getPetalsPresent()[1];

		this.plantModel.petal[6].isHidden = !plant.getPetalsPresent()[2];
		this.plantModel.petal[7].isHidden = !plant.getPetalsPresent()[3];
		this.plantModel.petal[8].isHidden = !plant.getPetalsPresent()[0];
		this.plantModel.petal[9].isHidden = !plant.getPetalsPresent()[1];

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
