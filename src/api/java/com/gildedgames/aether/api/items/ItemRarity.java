package com.gildedgames.aether.api.items;

public enum ItemRarity
{
	NONE("item.rarity.none.name"),
	COMMON("item.rarity.common.name"),
	RARE("item.rarity.rare.name"),
	EPIC("item.rarity.epic.name"),
	MYTHIC("item.rarity.mythic.name"),
	GODLY("item.rarity.godly.name");

	private final String unlocalizedName;

	ItemRarity(String unlocalizedName)
	{
		this.unlocalizedName = unlocalizedName;
	}

	public String getUnlocalizedName()
	{
		return this.unlocalizedName;
	}
}
