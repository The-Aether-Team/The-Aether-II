package com.gildedgames.aether.api.cache;

import net.minecraft.util.ResourceLocation;

public interface IEntityStatsCache
{
	IEntityStats getStats(final ResourceLocation entityId);
}
