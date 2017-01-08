package com.gildedgames.aether.common.entities.util;

import net.minecraft.entity.EntityLivingBase;

public class EntityGroupAggressor
{

	public EntityLivingBase agressor;

	public int time;

	final EntityGroup pack;

	public EntityGroupAggressor(EntityGroup pack, EntityLivingBase agressor, int time)
	{
		this.agressor = agressor;
		this.time = time;
		this.pack = pack;
	}

}
