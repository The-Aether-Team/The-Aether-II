package com.gildedgames.aether.common.entities.living;

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
	protected void dropFewItems(boolean p_70628_1_, int looting)
	{
		int amount = this.getItemQuantityDropped() + this.rand.nextInt(1 + looting);

		for (int count = 0; count < amount; ++count)
		{
			this.dropItem(this.getDropItem(), 1);
		}
	}

	protected int getItemQuantityDropped()
	{
		return this.rand.nextInt(3) + 1;
	}

	@Override
	public float getBlockPathWeight(BlockPos pos)
	{
		return this.worldObj.getBlockState(pos.down()).getBlock() == BlocksAether.aether_grass ? 10.0F : this.worldObj.getLightBrightness(pos) - 0.5F;
	}
}
