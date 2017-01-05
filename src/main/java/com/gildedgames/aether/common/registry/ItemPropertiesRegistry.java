package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.items.IItemProperties;
import com.gildedgames.aether.api.items.equipment.IEquipmentProperties;
import com.gildedgames.aether.api.registry.IItemPropertiesRegistry;
import com.gildedgames.aether.api.items.ItemProperties;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ItemPropertiesRegistry implements IItemPropertiesRegistry
{
	private static final IItemProperties DEFAULT = new ItemProperties(null, null);

	private final Map<Item, IItemProperties> registry = new HashMap<>();

	@Override
	@Nonnull
	public IItemProperties getProperties(Item item)
	{
		return this.registry.getOrDefault(item, ItemPropertiesRegistry.DEFAULT);
	}

	@Nonnull
	@Override
	public Optional<IEquipmentProperties> getEquipmentProperties(Item item)
	{
		return this.getProperties(item).getEquipmentProperties();
	}

	@Override
	public void registerItem(Item item, IItemProperties def)
	{
		this.registry.put(item, def);
	}
}
