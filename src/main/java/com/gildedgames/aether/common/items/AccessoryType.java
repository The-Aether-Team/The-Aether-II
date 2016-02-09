package com.gildedgames.aether.common.items;

public enum AccessoryType
{
	RING("gui.slot.ring"),
	NECKWEAR("gui.slot.neckwear"),
	COMPANION("gui.slot.companion"),
	SHIELD("gui.slot.shield"),
	GLOVE("gui.slot.glove"),
	MISC("gui.slot.misc");

	private final String unlocalizedName;

	AccessoryType(String unlocalizedName)
	{
		this.unlocalizedName = unlocalizedName;
	}

	public String getUnlocalizedName()
	{
		return this.unlocalizedName;
	}
}
