package com.gildedgames.aether.api.world.preparation;

public interface IPrepManager
{
	IPrepRegistryEntry getRegistryEntry();

	IPrepSectorAccess getAccess();

	IPrepSectorData createSector(int sectorX, int sectorZ);

	void decorateSectorData(IPrepSectorData data);
}
