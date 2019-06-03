package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.player.IPlayerConditionModule;
import com.gildedgames.aether.api.player.conditions.ConditionResolution;
import com.gildedgames.aether.api.travellers_guidebook.ITGEntry;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.google.common.collect.Sets;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;
import java.util.function.Function;

/**
 * Event's System for the Travellers Guidebook
 */
public class PlayerConditionModule extends PlayerAetherModule implements IPlayerConditionModule
{
	private HashSet<String> conditionsMet = Sets.newHashSet();

	private final Function<String, Boolean> isConditionMet = this::isConditionFlagged;

	public PlayerConditionModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	@Override
	public void flagCondition(final String conditionUniqueIdentifier)
	{
		this.conditionsMet.add(conditionUniqueIdentifier);
	}

	@Override
	public boolean isConditionFlagged(final String conditionUniqueIdentifier)
	{
		return this.conditionsMet.contains(conditionUniqueIdentifier);
	}

	@Override
	public boolean areConditionsFlagged(final ConditionResolution conditionResolution, final String... conditionUniqueIdentifiers)
	{
		return conditionResolution.areConditionsMet(conditionUniqueIdentifiers, this.isConditionMet);
	}

	public boolean isEntryUnlocked(final ITGEntry entry)
	{
		return this.areConditionsFlagged(entry.getConditionResolution(), entry.getConditionIDs().toArray(new String[0]));
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

		funnel.setSet("conditionsMet", this.conditionsMet, NBTFunnel.STRING_SETTER);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		this.conditionsMet = Sets.newHashSet(funnel.getSet("conditionsMet", NBTFunnel.STRING_GETTER));
	}
}
