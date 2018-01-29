package com.gildedgames.aether.common.entities.living.passive;

import com.gildedgames.aether.common.entities.util.flying.EntityFlying;
import com.gildedgames.aether.common.registry.content.SoundsAether;
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
	protected void initEntityAI()
	{
		super.initEntityAI();
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
