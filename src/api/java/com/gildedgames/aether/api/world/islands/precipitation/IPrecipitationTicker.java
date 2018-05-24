package com.gildedgames.aether.api.world.islands.precipitation;

import com.gildedgames.aether.api.world.islands.IIslandDataPartial;
import net.minecraft.world.World;

public interface IPrecipitationTicker
{
	void tick(World world, IIslandDataPartial data);
}
