package com.gildedgames.aether.api.capabilites.instances;

import com.gildedgames.aether.api.util.NBT;
import com.google.common.collect.BiMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import java.util.Collection;

public interface IInstanceHandler<T extends Instance> extends NBT
{
	T getInstance(int id);

	T createNew();

	void sendUnregisterInstancesPacket(EntityPlayerMP player);

	void unregisterInstances();

	T getInstanceForDimension(int id);

	int getDimensionForInstance(Instance instance);

	World getWorldForInstance(Instance instance);

	int getInstancesSize();

	Collection<T> getInstances();

	BiMap<Integer, T> getInstancesMap();

	World teleportPlayerToDimension(T instance, EntityPlayerMP player);

	void teleportPlayerOutsideInstance(EntityPlayerMP player);
}
