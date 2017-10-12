package com.gildedgames.aether.api.orbis;

public interface IWorldObjectGroupObserver
{

	void onObjectAdded(IWorldObjectGroup group, IWorldObject object);

	void onObjectRemoved(IWorldObjectGroup group, IWorldObject object);

	void onReloaded(IWorldObjectGroup group);

}
