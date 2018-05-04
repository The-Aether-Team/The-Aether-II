package com.gildedgames.aether.api.patron;

import com.gildedgames.aether.api.util.FunctionBoolean;

public class PatronUnlocks
{
	public static FunctionBoolean<PatronDetails> hasHadTier(PatronTier tier)
	{
		return p -> p.hasEverHad(tier);
	}
}
