package com.gildedgames.aether.common.entities.living;

import com.gildedgames.aether.common.registry.minecraft.SoundsAether;
import com.gildedgames.aether.common.entities.util.flying.EntityFlying;
import net.minecraft.world.World;

public class EntityAerwhale extends EntityFlying
{

	public EntityAerwhale(World world)
	{
		super(world);

		this.setSize(5.0F, 1.0F);
	}

	@Override
	public int getTalkInterval()
	{
		return 800;
	}

	@Override
	protected float getSoundVolume()
	{
		return 10.0F;
	}

	@Override
	protected net.minecraft.util.SoundEvent getAmbientSound()
	{
		return SoundsAether.aerwhale_ambient;
	}

	@Override
	protected net.minecraft.util.SoundEvent getHurtSound()
	{
		return SoundsAether.aerwhale_ambient;
	}

	@Override
	protected net.minecraft.util.SoundEvent getDeathSound()
	{
		return SoundsAether.aerwhale_death;
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

}
