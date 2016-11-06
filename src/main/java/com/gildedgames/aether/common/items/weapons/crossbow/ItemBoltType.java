package com.gildedgames.aether.common.items.weapons.crossbow;

public enum ItemBoltType
{

	/* DAMAGES ARE NOT FINAL */
	SKYROOT(1.0f, "skyroot"),
	HOLYSTONE(2.0f, "holystone"),
	SCATTERGLASS(2.5f, "scatterglass"),
	BONESHARD(2.5F, "boneshard"),
	ZANITE(3.5f, "zanite"),
	ARKENIUM(5.0f, "arkenium"),
	GRAVITITE(7.0f, "gravitite");

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
