package com.gildedgames.aether.api.orbis;

import com.gildedgames.aether.api.util.NBT;
import net.minecraft.world.World;

import java.util.Collection;

public interface IWorldObjectManager extends NBT
{

	World getWorld();

	<T extends IWorldObjectGroup> int getID(T group);

	/**
	 * Should create a new group if one isn't present
	 * @param id
	 * @param <T>
	 * @return
	 */
	<T extends IWorldObjectGroup> T getGroup(int id);

	<T extends IWorldObjectGroup> void setGroup(int id, T object);

	/**
	 * Adds a group to the world with an auto-assigned
	 * id. Use with caution, only adding when the group is new,
	 * not when it is re-added from a loaded state.
	 * @param object
	 * @param <T>
	 */
	<T extends IWorldObjectGroup> void addGroup(T object);

	Collection<IWorldObjectGroup> getGroups();

	void addObserver(IWorldObjectManagerObserver observer);

	boolean removeObserver(IWorldObjectManagerObserver observer);

	boolean containsObserver(IWorldObjectManagerObserver observer);

}
