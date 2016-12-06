package com.gildedgames.aether.common.items;

public enum ItemAbilityType
{
	/** The item doesn't have any abilities. */
	NONE,

	/** The item has an ability, but doesn't require a special button. */
	PASSIVE,

	/** The item has an ability, but requires a special button. */
	ACTIVE;

	public boolean isPassive()
	{
		return this != ItemAbilityType.ACTIVE;
	}
}
