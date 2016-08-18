package com.gildedgames.aether.api.items.properties;

public enum ItemRarity
{

	NONE("", 0),
	COMMON("item.rarity.common.name", 50),
	RARE("item.rarity.rare.name", 25),
	EPIC("item.rarity.epic.name", 5),
	MYTHIC("item.rarity.mythic.name", 3),
	GODLY("item.rarity.godly.name", 1);

	private final String unlocalizedName;

	private final int weight;

	ItemRarity(String unlocalizedName, int weight)
	{
		this.unlocalizedName = unlocalizedName;
		this.weight = weight;
	}

	public String getUnlocalizedName()
	{
		return this.unlocalizedName;
	}

	public float getWeight()
	{
		return this.weight;
	}
}
