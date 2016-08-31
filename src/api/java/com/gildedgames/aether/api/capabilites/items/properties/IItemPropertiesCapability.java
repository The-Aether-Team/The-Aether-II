package com.gildedgames.aether.api.capabilites.items.properties;

import com.gildedgames.aether.api.capabilites.entity.properties.ElementalDamageSource;
import com.gildedgames.aether.api.registry.equipment.IEquipmentProperties;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * A special capability used to add equipment properties to ItemStacks.
 */
public interface IItemPropertiesCapability
{
	IEquipmentProperties getProperties();

	/**
	 * Helper method for {@link IEquipmentProperties#getRarity()}
	 * @return The rarity of this {@link ItemStack}'s item.
	 */
	ItemRarity getRarity();

	/**
	 * Helper method for {@link IEquipmentProperties#getEquipmentType()}
	 * @return The equipment type of this {@link ItemStack}'s item.
	 */
	ItemEquipmentType getEquipmentType();

	/**
	 * Helper method to determine whether or not this item can be equipped.
	 * @return True if the item can be equipped, otherwise false if there's no properties registered.
	 */
	boolean isEquippable();
}
