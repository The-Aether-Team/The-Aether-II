package com.gildedgames.aether.common.entities.living.mobs;

import com.gildedgames.aether.common.entities.util.flying.EntityFlyingDayMob;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityZephyr extends EntityFlyingDayMob
{

	public EntityZephyr(final World world)
	{
		super(world);

		this.setSize(1.0F, 1.0F);

		this.experienceValue = 3;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundsAether.zephyr_ambient;
	}

	@Override
	protected SoundEvent getHurtSound(final DamageSource src)
	{
		return SoundsAether.zephyr_ambient;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.zephyr_ambient;
	}
}
