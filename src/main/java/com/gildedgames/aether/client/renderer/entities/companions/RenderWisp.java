package com.gildedgames.aether.client.renderer.entities.companions;

import com.gildedgames.aether.common.entities.living.companions.EntityCompanion;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;

public abstract class RenderWisp<T extends EntityCompanion> extends RenderCompanion<T>
{
	public RenderWisp(RenderManager renderManager, ModelBase model, float shadowSize, double distanceLimit)
	{
		super(renderManager, model, shadowSize, distanceLimit);
	}
}
