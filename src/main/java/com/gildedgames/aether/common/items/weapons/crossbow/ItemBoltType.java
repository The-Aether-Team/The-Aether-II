package com.gildedgames.aether.common.items.weapons.crossbow;

public enum ItemBoltType
{

	/* DAMAGES ARE NOT FINAL */
	SKYROOT(1.0f, "skyroot"),
	HOLYSTONE(1.5f, "holystone"),
	SCATTERGLASS(2.0f, "scatterglass"),
	ZANITE(2.5f, "zanite"),
	ARKENIUM(3.5f, "arkenium"),
	GRAVITITE(3.0f, "gravitite");

	private final float damage;

	private final String id;

	ItemBoltType(float damage, String id)
	{
		this.damage = damage;
		this.id = id;
	}

	public static ItemBoltType fromOrdinal(int ordinal)
	{
		ItemBoltType[] bolts = values();

		return bolts[ordinal > bolts.length || ordinal < 0 ? 0 : ordinal];
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
}
