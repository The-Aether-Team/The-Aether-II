package com.gildedgames.aether.common.entities.living;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.world.World;

public class EntityRam extends EntitySheep
{
	public EntityRam(World world)
	{
		super(world);

		this.spawnableBlock = BlocksAether.aether_grass;
	}

	@Override
	public EntityRam createChild(EntityAgeable ageable)
	{
		return new EntityRam(this.worldObj);
	}
}
