package com.gildedgames.aether.common.items.weapons.crossbow;

public enum ItemBoltType
{
	/* DAMAGES ARE NOT FINAL */
	SKYROOT(0.5f, "skyroot"),
	STONE(1.0f, "holystone"),
	ZANITE(2.0f, "zanite"),
	ARKENIUM(4.0f, "arkenium"),
	GRAVITITE(3.0f, "gravitite");

	private final float damage;

	private final String id;

	ItemBoltType(float damage, String id)
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

	public ItemBoltType getAmmoItem()
	{
		return this;
	}

	public static ItemBoltType fromOrdinal(int ordinal)
	{
		ItemBoltType[] bolts = values();

		return bolts[ordinal > bolts.length || ordinal < 0 ? 0 : ordinal];
	}
}
