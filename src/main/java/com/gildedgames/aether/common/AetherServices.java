package com.gildedgames.aether.common;

import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.aether.common.world.dungeon.DungeonInstanceHandler;

public class AetherServices
{

	private DungeonInstanceHandler dungeonInstanceHandler;
	
	public AetherServices(Side side)
	{

	}
	
	public DungeonInstanceHandler getDungeonInstanceHandler()
	{
		return this.dungeonInstanceHandler;
	}
	
	public void setDungeonInstanceHandler(DungeonInstanceHandler dungeonInstanceHandler)
	{
		this.dungeonInstanceHandler = dungeonInstanceHandler;
	}

}
