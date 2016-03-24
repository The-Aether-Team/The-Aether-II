package com.gildedgames.aether.common.items;

public enum ItemRarity
{

	NONE("", 1.0F),
	COMMON("item.rarity.common.name", 1.0F),
	RARE("item.rarity.rare.name", 1.0F),
	EPIC("item.rarity.epic.name", 1.0F),
	MYTHIC("item.rarity.mythic.name", 1.0F),
	GODLY("item.rarity.godly.name", 1.0F);

	private final String unlocalizedName;

	private final float weight;

	ItemRarity(String unlocalizedName, float weight)
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
