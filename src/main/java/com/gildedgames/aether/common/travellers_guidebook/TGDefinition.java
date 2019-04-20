package com.gildedgames.aether.common.travellers_guidebook;

import com.gildedgames.aether.api.travellers_guidebook.ITGCondition;
import com.gildedgames.aether.api.travellers_guidebook.ITGDefinition;
import com.gildedgames.aether.api.travellers_guidebook.ITGEntryDefinition;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;

public class TGDefinition implements ITGDefinition
{
	@SerializedName("conditions")
	private final Collection<ITGCondition> conditions;

	@SerializedName("entries")
	private final Map<String, ITGEntryDefinition> entries;

	public TGDefinition(final Collection<ITGCondition> conditions, final Map<String, ITGEntryDefinition> entries)
	{
		this.conditions = conditions;
		this.entries = entries;
	}

	@Nonnull
	@Override
	public Collection<ITGCondition> conditions()
	{
		return this.conditions;
	}

	@Nonnull
	@Override
	public Map<String, ITGEntryDefinition> entries()
	{
		return this.entries;
	}
}
