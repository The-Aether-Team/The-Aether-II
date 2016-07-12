package com.gildedgames.aether.common.entities.living;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.world.World;

public class EntitySheepuff extends EntitySheep
{
	public EntitySheepuff(World world)
	{
		super(world);

		this.spawnableBlock = BlocksAether.aether_grass;
	}

	@Override
	public EntitySheepuff createChild(EntityAgeable ageable)
	{
		return new EntitySheepuff(this.worldObj);
	}
}
