package com.gildedgames.aether.api.world;

import com.gildedgames.orbis.lib.world.data.IWorldData;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.Collection;

public interface ISpawnAreaManager extends IWorldData
{
	World getWorld();

	void tick();

	Collection<ISpawnArea> getLoaded();

	ISpawnArea getLoadedArea(int areaX, int areaZ);

	void onLivingDeath(LivingDeathEvent event);
}
