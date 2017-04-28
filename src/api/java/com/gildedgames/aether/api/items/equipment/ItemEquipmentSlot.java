package com.gildedgames.aether.api.items.equipment;

/**
 * The possible equipment slots for an item.
 */
public enum ItemEquipmentSlot
{
	NONE("gui.aether.slot.none"),
	RING("gui.aether.slot.ring"),
	NECKWEAR("gui.aether.slot.neckwear"),
	COMPANION("gui.aether.slot.companion"),
	HANDWEAR("gui.aether.slot.handwear"),
	RELIC("gui.aether.slot.relic"),
	CHARM("gui.aether.slot.charm"),
	ARTIFACT("gui.aether.slot.artifact"),
	OFFHAND("gui.aether.slot.offhand");

	private final String unlocalizedName;

	ItemEquipmentSlot(String unlocalizedName)
	{
		this.unlocalizedName = unlocalizedName;
	}

	/**
	 * @return Returns the unlocalized name of the the slot.
	 */
	public String getUnlocalizedName()
	{
		return this.unlocalizedName;
	}
}
