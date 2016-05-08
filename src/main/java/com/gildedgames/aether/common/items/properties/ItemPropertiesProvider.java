package com.gildedgames.aether.common.items.properties;

import com.gildedgames.aether.common.AetherCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemPropertiesProvider implements ICapabilityProvider
{
	private ItemPropertiesBase properties;

	private ItemStack stack;

	public ItemPropertiesProvider(ItemStack stack)
	{
		this.stack = stack;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == AetherCapabilities.ITEM_PROPERTIES && this.stack != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (this.hasCapability(capability, facing))
		{
			if (this.properties == null)
			{
				for (ItemProperties.RegistrationEntry entry : ItemProperties.getRegistrationEntries())
				{
					if (entry.getItem() == this.stack.getItem())
					{
						this.properties = new ItemProperties(entry.getRarity(), entry.getEquipmentType());

						break;
					}
				}
			}

			return (T) this.properties;
		}

		return null;
	}
}