package com.gildedgames.aether.api.world.spawn;

import com.gildedgames.aether.api.world.spawn.conditions.IConditionPosition;
import com.gildedgames.aether.api.world.spawn.conditions.IConditionWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public interface ISpawnHandler
{
	void init(World world);

	String getUniqueID();

	ISpawnHandler targetEntityCountPerArea(int targetEntityCountPerArea);

	ISpawnHandler chunkArea(int chunkArea);

	ISpawnHandler updateFrequencyInTicks(int updateFrequencyInTicks);

	<T extends IConditionWorld> ISpawnHandler addWorldCondition(T condition);

	<T extends IConditionPosition> ISpawnHandler condition(T condition);

	void addEntry(ISpawnEntry entry);

	void tick();

	int getChunkArea();

	void onLivingDeath(LivingDeathEvent event);
}
