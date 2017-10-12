package com.gildedgames.aether.api.orbis;

public interface IWorldObjectManagerObserver
{

	void onGroupAdded(IWorldObjectManager manager, IWorldObjectGroup group);

	void onGroupRemoved(IWorldObjectManager manager, IWorldObjectGroup group);

	void onReloaded(IWorldObjectManager manager);

}
