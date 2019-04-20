package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.travellers_guidebook.ITGEntryDefinition;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.google.common.collect.Sets;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;

/**
 * Event's System for the Travellers Guidebook
 */
public class PlayerTGModule extends PlayerAetherModule
{
	private HashSet<String> conditionsMet = Sets.newHashSet();

	public PlayerTGModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	public void flagCondition(final String conditionUniqueIdentifier)
	{
		this.conditionsMet.add(conditionUniqueIdentifier);
	}

	public boolean isConditionFlagged(final String conditionUniqueIdentifier)
	{
		return this.conditionsMet.contains(conditionUniqueIdentifier);
	}

	public boolean isEntryUnlocked(final ITGEntryDefinition entry)
	{
		for (final String condition : entry.getConditionIDs())
		{
			if (!this.isConditionFlagged(condition))
			{
				return false;
			}
		}

		return true;
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
