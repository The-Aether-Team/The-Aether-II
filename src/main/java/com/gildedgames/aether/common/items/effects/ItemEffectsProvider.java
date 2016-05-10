package com.gildedgames.aether.common.items.effects;

import com.gildedgames.aether.common.AetherCapabilities;
import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectProcessor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;

public class ItemEffectsProvider implements ICapabilityProvider
{
	private ItemEffectsBase effects;

	private ItemStack stack;

	public ItemEffectsProvider(ItemStack stack)
	{
		this.stack = stack;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == AetherCapabilities.ITEM_EFFECTS && this.stack != null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (this.hasCapability(capability, facing))
		{
			if (this.effects == null)
			{
				for (ItemEffects.RegistrationEntry entry : ItemEffects.getRegistrationEntries())
				{
					if (entry.getItem() == this.stack.getItem())
					{
						List<Pair<EffectProcessor, EffectInstance>> emptyList = Collections.emptyList();

						this.effects = new ItemEffects(entry.getEffectsProvider() == null ? emptyList : entry.getEffectsProvider().provide());

						break;
					}
				}
			}

			return (T) this.effects;
		}

		return null;
	}

}
