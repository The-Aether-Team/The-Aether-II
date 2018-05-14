package com.gildedgames.aether.common.capabilities.item.effects;

import com.gildedgames.aether.api.items.equipment.effects.EffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffectFactory;
import com.gildedgames.aether.api.items.equipment.effects.IEffectPool;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;

public class InvisiblityEffectFactory implements IEffectFactory<InvisiblityEffectFactory.Provider>
{
	private static final ResourceLocation NAME = new ResourceLocation(AetherCore.MOD_ID, "invisibility");

	@Override
	public EffectInstance createInstance(IEffectPool<Provider> pool)
	{
		return new Instance();
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return InvisiblityEffectFactory.NAME;
	}

	public static class Provider implements IEffectProvider
	{
		@Override
		public ResourceLocation getFactory()
		{
			return InvisiblityEffectFactory.NAME;
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
			EntityPlayer p = player.getEntity();
			p.setInvisible(true);
		}

		@Override
		public void addInformation(Collection<String> label)
		{
			label.add(TextFormatting.BLUE.toString() + TextFormatting.ITALIC.toString() + "Invisibility");
		}
	}
}
