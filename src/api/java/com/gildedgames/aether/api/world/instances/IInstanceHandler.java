package com.gildedgames.aether.api.world.instances;

import com.gildedgames.orbis.api.util.mc.NBT;
import com.google.common.collect.BiMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import java.util.Collection;

public interface IInstanceHandler<T extends IInstance> extends NBT
{
	T getInstance(int id);

	T createNew();

	void sendUnregisterInstancesPacket(EntityPlayerMP player);

	void unregisterInstances();

	T getInstanceForDimension(int id);

	int getDimensionForInstance(IInstance instance);

	World getWorldForInstance(IInstance instance);

	int getInstancesSize();

	Collection<T> getInstances();

	BiMap<Integer, T> getInstancesMap();

	World teleportPlayerToDimension(T instance, EntityPlayerMP player);

	void teleportPlayerOutsideInstance(EntityPlayerMP player);
}
