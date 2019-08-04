package com.gildedgames.aether.common.travellers_guidebook;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.player.conditions.IConditionResolution;
import com.gildedgames.aether.api.player.conditions.IPlayerCondition;
import com.gildedgames.aether.api.travellers_guidebook.ITGEntry;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerConditionModule;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Collections;

public abstract class TGEntryBase implements ITGEntry
{
	private Collection<ResourceLocation> conditionIDs;

	private IConditionResolution conditionResolution;

	public TGEntryBase()
	{

	}

	@Override
	public Collection<ResourceLocation> getConditionIDs()
	{
		return this.conditionIDs;
	}

	@Override
	public void setConditionIDs(final Collection<ResourceLocation> conditionIDs)
	{
		this.conditionIDs = conditionIDs;
	}

	@Override
	public Collection<IPlayerCondition> providePlayerConditions()
	{
		return Collections.emptyList();
	}

	@Override
	public IConditionResolution getConditionResolution()
	{
		return this.conditionResolution;
	}

	@Override
	public void setConditionResolution(final IConditionResolution conditionResolution)
	{
		this.conditionResolution = conditionResolution;
	}

	@Override
	public boolean isUnlocked(final IPlayerAether playerAether)
	{
		return playerAether.getModule(PlayerConditionModule.class).isEntryUnlocked(this);
	}
}
