package com.gildedgames.aether.common.capabilities.item.effects.stats;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
import com.gildedgames.aether.api.items.equipment.effects.EffectHelper;
import com.gildedgames.aether.api.items.equipment.effects.EffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffect;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
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

public class StatEffect implements IEffect<StatEffect.StatProvider>
{
	private static final ResourceLocation NAME = new ResourceLocation(AetherCore.MOD_ID, "stat_provider");

	private static final DecimalFormat POINT_FORMATTER = new DecimalFormat("#.#");

	@Override
	public EffectInstance createInstance(Collection<StatProvider> providers)
	{
		return new StatInstance(providers);
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return StatEffect.NAME;
	}

	public static class StatProvider implements IEffectProvider
	{
		public static final int OP_ADD = 0, OP_MULTIPLY = 1;

		private final IAttribute attribute;

		private final double amount;

		private final int opcode;

		public StatProvider(IAttribute attribute, double amount, int opcode)
		{
			this.attribute = attribute;
			this.amount = amount;
			this.opcode = opcode;
		}

		@Override
		public ResourceLocation getFactory()
		{
			return StatEffect.NAME;
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
				AttributeModifier modifier = new AttributeModifier("Equipment modifier", provider.amount, provider.opcode);
				modifier.setSaved(false);

				this.attributes.put(provider.attribute.getName(), modifier);
			}
		}

		@Override
		public void onInstanceRemoved(IPlayerAether player)
		{
			if (player.getEntity().world.isRemote)
			{
				return;
			}

			player.getEntity().getAttributeMap().removeAttributeModifiers(this.attributes);
		}

		@Override
		public void onInstanceAdded(IPlayerAether player)
		{
			if (player.getEntity().world.isRemote)
			{
				return;
			}

			player.getEntity().getAttributeMap().applyAttributeModifiers(this.attributes);
		}

		@Override
		public void addItemInformation(Collection<String> label)
		{
			for (String name : this.attributes.keySet())
			{
				Multimap<Integer, AttributeModifier> mods = MultimapBuilder.hashKeys().arrayListValues().build();

				for (AttributeModifier mod : this.attributes.get(name))
				{
					mods.put(mod.getOperation(), mod);
				}

				for (Integer opcode : mods.keySet())
				{
					double value = EffectHelper.combineDouble(mods.get(opcode), AttributeModifier::getAmount);

					String prefix = value > 0 ? TextFormatting.BLUE + "+" : TextFormatting.RED + "-";
					String desc = I18n.format("attribute.name." + name);

					if (opcode == 1)
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
