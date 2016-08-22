package com.gildedgames.aether.api.capabilites.items.properties;

public enum ItemEquipmentType
{
	RING("gui.aether.slot.ring"),
	NECKWEAR("gui.aether.slot.neckwear"),
	COMPANION("gui.aether.slot.companion"),
	HANDWEAR("gui.aether.slot.handwear"),
	RELIC("gui.aether.slot.relic"),
	CHARM("gui.aether.slot.charm"),
	ARTIFACT("gui.aether.slot.artifact");

	private final String unlocalizedName;

	ItemEquipmentType(String unlocalizedName)
	{
		this.unlocalizedName = unlocalizedName;
	}

	public String getUnlocalizedName()
	{
		return this.unlocalizedName;
	}
}
