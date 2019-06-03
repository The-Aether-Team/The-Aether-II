package com.gildedgames.aether.api.travellers_guidebook;

import com.gildedgames.aether.api.player.conditions.ConditionResolution;
import com.gildedgames.aether.api.player.conditions.IPlayerCondition;

import java.util.Collection;

public interface ITGEntryDefinition
{
	/**
	 * Called by the TGManager giving this entry the opportunity to
	 * populate and manually register player conditions that are used
	 * for sub data within this entry.
	 *
	 * This is especially useful if you need to transform any data
	 * detailed by the json into extra entries, instead of getting
	 * the designer to manually fill those entries out and understand
	 * the intricate relationships between them.
	 *
	 * Should return Collections.emptyList() if this entry does not want
	 * any sub player conditions.
	 * @return The player conditions this entry wants to register.
	 */
	Collection<IPlayerCondition> providePlayerConditions();

	String tag();

	ConditionResolution getConditionResolution();

	void setConditionResolution(ConditionResolution conditionResolution);

	Collection<String> getConditionIDs();

	void setConditionIDs(Collection<String> conditionIDs);
}
