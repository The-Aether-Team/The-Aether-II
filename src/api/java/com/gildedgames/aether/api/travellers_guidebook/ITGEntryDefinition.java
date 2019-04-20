package com.gildedgames.aether.api.travellers_guidebook;

import java.util.List;

public interface ITGEntryDefinition
{
	String tag();

	List<String> getConditionIDs();

	void setConditionIDs(List<String> conditionIDs);
}
