package com.gildedgames.aether.common.capabilities.item;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAetherCapability;
import com.gildedgames.aether.api.items.equipment.effects.EffectHelper;
import com.gildedgames.aether.api.items.equipment.effects.IEffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProcessor;
import com.gildedgames.aether.api.items.equipment.effects.IEffectState;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Random;

public class DebugEffectProcessor
		implements IEffectProcessor<DebugEffectProcessor.Instance, DebugEffectProcessor.State>
{
	private static final DecimalFormat FORMATTER = new DecimalFormat("#.##");

	private static final ResourceLocation NAME = new ResourceLocation("aether", "debug");

	@Override
	public void onEntityUpdate(IPlayerAetherCapability player, State state)
	{
		for (int i = 0; i < state.points; i++)
		{
			Random random = player.getPlayer().getEntityWorld().rand;

			double x = player.getPlayer().posX + random.nextDouble() + -0.5D;
			double y = player.getPlayer().posY + random.nextDouble() + 0.5D;
			double z = player.getPlayer().posZ + random.nextDouble() + -0.5D;

			player.getPlayer().getEntityWorld().spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
		}

		AetherCore.LOGGER.info("Ticks running: {}", state.ticksRunning++);
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return DebugEffectProcessor.NAME;
	}

	@Override
	public IEffectState createState(Collection<Instance> instances)
	{
		State state = new State();
		state.points = EffectHelper.combineDouble(instances, instance -> instance.points);
		state.ticksRunning = 0;

		return state;
	}

	@Override
	public void addItemInformation(Collection<String> label, State state)
	{
		label.add(TextFormatting.YELLOW.toString() + TextFormatting.ITALIC.toString() +
				"+" + FORMATTER.format(state.points) + " Debug Points");
	}

	public static class Instance implements IEffectInstance
	{
		private final double points;

		public Instance(double points)
		{
			this.points = points;
		}

		@Override
		public ResourceLocation getProcessor()
		{
			return DebugEffectProcessor.NAME;
		}
	}

	class State implements IEffectState
	{
		private double points;

		private int ticksRunning;
	}
}
