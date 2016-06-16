package com.gildedgames.aether.common.items.weapons.crossbow;

public enum ItemCrossbowType
{
	/* DAMAGES ARE NOT FINAL */
	WOOD(0.5f, "skyroot"),
	ZANITE(1.0f, "zanite"),
	GRAVITITE(2.0f, "gravitite");

	private final float damage;

	private final String id;

	ItemCrossbowType(float damage, String id)
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

	public ItemCrossbowType getCrossbowItem()
	{
		return this;
	}

	public static ItemCrossbowType fromOrdinal(int ordinal)
	{
		ItemCrossbowType[] bolts = values();

		return bolts[ordinal > bolts.length || ordinal < 0 ? 0 : ordinal];
	}
}
