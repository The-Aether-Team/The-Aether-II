package com.gildedgames.aether.common.capabilities.item.effects;

import com.gildedgames.aether.api.items.equipment.effects.*;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.text.DecimalFormat;
import java.util.Collection;

public class RegenerationEffectFactory implements IEffectFactory<RegenerationEffectFactory.Provider>
{
	private static final DecimalFormat FORMATTER = new DecimalFormat("#.##");

	private static final ResourceLocation NAME = new ResourceLocation(AetherCore.MOD_ID, "regeneration");

	@Override
	public EffectInstance createInstance(IEffectPool<Provider> pool)
	{
		RegenerationEffectInstance state = new RegenerationEffectInstance();
		state.healAmount = EffectHelper.combineInt(pool.getActiveProviders(), instance -> instance.heal);

		return state;
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return RegenerationEffectFactory.NAME;
	}

	public static class Provider implements IEffectProvider
	{
		private final int heal;

		public Provider(int heal)
		{
			this.heal = heal;
		}

		@Override
		public ResourceLocation getFactory()
		{
			return RegenerationEffectFactory.NAME;
		}

		@Override
		public IEffectProvider copy()
		{
			return new Provider(this.heal);
		}
	}

	private class RegenerationEffectInstance extends EffectInstance
	{
		private int healAmount;

		private int ticksUntilHeal = 120;

		@Override
		public void onEntityUpdate(IPlayerAether player)
		{
			if (player.getEntity().hurtTime > 0)
			{
				this.ticksUntilHeal = 120;
				return;
			}

			if (this.ticksUntilHeal <= 0)
			{
				player.getEntity().heal(this.healAmount);

				this.ticksUntilHeal = 20;
			}

			this.ticksUntilHeal--;
		}

		@Override
		public void addInformation(Collection<String> label)
		{
			label.add(TextFormatting.RED.toString() + TextFormatting.ITALIC.toString() +
					"+" + FORMATTER.format(this.healAmount) + " Regeneration per Second");
		}
	}
}
