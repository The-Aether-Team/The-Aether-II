package com.gildedgames.aether.common.items;

public enum ItemEquipmentType
{

	RING("gui.slot.ring"),
	NECKWEAR("gui.slot.neckwear"),
	COMPANION("gui.slot.companion"),
	SHIELD("gui.slot.shield"),
	HANDWEAR("gui.slot.handwear"),
	RELIC("gui.slot.relic"),
	CHARM("gui.slot.charm"),
	ARTIFACT("gui.slot.artifact"),
	AMMUNITION("gui.slot.ammunition");
	
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
