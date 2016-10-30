package com.gildedgames.aether.common.entities.genes.util;

import com.gildedgames.aether.api.genes.GeneRegion;
import com.gildedgames.aether.api.genes.IGenePool;
import com.gildedgames.aether.api.genes.IGeneStorage;

import java.util.List;

public abstract class GenePool<I, O> implements IGenePool<I, O>
{

	private List<GeneRegion> geneRegions;

	private IGeneStorage<I, O> storage;

	public GenePool(IGeneStorage<I, O> storage)
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
	public IGeneStorage<I, O> getStorage()
	{
		return this.storage;
	}

}
