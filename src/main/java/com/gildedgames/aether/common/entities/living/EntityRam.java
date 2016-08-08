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

		this.setSize(1.1F, 1.6F);

		this.spawnableBlock = BlocksAether.aether_grass;
	}

	@Override
	protected float getSoundPitch()
	{
		return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.55F;
	}

	@Override
	public EntityRam createChild(EntityAgeable ageable)
	{
		return new EntityRam(this.worldObj);
	}
}
