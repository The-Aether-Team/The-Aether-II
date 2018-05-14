package com.gildedgames.aether.api.world;

import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public interface ISpawnHandler
{
	void init(World world);

	String getUniqueID();

	ISpawnHandler targetEntityCountPerArea(int targetEntityCountPerArea);

	ISpawnHandler chunkArea(int chunkArea);

	ISpawnHandler updateFrequencyInTicks(int updateFrequencyInTicks);

	<T extends WorldCondition> ISpawnHandler addWorldCondition(T condition);

	<T extends PosCondition> ISpawnHandler condition(T condition);

	void addEntry(ISpawnEntry entry);

	void tick();

	int getChunkArea();

	void onLivingDeath(LivingDeathEvent event);
}
