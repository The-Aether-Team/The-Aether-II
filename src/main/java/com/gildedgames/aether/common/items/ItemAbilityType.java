package com.gildedgames.aether.common.items;

public enum ItemAbilityType
{
	/** The item doesn't have any abilities. */
	NONE,

	/** The item has an ability, but doesn't require a special action. */
	PASSIVE,

	/** The item has an ability, but requires a special action. */
	ACTIVE;

	public boolean isPassive()
	{
		return this != ItemAbilityType.ACTIVE;
	}
}
