package com.gildedgames.aether.api.world.preparation;

public interface IPrepSectorAccessClient extends IPrepSectorAccess
{
	void addSector(IPrepSector sector);

	void removeSector(IPrepSector sector);
}
