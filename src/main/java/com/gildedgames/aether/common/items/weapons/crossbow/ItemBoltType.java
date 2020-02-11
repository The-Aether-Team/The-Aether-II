package com.gildedgames.aether.common.items.weapons.crossbow;

public enum ItemBoltType
{

	/* DAMAGES ARE NOT FINAL */
	SKYROOT(0, 0, 0, "skyroot"),
	HOLYSTONE(0, 0, 0, "holystone"),
	SCATTERGLASS(0, 0, 0, "scatterglass"),
	ZANITE(0, 0, 0, "zanite"),
	ARKENIUM(0, 0, 0, "arkenium"),
	GRAVITITE(0, 0, 0, "gravitite");

	private final int slashDamageLevel, pierceDamageLevel, impactDamageLevel;

	private final String id;

	ItemBoltType(int slashDamageLevel, int pierceDamageLevel, int impactDamageLevel, String id)
	{
		this.slashDamageLevel = slashDamageLevel;
		this.pierceDamageLevel = pierceDamageLevel;
		this.impactDamageLevel = impactDamageLevel;

		this.id = id;
	}

	public static ItemBoltType fromOrdinal(int ordinal)
	{
		ItemBoltType[] bolts = values();

		return bolts[ordinal > bolts.length || ordinal < 0 ? 0 : ordinal];
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

	public ItemBoltType getAmmoItem()
	{
		return this;
	}
}
