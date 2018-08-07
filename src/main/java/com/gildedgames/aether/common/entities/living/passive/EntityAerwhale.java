package com.gildedgames.aether.common.entities.living.passive;

import com.gildedgames.aether.common.entities.ai.EntityAIForcedWander;
import com.gildedgames.aether.common.entities.util.flying.EntityFlying;
import com.gildedgames.aether.common.entities.util.flying.PathNavigateFlyer;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityAerwhale extends EntityFlying
{

	public EntityAerwhale(final World world)
	{
		super(world);

		this.setSize(5.0F, 1.0F);
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
	}

	@Override
	protected PathNavigate createNavigator(final World worldIn)
	{
		PathNavigateFlyer navigateFlyer = new PathNavigateFlyer(this, worldIn);

		navigateFlyer.setAvoidGround(true);

		return navigateFlyer;
	}

	@Override
	protected void initEntityAI()
	{
		final EntityAIMoveTowardsRestriction moveTowardsRestriction = new EntityAIMoveTowardsRestriction(this, 0.1D);
		final EntityAIWander wander = new EntityAIForcedWander(this, 0.1D, 3);

		wander.setMutexBits(3);
		moveTowardsRestriction.setMutexBits(3);

		this.tasks.addTask(1, moveTowardsRestriction);
		this.tasks.addTask(2, wander);
	}

	@Override
	public int getTalkInterval()
	{
		return 2000;
	}

	@Override
	protected float getSoundVolume()
	{
		return 10.0F;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundsAether.aerwhale_ambient;
	}

	@Override
	protected SoundEvent getHurtSound(final DamageSource src)
	{
		return SoundsAether.aerwhale_ambient;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.aerwhale_death;
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}
}
