package com.gildedgames.aether.common.capabilities.item.effects;

import com.gildedgames.aether.api.items.equipment.effects.EffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffectFactory;
import com.gildedgames.aether.api.items.equipment.effects.IEffectPool;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class PotionEffectFactory implements IEffectFactory<PotionEffectFactory.PotionEffectProvider>
{
	private static final ResourceLocation NAME = new ResourceLocation(AetherCore.MOD_ID, "potion_effect");

	@Override
	public EffectInstance createInstance(IEffectPool<PotionEffectProvider> pool)
	{
		return new PotionEffectInstance(pool.getActiveProviders());
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return NAME;
	}

	public static class PotionEffectProvider implements IEffectProvider
	{
		private final Potion potion;

		private final int strength;

		public PotionEffectProvider(Potion potion, int strength)
		{
			this.potion = potion;
			this.strength = strength;
		}

		@Override
		public ResourceLocation getFactory()
		{
			return NAME;
		}

		@Override
		public IEffectProvider copy()
		{
			return new PotionEffectProvider(this.potion, this.strength);
		}
	}

	private class PotionEffectInstance extends EffectInstance
	{
		private final Collection<PotionEffectProvider> potions;

		private int ticksExisted = 0;

		public PotionEffectInstance(Collection<PotionEffectProvider> providers)
		{
			this.potions = new ArrayList<>(providers);
		}

		@Override
		public void onEntityUpdate(IPlayerAether player)
		{
			this.ticksExisted++;

			if (this.ticksExisted % 200 == 0)
			{
				this.addPotions(player);
			}
		}

		@Override
		public void onInstanceRemoved(IPlayerAether player)
		{
			this.potions.stream().map(provider -> provider.potion).forEach(player.getEntity()::removePotionEffect);
		}

		@Override
		public void onInstanceAdded(IPlayerAether player)
		{
			this.addPotions(player);
		}

		private void addPotions(IPlayerAether player)
		{
			this.potions.stream().map(provider -> new PotionEffect(provider.potion, 1000, provider.strength, true, false))
					.forEach(player.getEntity()::addPotionEffect);
		}

		@Override
		public void addInformation(Collection<String> label, TextFormatting format1, TextFormatting format2)
		{
			this.potions.stream()
					.map(provider -> provider.potion.getName())
					.collect(Collectors.toSet())
					.forEach(name -> label.add(TextFormatting.BLUE + "Gives " + I18n.format(name)));
		}
	}
}
