package com.gildedgames.aether.common.capabilities.item.effects;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
import com.gildedgames.aether.api.items.equipment.effects.EffectHelper;
import com.gildedgames.aether.api.items.equipment.effects.IEffect;
import com.gildedgames.aether.api.items.equipment.effects.EffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Random;

public class DebugEffect implements IEffect<DebugEffect.Provider>
{
	private static final DecimalFormat FORMATTER = new DecimalFormat("#.##");

	private static final ResourceLocation NAME = new ResourceLocation(AetherCore.MOD_ID, "debug");

	@Override
	public EffectInstance createInstance(Collection<Provider> providers)
	{
		Instance state = new Instance();
		state.points = EffectHelper.combineDouble(providers, instance -> instance.points);
		state.ticksRunning = 0;

		return state;
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return DebugEffect.NAME;
	}

	public static class Provider implements IEffectProvider
	{
		private final double points;

		public Provider(double points)
		{
			this.points = points;
		}

		@Override
		public ResourceLocation getFactory()
		{
			return DebugEffect.NAME;
		}
	}

	private class Instance extends EffectInstance
	{
		private double points;

		private int ticksRunning;

		@Override
		public void onEntityUpdate(IPlayerAether player)
		{
			for (int i = 0; i < this.points; i++)
			{
				Random random = player.getEntity().getEntityWorld().rand;

				double x = player.getEntity().posX + random.nextDouble() + -0.5D;
				double y = player.getEntity().posY + random.nextDouble() + 0.5D;
				double z = player.getEntity().posZ + random.nextDouble() + -0.5D;

				player.getEntity().getEntityWorld().spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
			}

			AetherCore.LOGGER.info("Ticks running: {}", this.ticksRunning++);
		}

		@Override
		public void addItemInformation(Collection<String> label)
		{
			label.add(TextFormatting.YELLOW.toString() + TextFormatting.ITALIC.toString() +
					"+" + FORMATTER.format(this.points) + " Debug Points");
		}
	}
}
