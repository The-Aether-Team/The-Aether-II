package com.gildedgames.aether.common.entities.living.passive;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class EntityAetherAnimal extends EntityAnimal
{
	public EntityAetherAnimal(World world)
	{
		super(world);
	}

	@Override
	public float getBlockPathWeight(BlockPos pos)
	{
		return this.world.getBlockState(pos.down()).getBlock() == BlocksAether.aether_grass ? 10.0F :
				this.world.getLightBrightness(pos) - 0.5F;
	}
}
