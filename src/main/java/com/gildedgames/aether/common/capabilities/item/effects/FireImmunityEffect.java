package com.gildedgames.aether.common.capabilities.item.effects;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
import com.gildedgames.aether.api.items.equipment.effects.IEffect;
import com.gildedgames.aether.api.items.equipment.effects.IEffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.common.AetherCore;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Collection;

public class FireImmunityEffect implements IEffect<FireImmunityEffect.Provider>
{

	private static final ResourceLocation NAME = new ResourceLocation(AetherCore.MOD_ID, "fire_immunity");

	@Override
	public IEffectInstance createInstance(Collection<Provider> providers)
	{
		Instance state = new Instance();

		return state;
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return FireImmunityEffect.NAME;
	}

	public static class Provider implements IEffectProvider
	{

		@Override
		public ResourceLocation getFactory()
		{
			return FireImmunityEffect.NAME;
		}
	}

	class Instance implements IEffectInstance
	{

		@Override
		public void onEntityUpdate(IPlayerAether player)
		{
			player.getEntity().extinguish();
		}

		@Override
		public void addItemInformation(Collection<String> label)
		{
			label.add(TextFormatting.RED.toString() + TextFormatting.ITALIC.toString() + "Fire Immunity");
		}
	}
}
