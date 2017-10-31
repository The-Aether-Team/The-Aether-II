package com.gildedgames.orbis.common.world_object;

public interface IWorldObjectGroupObserver
{

	void onObjectAdded(IWorldObjectGroup group, IWorldObject object);

	void onObjectRemoved(IWorldObjectGroup group, IWorldObject object);

	void onReloaded(IWorldObjectGroup group);

}
