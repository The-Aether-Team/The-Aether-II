package com.gildedgames.aether.common.items.weapons;

public enum ItemDartType
{
	GOLDEN(0, 0, 0, "golden"),
	ENCHANTED(0, 0, 0, "enchanted"),
	POISON(0, 0, 0, "poison");

	private final int slashDamageLevel, pierceDamageLevel, impactDamageLevel;

	private final String id;

	ItemDartType(int slashDamageLevel, int pierceDamageLevel, int impactDamageLevel, String id)
	{
		this.slashDamageLevel = slashDamageLevel;
		this.pierceDamageLevel = pierceDamageLevel;
		this.impactDamageLevel = impactDamageLevel;

		this.id = id;
	}

	public static ItemDartType fromOrdinal(int ordinal)
	{
		ItemDartType[] darts = values();

		return darts[ordinal >= darts.length || ordinal < 0 ? 0 : ordinal];
	}

	public int getSlashDamageLevel()
	{
		return this.slashDamageLevel;
	}

	public int getPierceDamageLevel()
	{
		return this.pierceDamageLevel;
	}

	public int getImpactDamageLevel()
	{
		return this.impactDamageLevel;
	}

	public String getID()
	{
		return this.id;
	}

	public ItemDartType getAmmoItem()
	{
		return this;
	}
}
