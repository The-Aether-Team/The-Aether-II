package com.gildedgames.aether.common.entities.living.enemies;

import com.gildedgames.aether.common.SoundsAether;
import com.gildedgames.aether.common.entities.util.flying.EntityFlyingMob;
import net.minecraft.world.World;

public class EntityTempest extends EntityFlyingMob
{

	public EntityTempest(World world)
	{
		super(world);

		this.setSize(1.0F, 1.0F);
	}

	@Override
	protected net.minecraft.util.SoundEvent getAmbientSound()
	{
		return SoundsAether.tempest_ambient;
	}

	@Override
	protected net.minecraft.util.SoundEvent getHurtSound()
	{
		return SoundsAether.tempest_hurt;
	}

	@Override
	protected net.minecraft.util.SoundEvent getDeathSound()
	{
		return SoundsAether.tempest_death;
	}

}
