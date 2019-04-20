package com.gildedgames.aether.common.travellers_guidebook;

import com.gildedgames.aether.api.travellers_guidebook.ITGEntryDefinition;

import java.util.List;

public abstract class TGEntryDefinitionBase implements ITGEntryDefinition
{
	private List<String> conditionIDs;

	public TGEntryDefinitionBase()
	{

	}

	@Override
	public List<String> getConditionIDs()
	{
		return this.conditionIDs;
	}

	@Override
	public void setConditionIDs(final List<String> conditionIDs)
	{
		this.conditionIDs = conditionIDs;
	}
}
