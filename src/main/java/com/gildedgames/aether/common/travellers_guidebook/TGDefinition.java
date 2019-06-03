package com.gildedgames.aether.common.travellers_guidebook;

import com.gildedgames.aether.api.player.conditions.ConditionResolution;
import com.gildedgames.aether.api.player.conditions.IPlayerCondition;
import com.gildedgames.aether.api.travellers_guidebook.ITGDefinition;
import com.gildedgames.aether.api.travellers_guidebook.ITGEntry;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;

public class TGDefinition implements ITGDefinition
{
	@SerializedName("conditions")
	private final Collection<IPlayerCondition> conditions;

	@SerializedName("conditionResolution")
	private final ConditionResolution conditionResolution;

	@SerializedName("entries")
	private final Map<String, ITGEntry> entries;

	public TGDefinition(final Collection<IPlayerCondition> conditions, final ConditionResolution conditionResolution,
			final Map<String, ITGEntry> entries)
	{
		this.conditions = conditions;
		this.conditionResolution = conditionResolution;
		this.entries = entries;
	}

	@Nonnull
	@Override
	public Collection<IPlayerCondition> conditions()
	{
		return this.conditions;
	}

	@Nonnull
	@Override
	public ConditionResolution conditionResolution()
	{
		return this.conditionResolution;
	}

	@Nonnull
	@Override
	public Map<String, ITGEntry> entries()
	{
		return this.entries;
	}
}
