package com.gildedgames.aether.api.player.conditions;

import java.util.function.Function;

public enum ConditionResolution
{
	REQUIRE_ALL()
			{
				@Override
				public boolean areConditionsMet(final String[] conditionIDs, final Function<String, Boolean> isConditionMet)
				{
					for (final String condition : conditionIDs)
					{
						if (!isConditionMet.apply(condition))
						{
							return false;
						}
					}

					return true;
				}
			}, REQUIRE_ANY()
		{
			@Override
			public boolean areConditionsMet(final String[] conditionIDs, final Function<String, Boolean> isConditionMet)
			{
				for (final String condition : conditionIDs)
				{
					if (isConditionMet.apply(condition))
					{
						return true;
					}
				}

				return false;
			}
		};

	ConditionResolution()
	{

	}

	public abstract boolean areConditionsMet(String[] conditionIDs, Function<String, Boolean> isConditionMet);
}
