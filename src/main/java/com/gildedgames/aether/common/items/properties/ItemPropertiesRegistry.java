package com.gildedgames.aether.common.items.properties;

import com.gildedgames.aether.api.items.properties.IItemProperties;
import com.gildedgames.aether.api.items.properties.ItemPropertiesBuilder;
import com.gildedgames.aether.api.registry.IItemPropertiesRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class ItemPropertiesRegistry implements IItemPropertiesRegistry
{
	private static final IItemProperties DEFAULT = new ItemPropertiesImmutable();

	private final Map<ResourceLocation, IItemProperties> registry = new HashMap<>();

	@Override
	@Nonnull
	public IItemProperties getProperties(Item item)
	{
		return this.registry.getOrDefault(item.getRegistryName(), ItemPropertiesRegistry.DEFAULT);
	}

	@Override
	public void registerItem(Item item, ItemPropertiesBuilder def)
	{
		Validate.isTrue(!this.registry.containsKey(item.getRegistryName()), "Properties already registered for item %s", item.getRegistryName());

		this.registry.put(item.getRegistryName(), new ItemPropertiesImmutable(def.getSlot(), def.getEffects(), def.getPreconditions(), def.getEffectActivators(), def.getRarity()));
	}
}
