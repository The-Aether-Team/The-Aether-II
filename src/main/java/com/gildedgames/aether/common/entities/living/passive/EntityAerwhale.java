package com.gildedgames.aether.common.entities.living.passive;

import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
import com.gildedgames.aether.api.capabilites.entity.properties.IEntityProperties;
import com.gildedgames.aether.common.entities.util.flying.EntityFlying;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityAerwhale extends EntityFlying implements IEntityProperties
{

	public EntityAerwhale(World world)
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
	protected SoundEvent getHurtSound()
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
