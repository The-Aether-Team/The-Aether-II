package com.gildedgames.aether.common.cache;

import com.gildedgames.aether.api.cache.IEntityStats;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;

import java.util.Map;

public class EntityStats implements IEntityStats
{
	private final float maxHealth;

	private final double slashDefenseLevel, pierceDefenseLevel, impactDefenseLevel;

	private final Map<IAetherStatusEffects.effectTypes, Double> resistances;

	private EntityStats(final float maxHealth, final double slashDefenseLevel, final double pierceDefenseLevel, final double impactDefenseLevel, final Map<IAetherStatusEffects.effectTypes, Double> resistances)
	{
		this.maxHealth = maxHealth;
		this.slashDefenseLevel = slashDefenseLevel;
		this.pierceDefenseLevel = pierceDefenseLevel;
		this.impactDefenseLevel = impactDefenseLevel;
		this.resistances = resistances;
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

	public Map<IAetherStatusEffects.effectTypes, Double> getResistances()
	{
		return this.resistances;
	}

	public static class EntityStatsFactory
	{
		private float maxHealth;

		private double slashDefenseLevel, pierceDefenseLevel, impactDefenseLevel;

		private Map<IAetherStatusEffects.effectTypes, Double> resistances;

		private EntityStatsFactory()
		{

		}

		public EntityStatsFactory maxHealth(final float value)
		{
			this.maxHealth = value;

			return this;
		}

		public EntityStatsFactory slashDefenseLevel(final double value)
		{
			this.slashDefenseLevel = value;

			return this;
		}

		public EntityStatsFactory pierceDefenseLevel(final double value)
		{
			this.pierceDefenseLevel = value;

			return this;
		}

		public EntityStatsFactory impactDefenseLevel(final double value)
		{
			this.impactDefenseLevel = value;

			return this;
		}

		public EntityStatsFactory resistances(final Map<IAetherStatusEffects.effectTypes, Double> value)
		{
			this.resistances = value;

			return this;
		}

		public EntityStats flush()
		{
			return new EntityStats(this.maxHealth, this.slashDefenseLevel, this.pierceDefenseLevel, this.impactDefenseLevel, this.resistances);
		}
	}
}
