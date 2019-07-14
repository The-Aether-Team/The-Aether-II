package com.gildedgames.aether.common.entities.animals;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.entities.ai.AetherNavigateGround;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class EntityAetherAnimal extends AnimalEntity
{

	protected EntityAetherAnimal(EntityType<? extends AnimalEntity> type, World world)
	{
		super(type, world);
	}

	@Override
	public float getBlockPathWeight(BlockPos pos, IWorldReader reader)
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
