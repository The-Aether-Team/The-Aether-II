package com.gildedgames.orbis.common.data.framework;

import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.orbis.common.data.framework.interfaces.IFrameworkNode;
import com.gildedgames.orbis.common.data.pathway.PathwayData;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class FrameworkNode implements IFrameworkNode
{
	private final IFrameworkNode schedule;

	/**
	 * Gives the position of the node at the start of the algorithm.
	 * When chosen right, this can give the algorithm a good idea of 
	 * where to start and improve performance and quality.
	 * Make sure to place them in a way that looks like the final result. 
	 */
	private final BlockPos approxPosition;

	private final boolean isNullAllowed = false;

	public FrameworkNode(IFrameworkNode schedule, BlockPos approxPosition)
	{
		this.schedule = schedule;

		this.approxPosition = approxPosition;
	}

	public IFrameworkNode schedule()
	{
		return this.schedule;
	}

	/**
	 * Gives the position of the node at the start of the algorithm.
	 * When chosen right, this can give the algorithm a good idea of 
	 * where to start and improve performance and quality.
	 * Make sure to place them in a way that looks like the final result. 
	 */
	public BlockPos approxPosition()
	{
		return this.approxPosition;
	}

	@Override
	public int maxEdges()
	{
		return this.schedule.maxEdges();
	}

	@Override
	public List<BlueprintData> possibleValues(Random random)
	{
		final List<BlueprintData> superPossibleValues = this.schedule.possibleValues(random);
		if (this.isNullAllowed && !superPossibleValues.contains(null))
		{
			superPossibleValues.add(null);
		}
		return superPossibleValues;
	}

	@Override
	public Collection<PathwayData> pathways()
	{
		return this.schedule.pathways();
	}

}
