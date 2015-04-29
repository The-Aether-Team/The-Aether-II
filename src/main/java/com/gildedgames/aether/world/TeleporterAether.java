package com.gildedgames.aether.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterAether extends Teleporter
{

	public TeleporterAether(WorldServer world)
	{
		super(world);
	}

	@Override
	public void placeInPortal(Entity par1Entity, float rotationYaw)
	{
		return;
	}

	@Override
	public boolean placeInExistingPortal(Entity par1Entity, float rotationYaw)
	{
		return false;
	}

	@Override
	public boolean makePortal(Entity par1Entity)
	{
		return false;
	}

	@Override
	public void removeStalePortalLocations(long par1)
	{
		return;
	}
}
