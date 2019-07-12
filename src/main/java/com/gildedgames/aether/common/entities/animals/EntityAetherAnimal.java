package com.gildedgames.aether.common.entities.animals;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.entities.ai.AetherNavigateGround;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class EntityAetherAnimal extends AnimalEntity
{
	public EntityAetherAnimal(World world)
	{
		super(world);
	}

	@Override
	public float getBlockPathWeight(BlockPos pos)
	{
		return this.world.getBlockState(pos.down()).getBlock() == BlocksAether.aether_grass ? 10.0F :
				this.world.getBrightness(pos) - 0.5F;
	}

	@Override
	protected PathNavigator createNavigator(final World worldIn)
	{
		return new AetherNavigateGround(this, worldIn);
	}

	protected void applyStatusEffectOnAttack(final Entity target)
	{

	}
}
