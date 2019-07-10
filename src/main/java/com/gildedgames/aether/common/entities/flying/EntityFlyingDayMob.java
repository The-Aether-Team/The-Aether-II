package com.gildedgames.aether.common.entities.flying;

import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

public class EntityFlyingDayMob extends EntityFlyingMob
{

	public EntityFlyingDayMob(World world)
	{
		super(world);
	}

	@Override
	protected PathNavigate createNavigator(final World worldIn)
	{
		return new PathNavigateFlyer(this, worldIn);
	}

	@Override
	protected void ageInSunlight()
	{

	}

	@Override
	protected boolean isValidLightLevel()
	{
		return true;
	}

}
