package com.gildedgames.aether.common.entities.util.groups;

import net.minecraft.entity.EntityLivingBase;

public class EntityGroupAggressor
{

	final EntityGroup pack;

	public final EntityLivingBase agressor;

	public int time;

	public EntityGroupAggressor(EntityGroup pack, EntityLivingBase agressor, int time)
	{
		this.agressor = agressor;
		this.time = time;
		this.pack = pack;
	}

}
