package com.gildedgames.aether.common.entities.living.enemies;

import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
import com.gildedgames.aether.api.capabilites.entity.properties.IEntityProperties;
import com.gildedgames.aether.common.registry.minecraft.SoundsAether;
import com.gildedgames.aether.common.entities.util.flying.EntityFlyingDayMob;
import net.minecraft.world.World;

public class EntityZephyr extends EntityFlyingDayMob implements IEntityProperties
{

	public EntityZephyr(World world)
	{
		super(world);

		this.setSize(1.0F, 1.0F);
	}

	@Override
	protected net.minecraft.util.SoundEvent getAmbientSound()
	{
		return SoundsAether.zephyr_ambient;
	}

	@Override
	protected net.minecraft.util.SoundEvent getHurtSound()
	{
		return SoundsAether.zephyr_ambient;
	}

	@Override
	protected net.minecraft.util.SoundEvent getDeathSound()
	{
		return SoundsAether.zephyr_ambient;
	}

	@Override
	public ElementalState getElementalState()
	{
		return ElementalState.AIR;
	}

	@Override
	public ElementalState getAttackElement()
	{
		return ElementalState.AIR;
	}
}
