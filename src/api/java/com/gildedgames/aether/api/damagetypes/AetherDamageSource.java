package com.gildedgames.aether.api.damagetypes;

public class AetherDamageSource
{
	private final AetherDamageType type;

	private final int level;

	private AetherDamageSource(AetherDamageType damageType, int damageLevel)
	{
		this.type = damageType;
		this.level = damageLevel;
	}

	public static AetherDamageDefBuilder build()
	{
		return new AetherDamageDefBuilder();
	}

	public static class AetherDamageDefBuilder
	{
		private AetherDamageType type = AetherDamageType.SLASH;

		private int level = 0;

		private AetherDamageDefBuilder()
		{

		}

		public AetherDamageDefBuilder type(AetherDamageType type)
		{
			this.type = type;

			return this;
		}

		public AetherDamageDefBuilder level(int level)
		{
			this.level = level;

			return this;
		}

		public AetherDamageSource flush()
		{
			return new AetherDamageSource(this.type, this.level);
		}
	}
}
