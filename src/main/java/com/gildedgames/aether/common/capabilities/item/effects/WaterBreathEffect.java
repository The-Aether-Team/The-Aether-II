package com.gildedgames.aether.common.capabilities.item.effects;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
import com.gildedgames.aether.api.items.equipment.effects.IEffect;
import com.gildedgames.aether.api.items.equipment.effects.IEffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;

public class WaterBreathEffect implements IEffect<WaterBreathEffect.Provider>
{
	private static final ResourceLocation NAME = new ResourceLocation(AetherCore.MOD_ID, "water_breathing");

	@Override
	public IEffectInstance createInstance(Collection<Provider> providers)
	{
		Instance state = new Instance();

		return state;
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return WaterBreathEffect.NAME;
	}

	public static class Provider implements IEffectProvider
	{
		@Override
		public ResourceLocation getFactory()
		{
			return WaterBreathEffect.NAME;
		}
	}

	class Instance implements IEffectInstance
	{

		@Override
		public void onEntityUpdate(IPlayerAether player)
		{
			if (player.getEntity().isInWater())
			{
				player.getEntity().setAir(300); // full breath
			}
		}

		@Override
		public void addItemInformation(Collection<String> label)
		{
			label.add(TextFormatting.BLUE.toString() + TextFormatting.ITALIC.toString() + "Water Breathing");
		}
	}
}
