package com.gildedgames.aether.common.events;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;

public class PostAetherTravelEvent extends EntityEvent
{
	public PostAetherTravelEvent(Entity entity)
	{
		super(entity);
	}
}
