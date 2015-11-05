package com.gildedgames.aether.common.items.weapons;

public enum ItemDartType
{
	GOLDEN(1.5f, "golden"),
	ENCHANTED(2.0f, "enchanted"),
	POISON(1.5f, "poison"),
	PHOENIX(2.5f, "phoenix");

	private final float damage;

	private final String id;

	ItemDartType(float damage, String id)
	{
		this.damage = damage;
		this.id = id;
	}

	public float getDamage()
	{
		return this.damage;
	}

	public String getID()
	{
		return this.id;
	}

	public ItemDartType getAmmoItem()
	{
		return (this == ItemDartType.PHOENIX) ? ItemDartType.GOLDEN : this;
	}

	public static ItemDartType fromOrdinal(int ordinal)
	{
		ItemDartType[] darts = values();

		return darts[ordinal > darts.length || ordinal < 0 ? 0 : ordinal];
	}
}
