package com.gildedgames.aether.common.capabilities.item.effects;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
import com.gildedgames.aether.api.items.equipment.effects.IEffect;
import com.gildedgames.aether.api.items.equipment.effects.IEffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;

public class BaseEffect implements IEffect<BaseEffect.Provider>
{
	@Override
	public IEffectInstance createInstance(Collection<Provider> providers)
	{
		return null;
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return null;
	}

	public static class Provider implements IEffectProvider
	{

		@Override
		public ResourceLocation getFactory()
		{
			return null;
		}
	}

	class Instance implements IEffectInstance
	{

		@Override
		public void onEntityUpdate(IPlayerAether player)
		{
			
		}

		@Override
		public void addItemInformation(Collection<String> label)
		{

		}
	}
}
