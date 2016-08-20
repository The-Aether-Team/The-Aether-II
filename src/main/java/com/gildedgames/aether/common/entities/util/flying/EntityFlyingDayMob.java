package com.gildedgames.aether.common.entities.util.flying;

import net.minecraft.world.World;

public class EntityFlyingDayMob extends EntityFlyingMob
{

	public EntityFlyingDayMob(World world)
	{
		super(world);
	}

	@Override
	protected boolean isValidLightLevel()
	{
		return true;
	}

}
