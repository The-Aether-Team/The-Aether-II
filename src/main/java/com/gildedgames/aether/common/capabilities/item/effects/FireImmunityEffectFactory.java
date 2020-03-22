package com.gildedgames.aether.common.capabilities.item.effects;

import com.gildedgames.aether.api.items.equipment.effects.EffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffectFactory;
import com.gildedgames.aether.api.items.equipment.effects.IEffectPool;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;

public class FireImmunityEffectFactory implements IEffectFactory<FireImmunityEffectFactory.Provider>
{
	private static final ResourceLocation NAME = new ResourceLocation(AetherCore.MOD_ID, "fire_immunity");

	@Override
	public EffectInstance createInstance(IEffectPool<Provider> pool)
	{
		return new Instance();
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return FireImmunityEffectFactory.NAME;
	}

	public static class Provider implements IEffectProvider
	{
		@Override
		public ResourceLocation getFactory()
		{
			return FireImmunityEffectFactory.NAME;
		}

		@Override
		public IEffectProvider copy()
		{
			return new Provider();
		}
	}

	private class Instance extends EffectInstance
	{

		@Override
		public void onEntityUpdate(IPlayerAether player)
		{
			player.getEntity().extinguish();
		}

		@Override
		public void addInformation(Collection<String> label, TextFormatting format1, TextFormatting format2)
		{
			label.add(TextFormatting.RED.toString() + TextFormatting.ITALIC.toString() + "Fire Immunity");
		}
	}
}
