package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.player.IPlayerConditionModule;
import com.gildedgames.aether.api.player.conditions.IConditionResolution;
import com.gildedgames.aether.api.travellers_guidebook.ITGEntry;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.google.common.collect.Sets;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;
import java.util.function.Function;

/**
 * Event's System for the Travellers Guidebook
 */
public class PlayerConditionModule extends PlayerAetherModule implements IPlayerConditionModule
{
	private HashSet<ResourceLocation> conditionsMet = Sets.newHashSet();

	private final Function<ResourceLocation, Boolean> isConditionMet = this::isConditionFlagged;

	public PlayerConditionModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	@Override
	public void flagCondition(final ResourceLocation conditionUniqueIdentifier)
	{
		this.conditionsMet.add(conditionUniqueIdentifier);
	}

	@Override
	public boolean isConditionFlagged(final ResourceLocation conditionUniqueIdentifier)
	{
		return this.conditionsMet.contains(conditionUniqueIdentifier);
	}

	@Override
	public boolean areConditionsFlagged(final IConditionResolution conditionResolution, final ResourceLocation... conditionUniqueIdentifiers)
	{
		return conditionResolution.areConditionsMet(conditionUniqueIdentifiers, this.isConditionMet);
	}

	public boolean isEntryUnlocked(final ITGEntry entry)
	{
		return this.areConditionsFlagged(entry.getConditionResolution(), entry.getConditionIDs().toArray(new ResourceLocation[0]));
	}

	@Override
	public void tickStart(final TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void tickEnd(final TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void onUpdate()
	{

	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		funnel.setSet("conditionsMet", this.conditionsMet, NBTFunnel.RESOURCE_SETTER);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		this.conditionsMet = Sets.newHashSet(funnel.getSet("conditionsMet", NBTFunnel.RESOURCE_GETTER));
	}
}
