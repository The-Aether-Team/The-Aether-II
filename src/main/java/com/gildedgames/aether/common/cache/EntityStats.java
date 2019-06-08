package com.gildedgames.aether.common.cache;

import com.gildedgames.aether.api.cache.IEntityStats;

public class EntityStats implements IEntityStats
{
	private final float maxHealth;

	private final double slashDefenseLevel, pierceDefenseLevel, impactDefenseLevel;

	private EntityStats(final float maxHealth, final double slashDefenseLevel, final double pierceDefenseLevel, final double impactDefenseLevel)
	{
		this.maxHealth = maxHealth;
		this.slashDefenseLevel = slashDefenseLevel;
		this.pierceDefenseLevel = pierceDefenseLevel;
		this.impactDefenseLevel = impactDefenseLevel;
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

	@Override
	public double getSlashDefenseLevel()
	{
		return this.slashDefenseLevel;
	}

	@Override
	public double getPierceDefenseLevel()
	{
		return this.pierceDefenseLevel;
	}

	@Override
	public double getImpactDefenseLevel()
	{
		return this.impactDefenseLevel;
	}

	public static class EntityStatsFactory
	{
		private float maxHealth;

		private double slashDefenseLevel, pierceDefenseLevel, impactDefenseLevel;

		private EntityStatsFactory()
		{

		}

		public EntityStatsFactory maxHealth(final float value)
		{
			this.maxHealth = value;

			return this;
		}

		public EntityStatsFactory slashDefenseLevel(final float value)
		{
			this.slashDefenseLevel = value;

			return this;
		}

		public EntityStatsFactory pierceDefenseLevel(final float value)
		{
			this.pierceDefenseLevel = value;

			return this;
		}

		public EntityStatsFactory impactDefenseLevel(final float value)
		{
			this.impactDefenseLevel = value;

			return this;
		}

		public EntityStats flush()
		{
			return new EntityStats(this.maxHealth, this.slashDefenseLevel, this.pierceDefenseLevel, this.impactDefenseLevel);
		}
	}
}
