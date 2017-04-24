package com.gildedgames.aether.common.capabilities.item.effects;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
import com.gildedgames.aether.api.items.equipment.effects.EffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffect;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.common.AetherCore;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;

public class StatEffect implements IEffect<StatEffect.Provider>
{
	private static final ResourceLocation NAME = new ResourceLocation(AetherCore.MOD_ID, "stat_provider");

	@Override
	public EffectInstance createInstance(Collection<Provider> providers)
	{
		return new Instance(providers);
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return StatEffect.NAME;
	}

	public static class Provider implements IEffectProvider
	{
		public static final int OP_ADD = 0, OP_MULTIPLER = 1;

		private final IAttribute attribute;

		private final double amount;

		private final int opcode;

		public Provider(IAttribute attribute, double amount, int opcode)
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

	public static class Instance extends EffectInstance
	{
		private final Multimap<String, AttributeModifier> attributes;

		public Instance(Collection<StatEffect.Provider> providers)
		{
			this.attributes = MultimapBuilder.hashKeys().arrayListValues().build();

			for (StatEffect.Provider provider : providers)
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

		}
	}
}
