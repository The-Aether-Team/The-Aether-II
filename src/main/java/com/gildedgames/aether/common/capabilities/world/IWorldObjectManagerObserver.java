package com.gildedgames.aether.common.capabilities.world;

import com.gildedgames.orbis.common.world_object.IWorldObjectGroup;

public interface IWorldObjectManagerObserver
{

	void onGroupAdded(WorldObjectManager manager, IWorldObjectGroup group);

	void onGroupRemoved(WorldObjectManager manager, IWorldObjectGroup group);

	void onReloaded(WorldObjectManager manager);

}
