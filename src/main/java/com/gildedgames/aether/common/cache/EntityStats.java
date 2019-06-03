package com.gildedgames.aether.common.cache;

import com.gildedgames.aether.api.cache.IEntityStats;

public class EntityStats implements IEntityStats
{
	private final float maxHealth;

	private EntityStats(final float maxHealth)
	{
		this.maxHealth = maxHealth;
	}

	public static EntityStatsFactory build()
	{
		return new EntityStatsFactory();
	}

	@Override
	public float getMaxHealth()
	{
		return this.maxHealth;
	}

	public static class EntityStatsFactory
	{
		private float maxHealth;

		private EntityStatsFactory()
		{

		}

		public EntityStatsFactory maxHealth(final float value)
		{
			this.maxHealth = value;

			return this;
		}

		public EntityStats flush()
		{
			return new EntityStats(this.maxHealth);
		}
	}
}
