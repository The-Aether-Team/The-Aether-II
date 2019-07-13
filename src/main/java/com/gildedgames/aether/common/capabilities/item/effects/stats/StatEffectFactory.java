package com.gildedgames.aether.common.capabilities.item.effects.stats;

import com.gildedgames.aether.api.items.equipment.effects.*;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.text.DecimalFormat;
import java.util.Collection;

public class StatEffectFactory implements IEffectFactory<StatEffectFactory.StatProvider>
{
	private static final ResourceLocation NAME = new ResourceLocation(AetherCore.MOD_ID, "stat_provider");

	private static final DecimalFormat POINT_FORMATTER = new DecimalFormat("#.#");

	@Override
	public EffectInstance createInstance(IEffectPool<StatProvider> pool)
	{
		return new StatInstance(pool.getActiveProviders());
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return StatEffectFactory.NAME;
	}

	public static class StatProvider implements IEffectProvider
	{
		public static final int OP_ADD = 0, OP_MULTIPLY = 1;

		private final IAttribute attribute;

		private final double amount;

		private final AttributeModifier.Operation operation;

		public StatProvider(IAttribute attribute, double amount, AttributeModifier.Operation operation)
		{
			this.attribute = attribute;
			this.amount = amount;
			this.operation = operation;
		}

		@Override
		public ResourceLocation getFactory()
		{
			return StatEffectFactory.NAME;
		}

		@Override
		public IEffectProvider copy()
		{
			return new StatProvider(this.attribute, this.amount, this.operation);
		}
	}

	public static class StatInstance extends EffectInstance
	{
		private final Multimap<String, AttributeModifier> attributes;

		public StatInstance(Collection<StatProvider> providers)
		{
			this.attributes = MultimapBuilder.hashKeys().arrayListValues().build();

			for (StatProvider provider : providers)
			{
				AttributeModifier modifier = new AttributeModifier("Equipment modifier", provider.amount, provider.operation);
				modifier.setSaved(false);

				this.attributes.put(provider.attribute.getName(), modifier);
			}
		}

		@Override
		public void onInstanceRemoved(IPlayerAether player)
		{
			player.getEntity().getAttributes().removeAttributeModifiers(this.attributes);
		}

		@Override
		public void onInstanceAdded(IPlayerAether player)
		{
			player.getEntity().getAttributes().applyAttributeModifiers(this.attributes);
		}

		@Override
		public void addInformation(Collection<String> label)
		{
			for (String name : this.attributes.keySet())
			{
				Multimap<AttributeModifier.Operation, AttributeModifier> mods = MultimapBuilder.hashKeys().arrayListValues().build();

				for (AttributeModifier mod : this.attributes.get(name))
				{
					mods.put(mod.getOperation(), mod);
				}

				for (AttributeModifier.Operation op : mods.keySet())
				{
					double value = EffectHelper.combineDouble(mods.get(op), AttributeModifier::getAmount);

					String prefix = value > 0 ? TextFormatting.BLUE + "+" : TextFormatting.RED + "-";
					String desc = I18n.format("attribute.name." + name);

					if (op == AttributeModifier.Operation.ADDITION)
					{
						label.add(prefix + POINT_FORMATTER.format(Math.abs(value) * 100.0D) + "% " + desc);
					}
					else
					{
						label.add(prefix + POINT_FORMATTER.format(Math.abs(value)) + " " + desc);
					}
				}
			}
		}
	}
}
