package com.gildedgames.aether.common.world.util;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterGeneric extends Teleporter
{

	public TeleporterGeneric(WorldServer world)
	{
		super(world);
	}

	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw)
	{
	}

	@Override
	public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
	{
		return false;
	}

	@Override
	public boolean makePortal(Entity p_85188_1_)
	{
		return false;
	}

	@Override
	public void removeStalePortalLocations(long worldTime)
	{
	}

}
