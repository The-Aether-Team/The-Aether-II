package com.gildedgames.aether.api.patron;

import com.gildedgames.aether.api.net.data.UserFeatures;
import com.gildedgames.aether.api.util.FunctionBoolean;

public class PatronUnlocks
{
	public static FunctionBoolean<UserFeatures> hasSkin(String name)
	{
		return p -> p.skins.contains(name);
	}
}
