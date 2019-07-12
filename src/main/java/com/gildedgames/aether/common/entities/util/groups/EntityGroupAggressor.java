package com.gildedgames.aether.common.entities.util.groups;

import net.minecraft.entity.LivingEntity;

public class EntityGroupAggressor
{

	final EntityGroup pack;

	public final LivingEntity agressor;

	public int time;

	public EntityGroupAggressor(EntityGroup pack, LivingEntity agressor, int time)
	{
		this.agressor = agressor;
		this.time = time;
		this.pack = pack;
	}

}
