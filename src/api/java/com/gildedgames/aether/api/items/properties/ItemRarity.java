package com.gildedgames.aether.api.items.properties;

public enum ItemRarity
{
	NONE("", 1.0F),
	COMMON("item.rarity.common.localizedName", 1.0F),
	RARE("item.rarity.rare.localizedName", 1.0F),
	EPIC("item.rarity.epic.localizedName", 1.0F),
	MYTHIC("item.rarity.mythic.localizedName", 1.0F),
	GODLY("item.rarity.godly.localizedName", 1.0F);

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
