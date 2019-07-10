package com.gildedgames.aether.common.entities.genes;

import com.gildedgames.aether.api.entity.genes.GeneRegion;
import com.gildedgames.aether.api.entity.genes.IGenePool;
import com.gildedgames.aether.api.entity.genes.IGeneStorage;

import java.util.List;

public abstract class GenePool implements IGenePool
{

	private List<GeneRegion> geneRegions;

	private final IGeneStorage storage;

	public GenePool(IGeneStorage storage)
	{
		this.storage = storage;
	}

	public abstract List<GeneRegion> createGeneRegions();

	public final List<GeneRegion> getGeneRegions()
	{
		if (this.geneRegions == null)
		{
			this.geneRegions = this.createGeneRegions();
		}

		return this.geneRegions;
	}

	@Override
	public IGeneStorage getStorage()
	{
		return this.storage;
	}

}
