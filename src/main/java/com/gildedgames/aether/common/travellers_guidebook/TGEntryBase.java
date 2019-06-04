package com.gildedgames.aether.common.travellers_guidebook;

import com.gildedgames.aether.api.player.conditions.IConditionResolution;
import com.gildedgames.aether.api.player.conditions.IPlayerCondition;
import com.gildedgames.aether.api.travellers_guidebook.ITGEntry;

import java.util.Collection;
import java.util.Collections;

public abstract class TGEntryBase implements ITGEntry
{
	private Collection<String> conditionIDs;

	private IConditionResolution conditionResolution;

	public TGEntryBase()
	{

	}

	@Override
	public Collection<String> getConditionIDs()
	{
		return this.conditionIDs;
	}

	@Override
	public void setConditionIDs(final Collection<String> conditionIDs)
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
}
