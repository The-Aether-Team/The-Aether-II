package com.gildedgames.aether.common.items.weapons.crossbow;

/**
 * Created by Chris on 3/8/2016.
 */
public enum ItemBoltType
{
	STONE(1.0f, "stone"),
	ZANITE(2.0f, "zanite");

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
