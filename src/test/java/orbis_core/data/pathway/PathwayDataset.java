package orbis_core.data.pathway;

import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.orbis.common.data.pathway.PathwayData;

public class PathwayDataset
{
	private static PathwayData pathway1;

	public static PathwayData pathway1()
	{
		if (pathway1 == null)
		{
			throw new IllegalStateException();
		}
		return pathway1;
	}

	public static void constructPathway(BlueprintData piece1, BlueprintData piece2)
	{
		if (pathway1 != null)
		{
			return;
		}
		pathway1 = new PathwayData();

//		piece1.addEntrance(new BlockPos(0, 1, 3), pathway1);
//		piece1.addEntrance(new BlockPos(3, 1, 0), pathway1);

		pathway1.addPiece(piece1);

//		piece2.addEntrance(new BlockPos(2, 1, 0), pathway1);
//		piece2.addEntrance(new BlockPos(2, 1, 11), pathway1);

		pathway1.addPiece(piece2);
	}
}
