package com.gildedgames.orbis.common.data.pathway;

import com.gildedgames.aether.api.orbis_core.data.BlueprintData;

import java.util.ArrayList;
import java.util.List;

public class PathwayData
{

	private List<BlueprintData> pieces = new ArrayList<BlueprintData>();

	public PathwayData()
	{
		super();
	}

	public PathwayData(List<BlueprintData> pieces)
	{
		super();

		this.pieces = new ArrayList<BlueprintData>(pieces);

		for (final BlueprintData b : pieces)
		{
//			if (b.entrances().size() < 2)
//			{
//				this.pieces.remove(b);
//			}
		}
	}

	public void addPiece(BlueprintData piece)
	{
//		if (piece.entrances().size() < 2)
//		{
//			throw new IllegalStateException("You can only add blueprints with at least two entrances to a pathway");
//		}
		this.pieces.add(piece);
	}

	public List<BlueprintData> pieces()
	{
		return this.pieces;
	}

}
