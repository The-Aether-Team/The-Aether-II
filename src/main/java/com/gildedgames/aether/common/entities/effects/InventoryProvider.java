package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.function.Function;

public class InventoryProvider
{
	private ResourceLocation uniqueId;

	private Function<IPlayerAether, IInventory> inventoryFunction;

	public InventoryProvider(ResourceLocation uniqueId, Function<IPlayerAether, IInventory> inventoryFunction)
	{
		this.uniqueId = uniqueId;
		this.inventoryFunction = inventoryFunction;
	}

	public IInventory provide(IPlayerAether playerAether)
	{
		return this.inventoryFunction.apply(playerAether);
	}

	public ResourceLocation getUniqueId()
	{
		return this.uniqueId;
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().append(this.uniqueId).toHashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}

		if (obj instanceof InventoryProvider)
		{
			InventoryProvider other = (InventoryProvider) obj;

			return new EqualsBuilder().append(this.uniqueId, other.uniqueId).isEquals();
		}

		return false;
	}
}
